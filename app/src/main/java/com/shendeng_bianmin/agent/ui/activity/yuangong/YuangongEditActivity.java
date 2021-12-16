package com.shendeng_bianmin.agent.ui.activity.yuangong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
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
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YuangongEditActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ll_name)
    LinearLayout ll_name;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.ll_state)
    LinearLayout ll_state;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.ll_num)
    LinearLayout ll_num;
    @BindView(R.id.tv_bumen)
    TextView tv_bumen;
    @BindView(R.id.ll_bumen)
    LinearLayout ll_bumen;
    @BindView(R.id.bt_delete)
    TextView bt_delete;

    private String inst_id;
    private String of_user_id;
    private String subsystem_id;

    private List<BumenModel.DataBean> bumenModels;
    private OptionsPickerView<Object> bumenPicker;

    private String phone;
    private String name;
    private String num;
    private String state;
    private String role_name;
    private String branch_name;
    private String sub_user_id;

    @Override
    public int getContentViewResId() {
        return R.layout.act_yuangong_edit;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("修改员工信息");
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
                                   String role_id,
                                   String branch_name,
                                   String role_name,
                                   String sub_user_id) {
        Intent intent = new Intent();
        intent.setClass(context, YuangongEditActivity.class);
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
        intent.putExtra("branch_name", branch_name);
        intent.putExtra("role_name", role_name);
        intent.putExtra("sub_user_id", sub_user_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        inst_id = getIntent().getStringExtra("inst_id");
        of_user_id = getIntent().getStringExtra("of_user_id");
        subsystem_id = getIntent().getStringExtra("subsystem_id");
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        state = getIntent().getStringExtra("state");
        num = getIntent().getStringExtra("num");
        branch_name = getIntent().getStringExtra("branch_name");
        role_name = getIntent().getStringExtra("role_name");
        sub_user_id = getIntent().getStringExtra("sub_user_id");

        tv_phone.setText(phone);
        tv_name.setText(name);
        tv_num.setText(num);
        tv_state.setText(state);
        tv_bumen.setText(branch_name + "·" + role_name);

        if (state.equals("正常")) {
            state = "1";
        } else {
            state = "2";
        }
    }


    @OnClick({R.id.bt_delete})
    public void onViewClicked(View view) {
        TishiDialog dialog = new TishiDialog(mContext, new TishiDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, TishiDialog dialog) {

            }

            @Override
            public void onClickConfirm(View v, TishiDialog dialog) {
                xiayibu();
            }

            @Override
            public void onDismiss(TishiDialog dialog) {

            }
        });
        dialog.setTextCont("是否删除该员工");
        dialog.show();
    }


    private void xiayibu() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04222);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("sub_user_id", sub_user_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<BumenModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<BumenModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<BumenModel.DataBean>> response) {
                        Y.t(response.body().msg);
                        Notice n = new Notice();
                        n.type = ConstanceValue.ADD_YUANGONG;
                        RxBus.getDefault().sendRx(n);
                        finish();
                    }

                    @Override
                    public void onError(Response<AppResponse<BumenModel.DataBean>> response) {
                        super.onError(response);
                        Y.tError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<BumenModel.DataBean>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });
    }
}
