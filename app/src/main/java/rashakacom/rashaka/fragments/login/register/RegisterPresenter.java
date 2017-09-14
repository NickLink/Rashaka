package rashakacom.rashaka.fragments.login.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.domain.BaseResponse;
import rashakacom.rashaka.utils.rest.RestUtils;
import rashakacom.rashaka.domain.TermsData;

/**
 * Created by User on 22.08.2017.
 */

public class RegisterPresenter extends SuperPresenter<RegisterView, LoginRouter> {

    @Override
    public void onStart(@Nullable Bundle bundle) {
    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().setViewsOk();
    }

    public void onSignInSelected() {
        getView().goSign();
    }

    public void onTermsClicked() {
        mCompositeDisposable.add(
                Rest.call().getTerms(RaApp.getBase().getLangType())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response.getMData()), error -> handleError(error)));
    }

    private void handleError(Throwable error) {
        getRouter().showError(RestUtils.ErrorMessages(error));
    }

    private void handleResponse(TermsData mData) {
        getView().setTermsDialog(mData.getTitle(), mData.getDescription());
    }

    public void onRegisterClick(
            String name,
            String lName,
            String email,
            String pass,
            String passC,
            String phone,
            boolean isChecked
    ) {
        Boolean allOk = true;

        if (!Support.nameOk(name)) {
            getView().nameError();
            allOk = false;
        }

        if (!Support.nameOk(lName)) {
            getView().lNameError();
            allOk = false;
        }

        if (!Support.emailOk(email)) {
            getView().emailError();
            allOk = false;
        }

        if (!Support.passwordOk(pass)) {
            getView().passwError();
            allOk = false;
        }

        if (TextUtils.isEmpty(passC) || !passC.equals(pass)) {
            getView().passwConfError();
            allOk = false;
        }


        //TODO Call register API method
        if (allOk) {
            mCompositeDisposable.add(
            Rest.call().signIn(
                    email,
                    pass,
                    phone,
                    name,
                    lName
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseResponse -> registerResponse(baseResponse), error -> registerError(error)));
        }

    }

    private void registerError(Throwable error) {
        //TODO Registration failed
        getRouter().showError(RestUtils.ErrorMessages(error));
    }

    private void registerResponse(BaseResponse baseResponse) {
        //TODO Registration successful or not ? :)
        if (baseResponse.getStatus()) {
            getRouter().showError(baseResponse.getMessage());
            getView().goSign();
        } else {
            getRouter().showError(baseResponse.getMessage());
        }

    }

}
