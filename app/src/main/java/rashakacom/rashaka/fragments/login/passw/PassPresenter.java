package rashakacom.rashaka.fragments.login.passw;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

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

    public void onResetPass(){
        //TODO API Call to reset password
        //getView().goResetPassword();
    }

    public void onGoSignIn(){
        getView().goSign();
    }
}
