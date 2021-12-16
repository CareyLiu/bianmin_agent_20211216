package com.shendeng_bianmin.agent.dialog.tishi;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shendeng_bianmin.agent.R;

import androidx.annotation.NonNull;

public class MyCarCaoZuoDialog_CaoZuoTIshi extends Dialog {

    private Context context;
    private TextView tvCaozuocheng, tvCaoZuoChengGongHuaShu;
    private String str1, str2, strLft, strCenter, strRight;

    public MyCarCaoZuoDialog_CaoZuoTIshi(@NonNull Context context, String str1, String str2, String strCenter, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.str1 = str1;
        this.str2 = str2;
        this.strCenter = strCenter;
        this.listener = listener;
        init();
    }

    public MyCarCaoZuoDialog_CaoZuoTIshi(@NonNull Context context, String str1, String str2, String strLft, String strRight, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.str1 = str1;
        this.str2 = str2;
        this.strLft = strLft;
        this.strRight = strRight;
        this.listener = listener;
        init();
    }

    public MyCarCaoZuoDialog_CaoZuoTIshi(@NonNull Context context, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_caozuo_caozuo_tishi);
        tvCaozuocheng = findViewById(R.id.tv_caozuochegngong);
        tvCaoZuoChengGongHuaShu = findViewById(R.id.tv_caozuochenggonghuashu);
        TextView tvLft = findViewById(R.id.tv_left);
        tvLft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickLeft();
                dismiss();
            }
        });

        TextView tvCenter = findViewById(R.id.tv_center);
        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickLeft();
                dismiss();
            }
        });

        TextView tvRight = findViewById(R.id.tv_right);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickRight();
                dismiss();
            }
        });

        if (strCenter != null) {
            tvCenter.setText(strCenter);
            tvCenter.setVisibility(View.VISIBLE);
            tvLft.setVisibility(View.GONE);
            tvRight.setVisibility(View.GONE);
        } else {
            tvLft.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
            tvCenter.setVisibility(View.GONE);
        }

        if (strLft != null) {
            tvLft.setText(strLft);
        }

        if (strRight != null) {
            tvRight.setText(strRight);
        }

        if (str1 != null) {
            tvCaozuocheng.setText(str1);
            tvCaoZuoChengGongHuaShu.setText(str2);
        }
        setCanceledOnTouchOutside(false);
    }

    private OnDialogItemClickListener listener;

    public interface OnDialogItemClickListener {
        void clickLeft();

        void clickRight();
    }


    public void setTextContent(String text) {
        tvCaoZuoChengGongHuaShu.setText(text);
    }

    public void setTitle(String text) {
        tvCaozuocheng.setText(text);
    }
}
