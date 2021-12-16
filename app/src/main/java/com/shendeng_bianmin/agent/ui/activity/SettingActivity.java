package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.TishiDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_pwd_login)
    LinearLayout ll_pwd_login;
    @BindView(R.id.ll_pwd_pay)
    LinearLayout ll_pwd_pay;
    @BindView(R.id.tv_zhifubao_ming)
    TextView tv_zhifubao_ming;
    @BindView(R.id.ll_zhifubao)
    LinearLayout ll_zhifubao;
    @BindView(R.id.tv_weixin_ming)
    TextView tv_weixin_ming;
    @BindView(R.id.ll_weixin)
    LinearLayout ll_weixin;
    @BindView(R.id.ll_dianpu)
    LinearLayout ll_dianpu;
    @BindView(R.id.bt_login_out)
    TextView bt_login_out;
    private String pay_pwd_check;
    private String wx_pay_check;
    private String alipay_number_check;
    private String wx_user_name;
    private String alipay_uname;

    @Override
    public int getContentViewResId() {
        return R.layout.act_mine_setting;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("设置");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String inst_owner) {
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("inst_owner", inst_owner);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        String inst_owner = getIntent().getStringExtra("inst_owner");

        //       if (inst_owner.equals("1")) {
//            pay_pwd_check = UserManager.getManager(this).getPay_pwd_check();
//            wx_pay_check = UserManager.getManager(this).getWx_pay_check();
//            alipay_number_check = UserManager.getManager(this).getAlipay_number_check();
//
//            if (wx_pay_check.equals("1")) {
//                wx_user_name = UserManager.getManager(this).getWx_user_name();
//                tv_weixin_ming.setText(wx_user_name);
//            }
//
//            if (alipay_number_check.equals("1")) {
//                alipay_uname = UserManager.getManager(this).getAlipay_uname();
//                tv_zhifubao_ming.setText(alipay_uname);
//            }
//        } else {
//            ll_pwd_pay.setVisibility(View.GONE);
//            ll_weixin.setVisibility(View.GONE);
//            ll_zhifubao.setVisibility(View.GONE);
//        }
        ll_pwd_pay.setVisibility(View.GONE);
        ll_weixin.setVisibility(View.GONE);
        ll_zhifubao.setVisibility(View.GONE);
        ll_dianpu.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_pwd_login, R.id.ll_pwd_pay, R.id.ll_zhifubao, R.id.ll_weixin, R.id.ll_dianpu, R.id.bt_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_pwd_login:
                setLogin();
                break;
            case R.id.ll_pwd_pay:
                setPay();
                break;
            case R.id.ll_zhifubao:
                zhifubao();
                break;
            case R.id.ll_weixin:
                weixin();
                break;
            case R.id.ll_dianpu:
                break;
            case R.id.bt_login_out:
                loginOut();
                break;
        }
    }

    private void setLogin() {
        PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_login_pwd, AppCode.code_pwd_login);
    }

    private void setPay() {
        if (pay_pwd_check.equals("1")) {
            TishiDialog tishiDialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_zhifu_pwd, AppCode.code_pwd_zhifu);
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            tishiDialog.setTextCont("您已设置支付密码，是否去修改");
            tishiDialog.setTextConfirm("去修改");
            tishiDialog.show();
        } else {
            PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_zhifu_pwd, AppCode.code_pwd_zhifu);
        }
    }

    private void weixin() {
        if (wx_pay_check.equals("1")) {
            TishiDialog tishiDialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_zhifu_admin, AppCode.code_weixin_jie);
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            tishiDialog.setTextCont("您已绑定用户名为 " + wx_user_name + " 的微信账户，是否解绑");
            tishiDialog.setTextConfirm("去解绑");
            tishiDialog.show();
        } else {
            PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_zhifu_admin, AppCode.code_weixin);

        }
    }

    private void zhifubao() {
        if (alipay_number_check.equals("1")) {
            TishiDialog tishiDialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_zhifu_admin, AppCode.code_zhifubao);
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            tishiDialog.setTextCont("您已绑定用户名为 " + alipay_uname + " 的支付宝账户，是否更换账户");
            tishiDialog.setTextConfirm("去更换");
            tishiDialog.show();
        } else {
            PhoneCheckActivity.actionStart(SettingActivity.this, AppCode.mod_zhifu_admin, AppCode.code_zhifubao);
        }
    }

    private void loginOut() {
        TishiDialog tishiDialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, TishiDialog dialog) {

            }

            @Override
            public void onClickConfirm(View v, TishiDialog dialog) {
                UserManager.getManager(SettingActivity.this).removeUser();
                LoginActivity.actionStart(SettingActivity.this);
                RongIM.getInstance().logout();
            }

            @Override
            public void onDismiss(TishiDialog dialog) {

            }
        });
        tishiDialog.setTextCont("确认退出登录");
        tishiDialog.setTextConfirm("退出登录");
        tishiDialog.show();
    }
}
