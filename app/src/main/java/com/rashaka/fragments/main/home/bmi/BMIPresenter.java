package com.rashaka.fragments.main.home.bmi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.bmi.Bmi;
import com.rashaka.domain.bmi.BmiItem;
import com.rashaka.fragments.main.home.bmi.simulate.SimulateBMIFragment;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class BMIPresenter extends SuperPresenter<BMIView, MainRouter> {

    private static final String TAG = BMIPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private int mCount = 12;
    private String mToken, mUserId;

    public BMIPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mUserId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().setBmiValue(String.valueOf(
                Support.getBMI(
                        RaApp.getBase().getProfileUser().getWeight(),
                        RaApp.getBase().getProfileUser().getHight()
                )));
        mCompositeDisposable.add(Rest.call().getUserBMI(mToken, mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
        
    }

    private void handleResponse(RestResponse<Bmi> response) {
        List<BmiItem> list = response.getMData().getList();
        Log.e(TAG, "List ->" + list.toString());
        List<Double> wList = new ArrayList<>();
        List<String> dList = new ArrayList<>();
        Log.e(TAG, "List size ->" + list.size());
        for(int i = 0 ; i < list.size() ; i++){
            Log.e(TAG, "List weight ->" + list.get(i).getAvgBmi());
            Log.e(TAG, "List date ->" + list.get(i).getMonth());

            wList.add(Double.parseDouble(list.get(i).getAvgBmi()));
            dList.add(Support.getMonthByInt(Integer.parseInt(list.get(i).getMonth()) - 1) + "\n"
                    + String.valueOf(list.get(i).getYear()));
        }
        getView().setGraphData(wList, dList);
    }

    private void handleError(@NonNull String error) {
        getRouter().showError(error);
    }

    public void setGraph(List<Double> list, ProgressBar... pBars) {
        ProgressBar[] pArray = pBars;
        for (int i = 0; i < mCount; i++) {
            if(i < list.size()) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pArray[i].getLayoutParams();
                params.height = Support.dpToPx(getHeightForBMI(list.get(i)));
                pArray[i].setLayoutParams(params);
            } else {
                pArray[i].setVisibility(View.GONE);
            }
        }
    }

    public void setMonth(List<String> list, TextView... pText) {
        TextView[] pArray = pText;
        for (int i = 0; i < mCount; i++) {
            if(i < list.size()) {
                pArray[i].setText(list.get(i));
            } else {
                pArray[i].setVisibility(View.GONE);
            }
        }
    }

    public void setValues(List<Double> list, TextView... pText) {
        TextView[] pArray = pText;
        for (int i = 0; i < mCount; i++) {
            if(i < list.size()) {
                pArray[i].setText(String.valueOf(list.get(i)));
            } else {
                pArray[i].setVisibility(View.GONE);
            }
        }
    }

    private int getHeightForBMI(double bmi) {
        if(bmi < 15) return 0;
        int height = (int) (bmi - 15) * 8;
        return height;
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

//    public List<Double> bmiList() {
//        List<Double> data = new ArrayList<>();
//        data.add((double) 30);
//        data.add((double) 31);
//        data.add((double) 32);
//        data.add((double) 33);
//        data.add((double) 32);
//        data.add((double) 32);
//        data.add((double) 30);
//        data.add((double) 32);
//        data.add((double) 33);
//        data.add((double) 32);
//        data.add((double) 32);
//        data.add((double) 30);
//        return data;
//    }
//
//    public List<String> monthList() {
//        List<String> data = new ArrayList<>();
//        data.add(RaApp.getLabel(LangKeys.key_jan));
//        data.add(RaApp.getLabel(LangKeys.key_feb));
//        data.add(RaApp.getLabel(LangKeys.key_march));
//        data.add(RaApp.getLabel(LangKeys.key_april));
//        data.add(RaApp.getLabel(LangKeys.key_may));
//        data.add(RaApp.getLabel(LangKeys.key_june));
//        data.add(RaApp.getLabel(LangKeys.key_july));
//        data.add(RaApp.getLabel(LangKeys.key_aug));
//        data.add(RaApp.getLabel(LangKeys.key_sep));
//        data.add(RaApp.getLabel(LangKeys.key_oct));
//        data.add(RaApp.getLabel(LangKeys.key_nov));
//        data.add(RaApp.getLabel(LangKeys.key_dec));
//        return data;
//    }

    public void onSimulateClick() {

        getView().pushFragment(new SimulateBMIFragment());

//        if(getUserAge() > 19){
//
//        } else {
//
//        }
//
//        if(RaApp.getBase().getProfileUser().getSex().equals(RaApp.getLabel(LangKeys.key_male))){
//            //TODO MALE case for age under 19
//        } else {
//
//        }


    }

    private int getUserAge() {
        String mDob = RaApp.getBase().getProfileUser().getBirthday();
        Date mDate = Support.getDateFromString(mDob, Support.DATE_FORMAT);
        int mAge = Support.getDiffYears(mDate, new Date());
        return mAge;
    }
}
