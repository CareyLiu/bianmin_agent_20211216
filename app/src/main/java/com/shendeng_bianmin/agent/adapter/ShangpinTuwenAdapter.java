package com.shendeng_bianmin.agent.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.util.Y;

import java.util.List;

import androidx.annotation.Nullable;

public class ShangpinTuwenAdapter extends BaseQuickAdapter<ShangpinDetailsModel.DataBean.ImgTextListBeen, BaseViewHolder> {


    public ShangpinTuwenAdapter(int layoutResId, @Nullable List<ShangpinDetailsModel.DataBean.ImgTextListBeen> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShangpinDetailsModel.DataBean.ImgTextListBeen item) {
        int img_height = Y.getInt(item.getImg_height());
        int img_width = Y.getInt(item.getImg_width());
        int newWhidth = Y.getScreenWidth(mContext);
        int newHeigh = newWhidth * img_height / img_width;

        View fl_main = helper.getView(R.id.fl_main);
        Y.setViewHeight(fl_main, newHeigh);
        Y.setViewWidth(fl_main, newWhidth);

        Glide.with(mContext).load(item.getImg_url()).into((ImageView) helper.getView(R.id.iv_main));

        helper.addOnClickListener(R.id.iv_delete);
        helper.addOnClickListener(R.id.iv_main);
    }
}
