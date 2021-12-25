package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.ShangpinDetailsZiAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.BottomDialog;
import com.shendeng_bianmin.agent.dialog.BottomDialogView;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_CaoZuoTIshi;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Success;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShowActivity;
import com.shendeng_bianmin.agent.util.FullyLinearLayoutManager;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ShangpinDetailsActivity extends BaseActivity {

    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.rv_taocan)
    RecyclerView rv_taocan;
    @BindView(R.id.ll_xianshiquanbu)
    LinearLayout ll_xianshiquanbu;
    @BindView(R.id.tv_xianshiquanbu)
    TextView tv_xianshiquanbu;
    @BindView(R.id.ll_taocan)
    LinearLayout ll_taocan;
    @BindView(R.id.ll_tuwen_details)
    LinearLayout ll_tuwen_details;
    @BindView(R.id.ll_pingjia)
    LinearLayout ll_pingjia;
    @BindView(R.id.bt_edit)
    TextView bt_edit;
    @BindView(R.id.bt_add)
    TextView bt_add;
    @BindView(R.id.bt_delete)
    TextView bt_delete;
    @BindView(R.id.iv_nopic)
    ImageView iv_nopic;

    private String wares_id;
    private ShangpinDetailsModel.DataBean dataBean;
    private List<ShangpinDetailsModel.DataBean.PackageListBean> package_list = new ArrayList<>();
    private List<ShangpinDetailsModel.DataBean.PackageListBean> package_list_use = new ArrayList<>();
    private ShangpinDetailsZiAdapter ziAdapter;
    private boolean isShowAll = false;
    private MyCarCaoZuoDialog_CaoZuoTIshi caoZuoTIshi;

    @Override
    public int getContentViewResId() {
        return R.layout.act_shangpin_details;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("商品详情");
        tv_rightTitle.setText("保存");
        tv_rightTitle.setTextSize(17);
        tv_rightTitle.setTextColor(this.getResources().getColor(R.color.color_494949));
        tv_rightTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv_rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String wares_id) {
        Intent intent = new Intent();
        intent.setClass(context, ShangpinDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //请求网络 获得详情
        getNet();
    }

    private void init() {
        wares_id = getIntent().getStringExtra("wares_id");
        initAdapter();
        initHuidiao();

        showProgressDialog();
        getNet();
    }

    private void showTishi() {
        if (caoZuoTIshi == null) {
            caoZuoTIshi = new MyCarCaoZuoDialog_CaoZuoTIshi(mContext, "提示", "上架销售的商品不能直接修改，请您先下架商品，完成修改操作", "知道了", new MyCarCaoZuoDialog_CaoZuoTIshi.OnDialogItemClickListener() {
                @Override
                public void clickLeft() {

                }

                @Override
                public void clickRight() {

                }
            });
        }
        caoZuoTIshi.show();
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.shangpin_details_use) {
                    getNet();
                }
            }
        }));
    }

    private void initAdapter() {
        ziAdapter = new ShangpinDetailsZiAdapter(R.layout.item_shangpin_details_zi, package_list_use);
        rv_taocan.setLayoutManager(new FullyLinearLayoutManager(mContext));
        rv_taocan.setNestedScrollingEnabled(false);
        rv_taocan.setFocusable(false);
        rv_taocan.setAdapter(ziAdapter);
        ziAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (package_list_use != null && package_list_use.size() > position) {
                    ShangpinDetailsModel.DataBean.PackageListBean bean = package_list_use.get(position);
                    if (bean.getProduct_state().equals("1")) {
                        showTishi();
                    } else {
                        actionStart(dataBean.getWares_id(), bean);
                    }
                }
            }
        });
    }

    private void actionStart(String wares_id, ShangpinDetailsModel.DataBean.PackageListBean packageListBean) {
        Intent intent = new Intent();
        intent.setClass(this, ShangpinZiAddActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        intent.putExtra("packageListBean", packageListBean);
        intent.putExtra("isEdit", false);
        startActivity(intent);
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
                        dataBean = response.body().data.get(0);
                        dataBean.setWares_id(wares_id);
                        initView();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }

    private void initView() {
        tv_title_name.setText(dataBean.getShop_product_title());
        package_list = dataBean.getPackage_list();
        package_list_use.clear();
        if (package_list.size() > 0 && package_list.size() < 3) {
            ll_taocan.setVisibility(View.VISIBLE);
            ll_xianshiquanbu.setVisibility(View.GONE);
            for (int i = 0; i < package_list.size(); i++) {
                package_list_use.add(package_list.get(i));
            }
        } else if (package_list.size() >= 3) {
            ll_taocan.setVisibility(View.VISIBLE);
            ll_xianshiquanbu.setVisibility(View.VISIBLE);
            for (int i = 0; i < 2; i++) {
                package_list_use.add(package_list.get(i));
            }
        } else {
            ll_taocan.setVisibility(View.GONE);
        }
        ziAdapter.setNewData(package_list_use);
        ziAdapter.notifyDataSetChanged();


        if (dataBean.getWares_state().equals("1")) {//商品状态 1.上架中  2.下架中
            bt_add.setText("下架");
        } else {
            bt_add.setText("上架");
        }

        initBanner();
    }

    @OnClick({R.id.ll_xianshiquanbu, R.id.ll_tuwen_details, R.id.ll_pingjia, R.id.bt_edit, R.id.bt_add, R.id.bt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_xianshiquanbu:
                clickXianshiQuanbu();
                break;
            case R.id.ll_tuwen_details:
                clickFengmian();
                //showBotoomm();
                break;
            case R.id.ll_pingjia:
                break;
            case R.id.bt_edit:
                cliickEdit();
                break;
            case R.id.bt_add:
                clickAdd();
                break;
            case R.id.bt_delete:
                showDeleteShangpin();
                break;
        }
    }

    private void showBotoomm() {
        if (dataBean.getWares_state().equals("1")) {
            showTishi();
        } else {
            List<String> names = new ArrayList<>();
            names.add("商品封面图");
            names.add("商品详情图");
            final BottomDialog bottomDialog = new BottomDialog(this);
            bottomDialog.setModles(names);
            bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
                @Override
                public void onClickItem(int pos) {
                    bottomDialog.dismiss();
                    if (pos == 0) {
                        clickFengmian();
                    } else {
                        clickTuwen();
                    }
                }

                @Override
                public void onClickCancel(View v) {
                    bottomDialog.dismiss();
                }
            });
            bottomDialog.showBottom();
        }
    }

    private void clickFengmian() {
        Intent intent = new Intent();
        intent.setClass(this, ShangpinFenmianActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        intent.putExtra("url", dataBean.getWares_photo_url());
        intent.putExtra("isEdit", false);
        startActivity(intent);
    }

    private void clickTuwen() {
        Intent intent = new Intent();
        intent.setClass(this, ShangpinImgxiangActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        startActivity(intent);
    }

    private void clickXianshiQuanbu() {
        package_list_use.clear();
        if (isShowAll) {
            tv_xianshiquanbu.setText("显示全部");
            for (int i = 0; i < 2; i++) {
                package_list_use.add(package_list.get(i));
            }
        } else {
            tv_xianshiquanbu.setText("收起");
            for (int i = 0; i < package_list.size(); i++) {
                package_list_use.add(package_list.get(i));
            }
        }
        isShowAll = !isShowAll;
        ziAdapter.setNewData(package_list_use);
        ziAdapter.notifyDataSetChanged();
    }

    private void clickAdd() {
        String text = bt_add.getText().toString();
        if (text.equals("上架")) {
            setStates("2");
        } else {
            setStates("1");
        }
    }

    private void setStates(String type) {//商品：下架传1 上架传2  删除传3
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04323);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", dataBean.getWares_id());
        map.put("dif_type", "1");
        map.put("type", type);//商品：下架传1 上架传2  删除传3

        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        if (type.equals("3")) {
                            Y.t("删除成功");
                            back();
                        } else {
                            String text = bt_add.getText().toString();
                            if (text.equals("上架")) {
                                bt_add.setText("下架");
                            } else {
                                bt_add.setText("上架");
                            }

                            MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(ShangpinDetailsActivity.this, new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
                                @Override
                                public void onDismiss() {
                                    getNet();
                                }
                            });
                            dialog.show();
                        }
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

    private void cliickEdit() {
        if (dataBean.getWares_state().equals("1")) {
            showTishi();
        } else {
            ShangpinEditActivity.actionStartD(mContext, dataBean);
        }
    }

    private void initBanner() {//初始化banner
//        List<ShangpinDetailsModel.DataBean.ImgListBean> img_list = dataBean.getImg_list();
//        if (img_list != null && img_list.size() > 0) {
//            iv_nopic.setVisibility(View.GONE);
//            MyImageLoader mMyImageLoader = new MyImageLoader();
//            banner.setImageLoader(mMyImageLoader);
//            //  initMagicIndicator1(tagList);
//            ArrayList<String> imagePath = new ArrayList<String>();
//            for (int i = 0; i < img_list.size(); i++) {
//                imagePath.add(img_list.get(i).getImg_url());
//            }
//            banner.setImages(imagePath);
//            //设置图片加载地址
//
//            banner.setOnBannerListener(new OnBannerListener() {
//                @Override
//                public void OnBannerClick(int position) {
//                    ImageShowActivity.actionStart(ShangpinDetailsActivity.this, imagePath);
//                }
//            });
//            banner.start();
//        } else {
//            iv_nopic.setVisibility(View.VISIBLE);
//        }
        Glide.with(this).load(dataBean.getWares_photo_url()).into(banner);

    }

    /**
     * 图片加载类
     */
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }


    private void showDeleteShangpin() {
        MyCarCaoZuoDialog_CaoZuoTIshi caoZuoTIshi = new MyCarCaoZuoDialog_CaoZuoTIshi(mContext, new MyCarCaoZuoDialog_CaoZuoTIshi.OnDialogItemClickListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                setStates("3");
            }
        });
        caoZuoTIshi.setTitle("提示");
        caoZuoTIshi.setTextContent("你确定要删除该商品么");
        caoZuoTIshi.show();
    }

    private void back() {
        Notice n = new Notice();
        n.type = ConstanceValue.shangpin_frag;
        RxBus.getDefault().sendRx(n);
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        back();
    }
}
