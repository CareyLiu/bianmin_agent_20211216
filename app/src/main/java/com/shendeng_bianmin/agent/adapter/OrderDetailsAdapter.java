package com.shendeng_bianmin.agent.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.OrderDetailsModel;
import com.shendeng_bianmin.agent.model.OrderDetailsModel1;
import com.shendeng_bianmin.agent.model.OrderModel;

import java.util.List;

public class OrderDetailsAdapter extends BaseQuickAdapter<OrderDetailsModel1.DataBean.WaresListBean, BaseViewHolder> {


    public OrderDetailsAdapter(int layoutResId, @Nullable List<OrderDetailsModel1.DataBean.WaresListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailsModel1.DataBean.WaresListBean item) {
        Glide.with(mContext).load(item.getIndex_photo_url()).into((ImageView) helper.getView(R.id.iv_img));
        helper.setText(R.id.tv_content, item.getShop_product_title());
        helper.setText(R.id.tv_taocan, item.getShop_product_title());
        helper.setText(R.id.tv_money, "¥" + item.getForm_product_money());
        helper.setText(R.id.tv_num, "×" + item.getPay_count());
//        helper.setText(R.id.tv_beizhu, "订单备注：" + item.get());
    }
}
