package com.shendeng_bianmin.agent.ui.fragment.mendian;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TuanGouAccessAdapter;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.InputDialog;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.model.PingjiaModel;
import com.shendeng_bianmin.agent.model.tuangou.TuanPinglunModel;
import com.shendeng_bianmin.agent.ui.view.sys.ScreenUtil;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MenDianFragment2 extends BaseFragment {
    @BindView(R.id.tv_pingfen_number)
    TextView tvPingfenNumber;
    @BindView(R.id.ll_pingfen)
    LinearLayout llPingfen;
    @BindView(R.id.tv_zongpingshu)
    TextView tv_zongpingshu;
    @BindView(R.id.tv_haoping)
    TextView tv_haoping;
    @BindView(R.id.view_haoping)
    LinearLayout viewHaoping;
    @BindView(R.id.ll_haoping)
    LinearLayout llHaoping;
    @BindView(R.id.tv_zhongchaping)
    TextView tv_zhongchaping;
    @BindView(R.id.view_zhongchaping)
    LinearLayout viewZhongchaping;
    @BindView(R.id.ll_zhongchaping)
    LinearLayout llZhongchaping;
    @BindView(R.id.tv_haopinglv)
    TextView tv_haopinglv;
    @BindView(R.id.tv_zhongchapinglv)
    TextView tv_zhongchapinglv;
    @BindView(R.id.tv_quanbupingjia)
    TextView tvQuanbupingjia;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.choose_zhongchaping)
    TextView chooseZhongchaping;
    @BindView(R.id.choose_haoping)
    TextView chooseHaoping;
    @BindView(R.id.choose_quanbu)
    TextView chooseQuanbu;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private TuanPinglunModel.DataBean pinglunModel;
    private List<TuanPinglunModel.DataBean.AssessListBean> assess_list = new ArrayList<>();
    private TuanGouAccessAdapter tuanGouAccessAdapter;

    private String shop_to_text;
    private String shop_to_score;
    private String shop_form_id;
    private TuanPinglunModel.DataBean.AssessListBean pinglunBeen;
    private int huiPosition;
    private String assess_type;

    @Override
    protected void initLogic() {
        chooseHaoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHaoping();
            }
        });
        chooseZhongchaping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectZhongping();
            }
        });

        chooseQuanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectQuanbu();
            }
        });

        shop_to_score = "5";
        initAdapter();
        initSM();
        selectQuanbu();
    }

    private void selectQuanbu() {
        showProgressDialog();
        assess_type = "1";
        chooseQuanbu.setBackgroundResource(R.drawable.bg_fe3332b_radius_12);
        chooseHaoping.setBackgroundResource(R.drawable.gray_broad_radius_12);
        chooseZhongchaping.setBackgroundResource(R.drawable.gray_broad_radius_12);
        getNet();
    }

    private void selectZhongping() {
        showProgressDialog();
        assess_type = "3";
        chooseZhongchaping.setBackgroundResource(R.drawable.bg_fe3332b_radius_12);
        chooseHaoping.setBackgroundResource(R.drawable.gray_broad_radius_12);
        chooseQuanbu.setBackgroundResource(R.drawable.gray_broad_radius_12);
        getNet();
    }

    private void selectHaoping() {
        showProgressDialog();
        assess_type = "2";
        chooseHaoping.setBackgroundResource(R.drawable.bg_fe3332b_radius_12);
        chooseZhongchaping.setBackgroundResource(R.drawable.gray_broad_radius_12);
        chooseQuanbu.setBackgroundResource(R.drawable.gray_broad_radius_12);
        getNet();
    }

    private void initAdapter() {
        tuanGouAccessAdapter = new TuanGouAccessAdapter(R.layout.item_access, assess_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rlvList.setLayoutManager(linearLayoutManager);
        rlvList.setAdapter(tuanGouAccessAdapter);
        tuanGouAccessAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_huifu_bt) {
                    if (assess_list != null && assess_list.size() > position) {
                        pinglunBeen = assess_list.get(position);
                        String reply_state = pinglunBeen.getReply_state();
                        if (reply_state.equals("1")) {
                            Y.t("已回复评论");
                        } else {
                            huiPosition = position;
                            shop_form_id = pinglunBeen.getShop_form_id();
                            clickPinglun();
                        }
                    }
                }
            }
        });
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

    private int page_number;

    private void getNet() {
        page_number = 0;
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04217);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("page_number", page_number + "");
        map.put("assess_type", assess_type);
        Gson gson = new Gson();
        OkGo.<AppResponse<TuanPinglunModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TuanPinglunModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TuanPinglunModel.DataBean>> response) {
                        pinglunModel = response.body().data.get(0);
                        assess_list = pinglunModel.getAssess_list();

                        String total_assess_count = pinglunModel.getTotal_assess_count();
                        String high_assess_count = pinglunModel.getHigh_assess_count();
                        String low_assess_count = pinglunModel.getLow_assess_count();
                        tv_zongpingshu.setText("总评价数 " + total_assess_count);
                        tv_haoping.setText("好评 " + high_assess_count);
                        tv_zhongchaping.setText("中差评 " + low_assess_count);
                        tvPingfenNumber.setText(pinglunModel.getAverage_score());

                        try {
                            setHaopinglv(high_assess_count, total_assess_count);
                            setZhongcha(low_assess_count, total_assess_count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        tv_haopinglv.setText(pinglunModel.getHigh_assess_percent());
                        tv_zhongchapinglv.setText(pinglunModel.getLow_assess_percent());

                        tuanGouAccessAdapter.setNewData(assess_list);
                        tuanGouAccessAdapter.notifyDataSetChanged();

                        chooseZhongchaping.setText("中差评 " + pinglunModel.getLow_assess_count());
                        chooseHaoping.setText("好评 " + pinglunModel.getHigh_assess_count());
                        chooseQuanbu.setText("全部 " + pinglunModel.getTotal_assess_count());

                        if (assess_list.size() > 0) {
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        if (response.body().next.equals("0")) {
                            smartRefreshLayout.setEnableLoadMore(false);
                        } else {
                            smartRefreshLayout.setEnableLoadMore(true);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                        dismissProgressDialog();
                    }
                });
    }

    private void setHaopinglv(String strHaoPing, String strQuanBuPingLun) {
        viewHaoping.removeAllViews();
        BigDecimal haoPingBigDecimal = new BigDecimal(strHaoPing);
        BigDecimal quanBuBigDecimal = new BigDecimal(strQuanBuPingLun);
        BigDecimal quanBuView = new BigDecimal("58");
        BigDecimal oneBig = quanBuView.multiply(haoPingBigDecimal);
        BigDecimal finalViewLength = oneBig.divide(quanBuBigDecimal);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHaoping.getLayoutParams();
        ScreenUtil.init(getActivity());
        linearParams.width = ScreenUtil.dip2px(Float.valueOf(finalViewLength.toString()));
        viewHaoping.setLayoutParams(linearParams);
    }


    private void setZhongcha(String strHaoPing, String strQuanBuPingLun) {
        viewZhongchaping.removeAllViews();
        BigDecimal haoPingBigDecimal = new BigDecimal(strHaoPing);
        BigDecimal quanBuBigDecimal = new BigDecimal(strQuanBuPingLun);
        BigDecimal quanBuView = new BigDecimal("58");
        BigDecimal oneBig = quanBuView.multiply(haoPingBigDecimal);
        BigDecimal finalViewLength = oneBig.divide(quanBuBigDecimal);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewZhongchaping.getLayoutParams();
        ScreenUtil.init(getActivity());
        linearParams.width = ScreenUtil.dip2px(Float.valueOf(finalViewLength.toString()));
        viewZhongchaping.setLayoutParams(linearParams);
    }

    private void getLoad() {
        page_number++;
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04217);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("page_number", page_number + "");
        map.put("assess_type", assess_type);
        Gson gson = new Gson();
        OkGo.<AppResponse<TuanPinglunModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TuanPinglunModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TuanPinglunModel.DataBean>> response) {

                        pinglunModel = response.body().data.get(0);
                        List<TuanPinglunModel.DataBean.AssessListBean> pinglunModelAssess_list = pinglunModel.getAssess_list();
                        assess_list.addAll(pinglunModelAssess_list);

                        String total_assess_count = pinglunModel.getTotal_assess_count();
                        String high_assess_count = pinglunModel.getHigh_assess_count();
                        String low_assess_count = pinglunModel.getLow_assess_count();
                        tv_zongpingshu.setText("总评价数 " + total_assess_count);
                        tv_haoping.setText("好评 " + high_assess_count);
                        tv_zhongchaping.setText("中差评 " + low_assess_count);
                        tvPingfenNumber.setText(pinglunModel.getAverage_score());

                        try {
                            setHaopinglv(high_assess_count, total_assess_count);
                            setZhongcha(low_assess_count, total_assess_count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        tv_haopinglv.setText(pinglunModel.getHigh_assess_percent());
                        tv_zhongchapinglv.setText(pinglunModel.getLow_assess_percent());

                        tuanGouAccessAdapter.setNewData(MenDianFragment2.this.assess_list);
                        tuanGouAccessAdapter.notifyDataSetChanged();

                        chooseZhongchaping.setText("中差评 " + pinglunModel.getLow_assess_count());
                        chooseHaoping.setText("好评 " + pinglunModel.getHigh_assess_count());
                        chooseQuanbu.setText("全部 " + pinglunModel.getTotal_assess_count());

                        if (assess_list.size() > 0) {
                            ll_no_data.setVisibility(View.GONE);
                        } else {
                            ll_no_data.setVisibility(View.VISIBLE);
                        }

                        if (response.body().next.equals("0")) {
                            smartRefreshLayout.setEnableLoadMore(false);
                        } else {
                            smartRefreshLayout.setEnableLoadMore(true);
                        }
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
        return R.layout.layout_mendian_fragment2;
    }

    @Override
    protected void initView(View rootView) {

    }

    private void clickPinglun() {
        InputDialog dialog = new InputDialog(getContext(), new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                shop_to_text = dialog.getTextContent();
                if (TextUtils.isEmpty(shop_to_text)) {
                    Y.t("请输入评价内容");
                } else {
                    dialog.dismiss();
                    pinglun();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入评论内容");
        dialog.show();
    }

    private void pinglun() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04317);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(getContext()).getAppToken());
        map.put("shop_form_id", shop_form_id);
        map.put("shop_to_score", shop_to_score);
        map.put("shop_to_text", shop_to_text);
        Gson gson = new Gson();
        OkGo.<AppResponse<PingjiaModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<PingjiaModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<PingjiaModel.DataBean>> response) {
                        TishiDialog dialog = new TishiDialog(getContext(), new TishiDialog.TishiDialogListener() {
                            @Override
                            public void onClickCancel(View v, TishiDialog dialog) {

                            }

                            @Override
                            public void onClickConfirm(View v, TishiDialog dialog) {

                            }

                            @Override
                            public void onDismiss(TishiDialog dialog) {
                                pinglunBeen.setReply_state("1");
                                pinglunBeen.setShop_to_text(shop_to_text);
                                assess_list.set(huiPosition, pinglunBeen);
                                tuanGouAccessAdapter.setNewData(assess_list);
                                tuanGouAccessAdapter.notifyDataSetChanged();
                            }
                        });
                        dialog.setTextTitle("成功");
                        dialog.setTextCont("已成功回复评论");
                        dialog.setTextConfirm("知道了");
                        dialog.setTextCancel("");
                        dialog.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<PingjiaModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

}
