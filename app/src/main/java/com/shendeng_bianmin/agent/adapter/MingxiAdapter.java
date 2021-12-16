package com.shendeng_bianmin.agent.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MingxiModel;
import com.shendeng_bianmin.agent.util.Y;

import java.util.List;

import androidx.annotation.Nullable;

public class MingxiAdapter extends BaseQuickAdapter<MingxiModel.DataBean, BaseViewHolder> {
    public MingxiAdapter(int layoutResId, @Nullable List<MingxiModel.DataBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MingxiModel.DataBean item) {
        helper.setText(R.id.tv_money, item.getMoney());
        helper.setText(R.id.tv_data, item.getCreate_time());

        String pay_cost_type = item.getPay_cost_type();//类型：1.收入 2.支出 3.提现
        String pay_user_state = item.getPay_user_state();//状态：1.处理中2.已完成3.已关闭
        if (pay_cost_type.equals("1")) {
            helper.setText(R.id.tv_type, "收入");
        } else if (pay_cost_type.equals("2")) {
            helper.setText(R.id.tv_type, "支出");
        } else if (pay_cost_type.equals("3")) {
            helper.setText(R.id.tv_type, "提现");
        }

        if (pay_user_state.equals("1")) {
            helper.setText(R.id.tv_state, "处理中");
            helper.setTextColor(R.id.tv_state, Y.getColor(R.color.mingxi_lv));
        } else if (pay_user_state.equals("2")) {
            helper.setText(R.id.tv_state, "已完成");
            helper.setTextColor(R.id.tv_state, Y.getColor(R.color.mingxi_huang));
        } else if (pay_user_state.equals("3")) {
            helper.setText(R.id.tv_state, "已关闭");
            helper.setTextColor(R.id.tv_state, Y.getColor(R.color.text_color_6));
        }
    }
}
