package com.rashaka.utils.database;

import android.util.Log;

import com.rashaka.RaApp;
import com.rashaka.domain.database.DailyItem;
import com.rashaka.utils.Support;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 08.11.2017.
 */

public class DatabaseTask {
    private static final String TAG = DatabaseTask.class.getSimpleName();
    private static int PARTS_COUNT = 72;
    private static long PERIOD = 1200000; //1000 * 60 * 20 = 20 mins


    public static void Prepare(){
        String dateOfReg = Support.getDateFromFullDate(
                RaApp.getBase().getProfileUser().getRegisterDate());
        Log.e(TAG, "User Reg date -> " + dateOfReg);
        boolean untilToday = true;

        while (untilToday) {

            long startOfSteps = RaApp.getBase().getFirstStep();//Get first item from steps database
            if(startOfSteps == 0) break;
            Log.e(TAG, "First step was at -> " + startOfSteps + " this means at -> " + Support.getDateFromMillis(startOfSteps, Support.DATE_FORMAT_FULL));
            Date dateOfFirstStep = new Date(startOfSteps); //Get date of first step

            if (startOfDay(dateOfFirstStep) <  startOfDay(new Date(System.currentTimeMillis()))) {

                long startOfThisDay = startOfDay(dateOfFirstStep);
                long endOfThisDay = startOfThisDay + 86399999;

                long stepsCountInFirstDayStep = RaApp.getBase().getStepsCount(startOfThisDay, endOfThisDay); //getCountFromStepsDatabase(startOfThisDay, endOfThisDay);
                List<Integer> list = getListOfStepsByPeriod(startOfThisDay);

                DailyItem item = new DailyItem();
                item.setSteps((int)stepsCountInFirstDayStep);
                item.setDate(Support.getDateFromMillis(startOfSteps, Support.DATE_FORMAT));
                item.setActivities(getJsonFromArray(list));
                item.setSync(0);

                //saveToDailyStepsTable(DailyItem (with list inside));

                if (RaApp.getBase().saveDailySteps(item)) {
                    Log.e(TAG, "Item Saved -> " + item.toString());
                    RaApp.getBase().deleteSteps(startOfThisDay, endOfThisDay);
                    //deleteAllStepsFromStepsTable(startOfThisDay, endOfThisDay);
                }
                //dateOfFirstStep + 1 day;
            } else {
                //This is current day - no need to do anything
                Log.e(TAG, "Prepare We on current day!!!");
                untilToday = false;
            }
            untilToday = false;
        }

    }

    private static String getJsonFromArray(List<Integer> list) {
        JSONArray x = new JSONArray();
        for(Integer item : list){
            x.put(item);
        }
        Log.e(TAG, "JSON Array to send -> " + x.toString());
        return x.toString();
    }

    private static List<Integer> getListOfStepsByPeriod(long startOfThisDay) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0 ; i < PARTS_COUNT ; i++){
            long s = startOfThisDay + i * PERIOD;
            list.add((int) RaApp.getBase().getStepsCount(s, s + PERIOD));
        }
        return list;
    }


    public static long startOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
