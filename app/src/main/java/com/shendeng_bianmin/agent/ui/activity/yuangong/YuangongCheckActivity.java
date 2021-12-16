package com.shendeng_bianmin.agent.ui.activity.yuangong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.TimeCount;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YuangongCheckActivity extends BaseActivity {


    @BindView(R.id.ed_phone)
    TextView ed_phone;
    @BindView(R.id.ed_code)
    EditText ed_code;
    @BindView(R.id.tv_send_code)
    TextView tv_send_code;
    @BindView(R.id.ll_send_code)
    LinearLayout ll_send_code;
    @BindView(R.id.bt_ok)
    TextView bt_ok;

    private TimeCount timeCount;
    private String smsId;
    private String user_phone;
    private String name;
    private String branch_id;
    private String role_id;
    private String sms_num;

    @Override
    public int getContentViewResId() {
        return R.layout.act_yuangong_check;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("添加员工");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context,
                                   String of_user_id,
                                   String inst_id,
                                   String subsystem_id,
                                   String phone,
                                   String name,
                                   String state,
                                   String num,
                                   String branch_id,
                                   String role_id) {
        Intent intent = new Intent();
        intent.setClass(context, YuangongCheckActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("of_user_id", of_user_id);
        intent.putExtra("inst_id", inst_id);
        intent.putExtra("subsystem_id", subsystem_id);
        intent.putExtra("phone", phone);
        intent.putExtra("name", name);
        intent.putExtra("state", state);
        intent.putExtra("num", num);
        intent.putExtra("branch_id", branch_id);
        intent.putExtra("role_id", role_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        timeCount = new TimeCount(60000, 1000, tv_send_code);
        user_phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        branch_id = getIntent().getStringExtra("branch_id");
        role_id = getIntent().getStringExtra("role_id");

        ed_phone.setText(user_phone);
    }

    @OnClick({R.id.ll_send_code, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_send_code:
                get_code();
                break;
            case R.id.bt_ok:
                addYuangong();
                break;
        }
    }

    private void addYuangong() {
        sms_num = ed_code.getText().toString();
        if (TextUtils.isEmpty(sms_num)) {
            Y.t("请输入短信验证码");
            return;
        }

        if (TextUtils.isEmpty(smsId)) {
            Y.t("请发送短信验证码");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04221);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("user_phone", user_phone);
        map.put("user_name", name);
        map.put("branch_id_one", branch_id);
        map.put("role_id", role_id);
        map.put("sms_id", smsId);
        map.put("sms_num", sms_num);
        Gson gson = new Gson();
        OkGo.<AppResponse<Message.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<Message.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<Message.DataBean>> response) {
                        Y.t(response.body().msg);
                        Notice n = new Notice();
                        n.type = ConstanceValue.ADD_YUANGONG;
                        RxBus.getDefault().sendRx(n);
                        finish();
                    }

                    @Override
                    public void onError(Response<AppResponse<Message.DataBean>> response) {
                        Y.tError(response);
                    }

                    @Override
                    public void onStart(Request<AppResponse<Message.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });

    }


    private void get_code() {
        user_phone = ed_phone.getText().toString().trim();
        if (TextUtils.isEmpty(user_phone)) {
            Y.t("请输入员工手机号");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_00001);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("user_phone", user_phone);
        map.put("mod_id", "0324");
        Gson gson = new Gson();
        OkGo.<AppResponse<Message.DataBean>>post(Urls.MSG)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<Message.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<Message.DataBean>> response) {
                        Y.t("验证码获取成功");
                        timeCount.start();
                        if (response.body().data.size() > 0) {
                            smsId = response.body().data.get(0).getSms_id();
                        }
                    }

                    @Override
                    public void onError(Response<AppResponse<Message.DataBean>> response) {
                        Y.tError(response);
                        timeCount.start();
                    }

                    @Override
                    public void onStart(Request<AppResponse<Message.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }
                });
    }
}
