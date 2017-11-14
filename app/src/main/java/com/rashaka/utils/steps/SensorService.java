package com.rashaka.utils.steps;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.BaseResponse;
import com.rashaka.domain.database.DailyItem;
import com.rashaka.utils.Support;
import com.rashaka.utils.rest.Rest;

import java.util.Calendar;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 26.09.2017.
 */

public class SensorService extends Service implements SensorEventListener, StepListener {

    private static final String TAG = SensorService.class.getSimpleName();
    private int stepCounter = 0, syncedSteps = 0;
    private static StepDetector simpleStepDetector;
    private static SensorManager sensorManager;
    private static Sensor accelSensor, countSensor;
    private static final int NOTIFICATION_ID = 1;
    private boolean isLogged = false;

    public SensorService(Context applicationContext) {
        super();
        Log.e(TAG, "SensorService here I am!");
    }

    public SensorService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.e(TAG, " onStartCommand!");

        if (getCountSensor() != null) {
            //Toast.makeText(applicationContext, "Hardware count sensor found!", Toast.LENGTH_SHORT).show();
            registerCountSensor();
        } else {
            //Toast.makeText(applicationContext, "Hardware count sensor not available!", Toast.LENGTH_SHORT).show();
            registerAccelSensor();
        }

        return START_STICKY;
    }

    private void registerCountSensor() {
        getSensorManager().unregisterListener(this, getCountSensor());
        getSensorManager().registerListener(this, getCountSensor(), SensorManager.SENSOR_DELAY_UI);
        //sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
    }

    private void registerAccelSensor() {
        getSensorManager().unregisterListener(this, getAccelSensor());
        getSimpleStepDetector().registerListener(this);
        getSensorManager().registerListener(this, getAccelSensor(), SensorManager.SENSOR_DELAY_FASTEST);

//        simpleStepDetector = new StepDetector();
//        simpleStepDetector.registerListener(this);
//        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e(TAG, "Sensor Service onTaskRemoved!");
        CleanUp();
        Intent broadcastIntent = new Intent("com.rashaka.utils.steps.RestartSensor");
        sendBroadcast(broadcastIntent);
        stopSelf();
    }

    private void CleanUp() {
        if (getSimpleStepDetector() != null) simpleStepDetector = null;
        if (getAccelSensor() != null) accelSensor = null;
        if (getCountSensor() != null) countSensor = null;
        if (getSensorManager() != null) sensorManager = null;
    }

    private void MainJob() {
        stepCounter++;
        RaApp.getBase().saveStep(System.currentTimeMillis());

        long mStepsCount = RaApp.getBase().getStepsCount(startOfDay(), startOfDay() + 86399999);
        long mStepsGoal = -1;
        try {
            mStepsGoal = Long.parseLong(RaApp.getBase().getProfileUser().getStepsGoal());
            String thisDay = Support.getDateFromMillis(System.currentTimeMillis(), Support.DATE_FORMAT);
            Log.e(TAG, "This day -> " + thisDay);
            DailyItem item = RaApp.getBase().getDailyItemByDate(thisDay);
            if(item != null){
                syncedSteps = item.getSteps();
                Log.e(TAG, "syncedSteps -> " + syncedSteps);
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "onStep NumberFormatException -> " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e(TAG, "onStep Exception -> " + e.getLocalizedMessage());
        }
        Log.e(TAG, "mStepsCount -> " + (mStepsCount + syncedSteps) + " mStepsGoal -> " + mStepsGoal);
        if ((mStepsCount + syncedSteps) == mStepsGoal) {
            NotifyStepsGoalReached();
            SendNotificationData();
        }

    }

    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {
                    Log.e(TAG, "Thread finish it job!");
                }
            }
        };
        t.start();
        return t;
    }

    private Runnable runnable = () -> {
        MainJob();
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStep(long timeNs) {
        if (isUserLogged())
            performOnBackgroundThread(runnable);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (getSimpleStepDetector() != null)
                getSimpleStepDetector().updateAccel(
                        event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            Log.e(TAG, "onStep TYPE_STEP_COUNTER");
            if (getCountSensor() != null) {
                float value = event.values[0];
                onStep((long) value);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void SendNotificationData() {
        Rest.call().postNotificationItem(
                RaApp.getBase().getLoggedUser().getTocken(),
                RaApp.getBase().getLoggedUser().getId(),
                "STEPS",
                "You have reached your steps goal! Keep pushing yourself!",
                Support.getCurrentStringDate(true)
        )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> handleResponse(response), error -> handleError(error.getLocalizedMessage()));
    }

    private void handleResponse(BaseResponse response) {
        //TODO Notification data on server
        Log.e(TAG, "handleResponse -> " + response.toString());
    }

    private void handleError(String error) {
        //TODO Save data to prefs to send it later
        Log.e(TAG, "handleError -> " + error);
    }

    private void NotifyStepsGoalReached() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.noty_rashaka_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setSound(Uri.parse("android.resource://com.rashaka/raw/jingle_bell"))
                .setContentTitle("Congratulation!")
                .setContentText("You reached your steps goal!");
        notificationManager.notify(NOTIFICATION_ID, mNotifyBuilder.build());
    }

    public static long startOfDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    private SensorManager getSensorManager() {
        if (sensorManager == null)
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        return sensorManager;
    }

    private Sensor getCountSensor() {
        if (countSensor == null)
            countSensor = getSensorManager().getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        return countSensor;
    }

    private Sensor getAccelSensor() {
        if (accelSensor == null)
            accelSensor = getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return accelSensor;
    }

    private StepDetector getSimpleStepDetector() {
        if (simpleStepDetector == null) {
            simpleStepDetector = new StepDetector();
        }
        return simpleStepDetector;
    }

    private boolean isUserLogged(){
        return RaApp.getBase().isUserLogged();
    }
}