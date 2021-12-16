package com.shendeng_bianmin.agent.dialog.tishi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.shendeng_bianmin.agent.R;

import androidx.annotation.NonNull;

public class MyCarCaoZuoDialog_Failed extends Dialog {

    private View theView;
    private Activity context;

    TextView tvOk;

    public MyCarCaoZuoDialog_Failed(@NonNull Activity context) {
        super(context, R.style.turntable_dialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theView = inflater.inflate(R.layout.dialog_caozuo_fail, null);
        tvOk = theView.findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(theView);
    }
}

