package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.JiesuanAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.JiesuanModel;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WodeJiesuanActivity extends BaseActivity {

    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private String shop_form_id;
    private List<JiesuanModel.DataBean> data = new ArrayList<>();
    private JiesuanAdapter jiesuanAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.act_mine_jiesuan;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("待结算");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WodeJiesuanActivity.class);
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
        init();
    }

    private void init() {
        initAdapter();
        initSM();

        getNet();
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
                getLoad();
            }
        });
    }

    private void initAdapter() {
        jiesuanAdapter = new JiesuanAdapter(R.layout.item_mine_jiesuan, data);
        rv_content.setLayoutManager(new LinearLayoutManager(this));
        rv_content.setAdapter(jiesuanAdapter);
    }

    private void getNet() {
        shop_form_id = "";

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04335);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<JiesuanModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<JiesuanModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<JiesuanModel.DataBean>> response) {
                        data = response.body().data;
                        jiesuanAdapter.setNewData(data);
                        jiesuanAdapter.notifyDataSetChanged();

                        if (data.size() > 0) {
                            shop_form_id = WodeJiesuanActivity.this.data.get(WodeJiesuanActivity.this.data.size() - 1).getShop_form_id();
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    private void getLoad() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04335);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<JiesuanModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<JiesuanModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<JiesuanModel.DataBean>> response) {
                        List<JiesuanModel.DataBean> dataNew = response.body().data;
                        data.addAll(dataNew);
                        jiesuanAdapter.setNewData(data);
                        jiesuanAdapter.notifyDataSetChanged();

                        if (data.size() > 0) {
                            shop_form_id = WodeJiesuanActivity.this.data.get(WodeJiesuanActivity.this.data.size() - 1).getShop_form_id();
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }
}
