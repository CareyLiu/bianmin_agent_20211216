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

public class MyCarCaoZuoDialog_Success extends Dialog {
    private View theView;
    private Activity context;
    TextView tvOk;
    String str, str2, str3;

    public MyCarCaoZuoDialog_Success(@NonNull Activity context, String str, String str2, String str3, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.str = str;
        this.str2 = str2;
        this.str3 = str3;
        this.listener = listener;
    }

    public MyCarCaoZuoDialog_Success(@NonNull Activity context, String str, String str2, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.str = str;
        this.str2 = str2;
        this.listener = listener;
    }

    public MyCarCaoZuoDialog_Success(@NonNull Activity context) {
        super(context, R.style.turntable_dialog);
        this.context = context;
    }

    public MyCarCaoZuoDialog_Success(@NonNull Activity context, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theView = inflater.inflate(R.layout.dialog_caozuo_success, null);

        TextView tvCaozuocheng = theView.findViewById(R.id.tv_caozuochegngong);
        TextView tvCaoZuoChengGongHuaShu = theView.findViewById(R.id.tv_caozuochenggonghuashu);

        if (str != null) {
            tvCaozuocheng.setText(str);
            tvCaoZuoChengGongHuaShu.setText(str2);
        }

        tvOk = theView.findViewById(R.id.tv_ok);
        if (str3 != null) {
            tvOk.setText(str3);
        }
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(theView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    private OnDialogItemClickListener listener;

    public interface OnDialogItemClickListener {
        void onDismiss();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) {
            listener.onDismiss();
        }
    }
}

