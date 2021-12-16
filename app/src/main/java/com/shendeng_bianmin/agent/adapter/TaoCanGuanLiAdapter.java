package com.shendeng_bianmin.agent.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MenDianGuanLiModel;

import java.util.List;

public class TaoCanGuanLiAdapter extends BaseQuickAdapter<MenDianGuanLiModel.DataBean.TaoCanListBean, BaseViewHolder> {
    public TaoCanGuanLiAdapter(int layoutResId, @Nullable List<MenDianGuanLiModel.DataBean.TaoCanListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MenDianGuanLiModel.DataBean.TaoCanListBean item) {
//        Glide.with(mContext).load(item.getIndex_photo_url()).into((ImageView) helper.getView(R.id.iv_head));
//        helper.setText(R.id.tv_name, item.getUser_name());
//        helper.setText(R.id.tv_data, item.getPay_time());
//        helper.setText(R.id.tv_money, item.getPay_money());

        Glide.with(mContext).load(item.getWares_photo_url()).into((ImageView) helper.getView(R.id.iv_product));
        helper.setText(R.id.tv_product_name, item.getWares_name());
        helper.setText(R.id.tv_riqi, item.getShop_product_title());
        helper.setText(R.id.tv_yuanjia, item.getShop_money_old());
        helper.setText(R.id.tv_xianjia, item.getShop_money_now());
    }
}
