package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.ShangpinBannerAdapter;
import com.shendeng_bianmin.agent.adapter.ShangpinZiAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.InputDialog;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Success;
import com.shendeng_bianmin.agent.model.ChandiModel;
import com.shendeng_bianmin.agent.model.LeimuModel;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.ui.activity.sample.ImageShowActivity;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ShangpinEditActivity extends BaseActivity {

    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.ll_title_img)
    LinearLayout ll_title_img;
    @BindView(R.id.ed_title_name)
    TextView ed_title_name;
    @BindView(R.id.tv_leimu)
    TextView tv_leimu;
    @BindView(R.id.ll_leimu)
    LinearLayout ll_leimu;
    @BindView(R.id.iv_swich)
    ImageView iv_swich;
    @BindView(R.id.tv_anzhuangfei)
    TextView tv_anzhuangfei;
    @BindView(R.id.ll_anzhuangfei)
    LinearLayout ll_anzhuangfei;
    @BindView(R.id.ll_anzhuangfuwu)
    LinearLayout ll_anzhuangfuwu;
    @BindView(R.id.tv_yunfei)
    TextView tv_yunfei;
    @BindView(R.id.ll_yunfei)
    LinearLayout ll_yunfei;
    @BindView(R.id.tv_chandi)
    TextView tv_chandi;
    @BindView(R.id.ll_chandi)
    LinearLayout ll_chandi;
    @BindView(R.id.rv_add_shangpinhao)
    RecyclerView rv_add_shangpinhao;
    @BindView(R.id.ll_add_shangpinhao)
    LinearLayout ll_add_shangpinhao;
    @BindView(R.id.ll_tuwen)
    LinearLayout ll_tuwen;
    @BindView(R.id.ll_fengmian)
    LinearLayout ll_fengmian;
    @BindView(R.id.bt_ok)
    TextView bt_ok;
    @BindView(R.id.tv_img_num)
    TextView tv_img_num;
    @BindView(R.id.rv_img_add)
    RecyclerView rv_img_add;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.ll_bianhao)
    LinearLayout llBianhao;
    @BindView(R.id.tv_kucun)
    TextView tvKucun;
    @BindView(R.id.ll_kucun)
    LinearLayout llKucun;

    private String enter_type;
    private String dif_type;
    private ShangpinDetailsModel.DataBean detailsModel;

    private List<ChandiModel.DataBean> chandiModels;
    private List<LeimuModel.DataBean> leimuModels;
    private OptionsPickerView<Object> leimuPicker;
    private OptionsPickerView<Object> chandiPicker;
    private String item_id_one;
    private String item_id_one_name;
    private String item_id_two;
    private String item_id_two_name;
    private String item_id_three;
    private String item_id_three_name;
    private String province_name_pro;
    private String province_id_pro;
    private String city_name_pro;
    private String city_id_pro;

    private static final int type_name = 1;
    private static final int type_yunfei = 2;
    private static final int type_anzhuangfei = 3;
    private static final int type_leimu = 4;
    private static final int type_chandi = 5;
    private static final int type_shangpin_bianhao = 6;
    private static final int type_shangpin_kucun = 7;
    private String linshiYunfei;
    private String linshianzhuang;
    private List<ShangpinDetailsModel.DataBean.PackageListBean> package_list = new ArrayList<>();
    private ShangpinZiAdapter ziAdapter;
    private List<ShangpinDetailsModel.DataBean.ImgListBean> imgText_list = new ArrayList<>();
    private ShangpinBannerAdapter topAdapter;
    private String wares_id;
    private List<ShangpinDetailsModel.DataBean.ImgListBean> img_list;
    private boolean isDetails;

    @Override
    public int getContentViewResId() {
        return R.layout.act_shangpin_edit;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("完善商品信息");
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
    public static void actionStart(Context context, ShangpinDetailsModel.DataBean detailsModel) {
        Intent intent = new Intent();
        intent.setClass(context, ShangpinEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("detailsModel", detailsModel);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStartD(Context context, ShangpinDetailsModel.DataBean detailsModel) {
        Intent intent = new Intent();
        intent.setClass(context, ShangpinEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("detailsModel", detailsModel);
        intent.putExtra("isDetails", true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();

        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice notice) {
                if (notice.type == ConstanceValue.MSG_TIANJIALEIMU) {
                    tv_leimu.setText((String) notice.content);
                }
            }
        }));

    }

    private void init() {
        Window win = this.getWindow();
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        enter_type = "5";
        dif_type = "1";
        detailsModel = (ShangpinDetailsModel.DataBean) getIntent().getSerializableExtra("detailsModel");
        wares_id = detailsModel.getWares_id();
        isDetails = getIntent().getBooleanExtra("isDetails", false);

        tvKucun.setText(detailsModel.wares_count);
        tv_yunfei.setText(detailsModel.shop_money_now);
        tvBianhao.setText(detailsModel.wares_number);
        initAdapter();
        initTopAdapter();
        initView();
        initHuidiao();
    }

    private void initTopAdapter() {
        topAdapter = new ShangpinBannerAdapter(R.layout.item_shangpin_addimg, imgText_list);
        rv_img_add.setAdapter(topAdapter);
        rv_img_add.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        topAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
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

    private void showPicMain(int position) {
        if (img_list != null && img_list.size() > position) {
            ArrayList<String> imgs = new ArrayList<>();
            for (int i = 0; i < img_list.size(); i++) {
                imgs.add(img_list.get(i).getImg_url());
            }
            ImageShowActivity.actionStart(mContext, imgs, position);
        }
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.shangpin_edit_use) {
                    getNet();
                }
            }
        }));
    }

    private void initAdapter() {
        ziAdapter = new ShangpinZiAdapter(R.layout.item_shangpin_zi, package_list);
        rv_add_shangpinhao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_add_shangpinhao.setNestedScrollingEnabled(false);
        rv_add_shangpinhao.setFocusable(false);
        rv_add_shangpinhao.setAdapter(ziAdapter);
        ziAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (package_list != null && package_list.size() > position) {
                    ShangpinDetailsModel.DataBean.PackageListBean packageListBean = package_list.get(position);
                    if (view.getId() == R.id.tv_edit_click) {
                        actionStart(detailsModel.getWares_id(), packageListBean);
                    } else if (view.getId() == R.id.iv_add_img) {
                        String index_photo_url = packageListBean.getIndex_photo_url();
                        if (TextUtils.isEmpty(index_photo_url)) {
                            actionStart(detailsModel.getWares_id(), packageListBean);
                        } else {
                            ArrayList<String> imgs = new ArrayList<>();
                            imgs.add(index_photo_url);
                            ImageShowActivity.actionStart(mContext, imgs);
                        }
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
        intent.putExtra("isEdit", true);
        startActivity(intent);
    }

    private void initView() {
        if (detailsModel.getWares_state().equals("1")) {//商品状态 1.上架中  2.下架中
            bt_ok.setText("放入仓库");
        } else {
            bt_ok.setText("上架销售");
        }

        ed_title_name.setText(detailsModel.getShop_product_title());
        tv_leimu.setText(detailsModel.getItem_name());
        tv_chandi.setText(detailsModel.getPlace_name());
        //  tv_yunfei.setText(detailsModel.getWares_money_go());

        String is_installable = detailsModel.getIs_installable();

        if (!TextUtils.isEmpty(is_installable)) {
            if (is_installable.equals("1")) {
                ll_anzhuangfuwu.setVisibility(View.VISIBLE);
                tv_anzhuangfei.setText(detailsModel.getInstall_money());
                iv_swich.setImageResource(R.mipmap.swich_on);
            } else {
                ll_anzhuangfuwu.setVisibility(View.GONE);
                iv_swich.setImageResource(R.mipmap.swich_off);
                tv_anzhuangfei.setText("");
            }
        } else {
            ll_anzhuangfuwu.setVisibility(View.GONE);
            iv_swich.setImageResource(R.mipmap.swich_off);
            tv_anzhuangfei.setText("");
        }

        package_list = detailsModel.getPackage_list();
        ziAdapter.setNewData(package_list);
        ziAdapter.notifyDataSetChanged();

        img_list = detailsModel.getImg_list();
        imgText_list.clear();
        if (img_list != null) {
            if (img_list.size() > 0) {
                rv_img_add.setVisibility(View.VISIBLE);
                iv_add.setVisibility(View.GONE);
            } else {
                rv_img_add.setVisibility(View.GONE);
                iv_add.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < img_list.size(); i++) {
                if (i < 3) {
                    imgText_list.add(img_list.get(i));
                }
            }
            tv_img_num.setText(img_list.size() + "张");
        }


        topAdapter.setNewData(imgText_list);
        topAdapter.notifyDataSetChanged();

    }


    private void getChandi() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_00005);
        map.put("key", Urls.KEY);
        map.put("type_id", "province_city_all");//物流公司
        Gson gson = new Gson();
        OkGo.<AppResponse<ChandiModel.DataBean>>post(Urls.MSG)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ChandiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ChandiModel.DataBean>> response) {
                        chandiModels = response.body().data;
                        initChandi();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }

    private void getLeimu() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04193);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<LeimuModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<LeimuModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<LeimuModel.DataBean>> response) {
                        leimuModels = response.body().data;
                        initLeimu();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }


    private void showSelectLeimu() {
        if (leimuPicker == null) {
            showProgressDialog();
            getLeimu();
        } else {
            leimuPicker.show();
        }
    }

    private void initLeimu() {
        List<Object> names1 = new ArrayList<>();
        List<List<Object>> names2 = new ArrayList<>();
        List<List<List<Object>>> names3 = new ArrayList<>();
        for (int i = 0; i < leimuModels.size(); i++) {
            LeimuModel.DataBean bean = leimuModels.get(i);
            names1.add(bean.getItem_name());

            List<LeimuModel.DataBean.NextLevelBeanX> next_level = bean.getNext_level();
            List<Object> names2Beans = new ArrayList<>();
            List<List<Object>> names3BeansBeans = new ArrayList<>();
            for (int j = 0; j < next_level.size(); j++) {
                LeimuModel.DataBean.NextLevelBeanX nextLevelBeanX = next_level.get(j);
                names2Beans.add(nextLevelBeanX.getItem_name());

                List<LeimuModel.DataBean.NextLevelBeanX.NextLevelBean> next_level1 = nextLevelBeanX.getNext_level();
                List<Object> names3Beans = new ArrayList<>();
                for (int k = 0; k < next_level1.size(); k++) {
                    names3Beans.add(next_level1.get(k).getItem_name());
                }
                names3BeansBeans.add(names3Beans);
            }
            names2.add(names2Beans);
            names3.add(names3BeansBeans);
        }

        //条件选择器
        leimuPicker = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (leimuModels != null && leimuModels.size() > 0) {
                    LeimuModel.DataBean dataBean = leimuModels.get(options1);
                    String is_install = dataBean.getIs_install();
                    detailsModel.setIs_installable("2");
                    iv_swich.setImageResource(R.mipmap.swich_off);
                    tv_anzhuangfei.setText("");
                    if (is_install.equals("1")) {
                        ll_anzhuangfuwu.setVisibility(View.VISIBLE);
                    } else {
                        ll_anzhuangfuwu.setVisibility(View.GONE);
                    }

                    item_id_one = dataBean.getItem_id();
                    item_id_one_name = dataBean.getItem_name();
                    List<LeimuModel.DataBean.NextLevelBeanX> next_level = dataBean.getNext_level();
                    if (next_level != null && next_level.size() > 0) {
                        LeimuModel.DataBean.NextLevelBeanX nextLevelBeanX = next_level.get(option2);
                        item_id_two = nextLevelBeanX.getItem_id();
                        item_id_two_name = nextLevelBeanX.getItem_name();
                        List<LeimuModel.DataBean.NextLevelBeanX.NextLevelBean> next_level1 = nextLevelBeanX.getNext_level();
                        if (next_level1 != null && next_level1.size() > 0) {
                            LeimuModel.DataBean.NextLevelBeanX.NextLevelBean nextLevelBean = next_level1.get(options3);
                            item_id_three = nextLevelBean.getItem_id();
                            item_id_three_name = nextLevelBean.getItem_name();

                            tv_leimu.setText(item_id_one_name + "-" + item_id_two_name + "-" + item_id_three_name);
                        } else {
                            item_id_three = "";
                            item_id_three_name = "";
                            tv_leimu.setText(item_id_one_name + "-" + item_id_two_name);
                        }
                    } else {
                        item_id_two = "";
                        item_id_two_name = "";
                        tv_leimu.setText(item_id_one_name);
                    }
                } else {
                    item_id_one = "";
                    item_id_one_name = "";
                    tv_leimu.setText("");
                }

                addShangpin(type_leimu);
            }
        }).build();
        leimuPicker.setPicker(names1, names2, names3);
        leimuPicker.show();
    }

    private void showSelectChandi() {
        if (chandiPicker == null) {
            showProgressDialog();
            getChandi();
        } else {
            chandiPicker.show();
        }
    }


    private void initChandi() {
        List<Object> names1 = new ArrayList<>();
        List<List<Object>> names2 = new ArrayList<>();
        for (int i = 0; i < chandiModels.size(); i++) {
            ChandiModel.DataBean bean = chandiModels.get(i);
            names1.add(bean.getCode_name());

            List<ChandiModel.DataBean.CityBean> city = bean.getCity();
            List<Object> names2Beans = new ArrayList<>();
            for (int j = 0; j < city.size(); j++) {
                ChandiModel.DataBean.CityBean cityBean = city.get(j);
                names2Beans.add(cityBean.getCode_name());
            }
            names2.add(names2Beans);
        }

        //条件选择器
        chandiPicker = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                ChandiModel.DataBean dataBean = chandiModels.get(options1);
                ChandiModel.DataBean.CityBean cityBean = dataBean.getCity().get(option2);

                province_name_pro = dataBean.getCode_name();
                province_id_pro = dataBean.getCode_id();
                city_name_pro = cityBean.getCode_name();
                city_id_pro = cityBean.getCode_id();

                tv_chandi.setText(province_name_pro + "-" + city_name_pro);
                addShangpin(type_chandi);
            }
        }).build();
        chandiPicker.setPicker(names1, names2);
        chandiPicker.show();
    }

    @OnClick({R.id.iv_add, R.id.tv_img_num, R.id.ed_title_name, R.id.ll_leimu, R.id.iv_swich, R.id.ll_anzhuangfei, R.id.ll_yunfei, R.id.ll_chandi, R.id.ll_add_shangpinhao, R.id.ll_tuwen, R.id.ll_fengmian, R.id.bt_ok, R.id.ll_bianhao, R.id.ll_kucun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
            case R.id.tv_img_num:
                clickBanner();
                break;
            case R.id.ed_title_name:
                clickName();
                break;
            case R.id.ll_leimu:
                //showSelectLeimu();
                TianJiaLeiMuActivity.actionStart(mContext);
                break;
            case R.id.iv_swich:
                swich();
                break;
            case R.id.ll_anzhuangfei:
                clickAnzhuangfei();
                break;
            case R.id.ll_yunfei:
                clickYunfei();
                break;
            case R.id.ll_chandi:
                showSelectChandi();
                break;
            case R.id.ll_add_shangpinhao:
                ShangpinZiAddActivity.actionStart(mContext, detailsModel.getWares_id());
                break;
            case R.id.ll_tuwen:
                clickTuwen();
                break;
            case R.id.ll_fengmian:
                clickFengmian();
                break;
            case R.id.bt_ok:
                clickOK();
                break;
            case R.id.ll_bianhao:
                clickShangPinBianHao();
                break;
            case R.id.ll_kucun:
                clickShangPinKuCun();
                break;
        }
    }


    private void clickShangPinBianHao() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入商品编号");
                } else {
                    tvBianhao.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin(type_shangpin_bianhao);
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入商品编号");
        dialog.setTextContent(tvBianhao.getText().toString());
        dialog.show();
    }

    private void clickShangPinKuCun() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入商品库存");
                } else {
                    tvKucun.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin(type_shangpin_kucun);
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入商品库存");
        dialog.setTextContent(tvKucun.getText().toString());
        dialog.show();
    }


    private void clickFengmian() {
        Intent intent = new Intent();
        intent.setClass(this, ShangpinFenmianActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        intent.putExtra("url", detailsModel.getWares_photo_url());
        intent.putExtra("isEdit", true);
        startActivity(intent);
    }

    private void clickTuwen() {
        Intent intent = new Intent();
        intent.setClass(this, ShangpinImgxiangActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        startActivity(intent);
    }

    private void clickBanner() {
        Intent intent = new Intent();
        intent.setClass(this, ShangpinBannerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wares_id", wares_id);
        intent.putExtra("isEdit", true);
        startActivity(intent);
    }

    private void clickOK() {
        String text = bt_ok.getText().toString();
        if (text.equals("上架销售")) {
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
        map.put("wares_id", detailsModel.getWares_id());
        map.put("dif_type", dif_type);
        map.put("type", type);//商品：下架传1 上架传2  删除传3

        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        String text = bt_ok.getText().toString();
                        if (text.equals("上架销售")) {
                            bt_ok.setText("放入仓库");
                        } else {
                            bt_ok.setText("上架销售");
                        }

                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(ShangpinEditActivity.this);
                        dialog.show();
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


    private void swich() {
        if (detailsModel.getIs_installable().equals("1")) {
            detailsModel.setIs_installable("2");
            iv_swich.setImageResource(R.mipmap.swich_off);
            ll_anzhuangfei.setVisibility(View.GONE);
            tv_anzhuangfei.setText("");
            addShangpin(type_anzhuangfei);
        } else {
            detailsModel.setIs_installable("1");
            iv_swich.setImageResource(R.mipmap.swich_on);
            ll_anzhuangfei.setVisibility(View.VISIBLE);
        }
    }

    private void clickName() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入商品标题");
                } else {
                    ed_title_name.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin(type_name);
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入商品标题");
        dialog.setTextContent(ed_title_name.getText().toString());
        dialog.show();
    }

    private void clickAnzhuangfei() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请设置安装费");
                } else {
                    linshianzhuang = tv_anzhuangfei.getText().toString();
                    tv_anzhuangfei.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin(type_anzhuangfei);
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.setTextTitle("请设置安装费");
        dialog.setTextContent(tv_anzhuangfei.getText().toString());
        dialog.show();
    }

    private void clickYunfei() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请设置商品价格");
                } else {
                    linshiYunfei = tv_yunfei.getText().toString();
                    tv_yunfei.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin(type_yunfei);
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.setTextTitle("请设置商品价格");
        dialog.setTextContent(tv_yunfei.getText().toString());
        dialog.show();
    }


    /**
     * map.put("enter_type", enter_type);
     * map.put("wares_name", wares_name);
     * map.put("item_id_one", item_id_one);
     * map.put("item_id_one_name", item_id_one_name);
     * map.put("shop_money_now", wares_money_go);//运费变成价格
     * map.put("wares_number", bianHao);//编号
     * map.put("wares_count", kuCun);//库存
     *
     * @param type
     */
    private void addShangpin(int type) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04179);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("enter_type", enter_type);
        map.put("wares_id", detailsModel.getWares_id());

        if (type == type_name) {
            map.put("wares_name", ed_title_name.getText().toString());
        } else if (type == type_yunfei) {
            map.put("shop_money_now", tv_yunfei.getText().toString()); //运费变成价格
        } else if (type == type_shangpin_bianhao) {
            map.put("wares_number", tvBianhao.getText().toString().trim());
        } else if (type == type_shangpin_kucun) {
            map.put("wares_count", tvKucun.getText().toString().trim());
        }
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(ShangpinEditActivity.this);
                        dialog.show();
                    }

                    @Override
                    public void onError(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                        if (type == type_yunfei) {
                            tv_yunfei.setText(linshiYunfei);
                        } else if (type == type_anzhuangfei) {
                            tv_anzhuangfei.setText(linshianzhuang);
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
                });
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04322);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", detailsModel.getWares_id());
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        Y.e("克劳福德静电纺丝李开复所发生的唠嗑的见风使舵");
                        detailsModel = response.body().data.get(0);
                        detailsModel.setWares_id(wares_id);
                        initView();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }


    private void back() {
        Notice n = new Notice();
        if (isDetails) {
            n.type = ConstanceValue.shangpin_details_use;
        } else {
            n.type = ConstanceValue.shangpin_frag;
        }
        RxBus.getDefault().sendRx(n);
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        back();
    }
}
