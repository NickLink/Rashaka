package com.rashaka.fragments.login.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.LoginRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.login.UserLogin;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 22.08.2017.
 */

public class SignInPresenter extends SuperPresenter<SignInView, LoginRouter> {

    public SignInPresenter(LoginRouter myRouter) {
        setRouter(myRouter);
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().setViewsOk();
    }

    public void goRegister(){
        getView().goReg();
    }

    public void goForgotPass(){
        getView().goPass();
    }

    public void onSignInClick(String email, String passw) {
        mCompositeDisposable.add(Rest.call().logIn(email, passw).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleLoginResponse(response, email), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    public void onLoginCall(String userId, String tocken){
        mCompositeDisposable.add(Rest.call().getUserById(userId, tocken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleGetUserResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleGetUserResponse(RestResponse<UserProfile> response) {
        if(response != null && response.getStatus()) {
            //TODO Save Profile data
            RaApp.getBase().setProfileUser(response.getMData());
            //TODO Go MainActivity
            getRouter().goMainActivity();
        } else {
            handleError(response.getMessage());
        }
    }

    private void handleLoginResponse(RestResponse<UserLogin> response, String email) { //LoginData
        if(response != null && response.getStatus()){
            //TODO Save login data
            RaApp.getBase().saveLoggedEmail(email);
            RaApp.getBase().setLoggedUser(response.getMData());

            //TODO Go login
            onLoginCall(RaApp.getBase().getLoggedUser().getId(), RaApp.getBase().getLoggedUser().getTocken());

        } else {
            handleError(response.getMessage());
        }
    }

    private void handleError(String error) {
        getRouter().showError(error);
    }

}
