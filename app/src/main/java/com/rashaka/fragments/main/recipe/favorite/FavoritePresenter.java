package com.rashaka.fragments.main.recipe.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.domain.fake_models.FakeNews;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import com.rashaka.MainRouter;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class FavoritePresenter extends SuperPresenter<FavoriteView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;

    public FavoritePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        mCompositeDisposable.add(Rest.call().getFakeNews3().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(FakeNews response) {
        getView().setAdapterData(response.getArticles());
    }

    private void handleError(String error) {
        getRouter().showError(error);
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

}
