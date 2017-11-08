package com.rashaka.fragments.main.home.steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by User on 24.08.2017.
 */

public class StepsPresenter extends SuperPresenter<StepsView, MainRouter> {

    private static final String TAG = StepsPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private long INTERVAL = 1000 * 60 * 20;
    private int PERIODS_COUNT = 72;

    public StepsPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {

    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    public List<Integer> getStepsByPeriod(String date){
        List<Integer> list = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTime(Support.getDateFromString(date, Support.DATE_FORMAT));
        long start = c.getTimeInMillis(), end;

        for(int i = 0 ; i < PERIODS_COUNT ; i++) {
            end = start + INTERVAL;
            int count = (int) RaApp.getBase().getStepsCount(start, end);
            Log.e(TAG, "Count in -> " + list.size() + " is -> " + count + " steps");
            list.add(count);
            start = end;
        }

        return list;
    }

}
