package com.shendeng_bianmin.agent.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.shendeng_bianmin.agent.R;
import com.shendeng_bianmin.agent.app.BaseActivity;
import com.shendeng_bianmin.agent.util.UIHelper;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

import static android.text.TextUtils.isEmpty;

public class FeedBackActivity extends BaseActivity {
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.et_feedback)
//    EditText etFeedback;
//    @Bind(R.id.tv_num)
//    TextView tvNum;
//    @Bind(R.id.tv_maxlength)
//    TextView tvMaxlength;
//    @Bind(R.id.bt_submit)
//    Button btSubmit;
//    @Bind(R.id.RadioButton1)
//    RadioButton RadioButton1;
//    @Bind(R.id.RadioButton2)
//    RadioButton RadioButton2;
//    @Bind(R.id.RadioButton3)
//    RadioButton RadioButton3;
//    @Bind(R.id.ll_text_layout)
//    LinearLayout llTextLayout;
//    @Bind(R.id.radiogroup)
//    RadioGroup radiogroup;


    int feedBackType = -1;

    int num = 100;
    @BindView(R.id.RadioButton1)
    RadioButton RadioButton1;
    @BindView(R.id.RadioButton2)
    RadioButton RadioButton2;
    @BindView(R.id.RadioButton3)
    RadioButton RadioButton3;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_maxlength)
    TextView tvMaxlength;
    @BindView(R.id.ll_text_layout)
    LinearLayout llTextLayout;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.scrollView1)
    ScrollView scrollView1;

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initToolbar();
        setTitle("意见反馈");

        tvNum.setText("0");
        tvMaxlength.setText(String.valueOf(num));

        RxTextView.afterTextChangeEvents(etFeedback).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                String content = textViewAfterTextChangeEvent.editable().toString().trim();
                if (!isEmpty(content)) {
                    btSubmit.setEnabled(true);
                    llTextLayout.setVisibility(View.VISIBLE);
                } else {
                    btSubmit.setEnabled(false);
                    llTextLayout.setVisibility(View.GONE);
                }
                String length = String.valueOf(content.length());// + "/" + num;
                if (content.length() == num) {
                    //防止Toast被输入键盘遮挡，将Toast显示在屏幕中间
                    Toast toast = Toast.makeText(getApplicationContext(), "最多输入100字符！", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //如果字数超出了限定，则将其颜色变为红色
                    SpannableStringBuilder builder = new SpannableStringBuilder(length);
                    builder.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    tvNum.setText(builder);
                } else {
                    tvNum.setText(length);
                }

            }
        });
        //被观察者，可输入框是否为空
        Observable<Boolean> editTextChangeEvent = RxTextView.textChangeEvents(etFeedback)
                .map(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return isEmpty(textViewTextChangeEvent.text());
                    }
                });
        //被观察者，单选是否已选中
        Observable<Boolean> RadioButtonChangeEvent = RxRadioGroup.checkedChanges(radiogroup).map(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                boolean enabled;
                switch (integer) {
                    case R.id.RadioButton1:
                        feedBackType = 1;
                        enabled = true;
                        break;
                    case R.id.RadioButton2:
                        feedBackType = 2;
                        enabled = true;
                        break;
                    case R.id.RadioButton3:
                        feedBackType = 0;
                        enabled = true;
                        break;
                    default:
                        enabled = false;
                        break;
                }
                return enabled;
            }
        });
        //合并订阅，设置按钮可点击性
        Observable.combineLatest(editTextChangeEvent, RadioButtonChangeEvent, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean, Boolean aBoolean2) {
                return !aBoolean && aBoolean2;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        btSubmit.setEnabled(aBoolean);
                        btSubmit.setBackgroundResource(aBoolean ? R.color.deep_powder : R.color.pink);
                    }
                });

        RxView.clicks(btSubmit)
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UIHelper.ToastMessage(mContext, "提交");
                    }
                });
    }


    @Override
    public int getContentViewResId() {
        return R.layout.activity_feed_back;
    }

    //    private void initToolbar() {
//        super.initToolbar(toolbar);
//        toolbar.setTitle(R.string.title_activity_feed_back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imm.hideSoftInputFromWindow(etFeedback.getWindowToken(), 0);
//                finish();
//            }
//        });
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isEmpty(etFeedback.getText().toString())) {
                btSubmit.setEnabled(true);
                llTextLayout.setVisibility(View.VISIBLE);
            } else {
                btSubmit.setEnabled(false);
                llTextLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //  imm.hideSoftInputFromWindow(etFeedback.getWindowToken(), 0);
            if (!isEmpty(etFeedback.getText().toString())) {
                btSubmit.setEnabled(true);
            } else {
                btSubmit.setEnabled(false);
            }
            String uploadTitle = etFeedback.getText().toString().trim();
            String length = String.valueOf(uploadTitle.length());// + "/" + num;
            if (uploadTitle.length() == num) {
                //防止Toast被输入键盘遮挡，将Toast显示在屏幕中间
                Toast toast = Toast.makeText(getApplicationContext(), "最多输入100字符！", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //如果字数超出了限定，则将其颜色变为红色
                SpannableStringBuilder builder = new SpannableStringBuilder(length);
                builder.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                tvNum.setText(builder);
            } else {
                tvNum.setText(length);
            }
        }
    };
}
