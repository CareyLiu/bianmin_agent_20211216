package com.shendeng_bianmin.agent.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.billy.android.loading.Gloading;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.basicmvp.BasicModel;
import com.shendeng_bianmin.agent.basicmvp.BasicPresenter;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.dialog.LordingDialog;
import com.shendeng_bianmin.agent.util.RxBus;
import com.shendeng_bianmin.agent.util.RxUtils;
import com.shendeng_bianmin.agent.util.SDSizeListener;
import com.shendeng_bianmin.agent.util.SDWindowSizeListener;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity<T extends BasicPresenter, E extends BasicModel> extends BasicActivity<T, E> {

    protected ImmersionBar mImmersionBar;

    protected CompositeSubscription _subscriptions = new CompositeSubscription();

    protected Gloading.Holder mHolder;

    /**
     * make a Gloading.Holder wrap with current activity by default
     * override this method in subclass to do special initialization
     */
    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = Gloading.getDefault().wrap(this).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }

    protected void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    public void showLoading() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoading();
    }

    public void showLoadSuccess() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadSuccess();

    }

    public void showLoadFailed() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed();
    }


    @Override
    public int getContentViewResId() {
        return R.layout.basic_layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //??????????????????Activity??????

    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void initImmersion() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
        }
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
        initWindowSizeListener();//???????????????
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!_subscriptions.isUnsubscribed()) {
            _subscriptions.unsubscribe();
        }
        rootView = null;
        AppManager.getAppManager().finishActivity(this);
        //?????? Tag ????????????
        OkGo.getInstance().cancelTag(this);

    }

    /**
     * ??????????????????
     */
    public Observable<Notice> toObservable() {
        return RxBus.getDefault().toObservable(Notice.class);
    }

    /**
     * ????????????
     */
    public void sendRx(Notice msg) {
        RxBus.getDefault().sendRx(msg);
    }


    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    /**
     *        Map<String, String> map = new HashMap<>();
     *                 map.put("code", "04154");
     *                 map.put("key", Urls.key);
     *                 map.put("token", UserManager.getManager(DingDanShenQingTuikuanActivity.this).getAppToken());
     *                 map.put("shop_form_id", shop_form_id);
     *                 map.put("refund_type", refund_type);
     *                 map.put("refund_cause", refund_cause);
     *                 map.put("refund_phone", refund_phone);
     *
     *                 Gson gson = new Gson();
     *                 Log.e("map_data", gson.toJson(map));
     *                 OkGo.<AppResponse<TaoKeDetailList.DataBean>>post(TAOKELIST)
     *                         .tag(this)//
     *                         .upJson(gson.toJson(map))
     *                         .execute(new JsonCallback<AppResponse<TaoKeDetailList.DataBean>>() {
     *                             @Override
     *                             public void onSuccess(Response<AppResponse<TaoKeDetailList.DataBean>> response) {
     *                                 // Log.i("response_data", new Gson().toJson(response.body()));
     *
     *                             }
     *                         });
     */


    /**
     * ?????????????????????Activty
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * ?????????????????????Activty???????????????Bundle??????
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    //???????????????
    private SDWindowSizeListener windowSizeListener = new SDWindowSizeListener();

    private void initWindowSizeListener() {//???????????????
        windowSizeListener.listen(this, new SDSizeListener<View>() {
            @Override
            public void onWidthChanged(int newWidth, int oldWidth, int differ, View view) {

            }

            @Override
            public void onHeightChanged(int newHeight, int oldHeight, int differ, View view) {
                if (oldHeight > 0 && newHeight > 0) {
                    int absDiffer = Math.abs(differ);
                    if (absDiffer > 400) {
                        if (differ > 0) {
                            //????????????
                            onKeyboardVisibilityChange(false, absDiffer);
                        } else {
                            // ????????????
                            onKeyboardVisibilityChange(true, absDiffer);
                        }
                    }
                }
            }
        });
    }

    protected void onKeyboardVisibilityChange(boolean visible, int height) {//???????????????

    }


    private LordingDialog lordingDialog;


    public void showProgressDialog() {
        showProgressDialog("");
    }


    public void showProgressDialog(String msg) {
        if (lordingDialog == null) {
            lordingDialog = new LordingDialog(mContext);
        }
        lordingDialog.setTextMsg(msg);

        if (!lordingDialog.isShowing()) {
            lordingDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (lordingDialog != null) {
            try {
                lordingDialog.dismiss();
            } catch (Exception e) {
            }
        }
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
