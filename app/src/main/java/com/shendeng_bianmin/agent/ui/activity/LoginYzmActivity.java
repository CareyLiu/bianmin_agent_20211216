package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.model.Message;
import com.shendeng_bianmin.agent.util.TimeCount;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginYzmActivity extends BaseActivity {

    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_code)
    EditText ed_code;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.bt_ok)
    Button bt_ok;

    private TimeCount timeCount;
    private String smsId;//短信验证码id

    @Override
    public int getContentViewResId() {
        return R.layout.act_login_yzm;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("手机验证");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginYzmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        timeCount = new TimeCount(60000, 1000, tv_yzm);
    }

    @OnClick({R.id.tv_yzm, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yzm:
                getCode();
                break;
            case R.id.bt_ok:
                requestData();
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getCode() {
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            Y.t("请输入手机号");
            return;
        }
        showProgressDialog();
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_00001);
        map.put("key", Urls.KEY);
        map.put("user_phone", ed_phone.getText().toString());
        map.put("mod_id", AppCode.mod_login_pwd_zhaohui);
        Gson gson = new Gson();
        OkGo.<AppResponse<Message.DataBean>>post(Urls.MSG)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<Message.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<Message.DataBean>> response) {
                        Y.t(response.body().msg);
                        timeCount.start();
                        smsId = response.body().data.get(0).getSms_id();
                    }

                    @Override
                    public void onError(Response<AppResponse<Message.DataBean>> response) {
                        Y.tError(response);
                        timeCount.cancel();
                        timeCount.onFinish();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 提交验证
     */
    private void requestData() {
        String sms_code = ed_code.getText().toString();
        if (TextUtils.isEmpty(sms_code)) {
            Y.t("请输入验证码");
            return;
        }

        if (TextUtils.isEmpty(smsId)) {
            Y.t("请发送验证码");
            return;
        }

        PreferenceHelper.getInstance(mContext).putString(AppCode.SMS_ID, smsId);
        PreferenceHelper.getInstance(mContext).putString(AppCode.SMS_CODE, sms_code);
        LoginPwdActivity.actionStart(mContext, AppCode.mod_login_pwd_zhaohui);
    }
}
