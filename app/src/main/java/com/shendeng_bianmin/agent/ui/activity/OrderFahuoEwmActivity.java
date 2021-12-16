package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.Y;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class OrderFahuoEwmActivity extends BaseActivity implements QRCodeView.Delegate {


    @BindView(R.id.zxingview)
    ZBarView mQRCodeView;
    @BindView(R.id.capture_flash)
    ImageView captureFlash;
    @BindView(R.id.ll)
    LinearLayout ll;
    boolean flag = true;

    @Override
    public int getContentViewResId() {
        return R.layout.act_ewm;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("扫描快递单号");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OrderFahuoEwmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mQRCodeView.startSpot();
        mQRCodeView.setDelegate(this);
    }

    @OnClick(R.id.capture_flash)
    public void onViewClicked() {
        light();
    }

    private void light() {
        if (flag) {
            flag = false;
            // 开闪光灯
            mQRCodeView.openFlashlight();
            captureFlash.setTag(null);
            captureFlash.setBackgroundResource(R.drawable.flash_open);
        } else {
            flag = true;
            // 关闪光灯
            mQRCodeView.closeFlashlight();
            captureFlash.setTag("1");
            captureFlash.setBackgroundResource(R.drawable.flash_default);
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Y.e("扫描结果" + result);
        vibrate();
        mQRCodeView.startSpot();
        Notice n = new Notice();
        n.content = result;
        n.type = ConstanceValue.order_fahuo;
        RxBus.getDefault().sendRx(n);
        finish();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        mQRCodeView.startCamera();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQRCodeView.stopSpot();
    }
}
