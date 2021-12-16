package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.tishi.MyCarCaoZuoDialog_CaoZuo_Base;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TianJiaGuiZeActivity extends BaseActivity {


    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.tv_shanchu)
    TextView tvShanchu;
    @BindView(R.id.tv_queding)
    TextView tvQueding;
    @BindView(R.id.ll_ercijinru)
    LinearLayout llErcijinru;

    String taoCanId;
    private String guiZeId;//规则id
    private String guiZeMiaoShu;//规则描述

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getStringExtra("leixing").equals("0")) {

            tvShanchu.setVisibility(View.GONE);
        } else {

        }

        taoCanId = getIntent().getStringExtra("taoCanId");

        tvQueding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(mContext, "确定");
                tianJiaGuiZe();
            }
        });

        tvShanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCarCaoZuoDialog_CaoZuo_Base base = new MyCarCaoZuoDialog_CaoZuo_Base(mContext, "操作", "您确定要删除规则吗？", new MyCarCaoZuoDialog_CaoZuo_Base.OnDialogItemClickListener() {
                    @Override
                    public void clickLeft() {

                    }

                    @Override
                    public void clickRight() {
                        shanchuGuiZe();
                    }
                });

                base.show();

            }
        });

        guiZeMiaoShu = getIntent().getStringExtra("guiZeMiaoShu");
        etText.setText(guiZeMiaoShu);
        guiZeId = getIntent().getStringExtra("guiZeId");

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_tian_jia_gui_ze;
    }


    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("添加规则");
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
     */
    public static void actionStart(Context context, String leixing, String taoCanId, String guiZeId, String guiZeMiaoShu) {
        Intent intent = new Intent(context, TianJiaGuiZeActivity.class);
        intent.putExtra("leixing", leixing);
        intent.putExtra("taoCanId", taoCanId);
        intent.putExtra("guiZeId", guiZeId);
        intent.putExtra("guiZeMiaoShu", guiZeMiaoShu);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context leixing 0新建进入 1 二次进入
     */
    public static void actionStart(Context context, String leixing, String taoCanId) {
        Intent intent = new Intent(context, TianJiaGuiZeActivity.class);
        intent.putExtra("leixing", leixing);
        intent.putExtra("taoCanId", taoCanId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void tianJiaGuiZe() {
        if (StringUtils.isEmpty(etText.getText().toString())) {
            UIHelper.ToastMessage(mContext, "请填写规则");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04206);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        //map.put("prompt_detail_id", "");
        if (!StringUtils.isEmpty(guiZeId)) {
            map.put("prompt_detail_id", guiZeId);
        }

        map.put("prompt_text", etText.getText().toString());
        map.put("wares_id", taoCanId);

        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        UIHelper.ToastMessage(mContext, "规则添加成功");
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

    private void shanchuGuiZe() {

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04204);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("dif_type", "3");
        map.put("type", "1");
        map.put("prompt_detail_id", guiZeId);


        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(Response<AppResponse> response) {
                        UIHelper.ToastMessage(mContext, "删除成功");
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
