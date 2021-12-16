package com.shendeng_bianmin.agent.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;

import java.util.List;

import androidx.annotation.Nullable;

public class ShangpinZiAdapter extends BaseQuickAdapter<ShangpinDetailsModel.DataBean.PackageListBean, BaseViewHolder> {


    public ShangpinZiAdapter(int layoutResId, @Nullable List<ShangpinDetailsModel.DataBean.PackageListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShangpinDetailsModel.DataBean.PackageListBean item) {
        helper.addOnClickListener(R.id.tv_edit_click);
        helper.addOnClickListener(R.id.iv_add_img);

        helper.setText(R.id.ed_xinghao, item.getProduct_title());
        helper.setText(R.id.ed_yuan_money, item.getMoney_old());
        helper.setText(R.id.ed_new_money, item.getMoney_now());
        helper.setText(R.id.ed_kucun, item.getShop_product_count());

        String red_set_type = item.getRed_set_type();
        if (red_set_type.equals("1")) {
            helper.getView(R.id.ll_fanxian).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hongbao, item.getRed_packet());
        } else {
            helper.getView(R.id.ll_fanxian).setVisibility(View.GONE);
        }

        String index_photo_url = item.getIndex_photo_url();
        ImageView iv_add = (ImageView) helper.getView(R.id.iv_add_img);
        if (TextUtils.isEmpty(index_photo_url)) {
            iv_add.setImageResource(R.mipmap.shoppicture_icon_add);
        } else {
            Glide.with(mContext).load(index_photo_url).into(iv_add);
        }
    }
}
