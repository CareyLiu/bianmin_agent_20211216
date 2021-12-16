package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.MenDianPhotoAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.model.TaoCanDetailsModel;
import com.shendeng_bianmin.agent.model.Upload;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MenDianCaiDanActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_shanchu)
    TextView tvShanchu;
    @BindView(R.id.tv_sheweifengmian)
    TextView tvSheweifengmian;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    String chooseItem;//0 第一个 1 第二个 2 第三个 3 第四个
    private String waresId;//套餐id

    List<TaoCanDetailsModel.DataBean.ImgListBean> mDatas;

    private MenDianPhotoAdapter photoAdapter;
    private int choose_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
//        ivShanchuantupian1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseItem = "0";
//                String[] items = {"拍照", "相册"};
//                final ActionSheetDialog dialog = new ActionSheetDialog(MenDianCaiDanActivity.this, items, null);
//                dialog.isTitleShow(false).show();
//                dialog.setOnOperItemClickL(new OnOperItemClickL() {
//                    @Override
//                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//                        if (!file.getParentFile().exists()) {
//                            file.getParentFile().mkdirs();
//                        }
//                        Uri imageUri = Uri.fromFile(file);
//                        switch (position) {
//                            case 0:
//                                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
//                                break;
//                            case 1:
//                                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
//                                break;
//                        }
//                        dialog.dismiss();
//
//                    }
//                });
//            }
//        });


        waresId = getIntent().getStringExtra("waresId");

        mDatas = new ArrayList<>();


        photoAdapter = new MenDianPhotoAdapter(R.layout.item_taocantupian_list, mDatas);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvContent.setLayoutManager(gridLayoutManager);
        rvContent.setAdapter(photoAdapter);

        photoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.constrain:

                        if (mDatas.get(position).type == null) {

                            choose_position = position;
                            Glide.with(mContext).load(mDatas.get(choose_position).getImg_url()).into(ivMain);
                        } else {

                            String[] items = {"拍照", "相册"};
                            final ActionSheetDialog dialog = new ActionSheetDialog(MenDianCaiDanActivity.this, items, null);
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
//                                            takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                                            takePhoto.onPickFromCapture(imageUri);
                                            break;
                                        case 1:
//                                            takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                                            takePhoto.onPickFromGallery();
                                            break;
                                    }
                                    dialog.dismiss();

                                }
                            });


                        }

                        break;
                }
            }
        });
        getDetailsNet();
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePicture();
            }
        });


        tvSheweifengmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheWeiFengMian();
            }
        });
        tvShanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.size() == 1) {
                    UIHelper.ToastMessage(mContext, "暂无封面图片");
                } else {
                    deletePicture();
                }
            }
        });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.tuangou_imgbanner;
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context leixing 0新建进入 1 二次进入
     */
    public static void actionStart(Context context, String waresId) {
        Intent intent = new Intent(context, MenDianCaiDanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("waresId", waresId);
        context.startActivity(intent);
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("门店图片");
        tv_title.setTextSize(17);
        tv_title.setTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationIcon(R.mipmap.backbutton);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(findViewById(R.id.cl_layout).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
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
        UIHelper.ToastMessage(mContext, msg);
    }

    @Override
    public void takeCancel() {

        UIHelper.ToastMessage(mContext, "取消选择");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }


    private void saveEdit(File file) {
        OkGo.<AppResponse<Upload.DataBean>>post(Urls.UPLOAD)
                .tag(this)//
                .isMultipart(true)
                .params("code", Urls.code_04195)
                .params("key", Urls.KEY)
                .params("token", UserManager.getManager(mContext).getAppToken())
                .params("wares_id", waresId)
                .params("type", "1")
                .params("file", file)
                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "上传成功");
                        dismissProgressDialog();

                        //    mDatas.add(response.body().data.get(0).)

                        getDetailsNet();
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


    private void sheZhiFengMian() {
        OkGo.<AppResponse<Upload.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .isMultipart(true)
                .params("code", Urls.code_04195)
                .params("key", Urls.KEY)
                .params("token", UserManager.getManager(mContext).getAppToken())
                .params("wares_id", waresId)
                .params("type", "1")

                .execute(new JsonCallback<AppResponse<Upload.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<Upload.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "上传成功");
                        dismissProgressDialog();

                        //    mDatas.add(response.body().data.get(0).)

                        getDetailsNet();
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

    private void getDetailsNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04203);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", waresId);

        Gson gson = new Gson();
        OkGo.<AppResponse<TaoCanDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TaoCanDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TaoCanDetailsModel.DataBean>> response) {

                        if (response.body().data.get(0).getImg_list().size() != 0) {
                            Glide.with(mContext).load(response.body().data.get(0).getImg_list().get(choose_position).getImg_url()).into(ivMain);
                        }
                        mDatas.clear();
                        mDatas.addAll(response.body().data.get(0).getImg_list());
                        TaoCanDetailsModel.DataBean.ImgListBean bean = new TaoCanDetailsModel.DataBean.ImgListBean();
                        bean.type = "1";
                        mDatas.add(bean);
                        photoAdapter.notifyDataSetChanged();

                        if (mDatas.size() > 2) {
                            rvContent.scrollToPosition(mDatas.size() - 2);
                        }

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TaoCanDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    private void deletePicture() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04192);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_img_id", mDatas.get(choose_position).getWares_img_id());
        map.put("delete_type", "3");//删除图片类型 1.子商品图片 2.图文详情图片 3.轮播图图片 4.封面主图
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        Glide.with(mContext).load(R.mipmap.nopic_preview_shop).into(ivMain);
                        photoAdapter.remove(choose_position);
                        photoAdapter.notifyDataSetChanged();
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

    private void sheWeiFengMian() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04216);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_img_id", mDatas.get(choose_position).getWares_img_id());

        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "设为封面成功");
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

}
