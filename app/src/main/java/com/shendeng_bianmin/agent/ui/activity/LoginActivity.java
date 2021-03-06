package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.AppConfig;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.callback.DialogCallback;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.BottomDialog;
import com.shendeng_bianmin.agent.dialog.BottomDialogView;
import com.shendeng_bianmin.agent.dialog.FuWuDialog;
import com.shendeng_bianmin.agent.model.LoginUser;
import com.shendeng_bianmin.agent.model.Message;
import com.shendeng_bianmin.agent.ui.HomeBasicActivity;
import com.shendeng_bianmin.agent.ui.HomeBasicTuanGouActivity;
import com.shendeng_bianmin.agent.ui.widget.DoubleClickExitHelper;
import com.shendeng_bianmin.agent.util.TimeCount;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_pwd)
    EditText ed_pwd;
    @BindView(R.id.tv_yzm)
    TextView tv_yzm;
    @BindView(R.id.ll_pwd)
    LinearLayout ll_pwd;
    @BindView(R.id.tv_qiehuan)
    TextView tv_qiehuan;
    @BindView(R.id.tv_zhaohui)
    TextView tv_zhaohui;
    @BindView(R.id.ll_qiehuan)
    LinearLayout ll_qiehuan;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.tv_yinsi)
    TextView tv_yinsi;
    @BindView(R.id.tv_yonghu)
    TextView tv_yonghu;

    private TimeCount timeCount;
    private String smsId;//???????????????id
    private String req_type;//????????????:1.????????????2.?????????????????????

    DoubleClickExitHelper doubleClick;
    private FuWuDialog fuWuDialog;

    @Override
    public int getContentViewResId() {
        return R.layout.act_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        req_type = "2";
        timeCount = new TimeCount(60000, 1000, tv_yzm);
//        ed_phone.setText("13351102680");
//        ed_pwd.setText("13351102680");

//                ed_phone.setText("18249030297");
//                ed_pwd.setText("123456");


        //        ed_phone.setText("15244772616");
        //        ed_pwd.setText("15244772616");


        doubleClick = new DoubleClickExitHelper(this);


        String yonghuxieyi = PreferenceHelper.getInstance(mContext).getString("yonghuxieyi", "");
        if (!yonghuxieyi.equals("1")) {
            fuWuDialog = new FuWuDialog(mContext, new FuWuDialog.FuWuDiaLogClikListener() {
                @Override
                public void onClickCancel() {
//                    AppManager.getAppManager().AppExit(getContext());
                    fuWuDialog.dismiss();
                }

                @Override
                public void onClickConfirm() {
                    fuWuDialog.dismiss();
                }

                @Override
                public void onDismiss(FuWuDialog dialog) {
                    PreferenceHelper.getInstance(mContext).putString("yonghuxieyi", "1");
                }

                @Override
                public void fuwu() {
                    DefaultX5WebViewActivity.actionStart(mContext, "https://shop.hljsdkj.com/shop_new/user_agreement");
                }

                @Override
                public void yinsixieyi() {
                    DefaultX5WebViewActivity.actionStart(mContext, "https://shop.hljsdkj.com/shop_new/privacy_clause");
                }
            });

            fuWuDialog.setCancelable(false);
            fuWuDialog.show();
        }
    }

    @OnClick({R.id.tv_yzm, R.id.tv_qiehuan, R.id.tv_zhaohui, R.id.bt_login, R.id.tv_yinsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yzm:
                get_code();
                break;
            case R.id.tv_qiehuan:
                qiehuan();
                break;
            case R.id.tv_zhaohui:
                LoginYzmActivity.actionStart(this);
                break;
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_yinsi:
                DefaultX5WebViewActivity.actionStart(LoginActivity.this, "https://shop.hljsdkj.com/shop_new/privacy_clause");
                break;
            case R.id.tv_yonghu:
                DefaultX5WebViewActivity.actionStart(LoginActivity.this, "https://shop.hljsdkj.com/shop_new/user_agreement");
                break;
        }
    }

    private void qiehuan() {
        List<String> names = new ArrayList<>();
        names.add("????????????????????????");
        names.add("?????????????????????????????????");
        final BottomDialog bottomDialog = new BottomDialog(this);
        bottomDialog.setModles(names);
        bottomDialog.setClickListener(new BottomDialogView.ClickListener() {
            @Override
            public void onClickItem(int pos) {
                bottomDialog.dismiss();
                if (pos == 0) {
                    req_type = "2";
                    ed_pwd.setHint("??????????????????");
                    tv_yzm.setVisibility(View.VISIBLE);
                } else {
                    req_type = "1";
                    ed_pwd.setHint("?????????????????????");
                    tv_yzm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onClickCancel(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.showBottom();
    }


    /**
     * ?????????????????????
     */
    private void get_code() {
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            Y.t("?????????????????????");
        } else {
            showProgressDialog();
            Map<String, String> map = new HashMap<>();
            map.put("code", Urls.code_00001);
            map.put("key", Urls.KEY);
            map.put("user_phone", ed_phone.getText().toString());
            map.put("mod_id", "0110");//????????????
            map.put("subsystem_id", "bmgx");

            Gson gson = new Gson();
            OkGo.<AppResponse<Message.DataBean>>post(Urls.SERVER_URL + "msg")
                    .tag(this)//
                    .upJson(gson.toJson(map))
                    .execute(new JsonCallback<AppResponse<Message.DataBean>>() {
                        @Override
                        public void onSuccess(Response<AppResponse<Message.DataBean>> response) {
                            Y.t("?????????????????????");
                            timeCount.start();
                            if (response.body().data.size() > 0) {
                                smsId = response.body().data.get(0).getSms_id();
                            }
                        }

                        @Override
                        public void onError(Response<AppResponse<Message.DataBean>> response) {
                            Y.tError(response);
                            timeCount.cancel();
                            timeCount.onFinish();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            dismissProgressDialog();
                        }
                    });
        }
    }

    /**
     * ??????
     */
    private void login() {
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            Y.t("?????????????????????");
            return;
        }

        if (TextUtils.isEmpty(ed_pwd.getText().toString())) {
            if (req_type.equals("1")) {
                Y.t("?????????????????????");
            } else {
                Y.t("??????????????????");
            }
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04310);
        map.put("key", Urls.KEY);
        map.put("req_type", req_type);
        map.put("user_phone", ed_phone.getText().toString());
        map.put("subsystem_id", "bmgx");
        switch (req_type) {
            case "1"://????????????
                map.put("user_pwd", ed_pwd.getText().toString());
                break;
            case "2"://?????????????????????
                map.put("sms_id", smsId);
                map.put("sms_code", ed_pwd.getText().toString());
                break;
        }
        Gson gson = new Gson();
        OkGo.<AppResponse<LoginUser.DataBean>>post(Urls.LOGIN)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new DialogCallback<AppResponse<LoginUser.DataBean>>(this) {
                    @Override
                    public void onSuccess(Response<AppResponse<LoginUser.DataBean>> response) {
                        PreferenceHelper.getInstance(LoginActivity.this).putString("user_phone", ed_phone.getText().toString() + "");
                        UserManager.getManager(LoginActivity.this).saveUser(response.body().data.get(0));

                        String rongYunTouken = UserManager.getManager(mContext).getRongYun();

                        if (!StringUtils.isEmpty(rongYunTouken)) {
                            connectRongYun(response.body().data.get(0).getToken_rong());
                        }
                        //typeList	business_type   	????????????
                        //1.??????  2.??????
                        if (response.body().data.get(0).getTypeList().size() > 1) {

                            ChooseRoleActivity.actionStart(mContext);

                        } else {
                            LoginUser.DataBean dataBean = response.body().data.get(0);
                            List<LoginUser.DataBean.TypeListBean> typeList = dataBean.getTypeList();
//                            if (typeList != null) {
//                                if (typeList.get(0).getBusiness_type().equals("1")) {
//                                    startActivity(new Intent(LoginActivity.this, HomeBasicActivity.class));
//                                    PreferenceHelper.getInstance(mContext).putString(AppConfig.ROLE, String.valueOf(response.body().data.get(0).getTypeList().get(0).getBusiness_type()));
//                                } else if (typeList.get(0).getBusiness_type().equals("2")) {
//                                    HomeBasicTuanGouActivity.actionStart(LoginActivity.this);
//                                    PreferenceHelper.getInstance(mContext).putString(AppConfig.ROLE, String.valueOf(response.body().data.get(0).getTypeList().get(0).getBusiness_type()));
//                                }
//                            }
                            startActivity(new Intent(LoginActivity.this, HomeBasicActivity.class));
                            PreferenceHelper.getInstance(mContext).putString(AppConfig.ROLE, "1");//?????????1
                        }
                        PreferenceHelper.getInstance(mContext).putString(AppConfig.ROLE_NUMBER, String.valueOf(response.body().data.get(0).getTypeList().size()));
                    }

                    @Override
                    public void onError(Response<AppResponse<LoginUser.DataBean>> response) {
                        Y.tError(response);
                    }
                });
    }


    /**
     * ????????????Activty????????????Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public void connectRongYun(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallbackEx() {
            /**
             * ???????????????.
             * @param code ?????????????????????. DATABASE_OPEN_SUCCESS ?????????????????????; DATABASE_OPEN_ERROR ?????????????????????
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                Log.i("rongYun", "?????????????????????");
            }

            /**
             * token ??????
             */
            @Override
            public void onTokenIncorrect() {
                Log.i("rongYun", "token ??????");
            }

            /**
             * ????????????
             * @param userId ???????????? ID
             */
            @Override
            public void onSuccess(String userId) {
                //UIHelper.ToastMessage(mContext, "??????????????????");
                Log.i("rongYun", "??????????????????");
                PreferenceHelper.getInstance(mContext).putString(AppConfig.RONGYUN_TOKEN, token);
            }

            /**
             * ????????????
             * @param errorCode ?????????
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("rongYun", "??????????????????");
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            /*
             * ?????????if?????????????????????event.getAction() == KeyEvent.ACTION_DOWN?????????
             * ???????????????????????????ACTION_DOWN???ACTION_UP??????????????????????????????
             * ????????????????????????????????????????????????
             */
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //exitApp();
                doubleClick.onKeyDown(event.getKeyCode(), event);
            }
            //?????????????????????????????????????????????????????????????????????HOME?????????????????????
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
