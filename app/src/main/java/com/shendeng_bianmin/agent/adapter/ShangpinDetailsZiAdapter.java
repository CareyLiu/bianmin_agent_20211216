package com.shendeng_bianmin.agent.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.ShangpinDetailsModel;
import com.shendeng_bianmin.agent.util.Y;

import java.util.List;

import androidx.annotation.Nullable;

public class ShangpinDetailsZiAdapter extends BaseQuickAdapter<ShangpinDetailsModel.DataBean.PackageListBean, BaseViewHolder> {


    public ShangpinDetailsZiAdapter(int layoutResId, @Nullable List<ShangpinDetailsModel.DataBean.PackageListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShangpinDetailsModel.DataBean.PackageListBean item) {
        SpannableString shouJia = new SpannableString("售价    " + item.getMoney_now());
        shouJia.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        shouJia.setSpan(new ForegroundColorSpan(Y.getColor(R.color.text_red)), 5, shouJia.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String product_state = item.getProduct_state();
        SpannableString zhuangtai;
        if (product_state.equals("2")) {
            zhuangtai = new SpannableString("售价    已下架");
            zhuangtai.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            zhuangtai.setSpan(new ForegroundColorSpan(Color.parseColor("#FC0100")), 5, zhuangtai.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            zhuangtai = new SpannableString("售价    已上架");
            zhuangtai.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            zhuangtai.setSpan(new ForegroundColorSpan(Color.parseColor("#15D78E")), 5, zhuangtai.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        helper.setText(R.id.tv_xinghao, "型号    " + item.getProduct_title());
        helper.setText(R.id.tv_shoujia, shouJia);
        helper.setText(R.id.tv_xiaoliang, "销量    " + item.getPay_count());
        helper.setText(R.id.tv_kucun, "库存    " + item.getProduct_count());
        helper.setText(R.id.tv_zhuangtai, zhuangtai);

        helper.addOnClickListener(R.id.bt_bianji);
    }
}
