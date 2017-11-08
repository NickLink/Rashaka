package com.rashaka.fragments.main.home.weight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.BaseResponse;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class WeightPresenter extends SuperPresenter<WeightView, MainRouter> {

    private static final String TAG = WeightPresenter.class.getSimpleName();
    private double mCurrentWeight;
    private double mDefWeight = 80;
    private double mMinWeight = 30;
    private double mMaxWeight = 240;
    private String tocken, userId;


    public WeightPresenter() {
        tocken = RaApp.getBase().getLoggedUser().getTocken();
        userId = RaApp.getBase().getLoggedUser().getId();


        if(!TextUtils.isEmpty(RaApp.getBase().getProfileUser().getWeight()))
            try{
                mCurrentWeight = Double.parseDouble(RaApp.getBase().getProfileUser().getWeight());
                Log.e(TAG, "String val " + RaApp.getBase().getProfileUser().getWeight() + " parsed val " + mCurrentWeight);
            } catch (Exception e){
                mCurrentWeight = mDefWeight;
            }
        else
            mCurrentWeight = mDefWeight;
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().setBigWeightText(getWeight(mCurrentWeight));
        Log.e(TAG, "seek val " + seekProgress(mCurrentWeight));
        getView().setSeekBarValue(seekProgress(mCurrentWeight));
    }

    public int seekProgress(double value){
        double diff = value - (int)value;
        int progress = (int)(diff * 10);
        return progress;
    }

    public void onPlusClick() {
        if(mCurrentWeight < mMaxWeight){
            mCurrentWeight++;
            getView().setBigWeightText(getWeight(mCurrentWeight));
        }
    }

    public void onMinusClick() {
        if(mCurrentWeight > mMinWeight){
            mCurrentWeight--;
            getView().setBigWeightText(getWeight(mCurrentWeight));
        }

    }

    private String getWeight(double weight){
        return weight + " " + RaApp.getLabel("key_kg");
    }

    public void onSaveClick() {
        String saveWeight = String.valueOf(mCurrentWeight);
        mCompositeDisposable.add(Rest.call().postDailyWeight(tocken, userId, saveWeight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleWeightResponse(response, saveWeight), error -> handleError(error.getLocalizedMessage()))
        );
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);

    }

    private void handleWeightResponse(BaseResponse response, String saveWeight) {
        Log.e(TAG, "handleWeightResponse -> " + response.toString());
        if(response.getStatus()){
            getRouter().showError(response.getMessage());
            saveWeightChange(saveWeight);
            onSaveBmi();
            getView().recreatePager();
        }


    }

    private void saveWeightChange(String saveWeight) {
        UserProfile profile = RaApp.getBase().getProfileUser();
        profile.setWeight(saveWeight);
        RaApp.getBase().setProfileUser(profile);
    }

    private void onSaveBmi() {
        String bmi = String.valueOf(
                Support.getBMI(String.valueOf(mCurrentWeight),
                        RaApp.getBase().getProfileUser().getHight())
        );
        Log.e(TAG, "onSaveBmi -> " + bmi);
        mCompositeDisposable.add(Rest.call().postDailyBMI(tocken, userId, bmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleBmiResponse(response), error -> handleError(error.getLocalizedMessage()))
        );

    }

    private void handleBmiResponse(BaseResponse response) {
        Log.e(TAG, "handleBmiResponse -> " + response.toString());
    }

    public void setupViewPager(ViewPager mViewPager, FragmentManager childFragmentManager) { //Context context
        //WeightPagerAdapter adapter = new WeightPagerAdapter(context);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(childFragmentManager);
        mViewPager.setAdapter(adapter);
    }

    public void setSeekValue(int i) {
        int intPart = (int)mCurrentWeight;
        int decPart = i;
        mCurrentWeight = Double.valueOf(intPart + "." + decPart);
        getView().setBigWeightText(getWeight(mCurrentWeight));
    }


}
