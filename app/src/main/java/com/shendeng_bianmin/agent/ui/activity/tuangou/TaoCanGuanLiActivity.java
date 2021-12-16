package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.util.UIHelper;

import butterknife.BindView;

public class TaoCanGuanLiActivity extends BaseActivity {

    @BindView(R.id.iv_tianjia)
    ImageView ivTianjia;
    @BindView(R.id.tv_fenlei_2)
    TextView tvFenlei2;
    @BindView(R.id.ll_taocan)
    LinearLayout llTaocan;
    @BindView(R.id.iv_taocan_tianjia)
    ImageView ivTaocanTianjia;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    @BindView(R.id.rl_tianjia)
    RelativeLayout rlTianjia;
    @BindView(R.id.nested)
    NestedScrollView nested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        srLSmart.setEnableLoadMore(false);
        rlTianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(TaoCanGuanLiActivity.this, R.layout.item_taocan_tiaomu, null);

                ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);

                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIHelper.ToastMessage(mContext, "点击了");

                    }
                });
                llTaocan.addView(view);


                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        nested.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });

    }


    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("套餐管理");
    }

    @Override
    public boolean showToolBarLine() {
        return true;
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context, String waresId) {
        Intent intent = new Intent(context, TaoCanGuanLiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("waresId", waresId);
        context.startActivity(intent);
    }
}
