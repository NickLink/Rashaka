package com.rashaka.fragments.main.home.weight.month;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.weight.WeightMonth;
import com.rashaka.domain.weight.WeightMonthItem;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class MonthPresenter extends SuperPresenter<MonthView, MainRouter> {

    private static final String TAG = MonthPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private int mCount = 4;
    private String mToken, mUserId;
    private static final double emptyWeight = 0.1;

    public MonthPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mUserId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        mCompositeDisposable.add(Rest.call().getUserWeightMonth(mToken, mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<WeightMonth> response) {
        Log.e(TAG, "RestResponse = -> " + response.toString());
        if(response.getStatus()) {
            List<WeightMonthItem> list = response.getMData().getList();
            List<Double> wList = new ArrayList<>();
            List<String> dList = new ArrayList<>();
            //Log.e(TAG, "list = -> " + list.toString());
            for (int i = 0; i < mCount; i++) {
                if (!TextUtils.isEmpty(list.get(i).getAvgWeight())
                        && !TextUtils.isEmpty(list.get(i).getStart())
                        && !TextUtils.isEmpty(list.get(i).getEnd())) {
                    //Log.e(TAG, "Item != null -> " + list.get(i));
                    String mWeight = list.get(i).getAvgWeight();
                    String mStart = list.get(i).getStart();
                    String mEnd = list.get(i).getEnd();

                    wList.add(Double.parseDouble(mWeight));
                    dList.add(Support.getDatePeriodMonth(mStart, mEnd) + "\n"
                            + Support.getYearByDate(list.get(i).getEnd(), Support.DATE_FORMAT));
                    //Log.e(TAG, "Item == weight -> " + mWeight);
                    //Log.e(TAG, "Item == date -> " + Support.getDatePeriodMonth(mStart, mEnd));
                } else {
                    //Log.e(TAG, "Item == null -> " + list.get(i));
                    wList.add(Consts.emptyWeight);
                    dList.add("");
                }
            }
            getView().setGraphData(wList, dList);
        } else {
            getView().setEmptyView();
        }
    }

    private void handleError(@NonNull String error) {
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
            if (!TextUtils.isEmpty(list.get(i)))
                pArray[i].setText(list.get(i));
            else
                pArray[i].setVisibility(View.GONE);
        }
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

//    public List<Integer> weightList() {
//        List<Integer> data = new ArrayList<>();
//        data.add(80);
//        data.add(85);
//        data.add(88);
//        data.add(90);
//        return data;
//    }
//
//    public List<String> daysList() {
//        List<String> data = new ArrayList<>();
//        data.add("10-17 " + RaApp.getLabel(LangKeys.key_sep));
//        data.add("17-24 " + RaApp.getLabel(LangKeys.key_sep));
//        data.add("24-01 " + RaApp.getLabel(LangKeys.key_oct));
//        data.add("01-08 " + RaApp.getLabel(LangKeys.key_oct));
//        return data;
//    }

}
