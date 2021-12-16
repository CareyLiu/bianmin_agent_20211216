package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.AppConfig;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.ui.HomeBasicActivity;
import com.shendeng_bianmin.agent.ui.HomeBasicTuanGouActivity;
import com.shendeng_bianmin.agent.util.UIHelper;

import butterknife.BindView;

public class ChooseRoleActivity extends BaseActivity {
    @BindView(R.id.view_view1)
    View viewView1;
    @BindView(R.id.view_view2)
    View viewView2;
    @BindView(R.id.ll_denglufangshi)
    LinearLayout llDenglufangshi;
    @BindView(R.id.iv_tuangou_shangjia)
    ImageView ivTuangouShangjia;
    @BindView(R.id.cl_tuangoushangjia)
    ConstraintLayout clTuangoushangjia;
    @BindView(R.id.iv_dianpu_shangjia)
    ImageView ivDianpuShangjia;
    @BindView(R.id.cl_dianpu_shangjia)
    ConstraintLayout clDianpuShangjia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clDianpuShangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(mContext, "店铺");

                startActivity(new Intent(mContext, HomeBasicActivity.class));
                PreferenceHelper.getInstance(mContext).putString(AppConfig.ROLE, String.valueOf("1"));
            }
        });

        clTuangoushangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clTuangoushangjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeBasicTuanGouActivity.actionStart(mContext);
                        PreferenceHelper.getInstance(mContext).putString(AppConfig.ROLE, String.valueOf("2"));
                    }
                });
            }
        });





    }

    @Override
    public int getContentViewResId() {
        return R.layout.layout_choose_role;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseRoleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
