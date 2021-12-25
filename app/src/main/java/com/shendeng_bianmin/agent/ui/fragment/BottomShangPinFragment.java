package com.shendeng_bianmin.agent.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.shendeng_bianmin.agent.adapter.ShangpinAdapter;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.ShangpinModel;
import com.shendeng_bianmin.agent.ui.activity.ShangpinAddActivity;
import com.shendeng_bianmin.agent.ui.activity.ShangpinDetailsActivity;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

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

public class BottomShangPinFragment extends BaseFragment {
    public static final String TAG = "BottomShangPinFragment";
    @BindView(R.id.title_chushou)
    TextView title_chushou;
    @BindView(R.id.title_yixiajia)
    TextView title_yixiajia;
    @BindView(R.id.tv_paixu_time)
    TextView tv_paixu_time;
    @BindView(R.id.tv_paixu_xiaoliang)
    TextView tv_paixu_xiaoliang;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.ll_paixu_time)
    LinearLayout ll_paixu_time;
    @BindView(R.id.ll_paixu_xiaoliang)
    LinearLayout ll_paixu_xiaoliang;
    @BindView(R.id.iv_left_select)
    ImageView iv_left_select;
    @BindView(R.id.iv_right_select)
    ImageView iv_right_select;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private String wares_id;
    private String wares_state;
    private String screening_type;
    private boolean selectTimeShang;
    private boolean selectJiaShang;

    private List<ShangpinModel.DataBean> shangpinModels = new ArrayList<>();
    private ShangpinAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static BottomShangPinFragment newInstance() {
        Bundle args = new Bundle();
        BottomShangPinFragment fragment = new BottomShangPinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frag_mian_shangpin;
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
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        wares_id = "";
        wares_state = "1";
        screening_type = "1";
        selectTimeShang = true;
        selectJiaShang = false;

        initAdapter();
        initSM();
        getNet();
        initHuidiao();
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.shangpin_frag) {
                    getNet();
                }
            }
        }));
    }

    private void initSM() {
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });


        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getLoad();
            }
        });
    }

    private void initAdapter() {
        adapter = new ShangpinAdapter(R.layout.item_shangpin, shangpinModels);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShangpinModel.DataBean dataBean = shangpinModels.get(position);
                ShangpinDetailsActivity.actionStart(getContext(), dataBean.getWares_id());
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

    @OnClick({R.id.title_chushou, R.id.title_yixiajia, R.id.ll_paixu_time, R.id.ll_paixu_xiaoliang, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_chushou:
                selectWaresState("1");
                break;
            case R.id.title_yixiajia:
                selectWaresState("2");
                break;
            case R.id.ll_paixu_time:
                selectPaixu1();
                break;
            case R.id.ll_paixu_xiaoliang:
                selectPaixu2();
                break;
            case R.id.iv_add:
                ShangpinAddActivity.actionStart(getContext());
                break;
        }
    }

    private void selectPaixu1() {
        iv_right_select.setImageResource(R.mipmap.dianpu_jiage_weixuanze);
        selectJiaShang = false;
        tv_paixu_time.setTextColor(Y.getColor(R.color.waimai_main_color));
        tv_paixu_xiaoliang.setTextColor(Y.getColor(R.color.text_color_9));
        if (selectTimeShang) {
            screening_type = "2";
            iv_left_select.setImageResource(R.mipmap.dianpu_jiage_jiangxu);
        } else {
            screening_type = "1";
            iv_left_select.setImageResource(R.mipmap.dianpu_jiage_shengxu);
        }
        selectTimeShang = !selectTimeShang;
        showProgressDialog();
        getNet();
    }

    private void selectPaixu2() {
        iv_left_select.setImageResource(R.mipmap.dianpu_jiage_weixuanze);
        selectTimeShang = false;
        tv_paixu_time.setTextColor(Y.getColor(R.color.text_color_9));
        tv_paixu_xiaoliang.setTextColor(Y.getColor(R.color.waimai_main_color));
        if (selectJiaShang) {
            screening_type = "4";
            iv_right_select.setImageResource(R.mipmap.dianpu_jiage_jiangxu);
        } else {
            screening_type = "3";
            iv_right_select.setImageResource(R.mipmap.dianpu_jiage_shengxu);
        }
        selectJiaShang = !selectJiaShang;
        showProgressDialog();
        getNet();
    }

    private void selectWaresState(String wares_state) {
        this.wares_state = wares_state;
        title_chushou.setTextColor(Y.getColor(R.color.text_color_9));
        title_yixiajia.setTextColor(Y.getColor(R.color.text_color_9));
        switch (wares_state) {
            case "1":
                title_chushou.setTextColor(Y.getColor(R.color.waimai_main_color));
                break;
            case "2":
                title_yixiajia.setTextColor(Y.getColor(R.color.waimai_main_color));
                break;
        }

        showProgressDialog();
        getNet();
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04409);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("wares_state", wares_state);//1.出售中  2.已下架
        map.put("screening_type", screening_type);//1按添加时间降序 2按添加时间升序 3按总销量降序 4按总销量升序
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinModel.DataBean>> response) {
                        shangpinModels = response.body().data;

                        if (shangpinModels != null && shangpinModels.size() > 0) {
                            wares_id = shangpinModels.get(shangpinModels.size() - 1).getWares_id();
                            ll_no_data.setVisibility(View.GONE);
                        }else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        adapter.setNewData(shangpinModels);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    private void getLoad() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04409);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("wares_id", wares_id);
        map.put("wares_state", wares_state);//1.出售中  2.已下架
        map.put("screening_type", screening_type);//1按添加时间降序 2按添加时间升序 3按总销量降序 4按总销量升序
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinModel.DataBean>> response) {
                        List<ShangpinModel.DataBean> data = response.body().data;
                        shangpinModels.addAll(data);

                        if (shangpinModels != null && shangpinModels.size() > 0) {
                            wares_id = shangpinModels.get(shangpinModels.size() - 1).getWares_id();
                            ll_no_data.setVisibility(View.GONE);
                        }else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        adapter.setNewData(shangpinModels);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        smartRefreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onStart(Request<AppResponse<ShangpinModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }
}
