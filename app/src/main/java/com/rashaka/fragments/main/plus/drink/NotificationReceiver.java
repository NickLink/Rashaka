package com.rashaka.fragments.main.plus.drink;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Consts;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by User on 15.09.2017.
 */

public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            DrinkAlarmItem alarmItem = new Gson().fromJson(intent.getStringExtra(Consts.KEY_ALARM_DATA), DrinkAlarmItem.class);
            Log.e(TAG, "onReceive set to > " + alarmItem.toString());
            Calendar c = Calendar.getInstance();
            //Check for alarm that was set on time before
            c.set(Calendar.HOUR_OF_DAY, alarmItem.getHours());
            c.set(Calendar.MINUTE, alarmItem.getMinutes());
            c.set(Calendar.AM_PM, alarmItem.getAm());
            if(c.getTimeInMillis() < System.currentTimeMillis()){
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek) {
                    case Calendar.MONDAY:
                        if (alarmItem.isEnMonday())
                            Notify(context, alarmItem);
                        break;
                    case Calendar.TUESDAY:
                        if (alarmItem.isEnTuesday())
                            Notify(context, alarmItem);
                        break;
                    case Calendar.WEDNESDAY:
                        if (alarmItem.isEnWednesday())
                            Notify(context, alarmItem);
                        break;
                    case Calendar.THURSDAY:
                        if (alarmItem.isEnThursday())
                            Notify(context, alarmItem);
                        break;
                    case Calendar.FRIDAY:
                        if (alarmItem.isEnFriday())
                            Notify(context, alarmItem);
                        break;
                    case Calendar.SATURDAY:
                        if (alarmItem.isEnSaturday())
                            Notify(context, alarmItem);
                        break;
                    case Calendar.SUNDAY:
                        if (alarmItem.isEnSunday())
                            Notify(context, alarmItem);
                        break;
                }
            } else {
                Log.e(TAG, "Time passed ->");
            }


        } catch (Exception e) {
            Log.e(TAG, "onReceive Exception > " + e.toString());
        }

    }

    private void Notify(Context context, DrinkAlarmItem alarmItem) {
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, String.valueOf(alarmItem.getId()))
                        .setSmallIcon(R.drawable.icon_bell_small)
                        .setContentTitle("Drink Alarm")
                        .setSound(Uri.parse("android.resource://com.rashaka/raw/jingle_bell"))
                        .setContentText("It is " + alarmItem.getHours()
                                + ":" + alarmItem.getMinutes()
                                + " " +  ((alarmItem.getAm() == Calendar.AM) ? RaApp.getLabel(LangKeys.key_am) : RaApp.getLabel(LangKeys.key_pm))
                                + ". Time to drink water!");

        mNotifyMgr.notify(alarmItem.getId(), mBuilder.build());
    }
}
