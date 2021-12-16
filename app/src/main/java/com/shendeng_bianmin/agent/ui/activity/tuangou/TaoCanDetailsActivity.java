package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

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
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_CaoZuo_Base;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Delete;
import com.shendeng_bianmin.agent.model.TaoCanDetailsModel;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShowActivity;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TaoCanDetailsActivity extends BaseActivity {

    @BindView(R.id.ll_leimu)
    LinearLayout llLeimu;
    @BindView(R.id.ll_guize)
    LinearLayout llGuize;
    @BindView(R.id.nested)
    NestedScrollView nested;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    @BindView(R.id.tv_zhuangtai)
    TextView tvZhuangtai;
    @BindView(R.id.tv_bianji)
    TextView tvBianji;
    @BindView(R.id.tv_shangjia)
    TextView tvShangjia;
    @BindView(R.id.tv_shanchu)
    TextView tvShanchu;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_taocanmingcheng)
    TextView tvTaocanmingcheng;
    @BindView(R.id.tv_leimu)
    TextView tvLeimu;
    @BindView(R.id.ll_choose_leimu)
    LinearLayout llChooseLeimu;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_taocan_ming_huashu)
    TextView tvTaocanMingHuashu;
    @BindView(R.id.rl_none)
    RelativeLayout rlNone;
    @BindView(R.id.iv_nopic)
    ImageView ivNopic;
    private String waresId;


    Response<AppResponse<TaoCanDetailsModel.DataBean>> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        waresId = getIntent().getStringExtra("wares_id");
        getDetailsNet();

        srLSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDetailsNet();
            }
        });
        srLSmart.setEnableLoadMore(false);

        tvBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response.body().data.get(0).getWares_state().equals("1")) {
                    //shangJiaTaoCan("1");
                    MyCarCaoZuoDialog_CaoZuo_Base myCarCaoZuoDialog_caoZuo_base = new MyCarCaoZuoDialog_CaoZuo_Base(mContext, "提示", "上架销售的商品不能直接修改，请您先下架商品，完成修改操作", new MyCarCaoZuoDialog_CaoZuo_Base.OnDialogItemClickListener() {
                        @Override
                        public void clickLeft() {

                        }

                        @Override
                        public void clickRight() {

                        }
                    });
                    myCarCaoZuoDialog_caoZuo_base.show();
                    myCarCaoZuoDialog_caoZuo_base.setCenterVisible();

                } else {
                    //shangJiaTaoCan("2");
                    TianJiaTaoCanActivity.actionStart(mContext, "1", "0", waresId);
                }


            }
        });

        tvShangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response.body().data.get(0).getWares_state().equals("1")) {
                    shangJiaTaoCan("1");
                } else {
                    shangJiaTaoCan("2");
                }

            }
        });
        tvShanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UIHelper.ToastMessage(mContext, "删除");

                MyCarCaoZuoDialog_Delete myCarCaoZuoDialog_delete = new MyCarCaoZuoDialog_Delete((TaoCanDetailsActivity) mContext, new MyCarCaoZuoDialog_Delete.OnDialogItemClickListener() {
                    @Override
                    public void clickLeft() {

                    }

                    @Override
                    public void clickRight() {
                        shanchuTaoCan();
                    }
                });

                myCarCaoZuoDialog_delete.show();

            }
        });
    }

    private void initBanner() {//初始化banner
        List<TaoCanDetailsModel.DataBean.ImgListBean> img_list = response.body().data.get(0).getImg_list();

        if (img_list.size() == 0) {
            rlNone.setVisibility(View.VISIBLE);
            banner.setVisibility(View.GONE);
        } else {
            rlNone.setVisibility(View.GONE);
            banner.setVisibility(View.VISIBLE);

        }
        if (img_list != null && img_list.size() > 0) {
            MyImageLoader mMyImageLoader = new MyImageLoader();
            banner.setImageLoader(mMyImageLoader);
            //  initMagicIndicator1(tagList);
            ArrayList<String> imagePath = new ArrayList<String>();
            for (int i = 0; i < img_list.size(); i++) {
                imagePath.add(img_list.get(i).getImg_url());
            }
            banner.setImages(imagePath);
            //设置图片加载地址

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    ImageShowActivity.actionStart(mContext, imagePath);
                }
            });
            banner.start();
        } else {

        }
    }


    /**
     * 图片加载类
     */
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_tao_can_details;
    }


    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context leixing 0新建进入 1 二次进入
     *                bianjiOrTianjia 0编辑  1添加
     */
    public static void actionStart(Context context, String waresId) {
        Intent intent = new Intent(context, TaoCanDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", waresId);
        context.startActivity(intent);
    }


    private void getDetailsNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04203);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", waresId);

        Gson gson = new Gson();
        OkGo.<AppResponse<TaoCanDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TaoCanDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TaoCanDetailsModel.DataBean>> response) {
                        TaoCanDetailsActivity.this.response = response;
                        if (response.body().data.get(0).getItem_name() == null) {

                            llLeimu.setVisibility(View.GONE);
                        } else {

                            tvLeimu.setText(response.body().data.get(0).getItem_name());
                        }
                        srLSmart.finishRefresh();
                        if (response.body().data.get(0).getWares_state().equals("1")) {//1上架 2下架
                            tvZhuangtai.setText("已上架");
                        } else {
                            tvZhuangtai.setText("已下架");
                        }

                        tvTaocanmingcheng.setText(response.body().data.get(0).getWares_name());
//                        etTaocanmingcheng.setText(response.body().data.get(0).getWares_name());
//                        etTaocanyuanjia.setText(response.body().data.get(0).getShop_money_old());
//                        etTaocanxianjia.setText(response.body().data.get(0).getShop_money_now());
//                        etTaocanjianjie.setText(response.body().data.get(0).getShop_product_title());


                        llGuize.removeAllViews();
                        if (response.body().data.get(0).getRules_list().size() != 0) {
                            for (int i = 0; i < response.body().data.get(0).getRules_list().size(); i++) {
                                View view = View.inflate(mContext, R.layout.item_taocan_guize1, null);
                                ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);
                                TextView tvTaoCanMing = view.findViewById(R.id.tv_taocan_ming);
                                View viewLine = view.findViewById(R.id.view);
                                if (i == 0) {
                                    viewLine.setVisibility(View.GONE);
                                }
                                tvTaoCanMing.setText(response.body().data.get(0).getRules_list().get(i).getPrompt_text());

                                constraintLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //  TianJiaGuiZeActivity.actionStart(mContext, "1", waresId, response.body().data.get(0).getRules_list().get(finalI).getPrompt_detail_id(), response.body().data.get(0).getRules_list().get(finalI).getPrompt_text());

                                    }
                                });
                                llGuize.addView(view);


//                                Handler handler = new Handler();
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        nested.fullScroll(ScrollView.FOCUS_DOWN);
//                                    }
//                                });
                            }

                        } else {
                            View view = View.inflate(mContext, R.layout.item_taocan_guize1, null);
                            TextView tvTaoCanMing = view.findViewById(R.id.tv_taocan_ming);
                            tvTaoCanMing.setText("暂无数据");
                            llGuize.addView(view);
                        }
                        llLeimu.removeAllViews();

                        if (response.body().data.get(0).getTaocan_list().size() != 0) {
                            for (int i = 0; i < response.body().data.get(0).getTaocan_list().size(); i++) {
                                View view = View.inflate(mContext, R.layout.item_taocan_tiaomu1, null);
                                ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);
                                TextView tvTaoCanMing = view.findViewById(R.id.tv_taocan_ming);
                                TextView tvTaoCanJiaGe = view.findViewById(R.id.tv_jiage);
                                TextView tvNumber = view.findViewById(R.id.tv_number);
                                View viewLine = view.findViewById(R.id.view);
                                if (i == 0) {
                                    viewLine.setVisibility(View.GONE);
                                }
                                tvTaoCanMing.setText(response.body().data.get(0).getTaocan_list().get(i).getMenu_text());
                                tvTaoCanJiaGe.setText("¥" + response.body().data.get(0).getTaocan_list().get(i).getMenu_pay());
                                tvNumber.setText("(" + response.body().data.get(0).getTaocan_list().get(i).getMenu_count() + "份)");

                                int finalI = i;
                                constraintLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                    TaoCanDetailsModel.DataBean.TaocanListBean bean = response.body().data.get(0).getTaocan_list().get(finalI);
//                                    //UIHelper.ToastMessage(mContext, "去编辑套餐");
//                                    AddTaoCanActivity.actionStart(mContext, "1", waresId, bean.getMenu_detail_id(), bean.getMenu_text(), bean.getMenu_count(), bean.getMenu_pay());

                                    }
                                });
                                llLeimu.addView(view);

                            }
                        } else {

                            View view = View.inflate(mContext, R.layout.item_taocan_tiaomu1, null);

                            ConstraintLayout constraintLayout = view.findViewById(R.id.constrain1);
                            constraintLayout.setVisibility(View.VISIBLE);

                            ConstraintLayout constraintLayout1 = view.findViewById(R.id.constrain);
                            constraintLayout1.setVisibility(View.GONE);
                            llLeimu.addView(view);
                        }


//                        llTaocantupian.removeAllViews();
//                        for (int i = 0; i < response.body().data.get(0).getImg_list().size(); i++) {
//                            View view = View.inflate(mContext, R.layout.item_taocantupian, null);
//                            ImageView ivTaoCanTuPian = view.findViewById(R.id.iv_taocan_tupian);
//                            TaoCanDetailsModel.DataBean.ImgListBean bean = response.body().data.get(0).getImg_list().get(i);
//                            Glide.with(mContext).load(bean.getImg_url()).into(ivTaoCanTuPian);
//                            llTaocantupian.addView(view);
//                        }
//
//                        tvTupNum.setText(response.body().data.get(0).getImg_list().size() + "张");

                        initBanner();
                        if (response.body().data.get(0).getWares_state().equals("1")) {//上架

                            //执行下架操作

                            tvShangjia.setText("下架");
                        } else {

                            //执行上架操作
                            tvShangjia.setText("上架");
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TaoCanDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    @Override
    public boolean showToolBarLine() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("套餐详情");
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    private void shangJiaTaoCan(String shangxiajia) {

        //下架传1 上架传2
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04204);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("dif_type", "1");
        map.put("type", shangxiajia);
        map.put("wares_id", waresId);


        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        //下架传1 上架传2
                        if (shangxiajia.equals("1")) {
                            UIHelper.ToastMessage(mContext, "下架成功");
                        } else {
                            UIHelper.ToastMessage(mContext, "上架成功");
                        }

                        finish();

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

    private void shanchuTaoCan() {


        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04204);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("dif_type", "1");
        map.put("type", "3");
        map.put("wares_id", waresId);


        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        UIHelper.ToastMessage(mContext, "删除成功");
                        finish();

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

}
