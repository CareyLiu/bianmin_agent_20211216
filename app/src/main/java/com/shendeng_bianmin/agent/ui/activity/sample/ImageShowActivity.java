package com.shendeng_bianmin.agent.ui.activity.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;


public class ImageShowActivity extends BaseActivity {

    @BindView(R.id.hackyview_pager)
    HackyViewPager hackyviewPager;
    @BindView(R.id.page_number)
    TextView pageNumber;
    @BindView(R.id.full_image_root)
    RelativeLayout fullImageRoot;
    /**
     * 图片列表
     */
    private ArrayList<String> imgsUrl;
    private int position = 0;
    /**
     * PagerAdapter
     */
    private ImagePagerAdapter mAdapter;
    private ImmersionBar mImmersionBar;

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context, ArrayList<String> imgsUrl) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putStringArrayListExtra("infos", imgsUrl);
        context.startActivity(intent);
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context, ArrayList<String> imgsUrl, int postion) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putStringArrayListExtra("infos", imgsUrl);
        intent.putExtra("postion", postion);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImmersionBar = ImmersionBar.with(this);

        initView();
        initData();
        initViewPager();
        mImmersionBar();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("图片");
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    public boolean showToolBarLine() {
        return false;
    }

    private void initData() {
        Intent i = getIntent();
        if (i == null) {
            return;
        }
        imgsUrl = i.getStringArrayListExtra("infos");
        position = i.getExtras().getInt("postion", 0);
        if (imgsUrl.size() == 1) {
            pageNumber.setVisibility(View.GONE);
        } else {
            pageNumber.setVisibility(View.VISIBLE);
            pageNumber.setText("1" + "/" + imgsUrl.size());
        }
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_image_show;
    }


    private void mImmersionBar() {
        mImmersionBar
                .statusBarDarkFont(true)
                .init();
    }

    private void initView() {
        hackyviewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                pageNumber.setText((arg0 + 1) + "/" + imgsUrl.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initViewPager() {
        if (imgsUrl != null && imgsUrl.size() != 0) {
            mAdapter = new ImagePagerAdapter(getApplicationContext(), imgsUrl);
            hackyviewPager.setAdapter(mAdapter);
        }
        if (position < imgsUrl.size()) {
            hackyviewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }
}
