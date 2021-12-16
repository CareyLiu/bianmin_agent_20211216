package com.shendeng_bianmin.agent.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.TianJiaLeiMuModel;

import java.util.List;

public class TianJiaLeiMuAdapter extends BaseQuickAdapter<TianJiaLeiMuModel, BaseViewHolder> {

    public TianJiaLeiMuAdapter(int layoutResId, @Nullable List<TianJiaLeiMuModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TianJiaLeiMuModel item) {
        helper.setText(R.id.tv_shangpinming, item.item_name);
        helper.addOnClickListener(R.id.tv_xuanze);
        helper.addOnClickListener(R.id.tv_bianji);
        if (item.bianJiFlag) {
            helper.setText(R.id.tv_xuanze, "选择");
            helper.setVisible(R.id.tv_bianji, false);

        } else {
            helper.setText(R.id.tv_xuanze, "删除");
            helper.setVisible(R.id.tv_bianji, true);
        }
    }
}
