package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.shendeng_bianmin.agent.model.OrderModel;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HandAddActivity extends BaseActivity {


    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.bt_submit)
    TextView btSubmit;

    @Override
    public void onCreate(Bundle savedInstanceStateundle) {
        super.onCreate(savedInstanceStateundle);
        ButterKnife.bind(this);
        initView();
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(etNumber.getText().toString())) {
                    UIHelper.ToastMessage(mContext, "请输入校验码");
                    return;
                }
                getNet(etNumber.getText().toString());
            }
        });
    }

    private void getNet(String result) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04318);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("pay_code", result);

        Gson gson = new Gson();
        OkGo.<AppResponse<OrderModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<OrderModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<OrderModel.DataBean>> response) {
                        Y.t("验证成功");
                        finish();
                    }

                    @Override
                    public void onError(Response<AppResponse<OrderModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<OrderModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("手动验码");
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


    @Override
    public int getContentViewResId() {
        return R.layout.activity_hand_add;
    }

    private void initView() {

    }

    @Override
    public boolean showToolBarLine() {
        return true;
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HandAddActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
