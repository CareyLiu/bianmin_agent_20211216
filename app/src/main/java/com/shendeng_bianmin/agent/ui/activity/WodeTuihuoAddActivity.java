package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;

import butterknife.ButterKnife;

public class WodeTuihuoAddActivity extends BaseActivity {


    @Override
    public int getContentViewResId() {
        return R.layout.act_mine_tuihuo_add;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("新增退货地址");
        tv_rightTitle.setText("保存");
        tv_rightTitle.setVisibility(View.VISIBLE);
        tv_rightTitle.setTextSize(17);
        tv_rightTitle.setTextColor(this.getResources().getColor(R.color.text_red));
        tv_rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdress();
            }
        });
    }

    private void saveAdress() {

    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WodeTuihuoAddActivity.class);
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
        getNet();
    }


    private void getNet() {

    }
}
