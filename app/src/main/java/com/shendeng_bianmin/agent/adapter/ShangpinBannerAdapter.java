package com.shendeng_bianmin.agent.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;

import java.util.List;

import androidx.annotation.Nullable;

public class ShangpinBannerAdapter extends BaseQuickAdapter<ShangpinDetailsModel.DataBean.ImgListBean, BaseViewHolder> {


    public ShangpinBannerAdapter(int layoutResId, @Nullable List<ShangpinDetailsModel.DataBean.ImgListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShangpinDetailsModel.DataBean.ImgListBean item) {
        String img_url = item.getImg_url();
        ImageView iv_main = (ImageView) helper.getView(R.id.iv_main);
        if (TextUtils.isEmpty(img_url)) {
            iv_main.setImageResource(R.mipmap.shoppicture_icon_add);
        } else {
            Glide.with(mContext).load(img_url).into(iv_main);
        }

        helper.addOnClickListener(R.id.iv_main);
    }
}
