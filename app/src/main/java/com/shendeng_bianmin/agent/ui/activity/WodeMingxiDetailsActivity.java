package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.MingxiAdapter;
import com.shendeng_bianmin.agent.adapter.MingxiDetailsAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.MingxiDetailsModel;
import com.shendeng_bianmin.agent.model.MingxiModel;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WodeMingxiDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    private List<MingxiModel.DataBean> data = new ArrayList<>();
    private MingxiAdapter mingxiAdapter;
    private String pay_cost_id;

    @Override
    public int getContentViewResId() {
        return R.layout.act_mine_mingxi_details;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("明细");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WodeMingxiDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        pay_cost_id = getIntent().getStringExtra("pay_cost_id");
        getNet();
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04189);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("pay_cost_id", pay_cost_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<MingxiDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MingxiDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MingxiDetailsModel.DataBean>> response) {
                        MingxiDetailsModel.DataBean dataBean = response.body().data.get(0);
                        tv_title_name.setText(dataBean.getIncome_name());
                        tv_money.setText(dataBean.getIncome_money());

                        MingxiDetailsAdapter adapter = new MingxiDetailsAdapter(R.layout.item_mine_mingxi_details, dataBean.getDistribution_list());
                        rv_content.setLayoutManager(new LinearLayoutManager(WodeMingxiDetailsActivity.this));
                        rv_content.setAdapter(adapter);
                    }
                });
    }
}
