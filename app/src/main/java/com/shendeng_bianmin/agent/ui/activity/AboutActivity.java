package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_banben_hao)
    TextView tvBanbenHao;
    @BindView(R.id.tv_yinsi)
    TextView tvYinsi;
    @BindView(R.id.tv_yonghushiyong)
    TextView tvYonghushiyong;

    @Override
    public int getContentViewResId() {
        return R.layout.act_about_us;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("关于我们");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AboutActivity.class);
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
        tvBanbenHao.setText("版本号: v" + getAppVersionName(this));
    }

    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.shendeng_bianmin.agent", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @OnClick({R.id.tv_yinsi, R.id.tv_yonghushiyong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yinsi:
                DefaultX5WebViewActivity.actionStart(this, "https://shop.hljsdkj.com/shop_new/privacy_clause");
                break;
            case R.id.tv_yonghushiyong:
                DefaultX5WebViewActivity.actionStart(this, "https://shop.hljsdkj.com/shop_new/user_agreement");
                break;
        }
    }
}
