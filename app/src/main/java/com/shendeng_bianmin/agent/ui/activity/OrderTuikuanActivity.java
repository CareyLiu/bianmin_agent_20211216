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
import com.shendeng_bianmin.agent.adapter.OrderTuikuanAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Success;
import com.shendeng_bianmin.agent.model.OrderTuikuanModel;
import com.shendeng_bianmin.agent.util.FullyLinearLayoutManager;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderTuikuanActivity extends BaseActivity {

    @BindView(R.id.tv_danhao_tuihuo)
    TextView tv_danhao_tuihuo;
    @BindView(R.id.tv_danhao_order)
    TextView tv_danhao_order;
    @BindView(R.id.tv_yuanyin)
    TextView tv_yuanyin;
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
    @BindView(R.id.tv_daojishi)
    TextView tv_daojishi;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.bt1)
    TextView bt1;
    @BindView(R.id.bt2)
    TextView bt2;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_name_mai)
    TextView tv_name_mai;
    @BindView(R.id.tv_adress_tui)
    TextView tv_adress_tui;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.tv_wuliu)
    TextView tv_wuliu;

    private String shop_form_id;
    private String pay_check_index;
    private OrderTuikuanModel.DataBean dataBean;
    private List<String> order_info_arr = new ArrayList<>();
    private OrderTuikuanAdapter adapter;
    private List<String> pay_check_arr;

    @Override
    public int getContentViewResId() {
        return R.layout.act_order_tuikuan;
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
        intent.setClass(context, OrderTuikuanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String shop_form_id) {
        Intent intent = new Intent();
        intent.setClass(context, OrderTuikuanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("shop_form_id", shop_form_id);
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
        initAdapter();
        initSM();
        gerOrderTuikuan();
    }

    private void initAdapter() {
        adapter = new OrderTuikuanAdapter(R.layout.item_order_tuikuan, order_info_arr);
        rv_content.setLayoutManager(new FullyLinearLayoutManager(mContext));
        rv_content.setAdapter(adapter);
        rv_content.setNestedScrollingEnabled(false);
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                gerOrderTuikuan();
            }
        });
    }

    private void gerOrderTuikuan() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04312);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<OrderTuikuanModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderTuikuanModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderTuikuanModel.DataBean>> response) {
                        dataBean = response.body().data.get(0);
                        pay_check_arr = dataBean.getRefund_arr();
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

                        pay_check_index = dataBean.getRefund_index();
                        if (pay_check_index.equals("0")) {
                            bt1.setText("拒绝申请");
                            bt1.setVisibility(View.VISIBLE);
                            bt2.setText("同意申请");
                            bt2.setVisibility(View.VISIBLE);
                        } else if (pay_check_index.equals("1")) {
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);
                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                        } else if (pay_check_index.equals("2")) {
                            if (pay_check_arr.size() == 5) {
                                bt1.setText("确认收货");
                                bt1.setVisibility(View.VISIBLE);
                                bt2.setText("查看物流");
                                bt2.setVisibility(View.VISIBLE);
                            } else {
                                bt2.setText("查看物流");
                                bt1.setVisibility(View.GONE);
                                bt2.setVisibility(View.GONE);
                            }

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu3.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            line2.setBackgroundColor(Y.getColor(R.color.order_red));
                        } else if (pay_check_index.equals("3")) {
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu3.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu4.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            line2.setBackgroundColor(Y.getColor(R.color.order_red));
                            line3.setBackgroundColor(Y.getColor(R.color.order_red));
                        } else if (pay_check_index.equals("4")) {
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);

                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu3.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu4.setBackgroundResource(R.drawable.order_qiu_s);
                            qiu5.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                            line2.setBackgroundColor(Y.getColor(R.color.order_red));
                            line3.setBackgroundColor(Y.getColor(R.color.order_red));
                            line4.setBackgroundColor(Y.getColor(R.color.order_red));
                        }

                        tv_danhao_tuihuo.setText("退货单号：" + dataBean.getRefund_no());
                        tv_danhao_order.setText("订单号：" + dataBean.getForm_no());
                        tv_yuanyin.setText("退款原因：" + dataBean.getRefund_cause());

                        tv_name_mai.setText("卖家收货人：" + dataBean.getInst_worker_name());
                        tv_adress_tui.setText("退货地址：" + dataBean.getInst_addr_all());

                        Glide.with(mContext).load(dataBean.getIndex_photo_url()).into(iv_img);
                        tv_title_name.setText(dataBean.getShop_product_title());
                        tv_num.setText("数量：" + dataBean.getPay_count());
                        tv_money.setText("¥" + dataBean.getPay_money());

                        order_info_arr = dataBean.getOrder_info_arr();
                        adapter.setNewData(order_info_arr);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onStart(Request<AppResponse<OrderTuikuanModel.DataBean>, ? extends Request> request) {
                        showProgressDialog("");
                        super.onStart(request);
                    }
                });
    }

    private void clickBt1() {
        String title = bt1.getText().toString();
        if (title.equals("确认收货")) {//确认收货
            tuihuoSure();
        } else if (title.equals("拒绝申请")) {//拒绝申请
            TishiDialog dialog = new TishiDialog(mContext, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    tuikuanshenhe("6");
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            dialog.setTextCont("是否拒绝退款申请");
            dialog.setTextConfirm("拒绝");
            dialog.show();
        } else if (title.equals("已发货")) {

        } else if (title.equals("交易成功")) {//去评价

        } else if (title.equals("已评价")) {//查看评价

        }
    }

    private void tuihuoSure() {//确认收货
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04319);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(OrderTuikuanActivity.this, new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onError(Response<AppResponse> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    private void tuikuanshenhe(String refund_rate) {//refund_rate  审核结果：2同意6拒绝
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04315);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        map.put("refund_rate", refund_rate);
        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(OrderTuikuanActivity.this, new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onError(Response<AppResponse> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    private void clickBt2() {
        String title = bt2.getText().toString();
        if (title.equals("查看物流")) {//查看物流
            wuliu();
        } else if (title.equals("同意申请")) {//同意申请
            TishiDialog dialog = new TishiDialog(mContext, new TishiDialog.TishiDialogListener() {
                @Override
                public void onClickCancel(View v, TishiDialog dialog) {

                }

                @Override
                public void onClickConfirm(View v, TishiDialog dialog) {
                    tuikuanshenhe("2");
                }

                @Override
                public void onDismiss(TishiDialog dialog) {

                }
            });
            dialog.setTextCont("是否同意退款申请");
            dialog.setTextConfirm("同意");
            dialog.show();
        } else if (title.equals("已发货")) {

        } else if (title.equals("交易成功")) {//去评价

        } else if (title.equals("已评价")) {//查看评价

        }
    }

    @OnClick({R.id.tv_wuliu, R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wuliu:
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
        String express_url = dataBean.getRefund_express_url();
        if (TextUtils.isEmpty(express_url)) {
            Y.t("暂无物流详情");
        } else {
            DefaultX5WebViewActivity.actionStart(mContext, express_url);
        }
    }

    private void clickLianxi() {
        String receiver_phone = dataBean.getUser_phone();
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
}
