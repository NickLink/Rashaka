package com.rashaka.fragments.settings.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.rashaka.LoginActivity;
import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.BaseResponse;
import com.rashaka.fragments.settings.settings.change.ChangePasswordFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestKeys;
import com.rashaka.utils.rest.RestUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by User on 24.08.2017.
 */

public class SettingsPresenter extends SuperPresenter<SettingsView, MainRouter> {

    private static final String TAG = SettingsPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private String mToken, mUserId, mStatus;

    public SettingsPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mUserId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        if (!TextUtils.isEmpty(RaApp.getBase().getProfileUser().getEmail()))
            getView().setAccount(RaApp.getBase().getProfileUser().getEmail());
        if (!TextUtils.isEmpty(RaApp.getBase().getProfileUser().getInfo()))
            getView().setUserInfo(RaApp.getBase().getProfileUser().getInfo());
        else
            getView().setUserInfo("Please fill in your info!");
    }

    public void onNotificationChanged(boolean b) {
        int mStatus;
        if (b)
            mStatus = 1;
        else
            mStatus = 0;

        mCompositeDisposable.add(Rest.call().updateUserPushStatus(mToken, mUserId, mStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(BaseResponse response) {
        getRouter().showError(response.getMessage());
    }

    private void handleError(@NonNull String error) {
        getRouter().showError(error);
    }

    public void onChangePassword() {
        getView().pushFragment(new ChangePasswordFragment());
    }

    public void LogoutAndRestart(FragmentActivity activity) {
        //TODO Logout user first
        RaApp.getBase().removeLoggedUser();

        restart(activity);
        //TODO Restart in AlarmManager Way
//        Intent mStartActivity = new Intent(activity, LoginActivity.class);
//        int mPendingIntentId = 989796;
//        PendingIntent mPendingIntent = PendingIntent.getActivity(activity, mPendingIntentId, mStartActivity,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
//        System.exit(0);
    }

    public boolean getNotificationStatus() {
        if (RaApp.getBase().getProfileUser().getGetPush().equals("1"))
            return true;
        else
            return false;


    }

    public void restart(FragmentActivity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public void onUserInfoEditEnd(String s) {
        String tocken = RaApp.getBase().getLoggedUser().getTocken();
        String userId = RaApp.getBase().getLoggedUser().getId();
        mCompositeDisposable.add(Rest.call().updateUserProfile(
                tocken,
                userId,
                getParamsMap(s),
                null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error)))
        );

    }



    private Map<String, RequestBody> getParamsMap(String info) {
        Map<String, RequestBody> params = new HashMap<>();
        if (!TextUtils.isEmpty(info))
            params.put(RestKeys.KEY_INFO, Rest.createRequestBody(info));
        return params;
    }
}
