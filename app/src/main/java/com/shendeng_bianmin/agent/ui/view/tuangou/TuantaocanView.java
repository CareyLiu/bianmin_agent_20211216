package com.shendeng_bianmin.agent.ui.view.tuangou;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.model.tuangou.TOrderDetailsModel;

import androidx.annotation.Nullable;

public class TuantaocanView extends LinearLayout {//选择的标题栏

    private Context context;
    private TextView tv_caidan_title;
    private TextView tv_fenshu;
    private TextView tv_money;

    public TuantaocanView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TuantaocanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TuantaocanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_taocan_neirong, this, true);
        tv_caidan_title = view.findViewById(R.id.tv_caidan_title);
        tv_fenshu = view.findViewById(R.id.tv_fenshu);
        tv_money = view.findViewById(R.id.tv_money);
    }

    public void setModel(TOrderDetailsModel.DataBean.TaocanListBean model) {
        tv_caidan_title.setText(model.getMenu_text());
        tv_fenshu.setText("("+model.getMenu_count()+")");
        tv_money.setText("¥"+model.getMenu_pay());
    }
}
