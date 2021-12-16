package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TuPianCaiDanUpAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.MenDianGuanLiModel;
import com.shendeng_bianmin.agent.model.Upload;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShow_OnePicture_urlActivity;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class TuPianCaiDanActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    TuPianCaiDanUpAdapter tuPianCaiDanUpAdapter;
    @BindView(R.id.rlv_tupiancaidan)
    RecyclerView rlvTupiancaidan;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private List<MenDianGuanLiModel.DataBean.LunBoListBean> tuPianCaiDanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);


        tuPianCaiDanUpAdapter = new TuPianCaiDanUpAdapter(R.layout.item_tupiancaidan_up, tuPianCaiDanList);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvTupiancaidan.setLayoutManager(linearLayoutManager);
        rlvTupiancaidan.setAdapter(tuPianCaiDanUpAdapter);

        tuPianCaiDanUpAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.constrain:

                        if (tuPianCaiDanList.get(position) == null) {
                            String[] items = {"拍照", "相册"};
                            final ActionSheetDialog dialog = new ActionSheetDialog(mContext, items, null);
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
                        } else {
                            ImageShow_OnePicture_urlActivity.actionStart(mContext, tuPianCaiDanList.get(position).getImg_url(), tuPianCaiDanList.get(position).getInst_img_id());
                        }
                        break;
                }

            }
        });

        // getNet();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNet();
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 配置takerPhoto参数
     */
    public CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        return builder.create();
    }


    private void saveEdit(File file) {
        OkGo.<AppResponse<Upload.DataBean>>post(Urls.UPLOAD)
                .tag(this)//
                .isMultipart(true)
                .params("code", Urls.code_04213)
                .params("key", Urls.KEY)
                .params("token", UserManager.getManager(mContext).getAppToken())
                .params("type", "1")
                .params("file", file)
                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "上传成功");
                        //   Glide.with(MenDianHeiSeActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivImage);
                        getNet();
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
    public int getContentViewResId() {
        return R.layout.layout_tupiancaidan;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("菜单图片");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, List<MenDianGuanLiModel.DataBean.LunBoListBean> tuPianCaiDanList) {
        Intent intent = new Intent();
        intent.setClass(context, TuPianCaiDanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tuPianCaiDanList", (Serializable) tuPianCaiDanList);
        context.startActivity(intent);
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

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04214);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<MenDianGuanLiModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MenDianGuanLiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MenDianGuanLiModel.DataBean>> response) {
                        tuPianCaiDanList.clear();
                        tuPianCaiDanList = response.body().data.get(0).getLun_bo_list();
                        tuPianCaiDanList.add(null);
                        tuPianCaiDanUpAdapter.setNewData(tuPianCaiDanList);
                        tuPianCaiDanUpAdapter.notifyDataSetChanged();

                    }
                });
    }
}
