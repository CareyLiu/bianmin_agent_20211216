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
import com.shendeng_bianmin.agent.adapter.MsgOrderAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.MessageModel;
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

public class MsgOrderActivity extends BaseActivity {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    private MsgOrderAdapter orderAdapter;
    private String appCode;
    private String notifyId;
    private List<MessageModel.DataBean> models = new ArrayList<>();

    @Override
    public int getContentViewResId() {
        return R.layout.act_msg_order;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("订单消息");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String appCode) {
        Intent intent = new Intent();
        intent.setClass(context, MsgOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("appCode", appCode);
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
        appCode = getIntent().getStringExtra("appCode");
        initAdapter();
        initSM();
        getNet();
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(false);
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

    private void initAdapter() {
        orderAdapter = new MsgOrderAdapter(R.layout.item_msg_order, models);
        rv_content.setLayoutManager(new LinearLayoutManager(mContext));
        rv_content.setAdapter(orderAdapter);
    }

    private void getNet() {
        notifyId = "";
        Map<String, String> map = new HashMap<>();
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        if (appCode.equals(AppCode.msg_maijia)) {
            map.put("code", Urls.code_04341);
        } else {
            map.put("code", Urls.code_04218);
        }
        map.put("type", "2");
        Gson gson = new Gson();
        OkGo.<AppResponse<MessageModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MessageModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MessageModel.DataBean>> response) {
                        models = response.body().data;
                        if (models != null && models.size() > 0) {
                            notifyId = models.get(models.size() - 1).getNotify_id();
                            ll_no_data.setVisibility(View.GONE);
                        }else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        orderAdapter.setNewData(models);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }


    private void getMore() {
        notifyId = "";
        Map<String, String> map = new HashMap<>();
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        if (appCode.equals(AppCode.msg_maijia)) {
            map.put("code", Urls.code_04341);
        } else {
            map.put("code", Urls.code_04218);
        }
        map.put("type", "2");
        map.put("notify_id", notifyId);
        Gson gson = new Gson();
        OkGo.<AppResponse<MessageModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MessageModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MessageModel.DataBean>> response) {
                        List<MessageModel.DataBean> data = response.body().data;
                        models.addAll(data);
                        if (models != null && models.size() > 0) {
                            notifyId = models.get(models.size() - 1).getNotify_id();
                            ll_no_data.setVisibility(View.GONE);
                        }else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        orderAdapter.setNewData(models);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }
}
