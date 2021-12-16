package com.shendeng_bianmin.agent.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.model.tuangou.EwmModel;
import com.shendeng_bianmin.agent.ui.activity.tuangou.TuanMingxiActivity;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.functions.Action1;

public class MaiDanShouKuanActivity extends BaseActivity {

    @BindView(R.id.iv_erweima)
    ImageView iv_erweima;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.ll_zhangdan)
    LinearLayout llZhangdan;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNet();
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04181);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<EwmModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<EwmModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<EwmModel.DataBean>> response) {
                        String url_app = response.body().data.get(0).getUrl_app();
                        bitmap = QRCodeEncoder.syncEncodeQRCode(url_app, 300);
                        Glide.with(mContext).load(bitmap).into(iv_erweima);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_mai_dan_shou_kuan;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MaiDanShouKuanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("买单收款");
    }

    @OnClick({R.id.tv_save, R.id.iv_erweima, R.id.ll_zhangdan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_zhangdan:
                TuanMingxiActivity.actionStart(mContext, AppCode.mingxi_maidan);
                break;
            case R.id.tv_save:
            case R.id.iv_erweima:
                getP();
                break;
        }
    }

    private void getP() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) { // 在android 6.0之前会默认返回true
                    showDialogTi();
                } else {
                    Y.tLong("该应用需要赋予存储图片的权限，不开启将无法正常工作！");
                }
            }
        });
    }


    private void showDialogTi() {
        TishiDialog dialog = new TishiDialog(mContext, new TishiDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, TishiDialog dialog) {

            }

            @Override
            public void onClickConfirm(View v, TishiDialog dialog) {
                try {
                    String name = "jyj_tuan_" + System.currentTimeMillis();
                    boolean save = save(bitmap, name);
                    if (save) {
                        Y.t("保存成功");
                    } else {
                        Y.t("保存失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Y.t("保存失败");
                }
            }

            @Override
            public void onDismiss(TishiDialog dialog) {

            }
        });
        dialog.setTextCont("保存图片");
        dialog.show();
    }

    public boolean save(Bitmap bmp, String bitName) throws IOException {
        File dirFile = new File("./sdcard/DCIM/Camera/");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File("./sdcard/DCIM/Camera/" + bitName + ".jpg");
        boolean flag = false;
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
