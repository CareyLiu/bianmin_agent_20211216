/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shendeng.agent.callback;


import android.app.Dialog;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;
import com.shendeng.agent.app.App;
import com.shendeng.agent.app.AppManager;
import com.shendeng.agent.config.AppResponse;
import com.shendeng.agent.config.SimpleResponse;
import com.shendeng.agent.config.UserManager;
import com.shendeng.agent.dialog.TishiDialog;
import com.shendeng.agent.ui.activity.LoginActivity;
import com.shendeng.agent.ui.activity.SettingActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;


public class JsonConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public JsonConvert() {

    }

    public JsonConvert(Type type) {
        this.type = type;
    }

    public JsonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象，生成onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }
        Log.d("AppType", type + "");
        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<T>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<T> rawType) throws Exception {
        if (rawType == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (rawType == String.class) {
            //noinspection unchecked
            return (T) body.string();
        } else if (rawType == JSONObject.class) {
            //noinspection unchecked
            return (T) new JSONObject(body.string());
        } else if (rawType == JSONArray.class) {
            //noinspection unchecked
            return (T) new JSONArray(body.string());
        } else {
            T t = Convert.fromJson(body.string(), rawType);
            response.close();
            return t;
        }
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        // 泛型格式如下： new JsonCallback<任意JavaBean>(this)
        T t = Convert.fromJson(body.string(), type);
        response.close();
        return t;
    }

    private T parseParameterizedType(Response response, ParameterizedType type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type rawType = type.getRawType();                     // 泛型的实际类型
        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数
        if (rawType != AppResponse.class) {
            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
            T t = Convert.fromJson(body.string(), type);
            response.close();
            return t;
        } else {
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonCallback<AppResponse<Void>>(this)
                SimpleResponse simpleResponse = Convert.fromJson(body.string(), SimpleResponse.class);
                response.close();
                //noinspection unchecked
                return (T) simpleResponse.toAppResponse();
            } else {
                // 泛型格式如下： new JsonCallback<AppResponse<内层JavaBean>>(this)

                AppResponse appResponse = Convert.fromJson(body.string(), type);
                response.close();
                String msg_code = appResponse.msg_code;
                //这里的0是以下意思
                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
                if (msg_code.equals("0000")) {
                    //noinspection unchecked
                    return (T) appResponse;
                } else if (msg_code.equals("0003")) {
                    loginOut();
                    throw new IllegalStateException(appResponse.msg);
                } else {
                    //直接将服务端的错误信息抛出，onError中可以获取
                    throw new IllegalStateException("错误代码：" + msg_code + "，错误信息：" + appResponse.msg);
                }
            }
        }
    }

    private void loginOut() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();//增加部分

                TishiDialog dialog = new TishiDialog(AppManager.getAppManager().currentActivity(), new TishiDialog.TishiDialogListener() {
                    @Override
                    public void onClickCancel(View v, TishiDialog dialog) {

                    }

                    @Override
                    public void onClickConfirm(View v, TishiDialog dialog) {

                    }

                    @Override
                    public void onDismiss(TishiDialog dialog) {
                        AppManager.getAppManager().currentActivity().startActivity(new Intent(AppManager.getAppManager().currentActivity(), LoginActivity.class));
                        UserManager.getManager(AppManager.getAppManager().currentActivity()).removeUser();
                    }
                });
                dialog.setTextCont("您的登录信息已失效，请重新登录");
                dialog.setTextCancel("");
                dialog.show();
                Looper.loop();//增
            }
        }).start();
    }
}
