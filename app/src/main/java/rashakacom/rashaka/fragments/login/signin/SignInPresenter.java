package rashakacom.rashaka.fragments.login.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.domain.RestResponse;
import rashakacom.rashaka.utils.rest.RestUtils;
import rashakacom.rashaka.domain.login.UserLogin;
import rashakacom.rashaka.domain.profile.UserProfile;

/**
 * Created by User on 22.08.2017.
 */

public class SignInPresenter extends SuperPresenter<SignInView, LoginRouter> {

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
                .subscribe(response -> handleLoginResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
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

    private void handleLoginResponse(RestResponse<UserLogin> response) { //LoginData
        if(response != null && response.getStatus()){
            //TODO Save login data
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
