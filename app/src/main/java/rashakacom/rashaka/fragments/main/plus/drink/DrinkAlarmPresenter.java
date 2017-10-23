package rashakacom.rashaka.fragments.main.plus.drink;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.main.plus.drink.edit.NotificationReceiver;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.content.Context.ALARM_SERVICE;
import static rashakacom.rashaka.utils.Consts.KEY_ALARM_DATA;

/**
 * Created by User on 24.08.2017.
 */

public class DrinkAlarmPresenter extends SuperPresenter<DrinkAlarmView, MainRouter> {

    private static final String TAG = DrinkAlarmPresenter.class.getSimpleName();

    public DrinkAlarmPresenter() {

    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {

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
            Log.e(TAG, "alarmtime > " + alarmTime + " date -> " + Support.getDateFromMillis(alarmTime));
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime, INTERVAL_DAY, pI);
        } else {
            Log.e(TAG, "alarmItem.isEnabled() > " + alarmItem.isEnabled());
            //TODO Disable existing alarm
            am.cancel(pI);

        }

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
