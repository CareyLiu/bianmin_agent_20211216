package com.shendeng_bianmin.agent.ui.fragment.mendian;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TaoCanGuanLiAdapter;
import com.shendeng_bianmin.agent.adapter.TuPianCaiDanAdapter;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.MenDianGuanLiModel;
import com.shendeng_bianmin.agent.ui.activity.MenDianHeiSeActivity;
import com.shendeng_bianmin.agent.ui.activity.MenDianXinXiActivity;
import com.shendeng_bianmin.agent.ui.activity.TuPianCaiDanActivity;
import com.shendeng_bianmin.agent.ui.activity.tuangou.TaoCanGuanLi_HomeActivity;
import com.shendeng_bianmin.agent.util.GlideShowImageUtils;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MenDianFragment1 extends BaseFragment {
    @BindView(R.id.tv_mendian_tupian)
    TextView tvMendianTupian;
    @BindView(R.id.tv_mendianxinxi)
    TextView tvMendianxinxi;
    @BindView(R.id.tv_jichuxinxi)
    TextView tvJichuxinxi;
    @BindView(R.id.iv_back_bianji)
    ImageView ivBackBianji;
    @BindView(R.id.tv_huashu)
    TextView tvHuashu;
    @BindView(R.id.tv_kehufenlei)
    TextView tvKehufenlei;
    @BindView(R.id.tv_yingye_zhuagntai)
    TextView tvYingyeZhuagntai;
    @BindView(R.id.tv_yingyeshijian)
    TextView tvYingyeshijian;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.view_jichu_info)
    View viewJichuInfo;
    @BindView(R.id.tv_qitainfo)
    TextView tvQitainfo;
    @BindView(R.id.iv_back_bianji_1)
    ImageView ivBackBianji1;
    @BindView(R.id.tv_rongnarenshu)
    TextView tvRongnarenshu;
    @BindView(R.id.tv_mendian_mianji)
    TextView tvMendianMianji;
    @BindView(R.id.tv_baoxiangxinxi)
    TextView tvBaoxiangxinxi;
    @BindView(R.id.tv_mendiancaidan)
    TextView tvMendiancaidan;
    @BindView(R.id.tv_taocanguanli)
    TextView tvTaocanguanli;
    @BindView(R.id.iv_back_bianji_2)
    ImageView ivBackBianji2;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_tupian_caidan)
    TextView tvTupianCaidan;
    @BindView(R.id.iv_back_bianji_3)
    ImageView ivBackBianji3;
    @BindView(R.id.rlv_tupiancaidan)
    RecyclerView rlvTupiancaidan;
    @BindView(R.id.cl_mendiancaidan)
    ConstraintLayout clMendiancaidan;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;

    TaoCanGuanLiAdapter taoCanGuanLiAdapter;

    @BindView(R.id.taocan_bianji)
    TextView taocanBianji;

    List<MenDianGuanLiModel.DataBean.TaoCanListBean> mDatas;
    @BindView(R.id.cl_mendian_tupian)
    ConstraintLayout clMendianTupian;
    @BindView(R.id.iv_mendian_tupian)
    ImageView ivMendianTupian;
    @BindView(R.id.ll_mendian_xinxi_bianji)
    LinearLayout llMendianXinxiBianji;
    @BindView(R.id.tv_shanghufenlei)
    TextView tvShanghufenlei;
    @BindView(R.id.tv_yingyezhuagntai)
    TextView tvYingyezhuagntai;
    @BindView(R.id.tv_rongnarenshu_1)
    TextView tvRongnarenshu1;
    @BindView(R.id.tv_mendianmianji_1)
    TextView tvMendianmianji1;
    @BindView(R.id.tv_baoxiangxinxi_1)
    TextView tvBaoxiangxinxi1;
    @BindView(R.id.tv_tupian_caidan_bianji)
    TextView tvTupianCaidanBianji;
    private TuPianCaiDanAdapter tuPianCaiDanAdapter;
    List<MenDianGuanLiModel.DataBean.LunBoListBean> tuPianCaiDanList;

    @Override
    protected void initLogic() {
        srLSmart.setEnableLoadMore(false);
        mDatas = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvList.setLayoutManager(linearLayoutManager);
        taoCanGuanLiAdapter = new TaoCanGuanLiAdapter(R.layout.layout_caidan_guanli, mDatas);
        rlvList.setAdapter(taoCanGuanLiAdapter);

        srLSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
//        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(getActivity(), 3);
//        rlvTupiancaidan.setLayoutManager(linearLayoutManager1);
//        taoCanGuanLiAdapter = new TaoCanGuanLiAdapter(R.layout.item_caidan_list, mDatas);
//        rlvTupiancaidan.setAdapter(taoCanGuanLiAdapter);

        llMendianXinxiBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenDianXinXiActivity.actionStart(getActivity());
            }
        });

        taocanBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TaoCanGuanLiActivity.actionStart(getActivity());
                //AddTaoCanActivity.actionStart(getActivity(),"1");
                TaoCanGuanLi_HomeActivity.actionStart(getActivity());
            }
        });

        clMendianTupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenDianHeiSeActivity.actionStart(getActivity(), response_1.body().data.get(0).getInst_photo_url());
            }
        });
        getNet();

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(getActivity(), 3);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rlvTupiancaidan.setLayoutManager(linearLayoutManager1);
        tuPianCaiDanList = new ArrayList<>();
        tuPianCaiDanAdapter = new TuPianCaiDanAdapter(R.layout.item_taocantupian, tuPianCaiDanList);
        rlvTupiancaidan.setAdapter(tuPianCaiDanAdapter);


        tvTupianCaidanBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuPianCaiDanActivity.actionStart(getActivity(), tuPianCaiDanList);
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.i("isVisibleToUser", "true");
            //  getNet();
        } else {
            Log.i("isVisibleToUser", "false");
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_mendian_fragment1;
    }

    @Override
    protected void initView(View rootView) {

    }

    private Response<AppResponse<MenDianGuanLiModel.DataBean>> response_1;

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04214);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<MenDianGuanLiModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MenDianGuanLiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MenDianGuanLiModel.DataBean>> response) {
                        srLSmart.finishRefresh();
                        tvShanghufenlei.setText(response.body().data.get(0).getMerchant_type_name());

//                        if (response.body().data.get(0).getBusiness_status().equals("1")) {
//                            tvYingyezhuagntai.setText("门店开启");
//                        } else {
//                            tvYingyezhuagntai.setText("门店关闭");
//                        }
                        tvYingyezhuagntai.setText(response.body().data.get(0).getShop_state_name());
                        tvData.setText(response.body().data.get(0).getInst_hours_begin() + "-" + response.body().data.get(0).getInst_hours_end());


                        tvRongnarenshu1.setText(response.body().data.get(0).getShop_capacity());
                        tvMendianmianji1.setText(response.body().data.get(0).getShop_area());


                        tvBaoxiangxinxi1.setText(response.body().data.get(0).getIs_box_name());

//                        if (response.body().data.get(0).getIs_box().equals("1")) {
//                            tvBaoxiangxinxi1.setText("有包厢");
//                        } else {
//                            tvBaoxiangxinxi1.setText("无包厢");
//                        }

                        response_1 = response;
                        Glide.with(getActivity()).applyDefaultRequestOptions(GlideShowImageUtils.showZhengFangXing()).load(response.body().data.get(0).getInst_photo_url()).into(ivMendianTupian);
                        mDatas.clear();
                        mDatas.addAll(response.body().data.get(0).getTao_can_list());

                        if (mDatas.size() == 0) {
                            View view = View.inflate(getActivity(), R.layout.layout_taocan_empty, null);
                            taoCanGuanLiAdapter.setEmptyView(view);

                        } else {
                            taoCanGuanLiAdapter.setNewData(mDatas);
                            taoCanGuanLiAdapter.notifyDataSetChanged();
                        }


                        if (response.body().data.get(0).getLun_bo_list().size() == 0) {
                            View view = View.inflate(getActivity(), R.layout.layout_tupiancaidan_empty, null);
                            tuPianCaiDanAdapter.setEmptyView(view);
                        } else {
                            tuPianCaiDanList.clear();
                            tuPianCaiDanList.addAll(response.body().data.get(0).getLun_bo_list());
                            tuPianCaiDanAdapter.setNewData(tuPianCaiDanList);
                            tuPianCaiDanAdapter.notifyDataSetChanged();
                        }

                        tvTupianCaidan.setText("图片菜单(" + response.body().data.get(0).getLun_bo_list().size() + ")");
                    }

                    @Override
                    public void onStart(Request<AppResponse<MenDianGuanLiModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }

}
