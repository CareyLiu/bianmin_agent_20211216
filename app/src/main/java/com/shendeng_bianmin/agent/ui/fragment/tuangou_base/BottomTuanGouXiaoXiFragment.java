package com.shendeng_bianmin.agent.ui.fragment.tuangou_base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.MsgAdapter;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.model.MsgModel;
import com.shendeng_bianmin.agent.ui.activity.MsgGonggaoActivity;
import com.shendeng_bianmin.agent.ui.activity.MsgHuodongActivity;
import com.shendeng_bianmin.agent.ui.activity.MsgIMActivity;
import com.shendeng_bianmin.agent.ui.activity.MsgNewActivity;
import com.shendeng_bianmin.agent.ui.activity.MsgOrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BottomTuanGouXiaoXiFragment extends BaseFragment {

    @BindView(R.id.rv_view)
    RecyclerView rv_view;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.view_line)
    View view_line;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {

            }
        }));
    }

    public static BottomTuanGouXiaoXiFragment newInstance() {
        Bundle args = new Bundle();
        BottomTuanGouXiaoXiFragment fragment = new BottomTuanGouXiaoXiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frag_mian_msg;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initLogic() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNet();
        initAdapter();
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

    private void initAdapter() {
        List<MsgModel> models = new ArrayList<>();
        models.add(new MsgModel(R.mipmap.xiaoxi_tongzhi_liaotianxiaoxi, "私聊消息"));
        models.add(new MsgModel(R.mipmap.xiaoxi_tongzhi_dingdanxiaoxi, "订单消息"));
        models.add(new MsgModel(R.mipmap.xiaoxi_tongzhi_juyijiagonggao, "聚易佳公告"));
        models.add(new MsgModel(R.mipmap.xiaoxi_tongzhi_jingxuanhuodong, "精选活动"));
        models.add(new MsgModel(R.mipmap.xiaoxi_tongzhi_xingongneng, "新功能"));

        MsgAdapter adapter = new MsgAdapter(R.layout.item_msg_xiaoxi, models);
        rv_view.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (models != null && models.size() > position) {
                    String name = models.get(position).getName();
                    switch (name) {
                        case "订单消息":
                            MsgOrderActivity.actionStart(getContext(), AppCode.msg_tuangou);
                            break;
                        case "聚易佳公告":
                            MsgGonggaoActivity.actionStart(getContext(), AppCode.msg_tuangou);
                            break;
                        case "精选活动":
                            MsgHuodongActivity.actionStart(getContext(), AppCode.msg_tuangou);
                            break;
                        case "新功能":
                            MsgNewActivity.actionStart(getContext(), AppCode.msg_tuangou);
                            break;
                        case "私聊消息":
                            MsgIMActivity.actionStart(getContext(), AppCode.msg_tuangou);
                            break;
                    }
                }
            }
        });
    }

    private void getNet() {

    }

}
