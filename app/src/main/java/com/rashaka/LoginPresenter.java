package com.rashaka;

import android.os.Handler;
import android.util.Log;

import com.rashaka.domain.PartnersData;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 23.08.2017.
 */

public class LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginRouter mRouter;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginRouter router) {
        this.mRouter = router;
        mCompositeDisposable = new CompositeDisposable();
        loadPartnersLogos();
    }

    public void loadPartnersLogos() {
        mCompositeDisposable.add(
                Rest.call().getPartnersItems()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::handleError)); //response -> handleResponse(response), error -> handleError(error)

    }

    private void handleError(Throwable error) {
        Log.e(TAG, "handleError Throwable -> " + RestUtils.ErrorMessages(error));
        mRouter.showError(RestUtils.ErrorMessages(error));
    }

    private void handleError(String errorAPI) {
        Log.e(TAG, "handleError errorAPI -> " + errorAPI);
        mRouter.showError(errorAPI);
    }

    private void handleResponse(RestResponse<PartnersData> response) {
        mRouter.setPartnersLogos(response.getMData().getList());
    }

    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    public void loadUserProfile(String tocken, String userId) {
        Log.e(TAG, "loadUserProfile with -> " + tocken + " " + userId);
        mCompositeDisposable.add(Rest.call().getUserById(userId, tocken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleGetUserResponse(response), this::handleUserError));
    }

    private void handleGetUserResponse(RestResponse<UserProfile> response) {
        Log.e(TAG, "handleGetUserResponse with -> " + response.toString());
        if (response != null && response.getStatus()) {
            //TODO Save Profile data
            RaApp.getBase().setProfileUser(response.getMData());
            //TODO Go to Main Activity
            new Handler().postDelayed(() -> mRouter.goMainActivity(), 200);
        } else {
            handleError(response.getMessage());

        }
    }

    private void handleUserError(Throwable throwable) {
        //TODO Go to Main Activity anyway
        new Handler().postDelayed(() -> mRouter.goMainActivity(), 200);
    }


}
