package com.shendeng_bianmin.agent.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.WxBangModel;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String wx_type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Y.getString(R.string.wx_app_id));
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        // TODO: 2020/6/24 获取code 后，传递给后台，提现的时候 自动提现
        SendAuth.Resp resp = (SendAuth.Resp) baseResp;
        wx_type = PreferenceHelper.getInstance(WXEntryActivity.this).getString(AppCode.WX_TYPE, "");
        if (TextUtils.isEmpty(resp.code)) {
            shibai("微信授权被拒");
        } else {
            chenggong(resp.code);
        }
    }

    private void chenggong(String sendAuth_code) {
        getNet(sendAuth_code);
    }

    private void shibai(String msg) {
        Notice n = new Notice();
        n.content = msg;
        if (wx_type.equals("1")) {
            n.type = ConstanceValue.WX_SET_F;
        } else {
            n.type = ConstanceValue.WX_JIECHU_F;
        }
        RxBus.getDefault().sendRx(n);
        finish();
    }

    private void getNet(String sendAuth_code) {
        Map<String, String> map = new HashMap<>();
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(WXEntryActivity.this).getAppToken());
        if (wx_type.equals("1")) {
            map.put("code", "04184");
        } else {
            map.put("code", "04186");
        }
        map.put("js_code", sendAuth_code);
        map.put("sms_id", PreferenceHelper.getInstance(WXEntryActivity.this).getString(AppCode.SMS_ID, ""));
        map.put("sms_code", PreferenceHelper.getInstance(WXEntryActivity.this).getString(AppCode.SMS_CODE, ""));
        Gson gson = new Gson();
        OkGo.<AppResponse<WxBangModel.DataBean>>post(Urls.WORKER)
                .tag(WXEntryActivity.this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<WxBangModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<WxBangModel.DataBean>> response) {
                        String msg_code = response.body().msg_code;
                        String msg = response.body().msg;
                        if (msg_code.equals("0000")) {
                            if (wx_type.equals("1")) {
                                UserManager.getManager(WXEntryActivity.this).setWx_pay_check("1");
                                UserManager.getManager(WXEntryActivity.this).setWx_user_name(response.body().data.get(0).getWx_store_user_name());
                                Notice n = new Notice();
                                n.type = ConstanceValue.WX_SET_S;
                                RxBus.getDefault().sendRx(n);
                            } else {
                                UserManager.getManager(WXEntryActivity.this).setWx_pay_check("0");
                                Notice n = new Notice();
                                n.type = ConstanceValue.WX_JIECHU_S;
                                RxBus.getDefault().sendRx(n);
                            }
                        } else {
                            shibai(msg);
                        }
                        finish();
                    }

                    @Override
                    public void onError(Response<AppResponse<WxBangModel.DataBean>> response) {
                        super.onError(response);
                        shibai(response.message());
                    }
                });
    }


    /**
     * 注册事件通知
     */
    public Observable<Notice> toObservable() {
        return RxBus.getDefault().toObservable(Notice.class);
    }

    /**
     * 发送消息
     */
    public void sendRx(Notice msg) {
        RxBus.getDefault().sendRx(msg);
    }
}
