package com.shendeng_bianmin.agent.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.tuangou.TuanMingxiModel;

import java.util.List;

import androidx.annotation.Nullable;

public class TuanMingxiAdapter extends BaseQuickAdapter<TuanMingxiModel.DataBean.CunsumerListBean, BaseViewHolder> {
    public TuanMingxiAdapter(int layoutResId, @Nullable List<TuanMingxiModel.DataBean.CunsumerListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuanMingxiModel.DataBean.CunsumerListBean item) {
        helper.setText(R.id.tv_name,item.getTitle());
        helper.setText(R.id.tv_data,item.getTime());
        helper.setText(R.id.tv_money,item.getMoney());
    }
}
