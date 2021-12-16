package com.shendeng_bianmin.agent.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MingxiDetailsModel;

import java.util.List;

import androidx.annotation.Nullable;

public class MingxiDetailsAdapter extends BaseQuickAdapter<MingxiDetailsModel.DataBean.DistributionListBean, BaseViewHolder> {
    public MingxiDetailsAdapter(int layoutResId, @Nullable List<MingxiDetailsModel.DataBean.DistributionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MingxiDetailsModel.DataBean.DistributionListBean item) {
        helper.setText(R.id.tv_money, item.getMoney());
        helper.setText(R.id.tv_name, item.getName());
    }
}
