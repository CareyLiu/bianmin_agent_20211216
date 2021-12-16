package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.TishiDialog;
import com.shendeng_bianmin.agent.model.PingjiaModel;
import com.shendeng_bianmin.agent.util.TimeUtils;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderPingjiaActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_pingjia)
    TextView tv_pingjia;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_taocan)
    TextView tv_taocan;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.ll_pinglun)
    LinearLayout ll_pinglun;
    @BindView(R.id.ed_pinglun)
    EditText ed_pinglun;
    @BindView(R.id.ll_pinglun_send)
    LinearLayout ll_pinglun_send;
    @BindView(R.id.rl_msg)
    RelativeLayout rl_msg;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.iv_head_shang)
    CircleImageView iv_head_shang;
    @BindView(R.id.tv_name_shang)
    TextView tv_name_shang;
    @BindView(R.id.tv_time_shang)
    TextView tv_time_shang;
    @BindView(R.id.tv_pingjia_shang)
    TextView tv_pingjia_shang;
    @BindView(R.id.tv_send)
    TextView tv_send;
    private String shop_form_id;
    private PingjiaModel.DataBean dataBean;
    private String shop_to_score;
    private String shop_to_text;

    @Override
    public int getContentViewResId() {
        return R.layout.act_order_pingjia;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("评价");
    }

    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context, String shop_form_id) {
        Intent intent = new Intent();
        intent.setClass(context, OrderPingjiaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("shop_form_id", shop_form_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        shop_form_id = getIntent().getStringExtra("shop_form_id");
        shop_to_score = "5";
        shop_to_text = "";
        getNet();
    }

    private void getNet() {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04320);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        Gson gson = new Gson();
        OkGo.<AppResponse<PingjiaModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<PingjiaModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<PingjiaModel.DataBean>> response) {
                        dataBean = response.body().data.get(0);
                        Glide.with(mContext).load(dataBean.getUser_img_url()).into(iv_head);
                        tv_name.setText(dataBean.getUser_name());
                        String user_to_text = dataBean.getUser_to_text();
                        if (TextUtils.isEmpty(user_to_text)) {
                            tv_pingjia.setText("买家还没有留下任何痕迹哦");
                        } else {
                            tv_pingjia.setText(user_to_text);
                        }
                        String user_to_time = dataBean.getUser_to_time();
                        if (TextUtils.isEmpty(user_to_time)) {
                            tv_time.setText("暂无评价时间信息");
                        } else {
                            tv_time.setText(user_to_time);
                        }

                        tv_title_name.setText(dataBean.getShop_product_title());
                        tv_taocan.setText(dataBean.getProduct_title());
                        Glide.with(mContext).load(dataBean.getIndex_photo_url()).into(iv_img);
                        String shop_to_text = dataBean.getShop_to_text();
                        if (TextUtils.isEmpty(shop_to_text)) {
                            ll_pinglun.setVisibility(View.GONE);
                            ll_pinglun_send.setVisibility(View.VISIBLE);
                        } else {
                            ll_pinglun.setVisibility(View.VISIBLE);
                            ll_pinglun_send.setVisibility(View.GONE);

                            Glide.with(mContext).load(dataBean.getInst_img_url()).into(iv_head_shang);
                            tv_name_shang.setText(dataBean.getInst_name());
                            tv_time_shang.setText(dataBean.getShop_to_time());
                            tv_pingjia_shang.setText(shop_to_text);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    @Override
    protected void onKeyboardVisibilityChange(boolean visible, int height) {
        super.onKeyboardVisibilityChange(visible, height);
        if (visible) {
            rl_msg.scrollBy(0, height);
        } else {
            rl_msg.scrollTo(0, 0);
        }
    }

    @OnClick(R.id.tv_send)
    public void onViewClicked() {
        shop_to_text = ed_pinglun.getText().toString();
        if (TextUtils.isEmpty(shop_to_text)) {
            Y.t("请输入评价内容");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04317);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(this).getAppToken());
        map.put("shop_form_id", shop_form_id);
        map.put("shop_to_score", shop_to_score);
        map.put("shop_to_text", shop_to_text);
        Gson gson = new Gson();
        OkGo.<AppResponse<PingjiaModel.DataBean>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<PingjiaModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<PingjiaModel.DataBean>> response) {
                        TishiDialog dialog = new TishiDialog(mContext, new TishiDialog.TishiDialogListener() {
                            @Override
                            public void onClickCancel(View v, TishiDialog dialog) {

                            }

                            @Override
                            public void onClickConfirm(View v, TishiDialog dialog) {

                            }

                            @Override
                            public void onDismiss(TishiDialog dialog) {
                                ll_pinglun.setVisibility(View.VISIBLE);
                                ll_pinglun_send.setVisibility(View.GONE);

                                Glide.with(mContext).load(dataBean.getInst_img_url()).into(iv_head_shang);
                                tv_name_shang.setText(dataBean.getInst_name());
                                tv_time_shang.setText(TimeUtils.getCurrentTime());
                                tv_pingjia_shang.setText(shop_to_text);
                            }
                        });
                        dialog.setTextTitle("成功");
                        dialog.setTextCont("已成功回复评论");
                        dialog.setTextConfirm("知道了");
                        dialog.setTextCancel("");
                        dialog.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }
}
