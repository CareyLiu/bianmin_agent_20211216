package com.shendeng_bianmin.agent.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.OrderModel;

import java.util.List;

import androidx.annotation.Nullable;

public class OrderAdapter extends BaseQuickAdapter<OrderModel.DataBean, BaseViewHolder> {


    public OrderAdapter(int layoutResId, @Nullable List<OrderModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderModel.DataBean item) {
        Glide.with(mContext).load(item.getUser_img_url()).into((ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_name, item.getUser_name());
        helper.setText(R.id.tv_state, item.getShop_pay_check_name());

        String shop_pay_check = item.getShop_pay_check();
        if (shop_pay_check.equals("5")) {
            helper.getView(R.id.ll_shouhuo).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.ll_shouhuo).setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.tv_shouhuoren_name, "收货人：" + item.getReceiver_name());

        View tv_bt = helper.getView(R.id.tv_bt);
        if (shop_pay_check.equals("1")) {
            tv_bt.setVisibility(View.GONE);
            helper.setText(R.id.tv_bt, "退款申请");
        } else if (shop_pay_check.equals("3")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "发货");
        } else if (shop_pay_check.equals("4")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "查看物流");
        } else if (shop_pay_check.equals("6")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "去评价");
        } else if (shop_pay_check.equals("7")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "查看评价");
        } else if (shop_pay_check.equals("8")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "退款审核");
        } else {
            tv_bt.setVisibility(View.GONE);
        }

//        OrderModel.DataBean.ProductBean productBean = item.getProduct().get(0);
//        Glide.with(mContext).load(productBean.getIndex_photo_url()).into((ImageView) helper.getView(R.id.iv_img));
//        helper.setText(R.id.tv_content, productBean.getShop_product_title());
//        helper.setText(R.id.tv_taocan, productBean.getProduct_title());
//        helper.setText(R.id.tv_money, "¥" + productBean.getForm_product_money());
//        helper.setText(R.id.tv_num, "×" + productBean.getPay_count());
//        helper.setText(R.id.tv_beizhu, "订单备注：" + productBean.getShop_form_text());

        helper.addOnClickListener(R.id.tv_bt);
    }
}
