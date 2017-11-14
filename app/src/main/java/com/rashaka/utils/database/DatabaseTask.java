package com.rashaka.utils.database;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.database.DailyItem;
import com.rashaka.domain.database.DailyList;
import com.rashaka.utils.Support;
import com.rashaka.utils.rest.Rest;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 08.11.2017.
 */

public class DatabaseTask {
    private static final String TAG = DatabaseTask.class.getSimpleName();
    private static int PARTS_COUNT = 72;
    private static long PERIOD = 1200000; //1000 * 60 * 20 = 20 mins

    public static final int SYNCHRONIZED_TRUE = 1;
    public static final int SYNCHRONIZED_FALSE = 0;
    private static CompositeDisposable mCompositeDisposable;
    private SyncResult result;

    public DatabaseTask() {
    }

    public DatabaseTask(SyncResult listener) {
        this.result = listener;
    }

    public void Prepare() {
        String dateOfReg = Support.getDateFromFullDate(
                RaApp.getBase().getProfileUser().getRegisterDate());
        Log.e(TAG, "User Reg date -> " + dateOfReg);
        boolean untilToday = true;

/*        DailyItem toDel = RaApp.getBase().getDailyItemByDate("2017-11-09");
        if(toDel != null){
            Log.e(TAG, "Item to delete -> " + toDel.toString());
            RaApp.getBase().deleteDailySteps(toDel);
        } else {
            Log.e(TAG, "Item to delete -> NOT FOUND!");
        }*/

        while (untilToday) {

            long startOfSteps = RaApp.getBase().getFirstStep();//Get first item from steps database
            if (startOfSteps == 0) break;
            Log.e(TAG, "First step was at -> " + startOfSteps + " this means at -> " + Support.getDateFromMillis(startOfSteps, Support.DATE_FORMAT_FULL));

            long startOfThisDay = startOfDay(startOfSteps);
            long endOfThisDay = startOfThisDay + 86399999;

            if (startOfDay(startOfSteps) < startOfDay(System.currentTimeMillis())) {
                DailyItem item = OneDayConvert(startOfThisDay, endOfThisDay, false);
            } else {
                //This is current day - no need to do anything
                Log.e(TAG, "Prepare We on current day!!!");
                DailyItem item = OneDayConvert(startOfThisDay, endOfThisDay, true);
                untilToday = false;
            }
        }

        //TODO DataBase converted - goto Syncronization part ->
        DailyList list = new DailyList();
        list.setList(RaApp.getBase().getDailyItems(SYNCHRONIZED_FALSE));
        Log.e(TAG, "List getDailyItems -> " + list.toString());
        if (list.getList() != null && list.getList().size() > 0)
            GoSynk(list);
        else {
            Log.e(TAG, "List getDailyItems EMPTY OR NULL ");
            GoLoad();
        }
    }

    private void GoLoad() {
        String tocken = RaApp.getBase().getLoggedUser().getTocken();
        String userId = RaApp.getBase().getLoggedUser().getId();
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(Rest.call().getDailyItems(
                tocken,
                userId
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response), error -> handleError(error))
        );
    }

    public DailyItem OneDayConvert(long startOfThisDay, long endOfThisDay, boolean today) {
        long stepsCountInFirstDayStep = RaApp.getBase().getStepsCount(startOfThisDay, endOfThisDay); //getCountFromStepsDatabase(startOfThisDay, endOfThisDay);
        List<Integer> list = getListOfStepsByPeriod(startOfThisDay);
        Log.e(TAG, "NEW  Array is --->>> " + list.toString());
        int saved = 0;
        if (today) {
            //TODO Find previously saved day && add to it steps from Steps database
            DailyItem mDailyItem = RaApp.getBase()
                    .getDailyItemByDate(Support.getDateFromMillis(startOfThisDay, Support.DATE_FORMAT));

            if (mDailyItem != null) {
                saved = mDailyItem.getSteps();
                Log.e(TAG, "Previously on this day was -> " + saved + " steps");

                Type listType = new TypeToken<List<Integer>>() {}.getType();
                List<Integer> numbers = new Gson().fromJson(mDailyItem.getActivities(), listType);
                Log.e(TAG, "SAVED Array is --->>> " + numbers.toString());
                if (numbers != null && numbers.size() == PARTS_COUNT) {
                    for (int i = 0; i < PARTS_COUNT; i++)
                        list.set(i, list.get(i) + numbers.get(i));
                    Log.e(TAG, "FINAL Array is --->>> " + list.toString());
                }
            }
        }


        DailyItem item = new DailyItem();
        item.setSteps((int) stepsCountInFirstDayStep + saved);
        item.setDate(Support.getDateFromMillis(startOfThisDay, Support.DATE_FORMAT));
        item.setActivities(getJsonFromArray(list));
        item.setSync(0);

        //TODO saveToDailyStepsTable(DailyItem (with list inside));

        if (RaApp.getBase().saveDailySteps(item)) {
            Log.e(TAG, "Item Saved -> " + item.toString());
            //TODO deleteAllStepsFromStepsTable(startOfThisDay, endOfThisDay);
            //if (!today)
            RaApp.getBase().deleteSteps(startOfThisDay, endOfThisDay);

        }

        return item;
    }

    private void GoSynk(DailyList list) {
        String tocken = RaApp.getBase().getLoggedUser().getTocken();
        String userId = RaApp.getBase().getLoggedUser().getId();
        String mList = new Gson().toJson(list.getList());
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(Rest.call().postDailyItem(
                tocken,
                userId,
                mList
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response), error -> handleError(error))
        );
    }

    private void handleResponse(RestResponse<DailyList> response) {
        Log.e(TAG, "handleResponse -> " + response.toString());

        for (DailyItem item : response.getMData().getList()) {
            RaApp.getBase().saveDailySteps(item);
        }
        mCompositeDisposable.dispose();
        result.SyncSuccess();
    }

    private void handleError(Throwable error) {
        Log.e(TAG, "handleError -> " + error.getLocalizedMessage());
        mCompositeDisposable.dispose();
        result.SyncError();
    }

    private String getJsonFromArray(List<Integer> list) {
        JSONArray x = new JSONArray();
        for (Integer item : list) {
            x.put(item);
        }
        Log.e(TAG, "JSON Array to send -> " + x.toString());
        return x.toString();
    }

    private List<Integer> getListOfStepsByPeriod(long startOfThisDay) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < PARTS_COUNT; i++) {
            long s = startOfThisDay + i * PERIOD;
            list.add((int) RaApp.getBase().getStepsCount(s, s + PERIOD));
        }
        return list;
    }


    public long startOfDay(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
