package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TuanMingxiAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.tuangou.TuanMingxiModel;
import com.shendeng_bianmin.agent.util.TimeUtils;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TuanMingxiActivity extends BaseActivity {

    @BindView(R.id.tv_shouru)
    TextView tv_shouru;
    @BindView(R.id.tv_data)
    TextView tv_data;
    @BindView(R.id.rv_mingxi)
    RecyclerView rv_mingxi;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.ll_data)
    LinearLayout ll_data;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private String pay_cost_id;
    private TuanMingxiModel.DataBean mingxiModel;
    private List<TuanMingxiModel.DataBean.CunsumerListBean> cunsumerList = new ArrayList<>();
    private TuanMingxiAdapter adapter;
    private String create_time;
    private String detail_type;
    private TimePickerView timePicker;

    @Override
    public int getContentViewResId() {
        return R.layout.act_tuangou_mingxi;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String detail_type) {
        Intent intent = new Intent();
        intent.setClass(context, TuanMingxiActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("detail_type", detail_type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        detail_type = getIntent().getStringExtra("detail_type");
        create_time = TimeUtils.getCurrentTimeYue();
        tv_data.setText(create_time);

        if (detail_type.equals(AppCode.mingxi_maidan)) {
            tv_title.setText("买单明细");
            tv_title_name.setText("买单明细");
        } else {
            tv_title.setText("团购明细");
            tv_title_name.setText("团购明细");
        }

        initAdapter();
        initSM();
        getNet();
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04215);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("detail_type", detail_type);
        map.put("create_time", create_time);
        Gson gson = new Gson();
        OkGo.<AppResponse<TuanMingxiModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TuanMingxiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TuanMingxiModel.DataBean>> response) {
                        mingxiModel = response.body().data.get(0);
                        cunsumerList = mingxiModel.getCunsumerList();
                        if (cunsumerList.size() > 0) {
                            pay_cost_id = cunsumerList.get(cunsumerList.size() - 1).getPay_cost_id();
                            ll_no_data.setVisibility(View.GONE);
                        }else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                        adapter.setNewData(cunsumerList);
                        adapter.notifyDataSetChanged();

                        tv_shouru.setText(mingxiModel.getIncome());
                        tv_data.setText(mingxiModel.getMonth());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TuanMingxiModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();

                    }
                });
    }

    private void getMore() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04215);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("pay_cost_id", pay_cost_id);
        map.put("detail_type", detail_type);
        map.put("create_time", create_time);
        Gson gson = new Gson();
        OkGo.<AppResponse<TuanMingxiModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TuanMingxiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TuanMingxiModel.DataBean>> response) {
                        mingxiModel = response.body().data.get(0);
                        List<TuanMingxiModel.DataBean.CunsumerListBean> cunsumerListNew = mingxiModel.getCunsumerList();
                        cunsumerList.addAll(cunsumerListNew);
                        if (cunsumerList.size() > 0) {
                            pay_cost_id = cunsumerList.get(cunsumerList.size() - 1).getPay_cost_id();
                            ll_no_data.setVisibility(View.GONE);
                        }else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                        adapter.setNewData(cunsumerList);
                        adapter.notifyDataSetChanged();

                        tv_shouru.setText(mingxiModel.getIncome());
                        tv_data.setText(mingxiModel.getMonth());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        smartRefreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TuanMingxiModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    private void initAdapter() {
        adapter = new TuanMingxiAdapter(R.layout.item_tuangou_mingxi, cunsumerList);
        rv_mingxi.setLayoutManager(new LinearLayoutManager(mContext));
        rv_mingxi.setAdapter(adapter);
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getMore();
            }
        });
    }

    @OnClick(R.id.ll_data)
    public void onViewClicked() {
        if (timePicker == null) {
            boolean[] time = {true, true, false, false, false, false};
            //时间选择器
            timePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    create_time = TimeUtils.getData(date, "yyyy-MM");
                    tv_data.setText(create_time);
                    getNet();
                }
            }).setType(time).build();
        }
        timePicker.show();
    }
}
