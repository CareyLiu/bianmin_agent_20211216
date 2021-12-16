package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TaoCanGuanLiHomeAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.TaoCanListModel;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TaoCanGuanLi_HomeActivity extends BaseActivity {

    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    TaoCanGuanLiHomeAdapter taoCanGuanLiHomeAdapter;
    List<TaoCanListModel.DataBean.TaocanListBean> mDatas;
    @BindView(R.id.tv_chushouzhong)
    TextView tvChushouzhong;
    @BindView(R.id.tv_yixiajia)
    TextView tvYixiajia;
    @BindView(R.id.tv_xinjian_taocan)
    TextView tvXinjianTaocan;
    private String wares_id = "";
    private String str = "1";//1出售中 2 已下架

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        srLSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                wares_id = "";
                getDetails();
            }
        });

        srLSmart.setEnableLoadMore(false);
        mDatas = new ArrayList<>();


        taoCanGuanLiHomeAdapter = new TaoCanGuanLiHomeAdapter(R.layout.item_taocan_guanli, mDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rlvList.setLayoutManager(linearLayoutManager);

        rlvList.setAdapter(taoCanGuanLiHomeAdapter);


        tvChushouzhong.setText("出售中(" + mDatas.size() + ")");
        tvYixiajia.setText("已下架(" + mDatas.size() + ")");


        tvChushouzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvChushouzhong.setTextColor(mContext.getResources().getColor(R.color.FC0100));
                tvYixiajia.setTextColor(mContext.getResources().getColor(R.color.gray999999));


                str = "1";
                wares_id = "";
                getDetails();
            }
        });
        tvYixiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYixiajia.setTextColor(mContext.getResources().getColor(R.color.FC0100));
                tvChushouzhong.setTextColor(mContext.getResources().getColor(R.color.gray999999));
                str = "2";
                wares_id = "";
                getDetails();
            }
        });

        taoCanGuanLiHomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.constrain:


                        if (taoCanGuanLiHomeAdapter.getData().get(position).getWares_state().equals("1")) {//出售中
                            TaoCanDetailsActivity.actionStart(mContext, mDatas.get(position).getWares_id());
                        } else {
                            TaoCanDetailsActivity.actionStart(mContext, mDatas.get(position).getWares_id());
                        }


                        break;
                }
            }
        });




        tvXinjianTaocan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TianJiaTaoCanActivity.actionStart(mContext, "0", "1", null);
            }
        });

        getDetails();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetails();
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_tao_can_guan_li__home;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("套餐管理");
        tv_title.setTextColor(getResources().getColor(R.color.black));
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, TaoCanGuanLi_HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04202);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("wares_id", wares_id);
        map.put("wares_state", str);
        Gson gson = new Gson();
        OkGo.<AppResponse<TaoCanListModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TaoCanListModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TaoCanListModel.DataBean>> response) {


                        taoCanGuanLiHomeAdapter.removeAllHeaderView();
                        if (StringUtils.isEmpty(wares_id)) {
                            mDatas.clear();
                            mDatas.addAll(response.body().data.get(0).getTaocan_list());

                            taoCanGuanLiHomeAdapter.notifyDataSetChanged();
                        } else {
                            mDatas.addAll(response.body().data.get(0).getTaocan_list());
                            taoCanGuanLiHomeAdapter.notifyDataSetChanged();
                        }
                        if (mDatas.size() == 0) {

                            View view = View.inflate(mContext, R.layout.layout_zanwushuju, null);
                            taoCanGuanLiHomeAdapter.setEmptyView(view);
                        }

                        tvChushouzhong.setText("出售中(" + response.body().data.get(0).getBuyed_count() + ")");
                        tvYixiajia.setText("已下架(" + response.body().data.get(0).getPull_off_count() + ")");

                        srLSmart.finishRefresh();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TaoCanListModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog("");
                    }
                });
    }
}
