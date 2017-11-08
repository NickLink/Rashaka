package com.rashaka.utils.helpers.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;

/**
 * Created by User on 03.11.2017.
 */

public class EmptyListView extends RelativeLayout {

    private static final String TAG = EmptyListView.class.getSimpleName();
    private Context context;
    private AttributeSet attrs;
    private LayoutInflater mInflater;
    private boolean isFullScreen;

    public EmptyListView(Context context) {
        super(context);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init();
    }

    public EmptyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        mInflater = LayoutInflater.from(context);

//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.EmptyListView,
//                0, 0);
//
//        try {
//            isFullScreen = a.getBoolean(R.styleable.EmptyListView_fullScreen, false);
//        } finally {
//            a.recycle();
//        }

        init();
    }

    public EmptyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmptyListView);
        isFullScreen = a.getBoolean(R.styleable.EmptyListView_fullScreen, false);
        a.recycle();

        View v = mInflater.inflate(R.layout.view_empty_list_view, this, true);
        Log.e(TAG, "isFullScreen -> " + isFullScreen);
        if (isFullScreen) {
            RelativeLayout rl = v.findViewById(R.id.parent_layout);
            LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            rl.setLayoutParams(params);
        }

        TextView tv = v.findViewById(R.id.text);
        tv.setText(RaApp.getLabel(LangKeys.key_list_empty));
    }
}