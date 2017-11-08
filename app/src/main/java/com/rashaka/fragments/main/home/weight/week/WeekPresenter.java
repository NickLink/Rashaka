package com.rashaka.fragments.main.home.weight.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.weight.WeightWeek;
import com.rashaka.domain.weight.WeightWeekItem;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class WeekPresenter extends SuperPresenter<WeekView, MainRouter> {

    private static final String TAG = WeekPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private int mCount = 7;
    private String mToken, mUserId;


    public WeekPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mUserId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        mCompositeDisposable.add(Rest.call().getUserWeightWeek(mToken, mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<WeightWeek> response) {
        Log.e(TAG, "handleResponse - > " + response.toString());
        if(response.getStatus()) {
            //setGraph(response.getMData().getList(), );
            List<WeightWeekItem> list = response.getMData().getList();
            List<Double> wList = new ArrayList<>();
            List<String> dList = new ArrayList<>();
            int b = list.size() - 1;
            for (int i = 0; i < mCount; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -i);
                int thisYear = calendar.get(Calendar.YEAR);
                int thisMonth = calendar.get(Calendar.MONTH) + 1;
                int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
                String date = thisDay + " " + Support.getMonthByInt(thisMonth - 1); // + " : " + thisYear;
                //Log.e(TAG, "DateAPI -> " + Integer.parseInt(list.get(b).getDay()) + " : " + Integer.parseInt(list.get(b).getMonth()) + " : " + Integer.parseInt(list.get(b).getYear()));
                if (b >= 0 && Integer.parseInt(list.get(b).getYear()) == thisYear &&
                        Integer.parseInt(list.get(b).getMonth()) == thisMonth &&
                        Integer.parseInt(list.get(b).getDay()) == thisDay) {
                    //TODO Day exist add it to datalist
                    wList.add(0, Double.parseDouble(list.get(b).getAvgWeight()));
                    b--;
                    Log.e(TAG, "Item added -> " + wList.get(i));
                } else {
                    wList.add(0, Consts.emptyWeight);
                }
                dList.add(0, Support.getDayByInt(calendar.get(Calendar.DAY_OF_WEEK)) + "\n" + date);
                Log.e(TAG, "Day added -> " + Support.getDayByInt(calendar.get(Calendar.DAY_OF_WEEK)));
            }
            getView().setGraphData(wList, dList);
        } else {
            getView().setEmptyView();
        }
    }

    private void handleError(@NonNull String error) {
        Log.e(TAG, "handleError - > " + error);
        getRouter().showError(error);
    }

    public void setGraph(List<Double> list, ProgressBar... pBars) {
        ProgressBar[] pArray = pBars;
        for (int i = 0; i < mCount; i++) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pArray[i].getLayoutParams();
            params.height = Support.dpToPx((int) (list.get(i) * 2));
            pArray[i].setLayoutParams(params);
        }
    }

    public void setDays(List<String> list, TextView... pText) {
        TextView[] pArray = pText;
        for (int i = 0; i < mCount; i++) {
            pArray[i].setText(list.get(i));
        }
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }


//    public List<Double> weightList() {
//        List<Double> data = new ArrayList<>();
//        data.add(80.4);
//        data.add(85.5);
//        data.add(88.0);
//        data.add(90.3);
//        data.add(86.1);
//        data.add(84.9);
//        data.add(82.8);
//        return data;
//    }
//
//    public List<String> daysList() {
//        List<String> data = new ArrayList<>();
//        data.add(RaApp.getLabel(LangKeys.key_mon));
//        data.add(RaApp.getLabel(LangKeys.key_tues));
//        data.add(RaApp.getLabel(LangKeys.key_weds));
//        data.add(RaApp.getLabel(LangKeys.key_thurs));
//        data.add(RaApp.getLabel(LangKeys.key_fri));
//        data.add(RaApp.getLabel(LangKeys.key_sat));
//        data.add(RaApp.getLabel(LangKeys.key_sun));
//        return data;
//    }

}
