package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppCode;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.MessageModel;
import com.shendeng_bianmin.agent.ui.adapter.MessageListAdapter;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MsgIMActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    List<MessageModel.DataBean> mDatas = new ArrayList<>();
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;


    private MessageListAdapter messageListAdapter;
    private String appCode;
    private String notifyId;

    @Override
    public int getContentViewResId() {
        return R.layout.act_msg_im;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("私聊消息");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String appCode) {
        Intent intent = new Intent();
        intent.setClass(context, MsgIMActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("appCode", appCode);
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
        appCode = getIntent().getStringExtra("appCode");
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {

            }
        }));
        initAdapter();
        getNet();
        initSM();
        initHuidiao();
    }

    private boolean isOn = false;

    @Override
    protected void onPause() {
        super.onPause();
        isOn = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOn = true;
        messageListAdapter.notifyDataSetChanged();
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.MSG_RONGYUN_REVICE) {
                    if (isOn) {
                        messageListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }));
    }

    private void initSM() {
        srLSmart.setEnableLoadMore(true);
        srLSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNet();
            }
        });
        srLSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getMore();
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        messageListAdapter = new MessageListAdapter(R.layout.item_messagelist, mDatas);
        messageListAdapter.openLoadAnimation();//默认为渐显效果
        recyclerView.setAdapter(messageListAdapter);

        messageListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.constrain:
                        Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
                        String targetId = mDatas.get(position).getLt_user_accid();
                        String instName = mDatas.get(position).getLt_user_name();
                        Bundle bundle = new Bundle();
                        bundle.putString("dianpuming", instName);
                        bundle.putString("inst_accid", mDatas.get(position).getLt_user_accid());
                        if (appCode.equals(AppCode.msg_maijia)) {
                            bundle.putString("shoptype", "1");
                        } else {
                            bundle.putString("shoptype", "2");
                        }

                        RongIM.getInstance().startConversation(mContext, conversationType, targetId, instName, bundle);
                        break;
                }
            }
        });
    }

    private void getNet() {
        notifyId = "";
        Map<String, String> map = new HashMap<>();
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        if (appCode.equals(AppCode.msg_maijia)) {
            map.put("code", Urls.code_04341);
        } else {
            map.put("code", Urls.code_04218);
        }
        map.put("type", "1");
        Gson gson = new Gson();
        OkGo.<AppResponse<MessageModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MessageModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MessageModel.DataBean>> response) {
                        if (response.body().data.size() > 0) {
                            if (StringUtils.isEmpty(notifyId)) {
                                mDatas.clear();
                                mDatas.addAll(response.body().data);
                                messageListAdapter.setNewData(mDatas);

                            } else {
                                srLSmart.setEnableLoadMore(true);
                                mDatas.addAll(response.body().data);
                            }

                            notifyId = mDatas.get(mDatas.size() - 1).getNotify_id();
                            messageListAdapter.notifyDataSetChanged();

                            recyclerView.setVisibility(View.VISIBLE);
                            srLSmart.setEnableLoadMore(true);
                        } else {
                            srLSmart.setEnableLoadMore(false);
                        }

                        if (mDatas.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            ll_no_data.setVisibility(View.VISIBLE);
                        } else {
                            ll_no_data.setVisibility(View.GONE);
                        }
                        srLSmart.finishLoadMore();
                        srLSmart.finishRefresh();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    private void getMore() {
        notifyId = "";
        Map<String, String> map = new HashMap<>();
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        if (appCode.equals(AppCode.msg_maijia)) {
            map.put("code", Urls.code_04341);
        } else {
            map.put("code", Urls.code_04218);
        }
        map.put("type", "1");
        map.put("notify_id", notifyId);
        Gson gson = new Gson();
        OkGo.<AppResponse<MessageModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MessageModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MessageModel.DataBean>> response) {
                        if (response.body().data.size() > 0) {
                            if (StringUtils.isEmpty(notifyId)) {
                                mDatas.clear();
                                mDatas.addAll(response.body().data);
                                messageListAdapter.setNewData(mDatas);

                            } else {
                                srLSmart.setEnableLoadMore(true);
                                mDatas.addAll(response.body().data);
                            }

                            notifyId = mDatas.get(mDatas.size() - 1).getNotify_id();
                            messageListAdapter.notifyDataSetChanged();

                            recyclerView.setVisibility(View.VISIBLE);
                            srLSmart.setEnableLoadMore(true);
                        } else {
                            srLSmart.setEnableLoadMore(false);
                        }

                        if (mDatas.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            ll_no_data.setVisibility(View.VISIBLE);
                        } else {
                            ll_no_data.setVisibility(View.GONE);
                        }
                        srLSmart.finishLoadMore();
                        srLSmart.finishRefresh();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }
}
