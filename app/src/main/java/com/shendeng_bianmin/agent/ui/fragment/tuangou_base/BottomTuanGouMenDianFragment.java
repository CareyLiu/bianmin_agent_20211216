package com.shendeng_bianmin.agent.ui.fragment.tuangou_base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.basicmvp.BaseFragment;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.ui.fragment.mendian.MenDianFragment1;
import com.shendeng_bianmin.agent.ui.fragment.mendian.MenDianFragment2;
import com.shendeng_bianmin.agent.ui.fragment.mendian.MyFragmentPagerAdapter;
import com.shendeng_bianmin.agent.ui.widget.MenDianViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BottomTuanGouMenDianFragment extends BaseFragment {
    public static final String TAG = "BottomShangPinFragment";
    @BindView(R.id.tv_mendian_guanli)
    TextView tvMendianGuanli;
    @BindView(R.id.view_mendianguanli_line)
    View viewMendianguanliLine;
    @BindView(R.id.tv_pingjia_xiangqing)
    TextView tvPingjiaXiangqing;
    @BindView(R.id.view_pingjiaxiangqing_line)
    View viewPingjiaxiangqingLine;
    @BindView(R.id.vp_viewpager)
    MenDianViewPager vpViewpager;
    List<Fragment> aList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice message) {
            }
        }));

    }

    public static BottomTuanGouMenDianFragment newInstance() {
        Bundle args = new Bundle();
        BottomTuanGouMenDianFragment fragment = new BottomTuanGouMenDianFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frag_mian_mendian;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initLogic() {

        tvMendianGuanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMendianguanliLine.setVisibility(View.VISIBLE);
                viewPingjiaxiangqingLine.setVisibility(View.GONE);

                //UIHelper.ToastMessage(getActivity(), "点击了门店管理");
                vpViewpager.setCurrentItem(0);
            }
        });

        tvPingjiaXiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMendianguanliLine.setVisibility(View.GONE);
                viewPingjiaxiangqingLine.setVisibility(View.VISIBLE);
                vpViewpager.setCurrentItem(1);
            }
        });


        aList = new ArrayList<Fragment>(); //new一个List<Fragment>
        aList.add(new MenDianFragment1());
        aList.add(new MenDianFragment2());

        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(getFragmentManager(), aList);
        vpViewpager.setAdapter(mAdapter);
        vpViewpager.setCurrentItem(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void immersionInit(ImmersionBar mImmersionBar) {
        mImmersionBar
                .titleBar(toolbar).fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    protected boolean immersionEnabled() {
        return true;
    }

}
