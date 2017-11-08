package com.rashaka.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;


/**
 * Created by User on 18.07.2017.
 */


public class DialogBlueButton extends Dialog implements
        android.view.View.OnClickListener {

    Activity mActivity;
    String mTitle, mText, mButton;

    public DialogBlueButton(Activity activity, String title, String text, String button) {
        super(activity);
        this.mActivity = activity;
        this.mTitle = title;
        this.mText = text;
        this.mButton = button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_with_button_title);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setCancelable(false);

        TextView title = findViewById(R.id.dialog_title);
        TextView text = findViewById(R.id.dialog_text);
        TextView button = findViewById(R.id.dialog_button);

        if(TextUtils.isEmpty(mTitle)){
            title.setVisibility(View.GONE);
        } else {
            title.setText(mTitle);
        }

        if(TextUtils.isEmpty(mText)){
            text.setVisibility(View.GONE);
        } else {
            text.setText(mText);
        }

        if(TextUtils.isEmpty(mButton)){
            button.setText(RaApp.getResourceString(R.string.dialog_button_text));
        } else {
            button.setText(mButton);
        }

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_button:
                dismiss();
                break;
            default:
                break;
        }
    }
}