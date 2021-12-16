package com.shendeng_bianmin.agent.ui.activity.yuangong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
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
import com.shendeng_bianmin.agent.dialog.BottomDialog;
import com.shendeng_bianmin.agent.dialog.BottomDialogView;
import com.shendeng_bianmin.agent.dialog.InputDialog;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class YuangongAddActivity extends BaseActivity {

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

    private String inst_id;
    private String of_user_id;
    private String subsystem_id;

    private List<BumenModel.DataBean> bumenModels;
    private OptionsPickerView<Object> bumenPicker;

    private String phone;
    private String name;
    private String num;
    private String state;
    private String branch_id;
    private String role_id;

    @Override
    public int getContentViewResId() {
        return R.layout.act_yuangong_add;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("添加新员工");
        tv_rightTitle.setText("下一步");
        tv_rightTitle.setVisibility(View.VISIBLE);
        tv_rightTitle.setTextSize(17);
        tv_rightTitle.setTextColor(this.getResources().getColor(R.color.text_color_3));
        tv_rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiayibu();
            }
        });
    }

    private void xiayibu() {
        state = "1";
        if (TextUtils.isEmpty(phone)) {
            Y.t("请输入员工手机号");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Y.t("请输入员工姓名");
            return;
        }

        if (TextUtils.isEmpty(state)) {
            Y.t("请选择员工状态");
            return;
        }

        if (TextUtils.isEmpty(branch_id)) {
            Y.t("请选择员工部门角色");
            return;
        }

        YuangongCheckActivity.actionStart(
                mContext,
                of_user_id,
                inst_id,
                subsystem_id,
                phone,
                name,
                state,
                num,
                branch_id,
                role_id);

    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String of_user_id, String inst_id, String subsystem_id) {
        Intent intent = new Intent();
        intent.setClass(context, YuangongAddActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("of_user_id", of_user_id);
        intent.putExtra("inst_id", inst_id);
        intent.putExtra("subsystem_id", subsystem_id);
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
        initHuidiao();

        ll_state.setVisibility(View.GONE);
        ll_num.setVisibility(View.GONE);
    }

    private void initHuidiao() {
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
                if (message.type == ConstanceValue.ADD_YUANGONG) {
                    finish();
                }
            }
        }));
    }

    @OnClick({R.id.ll_phone, R.id.ll_name, R.id.ll_state, R.id.ll_num, R.id.ll_bumen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_phone:
                clickPhone();
                break;
            case R.id.ll_name:
                clickName();
                break;
            case R.id.ll_state:
                clickState();
                break;
            case R.id.ll_num:
                clickNum();
                break;
            case R.id.ll_bumen:
                clickBumen();
                break;
        }
    }

    private void clickPhone() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入员工手机号");
                } else {
                    phone = dialog.getTextContent();
                    tv_phone.setText(phone);
                    dialog.dismiss();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入员工手机号");
        dialog.setTextContent(tv_phone.getText().toString());
        dialog.show();
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
                    Y.t("请输入员工姓名");
                } else {
                    name = dialog.getTextContent();
                    tv_name.setText(name);
                    dialog.dismiss();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入员工姓名");
        dialog.setTextContent(tv_name.getText().toString());
        dialog.show();
    }

    private void clickState() {
        List<String> names = new ArrayList<>();
        names.add("正常");
        names.add("离职");
        final BottomDialog bottomDialog = new BottomDialog(this);
        bottomDialog.setModles(names);
        bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
            @Override
            public void onClickItem(int pos) {
                bottomDialog.dismiss();
                if (pos == 0) {
                    state = "1";
                    tv_state.setText("正常");
                } else {
                    state = "2";
                    tv_state.setText("离职");
                }
            }

            @Override
            public void onClickCancel(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.showBottom();
    }

    private void clickNum() {
        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
            @Override
            public void onClickCancel(View v, InputDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, InputDialog dialog) {
                if (TextUtils.isEmpty(dialog.getTextContent())) {
                    Y.t("请输入员工编号");
                } else {
                    num = dialog.getTextContent();
                    tv_num.setText(num);
                    dialog.dismiss();
                }
            }

            @Override
            public void onDismiss(InputDialog dialog) {

            }
        });
        dialog.setDismissAfterClick(false);
        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
        dialog.setTextTitle("请输入员工编号");
        dialog.setTextContent(tv_num.getText().toString());
        dialog.show();
    }

    private void clickBumen() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04223);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("inst_id", inst_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<BumenModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<BumenModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<BumenModel.DataBean>> response) {
                        bumenModels = response.body().data;
                        if (bumenModels.size() > 0) {
                            showBumenSelect();
                        }
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

    private void showBumenSelect() {
        List<Object> names1 = new ArrayList<>();
        List<List<Object>> names2 = new ArrayList<>();
        for (int i = 0; i < bumenModels.size(); i++) {
            BumenModel.DataBean dataBean = bumenModels.get(i);
            names1.add(dataBean.getBranch_name());

            List<BumenModel.DataBean.BranchBean> branch = dataBean.getBranch();
            List<Object> names2Beans = new ArrayList<>();
            for (int j = 0; j < branch.size(); j++) {
                BumenModel.DataBean.BranchBean branchBean = branch.get(j);
                names2Beans.add(branchBean.getRole_name());
            }
            names2.add(names2Beans);
        }

        //条件选择器
        bumenPicker = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                BumenModel.DataBean dataBean = bumenModels.get(options1);
                BumenModel.DataBean.BranchBean branchBean = dataBean.getBranch().get(option2);
                tv_bumen.setText(dataBean.getBranch_name() + "·" + branchBean.getRole_name());
                branch_id = dataBean.getBranch_id();
                role_id = branchBean.getRole_id();
            }
        }).build();
        bumenPicker.setPicker(names1, names2);
        bumenPicker.show();
    }
}
