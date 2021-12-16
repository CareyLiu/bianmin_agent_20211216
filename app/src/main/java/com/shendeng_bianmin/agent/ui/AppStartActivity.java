package com.shendeng_bianmin.agent.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.AppConfig;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.ui.activity.LoginActivity;
import com.shendeng_bianmin.agent.ui.widget.DoubleClickExitHelper;

import java.util.Timer;
import java.util.TimerTask;


public class AppStartActivity extends Activity {
    private static final String tag = AppStartActivity.class.getSimpleName();

    LinearLayout llSkip;
    RelativeLayout ad_layout;
    ImageView ad_img;
    TextView tvTime;
    private String versionName;
    //    CountDownTimer timer;
    private boolean click = false;//true 点击过，false没有点击过
    //新增 广告倒计时
    private int recLen = 5;//跳过倒计时提示5秒
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    String welcome;//是不是首次访问，空是首次，非空不是
    DoubleClickExitHelper doubleClick;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        initStatus();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        doubleClick = new DoubleClickExitHelper(this);
        welcome = PreferenceHelper.getInstance(AppStartActivity.this).getString(AppConfig.WELCOME_PAGE, "-1");
//        Glide.with(this).load(R.mipmap.welcome_page)
//                .into(new ViewTarget<View, GlideDrawable>(findViewById(R.id.ll_appstart)) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
//                        this.view.setBackground(resource.getCurrent());
//                    }
//
//                });

        llSkip = (LinearLayout) findViewById(R.id.ll_skip);
        ad_layout = (RelativeLayout) findViewById(R.id.ad_layout);
        ad_img = (ImageView) findViewById(R.id.ad_img);
        tvTime = (TextView) findViewById(R.id.tv_time);

        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        llSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppStartActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
            }
        });

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }

//        new RxPermissions(AppStartActivity.this).requestEach(AppConfig.BASIC_PERMISSIONS)
//                .subscribe(new Action1<Permission>() {
//                    @Override
//                    public void call(Permission permission) {
//                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
//                            if (permission.granted) {
//                                // 用户已经同意该权限
//                                boolean isConnected = NetworkUtils.isConnected(AppStartActivity.this);
//                                if (isConnected) {
//                                    if (savedInstanceState != null) {
//                                        setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
//                                    } else {
//                                        parseNormalIntent(new Intent());
//
//                                    }
//                                } else {
//                                    Toast.makeText(AppStartActivity.this, "您的网络已断开！", Toast.LENGTH_LONG).show();
//                                    parseNormalIntent(new Intent());
//                                }
//                            } else if (permission.shouldShowRequestPermissionRationale) {
//                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                                Toast.makeText(AppStartActivity.this, "该应用需要赋予访问电话的权限，不开启将无法正常工作！", Toast.LENGTH_LONG).show();
//                                finish();
//                            } else {
//                                // 用户拒绝了该权限，并且选中『不再询问』
//                                Toast.makeText(AppStartActivity.this, "该应用需要赋予访问电话的权限，不开启将无法正常工作！", Toast.LENGTH_LONG).show();
//                                finish();
//                            }
//
//                        }
//                    }
//                });

        llSkip.setVisibility(View.VISIBLE);
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        handler.postDelayed(runnable = new

                Runnable() {
                    @Override
                    public void run() {
                        //从闪屏界面跳转到首界面
                        Intent intent = new Intent(AppStartActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }, 5000);//延迟5S后发送handler信息

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decoderView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decoderView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    private void parseNormalIntent(Intent intent) {
        showMainActivity(intent);
    }


    private void showMainActivity(Intent intent) {
        HomeBasicActivity.actionStart(this);
        finish();
        overridePendingTransition(0, 0);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            /*
             * 注意在if判断中要加一个event.getAction() == KeyEvent.ACTION_DOWN判断，
             * 因为按键有两个事件ACTION_DOWN和ACTION_UP，也就是按下和松开，
             * 如果不加这个判断，代码会执行两遍
             */
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //exitApp();
                doubleClick.onKeyDown(event.getKeyCode(), event);
            }
            //实现只在冷启动时显示启动页，即点击返回键与点击HOME键退出效果一致
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tvTime.setText(" " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        llSkip.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}