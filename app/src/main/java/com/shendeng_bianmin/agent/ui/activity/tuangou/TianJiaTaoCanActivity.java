package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.InputDialog;
import com.shendeng_bianmin.agent.model.LeimuModel;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.model.TaoCanDetailsModel;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TianJiaTaoCanActivity extends BaseActivity {

    @BindView(R.id.tv_fenlei_2)
    TextView tvFenlei2;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tup_num)
    TextView tvTupNum;
    @BindView(R.id.nested)
    NestedScrollView nested;
    @BindView(R.id.srL_smart)
    SmartRefreshLayout srLSmart;
    @BindView(R.id.ll_tianjia_leimu)
    LinearLayout llTianjiaLeimu;
    @BindView(R.id.ll_leimu)
    LinearLayout llLeimu;
    @BindView(R.id.ll_guize)
    LinearLayout llGuize;
    @BindView(R.id.ll_tianjia_guize)
    LinearLayout llTianjiaGuize;
    @BindView(R.id.rl_shoucijinru)
    RelativeLayout rlShoucijinru;
    @BindView(R.id.ll_ercijinru)
    LinearLayout llErcijinru;
    @BindView(R.id.tv_fangrucangku)
    TextView tvFangrucangku;
    @BindView(R.id.tv_shangjiaxiaoshou)
    TextView tvShangjiaxiaoshou;
    @BindView(R.id.constrain_tupian)
    ConstraintLayout constrainTupian;
    @BindView(R.id.ll_bianjineirong)
    LinearLayout llBianjineirong;
    @BindView(R.id.et_taocanmingcheng)
    EditText etTaocanmingcheng;
    @BindView(R.id.et_taocanyuanjia)
    EditText etTaocanyuanjia;
    @BindView(R.id.et_taocanxianjia)
    EditText etTaocanxianjia;
    @BindView(R.id.et_taocanjianjie)
    EditText etTaocanjianjie;
    @BindView(R.id.ll_taocantupian)
    LinearLayout llTaocantupian;
    @BindView(R.id.tv_leimu)
    TextView tvLeimu;
    @BindView(R.id.ll_choose_leimu)
    LinearLayout llChooseLeimu;
    @BindView(R.id.hsl_caidantupian)
    HorizontalScrollView hslCaidantupian;
    @BindView(R.id.rl_taocanming)
    RelativeLayout rlTaocanming;
    @BindView(R.id.rl_taocan_yuanjia)
    RelativeLayout rlTaocanYuanjia;
    @BindView(R.id.rl_taocan_xianjia)
    RelativeLayout rlTaocanXianjia;
    @BindView(R.id.rl_taocan_jianjie)
    RelativeLayout rlTaocanJianjie;
    private String taoCanMingCheng, taoCanYuanjia, taoCanXianJia, taoCanJianJie;//套餐名称，套餐原价，套餐现价，套餐简介
    private String taoCanId;
    private OptionsPickerView<Object> leimuPicker;
    private List<LeimuModel.DataBean> leimuModels;
    private String item_id_one;
    private String item_id_one_name;
    private String item_id_two;
    private String item_id_two_name;
    private String item_id_three;
    private String item_id_three_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        srLSmart.setEnableLoadMore(false);

        llTianjiaLeimu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaoCanActivity.actionStart(mContext, "0", taoCanId);

//                View view = View.inflate(mContext, R.layout.item_taocan_tiaomu, null);
//                ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);
//                constraintLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        AddTaoCanActivity.actionStart(mContext, "1");
//                    }
//                });
//                llLeimu.addView(view);
//
//
//                Handler handler = new Handler();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        nested.fullScroll(ScrollView.FOCUS_DOWN);
//                    }
//                });
            }
        });

        llTianjiaGuize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TianJiaGuiZeActivity.actionStart(mContext, "0", taoCanId);

            }
        });


        String str = getIntent().getStringExtra("leixing");

        if (str.equals("0")) {
            rlShoucijinru.setVisibility(View.VISIBLE);
            llErcijinru.setVisibility(View.GONE);
        } else if (str.equals("1")) {
            llErcijinru.setVisibility(View.VISIBLE);
            rlShoucijinru.setVisibility(View.GONE);
        }


        rlShoucijinru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UIHelper.ToastMessage(mContext, "点击了提交");
                addShangpin();
            }
        });
        tvFangrucangku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(mContext, "点击了放入仓库");
            }
        });
        tvShangjiaxiaoshou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shangJiaTaoCan();
                UIHelper.ToastMessage(mContext, "点击了上架销售");
            }
        });
        constrainTupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenDianCaiDanActivity.actionStart(TianJiaTaoCanActivity.this, taoCanId);
            }
        });

        if (getIntent().getStringExtra("bianjiOrTianJia").equals("0")) {//0编辑
            llBianjineirong.setVisibility(View.VISIBLE);


            rlTaocanming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickName();
                }
            });
            etTaocanmingcheng.setFocusable(false);

            etTaocanmingcheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickName();
                }
            });


            rlTaocanYuanjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTaoCanYuanJia();
                }
            });

            etTaocanyuanjia.setFocusable(false);
            etTaocanyuanjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTaoCanYuanJia();
                }
            });


            rlTaocanXianjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickTaoCanXianJia();
                }
            });

            etTaocanxianjia.setFocusable(false);
            etTaocanxianjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTaoCanXianJia();
                }
            });

            rlTaocanJianjie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickTaoCanJianJie();

                }
            });

            etTaocanjianjie.setFocusable(false);
            rlTaocanJianjie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTaoCanJianJie();
                }
            });
            etTaocanjianjie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTaoCanJianJie();
                }
            });

        } else {//1添加
            llBianjineirong.setVisibility(View.GONE);
        }

        taoCanId = getIntent().getStringExtra("taoCanId");

        srLSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDetailsNet();
            }
        });

        getDetailsNet();

        tvLeimu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectLeimu();
            }
        });

    }

    private void showSelectLeimu() {
        if (leimuPicker == null) {
            showProgressDialog();
            getLeimu();
        } else {
            leimuPicker.show();
        }
    }

    private void shangJiaTaoCan() {


        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04204);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("dif_type", "1");
        map.put("type", "2");
        map.put("wares_id", taoCanId);


        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        UIHelper.ToastMessage(mContext, "上架成功");
                        finish();

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }


    boolean flag = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(taoCanId)) {
            if (!flag) {
                srLSmart.autoRefresh();
            }
            flag = false;
        }

    }

    private void getDetailsNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04203);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", taoCanId);

        Gson gson = new Gson();
        OkGo.<AppResponse<TaoCanDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TaoCanDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<TaoCanDetailsModel.DataBean>> response) {
                        srLSmart.finishRefresh();
                        etTaocanmingcheng.setText(response.body().data.get(0).getWares_name());
                        etTaocanyuanjia.setText(response.body().data.get(0).getShop_money_old());
                        etTaocanxianjia.setText(response.body().data.get(0).getShop_money_now());
                        etTaocanjianjie.setText(response.body().data.get(0).getShop_product_title());

                        llGuize.removeAllViews();
                        if (response.body().data.get(0).getRules_list().size() != 0) {
                            for (int i = 0; i < response.body().data.get(0).getRules_list().size(); i++) {
                                View view = View.inflate(mContext, R.layout.item_taocan_guize, null);
                                ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);
                                TextView tvTaoCanMing = view.findViewById(R.id.tv_taocan_ming);

                                tvTaoCanMing.setText(response.body().data.get(0).getRules_list().get(i).getPrompt_text());
                                int finalI = i;
                                constraintLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TianJiaGuiZeActivity.actionStart(mContext, "1", taoCanId, response.body().data.get(0).getRules_list().get(finalI).getPrompt_detail_id(), response.body().data.get(0).getRules_list().get(finalI).getPrompt_text());

                                    }
                                });
                                llGuize.addView(view);


//                                Handler handler = new Handler();
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        nested.fullScroll(ScrollView.FOCUS_DOWN);
//                                    }
//                                });
                            }

                        }
                        llLeimu.removeAllViews();
                        for (int i = 0; i < response.body().data.get(0).getTaocan_list().size(); i++) {
                            View view = View.inflate(mContext, R.layout.item_taocan_tiaomu, null);
                            ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);
                            TextView tvTaoCanMing = view.findViewById(R.id.tv_taocan_ming);
                            TextView tvTaoCanJiaGe = view.findViewById(R.id.tv_jiage);
                            TextView tvNumber = view.findViewById(R.id.tv_number);

                            tvTaoCanMing.setText(response.body().data.get(0).getTaocan_list().get(i).getMenu_text());
                            tvTaoCanJiaGe.setText("¥" + response.body().data.get(0).getTaocan_list().get(i).getMenu_pay());
                            tvNumber.setText("(" + response.body().data.get(0).getTaocan_list().get(i).getMenu_count() + "份)");

                            int finalI = i;
                            constraintLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TaoCanDetailsModel.DataBean.TaocanListBean bean = response.body().data.get(0).getTaocan_list().get(finalI);
                                    //UIHelper.ToastMessage(mContext, "去编辑套餐");
                                    AddTaoCanActivity.actionStart(mContext, "1", taoCanId, bean.getMenu_detail_id(), bean.getMenu_text(), bean.getMenu_count(), bean.getMenu_pay());

                                }
                            });
                            llLeimu.addView(view);

                        }

                        llTaocantupian.removeAllViews();
                        for (int i = 0; i < response.body().data.get(0).getImg_list().size(); i++) {
                            View view = View.inflate(mContext, R.layout.item_taocantupian, null);
                            ImageView ivTaoCanTuPian = view.findViewById(R.id.iv_taocan_tupian);
                            TaoCanDetailsModel.DataBean.ImgListBean bean = response.body().data.get(0).getImg_list().get(i);
                            Glide.with(mContext).load(bean.getImg_url()).into(ivTaoCanTuPian);
                            llTaocantupian.addView(view);
                        }

                        if (response.body().data.get(0).getImg_list().size() == 0) {
                            View view = View.inflate(mContext, R.layout.item_taocantupian, null);
                            ImageView ivTaoCanTuPian = view.findViewById(R.id.iv_taocan_tupian);
                            ivTaoCanTuPian.setBackgroundResource(R.mipmap.shangchuantupian);
                            llTaocantupian.addView(view);
                        }
                        tvTupNum.setText(response.body().data.get(0).getImg_list().size() + "张");

                        tvLeimu.setText(response.body().data.get(0).getItem_name());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TaoCanDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }


    @Override
    public int getContentViewResId() {
        return R.layout.layout_tianjia_taocan;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("添加套餐");
        tv_title.setTextSize(17);
        tv_title.setTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationIcon(R.mipmap.backbutton);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(findViewById(R.id.cl_layout).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
    }


    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context leixing 0新建进入 1 二次进入
     *                bianjiOrTianjia 0编辑  1添加
     */
    public static void actionStart(Context context, String leixing, String bianjiOrTianJia, String taoCanId) {
        Intent intent = new Intent(context, TianJiaTaoCanActivity.class);
        intent.putExtra("leixing", leixing);
        intent.putExtra("bianjiOrTianJia", bianjiOrTianJia);
        intent.putExtra("taoCanId", taoCanId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void addShangpin() {

        /**
         * item_id_one	一级商品类型id
         * item_id_one_name	一级商品类型名称
         * item_id_two	二级商品类型id
         * item_id_two_name	二级商品类型名称
         */


        taoCanYuanjia = etTaocanyuanjia.getText().toString();
        taoCanXianJia = etTaocanxianjia.getText().toString();
        taoCanMingCheng = etTaocanmingcheng.getText().toString();
        taoCanJianJie = etTaocanjianjie.getText().toString();
        if (StringUtils.isEmpty(taoCanMingCheng)) {
            UIHelper.ToastMessage(mContext, "请填写您的套餐名称");
            return;
        }
        if (StringUtils.isEmpty(taoCanYuanjia)) {
            UIHelper.ToastMessage(mContext, "请填写您的套餐原价");
            return;
        }
        if (StringUtils.isEmpty(taoCanXianJia)) {
            UIHelper.ToastMessage(mContext, "请填写您的套餐现价");
            return;
        }

        if (StringUtils.isEmpty(taoCanJianJie)) {
            UIHelper.ToastMessage(mContext, "请填写您的套餐描述");
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04179);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("enter_type", "5");
        map.put("wares_name", taoCanMingCheng);
        map.put("shop_product_title", taoCanJianJie);
        map.put("shop_money_old", taoCanYuanjia);
        map.put("shop_money_now", taoCanXianJia);

        /**
         * item_id_one	一级商品类型id
         * item_id_one_name	一级商品类型名称
         * item_id_two	二级商品类型id
         * item_id_two_name	二级商品类型名称
         */
        map.put("item_id_one", item_id_one);
        map.put("item_id_one_name", item_id_one_name);
        map.put("item_id_two", item_id_two);
        map.put("item_id_two_name", item_id_two_name);

        if (!StringUtils.isEmpty(taoCanId)) {
            map.put("wares_id", taoCanId);
        }
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {
                        //  finish();

                        if (getIntent().getStringExtra("bianjiOrTianJia").equals("0")) {//0编辑
                            getDetailsNet();

                        } else {
                            finish();
                            TianJiaTaoCanActivity.actionStart(mContext, "1", "0", response.body().data.get(0).getWares_id());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<ShangpinDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    private void initLeimu() {
        if (leimuModels.size() > 0) {
            List<Object> names1 = new ArrayList<>();
            List<List<Object>> names2 = new ArrayList<>();
            List<List<List<Object>>> names3 = new ArrayList<>();

            for (int i = 0; i < leimuModels.size(); i++) {
                LeimuModel.DataBean bean = leimuModels.get(i);
                names1.add(bean.getItem_name());
                List<LeimuModel.DataBean.NextLevelBeanX> next2 = bean.getNext_level();
                List<Object> names2Beans = new ArrayList<>();
                List<List<Object>> names3Beans = new ArrayList<>();
                for (int j = 0; j < next2.size(); j++) {
                    LeimuModel.DataBean.NextLevelBeanX nextLevelBeanX = next2.get(j);
                    names2Beans.add(nextLevelBeanX.getItem_name());
                    List<LeimuModel.DataBean.NextLevelBeanX.NextLevelBean> next3 = nextLevelBeanX.getNext_level();

                    List<Object> names3BeansZi = new ArrayList<>();
                    for (int k = 0; k < next3.size(); k++) {
                        names3BeansZi.add(next3.get(k).getItem_name());
                    }
                    names3Beans.add(names3BeansZi);

                }
                names2.add(names2Beans);
                names3.add(names3Beans);
            }

            //条件选择器
            leimuPicker = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    if (leimuModels != null && leimuModels.size() > 0) {
                        LeimuModel.DataBean dataBean = leimuModels.get(options1);
                        String is_install = dataBean.getIs_install();
                        item_id_one = dataBean.getItem_id();
                        item_id_one_name = dataBean.getItem_name();
                        List<LeimuModel.DataBean.NextLevelBeanX> next_level = dataBean.getNext_level();
                        if (next_level != null && next_level.size() > 0) {
                            LeimuModel.DataBean.NextLevelBeanX nextLevelBeanX = next_level.get(option2);
                            item_id_two = nextLevelBeanX.getItem_id();
                            item_id_two_name = nextLevelBeanX.getItem_name();
                            List<LeimuModel.DataBean.NextLevelBeanX.NextLevelBean> next_level1 = nextLevelBeanX.getNext_level();
                            if (next_level1 != null && next_level1.size() > 0) {
                                LeimuModel.DataBean.NextLevelBeanX.NextLevelBean nextLevelBean = next_level1.get(options3);
                                item_id_three = nextLevelBean.getItem_id();
                                item_id_three_name = nextLevelBean.getItem_name();

                                tvLeimu.setText(item_id_one_name + "-" + item_id_two_name + "-" + item_id_three_name);
                            } else {
                                item_id_three = "";
                                item_id_three_name = "";
                                tvLeimu.setText(item_id_one_name + "-" + item_id_two_name);
                            }
                        } else {
                            item_id_two = "";
                            item_id_two_name = "";
                            tvLeimu.setText(item_id_one_name);
                        }
                    } else {
                        item_id_one = "";
                        item_id_one_name = "";
                        tvLeimu.setText("");
                    }
                }
            }).build();

            if (names2.size() > 0) {
                if (names3.size() > 0) {
                    leimuPicker.setPicker(names1, names2, names3);
                } else {
                    leimuPicker.setPicker(names1, names2);
                }
            } else {
                leimuPicker.setPicker(names1);
            }

            leimuPicker.show();
        }
    }

    private void getLeimu() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04207);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<LeimuModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<LeimuModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<LeimuModel.DataBean>> response) {
                        leimuModels = response.body().data;
                        initLeimu();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }

    private void clickName() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入套餐标题");
                } else {
                    etTaocanmingcheng.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入套餐标题");
        dialog.setTextContent(etTaocanmingcheng.getText().toString());
        dialog.show();
    }

    private void clickTaoCanYuanJia() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入价格");
                } else {
                    etTaocanyuanjia.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.setTextTitle("请输入价格");
        dialog.setTextContent(etTaocanyuanjia.getText().toString());
        dialog.show();
    }

    private void clickTaoCanXianJia() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入价格");
                } else {
                    etTaocanxianjia.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.setTextTitle("请输入价格");
        dialog.setTextContent(etTaocanxianjia.getText().toString());
        dialog.show();
    }

    private void clickTaoCanJianJie() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入简介");
                } else {
                    etTaocanjianjie.setText(dialog.getTextContent());
                    dialog.dismiss();
                    addShangpin();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入简介");
        dialog.setTextContent(etTaocanjianjie.getText().toString());
        dialog.show();
    }


}
