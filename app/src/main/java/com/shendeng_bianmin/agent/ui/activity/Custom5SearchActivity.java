package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.db.DBHelperConsumer;
import com.shendeng_bianmin.agent.db.DBManager;
import com.shendeng_bianmin.agent.db.HistoryRecordDao;
import com.shendeng_bianmin.agent.db.table.HistoryRecord;
import com.shendeng_bianmin.agent.ui.adapter.Custom5HistoryAdapter;
import com.shendeng_bianmin.agent.util.UIHelper;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shendeng_bianmin.agent.db.DbField.DOUDARENSEARCH;


public class Custom5SearchActivity extends BaseActivity {
    private static final String tag = Custom5SearchActivity.class.getSimpleName();
    @BindView(R.id.et_serarchKey)
    EditText etSerarchKey;
    @BindView(R.id.rl_search)
    RoundLinearLayout rlSearch;
    @BindView(R.id.iv_cancel)
    TextView ivCancel;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rv_view2)
    RecyclerView rvView2;
    @BindView(R.id.ll_clear_all)
    LinearLayout llClearAll;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.activity_custom3_search)
    RelativeLayout activityCustom3Search;

    private Custom5HistoryAdapter historyAdapter;
    private InputMethodManager imm;
    private DBHelperConsumer helper;
    private List<String> search_history;
    List<HistoryRecord> searchKeywords1;
    public DBManager dbManager;


    /**
     * ????????????activity????????????activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Custom5SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchKeywords1.size() > 0) {
            llClearAll.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
        } else {
            llClearAll.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }
    }

    TextView tv;//????????????
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setBaseTitleGone();

        dbManager = DBManager.getInstance(this);
        dbManager.setDebug();
        searchKeywords1 = new ArrayList<>();
        context = this;
        // App.scaleScreenHelper.loadView((ViewGroup) getWindow().getDecorView());
        helper = new DBHelperConsumer(this);
        search_history = new ArrayList<>();
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                //????????????????????????????????????
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            tv = textView;
                            if (StringUtils.isEmpty(tv.getText().toString().trim())) {
                                UIHelper.ToastMessage(Custom5SearchActivity.this, "????????????????????????");
                            } else {
                                //????????????
                                insertKeyword(textView.getText().toString().trim(), DOUDARENSEARCH);
                                Custom5SearchThingActivity.actionStart(Custom5SearchActivity.this, textView.getText().toString().trim());
                            }

                            return true;
                        default:
                            return true;
                    }
                }

                return false;
            }
        });

        searchKeywords1 = queryKeywordList(DOUDARENSEARCH);
        historyAdapter.setNewData(searchKeywords1);
        if (searchKeywords1.size() > 0) {
            llClearAll.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
        } else {
            llClearAll.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }


    }

    /**
     * ??????
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
     * ??????
     */
    public List<HistoryRecord> queryKeywordList(int type) {
        HistoryRecordDao historyRecordDao = dbManager.getDaoSession().getHistoryRecordDao();
        List<HistoryRecord> searchKeywordList = historyRecordDao.queryBuilder().where(HistoryRecordDao.Properties.Type.eq(type)).orderDesc(HistoryRecordDao.Properties.Date).build().list();
        return searchKeywordList;
    }

    /**
     * ??????
     */
    public void delAllKeyword(int type) {
        HistoryRecordDao historyRecordDao = dbManager.getDaoSession().getHistoryRecordDao();
        historyRecordDao.queryBuilder().where(HistoryRecordDao.Properties.Type.eq(type)).buildDelete().executeDeleteWithoutDetachingEntities();
        if (historyAdapter != null) {
            searchKeywords1.clear();
            searchKeywords1 = queryKeywordList(1);
            historyAdapter.setNewData(searchKeywords1);
        }
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
        // ????????????????????????????????????????????????TouchEvent???
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //????????????????????????location??????
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // ??????????????????????????????????????????EditText?????????
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_custom5_search;
    }

    @OnClick({R.id.iv_cancel, R.id.ll_clear_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_clear_all:
                delAllKeyword(DOUDARENSEARCH);
                llClearAll.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                break;
            case R.id.iv_cancel:

                imm.hideSoftInputFromWindow(activityCustom3Search.getWindowToken(), 0);
                finish();
                break;
        }
    }

    //????????????
    private void historyAdapter() {
        historyAdapter = new Custom5HistoryAdapter(searchKeywords1);
        rvView2.setLayoutManager(new LinearLayoutManager(this));
        rvView2.setAdapter(historyAdapter);
        historyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_layout:
                        //????????????
                        insertKeyword(historyAdapter.getData().get(position).getName().toString(), DOUDARENSEARCH);
                        Custom5SearchThingActivity.actionStart(Custom5SearchActivity.this, historyAdapter.getData().get(position).getName().toString());
                        break;
                    case R.id.iv_delete:
                        delOneItemword(historyAdapter.getData().get(position).getName().toString());
                        if (searchKeywords1.size() > 0) {
                            llClearAll.setVisibility(View.VISIBLE);
                            viewLine.setVisibility(View.VISIBLE);
                        } else {
                            llClearAll.setVisibility(View.GONE);
                            viewLine.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });
    }

    /**
     * ??????
     */
    public void delOneItemword(String name) {
        HistoryRecordDao historyRecordDao = dbManager.getDaoSession().getHistoryRecordDao();
        historyRecordDao.queryBuilder().where(HistoryRecordDao.Properties.Name.eq(name)).buildDelete().executeDeleteWithoutDetachingEntities();
        if (historyAdapter != null) {
            searchKeywords1.clear();
            searchKeywords1 = queryKeywordList(1);
            historyAdapter.setNewData(searchKeywords1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
