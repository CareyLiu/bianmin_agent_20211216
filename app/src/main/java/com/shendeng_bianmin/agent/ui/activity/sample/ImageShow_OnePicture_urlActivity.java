package com.shendeng_bianmin.agent.ui.activity.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.MenDianGuanLiModel;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class ImageShow_OnePicture_urlActivity extends BaseActivity {

    /**
     * 菜单图片进入页 专用
     */

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.full_image_root)
    RelativeLayout fullImageRoot;
    /**
     * PagerAdapter
     */
    private ImagePagerAdapter mAdapter;
    private String imgInstID;


    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context, String url, String inst_image_id) {
        Intent intent = new Intent(context, ImageShow_OnePicture_urlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("bitmap", url);
        intent.putExtra("inst_image_id", inst_image_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initData();

        imgInstID = getIntent().getStringExtra("inst_image_id");


        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initData() {
        Intent i = getIntent();
        if (i == null) {
            return;
        }
        Glide.with(mContext).load(getIntent().getStringExtra("bitmap")).into(ivImage);

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_one_image_show;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    @Override
    public boolean showToolBar() {
        return true;
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

                String[] items = {"删除"};
                final ActionSheetDialog dialog = new ActionSheetDialog(ImageShow_OnePicture_urlActivity.this, items, null);
                dialog.isTitleShow(false).show();
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0:
                                deleteNet();
                                break;
                        }
                        dialog.dismiss();

                    }
                });
            }
        });
        mToolbar.setBackgroundColor(ImageShow_OnePicture_urlActivity.this.getResources().getColor(R.color.black));
    }

    private void deleteNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04213);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("inst_img_id", imgInstID);
        Gson gson = new Gson();
        OkGo.<AppResponse<MenDianGuanLiModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MenDianGuanLiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MenDianGuanLiModel.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "删除图片成功");
                        finish();
                    }

                    @Override
                    public void onStart(Request<AppResponse<MenDianGuanLiModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }
}
