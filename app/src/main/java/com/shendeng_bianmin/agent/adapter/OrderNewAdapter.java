package com.shendeng_bianmin.agent.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.OrderModel;
import com.shendeng_bianmin.agent.model.WaresListBean;


import java.util.List;

public class OrderNewAdapter extends BaseSectionQuickAdapter<WaresListBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public OrderNewAdapter(int layoutResId, int sectionHeadResId, List<WaresListBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaresListBean item) {

        String shop_pay_check = item.shop_pay_check;
        if (shop_pay_check.equals("5")) {
            helper.getView(R.id.ll_shouhuo).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.ll_shouhuo).setVisibility(View.VISIBLE);
        }

        if (item.zuiHouYige.equals("1")) {
            helper.setVisible(R.id.ll_shouhuo, true);
        } else {
            //  helper.setVisible(R.id.ll_shouhuo, false);
            LinearLayout linearLayout = helper.getView(R.id.ll_shouhuo);
            linearLayout.setVisibility(View.GONE);
        }

        View tv_bt = helper.getView(R.id.tv_bt);
        if (shop_pay_check.equals("1")) {
            tv_bt.setVisibility(View.GONE);
            helper.setText(R.id.tv_bt, "退款申请");
        } else if (shop_pay_check.equals("3")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "去发货");
        } else if (shop_pay_check.equals("4")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "点击完成");
        } else if (shop_pay_check.equals("6")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "去评价");
        } else if (shop_pay_check.equals("7")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "已完成");
        } else if (shop_pay_check.equals("8")) {
            tv_bt.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bt, "退款审核");
        } else {
            tv_bt.setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_shouhuoren_name, "收货人：" + item.receiver_name);
        Glide.with(mContext).load(item.index_photo_url).into((ImageView) helper.getView(R.id.iv_img));
        helper.setText(R.id.tv_content, item.shop_product_title);
        helper.setText(R.id.tv_taocan, item.shop_product_title);
        helper.setText(R.id.tv_money, "¥" + item.form_product_money);
        helper.setText(R.id.tv_num, "×" + item.pay_count);
        if (StringUtils.isEmpty(item.shop_form_text)) {
            helper.setText(R.id.tv_beizhu, "订单备注：" + "暂无");
        } else {
            helper.setText(R.id.tv_beizhu, "订单备注：" + item.shop_form_text);
        }

        helper.addOnClickListener(R.id.tv_bt);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, WaresListBean item) {

        Glide.with(mContext).load(item.user_img_url).into((ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_name, item.user_name);
        helper.setText(R.id.tv_state, item.shop_pay_check_name);


    }
}
