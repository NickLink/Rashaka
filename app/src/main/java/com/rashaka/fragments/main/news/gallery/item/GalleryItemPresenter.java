package com.rashaka.fragments.main.news.gallery.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rashaka.RaApp;
import com.rashaka.domain.gallery.GalleryItemFull;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.rashaka.MainRouter;
import com.rashaka.domain.RestResponse;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class GalleryItemPresenter extends SuperPresenter<GalleryItemView, MainRouter> {

    private static final String TAG = GalleryItemPresenter.class.getSimpleName();
    private String mToken, mLangType;

    public GalleryItemPresenter() {
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mLangType = RaApp.getBase().getLangType();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    public void loadData(String id){
        Log.e(TAG, "loadData -> " + mToken + mLangType + id);
                mCompositeDisposable.add(Rest.call().getGalleryItem(mToken, mLangType, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response),
                                error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<GalleryItemFull> response) {
        getView().setViewData(response.getMData());
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);
    }

    @Override
    public void onViewReady() {

    }

}
