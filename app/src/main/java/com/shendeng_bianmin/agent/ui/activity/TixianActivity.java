package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.InputDialog;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Success;
import com.shendeng_bianmin.agent.model.JiesuanModel;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TixianActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.ll_1)
    LinearLayout ll_1;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_zhifufangshi)
    TextView tv_zhifufangshi;
    @BindView(R.id.iv_right_back)
    ImageView iv_right_back;
    @BindView(R.id.view_line)
    View view_line;
    @BindView(R.id.ll_2)
    ConstraintLayout ll_2;
    @BindView(R.id.tv_tixianhuashu)
    TextView tv_tixianhuashu;
    @BindView(R.id.tv_renminbi)
    TextView tv_renminbi;
    @BindView(R.id.et_text)
    EditText et_text;
    @BindView(R.id.cl_3)
    ConstraintLayout cl_3;
    @BindView(R.id.tv_zuiditixian)
    TextView tv_zuiditixian;
    @BindView(R.id.tv_tixiankouchu)
    TextView tv_tixiankouchu;
    @BindView(R.id.show_shui)
    TextView show_shui;
    @BindView(R.id.tv_tixian)
    TextView tv_tixian;

    private String weiXinOrZhiFuBao;
    private String money_use;
    private String zuidiMoney;
    private String shouxufei;
    private double tixianBai;

    @Override
    public int getContentViewResId() {
        return R.layout.act_tixian;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("提现");
    }

    /**
     * 用于其他Activty跳转到该Activity
     * weiXinOrZhiFuBao  1支付宝 2微信
     * money_use  可提现金额
     * zuidiMoney  最低提现金额
     * shouxufei  提现手续费
     */
    public static void actionStart(Context context, String weiXinOrZhiFuBao, String money_use, String zuidiMoney, String shouxufei) {
        Intent intent = new Intent();
        intent.setClass(context, TixianActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("weiXinOrZhiFuBao", weiXinOrZhiFuBao);
        intent.putExtra("money_use", money_use);
        intent.putExtra("zuidiMoney", zuidiMoney);
        intent.putExtra("shouxufei", shouxufei);
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
        weiXinOrZhiFuBao = getIntent().getStringExtra("weiXinOrZhiFuBao");
        money_use = getIntent().getStringExtra("money_use");
        zuidiMoney = getIntent().getStringExtra("zuidiMoney");
        shouxufei = getIntent().getStringExtra("shouxufei");
        et_text.setText(money_use);
        zuidiMoney = "10";
        tv_tixiankouchu.setText("提现扣除比例：" + shouxufei + "收费(最低2元 最高25元)");
        tixianBai = Y.getDouble(shouxufei.replace("%", ""));
        double b = Y.getDouble(money_use);
        double tixianfei = tixianBai * b;
        if (tixianfei <= 2) {
            tixianfei = 2;
        } else if (tixianfei >= 25) {
            tixianfei = 25;
        }
        show_shui.setText("手续费：" + Y.getMoney(tixianfei) + "元");
        tv_zuiditixian.setText("最低提现金额：" + zuidiMoney + "元");

        if (weiXinOrZhiFuBao.equals(AppCode.code_weixin)) {
            tv_msg.setText("提示:请仔细核对微信帐号信息，避免因为帐号错误导致无法及时到账");
            iv_icon.setBackgroundResource(R.mipmap.dingdan_icon_wexin);
            tv_zhifufangshi.setText("微信");
        } else if (weiXinOrZhiFuBao.equals(AppCode.code_zhifubao)) {
            iv_icon.setBackgroundResource(R.mipmap.dingdan_icon_zhifubao);
            tv_zhifufangshi.setText("支付宝");
            tv_msg.setText("提示:请仔细核对支付宝帐号信息，避免因为帐号错误导致无法及时到账");
        }

        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double tixianfei = Y.getDouble(s.toString()) * tixianBai;
                if (tixianfei <= 2) {
                    tixianfei = 2;
                } else if (tixianfei >= 25) {
                    tixianfei = 25;
                }
                show_shui.setText("手续费：" + Y.getMoney(tixianfei) + "元");
            }
        });
    }

    @OnClick({R.id.ll_2, R.id.tv_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_2:
                break;
            case R.id.tv_tixian:
                tixian();
                break;
        }
    }

    private void tixian() {
        String userMoney = et_text.getText().toString();

        if (Y.getDouble(userMoney) < Y.getDouble(zuidiMoney)) {
            Y.t("最低提现金额不能低于10元");
            return;
        }


        String pay_pwd_check = UserManager.getManager(this).getPay_pwd_check();

        if (pay_pwd_check.equals("1")) {

            InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, InputDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, InputDialog dialog) {
                    String payPwd = dialog.getTextContent();

                    if (!TextUtils.isEmpty(payPwd)) {
                        getTixian(payPwd);
                    } else {
                        Y.t("请输入支付密码");
                    }
                }

                @Override
                public void onDismiss(InputDialog dialog) {

                }
            });

            dialog.setTextInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dialog.setTextTitle("请输入支付密码");
            dialog.show();
        } else {
            TishiDialog tishiDialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    PhoneCheckActivity.actionStart(TixianActivity.this, "0006", "4");
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            tishiDialog.setTextCont("您还未设置支付密码，是否去设置");
            tishiDialog.setTextConfirm("去设置");
            tishiDialog.show();
        }
    }

    private void getTixian(String pay_pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04338);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("pay_pwd", pay_pwd);
        map.put("money", et_text.getText().toString());
        map.put("withdraw_type", weiXinOrZhiFuBao);
        Gson gson = new Gson();
        OkGo.<AppResponse<JiesuanModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<JiesuanModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<JiesuanModel.DataBean>> response) {
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(TixianActivity.this, "提现成功", "恭喜您操作成功", new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<JiesuanModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }
}
