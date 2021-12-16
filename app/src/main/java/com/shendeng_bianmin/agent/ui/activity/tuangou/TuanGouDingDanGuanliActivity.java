package com.shendeng_bianmin.agent.ui.activity.tuangou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.adapter.NewsFragmentPagerAdapter;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.ui.fragment.mendian.OrderListFragment;
import com.shendeng_bianmin.agent.ui.view.CustomViewPager;
import com.shendeng_bianmin.agent.ui.view.magicindicator.MagicIndicator;
import com.shendeng_bianmin.agent.ui.view.magicindicator.ViewPagerHelper;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.shendeng_bianmin.agent.ui.view.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TuanGouDingDanGuanliActivity extends BaseActivity {

    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator4;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    List<String> tagList;
    ArrayList<Fragment> messageListFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagList = new ArrayList<>();
        tagList.add("全部");
        tagList.add("待付款");
        tagList.add("到店消费");
        tagList.add("待评价");
        tagList.add("已评价");
        tagList.add("退款");
//        tagList.add("退款中");
//        tagList.add("已关闭");

        setTopAdapter();
        initMagicIndicator1(tagList);
    }

    private void setTopAdapter() {
        // messageListFragments.clear();//清空
        int count = tagList.size();
        for (int i = 0; i < count; i++) {
            Bundle data = new Bundle();
            data.putString("title", tagList.get(i));
            OrderListFragment newfragment = new OrderListFragment();
            newfragment.setArguments(data);
            messageListFragments.add(newfragment);
        }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager(), messageListFragments);
        viewPager.setAdapter(mAdapetr);

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_tuan_gou_ding_dan_guanli;
    }

    @Override
    public boolean showToolBar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("订单管理");
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context leixing 0新建进入 1 二次进入
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TuanGouDingDanGuanliActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void initMagicIndicator1(final List<String> list) {
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator4);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list == null ? 0 : list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(TuanGouDingDanGuanliActivity.this.getResources().getColor(R.color.black_666666));
                simplePagerTitleView.setSelectedColor(TuanGouDingDanGuanliActivity.this.getResources().getColor(R.color.color_FFFC0100));
                simplePagerTitleView.setText(list.get(index));
                //   App.scaleScreenHelper.loadViewSize(simplePagerTitleView, 35);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(TuanGouDingDanGuanliActivity.this.getResources().getColor(R.color.color_FFFC0100));
                return linePagerIndicator;
            }
        });
//        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
