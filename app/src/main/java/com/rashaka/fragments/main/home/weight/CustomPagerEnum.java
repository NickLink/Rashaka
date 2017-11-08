package com.rashaka.fragments.main.home.weight;

import com.rashaka.R;

/**
 * Created by User on 04.10.2017.
 */

public enum CustomPagerEnum {

    WEEK(R.string.week, R.layout.view_week),
    MONTH(R.string.month, R.layout.view_month),
    YEAR(R.string.year, R.layout.view_year);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}