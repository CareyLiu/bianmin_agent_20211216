package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.ShangpinAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.db.DBManager;
import com.shendeng_bianmin.agent.db.HistoryRecordDao;
import com.shendeng_bianmin.agent.db.table.HistoryRecord;
import com.shendeng_bianmin.agent.model.ShangpinModel;
import com.shendeng_bianmin.agent.ui.adapter.Custom5HistoryAdapter;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shendeng_bianmin.agent.db.DbField.DOUDARENSEARCH;
import static com.shendeng_bianmin.agent.util.Urls.WORKER;


public class Custom5SearchThingActivity extends BaseActivity {
    private static final String tag = Custom5SearchThingActivity.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_serarchKey)
    EditText etSerarchKey;
    @BindView(R.id.rl_search)
    RoundLinearLayout rlSearch;
    @BindView(R.id.iv_cancel)
    TextView ivCancel;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    String strTitle;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.activity_custom3_search)
    RelativeLayout activityCustom3Search;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Custom5HistoryAdapter historyAdapter;
    private ShangpinAdapter taoKeListAdapter2;
    private InputMethodManager imm;
    List<HistoryRecord> searchKeywords1;
    public DBManager dbManager;
    private Context context = Custom5SearchThingActivity.this;
    private List<ShangpinModel.DataBean> mDatas = new ArrayList<>();
    int pagesize = 20;
    int pageNumber = 0;
    List<ShangpinModel.DataBean> dataBeanList = new ArrayList<>();

    private String wares_id;
    private String wares_state;
    private String screening_type;

    /**
     * 用于其他activity跳转到该activity
     */
    public static void actionStart(Context context, String string) {
        Intent intent = new Intent(context, Custom5SearchThingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("string", string);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setBaseTitleGone();
        wares_id = "";
        wares_state = "1";
        screening_type = "1";

        if (getIntent() == null) {
            return;
        }
        strTitle = getIntent().getStringExtra("string");
        etSerarchKey.setText(strTitle);
//        douDaRen5_6SearchPresenter = new DouDaRenSearchPresenterImpl(this);
        dbManager = DBManager.getInstance(this);
        dbManager.setDebug();
        searchKeywords1 = new ArrayList<>();
        context = this;
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeTarget.setLayoutManager(gridLayoutManager);
        initAdapter();
        getNet();
        //refreshLayout.setEnableAutoLoadMore(true);
       // refreshLayout.setEnableRefresh(true);
       refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNumber = 0;
                refreshLayout.setEnableLoadMore(true);
                getNet();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNumber++;
                getNet();
            }
        });
        historyAdapter();
        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSerarchKey.setFocusable(true);
                etSerarchKey.setFocusableInTouchMode(true);
                etSerarchKey.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            }
        });
        etSerarchKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                insertKeyword(textView.getText().toString().trim(), 1);
                strTitle = textView.getText().toString();
                pageNumber = 0;
                //getNet();
                //      BeautifulTellArticleSearchDtoIn in = new BeautifulTellArticleSearchDtoIn();
                //      App app = App.getInstance();
                //    if (app.isLogin()) {
                //      in.personId = app.getLoginUid();
                // }
                //        in.areaName = String.valueOf(App.getInstance().getProperty(AppConfig.CONF_CITY));
                // in.setPageNumber(pageNumber);
                // in.setPageSize(10);
                //if (StringUtils.isEmpty(textView.getText().toString().trim())) {
                //   UIHelper.ToastMessage(Custom5SearchThingActivity.this, "检索内容不能为空", Toast.LENGTH_SHORT);
                //} else {
                //  in.searchValue = textView.getText().toString().trim();
                // tv = textView;
                // try {
                //   douDaRen5_6SearchPresenter.getDouDaRenSearch(in);
                // } catch (AppException e) {
                //    e.printStackTrace();
                // }
                // }

                getNet();

                return false;
            }
        });

        searchKeywords1 = queryKeywordList(DOUDARENSEARCH);
        // historyAdapter.setNewData(searchKeywords1);


    }

    /**
     * 插入
     *
     * @param keyword
     */
    public void insertKeyword(String keyword, int type) {
        if (keyword == null || keyword.isEmpty()) {
            return;
        }
        HistoryRecordDao historyRecordDao = dbManager.getDaoSession().getHistoryRecordDao();
        List<HistoryRecord> searchKeywordList = queryKeywordList(type);
        if (searchKeywordList.size() > 9) {
            historyRecordDao.queryBuilder().where(HistoryRecordDao.Properties.Date.eq(searchKeywordList.get(0).getDate())).buildDelete().executeDeleteWithoutDetachingEntities();
        }

        Iterator<HistoryRecord> iterator = searchKeywordList.iterator();
        while (iterator.hasNext()) {
            HistoryRecord next = iterator.next();
            if (keyword.equals(next.getName())) {
                iterator.remove();
                historyRecordDao.queryBuilder().where(HistoryRecordDao.Properties.Name.eq(next.getName())).buildDelete().executeDeleteWithoutDetachingEntities();
            }
        }

        HistoryRecord sk = new HistoryRecord();
        sk.setName(keyword);
        sk.setDate(System.currentTimeMillis());
        sk.setType(type);
        historyRecordDao.insertInTx(sk);

        if (historyAdapter != null) {
            searchKeywords1.clear();
            searchKeywords1 = queryKeywordList(1);
            historyAdapter.setNewData(searchKeywords1);
        }
    }

    /**
     * 查询
     */
    public List<HistoryRecord> queryKeywordList(int type) {
        HistoryRecordDao historyRecordDao = dbManager.getDaoSession().getHistoryRecordDao();
        List<HistoryRecord> searchKeywordList = historyRecordDao.queryBuilder().where(HistoryRecordDao.Properties.Type.eq(type)).orderAsc(HistoryRecordDao.Properties.Date).build().list();
        return searchKeywordList;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void initAdapter() {
        taoKeListAdapter2 = new ShangpinAdapter(R.layout.item_shangpin, mDatas);
        taoKeListAdapter2.openLoadAnimation();//默认为渐显效果
        swipeTarget.setAdapter(taoKeListAdapter2);
        taoKeListAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShangpinModel.DataBean dataBean = mDatas.get(position);
                ShangpinDetailsActivity.actionStart(Custom5SearchThingActivity.this, dataBean.getWares_id());
              //  UIHelper.ToastMessage(mContext,"sfdlksfj");
            }
        });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_custom5_search_only;
    }

    @OnClick({R.id.iv_cancel, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                //imm.hideSoftInputFromWindow(activityCustom3Search.getWindowToken(), 0);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    //历史记录
    private void historyAdapter() {
        historyAdapter = new Custom5HistoryAdapter(searchKeywords1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    private String sort = "total_sales";//
    private String shengxu = "_asc";
    private String jiangxu = "_des";

    private String sortBenTi = sort + jiangxu;


    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04409);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("wares_state", wares_state);//1.出售中  2.已下架
        map.put("screening_type", screening_type);//1按添加时间降序 2按添加时间升序 3按总销量降序 4按总销量升序
        // map.put("text", strTitle);
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinModel.DataBean>> response) {
                        mDatas = response.body().data;

                        if (mDatas != null && mDatas.size() > 0) {
                            wares_id = mDatas.get(mDatas.size() - 1).getWares_id();
                            llNone.setVisibility(View.GONE);
                        } else {
                            llNone.setVisibility(View.VISIBLE);
                        }

                        taoKeListAdapter2.setNewData(mDatas);
                        taoKeListAdapter2.notifyDataSetChanged();

                        hideKeyboard(activityCustom3Search);
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                        //   smartRefreshLayout.finishRefresh();
                    }
                });
    }

    String str = "0";
    String tanchuCon = "0";//0否 1 是



}
