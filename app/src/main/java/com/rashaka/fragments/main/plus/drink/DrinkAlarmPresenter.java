package com.rashaka.fragments.main.plus.drink;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;

import java.util.Calendar;
import java.util.List;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.content.Context.ALARM_SERVICE;
import static com.rashaka.utils.Consts.KEY_ALARM_DATA;

/**
 * Created by User on 24.08.2017.
 */

public class DrinkAlarmPresenter extends SuperPresenter<DrinkAlarmView, MainRouter> {

    private static final String TAG = DrinkAlarmPresenter.class.getSimpleName();

    public DrinkAlarmPresenter(MainRouter myRouter) {
        setRouter(myRouter);
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }


    public void setAlarm(Context context, DrinkAlarmItem alarmItem) {
        Log.e(TAG, "ALARM set to > " + alarmItem.toString());
        Intent mIntent = new Intent(context,
                NotificationReceiver.class);
        mIntent.putExtra(KEY_ALARM_DATA, new Gson().toJson(alarmItem, DrinkAlarmItem.class));
        PendingIntent pI = PendingIntent.getBroadcast(context,
                alarmItem.getId(),
                mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (alarmItem.isEnabled()) {
            Log.e(TAG, "alarmItem.isEnabled() > " + alarmItem.isEnabled());
            //TODO Enable Alarm
            // Add this day of the week line to your existing code
            //alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.set(Calendar.HOUR, alarmItem.getHours());
            alarmCalendar.set(Calendar.MINUTE, alarmItem.getMinutes());
            alarmCalendar.set(Calendar.SECOND, 0);
            alarmCalendar.set(Calendar.AM_PM, alarmItem.getAm());
            Long alarmTime = alarmCalendar.getTimeInMillis();
            //Also change the time to 24 hours.
            Log.e(TAG, "alarmtime > " + alarmTime + " date -> " + Support.getDateFromMillis(alarmTime, Support.DATE_FORMAT_FULL));
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime, INTERVAL_DAY, pI);
        } else {
            Log.e(TAG, "alarmItem.isEnabled() > " + alarmItem.isEnabled());
            //TODO Disable existing alarm
            am.cancel(pI);
        }
    }

    public void removeAlarm(Context context, DrinkAlarmItem alarmItem){
        //TODO Remove Alarm by its Id

    }

    public void setUniqueAlarmId(List<DrinkAlarmItem> list) {
        //TODO Set unique id for each alarm
        for (DrinkAlarmItem i : list
                ) {
            i.setId(list.indexOf(i));
        }
    }

    public void saveAlarmListChanges(List<DrinkAlarmItem> list) {
        RaApp.getBase().saveAlarmList(list);
    }

    public List<DrinkAlarmItem> loadAlarmList() {
        return RaApp.getBase().loadAlarmList();
    }
}
