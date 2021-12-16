package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WodeTuihuoActivity extends BaseActivity {

    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public int getContentViewResId() {
        return R.layout.act_mine_tuihuo;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("退货地址设置");
        tv_rightTitle.setText("添加新地址");
        tv_rightTitle.setVisibility(View.VISIBLE);
        tv_rightTitle.setTextSize(17);
        tv_rightTitle.setTextColor(this.getResources().getColor(R.color.text_red));
        tv_rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WodeTuihuoAddActivity.actionStart(WodeTuihuoActivity.this);
            }
        });
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WodeTuihuoActivity.class);
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

    private void initAdapter() {
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

    private void getNet() {

    }

    private void getLoad() {

    }
}
