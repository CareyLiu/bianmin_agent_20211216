package com.shendeng_bianmin.agent.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.ShangpinBannerAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.BottomDialog;
import com.shendeng_bianmin.agent.dialog.BottomDialogView;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_CaoZuoTIshi;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.model.Upload;
import com.shendeng_bianmin.agent.ui.activity.headimage.ClipImageActivity;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShowActivity;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ShangpinBannerActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


    @BindView(R.id.iv_main)
    ImageView iv_main;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private String wares_id;
    private ShangpinBannerAdapter adapter;
    private ShangpinDetailsModel.DataBean detailsModel;
    private List<ShangpinDetailsModel.DataBean.ImgListBean> imgText_list = new ArrayList<>();
    private int position;
    private File file;
    private boolean isEdit;
    private TakePhoto takePhoto;

    @Override
    public int getContentViewResId() {
        return R.layout.act_shangpin_imgbanner;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("商品详情图片");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityFinish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        getTakePhoto().onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        wares_id = getIntent().getStringExtra("wares_id");
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        initAdapter();

        showProgressDialog();
        getNet();
    }

    private void initAdapter() {
        adapter = new ShangpinBannerAdapter(R.layout.item_shangpin_addimg, imgText_list);
        rv_content.setAdapter(adapter);
        rv_content.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (imgText_list != null && imgText_list.size() > position) {
                    switch (view.getId()) {
                        case R.id.iv_main:
                            showPicMain(position);
                            break;
                    }
                }
            }
        });
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04322);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", wares_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        detailsModel = response.body().data.get(0);
                        imgText_list = detailsModel.getImg_list();
                        ShangpinDetailsModel.DataBean.ImgListBean addBeen = new ShangpinDetailsModel.DataBean.ImgListBean();
                        imgText_list.add(addBeen);
                        if (imgText_list != null && imgText_list.size() > 1) {
                            position = 0;
                            Glide.with(mContext).load(imgText_list.get(position).getImg_url()).into(iv_main);
                        }
                        adapter.setNewData(imgText_list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }


    private void showPic() {
        if (imgText_list.size() > 1) {
            ArrayList<String> imgs = new ArrayList<>();
            for (int i = 0; i < imgText_list.size() - 1; i++) {
                imgs.add(imgText_list.get(i).getImg_url());
            }
            ImageShowActivity.actionStart(mContext, imgs, position);
        }
    }


    private void showPicMain(int position) {
        if (position == imgText_list.size() - 1) {
            clickAdd();
        } else {
            this.position = position;
            String img_url = imgText_list.get(position).getImg_url();
            Glide.with(mContext).load(img_url).into(iv_main);
        }
    }

    private void clickAdd() {
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        final BottomDialog bottomDialog = new BottomDialog(this);
        bottomDialog.setModles(names);
        bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
            @Override
            public void onClickItem(int position) {
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
                bottomDialog.dismiss();
            }

            @Override
            public void onClickCancel(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.showBottom();
    }

    private void selectPaizhao() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) { // 在android 6.0之前会默认返回true
                    gotoCamera();
                } else {
                    Y.tLong("该应用需要赋予访问相机的权限，不开启将无法正常工作！");
                }
            }
        });
    }

    //调用照相机返回图片临时文件
    private File tempFile;

    private void gotoCamera() {
        //跳转到调用系统相机
        tempFile = createCameraTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, AppCode.CAMERA_PERMISSIONS_REQUEST_CODE);
    }

    /**
     * 创建调用系统照相机待存储的临时文件
     */
    public File createCameraTempFile() {
        return new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/sleepApp/image/"),
                System.currentTimeMillis() + ".jpg");
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    private void selectXiangce() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) { // 在android 6.0之前会默认返回true
                    gotoPic();
                } else {
                    Y.tLong("该应用需要赋予访问相册的权限，不开启将无法正常工作！");
                }
            }
        });
    }

    private void gotoPic() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), AppCode.STORAGE_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case AppCode.CAMERA_PERMISSIONS_REQUEST_CODE: //调用系统相机返回
//                if (resultCode == this.RESULT_OK) {
//                    gotoClipActivity(Uri.fromFile(tempFile));
//                }
//                break;
//            case AppCode.STORAGE_PERMISSIONS_REQUEST_CODE:  //调用系统相册返回
//                if (resultCode == this.RESULT_OK) {
//                    Uri uri = data.getData();
//                    gotoClipActivity(uri);
//                }
//                break;
//            case AppCode.REQUEST_CROP_PHOTO:  //剪切图片返回
//                handleCrop(resultCode, data);
//                if (tempFile != null) {
//                    tempFile.delete();
//                }
//                break;
//        }
    }

    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, AppCode.REQUEST_CROP_PHOTO);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == this.RESULT_OK) {
            Uri uri = result.getData();
            if (uri == null) {
                return;
            }
            String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
            uploadImage(cropImagePath);
        } else if (resultCode == 404) {
            Y.t("剪裁失败");
        }
    }

    public String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void uploadImage(final String imageString) {
        file = new File(imageString);
        saveEdit();
    }

    private void saveEdit() {
        OkGo.<AppResponse<Upload.DataBean>>post(Urls.UPLOAD)
                .tag(this)//
                .isMultipart(true)
                .params("code", Urls.code_04195)
                .params("key", Urls.KEY)
                .params("token", UserManager.getManager(mContext).getAppToken())
                .params("wares_id", wares_id)
                .params("type", "1")
                .params("file", file)
                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
                        getNet();
                        addShangpin(response.body().data.get(0).getFile_url());
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
    public void onBackPressedSupport() {
        onActivityFinish();
    }

    private void onActivityFinish() {
        Notice n = new Notice();
        if (isEdit) {
            n.type = ConstanceValue.shangpin_edit_use;
        } else {
            n.type = ConstanceValue.shangpin_details_use;
        }
        RxBus.getDefault().sendRx(n);
        finish();
    }

    private void showDeleteDialog(int pos) {
        MyCarCaoZuoDialog_CaoZuoTIshi caoZuoTIshi = new MyCarCaoZuoDialog_CaoZuoTIshi(mContext, new MyCarCaoZuoDialog_CaoZuoTIshi.OnDialogItemClickListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                deletePicture(pos);
            }
        });
        caoZuoTIshi.setTitle("提示");
        caoZuoTIshi.setTextContent("你确定要删除当前图片么");
        caoZuoTIshi.show();
    }

    private void deletePicture(int pos) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04192);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_img_id", imgText_list.get(pos).getWares_img_id());
        map.put("delete_type", "3");//删除图片类型 1.子商品图片 2.图文详情图片 3.轮播图图片 4.封面主图
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        position = 0;
                        imgText_list.remove(pos);
                        detailsModel.setImg_list(imgText_list);
                        if (imgText_list.size() <= 1) {
                            iv_delete.setVisibility(View.GONE);
                            iv_main.setImageResource(R.mipmap.nopic_preview_shop);
                        } else {
                            Glide.with(mContext).load(imgText_list.get(0).getImg_url()).into(iv_main);
                            iv_delete.setVisibility(View.VISIBLE);
                        }
                        adapter.setNewData(imgText_list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<ShangpinDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onError(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                    }
                });
    }

    @OnClick({R.id.iv_main, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main:
                showPic();
                break;
            case R.id.iv_delete:
                showDeleteDialog(position);
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        //此处使用原图路径，不压缩


        file = new File(result.getImage().getOriginalPath());
        saveEdit();
//        OkGo.<AppResponse<Upload.DataBean>>post(Urls.SERVER_URL + "msg/upload")
//                .tag(this)//
//                .isSpliceUrl(true)
//                .params("key", Urls.key)
//                .params("token", UserManager.getManager(SettingActivity.this).getAppToken())
//                .params("type", "1")
//                .params("file", file)
//                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
//                    @Override
//                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
//                        Glide.with(SettingActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivHeader);
//                    }
//
//                    @Override
//                    public void onError(Response<AppResponse<Upload.DataBean>> response) {
//                        AlertUtil.t(SettingActivity.this, response.getException().getMessage());
//                    }
//                });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        UIHelper.ToastMessage(mContext, msg);
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
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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

    /**
     * 配置takerPhoto参数
     */
    public CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        return builder.create();
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        return strDate;
    }

    private InvokeParam invokeParam;

    private void addShangpin(String str) {


//        if (TextUtils.isEmpty(textChandi)) {
//            Y.t("请选择产地");
//            return;
//        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04179);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_photo_url", str);
        map.put("wares_id", wares_id);

        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        detailsModel = response.body().data.get(0);
                        ShangpinEditActivity.actionStart(mContext, detailsModel);
                        finish();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<ShangpinDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }
}
