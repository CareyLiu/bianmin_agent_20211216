package com.shendeng_bianmin.agent.dialog.tishi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.shendeng_bianmin.agent.R;

import androidx.annotation.NonNull;

public class MyCarCaoZuoDialog_CaoZuo_Base extends Dialog {

    private View theView;
    private Context context;
    private String message1;
    private String message2;
    public TextView tvLft;
    public TextView tv_message1;
    public TextView tv_message2;
    public TextView tvCenter;
    public TextView tvRight;

    public MyCarCaoZuoDialog_CaoZuo_Base(@NonNull Context context, String message1, String message2, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.listener = listener;
        this.message1 = message1;
        this.message2 = message2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theView = inflater.inflate(R.layout.dialog_caozuo_caozuo_tishi, null);
        tvLft = theView.findViewById(R.id.tv_left);
        tv_message1 = theView.findViewById(R.id.tv_caozuochegngong);
        tv_message2 = theView.findViewById(R.id.tv_caozuochenggonghuashu);
        tvCenter = theView.findViewById(R.id.tv_center);
        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_message1.setText(message1);
        tv_message2.setText(message2);
        tvLft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickLeft();
                dismiss();
            }
        });

        tvRight = theView.findViewById(R.id.tv_right);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickRight();
                dismiss();
            }
        });
        setContentView(theView);
    }

    private OnDialogItemClickListener listener;

    public interface OnDialogItemClickListener {
        void clickLeft();

        void clickRight();
    }


    public void setCenterVisible() {
        tvCenter.setVisibility(View.VISIBLE);
        tvLft.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
    }

}
