package com.rashaka.fragments.main.home.bmi.web;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import com.rashaka.MainRouter;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class WebBMIPresenter extends SuperPresenter<WebBMIView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;
    private String link;

    public WebBMIPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().loadLink(this.link);
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    public void setLoadLink(String link) {
        this.link = link;
    }
}
