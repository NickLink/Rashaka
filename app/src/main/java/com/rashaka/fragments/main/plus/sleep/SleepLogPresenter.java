package com.rashaka.fragments.main.plus.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.MainRouter;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class SleepLogPresenter extends SuperPresenter<SleepLogView, MainRouter> {

    public SleepLogPresenter() {


    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setLangValues();
    }
}