package com.shendeng_bianmin.agent.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;

import java.util.List;

import androidx.annotation.Nullable;

public class OrderTuikuanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OrderTuikuanAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
    }
}
