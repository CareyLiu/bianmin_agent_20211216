package com.shendeng_bianmin.agent.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.tuangou.TOrderModel;

import java.util.List;

public class TuanGouDingDanListAdapter extends BaseQuickAdapter<TOrderModel.DataBean, BaseViewHolder> {
    public TuanGouDingDanListAdapter(int layoutResId, @Nullable List<TOrderModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TOrderModel.DataBean item) {
        helper.setText(R.id.tv_taocan_ming, item.getShop_product_title());
        helper.setText(R.id.tv_xiadanshijian, "下单时间：" + item.getCreate_time());
        helper.setText(R.id.tv_shuliang, "数量：" + item.getPay_count());
        helper.setText(R.id.tv_money, item.getTotal_money_all());
        helper.setText(R.id.tv_state, item.getShop_pay_check_name());
        Glide.with(mContext).load(item.getIndex_photo_url()).into((ImageView) helper.getView(R.id.iv_product));
        helper.addOnClickListener(R.id.constrain);
//        helper.addOnLongClickListener(R.id.constrain);

    }
}
