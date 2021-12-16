package com.shendeng_bianmin.agent.ui.activity.yuangong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class YuangongActivity extends BaseActivity {

    @BindView(R.id.tv_zaizhi)
    TextView tv_zaizhi;
    @BindView(R.id.tv_lizhi)
    TextView tv_lizhi;
    @BindView(R.id.rv_yuangong)
    RecyclerView rv_yuangong;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    private String of_user_id;
    private String subsystem_id;
    private String inst_id;
    private String sub_state;
    private List<YuangongModel.DataBean> yuangongModels = new ArrayList<>();
    private YuangongAdapter adapter;

    @Override
    public int getContentViewResId() {
        return R.layout.act_yuangong;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("员工管理");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String of_user_id, String inst_id, String subsystem_id) {
        Intent intent = new Intent();
        intent.setClass(context, YuangongActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("of_user_id", of_user_id);
        intent.putExtra("inst_id", inst_id);
        intent.putExtra("subsystem_id", subsystem_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        inst_id = getIntent().getStringExtra("inst_id");
        of_user_id = getIntent().getStringExtra("of_user_id");
        subsystem_id = getIntent().getStringExtra("subsystem_id");
        sub_state = "1";

        initAdapter();
        initSM();
        getNet();

        initHuidiao();
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.ADD_YUANGONG) {
                    getNet();
                }
            }
        }));
    }

    private void initAdapter() {
        adapter = new YuangongAdapter(R.layout.item_yuangong, yuangongModels);
        rv_yuangong.setLayoutManager(new LinearLayoutManager(mContext));
        rv_yuangong.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (yuangongModels != null && yuangongModels.size() > position) {
                    YuangongModel.DataBean dataBean = yuangongModels.get(position);
                    YuangongEditActivity.actionStart(mContext,
                            of_user_id,
                            dataBean.getInst_id(),
                            dataBean.getSubsystem_id(),
                            dataBean.getUser_phone(),
                            dataBean.getUser_name(),
                            dataBean.getState_name(),
                            dataBean.getSub_user_no(),
                            dataBean.getBranch_id(),
                            dataBean.getRole_id(),
                            dataBean.getBranch_name(),
                            dataBean.getRole_name(),
                            dataBean.getSub_user_id());
                }
            }
        });
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04219);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("of_user_id", of_user_id);
        map.put("subsystem_id", subsystem_id);
        map.put("inst_id", inst_id);
        map.put("sub_state", sub_state);
        Gson gson = new Gson();
        OkGo.<AppResponse<YuangongModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<YuangongModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<YuangongModel.DataBean>> response) {
                        yuangongModels = response.body().data;
                        if (yuangongModels.size() > 0) {
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                        adapter.setNewData(yuangongModels);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<YuangongModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onError(Response<AppResponse<YuangongModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                    }
                });
    }

    @OnClick({R.id.tv_zaizhi, R.id.tv_lizhi, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zaizhi:
                clickZaizhi();
                break;
            case R.id.tv_lizhi:
                clickLizhi();
                break;
            case R.id.iv_add:
                YuangongAddActivity.actionStart(mContext, of_user_id, inst_id, subsystem_id);
                break;
        }
    }

    private void clickLizhi() {
        sub_state = "2";
        getNet();

        tv_lizhi.setTextColor(Y.getColor(R.color.text_red));
        tv_zaizhi.setTextColor(Y.getColor(R.color.text_color_9));
    }

    private void clickZaizhi() {
        sub_state = "1";
        getNet();

        tv_zaizhi.setTextColor(Y.getColor(R.color.text_red));
        tv_lizhi.setTextColor(Y.getColor(R.color.text_color_9));
    }
}
