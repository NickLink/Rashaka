package com.rashaka.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 18.07.2017.
 */


public class DialogTwoButton extends Dialog implements
        View.OnClickListener {

    private Activity mActivity;
    private String mTitle, mText, mButtonYes, mButtonNo;
    private DialogActionInterface  listener;

    public DialogTwoButton(
            Activity activity,
            String title,
            String text,
            String yes_button,
            String no_button,
            DialogActionInterface  listener
    ) {
        super(activity);
        this.mActivity = activity;
        this.mTitle = title;
        this.mText = text;
        this.mButtonYes = yes_button;
        this.mButtonNo = no_button;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = View.inflate(getContext(), R.layout.dialog_two_buttons, null);
        ButterKnife.bind(this, view);
        setContentView(view);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setCancelable(false);

//        TextView title = findViewById(R.id.dialog_title);
//        TextView text = findViewById(R.id.dialog_text);
//        TextView button = findViewById(R.id.dialog_button);

        if(TextUtils.isEmpty(mTitle)){
            mTitleView.setVisibility(View.GONE);
        } else {
            mTitleView.setText(mTitle);
        }

        if(TextUtils.isEmpty(mText)){
            mTextView.setVisibility(View.GONE);
        } else {
            mTextView.setText(mText);
        }

        if(TextUtils.isEmpty(mButtonYes)){
            mButtonYesView.setText(RaApp.getResourceString(R.string.dialog_button_text_yes));
        } else {
            mButtonYesView.setText(mButtonYes);
        }

        if(TextUtils.isEmpty(mButtonNo)){
            mButtonNoView.setText(RaApp.getResourceString(R.string.dialog_button_text_no));
        } else {
            mButtonNoView.setText(mButtonNo);
        }

        mButtonYesView.setOnClickListener(this);
        mButtonNoView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_button_yes:
                dismiss();
                listener.actionYes();
                break;
            case R.id.dialog_button_no:
                dismiss();
                listener.actionYes();
                break;
            default:
                break;
        }
    }

    @BindView(R.id.dialog_title)
    TextView mTitleView;

    @BindView(R.id.dialog_text)
    TextView mTextView;

    @BindView(R.id.dialog_button_yes)
    TextView mButtonYesView;

    @BindView(R.id.dialog_button_no)
    TextView mButtonNoView;
}