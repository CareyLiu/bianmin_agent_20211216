package com.shendeng_bianmin.agent.ui.activity.rongyun_liaotian;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;


import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class ConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加会话界面
        ConversationFragment conversationFragment = new ConversationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationFragment);
        transaction.commit();
        String str = getIntent().getStringExtra("dianpuming");
        String inst_accid = getIntent().getStringExtra("inst_accid");
        String shopType = getIntent().getStringExtra("shoptype");
        tv_title.setText(str);
        getLiaoTian(mContext, inst_accid, shopType);

        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.MSG_RONGYUN_STATE) {
                    RongIMClient.ConnectionStatusListener.ConnectionStatus status = (RongIMClient.ConnectionStatusListener.ConnectionStatus) message.content;
                    if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONN_USER_BLOCKED)) {
                        //* 用户被开发者后台封禁
                        // notice.content = status.CONN_USER_BLOCKED;
                        UIHelper.ToastMessage(mContext, "该商户已被封禁");
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                        //连接成功
                        showLoadSuccess();
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTING)) {
                        //连接中
                        // notice.content = status.CONNECTING;
                        showLoading();
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)) {
                        //断开连接
                        // notice.content = status.DISCONNECTED;
                        showLoadFailed();
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT)) {
                        //用户账户在其他设备登录，本机会被踢掉线。
                        UIHelper.ToastMessage(mContext, "用户账户在其他设备登录，本机会被踢掉线。");
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.NETWORK_UNAVAILABLE)) {
                        //网络不可用
                        showLoadFailed();
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.SERVER_INVALID)) {
                        //TOKEN_INCORRECT
                        //连接失败
                        UIHelper.ToastMessage(mContext, "连接失败");
                    } else if (status.getMessage().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.TOKEN_INCORRECT)) {
                        //Token 不正确
                        //notice.content = status.TOKEN_INCORRECT;
                    }
                }
            }
        }));


    }

    @Override
    public int getContentViewResId() {
        return R.layout.conversation;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("关于我们");
        tv_title.setTextSize(17);
        tv_title.setTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationIcon(R.mipmap.backbutton);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(findViewById(R.id.cl_layout).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
    }

    public void getLiaoTian(Context context, String inst_accid, String shopType) {
        Map<String, String> map = new HashMap<>();
        map.put("token", UserManager.getManager(context).getAppToken());
        map.put("code", "04187");
        map.put("key", Urls.KEY);
        map.put("accid", inst_accid);
        map.put("shop_type", shopType);

//        Gson gson = new Gson();
//        OkGo.<AppResponse<Object>>
//                post(Urls.WORKER).
//                tag(context).
//                upJson(gson.toJson(map)).
//                execute(new JsonCallback<AppResponse<Object>>() {
//                    @Override
//                    public void onSuccess(Response<AppResponse<Object>> response) {
//                        //  UIHelper.ToastMessage(context, "发送成功", Toast.LENGTH_SHORT);
//                        UIHelper.ToastMessage(mContext, "建立聊天成功");
//                        showLoadSuccess();
//                    }
//
//                    @Override
//                    public void onError(Response<AppResponse<Object>> response) {
//                        Y.t("错误服务风刀霜剑");
//                    }
//
//                    @Override
//                    public void onStart(Request<AppResponse<Object>, ? extends Request> request) {
//                        super.onStart(request);
//                        showLoading();
//                    }
//                });
    }

}