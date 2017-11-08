package com.rashaka.fragments.main.home.weight;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rashaka.RaApp;
import com.rashaka.fragments.main.home.weight.month.MonthFragment;
import com.rashaka.fragments.main.home.weight.year.YearFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.fragments.main.home.weight.week.WeekFragment;

/**
 * Created by User on 10.10.2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT = 3;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return WeekFragment.newInstance(position);
            case 1:
                return MonthFragment.newInstance(position);
            case 2:
                return YearFragment.newInstance(position);
            default:
                return WeekFragment.newInstance(position);
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
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
    }

}