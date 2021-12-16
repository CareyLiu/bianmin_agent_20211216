package com.shendeng_bianmin.agent.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MsgModel;

import java.util.List;

import androidx.annotation.Nullable;

public class MsgAdapter extends BaseQuickAdapter<MsgModel, BaseViewHolder> {
    public MsgAdapter(int layoutResId, @Nullable List<MsgModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgModel item) {
        helper.setText(R.id.tv_name, item.getName());
        ImageView iv_icon = (ImageView) helper.getView(R.id.iv_icon);
        iv_icon.setImageResource(item.getImgId());
    }
}
