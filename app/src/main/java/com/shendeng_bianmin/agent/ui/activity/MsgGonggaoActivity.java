package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MsgGonggaoActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private String appCode;

    @Override
    public int getContentViewResId() {
        return R.layout.act_msg_im;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("聚易佳公告");
    }


    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String appCode) {
        Intent intent = new Intent();
        intent.setClass(context, MsgGonggaoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("appCode", appCode);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        appCode = getIntent().getStringExtra("appCode");
    }
}
