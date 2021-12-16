package com.shendeng_bianmin.agent.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.util.UIHelper;


public class TianJiaSheBeiDialog extends Dialog {

    private View theView;
    private Context context;

    LinearLayout llxiangce, llQuXiao;
    RelativeLayout rlPaiZhao;


    public TianJiaSheBeiDialog(@NonNull Context context, OnDialogItemClickListener listener) {
        super(context, R.style.turntable_dialog);
        this.context = context;
        this.listener = listener;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theView = inflater.inflate(R.layout.layout_tanchuang_geren, null);
        llxiangce = theView.findViewById(R.id.ll_congxiangce_choose);
        llQuXiao = theView.findViewById(R.id.ll_quxiao);
        rlPaiZhao = theView.findViewById(R.id.rl_paizhao);

        llxiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.xiangce();
                UIHelper.ToastMessage(context, "相册");
            }
        });
        llQuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.quxiao();
                UIHelper.ToastMessage(context, "取消");
            }
        });
        rlPaiZhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.paizhao();
                UIHelper.ToastMessage(context, "拍照");
            }
        });


        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部 默认在中间


        setContentView(theView);
    }

    private OnDialogItemClickListener listener;

    public interface OnDialogItemClickListener {
        void paizhao();

        void quxiao();

        void xiangce();
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}
