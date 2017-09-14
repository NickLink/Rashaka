package rashakacom.rashaka.fragments.login.passw;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.domain.BaseResponse;
import rashakacom.rashaka.utils.rest.RestUtils;

/**
 * Created by User on 22.08.2017.
 */


public class PassPresenter extends SuperPresenter <PassView, LoginRouter>{

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void onResetPass(String email){
        //TODO API Call to reset password
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
