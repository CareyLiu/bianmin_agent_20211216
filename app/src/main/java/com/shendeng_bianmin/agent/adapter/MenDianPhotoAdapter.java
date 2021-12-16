package com.shendeng_bianmin.agent.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.TaoCanDetailsModel;

import java.util.List;

public class MenDianPhotoAdapter extends BaseQuickAdapter<TaoCanDetailsModel.DataBean.ImgListBean, BaseViewHolder> {
    public MenDianPhotoAdapter(int layoutResId, @Nullable List<TaoCanDetailsModel.DataBean.ImgListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaoCanDetailsModel.DataBean.ImgListBean item) {
        if (StringUtils.isEmpty(item.type)) {
            Glide.with(mContext).load(item.getImg_url()).into((ImageView) helper.getView(R.id.iv_taocan_tupian));
        } else {
            Glide.with(mContext).load(R.mipmap.shoppicture_icon_add).into((ImageView) helper.getView(R.id.iv_taocan_tupian));
        }
        helper.addOnClickListener(R.id.constrain);
    }
}
