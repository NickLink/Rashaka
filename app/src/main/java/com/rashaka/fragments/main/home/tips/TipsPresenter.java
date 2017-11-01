package com.rashaka.fragments.main.home.tips;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.RaApp;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.MainRouter;

/**
 * Created by User on 24.08.2017.
 */

public class TipsPresenter extends SuperPresenter<TipsView, MainRouter> {

    public TipsPresenter() {

    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().loadLink("http://demo.mbooz.com/rashaka/mobile/pages/18/"
        + RaApp.getBase().getLangType().toUpperCase());
    }
}
