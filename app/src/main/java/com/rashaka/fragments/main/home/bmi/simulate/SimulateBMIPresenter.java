package com.rashaka.fragments.main.home.bmi.simulate;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.fragments.main.home.bmi.web.WebBMIFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;

import static com.rashaka.utils.Consts.BMI_LINK_ADULT;
import static com.rashaka.utils.Consts.BMI_LINK_BOY;
import static com.rashaka.utils.Consts.BMI_LINK_GIRL;

/**
 * Created by User on 24.08.2017.
 */

public class SimulateBMIPresenter extends SuperPresenter<SimulateBMIView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;

    public SimulateBMIPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    public void onGirlClick() {
        getView().pushFragment(WebBMIFragment
                .newInstance(BMI_LINK_GIRL + RaApp.getBase().getLangType().toUpperCase()));
    }

    public void onBoyClick() {
        getView().pushFragment(WebBMIFragment
                .newInstance(BMI_LINK_BOY + RaApp.getBase().getLangType().toUpperCase()));
    }

    public void onAdultClick() {
        getView().pushFragment(WebBMIFragment
                .newInstance(BMI_LINK_ADULT + RaApp.getBase().getLangType().toUpperCase()));
    }
}
