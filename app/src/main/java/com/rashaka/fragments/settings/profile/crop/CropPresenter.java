package com.rashaka.fragments.settings.profile.crop;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import com.rashaka.MainRouter;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class CropPresenter extends SuperPresenter<CropView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;

    public CropPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {

    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

}
