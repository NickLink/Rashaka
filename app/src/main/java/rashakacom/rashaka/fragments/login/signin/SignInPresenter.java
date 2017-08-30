package rashakacom.rashaka.fragments.login.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.models.LoginData;
import rashakacom.rashaka.utils.rest.models.RestResponse;
import rashakacom.rashaka.utils.rest.models.RestUtils;

/**
 * Created by User on 22.08.2017.
 */

public class SignInPresenter extends SuperPresenter<SignInView, LoginRouter> {

    private CompositeDisposable mCompositeDisposable;

    public SignInPresenter() {
        mCompositeDisposable = new CompositeDisposable();

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
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<LoginData> response) {
        if(response != null && response.getStatus()){
            //TODO Go login
            RaApp.getBase().setLoggedUser(response.getMData());
            //getView().goMainActivity();
            getRouter().goMainActivity();
        } else {
            handleError(response.getMessage());
        }
    }

    private void handleError(String error) {
        getRouter().showError(error);
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }
}
