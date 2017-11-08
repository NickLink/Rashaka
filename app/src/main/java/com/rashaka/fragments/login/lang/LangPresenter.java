package com.rashaka.fragments.login.lang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rashaka.LoginRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.LabelItem;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.rashaka.utils.Consts.ANIMATION_LEFT;

/**
 * Created by User on 22.08.2017.
 */


public class LangPresenter extends SuperPresenter<LangView, LoginRouter> {

    private static final String TAG = LangPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private String langType;

    public LangPresenter(LoginRouter myRouter) {
        setRouter(myRouter);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
//        db = getView().getDb();
    }

    public void onLangSelected(String lang) {
        this.langType = lang;
        mCompositeDisposable.add(
                Rest.call().getLabelsItems(lang)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response.getMData()), error -> handleError(error))); //this::handleResponse, this::handleError

    }

    private void handleResponse(List<LabelItem> listRestResponse) {
        if (listRestResponse != null && listRestResponse.size() != 0) {
            RaApp.getBase().saveLangType(langType);
            RaApp.getBase().clearLabelTable();
            RaApp.getBase().saveLabelList(listRestResponse);
            getRouter().callSignIn(ANIMATION_LEFT);
            getRouter().showError("All data saved succesfully!");
        } else {
            getRouter().showError("Data save Error!");
        }

    }

    private void handleError(Throwable error) {
        getRouter().showError(RestUtils.ErrorMessages(error));
        Log.e(TAG, "RestUtils.ErrorMessages(error)! " + error.getLocalizedMessage());
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

}
