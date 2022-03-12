package com.shendeng_bianmin.agent.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.ShangpinModel;

import java.util.List;

import androidx.annotation.Nullable;

public class ShangpinAdapter extends BaseQuickAdapter<ShangpinModel.DataBean, BaseViewHolder> {

    public ShangpinAdapter(int layoutResId, @Nullable List<ShangpinModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShangpinModel.DataBean item) {
        helper.addOnClickListener(R.id.ll_contrain);
        Glide.with(mContext).load(item.getWares_photo_url()).error(R.mipmap.nopic_preview_shop).into((ImageView) helper.getView(R.id.iv_img));
        helper.setText(R.id.tv_title_name, item.getShop_product_title());
        String money = item.getMoney();
        if (TextUtils.isEmpty(money)) {
            helper.setText(R.id.tv_red, "暂未添加商品型号");
        } else {
            helper.setText(R.id.tv_red, "¥" + money);
        }
        helper.setText(R.id.tv_xiaoliao, "销量：" + item.getWares_sales_volume());
        helper.setText(R.id.tv_time, item.getCreate_time());
        View iv_yixiajia = helper.getView(R.id.iv_yixiajia);
        String wares_state = item.getWares_state();
        if (wares_state.equals("2")) {
            iv_yixiajia.setVisibility(View.VISIBLE);
        } else {
            iv_yixiajia.setVisibility(View.GONE);
        }
    }
}
