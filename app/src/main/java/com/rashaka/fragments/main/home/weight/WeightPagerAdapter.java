package com.rashaka.fragments.main.home.weight;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;

/**
 * Created by User on 04.10.2017.
 */

public class WeightPagerAdapter extends PagerAdapter {

    private Context mContext;

    public WeightPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];

        switch (position){
            case 0:
                return RaApp.getLabel(LangKeys.key_weekly_summary);
            case 1:
                return RaApp.getLabel(LangKeys.key_mon);
            case 2:
                return RaApp.getLabel(LangKeys.key_years);
            default:
                return RaApp.getLabel(LangKeys.key_weekly_summary);
        }

        //return mContext.getString(customPagerEnum.getTitleResId());
    }
}
