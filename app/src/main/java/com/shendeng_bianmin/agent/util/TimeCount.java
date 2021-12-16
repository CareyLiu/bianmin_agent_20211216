package com.shendeng_bianmin.agent.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.App;

import androidx.core.content.ContextCompat;


/**
 * Created by Administrator on 2017/3/31.
 */
//计时器
public class TimeCount extends CountDownTimer {
    private TextView view;

    public TimeCount(long millisInFuture, long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
    }

    @Override
    public void onFinish() {// 计时完毕
        view.setClickable(true);
        view.setText("获取验证码");
        view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.app_bg));
        view.setTextSize(14);
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
        view.setClickable(false);
        view.setTextSize(12);
        view.setText(millisUntilFinished / 1000 + "s后重新发送");
        view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.app_bg));

    }


}