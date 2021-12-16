package com.shendeng_bianmin.agent.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.tuangou.TuanPinglunModel;
import com.shendeng_bianmin.agent.util.Y;

import java.util.List;

public class TuanGouAccessAdapter extends BaseQuickAdapter<TuanPinglunModel.DataBean.AssessListBean, BaseViewHolder> {


    public TuanGouAccessAdapter(int layoutResId, @Nullable List<TuanPinglunModel.DataBean.AssessListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuanPinglunModel.DataBean.AssessListBean item) {
        Glide.with(mContext).load(item.getUser_img_url()).into((ImageView) helper.getView(R.id.iv_yonghuming));
        helper.setText(R.id.tv_yonghuming, item.getUser_name());
        helper.setText(R.id.tv_time, item.getUser_to_time());
        helper.setText(R.id.tv_pinglun, item.getUser_to_text());

        AppCompatRatingBar rb_bar1 = helper.getView(R.id.rb_pingfenbar);
        rb_bar1.setNumStars(Y.getInt(item.getUser_to_score()));

        String reply_state = item.getReply_state();
        View ll_huifu = helper.getView(R.id.ll_huifu);
        View ll_huifu_bt = helper.getView(R.id.ll_huifu_bt);
        if (reply_state.equals("1")) {
            ll_huifu.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_access, "商家回复：" + item.getShop_to_text());
        } else {
            ll_huifu.setVisibility(View.GONE);
            helper.setText(R.id.tv_access, "");
        }

        helper.addOnClickListener(R.id.ll_huifu_bt);
    }
}
