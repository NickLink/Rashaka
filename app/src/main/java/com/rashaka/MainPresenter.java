package com.rashaka;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rashaka.domain.BaseResponse;
import com.rashaka.utils.Consts;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;
import com.rashaka.utils.steps.SensorService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 26.09.2017.
 */

public class MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private MainRouter mRouter;
    private CompositeDisposable mCompositeDisposable;

    private SensorService mSensorService;
    private Intent mServiceIntent;

    public MainPresenter(MainRouter mRouter) {
        this.mRouter = mRouter;
        mCompositeDisposable = new CompositeDisposable();
        sendGsmToken();
    }

    private Context getContext() {
        return ((Activity) mRouter);
    }

    public void startService() {
        if (!isMyServiceRunning(SensorService.class)) {
            mSensorService = new SensorService(getContext());
            mServiceIntent = new Intent(getContext(), mSensorService.getClass());
            getContext().startService(mServiceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.e("isMyServiceRunning?", false + "");
        return false;
    }

    public void stopService() {
        if (mServiceIntent != null)
            getContext().stopService(mServiceIntent);
    }

    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    public void sendGsmToken() {
        String GsmToken = RaApp.getBase().getGsmToken();
        if (TextUtils.isEmpty(GsmToken)) {
            GsmToken = FirebaseInstanceId.getInstance().getToken();
        }
        Log.e(TAG, "GsmToken -> " + GsmToken);
        mCompositeDisposable.add(Rest.call().addGsmToken(
                RaApp.getBase().getLoggedUser().getTocken(),
                GsmToken,
                RaApp.getBase().getLoggedUser().getId(),
                Consts.DEVICE_TYPE
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(BaseResponse response) {
        Log.e(TAG, "Response GSM token -> " + response.getMessage());
    }

    private void handleError(String error) {
        Log.e(TAG, "Error GSM token -> " + error);
    }
}
