package com.shendeng_bianmin.agent.ui.fragment.tuangou_base;


import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.tuangou.TuanMainModel;
import com.shendeng_bianmin.agent.ui.activity.MaiDanShouKuanActivity;
import com.shendeng_bianmin.agent.ui.activity.OrderSaoyisaoActivity;
import com.shendeng_bianmin.agent.ui.activity.tuangou.HandAddActivity;
import com.shendeng_bianmin.agent.ui.activity.tuangou.TuanGouDingDanGuanliActivity;
import com.shendeng_bianmin.agent.ui.activity.tuangou.TuanMingxiActivity;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BottomTuanGouShouYeFragment extends BaseFragment {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.view_title_line)
    View viewTitleLine;
    @BindView(R.id.iv_shumayanzheng)
    ImageView ivShumayanzheng;
    @BindView(R.id.cl_shumayanzheng)
    ConstraintLayout clShumayanzheng;
    @BindView(R.id.iv_saomayanzheng)
    ImageView ivSaomayanzheng;
    @BindView(R.id.cl_saomayanzheng)
    ConstraintLayout clSaomayanzheng;
    @BindView(R.id.iv_maidanshoukuan)
    ImageView ivMaidanshoukuan;
    @BindView(R.id.cl_maidanshoukuan)
    ConstraintLayout clMaidanshoukuan;
    @BindView(R.id.iv_dingdanguanli)
    ImageView ivDingdanguanli;
    @BindView(R.id.cl_dingdanguanli)
    ConstraintLayout clDingdanguanli;
    @BindView(R.id.ll_dingbu)
    LinearLayout llDingbu;
    @BindView(R.id.tv_jinqitian)
    TextView tvJinqitian;
    @BindView(R.id.view_jinqitian_line)
    View viewJinqitianLine;
    @BindView(R.id.tv_zuotian)
    TextView tvZuotian;
    @BindView(R.id.view_zuotian_line)
    View viewZuotianLine;
    @BindView(R.id.tv_jintian)
    TextView tvJintian;
    @BindView(R.id.view_jintian_line)
    View viewJintianLine;
    @BindView(R.id.cl_mendianshuju)
    ConstraintLayout clMendianshuju;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_yingshoujine)
    TextView tvYingshoujine;
    @BindView(R.id.cl_mendian)
    ConstraintLayout clMendian;
    @BindView(R.id.tv_tuangou)
    TextView tvTuangou;
    @BindView(R.id.tv_dingdanshu)
    TextView tvDingdanshu;
    @BindView(R.id.iv_icon_right)
    ImageView ivIconRight;
    @BindView(R.id.tv_yingshoujine_huashu)
    TextView tvYingshoujineHuashu;
    @BindView(R.id.cl_yingshoujine)
    ConstraintLayout clYingshoujine;
    @BindView(R.id.tv_shishoujine)
    TextView tvShishoujine;
    @BindView(R.id.cl_tuangou)
    ConstraintLayout clTuangou;
    @BindView(R.id.tv_maidan)
    TextView tvMaidan;
    @BindView(R.id.tv_maidan_dingdanshu)
    TextView tvMaidanDingdanshu;
    @BindView(R.id.iv_icon_right_1)
    ImageView ivIconRight1;
    @BindView(R.id.tv_maidan_yingshoujine_huashu)
    TextView tvMaidanYingshoujineHuashu;
    @BindView(R.id.cl_maidan_yingshoujine)
    ConstraintLayout clMaidanYingshoujine;
    @BindView(R.id.tv_maidan_shishoujine)
    TextView tvMaidanShishoujine;
    boolean jintian;//今天
    boolean zuotian;//昨天
    boolean jinqitian;//近七天
    private final CompositeDisposable disposables = new CompositeDisposable();
    @BindView(R.id.rl_shumayanzheng)
    RelativeLayout rlShumayanzheng;
    @BindView(R.id.cl_maidan)
    ConstraintLayout clMaidan;
    @BindView(R.id.rl_saomayanzheng)
    RelativeLayout rlSaomayanzheng;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    @BindView(R.id.rl_dingdan_guanli)
    RelativeLayout rlDingdanGuanli;
    @BindView(R.id.rl_maidanshoukuan)
    RelativeLayout rlMaidanshoukuan;
    @BindView(R.id.view)
    View view;

    private String date_type = "1";
    private TuanMainModel.DataBean homeModel;


    public static BottomTuanGouShouYeFragment newInstance() {
        Bundle args = new Bundle();
        BottomTuanGouShouYeFragment fragment = new BottomTuanGouShouYeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_tuangou_shouye;
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initLogic() {
        initHuidiao();
        getNet();
        initSM();


        tvJinqitian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = "3";
                viewJinqitianLine.setVisibility(View.VISIBLE);
                viewJintianLine.setVisibility(View.GONE);
                viewZuotianLine.setVisibility(View.GONE);
                getNet();
            }
        });

        tvJintian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = "1";
                viewJinqitianLine.setVisibility(View.GONE);
                viewJintianLine.setVisibility(View.VISIBLE);
                viewZuotianLine.setVisibility(View.GONE);
                getNet();
            }
        });


        tvZuotian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = "2";
                viewJinqitianLine.setVisibility(View.GONE);
                viewJintianLine.setVisibility(View.GONE);
                viewZuotianLine.setVisibility(View.VISIBLE);
                getNet();
            }
        });

        rlShumayanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandAddActivity.actionStart(getActivity());
            }
        });

        clTuangou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanMingxiActivity.actionStart(getContext(), AppCode.mingxi_tuangou);
            }
        });

        clMaidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanMingxiActivity.actionStart(getContext(), AppCode.mingxi_maidan);
            }
        });

        rlSaomayanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ewm();
            }
        });
        rlDingdanGuanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanGouDingDanGuanliActivity.actionStart(getActivity());
            }
        });
        rlMaidanshoukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaiDanShouKuanActivity.actionStart(getActivity());
            }
        });
    }

    private void initSM() {
        srLSmart.setEnableLoadMore(false);
        srLSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {

            }
        }));
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04210);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("date_type", date_type);
        Gson gson = new Gson();
        OkGo.<AppResponse<TuanMainModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TuanMainModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TuanMainModel.DataBean>> response) {
                        homeModel = response.body().data.get(0);

                        tvMoney.setText(homeModel.getReceivable());
                        tvYingshoujine.setText(homeModel.getActual_use());

                        tvDingdanshu.setText("订单数  " + homeModel.getP_order_count());
                        tvMaidanDingdanshu.setText("订单数  " + homeModel.getB_order_count());

                        tvShishoujine.setText(homeModel.getP_actual_use());
                        tvYingshoujineHuashu.setText(homeModel.getP_receivable());

                        tvMaidanShishoujine.setText(homeModel.getB_actual_use());
                        tvMaidanYingshoujineHuashu.setText(homeModel.getB_receivable());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        srLSmart.finishRefresh();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void ewm() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) { // 在android 6.0之前会默认返回true
                    OrderSaoyisaoActivity.actionStartForResult(getActivity(), 100);
                } else {
                    Y.tLong("该应用需要赋予访问相机的权限，不开启将无法正常工作！");
                }
            }
        });
    }

    @Override
    protected void immersionInit(ImmersionBar mImmersionBar) {
        mImmersionBar
                .titleBar(toolbar).fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    protected boolean immersionEnabled() {
        return true;
    }
}
