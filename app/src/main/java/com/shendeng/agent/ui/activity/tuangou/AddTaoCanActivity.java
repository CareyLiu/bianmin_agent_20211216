package com.shendeng.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng.agent.R;
import com.shendeng.agent.app.BaseActivity;
import com.shendeng.agent.callback.JsonCallback;
import com.shendeng.agent.config.AppResponse;
import com.shendeng.agent.config.UserManager;
import com.shendeng.agent.model.TaoCanDetailsModel;
import com.shendeng.agent.util.UIHelper;
import com.shendeng.agent.util.Urls;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AddTaoCanActivity extends BaseActivity {

    @BindView(R.id.tv_mingcheng)
    TextView tvMingcheng;
    @BindView(R.id.tv_shuliang)
    TextView tvShuliang;
    @BindView(R.id.tv_jiage)
    TextView tvJiage;
    @BindView(R.id.et_jiage)
    EditText etJiage;
    @BindView(R.id.cl_one)
    ConstraintLayout clOne;
    @BindView(R.id.ll_diercijinru)
    LinearLayout llDiercijinru;
    String type; // 0 第一次进入 1 第二次进入
    @BindView(R.id.tv_queding)
    TextView tvQueding;
    @BindView(R.id.tv_shanchu)
    TextView tvShanchu;
    @BindView(R.id.tv_queding1)
    TextView tvQueding1;
    @BindView(R.id.et_mingcheng)
    EditText etMingcheng;
    @BindView(R.id.et_shuliang)
    EditText etShuliang;
    private String taoCanId;
    String ziTaoCanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getStringExtra("type").equals("0")) {

            tvQueding.setVisibility(View.VISIBLE);
            llDiercijinru.setVisibility(View.GONE);
        } else {
            tvQueding.setVisibility(View.GONE);
            llDiercijinru.setVisibility(View.VISIBLE);
        }

        taoCanId = getIntent().getStringExtra("taoCanId");

        tvQueding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(etMingcheng.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "输入的名称不能为空");
                    return;
                } else if (StringUtils.isEmpty(etShuliang.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "输入的数量不能为空");
                    return;
                } else if (StringUtils.isEmpty(etJiage.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "输入的价格不能为空");
                    return;
                }

                UIHelper.ToastMessage(mContext, "点击了确定");

                tianJiaTaoCan();
            }
        });

        tvQueding1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(etMingcheng.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "输入的名称不能为空");
                    return;
                } else if (StringUtils.isEmpty(etShuliang.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "输入的数量不能为空");
                    return;
                } else if (StringUtils.isEmpty(etJiage.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "输入的价格不能为空");
                    return;
                }

                UIHelper.ToastMessage(mContext, "点击了确定");
            }
        });

        etJiage.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});

    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("套餐管理");
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_tao_can;
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String type, String taoCanId) {
        Intent intent = new Intent();
        intent.setClass(context, AddTaoCanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", type);
        intent.putExtra("taoCanId", taoCanId);
        context.startActivity(intent);
    }

    private void tianJiaTaoCan() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04205);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("wares_id", taoCanId);

        map.put("menu_text", etMingcheng.getText().toString());
        map.put("menu_count", etShuliang.getText().toString());
        map.put("menu_pay", etJiage.getText().toString());

        map.put("menu_detail_id", ziTaoCanId);

        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {

                        UIHelper.ToastMessage(mContext, "套餐添加成功");
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


}
