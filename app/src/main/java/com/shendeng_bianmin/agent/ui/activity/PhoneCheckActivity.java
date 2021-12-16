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
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.Message;
import com.shendeng_bianmin.agent.util.TimeCount;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PhoneCheckActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_yzm)
    TextView tvYzm;
    @BindView(R.id.bt_ok)
    Button btOk;
    private String mod_id;
    private String weixinOrZhiFuBao;//1支付宝 5.支付宝提现账号修改 2微信 3微信解绑 4修改支付密码 5修改蜜蜜
    private TimeCount timeCount;
    private String smsId;
    private String sms_code;
    private IWXAPI api;

    @Override
    public int getContentViewResId() {
        return R.layout.act_phone_check;
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
        Intent intent = new Intent(context, PhoneCheckActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param weixinOrZhiFuBao 1支付宝 5.支付宝提现账号修改 2微信 3微信解绑 4修改支付密码 5修改蜜蜜
     */
    public static void actionStart(Context context, String mod_id, String weixinOrZhiFuBao) {
        Intent intent = new Intent(context, PhoneCheckActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mod_id", mod_id);
        intent.putExtra(AppCode.weixinOrZhiFuBao, weixinOrZhiFuBao);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mod_id = getIntent().getStringExtra("mod_id");
        weixinOrZhiFuBao = getIntent().getStringExtra(AppCode.weixinOrZhiFuBao);
        tvPhone.setText(UserManager.getManager(this).getUser_phone());
        timeCount = new TimeCount(60000, 1000, tvYzm);
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.WX_SET_S) {
                    Y.t("微信绑定成功");
                    finish();
                } else if (message.type == ConstanceValue.WX_JIECHU_S) {
                    Y.t("微信解绑成功");
                    finish();
                } else if (message.type == ConstanceValue.WX_SET_F) {
                    Y.t((String) message.content);

                } else if (message.type == ConstanceValue.WX_JIECHU_F) {
                    Y.t((String) message.content);
                    Y.e("开发了将大幅度收看电视");
                }
            }
        }));

        if (weixinOrZhiFuBao.equals(AppCode.code_pwd_zhifu)) {
            tv_title.setText("设置支付密码");
        } else if (weixinOrZhiFuBao.equals(AppCode.code_weixin_jie)) {
            tv_title.setText("解绑微信");
        } else if (weixinOrZhiFuBao.equals(AppCode.code_weixin)) {
            tv_title.setText("绑定微信");
        } else if (weixinOrZhiFuBao.equals(AppCode.code_zhifubao)) {
            tv_title.setText("绑定支付宝");
        } else if (weixinOrZhiFuBao.equals(AppCode.code_pwd_login)) {
            tv_title.setText("设置登录密码");
        }
    }

    /**
     * 获取短信验证码
     */
    private void get_code() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_00001);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("mod_id", mod_id);//微信支付宝0111, 修改登录密码0112，修改支付密码0113
        Gson gson = new Gson();
        Y.e("接口数据是是呢 " + gson.toJson(map));
        OkGo.<AppResponse<Message.DataBean>>post(Urls.MSG)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<Message.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<Message.DataBean>> response) {
                        Y.t("验证码获取成功");
                        timeCount.start();
                        if (response.body().data.size() > 0) {
                            smsId = response.body().data.get(0).getSms_id();
                        }
                    }

                    @Override
                    public void onError(Response<AppResponse<Message.DataBean>> response) {
                        Y.tError(response);
                        timeCount.cancel();
                        timeCount.onFinish();
                    }

                    @Override
                    public void onStart(Request<AppResponse<Message.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
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
        sms_code = edCode.getText().toString();
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

        if (weixinOrZhiFuBao.equals(AppCode.code_pwd_zhifu) || weixinOrZhiFuBao.equals(AppCode.code_pwd_login)) {
            LoginPwdActivity.actionStart(this, mod_id);
            finish();
        } else if (weixinOrZhiFuBao.equals(AppCode.code_weixin_jie)) {
            PreferenceHelper.getInstance(mContext).putString(AppCode.WX_TYPE, "2");
            api = WXAPIFactory.createWXAPI(mContext, Y.getString(R.string.wx_app_id));
            if (!api.isWXAppInstalled()) {
                Y.t("您的设备未安装微信客户端");
            } else {
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
            }
        } else if (weixinOrZhiFuBao.equals(AppCode.code_weixin)) {
            PreferenceHelper.getInstance(mContext).putString(AppCode.WX_TYPE, "1");
            api = WXAPIFactory.createWXAPI(mContext, Y.getString(R.string.wx_app_id));
            if (!api.isWXAppInstalled()) {
                Y.t("您的设备未安装微信客户端");
            } else {
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
            }
        } else if (weixinOrZhiFuBao.equals(AppCode.code_zhifubao)) {
            SetAlipayActivity.actionStart(this);
            finish();
        }
    }


    @OnClick({R.id.tv_yzm, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yzm:
                get_code();
                break;
            case R.id.bt_ok:
                requestData();
                break;
        }
    }
}
