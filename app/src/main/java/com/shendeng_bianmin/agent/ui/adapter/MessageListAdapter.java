package com.shendeng_bianmin.agent.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.MessageModel;


import java.util.List;

public class MessageListAdapter extends BaseQuickAdapter<MessageModel.DataBean, BaseViewHolder> {
    public MessageListAdapter(int layoutResId, @Nullable List<MessageModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel.DataBean item) {
        ImageView ivImage = helper.getView(R.id.iv_image);
        Glide.with(mContext)
                .load(item.getOther_img_url())
                .into((ImageView) ivImage);
        helper.setText(R.id.tv_type, item.getNotify_text());
        helper.setText(R.id.tv_text, item.getCreate_time());
        helper.addOnClickListener(R.id.constrain);
        helper.addOnLongClickListener(R.id.constrain);


        Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
        String targetId = item.getLt_user_accid();
        RongIMClient.getInstance().getUnreadCount(conversationType, targetId,
                new RongIMClient.ResultCallback<Integer>() {
                    /**
                     * 成功回调
                     * @param unReadCount 未读数
                     */
                    @Override
                    public void onSuccess(Integer unReadCount) {
                        View tv_num = helper.getView(R.id.tv_num);
                        if (unReadCount > 0) {
                            tv_num.setVisibility(View.VISIBLE);
                            if (unReadCount > 99) {
                                helper.setText(R.id.tv_num, "99+");
                            } else {
                                helper.setText(R.id.tv_num, unReadCount + "");
                            }
                        } else {
                            tv_num.setVisibility(View.GONE);
                            helper.setText(R.id.tv_num, unReadCount + "");
                        }
                    }

                    /**
                     * 错误回调
                     */
                    @Override
                    public void onError(RongIMClient.ErrorCode ErrorCode) {

                    }
                });
    }
}
