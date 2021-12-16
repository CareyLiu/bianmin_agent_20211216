package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.Upload;
import com.shendeng_bianmin.agent.util.GlideShowImageUtils;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import butterknife.BindView;

public class MenDianHeiSeActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.iv_image)
    ImageView ivImage;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String inst_photo_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        inst_photo_url = getIntent().getStringExtra("inst_photo_url");
        Glide.with(mContext).applyDefaultRequestOptions(GlideShowImageUtils.showZhengFangXing()).load(inst_photo_url).into(ivImage);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.layout_mendiantup_heise;
    }

    @Override
    public void initImmersion() {
        super.initImmersion();
        mImmersionBar.statusBarColor(R.color.black)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();

        tv_title.setText("门店图片");
        tv_title.setTextSize(17);
        tv_title.setTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.mipmap.back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(findViewById(R.id.cl_layout).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
        iv_rightTitle.setVisibility(View.VISIBLE);
        iv_rightTitle.setBackgroundResource(R.mipmap.mendiantupian_icon_gengduo);
        iv_rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UIHelper.ToastMessage(ZiJianShopMallDetailsActivity.this, "添加到购物车");
                // UIHelper.ToastMessage(ZiJianShopMallDetailsActivity.this, "功能开发中");
//                TianJiaSheBeiDialog tianJiaSheBeiDialog = new TianJiaSheBeiDialog(mContext, new TianJiaSheBeiDialog.OnDialogItemClickListener() {
//                    @Override
//                    public void paizhao() {
//                        //UIHelper.ToastMessage(mContext, "点击了拍照");
//                    }
//
//                    @Override
//                    public void quxiao() {
//                        //UIHelper.ToastMessage(mContext, "点击了取消");
//                        finish();
//                    }
//
//                    @Override
//                    public void xiangce() {
//                        //UIHelper.ToastMessage(mContext, "点击了相册");
//                    }
//                });
//                tianJiaSheBeiDialog.show();

                String[] items = {"拍照", "相册"};
                final ActionSheetDialog dialog = new ActionSheetDialog(MenDianHeiSeActivity.this, items, null);
                dialog.isTitleShow(false).show();
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        Uri imageUri = Uri.fromFile(file);
                        switch (position) {
                            case 0:
                                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                                break;
                            case 1:
                                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                                break;
                        }
                        dialog.dismiss();

                    }
                });
            }
        });
        mToolbar.setBackgroundColor(MenDianHeiSeActivity.this.getResources().getColor(R.color.black));
    }

    /**
     * 配置takerPhoto参数
     */
    public CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        return builder.create();
    }


    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    public boolean showToolBarLine() {
        return true;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String inst_photo_url) {
        Intent intent = new Intent();
        intent.setClass(context, MenDianHeiSeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("inst_photo_url", inst_photo_url);
        context.startActivity(intent);
    }

    @Override
    public void takeSuccess(TResult result) {
        //此处使用原图路径，不压缩
        File file = new File(result.getImage().getOriginalPath());

//        OkGo.<AppResponse<Upload.DataBean>>post(Urls.SERVER_URL + "msg/upload")
//                .tag(this)//
//                .isSpliceUrl(true)
//                .params("key", Urls.KEY)
//                .params("token", UserManager.getManager(MenDianCaiDanActivity.this).getAppToken())
//                .params("type", "1")
//                .params("file", file)
//                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
//                    @Override
//                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
//                        Glide.with(MenDianCaiDanActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivShanchuantupian);
//                    }
//
//                    @Override
//                    public void onError(Response<AppResponse<Upload.DataBean>> response) {
//
//                        UIHelper.ToastMessage(MenDianCaiDanActivity.this, response.getException().getMessage());
//                    }
//                });

        saveEdit(file);
    }

    private void saveEdit(File file) {
        OkGo.<AppResponse<Upload.DataBean>>post(Urls.UPLOAD)
                .tag(this)//
                .isMultipart(true)
                .params("code", Urls.code_04211)
                .params("key", Urls.KEY)
                .params("token", UserManager.getManager(mContext).getAppToken())
                .params("type", "1")
                .params("file", file)
                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "上传成功");
                        //   Glide.with(MenDianHeiSeActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivImage);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<AppResponse<Upload.DataBean>> response) {
                        Y.tError(response);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<Upload.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {
        UIHelper.ToastMessage(mContext, "取消选择");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }
}
