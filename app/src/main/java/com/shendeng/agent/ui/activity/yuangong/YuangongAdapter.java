package com.shendeng.agent.ui.activity.yuangong;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng.agent.R;

import java.util.List;

import androidx.annotation.Nullable;

public class YuangongAdapter extends BaseQuickAdapter<YuangongModel.DataBean.UserListBean, BaseViewHolder> {

    public YuangongAdapter(int layoutResId, @Nullable List<YuangongModel.DataBean.UserListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YuangongModel.DataBean.UserListBean item) {
        helper.setText(R.id.tv_num, item.getSub_user_no());
        helper.setText(R.id.tv_name, item.getUser_name());
        helper.setText(R.id.tv_phone, item.getUser_phone());

        helper.setText(R.id.tv_content, item.getBranch_name() + "·" + item.getRole_name());
    }
}
