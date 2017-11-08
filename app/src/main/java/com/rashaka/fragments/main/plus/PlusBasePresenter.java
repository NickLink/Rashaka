package com.rashaka.fragments.main.plus;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.MainRouter;
import com.rashaka.fragments.main.plus.drink.DrinkAlarmFragment;
import com.rashaka.fragments.main.plus.food.FoodLogFragment;
import com.rashaka.fragments.main.plus.sleep.SleepLogFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class PlusBasePresenter extends SuperPresenter<PlusBaseView, MainRouter> {

    public PlusBasePresenter() {


    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void onDrinkClick() {
        getView().pushFragment(new DrinkAlarmFragment());
    }

    public void onSleepClick() {
        getView().pushFragment(new SleepLogFragment());
    }

    public void onFoodClick() {
        getView().pushFragment(new FoodLogFragment());
    }
}
