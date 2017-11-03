package com.rashaka.fragments.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.main.home.bmi.BMIFragment;
import com.rashaka.fragments.main.home.exercise.ExerciseFragment;
import com.rashaka.fragments.main.home.steps.StepsFragment;
import com.rashaka.fragments.main.home.tips.TipsFragment;
import com.rashaka.fragments.main.home.weight.WeightFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;

import java.util.Calendar;

/**
 * Created by User on 24.08.2017.
 */

public class HomeBasePresenter extends SuperPresenter<HomeBaseView, MainRouter> {

    private static final String TAG = HomeBasePresenter.class.getSimpleName();
//    private long mStepsCount;
//    private double mDistance;
//    private long mActiveTime;
//    private long mCalories;
//    private double mWeight;

    private int stepsGoal;
    private static final int maxProgressValue = 83;


    public HomeBasePresenter() {
        stepsGoal = 0;
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setLangValue();

        //TODO Check for StepsGoal value
        if(!TextUtils.isEmpty(RaApp.getBase().getProfileUser().getStepsGoal())){
            try {
                stepsGoal = Integer.parseInt(RaApp.getBase().getProfileUser().getStepsGoal());
            } catch (Exception e){
            }

        } else {
            getView().showDialogSetStepsGoal(null, RaApp.getResourceString(R.string.dialog_set_steps_goal), null);
        }
    }

    public void onWeightClick() {
        getView().pushFragment(new WeightFragment());
    }

    public void onExerciseClick() {
        getView().pushFragment(new ExerciseFragment());
    }

    public void onTipsClick() {
        getView().pushFragment(new TipsFragment());
    }

    public void onBMIClick() {
        getView().pushFragment(new BMIFragment());
    }

    public void readData(boolean refresh, long start) {
        long mStepsCount = RaApp.getBase().getStepsCount(start, start + 86399999); //startOfDay()
//        if (refresh)
//            getView().stopRefresh();
        if (getView().isRefreshing())
            getView().stopRefresh();

        Log.e(TAG, "mStepsCount -> " + mStepsCount + " from -> " + startOfDay());
//        calculateValue();
//        setData();

        setActiveTime(mStepsCount);
        setCalories(mStepsCount);
        setDistance(mStepsCount);
        setSteps(mStepsCount);
        setWeight();
    }

    private void setActiveTime(long count){
        String time = "0:00";
        if(count != 0){
            int m = (int)count / 60;
            time = String.valueOf(m / 60) + ":" + String.format("%02d", m % 60);
        }
        getView().setActiveTime(time, getProgress((int)count, stepsGoal));
    }

    public static int getProgress(int count, int goal) {
        if(count != 0 && goal !=0) {
            int progress = (int)(((double) count / (double) goal) * maxProgressValue);
            if(progress == 0) progress = 1;
            return progress > maxProgressValue ? maxProgressValue : progress;
        }
        else
            return 1;
    }

    private void setCalories(long count){
        String calories = "0";
        if(count != 0){
            calories = String.valueOf((int)count / 20);
        }
        getView().setCalories(calories, getProgress((int)count, stepsGoal));
    }

    private void setDistance(long count){
        String distanse = "0,00";
        if(count != 0){
            distanse = String.format("%.2f", (count * 0.7) / 1000);

        }
        getView().setDistance(distanse, count > stepsGoal);
    }

    private void setSteps(long count){
        getView().setSteps(String.valueOf(count), count > stepsGoal);
    }

    private void setWeight(){
        double weight = 0, weightGoal = 0;
        try {
            weight = Double.parseDouble(RaApp.getBase().getProfileUser().getWeight());
            weightGoal = Double.parseDouble(RaApp.getBase().getProfileUser().getWeightGoal());
        } catch (Exception e){

        }
        getView().setWeight(String.format("%.1f", weight), weight <= weightGoal);
    }

    public static long startOfDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public void onStepsClick() {
        getView().pushFragment(new StepsFragment());
    }
}
