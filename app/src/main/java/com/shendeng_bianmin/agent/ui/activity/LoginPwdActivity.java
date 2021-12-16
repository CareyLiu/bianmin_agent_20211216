package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Success;
import com.shendeng_bianmin.agent.model.MingxiDetailsModel;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPwdActivity extends BaseActivity {


    @BindView(R.id.tv_pwd)
    TextView tv_pwd;
    @BindView(R.id.ed_pwd)
    EditText ed_pwd;
    @BindView(R.id.tv_pwd_two)
    TextView tv_pwd_two;
    @BindView(R.id.ed_pwd_two)
    EditText ed_pwd_two;
    @BindView(R.id.bt_ok)
    Button bt_ok;

    private String sms_id;
    private String sms_code;
    private String mod_id;

    @Override
    public int getContentViewResId() {
        return R.layout.act_login_pwd;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String mod_id) {
        Intent intent = new Intent();
        intent.setClass(context, LoginPwdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mod_id", mod_id);
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
        sms_id = PreferenceHelper.getInstance(this).getString(AppCode.SMS_ID, "");
        sms_code = PreferenceHelper.getInstance(this).getString(AppCode.SMS_CODE, "");
        mod_id = getIntent().getStringExtra("mod_id");
        if (mod_id.equals(AppCode.mod_login_pwd) || mod_id.equals(AppCode.mod_login_pwd_zhaohui)) {
            tv_title.setText("设置登录密码");
            ed_pwd.setHint("请输入登录密码");
            ed_pwd_two.setHint("请确认登录密码");
        } else if (mod_id.equals(AppCode.mod_zhifu_pwd)) {
            tv_title.setText("设置支付密码");
            ed_pwd.setHint("请输入支付密码");
            ed_pwd_two.setHint("请确认支付密码");
        }
    }

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        String pwd = ed_pwd.getText().toString();
        String pwd_two = ed_pwd_two.getText().toString();

        if (TextUtils.isEmpty(pwd)) {
            if (mod_id.equals(AppCode.mod_login_pwd) || mod_id.equals(AppCode.mod_login_pwd_zhaohui)) {
                Y.t("请输入登录密码");
            } else if (mod_id.equals(AppCode.mod_zhifu_pwd)) {
                Y.t("请输入支付密码");
            }
            return;
        }

        if (TextUtils.isEmpty(pwd_two)) {
            if (mod_id.equals(AppCode.mod_login_pwd) || mod_id.equals(AppCode.mod_login_pwd_zhaohui)) {
                Y.t("请确认登录密码");
            } else if (mod_id.equals(AppCode.mod_zhifu_pwd)) {
                Y.t("请确认支付密码");
            }
            return;
        }

        if (!pwd.equals(pwd_two)) {
            Y.t("两次密码输入不一致");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04336);
        map.put("key", Urls.KEY);
        map.put("password", pwd);
        map.put("sms_code", sms_code);
        map.put("sms_id", sms_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<MingxiDetailsModel.DataBean>>post(Urls.LOGIN)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MingxiDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MingxiDetailsModel.DataBean>> response) {
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(LoginPwdActivity.this, new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onError(Response<AppResponse<MingxiDetailsModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onStart(Request<AppResponse<MingxiDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog("");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }
}
