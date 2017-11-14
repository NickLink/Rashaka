package com.rashaka.fragments.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.database.DailyItem;
import com.rashaka.fragments.main.home.bmi.BMIFragment;
import com.rashaka.fragments.main.home.exercise.ExerciseFragment;
import com.rashaka.fragments.main.home.steps.StepsFragment;
import com.rashaka.fragments.main.home.tips.TipsFragment;
import com.rashaka.fragments.main.home.weight.WeightFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.database.DatabaseTask;
import com.rashaka.utils.helpers.structure.SuperPresenter;

import java.lang.reflect.Type;
import java.util.List;

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
            getView().showDialogSetStepsGoal(null, RaApp.getLabel(LangKeys.key_fill_steps_goal), RaApp.getLabel(LangKeys.key_ok));
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
        long mStepsCount = 0;
        //TODO Check if present day go for steps database
        if(start == Support.startOfDay()){
            //TODO Check for existing day in database & find out is it synchronized?
            DailyItem today = null;
            today = RaApp.getBase()
                    .getDailyItemByDate(Support.getDateFromMillis(start, Support.DATE_FORMAT));
            if(today != null){
                //TODO Item was synchronized from server
                mStepsCount = today.getSteps() + RaApp.getBase().getStepsCount(start, start + 86399999);
                Log.e(TAG, "Current day with sync - DailySteps + data from Steps database");
            } else {
                //TODO Item not existed -> create it from Steps database
                mStepsCount = new DatabaseTask().OneDayConvert(start, start + 86399999, false).getSteps();
                Log.e(TAG, "Current day without sync - convert it & SAVE");
            }

        } else {
            //TODO Else go to DailyItems database
            Log.e(TAG, "Else go to DailyItems database");
            DailyItem item = null;
            item = RaApp.getBase()
                    .getDailyItemByDate(Support.getDateFromMillis(start, Support.DATE_FORMAT));
            //TODO Check for presense this data in DailyDatabase
            if(item != null){
                mStepsCount = item.getSteps();
                Log.e(TAG, "item != null  Check for presense this data in DailyDatabase -> mStepsCount" + mStepsCount);
            } else {
                //TODO If no DailyItem present -> go to StepsDatabase -> Check for steps at this day
                Log.e(TAG, "If no DailyItem present -> go to StepsDatabase -> Check for steps at this day");
                long ifStepsCount = RaApp.getBase().getStepsCount(start, start + 86399999);
                if(ifStepsCount > 0) {
                    //TODO  -> convert to Daily Item -> Return DailyItem
                    Log.e(TAG, "convert to Daily Item -> Return DailyItem");
                    item = new DatabaseTask().OneDayConvert(start, start + 86399999, false);
                    mStepsCount = item.getSteps();
                }
            }
            //TODO Show daily graph
            if(item != null && !TextUtils.isEmpty(item.getActivities())){
                Type listType = new TypeToken<List<Integer>>() {}.getType();
                List<Integer> numbers = new Gson().fromJson(item.getActivities(), listType);
                getView().setDailyGraph(numbers);
            }

        }


//        if (refresh)
//            getView().stopRefresh();
        if (getView().isRefreshing())
            getView().stopRefresh();

        Log.e(TAG, "mStepsCount -> " + mStepsCount + " from -> " + Support.startOfDay());
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
        String calories = "0.00";
        if(count != 0 && getWeight() != 0){
            double cal = 0.5 * getWeight() * count * 0.7 / 1000;
            calories = String.format("%.2f", cal);
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
        getView().setWeight(String.format("%.1f", getWeight()), getWeight() <= getWeightGoal());
    }

    private double getWeight(){
        double weight = 0;
        try {
            weight = Double.parseDouble(RaApp.getBase().getProfileUser().getWeight());
        } catch (Exception e){}
        return weight;
    }

    private double getWeightGoal(){
        double weightGoal = 0;
        try {
            weightGoal = Double.parseDouble(RaApp.getBase().getProfileUser().getWeightGoal());
        } catch (Exception e){}
        return weightGoal;
    }

    public void onStepsClick() {
        getView().pushFragment(new StepsFragment());
    }
}
