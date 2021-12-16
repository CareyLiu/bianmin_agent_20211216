package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.model.OrderDetailsModel;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.qiu1)
    View qiu1;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.qiu2)
    View qiu2;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.qiu3)
    View qiu3;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.qiu4)
    View qiu4;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.qiu5)
    View qiu5;

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_adress)
    TextView tv_adress;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_wuliu)
    TextView tv_wuliu;
    @BindView(R.id.tv_title_msg)
    TextView tv_title_msg;
    @BindView(R.id.tv_taocan)
    TextView tv_taocan;
    @BindView(R.id.tv_lianxi)
    TextView tv_lianxi;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_beizhu)
    TextView tv_beizhu;
    @BindView(R.id.tv_youhui)
    TextView tv_youhui;
    @BindView(R.id.tv_mai_name)
    TextView tv_mai_name;
    @BindView(R.id.tv_bianhao)
    TextView tv_bianhao;
    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;
    @BindView(R.id.tv_yunfei)
    TextView tv_yunfei;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.bt1)
    TextView bt1;
    @BindView(R.id.bt2)
    TextView bt2;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private String shop_form_id;
    private OrderDetailsModel.DataBean dataBean;
    private String pay_check_index;
    private List<String> pay_check_arr;

    @Override
    public int getContentViewResId() {
        return R.layout.act_order_details;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("订单详情");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OrderDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, OrderDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
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
        shop_form_id = getIntent().getExtras().getString("shop_form_id");

        gerOrtherDetails();

        initSM();
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                gerOrtherDetails();
            }
        });
    }

    private void gerOrtherDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04313);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<OrderDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderDetailsModel.DataBean>> response) {
                        dataBean = response.body().data.get(0);

                        pay_check_arr = dataBean.getPay_check_arr();

                        if (pay_check_arr.size() == 5) {
                            tv1.setText(pay_check_arr.get(0));
                            tv2.setText(pay_check_arr.get(1));
                            tv3.setText(pay_check_arr.get(2));
                            tv4.setText(pay_check_arr.get(3));
                            tv5.setText(pay_check_arr.get(4));
                        } else if (pay_check_arr.size() == 4) {
                            tv1.setText(pay_check_arr.get(0));
                            tv2.setText(pay_check_arr.get(1));
                            tv3.setText(pay_check_arr.get(2));
                            tv4.setText(pay_check_arr.get(3));
                            tv5.setVisibility(View.GONE);
                            qiu5.setVisibility(View.GONE);
                            line4.setVisibility(View.GONE);
                        } else if (pay_check_arr.size() == 3) {
                            tv1.setText(pay_check_arr.get(0));
                            tv2.setText(pay_check_arr.get(1));
                            tv3.setText(pay_check_arr.get(2));
                            tv4.setVisibility(View.GONE);
                            tv5.setVisibility(View.GONE);
                            qiu4.setVisibility(View.GONE);
                            qiu5.setVisibility(View.GONE);
                            line3.setVisibility(View.GONE);
                            line4.setVisibility(View.GONE);
                        }

                        pay_check_index = dataBean.getPay_check_index();
                        if (pay_check_index.equals("0")) {
                            bt1.setText("修改快递费");
                            bt1.setTextColor(Y.getColor(R.color.text_red));
                            bt2.setText("关闭此交易");
                            bt2.setTextColor(Y.getColor(R.color.text_color_3));
                            bt1.setVisibility(View.VISIBLE);
                        } else if (pay_check_index.equals("1")) {
                            bt1.setVisibility(View.GONE);
                            bt2.setText("去发货");
                            bt2.setTextColor(Y.getColor(R.color.text_red));

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            bt2.setVisibility(View.VISIBLE);
                        } else if (pay_check_index.equals("2")) {
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu3.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            line2.setBackgroundColor(Y.getColor(R.color.order_red));
                        } else if (pay_check_index.equals("3")) {
                            bt1.setText("查看欠款去向");
                            bt1.setTextColor(Y.getColor(R.color.text_color_3));
                            bt1.setVisibility(View.GONE);
                            bt2.setText("去评价");
                            bt2.setTextColor(Y.getColor(R.color.text_red));
                            bt2.setVisibility(View.VISIBLE);

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu3.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu4.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            line2.setBackgroundColor(Y.getColor(R.color.order_red));
                            line3.setBackgroundColor(Y.getColor(R.color.order_red));
                        } else if (pay_check_index.equals("4")) {
                            bt1.setText("查看欠款去向");
                            bt1.setTextColor(Y.getColor(R.color.text_color_3));
                            bt1.setVisibility(View.GONE);
                            bt2.setText("查看评价");
                            bt2.setTextColor(Y.getColor(R.color.text_red));
                            bt2.setVisibility(View.VISIBLE);

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu3.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu4.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu5.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            line2.setBackgroundColor(Y.getColor(R.color.order_red));
                            line3.setBackgroundColor(Y.getColor(R.color.order_red));
                            line4.setBackgroundColor(Y.getColor(R.color.order_red));
                        }

                        if (pay_check_arr.size() == 4) {
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);
                        }

                        Glide.with(OrderDetailsActivity.this).load(dataBean.getIndex_photo_url()).into(iv_img);
                        tv_money.setText("¥ " + dataBean.getPay_money());
                        tv_time.setText(dataBean.getCreate_time());
                        tv_title_msg.setText(dataBean.getShop_product_title());
                        tv_taocan.setText(dataBean.getProduct_title() + "        X" + dataBean.getPay_count());
                        tv_adress.setText(dataBean.getUser_addr_all());

                        tv_beizhu.setText("订单备注：" + dataBean.getShop_form_text());
                        tv_youhui.setText("优惠券：");
                        tv_mai_name.setText("买家昵称：" + dataBean.getUser_name());
                        tv_bianhao.setText("订单编号：" + dataBean.getForm_no());
                        tv_pay_type.setText("支付方式：" + dataBean.getPay_name());
                        tv_yunfei.setText("运费：" + dataBean.getForm_money_go());
                        tv_pay_time.setText("交易时间：" + dataBean.getCreate_time());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onStart(Request<AppResponse<OrderDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog("");
                    }
                });
    }

    @OnClick({R.id.tv_wuliu, R.id.tv_lianxi, R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wuliu:
                wuliu();
                break;
            case R.id.tv_lianxi:
                clickLianxi();
                break;
            case R.id.bt1:
                clickBt1();
                break;
            case R.id.bt2:
                clickBt2();
                break;
        }
    }

    private void wuliu() {
        String express_url = dataBean.getExpress_url();
        if (TextUtils.isEmpty(express_url)) {
            Y.t("暂无物流详情");
        } else {
            DefaultX5WebViewActivity.actionStart(mContext, express_url);
        }
    }

    private void clickLianxi() {
        String receiver_phone = dataBean.getReceiver_phone();
        if (TextUtils.isEmpty(receiver_phone)) {
            Y.t("暂无买家联系方式");
            return;
        }

        TishiDialog dialog = new TishiDialog(this, new TishiDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, TishiDialog dialog) {

            }

            @Override
            public void onClickConfirm(View v, TishiDialog dialog) {
                callPhone(receiver_phone);
            }

            @Override
            public void onDismiss(TishiDialog dialog) {

            }
        });
        dialog.setTextTitle("联系买家");
        dialog.setTextCont(receiver_phone);
        dialog.setTextConfirm("呼叫");
        dialog.show();
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打） * * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    private void clickBt1() {
        String title = pay_check_arr.get(Y.getInt(pay_check_index));
        if (title.equals("已拍下")) {//修改快递费
            OrderKuaidiActivity.actionStart(mContext, dataBean.getForm_money_go(), shop_form_id);
        } else if (title.equals("已付款")) {

        } else if (title.equals("已发货")) {

        } else if (title.equals("交易成功")) {//查看欠款去向
            Y.t("查看欠款去向");
        } else if (title.equals("已评价")) {//查看欠款去向
            Y.t("查看欠款去向");
        }
    }

    private void clickBt2() {
        String title = pay_check_arr.get(Y.getInt(pay_check_index));
        if (title.equals("已拍下")) {//关闭此交易
            Y.t("关闭此交易");
        } else if (title.equals("已付款")) {//去发货
            OrderFahuoActivity.actionStart(this, shop_form_id);
        } else if (title.equals("已发货")) {

        } else if (title.equals("交易成功")) {//去评价
            OrderPingjiaActivity.actionStart(mContext, shop_form_id);
        } else if (title.equals("已评价")) {//查看评价
            OrderPingjiaActivity.actionStart(mContext, shop_form_id);
        }
    }}
