package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.BottomDialog;
import com.shendeng_bianmin.agent.dialog.BottomDialogView;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.model.QainbaoModel;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WodeQainbaoActivity extends BaseActivity {

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.ll_mingxi)
    LinearLayout ll_mingxi;
    @BindView(R.id.bt_tixian)
    Button bt_tixian;
    @BindView(R.id.tv_jiesuan_money)
    TextView tv_jiesuan_money;
    @BindView(R.id.ll_jiesuan)
    LinearLayout ll_jiesuan;
    @BindView(R.id.ll_zhanghaoset)
    LinearLayout ll_zhanghaoset;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private QainbaoModel.DataBean dataBean;

    @Override
    public int getContentViewResId() {
        return R.layout.act_mine_qianbao;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    public boolean showToolBarLine() {
        return false;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("我的钱包");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WodeQainbaoActivity.class);
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
        initSM();
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNet();
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04332);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<QainbaoModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<QainbaoModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<QainbaoModel.DataBean>> response) {
                        dataBean = response.body().data.get(0);
                        tv_money.setText(dataBean.getInst_money_access());
                        tv_jiesuan_money.setText(dataBean.getInst_money_ready());
                    }

                    @Override
                    public void onError(Response<AppResponse<QainbaoModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                        finish();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    @OnClick({R.id.ll_mingxi, R.id.bt_tixian, R.id.ll_jiesuan, R.id.ll_zhanghaoset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_mingxi:
                WodeMingxiActivity.actionStart(this);
                break;
            case R.id.bt_tixian:
                tianxian();
                break;
            case R.id.ll_jiesuan:
                WodeJiesuanActivity.actionStart(this);
                break;
            case R.id.ll_zhanghaoset:
                zhaohaoset();
                break;
        }
    }

    private void tianxian() {
        String inst_money_access1 = dataBean.getInst_money_access();
        Y.e("klfjdslkdfjs fsd" + inst_money_access1);
        float inst_money_access = Y.getFloat(inst_money_access1);
        if (inst_money_access > 0) {
            showWeiXinOrZhiFuBaoSelect();
        } else {
            Y.t("当前金额为0，不可提现");
        }
    }

    private void showWeiXinOrZhiFuBaoSelect() {
        List<String> names = new ArrayList<>();
        names.add("微信提现");
        names.add("支付宝提现");
        final BottomDialog bottomDialog = new BottomDialog(this);
        bottomDialog.setModles(names);
        bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
            @Override
            public void onClickItem(int pos) {
                bottomDialog.dismiss();
                if (pos == 0) {
                    if (UserManager.getManager(WodeQainbaoActivity.this).getWx_pay_check().equals("1")) {
                        TixianActivity.actionStart(
                                WodeQainbaoActivity.this,
                                AppCode.code_weixin,
                                dataBean.getInst_money_access(),
                                dataBean.getScore_zd(),
                                dataBean.getScore_tx());
                    } else {
                        Intent intent = new Intent(WodeQainbaoActivity.this, PhoneCheckActivity.class);
                        intent.putExtra("mod_id", AppCode.mod_zhifu_admin);
                        intent.putExtra(AppCode.weixinOrZhiFuBao, AppCode.code_weixin);
                        intent.putExtra("money_use", dataBean.getInst_money_access());
                        intent.putExtra("zuidiMoney", dataBean.getScore_zd());
                        intent.putExtra("shouxufei", dataBean.getScore_tx());
                        startActivity(intent);
                    }
                } else {
                    if (UserManager.getManager(WodeQainbaoActivity.this).getAlipay_number_check().equals("1")) {
                        TixianActivity.actionStart(
                                WodeQainbaoActivity.this,
                                AppCode.code_zhifubao,
                                dataBean.getInst_money_access(),
                                dataBean.getScore_zd(),
                                dataBean.getScore_tx());
                    } else {
                        Intent intent = new Intent(WodeQainbaoActivity.this, PhoneCheckActivity.class);
                        intent.putExtra("mod_id", AppCode.mod_zhifu_admin);
                        intent.putExtra(AppCode.weixinOrZhiFuBao, AppCode.code_zhifubao);
                        intent.putExtra("money_use", dataBean.getInst_money_access());
                        intent.putExtra("zuidiMoney", dataBean.getScore_zd());
                        intent.putExtra("shouxufei", dataBean.getScore_tx());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onClickCancel(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.showBottom();
    }

    private void zhaohaoset() {//支付宝提现账号设置
        if (UserManager.getManager(WodeQainbaoActivity.this).getAlipay_number_check().equals("1")) {
            TishiDialog tishiDialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    Intent intent = new Intent(WodeQainbaoActivity.this, PhoneCheckActivity.class);
                    intent.putExtra("mod_id", AppCode.mod_zhifu_admin);
                    intent.putExtra(AppCode.weixinOrZhiFuBao, AppCode.code_zhifubao);
                    startActivity(intent);
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            String alipay_uname = UserManager.getManager(WodeQainbaoActivity.this).getAlipay_uname();
            tishiDialog.setTextCont("您已绑定用户名为 " + alipay_uname + " 的支付宝账户，是否更换账户");
            tishiDialog.setTextConfirm("去更换");
            tishiDialog.show();

        } else {
            Intent intent = new Intent(WodeQainbaoActivity.this, PhoneCheckActivity.class);
            intent.putExtra("mod_id", AppCode.mod_zhifu_admin);
            intent.putExtra(AppCode.weixinOrZhiFuBao, AppCode.code_zhifubao);
            startActivity(intent);
        }
    }
}
