package com.rashaka.fragments.settings.settings.change;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import com.rashaka.MainRouter;
import com.rashaka.domain.BaseResponse;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class ChangePasswordPresenter extends SuperPresenter<ChangePasswordView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;
    public String TAG;

    public ChangePasswordPresenter() {
        TAG = this.getClass().getSimpleName();
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

    public void onChangePassword(@NonNull String s, @NonNull String s1, @NonNull String s2) {

        if(s.length() < 6 || s.length() > 16){
            //TODO Current password incorrect
            getView().showErrorDialod("Current password incorrect");
            return;
        }

        if(s1.length() < 6 || s1.length() > 16){
            //TODO New password incorrect
            getView().showErrorDialod("New password incorrect");
            return;
        }

        if(!s2.equals(s1)){
            //TODO Passwords not equal
            getView().showErrorDialod("Passwords not equal");
            return;
        }
        getRouter().showLoader();
        mCompositeDisposable.add(
                Rest.call().updatePassword(s, s1, s2).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
        


    }

    private void handleResponse(BaseResponse response) {
        getRouter().hideLoader();
        Log.e(TAG, "handleResponse -> " + response.toString());
    }

    private void handleError(String s) {
        getRouter().hideLoader();
        Log.e(TAG, "handhandleError -> " + s.toString());
    }
}
