package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.OrderDetailsAdapter;
import com.shendeng_bianmin.agent.adapter.OrderNewAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.OrderDetailsModel1;
import com.shendeng_bianmin.agent.model.WaresListBean;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class BianMinDingDanXiangQingActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_mai_name)
    TextView tvMaiName;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_yunfei)
    TextView tvYunfei;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.bt1)
    TextView bt1;
    @BindView(R.id.bt2)
    TextView bt2;
    OrderDetailsAdapter adapter;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    String shop_form_id;

    private OrderDetailsAdapter orderDetailsAdapter;
    private OrderDetailsModel1.DataBean dataBean;
    private List<OrderDetailsModel1.DataBean.WaresListBean> mDatas = new ArrayList<OrderDetailsModel1.DataBean.WaresListBean>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shop_form_id = getIntent().getStringExtra("shopFromId");
        gerOrtherDetails();
    }

    private void gerOrtherDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04406);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<OrderDetailsModel1.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderDetailsModel1.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderDetailsModel1.DataBean>> response) {

                        dataBean = response.body().data.get(0);


                        mDatas = dataBean.getWares_list();
                        adapter = new OrderDetailsAdapter(R.layout.item_dingdan_details, mDatas);
                        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
                        rvContent.setAdapter(adapter);


                       // Glide.with(BianMinDingDanXiangQingActivity.this).load(dataBean.getIndex_photo_url()).into(iv_img);

                        tvPayType.setText(dataBean.getCreate_time());
                        tvName.setText(dataBean.getReceiver_name());
                        tvPhone.setText(dataBean.getReceiver_phone());

                        tvAdress.setText(dataBean.getUser_addr_all());

                        tvBeizhu.setText("订单备注：" + dataBean.getShop_form_text());
                        // tv_youhui.setText("优惠券：");

                        tvBianhao.setText("订单编号：" + dataBean.getForm_no());
                        tvPayType.setText("支付方式：" + dataBean.getPay_name());
                        // tv_yunfei.setText("运费：" + dataBean.getForm_money_go());
                        tvPayTime.setText("交易时间：" + dataBean.getCreate_time());

//                        tv_money.setText("¥ " + dataBean.getPay_money());
//                        tv_taocan.setText(dataBean.getProduct_title() + "        X" + dataBean.getPay_count());
//                        tv_mai_name.setText("买家昵称：" + dataBean.getUser_name());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        //smartRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onStart(Request<AppResponse<OrderDetailsModel1.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog("");
                    }
                });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.layout_bianmindingdan_xiangqing;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String shopFromId) {
        Intent intent = new Intent();
        intent.setClass(context, BianMinDingDanXiangQingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("shopFromId", shopFromId);
        context.startActivity(intent);
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
}
