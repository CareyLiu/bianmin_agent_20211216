package com.shendeng_bianmin.agent.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.model.Message;
import com.shendeng_bianmin.agent.model.OrderDetailsModel;
import com.shendeng_bianmin.agent.model.WuliuModel;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;
import com.tbruyelle.rxpermissions.RxPermissions;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class OrderFahuoActivity extends BaseActivity {


    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_adress)
    TextView tv_adress;
    @BindView(R.id.tv_kuaidigongsi)
    TextView tv_kuaidigongsi;
    @BindView(R.id.ll_select_gongsi)
    LinearLayout ll_select_gongsi;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_xl_kc)
    TextView tv_xl_kc;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.ed_danhao)
    EditText ed_danhao;
    @BindView(R.id.iv_ewm)
    ImageView iv_ewm;

    private List<WuliuModel.DataBean> wuliuModels = new ArrayList<>();
    private OptionsPickerView pickerSelct;
    private String express_name;
    private String express_code;
    private String express_id;
    private String express_no;
    private OrderDetailsModel.DataBean dataBean;
    private String shop_form_id;

    @Override
    public int getContentViewResId() {
        return R.layout.act_order_fahuo;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("发货");
        tv_rightTitle.setTextSize(17);
        tv_rightTitle.setTextColor(this.getResources().getColor(R.color.text_red));
        tv_rightTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv_rightTitle.setVisibility(View.VISIBLE);
        tv_rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fahuo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String shop_form_id) {
        Intent intent = new Intent();
        intent.setClass(context, OrderFahuoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("shop_form_id", shop_form_id);
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
        shop_form_id = getIntent().getStringExtra("shop_form_id");

        getDetails();
        getWuliu();
        initHuidiao();
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.order_fahuo) {
                    String express_no = (String) message.content;
                    ed_danhao.setText(express_no);
                }
            }
        }));
    }

    private void getDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04313);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<OrderDetailsModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderDetailsModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderDetailsModel.DataBean>> response) {
                        dataBean = response.body().data.get(0);

                        tv_name.setText("收件人：" + dataBean.getReceiver_name());
                        tv_phone.setText(dataBean.getReceiver_phone());
                        tv_adress.setText(dataBean.getUser_addr_all());
                        Glide.with(mContext).load(dataBean.getIndex_photo_url()).into(iv_img);
                        tv_title_name.setText(dataBean.getShop_product_title());
                        tv_money.setText(dataBean.getPay_money());
                        tv_time.setText(dataBean.getCreate_time());
                        tv_xl_kc.setText("数量 " + dataBean.getPay_count());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<OrderDetailsModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog("");
                    }
                });
    }

    private void getWuliu() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_00005);
        map.put("key", Urls.KEY);
        map.put("type_id", "express");//物流公司
        Gson gson = new Gson();
        OkGo.<AppResponse<WuliuModel.DataBean>>post(Urls.MSG)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<WuliuModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<WuliuModel.DataBean>> response) {
                        wuliuModels = response.body().data;
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    private void showSelect() {
        if (pickerSelct == null) {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < wuliuModels.size(); i++) {
                names.add(wuliuModels.get(i).getName());
            }

            //条件选择器
            pickerSelct = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    WuliuModel.DataBean dataBean = wuliuModels.get(options1);
                    express_name = dataBean.getName();
                    express_code = dataBean.getVal1();
                    express_id = dataBean.getId();
                    tv_kuaidigongsi.setText(express_name);
                }
            }).build();
            pickerSelct.setPicker(names);
        }


        pickerSelct.show();
    }

    @OnClick({R.id.ll_select_gongsi, R.id.iv_ewm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_gongsi:
                showSelect();
                break;
            case R.id.iv_ewm:
                ewm();
                break;
        }
    }

    private void ewm() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) { // 在android 6.0之前会默认返回true
                    actionStartForResult();
                } else {
                    Y.tLong("该应用需要赋予访问相机的权限，不开启将无法正常工作！");
                }
            }
        });
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public void actionStartForResult() {
        Intent intent = new Intent(this, OrderFahuoEwmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 100);
    }


    private class Shop_form_id {
        private String shop_form_id;

        public Shop_form_id(String shop_form_id) {
            this.shop_form_id = shop_form_id;
        }
    }


    private void fahuo() throws JSONException {
        if (TextUtils.isEmpty(express_name)) {
            Y.t("请选择快递公司");
            return;
        }

        express_no = ed_danhao.getText().toString();
//        express_no = "SF1081723448107";
        if (TextUtils.isEmpty(express_no)) {
            Y.t("请输入快递单号");
            return;
        }

        List<Shop_form_id> list = new ArrayList<>();
        list.add(new Shop_form_id(dataBean.getShop_form_id()));
        Map<String, Object> map = new HashMap<>();
        map.put("code", Urls.code_04316);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("shop_form_ids", list);
        map.put("express_id", express_id);
        map.put("express_code", express_code);
        map.put("express_name", express_name);
        map.put("express_no", express_no);

        Gson gson = new Gson();
        OkGo.<AppResponse<Message.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<Message.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<Message.DataBean>> response) {
                        Y.t("发货成功");
                        finish();
                    }

                    @Override
                    public void onError(Response<AppResponse<Message.DataBean>> response) {
                        Y.tError(response);
                    }

                    @Override
                    public void onStart(Request<AppResponse<Message.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog("");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }
}
