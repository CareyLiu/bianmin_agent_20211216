package com.shendeng_bianmin.agent.ui.fragment;


import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.OrderAdapter;
import com.shendeng_bianmin.agent.adapter.OrderNewAdapter;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.BottomDialog;
import com.shendeng_bianmin.agent.dialog.BottomDialogView;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_Success;
import com.shendeng_bianmin.agent.model.OrderModel;
import com.shendeng_bianmin.agent.model.WaresListBean;
import com.shendeng_bianmin.agent.ui.activity.BianMinDingDanXiangQingActivity;
import com.shendeng_bianmin.agent.ui.activity.DefaultX5WebViewActivity;
import com.shendeng_bianmin.agent.ui.activity.OrderDetailsActivity;
import com.shendeng_bianmin.agent.ui.activity.OrderFahuoActivity;
import com.shendeng_bianmin.agent.ui.activity.OrderPingjiaActivity;
import com.shendeng_bianmin.agent.ui.activity.OrderSaoyisaoActivity;
import com.shendeng_bianmin.agent.ui.activity.OrderTuikuanActivity;
import com.shendeng_bianmin.agent.ui.view.SelectTabView;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BottomDingDanFragment extends BaseFragment {
    public static final String TAG = "BottomDingDanFragment";
    @BindView(R.id.tab_all)
    SelectTabView tab_all;
    @BindView(R.id.tab_daifukuan)
    SelectTabView tab_daifukuan;
    @BindView(R.id.tab_daifahuo)
    SelectTabView tab_daifahuo;
    @BindView(R.id.tab_daishouhuo)
    SelectTabView tab_daishouhuo;
    @BindView(R.id.tab_xiaofei)
    SelectTabView tab_xiaofei;
    @BindView(R.id.tab_daipingjia)
    SelectTabView tab_daipingjia;
    @BindView(R.id.tab_yipingjia)
    SelectTabView tab_yipingjia;
    @BindView(R.id.tab_tuikuanshenqing)
    SelectTabView tab_tuikuanshenqing;
    @BindView(R.id.tab_tuikuanzhong)
    SelectTabView tab_tuikuanzhong;
    @BindView(R.id.tab_guanbi)
    SelectTabView tab_guanbi;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_saoyisao)
    ImageView iv_saoyisao;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private int shop_pay_check;
    private String form_id;
    private List<WaresListBean> data = new ArrayList<>();
    private OrderNewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {

            }
        }));
    }


    public static BottomDingDanFragment newInstance() {
        Bundle args = new Bundle();
        BottomDingDanFragment fragment = new BottomDingDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frag_mian_order;
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initLogic() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        tab_all.setTitle("全部");
        tab_daifukuan.setTitle("待付款");
        tab_daifahuo.setTitle("待发货");
        tab_daishouhuo.setTitle("待收货");
        tab_xiaofei.setTitle("到店订单");
        tab_daipingjia.setTitle("待评价");
        tab_yipingjia.setTitle("已完成");
        tab_tuikuanshenqing.setTitle("退款申请");
        tab_tuikuanzhong.setTitle("退款中");
        tab_guanbi.setTitle("已关闭");

        initAdapter();
        initSM();

        selectTab(0);
    }

    /**
     * shop_pay_check 状态：0.全部1.待付款3.待发货4.待收货5.到店消费6.待评价 7.已评价 8.退款申请 9.退款中 10.已关闭
     */
    private void selectTab(int shop_pay_check) {
        this.shop_pay_check = shop_pay_check;

        tab_all.setSelect(false);
        tab_daifukuan.setSelect(false);
        tab_daifahuo.setSelect(false);
        tab_daishouhuo.setSelect(false);
        tab_xiaofei.setSelect(false);
        tab_daipingjia.setSelect(false);
        tab_yipingjia.setSelect(false);
        tab_tuikuanshenqing.setSelect(false);
        tab_tuikuanzhong.setSelect(false);
        tab_guanbi.setSelect(false);
        switch (shop_pay_check) {
            case 0:
                tab_all.setSelect(true);
                break;
            case 1:
                tab_daifukuan.setSelect(true);
                break;
            case 3:
                tab_daifahuo.setSelect(true);
                break;
            case 4:
                tab_daishouhuo.setSelect(true);
                break;
            case 5:
                tab_xiaofei.setSelect(true);
                break;
            case 6:
                tab_daipingjia.setSelect(true);
                break;
            case 7:
                tab_yipingjia.setSelect(true);
                break;
            case 8:
                tab_tuikuanshenqing.setSelect(true);
                break;
            case 9:
                tab_tuikuanzhong.setSelect(true);
                break;
            case 10:
                tab_guanbi.setSelect(true);
                break;
        }

        showProgressDialog("");
        data.clear();
        getOrder(shop_pay_check);
    }

    private void initAdapter() {
        adapter = new OrderNewAdapter(R.layout.item_order_mian_content, R.layout.item_order_mian_header, data);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_bt:
                        if (data != null && data.size() > 0) {
                            WaresListBean dataBean = data.get(position);
                            String shop_form_id = dataBean.shop_form_id;
                            String shop_pay_check = dataBean.shop_pay_check;

                            if (shop_pay_check.equals("3")) {//去发货
                                //   OrderFahuoActivity.actionStart(getContext(), shop_form_id);

                                TishiDialog tishiDialog = new TishiDialog(getActivity(), new TishiDialog.TishiDialogListener() {
                                    @Override
                                    public void onClickCancel(View v, TishiDialog dialog) {

                                    }

                                    @Override
                                    public void onClickConfirm(View v, TishiDialog dialog) {
                                        getFaHuoNet(shop_form_id);
                                    }

                                    @Override
                                    public void onDismiss(TishiDialog dialog) {

                                    }
                                });
                                tishiDialog.setTextCont("是否执行发货？");
                                tishiDialog.show();


                            } else if (shop_pay_check.equals("4")) {//查看物流
//                                String express_url = dataBean.express_url;
//                                if (TextUtils.isEmpty(express_url)) {
//                                    Y.t("暂无物流详情");
//                                } else {
//                                    DefaultX5WebViewActivity.actionStart(getContext(), express_url);
//                                }

                                TishiDialog tishiDialog = new TishiDialog(getActivity(), new TishiDialog.TishiDialogListener() {
                                    @Override
                                    public void onClickCancel(View v, TishiDialog dialog) {

                                    }

                                    @Override
                                    public void onClickConfirm(View v, TishiDialog dialog) {
                                        getQueRenFaHuo(shop_form_id);
                                    }

                                    @Override
                                    public void onDismiss(TishiDialog dialog) {

                                    }
                                });
                                tishiDialog.setTextCont("确认货物已送到？");
                                tishiDialog.show();

                            } else if (shop_pay_check.equals("6")) {//去评价
//                                OrderPingjiaActivity.actionStart(getContext(), shop_form_id);
                            } else if (shop_pay_check.equals("7")) {//查看详情
//                                OrderPingjiaActivity.actionStart(getContext(), shop_form_id);
                            } else if (shop_pay_check.equals("8")) {//退款审核
                                showBottom(shop_form_id);
                            }
                        }
                        break;
                }
            }


        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (data != null && data.size() > 0) {
//                    WaresListBean dataBean = data.get(position);
//                    String shop_form_id = dataBean.form_id;
//                    String shop_pay_check = dataBean.shop_pay_check;
//                    if (shop_pay_check.equals("8") || shop_pay_check.equals("9") || shop_pay_check.equals("10") || shop_pay_check.equals("12")) {//退款
//                        OrderTuikuanActivity.actionStart(getContext(), shop_form_id);
//                    } else {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("shop_form_id", shop_form_id);
//                        OrderDetailsActivity.actionStart(getContext(), bundle);
//                    }

                    BianMinDingDanXiangQingActivity.actionStart(getActivity(), data.get(position).shop_form_id);
                }
            }
        });
    }

    private void getFaHuoNet(String shop_form_id) {

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04407);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        smartRefreshLayout.autoRefresh();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();


                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    private void getQueRenFaHuo(String shop_form_id) {

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04408);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        smartRefreshLayout.autoRefresh();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                        if (data.size() > 0) {
                            form_id = BottomDingDanFragment.this.data.get(BottomDingDanFragment.this.data.size() - 1).form_id;
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    private void showBottom(String shop_form_id) {
        List<String> names = new ArrayList<>();
        names.add("同意退款");
        names.add("拒绝退款");
        final BottomDialog bottomDialog = new BottomDialog(getActivity());
        bottomDialog.setModles(names);
        bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
            @Override
            public void onClickItem(int pos) {
                bottomDialog.dismiss();
                if (pos == 0) {
                    tuikuanshenhe("2", shop_form_id);
                } else {
                    tuikuanshenhe("6", shop_form_id);
                }
            }

            @Override
            public void onClickCancel(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.showBottom();
    }

    private void tuikuanshenhe(String refund_rate, String shop_form_id) {//refund_rate  审核结果：2同意6拒绝
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04315);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getActivity()).getAppToken());
        map.put("shop_form_id", shop_form_id);
        map.put("refund_rate", refund_rate);
        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        MyCarCaoZuoDialog_Success dialog = new MyCarCaoZuoDialog_Success(getActivity(), new MyCarCaoZuoDialog_Success.OnDialogItemClickListener() {
                            @Override
                            public void onDismiss() {
                                getOrder(shop_pay_check);
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onError(Response<AppResponse> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                data.clear();
                getOrder(shop_pay_check);
            }
        });


        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getLoad(shop_pay_check);
            }
        });
    }

    private List<WaresListBean> zhengHeLei(Response<AppResponse<OrderModel.DataBean>> response) {


        for (int i = 0; i < response.body().data.size(); i++) {

            WaresListBean waresListBean = new WaresListBean(true, "header");

//                            public String pay_count;
//                            public String user_accid;
//                            public String user_name;
//                            public String form_id;
//                            public String user_img_url;
//                            public String pay_money;
//                            public String shop_pay_check;
//                            public String shop_pay_check_name;
//                            public String express_url;
//                            public String wares_type;
//                            public String receiver_text;
//                            public String receiver_name;
//                            public String user_addr_all;
//                            public String receiver_phone;
            OrderModel.DataBean dataBean = response.body().data.get(i);
            waresListBean.receiver_name = dataBean.getReceiver_name();
            waresListBean.express_url = dataBean.getExpress_url();
            waresListBean.form_id = dataBean.getForm_id();
            waresListBean.user_img_url = dataBean.getUser_img_url();
            waresListBean.pay_money = dataBean.getPay_money();
            waresListBean.shop_pay_check = dataBean.getShop_pay_check();
            waresListBean.shop_pay_check_name = dataBean.getShop_pay_check_name();
            waresListBean.express_url = dataBean.getExpress_url();
            waresListBean.wares_type = dataBean.getWares_type();
            waresListBean.receiver_text = dataBean.getReceiver_text();
            waresListBean.receiver_name = dataBean.getReceiver_name();
            waresListBean.user_addr_all = dataBean.getUser_addr_all();
            waresListBean.receiver_phone = dataBean.getReceiver_phone();
            waresListBean.user_name = dataBean.getUser_name();
            waresListBean.shop_form_id = dataBean.shop_form_id;

            data.add(waresListBean);
            for (int j = 0; j < response.body().data.get(i).getWares_list().size(); j++) {
                OrderModel.DataBean.WaresListBean productBean = response.body().data.get(i).getWares_list().get(j);
                WaresListBean waresListBean1 = new WaresListBean(false, "header");
                /**
                 *     public String wares_id;
                 *     public String shop_product_title;
                 *     public String pay_count_jutishangpin;
                 *     public String index_photo_url;
                 *     public String form_product_money;
                 */

                waresListBean1.receiver_name = dataBean.getReceiver_name();
                waresListBean1.express_url = dataBean.getExpress_url();
                waresListBean1.form_id = dataBean.getForm_id();
                waresListBean1.user_img_url = dataBean.getUser_img_url();
                waresListBean1.pay_money = dataBean.getPay_money();
                waresListBean1.shop_pay_check = dataBean.getShop_pay_check();
                waresListBean1.shop_pay_check_name = dataBean.getShop_pay_check_name();
                waresListBean1.express_url = dataBean.getExpress_url();
                waresListBean1.wares_type = dataBean.getWares_type();
                waresListBean1.receiver_text = dataBean.getReceiver_text();
                waresListBean1.receiver_name = dataBean.getReceiver_name();
                waresListBean1.user_addr_all = dataBean.getUser_addr_all();
                waresListBean1.receiver_phone = dataBean.getReceiver_phone();
                waresListBean1.shop_form_id = dataBean.shop_form_id;

                waresListBean1.wares_id = productBean.getWares_id();
                waresListBean1.shop_product_title = productBean.getShop_product_title();
                waresListBean1.pay_count_jutishangpin = productBean.getPay_count();
                waresListBean1.index_photo_url = productBean.getIndex_photo_url();
                waresListBean1.form_product_money = productBean.getForm_product_money();
                waresListBean1.pay_count = productBean.getPay_count();

                if (j == response.body().data.get(i).getWares_list().size() - 1) {
                    waresListBean1.zuiHouYige = "1";
                }
                data.add(waresListBean1);
            }

        }
        return data;
    }

    private void getOrder(int shop_pay_check) {

        form_id = "";
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04405);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_pay_check", shop_pay_check + "");
        map.put("form_id", form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<OrderModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderModel.DataBean>> response) {
                        if (response.body().data.size() == 0) {
                            return;
                        }

                        adapter.setNewData(zhengHeLei(response));
                        adapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();

                        if (data.size() > 0) {
                            form_id = BottomDingDanFragment.this.data.get(BottomDingDanFragment.this.data.size() - 1).form_id;
                            ll_no_data.setVisibility(View.GONE);
                            smartRefreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                            smartRefreshLayout.setVisibility(View.GONE);
                        }


                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }


    private void getLoad(int shop_pay_check) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04405);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_pay_check", shop_pay_check + "");
        map.put("form_id", form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<OrderModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderModel.DataBean>> response) {
                        List<OrderModel.DataBean> dataNew = response.body().data;
                        if (response.body().data.size() == 0) {
                            return;
                        } else {
                            data.addAll(zhengHeLei(response));
                            adapter.setNewData(BottomDingDanFragment.this.data);
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (data.size() > 0) {
                            form_id = BottomDingDanFragment.this.data.get(BottomDingDanFragment.this.data.size() - 1).form_id;
                            ll_no_data.setVisibility(View.GONE);
                            smartRefreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                            smartRefreshLayout.setVisibility(View.GONE);
                        }


                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void ewm() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) { // 在android 6.0之前会默认返回true
                    OrderSaoyisaoActivity.actionStartForResult(getActivity(), 100);
                } else {
                    Y.tLong("该应用需要赋予访问相机的权限，不开启将无法正常工作！");
                }
            }
        });
    }

    @Override
    protected void immersionInit(ImmersionBar mImmersionBar) {
        mImmersionBar
                .titleBar(toolbar).fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    protected boolean immersionEnabled() {
        return true;
    }


    @OnClick({R.id.iv_saoyisao, R.id.tab_all, R.id.tab_daifukuan, R.id.tab_daifahuo, R.id.tab_daishouhuo, R.id.tab_xiaofei, R.id.tab_daipingjia, R.id.tab_yipingjia, R.id.tab_tuikuanshenqing, R.id.tab_tuikuanzhong, R.id.tab_guanbi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_saoyisao:
                ewm();
                // FeedBackActivity.actionStart(getActivity());
                break;
            case R.id.tab_all:
                selectTab(0);
                break;
            case R.id.tab_daifukuan:
                selectTab(1);
                break;
            case R.id.tab_daifahuo:
                selectTab(3);
                break;
            case R.id.tab_daishouhuo:
                selectTab(4);
                break;
            case R.id.tab_xiaofei:
                selectTab(5);
                break;
            case R.id.tab_daipingjia:
                selectTab(6);
                break;
            case R.id.tab_yipingjia:
                selectTab(7);
                break;
            case R.id.tab_tuikuanshenqing:
                selectTab(8);
                break;
            case R.id.tab_tuikuanzhong:
                selectTab(9);
                break;
            case R.id.tab_guanbi:
                selectTab(10);
                break;
        }
    }
}
