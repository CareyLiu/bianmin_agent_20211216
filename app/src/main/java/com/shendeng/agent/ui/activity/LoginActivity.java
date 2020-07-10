package com.shendeng.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng.agent.R;
import com.shendeng.agent.app.AppConfig;
import com.shendeng.agent.app.BaseActivity;
import com.shendeng.agent.app.PreferenceHelper;
import com.shendeng.agent.bean.Notice;
import com.shendeng.agent.callback.DialogCallback;
import com.shendeng.agent.callback.JsonCallback;
import com.shendeng.agent.config.AppResponse;
import com.shendeng.agent.config.UserManager;
import com.shendeng.agent.dialog.BottomDialog;
import com.shendeng.agent.dialog.BottomDialogView;
import com.shendeng.agent.model.LoginUser;
import com.shendeng.agent.model.Message;
import com.shendeng.agent.ui.HomeBasicActivity;
import com.shendeng.agent.util.RxBus;
import com.shendeng.agent.util.TimeCount;
import com.shendeng.agent.util.Urls;
import com.shendeng.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_pwd)
    EditText ed_pwd;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.ll_pwd)
    LinearLayout ll_pwd;
    @BindView(R.id.tv_qiehuan)
    TextView tv_qiehuan;
    @BindView(R.id.tv_zhaohui)
    TextView tv_zhaohui;
    @BindView(R.id.ll_qiehuan)
    LinearLayout ll_qiehuan;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.tv_yinsi)
    TextView tv_yinsi;
    @BindView(R.id.tv_yonghu)
    TextView tv_yonghu;


    private TimeCount timeCount;
    private String smsId;//短信验证码id
    private String req_type;//登录场景:1.密码登陆2.手机验证码登陆

    @Override
    public int getContentViewResId() {
        return R.layout.act_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        req_type = "1";
        timeCount = new TimeCount(60000, 1000, tv_yzm);
        ed_phone.setText("18249030297");
        ed_pwd.setText("123456");
    }

    @OnClick({R.id.tv_yzm, R.id.tv_qiehuan, R.id.tv_zhaohui, R.id.bt_login, R.id.tv_yinsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yzm:
                get_code();
                break;
            case R.id.tv_qiehuan:
                qiehuan();
                break;
            case R.id.tv_zhaohui:
                LoginYzmActivity.actionStart(this);
                break;
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_yinsi:
                DefaultX5WebViewActivity.actionStart(LoginActivity.this, "https://shop.hljsdkj.com/shop_new/privacy_clause");
                break;
            case R.id.tv_yonghu:
                DefaultX5WebViewActivity.actionStart(LoginActivity.this, "https://shop.hljsdkj.com/shop_new/user_agreement");
                break;
        }
    }

    private void qiehuan() {
        List<String> names = new ArrayList<>();
        names.add("用短信验证码登录");
        names.add("用车联网密码登录");
        final BottomDialog bottomDialog = new BottomDialog(this);
        bottomDialog.setModles(names);
        bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
            @Override
            public void onClickItem(int pos) {
                bottomDialog.dismiss();
                if (pos == 0) {
                    req_type = "2";
                    ed_pwd.setHint("请输入验证码");
                    tv_yzm.setVisibility(View.VISIBLE);
                } else {
                    req_type = "1";
                    ed_pwd.setHint("请输入登录密码");
                    tv_yzm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onClickCancel(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.showBottom();
    }


    /**
     * 获取短信验证码
     */
    private void get_code() {
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            Y.t("请输入手机号码");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("code", "00001");
            map.put("key", Urls.KEY);
            map.put("user_phone", ed_phone.getText().toString());
            map.put("mod_id", "0110");//登录注册

            Gson gson = new Gson();
            OkGo.<AppResponse<Message.DataBean>>post(Urls.SERVER_URL + "msg")
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
                            Y.t(response.getException().getMessage());
                            timeCount.cancel();
                            timeCount.onFinish();
                        }
                    });
        }
    }

    /**
     * 登陆
     */
    private void login() {
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            Y.t("请输入手机号码");
            return;
        }

        if (TextUtils.isEmpty(ed_pwd.getText().toString())) {
            if (req_type.equals("1")) {
                Y.t("请输入登录密码");
            } else {
                Y.t("请输入验证码");
            }
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("code", "04310");
        map.put("key", Urls.KEY);
        map.put("req_type", req_type);
        map.put("user_phone", ed_phone.getText().toString());
        switch (req_type) {
            case "1"://密码登录
                map.put("user_pwd", ed_pwd.getText().toString());
                break;
            case "2"://手机验证码登录
                map.put("sms_id", smsId);
                map.put("sms_code", ed_pwd.getText().toString());
                break;
        }
        Gson gson = new Gson();
        OkGo.<AppResponse<LoginUser.DataBean>>post(Urls.LOGIN)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new DialogCallback<AppResponse<LoginUser.DataBean>>(this) {
                    @Override
                    public void onSuccess(Response<AppResponse<LoginUser.DataBean>> response) {
                        PreferenceHelper.getInstance(LoginActivity.this).putString("user_phone", ed_phone.getText().toString() + "");
                        UserManager.getManager(LoginActivity.this).saveUser(response.body().data.get(0));
                        startActivity(new Intent(LoginActivity.this, HomeBasicActivity.class));
                        String rongYunTouken = UserManager.getManager(mContext).getRongYun();

                        if (!StringUtils.isEmpty(rongYunTouken)) {
                            connectRongYun(response.body().data.get(0).getToken_rong());


                        }
                    }

                    @Override
                    public void onError(Response<AppResponse<LoginUser.DataBean>> response) {
                        Y.t(response.getException().getMessage());
                    }
                });
    }


    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public void connectRongYun(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallbackEx() {
            /**
             * 数据库回调.
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                Log.i("rongYun", "数据库打开失败");
            }

            /**
             * token 无效
             */
            @Override
            public void onTokenIncorrect() {
                Log.i("rongYun", "token 无效");
            }

            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            @Override
            public void onSuccess(String userId) {
                //UIHelper.ToastMessage(mContext, "融云连接成功");
                Log.i("rongYun", "融云连接成功");
                PreferenceHelper.getInstance(mContext).putString(AppConfig.RONGYUN_TOKEN, token);
            }

            /**
             * 错误回调
             * @param errorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("rongYun", "融云连接失败");
            }
        });

    }


}
