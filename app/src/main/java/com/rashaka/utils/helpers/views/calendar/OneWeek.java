package com.rashaka.utils.helpers.views.calendar;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashaka.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by User on 29.08.2017.
 */

public class OneWeek extends LinearLayout {

    private static int STROKE_WIDTH = 2;
    private static int CORNER_RADIUS = 6;
    private static int WEEK_NUMBER = 7;
    private static int NUMBER_TEXT_SIZE = 15;
    private static int DAY_TEXT_SIZE = 11;
    private static int LAYOUT_PADDING = 6;
    private static int LAYOUT_MARGIN = 6;

    private long mTimeShift;
    private OnSelectListener mOnEventListener;
    private Context mContext;

    public interface OnSelectListener {
        void onSelect(long startDay);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        mOnEventListener = listener;
    }

    public OneWeek(Context context) {
        super(context);
        mTimeShift = System.currentTimeMillis();
        init(context);
    }

    public OneWeek(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTimeShift = System.currentTimeMillis();
        init(context);
    }

    public OneWeek(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTimeShift = System.currentTimeMillis();
        init(context);
    }

    private void init(Context context) {
        this.removeAllViews();
        this.mContext = context;

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setWeightSum(WEEK_NUMBER);
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < WEEK_NUMBER; i++) {
            final int p = i;

            LinearLayout inner = new LinearLayout(mContext);
            inner.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            layoutParams.setMargins(mInDp(LAYOUT_MARGIN), 0, mInDp(LAYOUT_MARGIN), 0);
            inner.setLayoutParams(layoutParams);
            inner.setPadding(0, mInDp(LAYOUT_PADDING), 0, mInDp(LAYOUT_PADDING));

            if (i == 3) {
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.TRANSPARENT);
                gd.setCornerRadius(mInDp(CORNER_RADIUS));
                gd.setStroke(mInDp(STROKE_WIDTH), mContext.getResources().getColor(R.color.outline_blue));
                inner.setBackground(gd);
            }

            long mTime = mTimeShift - ((3 - i) * 24 * 60 * 60 * 1000l);
            c.setTimeInMillis(mTime);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            textParams.gravity = Gravity.CENTER_HORIZONTAL;

            TextView dateNum = new TextView(mContext);
            dateNum.setLayoutParams(textParams);
            dateNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, NUMBER_TEXT_SIZE);
            dateNum.setTypeface(dateNum.getTypeface(), Typeface.BOLD);
            dateNum.setText(String.valueOf(dayOfMonth));

            TextView dateName = new TextView(mContext);
            dateName.setLayoutParams(textParams);
            dateName.setTextSize(TypedValue.COMPLEX_UNIT_SP, DAY_TEXT_SIZE);
            dateName.setText(weekday);

            //Set color for current day
            if (currentDay(mTime)) {
                dateNum.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                dateName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            }

            inner.addView(dateNum);
            inner.addView(dateName);

            inner.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTimeShift = mTimeShift - ((3 - p) * 24 * 60 * 60 * 1000l);
                    selected(mTimeShift);
                    init(mContext);
                }
            });

            this.addView(inner);
            this.setLayoutTransition(new LayoutTransition());
        }

    }

    private boolean currentDay(long mTime) {
        if (mTime > startDay(System.currentTimeMillis()) &&
                mTime < (startDay(System.currentTimeMillis() + 86399999)))
            return true;
        else
            return false;
    }

    private int mInDp(int size) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, size, getResources()
                        .getDisplayMetrics());
    }

    private void selected(long timestamp) {
        if (mOnEventListener != null)
            mOnEventListener.onSelect(startDay(timestamp));
    }

    private long startDay(long timestamp) {
        return timestamp - timestamp % 86400000;
    }

}
