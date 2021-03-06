package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.InputDialog_v1;
import com.shendeng_bianmin.agent.dialog.TimeChooseDialog;
import com.shendeng_bianmin.agent.model.MenDianGuanLiModel;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.suke.widget.SwitchButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MenDianXinXiActivity extends BaseActivity {

    @BindView(R.id.rl_lianxidianhua)
    RelativeLayout rlLianxidianhua;
    @BindView(R.id.switch_yingyezhuangtai)
    SwitchButton switchYingyezhuangtai;
    @BindView(R.id.rl_dianpukaidianshijian)
    RelativeLayout rlDianpukaidianshijian;
    @BindView(R.id.rl_dianpubidianshijian)
    RelativeLayout rlDianpubidianshijian;
    @BindView(R.id.rl_rongnarenshu)
    RelativeLayout rlRongnarenshu;
    @BindView(R.id.rl_mendianmianji)
    RelativeLayout rlMendianmianji;
    @BindView(R.id.switch_baoxiang)
    SwitchButton switchBaoxiang;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_kaidianshijian)
    TextView tvKaidianshijian;
    @BindView(R.id.tv_bidianshijian)
    TextView tvBidianshijian;
    @BindView(R.id.tv_rongnarenshu)
    TextView tvRongnarenshu;
    @BindView(R.id.tv_qingshudianpumianji)
    TextView tvQingshudianpumianji;

    private String phone;//????????????
    private String kaiShiYingYeShiJian;//??????????????????
    private String jieShuYingYeShiJian;//??????????????????
    private String rongNaRenShu;//????????????
    private String menDianMianJi;//????????????
    private String shiFouYouBaoXiang;//??????????????? ??????????????? 1.??? 2.??????
    private String kaiQiMenDian;//???????????? ???????????????1.????????????  2.????????????
    TimeChooseDialog timeChooseDialog;
    private String firstEnter_YingYe = "1";// 0 ??? 1 ???
    private String firstEnter_BaoXiang = "1";//0 ??? 1 ???

    private String shouCiJinRu = "1";//0??? 1 ???

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getNetNeiRong();
        rlLianxidianhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLianXiDianHua();
            }
        });
        rlDianpukaidianshijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeChooseDialog = new TimeChooseDialog(mContext, new TimeChooseDialog.OnDialogItemClickListener() {
                    @Override
                    public void clickLeft() {

                    }

                    @Override
                    public void clickRight(String time) {
                        UIHelper.ToastMessage(mContext, time);
                        tvKaidianshijian.setText(time);
                        kaiShiYingYeShiJian = time;
                        getNet();
                        timeChooseDialog.dismiss();


                    }
                });
                timeChooseDialog.show();
            }
        });

        rlDianpubidianshijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeChooseDialog = new TimeChooseDialog(mContext, new TimeChooseDialog.OnDialogItemClickListener() {
                    @Override
                    public void clickLeft() {

                    }

                    @Override
                    public void clickRight(String time) {
                        UIHelper.ToastMessage(mContext, time);
                        tvBidianshijian.setText(time);
                        timeChooseDialog.dismiss();
                        jieShuYingYeShiJian = time;
                        getNet();

                    }
                });
                timeChooseDialog.show();

            }
        });

        rlRongnarenshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputDialog_v1 dialog = new InputDialog_v1(mContext, new InputDialog_v1.TishiDialogListener() {
                    @Override
                    public void onClickCancel(View v, InputDialog_v1 dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickConfirm(View v, InputDialog_v1 dialog) {
                        if (TextUtils.isEmpty(dialog.getTextContent())) {
                            Y.t("?????????????????????");
                        } else {
                            tvRongnarenshu.setText(dialog.getTextContent());
                            //getNet();
                            dialog.dismiss();
                            // addShangpin();
                            rongNaRenShu = tvRongnarenshu.getText().toString().trim();
                            getNet();
                        }
                    }

                    @Override
                    public void onDismiss(InputDialog_v1 dialog) {

                    }
                });
                dialog.setDismissAfterClick(false);
                dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
                dialog.setTextTitle("?????????????????????");
                dialog.setTextContent(tvRongnarenshu.getText().toString());
                dialog.show();
            }
        });


        rlMendianmianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog_v1 dialog = new InputDialog_v1(mContext, new InputDialog_v1.TishiDialogListener() {
                    @Override
                    public void onClickCancel(View v, InputDialog_v1 dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickConfirm(View v, InputDialog_v1 dialog) {
                        if (TextUtils.isEmpty(dialog.getTextContent())) {
                            Y.t("?????????????????????");
                        } else {
                            tvQingshudianpumianji.setText(dialog.getTextContent());
                            menDianMianJi = tvQingshudianpumianji.getText().toString().trim();
                            getNet();
                            dialog.dismiss();
                            // addShangpin();
                        }
                    }

                    @Override
                    public void onDismiss(InputDialog_v1 dialog) {

                    }
                });
                dialog.setDismissAfterClick(false);
                dialog.setTextInput(InputType.TYPE_CLASS_NUMBER);
                dialog.setTextTitle("?????????????????????");
                dialog.setTextContent(tvQingshudianpumianji.getText().toString());
                dialog.show();

            }
        });


    }

    @Override
    public int getContentViewResId() {
        return R.layout.layout_mendianxinxi;
    }

    private void getNet() {
        if (shouCiJinRu.equals("1")) {
            shouCiJinRu = "0";
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04212);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("contact_phone", phone);
        map.put("inst_hours_begin", kaiShiYingYeShiJian);
        map.put("inst_hours_end", jieShuYingYeShiJian);
        map.put("shop_capacity", rongNaRenShu);
        map.put("shop_area", menDianMianJi);
        map.put("is_box", shiFouYouBaoXiang);
        map.put("business_status", kaiQiMenDian);
        Gson gson = new Gson();
        OkGo.<AppResponse<ShangpinDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<ShangpinDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<ShangpinDetailsModel.DataBean>> response) {

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("????????????");
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    private void clickLianXiDianHua() {
        InputDialog_v1 dialog = new InputDialog_v1(mContext, new InputDialog_v1.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog_v1 dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog_v1 dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("?????????????????????");
                } else {
                    tvPhone.setText(dialog.getTextContent());
                    phone = tvPhone.getText().toString().trim();
                    getNet();
                    dialog.dismiss();
                }
            }

            @Override
            public void onDismiss(InputDialog_v1 dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_NUMBER);
        dialog.setTextTitle("?????????????????????");
        dialog.show();
    }

    /**
     * ????????????Activty????????????Activity
     *
     * @param context leixing 0???????????? 1 ????????????
     *                bianjiOrTianjia 0??????  1??????
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MenDianXinXiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getNetNeiRong() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04214);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        Gson gson = new Gson();
        OkGo.<AppResponse<MenDianGuanLiModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<MenDianGuanLiModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<MenDianGuanLiModel.DataBean>> response) {
                        tvPhone.setHint(response.body().data.get(0).getContact_phone());
                        phone = response.body().data.get(0).getContact_phone();

                        tvKaidianshijian.setHint(response.body().data.get(0).getInst_hours_begin());
                        kaiShiYingYeShiJian = response.body().data.get(0).getInst_hours_begin();

                        tvBidianshijian.setHint(response.body().data.get(0).getInst_hours_end());
                        jieShuYingYeShiJian = response.body().data.get(0).getInst_hours_end();

                        tvRongnarenshu.setHint(response.body().data.get(0).getShop_capacity());
                        rongNaRenShu = response.body().data.get(0).getShop_capacity();

                        tvQingshudianpumianji.setHint(response.body().data.get(0).getShop_area());
                        menDianMianJi = response.body().data.get(0).getShop_area();


                        if (response.body().data.get(0).getBusiness_status().equals("1")) {
                            switchYingyezhuangtai.setChecked(true);
                        } else {
                            switchYingyezhuangtai.setChecked(false);
                        }

                        if (response.body().data.get(0).getIs_box().equals("1")) {
                            switchBaoxiang.setChecked(true);
                        } else {
                            switchBaoxiang.setChecked(false);
                        }

                        switchYingyezhuangtai.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                                if (isChecked) {
                                    if (firstEnter_YingYe.equals("1")) {

                                    } else {
                                        kaiQiMenDian = "1";
                                        getNet();
                                    }
                                } else {
                                    if (firstEnter_YingYe.equals("1")) {

                                    } else {
                                        kaiQiMenDian = "2";
                                        getNet();
                                    }
                                }
                            }
                        });

                        switchBaoxiang.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                                if (isChecked) {
                                    if (firstEnter_BaoXiang.equals("1")) {

                                    } else {
                                        shiFouYouBaoXiang = "1";
                                        getNet();
                                    }
                                } else {

                                    if (firstEnter_BaoXiang.equals("1")) {

                                    } else {
                                        shiFouYouBaoXiang = "2";
                                        getNet();
                                    }
                                }
                            }
                        });

                        firstEnter_YingYe = "0";
                        firstEnter_BaoXiang = "0";
                    }
                });
    }
}
