package com.shendeng_bianmin.agent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.TianJiaLeiMuAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.app.ConstanceValue;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.callback.JsonCallback;
import com.shendeng_bianmin.agent.config.AppResponse;
import com.shendeng_bianmin.agent.config.UserManager;
import com.shendeng_bianmin.agent.dialog.InputDialog;
import com.shendeng_bianmin.agent.model.LeimuModel;
import com.shendeng_bianmin.agent.model.TianJiaLeiMuModel;
import com.shendeng_bianmin.agent.model.Upload;
import com.shendeng_bianmin.agent.util.UIHelper;
import com.shendeng_bianmin.agent.util.Urls;
import com.shendeng_bianmin.agent.util.Y;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TianJiaLeiMuActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.rlv_leimu_list)
    RecyclerView rlvLeimuList;
    @BindView(R.id.tv_bianji)
    TextView tvBianji;
    @BindView(R.id.ll_tianjiaxinleimu)
    LinearLayout llTianjiaxinleimu;

    private boolean bianJiFlag = true; //true 显示编辑 false 显示完成

    List<TianJiaLeiMuModel> mDatas = new ArrayList<>();
    TianJiaLeiMuAdapter tianJiaLeiMuAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_tianjia_leimu;

    }

    @Override
    public boolean showToolBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        tvBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bianJiFlag) {

                    tvBianji.setText("完成");


                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).bianJiFlag = false;
                    }

                    bianJiFlag = false;

                    if (tianJiaLeiMuAdapter != null) {
                        tianJiaLeiMuAdapter.notifyDataSetChanged();
                    }

                } else {
                    bianJiFlag = true;
                    tvBianji.setText("编辑");
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).bianJiFlag = true;
                    }
                    if (tianJiaLeiMuAdapter != null) {
                        tianJiaLeiMuAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        llTianjiaxinleimu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
                    @Override
                    public void onClickCancel(View v, InputDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickConfirm(View v, InputDialog dialog) {
                        if (TextUtils.isEmpty(dialog.getTextContent())) {
                            Y.t("请输入商品名");
                        } else {
                            str = dialog.getTextContent();
                            //   etTaocanjianjie.setText(dialog.getTextContent());
                            tianJiaShangPin(str);
                            dialog.dismiss();

                        }
                    }


                    @Override
                    public void onDismiss(InputDialog dialog) {

                    }
                });
                dialog.setDismissAfterClick(false);
                dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
                dialog.setTextTitle("请输入简介");
                dialog.setTextContent(str);
                dialog.show();

            }
        });
        //获得列表
        getLieBieLieBiaoNet();


    }

    private void getLieBieLieBiaoNet() {

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04403);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("itemtype_id", "5");

        Gson gson = new Gson();
        OkGo.<AppResponse<TianJiaLeiMuModel>>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<TianJiaLeiMuModel>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<TianJiaLeiMuModel>> response) {
                        //UIHelper.ToastMessage(mContext, "添加成功");
                        //   Glide.with(MenDianHeiSeActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivImage);
                        dismissProgressDialog();

                        mDatas = response.body().data;
                        if (mDatas.size() == 0) {
                            llTianjiaxinleimu.setVisibility(View.VISIBLE);
                        }
                        tianJiaLeiMuAdapter = new TianJiaLeiMuAdapter(R.layout.item_tianjialeimu, mDatas);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TianJiaLeiMuActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rlvLeimuList.setLayoutManager(linearLayoutManager);
                        rlvLeimuList.setAdapter(tianJiaLeiMuAdapter);
                        tianJiaLeiMuAdapter.setNewData(mDatas);
                        tianJiaLeiMuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                switch (view.getId()) {

                                    case R.id.tv_xuanze:

                                        TianJiaLeiMuModel tianJiaLeiMuModel3 = (TianJiaLeiMuModel) adapter.getData().get(position);
                                        if (tianJiaLeiMuModel3.bianJiFlag) {
                                            Notice notice = new Notice();
                                            notice.type = ConstanceValue.MSG_TIANJIALEIMU;
                                            notice.content = tianJiaLeiMuModel3;
                                            sendRx(notice);
                                            finish();
                                        } else {//这里走删除接口
                                            shanChuLeiBie(tianJiaLeiMuModel3.item_id);
                                        }

                                        break;

                                    case R.id.tv_bianji:
                                        TianJiaLeiMuModel tianJiaLeiMuModel4 = (TianJiaLeiMuModel) adapter.getData().get(position);
                                        InputDialog dialog = new InputDialog(mContext, new InputDialog.TishiDialogListener() {
                                            @Override
                                            public void onClickCancel(View v, InputDialog dialog) {
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onClickConfirm(View v, InputDialog dialog) {
                                                if (TextUtils.isEmpty(dialog.getTextContent())) {
                                                    Y.t("请输入商品名");
                                                } else {
                                                    str = dialog.getTextContent();
                                                    //   etTaocanjianjie.setText(dialog.getTextContent());
                                                    xiuGaiShangPin(str, tianJiaLeiMuModel4.item_id);
                                                    dialog.dismiss();

                                                }
                                            }


                                            @Override
                                            public void onDismiss(InputDialog dialog) {

                                            }
                                        });
                                        dialog.setDismissAfterClick(false);
                                        dialog.setTextInput(InputType.TYPE_CLASS_TEXT);
                                        dialog.setTextTitle("请输入您要修改的商品名称");
                                        dialog.setTextContent(str);
                                        dialog.show();

                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Response<AppResponse<TianJiaLeiMuModel>> response) {
                        Y.tError(response);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse<TianJiaLeiMuModel>, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });

    }

    public String str;

    private void tianJiaShangPin(String str) {

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04401);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("item_name", str);
        Gson gson = new Gson();
        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(final Response<AppResponse> response) {
                        UIHelper.ToastMessage(mContext, "添加成功");
                        //   Glide.with(MenDianHeiSeActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivImage);
                        dismissProgressDialog();

                        getLieBieLieBiaoNet();
                        bianJiFlag = true;
                        tvBianji.setText("编辑");
                    }

                    @Override
                    public void onError(Response<AppResponse> response) {
                        Y.tError(response);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });

    }

    private void xiuGaiShangPin(String name, String item_id) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04402);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("item_name", name);
        map.put("item_id", item_id);
        Gson gson = new Gson();

        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(final Response<AppResponse> response) {
                        UIHelper.ToastMessage(mContext, "修改成功");

                        //   Glide.with(MenDianHeiSeActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivImage);
                        dismissProgressDialog();

                        getLieBieLieBiaoNet();

                        bianJiFlag = true;
                        tvBianji.setText("编辑");
                    }

                    @Override
                    public void onError(Response<AppResponse> response) {
                        Y.tError(response);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onStart(Request<AppResponse, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }
                });

    }


    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, TianJiaLeiMuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void shanChuLeiBie(String leiBieId) {

        Map<String, String> map = new HashMap<>();
        map.put("code", Urls.code_04404);
        map.put("key", Urls.KEY);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("item_id", leiBieId);
        Gson gson = new Gson();


        OkGo.<AppResponse>post(Urls.WORKER)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse>() {
                    @Override
                    public void onSuccess(final Response<AppResponse> response) {
                        // UIHelper.ToastMessage(mContext, "删除成功");
                        //   Glide.with(MenDianHeiSeActivity.this).load(response.body().data.get(0).getFile_all_url()).into(ivImage);
                        if (response.body().msg_code.equals("0001")) {
                            UIHelper.ToastMessage(mContext, response.body().msg);
                        } else {
                            getLieBieLieBiaoNet();
                            bianJiFlag = true;
                            tvBianji.setText("编辑");
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<AppResponse> response) {
                        Y.tError(response);
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
