package com.shendeng_bianmin.agent.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.TaoCanListModel;

import java.util.List;

public class TaoCanGuanLiHomeAdapter extends BaseQuickAdapter<TaoCanListModel.DataBean.TaocanListBean, BaseViewHolder> {
    public TaoCanGuanLiHomeAdapter(int layoutResId, @Nullable List<TaoCanListModel.DataBean.TaocanListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, TaoCanListModel.DataBean.TaocanListBean item) {
        helper.addOnClickListener(R.id.constrain);
        helper.setText(R.id.tv_product_name, item.getWares_name());
        helper.setText(R.id.tv_riqi, item.getShop_product_title());
        helper.setText(R.id.tv_yuanjia, "¥" + item.getShop_money_old());
        TextView tvYuanJia = helper.getView(R.id.tv_yuanjia);
        tvYuanJia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        helper.setText(R.id.tv_xianjia, item.getShop_money_now());

        if (item.getWares_state().equals("1")) {//上架
            helper.setVisible(R.id.iv_yixiajia, false);
            Glide.with(mContext).load(item.getWares_photo_url()).placeholder(R.mipmap.nopic_preview_shop).error(R.mipmap.nopic_preview_shop).into((ImageView) helper.getView(R.id.iv_product));
        } else {//2 是下架
            helper.setVisible(R.id.iv_yixiajia, true);

            Glide.with(mContext).load(item.getWares_photo_url()).placeholder(R.mipmap.nopic_preview_shop).error(R.mipmap.nopic_preview_shop).into((ImageView) helper.getView(R.id.iv_product));
        }

    }
}
