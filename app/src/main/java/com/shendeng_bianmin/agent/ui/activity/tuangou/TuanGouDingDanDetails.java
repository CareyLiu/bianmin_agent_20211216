package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shendeng_bianmin.agent.model.tuangou.TOrderDetailsModel;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShowActivity;
import com.shendeng_bianmin.agent.ui.view.tuangou.TuanRuleView;
import com.shendeng_bianmin.agent.ui.view.tuangou.TuantaocanView;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class TuanGouDingDanDetails extends BaseActivity {

    @BindView(R.id.tv_dingdanzhuangtai)
    TextView tv_dingdanzhuangtai;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srL_smart;
    @BindView(R.id.ll_taocan_neirong)
    LinearLayout ll_taocan_neirong;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.ll_shiyongguize)
    LinearLayout ll_shiyongguize;
    @BindView(R.id.bt_anniu)
    TextView bt_anniu;

    private String shop_form_id;
    private TOrderDetailsModel.DataBean tuanModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shop_form_id = getIntent().getStringExtra("shop_form_id");
        initSM();
        getNet();
    }

    private void initSM() {
        srL_smart.setEnableLoadMore(false);
        srL_smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04201);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<TOrderDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TOrderDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TOrderDetailsModel.DataBean>> response) {
                        tuanModel = response.body().data.get(0);
                        String shop_pay_check_name = tuanModel.getShop_pay_check_name();
                        if (shop_pay_check_name.equals("已完成")) {
                            tv_dingdanzhuangtai.setTextColor(Color.parseColor("#15D78E"));
                        }

                        if (shop_pay_check_name.equals("待付款")) {
                            bt_anniu.setVisibility(View.VISIBLE);
                        } else {
                            bt_anniu.setVisibility(View.GONE);
                        }

                        tv_dingdanzhuangtai.setText(shop_pay_check_name);
                        tv_title_name.setText(tuanModel.getShop_product_title());
                        tv_title.setText(tuanModel.getShop_product_title());
                        Glide.with(mContext).load(tuanModel.getIndex_photo_url()).into(iv_image);

                        List<TOrderDetailsModel.DataBean.TaocanListBean> taocan_list = tuanModel.getTaocan_list();
                        ll_taocan_neirong.removeAllViews();
                        for (int i = 0; i < taocan_list.size(); i++) {
                            TuantaocanView tuantaocanView = new TuantaocanView(mContext);
                            tuantaocanView.setModel(taocan_list.get(i));
                            ll_taocan_neirong.addView(tuantaocanView);
                        }

                        List<TOrderDetailsModel.DataBean.RulesListBean> rules_list = tuanModel.getRules_list();
                        ll_shiyongguize.removeAllViews();
                        for (int i = 0; i < rules_list.size(); i++) {
                            TuanRuleView tuantaocanView = new TuanRuleView(mContext);
                            tuantaocanView.setModel(rules_list.get(i));
                            ll_shiyongguize.addView(tuantaocanView);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        srL_smart.finishRefresh();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TOrderDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_tuan_gou_ding_dan_details;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("订单详情");
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    public boolean showToolBarLine() {
        return true;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String shop_form_id) {
        Intent intent = new Intent(context, TuanGouDingDanDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("shop_form_id", shop_form_id);
        context.startActivity(intent);
    }


    @OnClick({R.id.iv_image, R.id.bt_anniu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_image:
                ArrayList<String> imagePath = new ArrayList<String>();
                imagePath.add(tuanModel.getIndex_photo_url());
                ImageShowActivity.actionStart(mContext, imagePath);
                break;
            case R.id.bt_anniu:
                clicktui();
                break;
        }
    }

    private void clicktui() {
        Y.t("代付款");
    }
}
