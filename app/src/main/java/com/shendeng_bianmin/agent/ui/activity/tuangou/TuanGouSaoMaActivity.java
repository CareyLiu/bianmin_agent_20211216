package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.OrderModel;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class TuanGouSaoMaActivity extends BaseActivity implements QRCodeView.Delegate {


    @BindView(R.id.zxingview)
    ZBarView mQRCodeView;
    @BindView(R.id.capture_flash)
    ImageView captureFlash;
    @BindView(R.id.ll)
    LinearLayout ll;
    boolean flag = true;
    private ProgressDialog waitdialog;

    @Override
    public int getContentViewResId() {
        return R.layout.tuangou_act_ewm;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("扫一扫");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TuanGouSaoMaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStartForResult(Activity activity, int result) {
        Intent intent = new Intent(activity, TuanGouSaoMaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, result);
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
        waitdialog = ProgressDialog.show(TuanGouSaoMaActivity.this, null, "已扫描，正在处理···", true, true);
        waitdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {

            }
        });

        UIHelper.ToastMessage(mContext, result);
        getNet(result);
        finish();
    }

    private void getNet(String result) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04318);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("pay_code", result);

        Gson gson = new Gson();
        OkGo.<AppResponse<OrderModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderModel.DataBean>> response) {
                        Y.t("扫码验证成功");
                        finish();
                    }

                    @Override
                    public void onError(Response<AppResponse<OrderModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        waitdialog.dismiss();
                    }
                });
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
