package com.shendeng_bianmin.agent.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MessageModel;

import java.util.List;

import androidx.annotation.Nullable;

public class MsgOrderAdapter extends BaseQuickAdapter<MessageModel.DataBean, BaseViewHolder> {
    public MsgOrderAdapter(int layoutResId, @Nullable List<MessageModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel.DataBean item) {
        helper.setText(R.id.tv_name, item.getLt_user_name());
    }
}
