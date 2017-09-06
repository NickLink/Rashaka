package rashakacom.rashaka.fragments.login.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.models.LoginData;
import rashakacom.rashaka.utils.rest.models.RestResponse;
import rashakacom.rashaka.utils.rest.models.RestUtils;
import rashakacom.rashaka.utils.rest.models.UserData;

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
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<UserData> response) { //LoginData
        if(response != null && response.getStatus()){
            //TODO Go login

            RaApp.getBase().setLoggedUser(response.getMData());
            getRouter().goMainActivity();
        } else {
            handleError(response.getMessage());
        }
    }

    private void handleError(String error) {
        getRouter().showError(error);
    }

}
