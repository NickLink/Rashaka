package com.rashaka.fragments.main.home.weight.year;

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
import com.rashaka.domain.weight.WeightYear;
import com.rashaka.domain.weight.WeightYearItem;
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

public class YearPresenter extends SuperPresenter<YearView, MainRouter> {

    private static final String TAG = YearPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private int mCount = 12;
    private String mToken, mUserId;

    public YearPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mUserId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        mCompositeDisposable.add(Rest.call().getUserWeightYear(mToken, mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<WeightYear> response) {
        if(response.getStatus()) {
            List<WeightYearItem> list = response.getMData().getList();
            Log.e(TAG, "List ->" + list.toString());
            List<Double> wList = new ArrayList<>();
            List<String> dList = new ArrayList<>();
            Log.e(TAG, "List size ->" + list.size());
            for (int i = 0; i < list.size(); i++) {
                Log.e(TAG, "List weight ->" + list.get(i).getAvgWeight());
                Log.e(TAG, "List date ->" + list.get(i).getMonth());

                wList.add(Double.parseDouble(list.get(i).getAvgWeight()));
                dList.add(Support.getMonthByInt(Integer.parseInt(list.get(i).getMonth()) - 1) + "\n"
                        + list.get(i).getYear());
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

    public void setGraph(List<Double> list, ProgressBar... pBars){
        ProgressBar[] pArray = pBars;
        for(int i = 0 ; i < mCount ; i++){
            if(i < list.size()){
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)pArray[i].getLayoutParams();
                params.height = Support.dpToPx((int)(list.get(i) * 2));
                pArray[i].setLayoutParams(params);
            } else {
                pArray[i].setVisibility(View.GONE);
            }
        }
    }

    public void setDays(List<String> list, TextView... pText){
        TextView[] pArray = pText;
        for(int i = 0 ; i < mCount ; i++){
            if(i < list.size()){
                pArray[i].setText(list.get(i));
            } else {
                pArray[i].setVisibility(View.GONE);
            }
        }
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

//    public List<Integer> weightList(){
//        List<Integer> data = new ArrayList<>();
//        data.add(80);
//        data.add(85);
//        data.add(88);
//        data.add(90);
//        data.add(86);
//        data.add(84);
//        data.add(80);
//        data.add(85);
//        data.add(88);
//        data.add(90);
//        data.add(86);
//        data.add(84);
//        return data;
//    }
//
//    public List<String> daysList(){
//        List<String> data = new ArrayList<>();
//        data.add(RaApp.getLabel(LangKeys.key_nov));
//        data.add(RaApp.getLabel(LangKeys.key_dec));
//        data.add(RaApp.getLabel(LangKeys.key_jan));
//        data.add(RaApp.getLabel(LangKeys.key_feb));
//        data.add(RaApp.getLabel(LangKeys.key_march));
//        data.add(RaApp.getLabel(LangKeys.key_may));
//        data.add(RaApp.getLabel(LangKeys.key_nov));
//        data.add(RaApp.getLabel(LangKeys.key_dec));
//        data.add(RaApp.getLabel(LangKeys.key_jan));
//        data.add(RaApp.getLabel(LangKeys.key_feb));
//        data.add(RaApp.getLabel(LangKeys.key_march));
//        data.add(RaApp.getLabel(LangKeys.key_may));
//        return data;
//    }

}
