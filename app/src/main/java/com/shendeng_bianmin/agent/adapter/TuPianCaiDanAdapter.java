package com.shendeng_bianmin.agent.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MenDianGuanLiModel;

import java.util.List;

public class TuPianCaiDanAdapter extends BaseQuickAdapter<MenDianGuanLiModel.DataBean.LunBoListBean, BaseViewHolder> {


    public TuPianCaiDanAdapter(int layoutResId, @Nullable List<MenDianGuanLiModel.DataBean.LunBoListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MenDianGuanLiModel.DataBean.LunBoListBean item) {
        Glide.with(mContext).load(item.getImg_url()).into((ImageView) helper.getView(R.id.iv_taocan_tupian));
    }
}
