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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
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
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel1;
import com.shendeng_bianmin.agent.model.Upload;
import com.shendeng_bianmin.agent.model.Upload1;
import com.shendeng_bianmin.agent.ui.activity.headimage.ClipImageActivity;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShowActivity;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tbruyelle.rxpermissions.RxPermissions;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ShangpinFenmianActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.rv_wu)
    RelativeLayout rv_wu;
    @BindView(R.id.iv_main)
    ImageView iv_main;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.bt_chongxin)
    TextView bt_chongxin;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    private String wares_id;
    private String url;
    private File file;
    private boolean isEdit;
    private TakePhoto takePhoto;
    private ShangpinDetailsModel.DataBean detailsModel;
    private ShangpinDetailsModel1.DataBean detailsModel1;

    @Override
    public int getContentViewResId() {
        return R.layout.act_shangpin_fengmian;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("??????????????????");
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
        url = getIntent().getStringExtra("url");
        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (TextUtils.isEmpty(url)) {
            ll_main.setVisibility(View.GONE);
            rv_wu.setVisibility(View.VISIBLE);
        } else {
            ll_main.setVisibility(View.VISIBLE);
            rv_wu.setVisibility(View.GONE);
            Glide.with(mContext).load(url).into(iv_main);
        }
    }

    @OnClick({R.id.iv_add, R.id.iv_main, R.id.iv_delete, R.id.bt_chongxin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main:
                showPic();
                break;
            case R.id.iv_delete:
                showDeleteDialog();
                break;
            case R.id.bt_chongxin:
            case R.id.iv_add:
                clickAdd();
                break;
        }
    }

    private void showPic() {
        ArrayList<String> imgs = new ArrayList<>();
        if (!TextUtils.isEmpty(url)) {
            if (TextUtils.isEmpty(url)) {
                imgs.add(file.getPath());
            } else {
                imgs.add(url);
            }
        } else {
            imgs.add(file.getPath());
        }
        ImageShowActivity.actionStart(mContext, imgs);
    }

    private void clickAdd() {
        List<String> names = new ArrayList<>();
        names.add("??????");
        names.add("??????");
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
                if (granted) { // ???android 6.0?????????????????????true
                    gotoCamera();
                } else {
                    Y.tLong("??????????????????????????????????????????????????????????????????????????????");
                }
            }
        });
    }

    //???????????????????????????????????????
    private File tempFile;

    private void gotoCamera() {
        //???????????????????????????
        tempFile = createCameraTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, AppCode.CAMERA_PERMISSIONS_REQUEST_CODE);
    }

    /**
     * ???????????????????????????????????????????????????
     */
    public File createCameraTempFile() {
        return new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/sleepApp/image/"),
                System.currentTimeMillis() + ".jpg");
    }

    /**
     * ????????????????????????
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
                if (granted) { // ???android 6.0?????????????????????true
                    gotoPic();
                } else {
                    Y.tLong("??????????????????????????????????????????????????????????????????????????????");
                }
            }
        });
    }

    private void gotoPic() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "???????????????"), AppCode.STORAGE_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case AppCode.CAMERA_PERMISSIONS_REQUEST_CODE: //????????????????????????
//                if (resultCode == this.RESULT_OK) {
//                    gotoClipActivity(Uri.fromFile(tempFile));
//                }
//                break;
//            case AppCode.STORAGE_PERMISSIONS_REQUEST_CODE:  //????????????????????????
//                if (resultCode == this.RESULT_OK) {
//                    Uri uri = data.getData();
//                    gotoClipActivity(uri);
//                }
//                break;
//            case AppCode.REQUEST_CROP_PHOTO:  //??????????????????
//                handleCrop(resultCode, data);
//                if (tempFile != null) {
//                    tempFile.delete();
//                }
//                break;
//        }
    }

    /**
     * ??????????????????
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
            Y.t("????????????");
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
        rv_wu.setVisibility(View.GONE);
        ll_main.setVisibility(View.VISIBLE);
        file = new File(imageString);
        Glide.with(mContext).load(file).into(iv_main);
        saveEdit();
    }

    private void saveEdit() {
        OkGo.<AppResponse<Upload1.DataBean>>post(Urls.USER_UPLOAD)
                .tag(this)//
                .isMultipart(true)
                .params("code", Urls.code_04199)
                .params("key", Urls.KEY)
                .params("token", UserManager.getManager(mContext).getAppToken())
                .params("wares_id", wares_id)
                .params("type", "3")
                .params("file", file)
                .execute(new JsonCallback<AppResponse<Upload1.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<Upload1.DataBean>> response) {
                        // TODO: 2021-12-16 ??????????????????????????????code
                        String img_url = response.body().data.get(0).getImg_url();
                        if (!TextUtils.isEmpty(wares_id)){
                            addShangpin(img_url);
                        }else {
                            Notice notice = new Notice();
                            notice.type = ConstanceValue.MSG_LEIMU_ID;
                            notice.content = img_url;
                            sendRx(notice);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<AppResponse<Upload1.DataBean>> response) {

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<Upload1.DataBean>, ? extends Request> request) {
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

    private void showDeleteDialog() {
        MyCarCaoZuoDialog_CaoZuoTIshi caoZuoTIshi = new MyCarCaoZuoDialog_CaoZuoTIshi(mContext, new MyCarCaoZuoDialog_CaoZuoTIshi.OnDialogItemClickListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                deletePicture();
            }
        });
        caoZuoTIshi.setTitle("??????");
        caoZuoTIshi.setTextContent("?????????????????????????????????");
        caoZuoTIshi.show();
    }

    private void deletePicture() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04192);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", wares_id);
        map.put("delete_type", "4");//?????????????????? 1.??????????????? 2.?????????????????? 3.??????????????? 4.????????????
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        ll_main.setVisibility(View.GONE);
                        rv_wu.setVisibility(View.VISIBLE);
                        url = "";
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

    @Override
    public void takeSuccess(TResult result) {
        //????????????????????????????????????


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
        UIHelper.ToastMessage(mContext, "????????????");
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
     * ??????TakePhoto??????
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
     * ??????takerPhoto??????
     */
    public CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        return builder.create();
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime(Date date) {//???????????????????????????????????????
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        return strDate;
    }

    private InvokeParam invokeParam;

    private void addShangpin(String str) {


//        if (TextUtils.isEmpty(textChandi)) {
//            Y.t("???????????????");
//            return;
//        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04179);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_photo_url", str);
        map.put("wares_id", wares_id);
        map.put("enter_type", "5");

        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        //detailsModel = response.body().data.get(0);
                        //ShangpinEditActivity.actionStart(mContext, detailsModel);
                        Glide.with(mContext).load(str).into(iv_main);
                        UIHelper.ToastMessage(mContext, "????????????");
                        finish();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        for (int i = 0; i < 100; i++) {
                            System.out.println("");
                        }
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
