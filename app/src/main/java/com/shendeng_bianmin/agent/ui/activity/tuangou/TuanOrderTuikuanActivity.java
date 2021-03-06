package com.shendeng_bianmin.agent.ui.activity.tuangou;

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
import com.shendeng_bianmin.agent.ui.activity.DefaultX5WebViewActivity;
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

public class TuanOrderTuikuanActivity extends BaseActivity {

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
        tv_title.setText("????????????");
    }

    /**
     * ????????????Activty????????????Activity
     */
    public static void actionStart(Context context, String shop_form_id) {
        Intent intent = new Intent();
        intent.setClass(context, TuanOrderTuikuanActivity.class);
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
                            bt1.setText("????????????");
                            bt1.setVisibility(View.VISIBLE);
                            bt2.setText("????????????");
                            bt2.setVisibility(View.VISIBLE);
                        } else if (pay_check_index.equals("1")) {
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);
                            qiu2.setBackgroundResource(R.drawable.order_qiu_s);
                            line1.setBackgroundColor(Y.getColor(R.color.order_red));
                        } else if (pay_check_index.equals("2")) {
                            if (pay_check_arr.size() == 5) {
                                bt1.setText("????????????");
                                bt1.setVisibility(View.VISIBLE);
                                bt2.setText("????????????");
                                bt2.setVisibility(View.VISIBLE);
                            } else {
                                bt2.setText("????????????");
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

                        tv_danhao_tuihuo.setText("???????????????" + dataBean.getRefund_no());
                        tv_danhao_order.setText("????????????" + dataBean.getForm_no());
                        tv_yuanyin.setText("???????????????" + dataBean.getRefund_cause());

                        tv_name_mai.setText("??????????????????" + dataBean.getInst_worker_name());
                        tv_adress_tui.setText("???????????????" + dataBean.getInst_addr_all());

                        Glide.with(mContext).load(dataBean.getIndex_photo_url()).into(iv_img);
                        tv_title_name.setText(dataBean.getShop_product_title());
                        tv_num.setText("?????????" + dataBean.getPay_count());
                        tv_money.setText("??" + dataBean.getPay_money());

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
        if (title.equals("????????????")) {//????????????
            tuihuoSure();
        } else if (title.equals("????????????")) {//????????????
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
            dialog.setTextCont("????????????????????????");
            dialog.setTextConfirm("??????");
            dialog.show();
        } else if (title.equals("?????????")) {

        } else if (title.equals("????????????")) {//?????????

        } else if (title.equals("?????????")) {//????????????

        }
    }

    private void tuihuoSure() {//????????????
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
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(TuanOrderTuikuanActivity.this, new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
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

    private void tuikuanshenhe(String refund_rate) {//refund_rate  ???????????????2??????6??????
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
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(TuanOrderTuikuanActivity.this, new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
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
        if (title.equals("????????????")) {//????????????
            wuliu();
        } else if (title.equals("????????????")) {//????????????
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
            dialog.setTextCont("????????????????????????");
            dialog.setTextConfirm("??????");
            dialog.show();
        } else if (title.equals("?????????")) {

        } else if (title.equals("????????????")) {//?????????

        } else if (title.equals("?????????")) {//????????????

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
            Y.t("??????????????????");
        } else {
            DefaultX5WebViewActivity.actionStart(mContext, express_url);
        }
    }

    private void clickLianxi() {
        String receiver_phone = dataBean.getUser_phone();
        if (TextUtils.isEmpty(receiver_phone)) {
            Y.t("????????????????????????");
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
        dialog.setTextTitle("????????????");
        dialog.setTextCont(receiver_phone);
        dialog.setTextConfirm("??????");
        dialog.show();
    }

    /**
     * ?????????????????????????????????????????????????????????????????? * * @param phoneNum ????????????
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
