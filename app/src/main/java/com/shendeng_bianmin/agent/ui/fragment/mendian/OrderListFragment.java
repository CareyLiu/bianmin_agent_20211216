package com.shendeng_bianmin.agent.ui.fragment.mendian;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TuanGouDingDanListAdapter;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.tuangou.TOrderModel;
import com.shendeng_bianmin.agent.ui.activity.tuangou.TuanGouDingDanDetails;
import com.shendeng_bianmin.agent.ui.activity.tuangou.TuanOrderTuikuanActivity;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class OrderListFragment extends BaseFragment {

    @BindView(R.id.rlv_list)
    RecyclerView rlv_list;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private String shop_pay_check;
    private String form_id;
    private List<TOrderModel.DataBean> orderList = new ArrayList<>();
    private TuanGouDingDanListAdapter tuanGouDingDanListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLogic() {
        getData();
        getOrder();
        initAdapter();
        initSM();
    }

    private void initAdapter() {
        tuanGouDingDanListAdapter = new TuanGouDingDanListAdapter(R.layout.item_tuangou_dingdan, orderList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlv_list.setLayoutManager(linearLayoutManager);
        rlv_list.setAdapter(tuanGouDingDanListAdapter);
        tuanGouDingDanListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.constrain:
                        if (orderList != null && orderList.size() > position) {

                            TOrderModel.DataBean dataBean = orderList.get(position);
                            String shop_pay_check = dataBean.getShop_pay_check();
                            String shop_form_id = dataBean.getShop_form_id();

                            if (shop_pay_check.equals("8") || shop_pay_check.equals("9") || shop_pay_check.equals("10") || shop_pay_check.equals("12")) {
                                TuanOrderTuikuanActivity.actionStart(getActivity(), shop_form_id);
                            } else {
                                TuanGouDingDanDetails.actionStart(getActivity(), shop_form_id);
                            }


                        }
                        break;
                }
            }
        });
    }

    private void getData() {
        String title = getArguments().getString("title");
        if (title.equals("待付款")) {
            shop_pay_check = "1";
        } else if (title.equals("到店消费")) {
            shop_pay_check = "5";
        } else if (title.equals("待评价")) {
            shop_pay_check = "6";
        } else if (title.equals("已评价")) {
            shop_pay_check = "7";
        } else if (title.equals("退款")) {
            shop_pay_check = "8";
        } else if (title.equals("退款中")) {
            shop_pay_check = "9";
        } else if (title.equals("已关闭")) {
            shop_pay_check = "10";
        } else {
            shop_pay_check = "0";
        }

    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getOrder();
            }
        });


        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getLoad();
            }
        });
    }

    private void getOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04200);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_pay_check", shop_pay_check);
        map.put("shop_form_id", "");
        Gson gson = new Gson();
        OkGo.<AppResponse<TOrderModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TOrderModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TOrderModel.DataBean>> response) {
                        orderList = response.body().data;
                        if (orderList != null && orderList.size() > 0) {
                            form_id = orderList.get(orderList.size() - 1).getShop_form_id();
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                        tuanGouDingDanListAdapter.setNewData(orderList);
                        tuanGouDingDanListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }


    private void getLoad() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04200);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_pay_check", shop_pay_check);
        map.put("shop_form_id", form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<TOrderModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TOrderModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TOrderModel.DataBean>> response) {
                        List<TOrderModel.DataBean> data = response.body().data;
                        orderList.addAll(data);
                        if (orderList != null && orderList.size() > 0) {
                            form_id = orderList.get(orderList.size() - 1).getShop_form_id();
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                        tuanGouDingDanListAdapter.setNewData(orderList);
                        tuanGouDingDanListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishLoadMore();
                    }
                });
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_order_list;
    }

    @Override
    protected void initView(View rootView) {

    }
}
