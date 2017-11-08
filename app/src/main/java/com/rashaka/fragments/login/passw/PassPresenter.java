package com.rashaka.fragments.login.passw;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.LoginRouter;
import com.rashaka.domain.BaseResponse;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 22.08.2017.
 */


public class PassPresenter extends SuperPresenter <PassView, LoginRouter>{

    public PassPresenter(LoginRouter myRouter) {
        setRouter(myRouter);
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void onResetPass(String email){
        mCompositeDisposable.add(Rest.call().forgotPassword(email).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    public void onGoSignIn(){
        getView().goSign();
    }

    private void handleResponse(BaseResponse response) {
        if(response != null && response.getStatus()){
            //TODO Show result of password reset

        } else {
            handleError("From Response -> " + response.getMessage());
        }
    }

    private void handleError(String error) {
        getRouter().showError(error);
    }
}
