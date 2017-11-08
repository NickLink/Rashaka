package com.rashaka.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rashaka.RaApp;
import com.rashaka.domain.LabelItem;
import com.rashaka.domain.database.ActivityItem;
import com.rashaka.domain.database.DailyItem;
import com.rashaka.domain.login.UserLogin;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.domain.steps.StepsInMinute;
import com.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.rashaka.utils.Consts.ALARM_LIST;
import static com.rashaka.utils.Consts.GSM_TOKEN;
import static com.rashaka.utils.Consts.LANG_EN;
import static com.rashaka.utils.Consts.PREFS_LANG;
import static com.rashaka.utils.Consts.PREFS_USER;
import static com.rashaka.utils.Consts.PROFILE_EMAIL;
import static com.rashaka.utils.Consts.PROFILE_USER;
import static com.rashaka.utils.database.DatabaseContract.*;
import static com.rashaka.utils.database.DatabaseContract.ActivityEntry.*;
import static com.rashaka.utils.database.DatabaseContract.DailyStepEntry.*;
import static com.rashaka.utils.database.DatabaseContract.StepEntry.STEP_COLUMN_TIME;

/**
 * Created by User on 23.08.2017.
 */

public class RashakaBaseImpl implements RashakaBase {

    public static final String TAG = RashakaBaseImpl.class.getSimpleName();
    private static DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Context mContext;
    private HashMap<String, String> labelCache;
    private Gson gson;

    public RashakaBaseImpl(Context context) {
        this.mContext = context;
        databaseHelper = getHelper(mContext);
        db = databaseHelper.getWritableDatabase();
        labelCache = getLabelCache();
        gson = new GsonBuilder().create();
        //mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);

    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(context);

        return databaseHelper;
    }

    public HashMap<String, String> getLabelCache() {
        if (labelCache == null)
            labelCache = new HashMap<>();
        return labelCache;
    }

    @Override
    public void saveLangType(String type) {
        RaApp.getPref().edit().putString(PREFS_LANG, type).commit();
    }

    @Override
    public String getLangType() {
        return RaApp.getPref().getString(PREFS_LANG, LANG_EN);
    }

    @Override
    public void saveLabelList(List<LabelItem> labelList) {
        //Log.e(TAG, "saveLabelList");
        if (labelList != null && labelList.size() != 0) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (LabelItem item : labelList) {
                    values.put(LabelEntry.LABEL_COLUMN_KEY, item.getKeyWord());
                    values.put(LabelEntry.LABEL_COLUMN_TITLE, item.getTitle());
                    db.insert(LabelEntry.LABEL_TABLE_NAME, null, values);
                }

                db.setTransactionSuccessful();

            } catch (Exception e) {
                Log.e(TAG, "saveLabelList ->" + " Exception " + e.getLocalizedMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    @Override
    public void saveStepsInMinute(StepsInMinute stepsInMinute) {
        Log.e(TAG, "saveStepsInMinute Call ");
        if(stepsInMinute != null && stepsInMinute.getSteps() != 0){
            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(StepsEntry.STEPS_COLUMN_TIME, stepsInMinute.getTime());
                values.put(StepsEntry.STEPS_COLUMN_STEPS, stepsInMinute.getSteps());
                db.insert(StepsEntry.STEPS_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                Log.e(TAG, "saveStepsInMinute -> setTransactionSuccessful time > " + stepsInMinute.getTime()
                + " steps > " + stepsInMinute.getSteps());
            } catch (Exception e) {
                Log.e(TAG, "saveStepsInMinute -> " + " Exception " + e.getLocalizedMessage());
            } finally {
                db.endTransaction();
                Log.e(TAG, "saveStepsInMinute -> " + " finally ");
            }
        } else {
            Log.e(TAG, "saveStepsInMinute -> " + " Input data Error! ");
        }
    }

    @Override
    public List<StepsInMinute> getSteps() {
        List<StepsInMinute> list = new ArrayList<>();
        Cursor cursor = getStepsCursor();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            list.add(new StepsInMinute(
                    cursor.getLong(cursor.getColumnIndex(StepsEntry.STEPS_COLUMN_TIME)),
                    cursor.getInt(cursor.getColumnIndex(StepsEntry.STEPS_COLUMN_STEPS))
            ));
            cursor.moveToNext();
        }
        if(cursor != null)
            cursor.close();
        return list;
    }

    @Override
    public void saveStep(long time) {
        Log.e(TAG, "saveStepsInMinute Call ");
        if(time != 0){
            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(STEP_COLUMN_TIME, time);
                db.insert(StepEntry.STEP_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                Log.e(TAG, "saveSteP -> setTransactionSuccessful time > " + time);
            } catch (Exception e) {
                Log.e(TAG, "saveSteP -> " + " Exception " + e.getLocalizedMessage());
            } finally {
                db.endTransaction();
                Log.e(TAG, "saveSteP -> " + " finally ");
            }
        } else {
            Log.e(TAG, "saveSteP -> " + " Input data Error! ");
        }
    }



    @Override
    public long getStepsCount() {
        return DatabaseUtils.queryNumEntries(db, StepEntry.STEP_TABLE_NAME);
    }

    @Override
    public long getStepsCount(long from) {
        return DatabaseUtils.queryNumEntries(db,
                StepEntry.STEP_TABLE_NAME, STEP_COLUMN_TIME + " > ?", new String[] {String.valueOf(from)});
    }

    @Override
    public long getStepsCount(long from, long to) {
        return DatabaseUtils.queryNumEntries(db,
                StepEntry.STEP_TABLE_NAME, STEP_COLUMN_TIME + " > ? AND " + STEP_COLUMN_TIME + " < ?",
                new String[] {String.valueOf(from), String.valueOf(to)});
    }

    @Override
    public long getFirstStep() {
        long firstStepTime = 0;
        Cursor cursor = db.query(StepEntry.STEP_TABLE_NAME, null, null, null, null, null, STEP_COLUMN_TIME, "1");
        if(cursor != null)
        {
            if (cursor.moveToFirst()) {
                firstStepTime = cursor.getLong(cursor.getColumnIndex(STEP_COLUMN_TIME));
            }
        }
        cursor.close();
        return firstStepTime;
    }

    @Override
    public boolean deleteSteps(long from, long to) {
        db.beginTransaction();
        if(db.delete(
                StepEntry.STEP_TABLE_NAME,
                DatabaseContract.StepEntry.STEP_COLUMN_TIME + " >= " + String.valueOf(from) + " AND "
                        + DatabaseContract.StepEntry.STEP_COLUMN_TIME + " <= " + String.valueOf(to), null) > 0) {
            db.setTransactionSuccessful();
            db.endTransaction();
            return true;
        } else {
            db.endTransaction();
            return false;
        }
    }

    //TODO Activity part
    @Override
    public boolean saveActivityItem(ActivityItem item) {
        boolean success = false;
        Log.e(TAG, "saveActivityItem ->" + item.toString());
        if(item != null && item.getSteps() != 0){
            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(ACTIVITY_COLUMN_HOST, item.getHost());
                values.put(ACTIVITY_COLUMN_SEC, item.getSec());
                values.put(ACTIVITY_COLUMN_STEPS, item.getSteps());
                values.put(ACTIVITY_COLUMN_TYPE, item.getType());
                values.put(ACTIVITY_COLUMN_DATE, item.getDate());
                db.insert(ACTIVITY_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                success = true;
            } catch (Exception e) {
                Log.e(TAG, "saveActivityItem -> " + " Exception " + e.getLocalizedMessage());
            } finally {
                db.endTransaction();
                Log.e(TAG, "saveActivityItem -> " + " finally ");
            }
        } else {
            Log.e(TAG, "saveActivityItem -> " + " Input data Error! ");
        }
        return success;
    }

    private Cursor cActivityById(long id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.query(ACTIVITY_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public boolean deleteActivityItem(ActivityItem item) {
        boolean success = false;
        Cursor c = cActivityById(item.getId());
        if(c.moveToFirst()){
            db.beginTransaction();
            try {
                int count = db.delete(ACTIVITY_TABLE_NAME,
                        _ID + " = " + String.valueOf(item.getId()), null);
                if(count == 1) {
                    db.setTransactionSuccessful();
                    success = true;
                }
            } catch (Exception e){
                Log.e(TAG, "deleteActivityItem -> " + " Exception " + e.getLocalizedMessage());
            } finally {
                if(c != null)
                    c.close();
                db.endTransaction();
            }
        }
        return success;
    }

    @Override
    public ActivityItem getActivityItemById(int id) {
        ActivityItem item = null;
        Cursor c = cActivityById(id);
        if(c.moveToFirst()){
            item = new ActivityItem();
            item.setId(c.getLong(c.getColumnIndex(_ID)));
            item.setHost(c.getString(c.getColumnIndex(ACTIVITY_COLUMN_HOST)));
            item.setSec(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_SEC)));
            item.setSteps(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_STEPS)));
            item.setType(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_TYPE)));
            item.setDate(c.getString(c.getColumnIndex(ACTIVITY_COLUMN_DATE)));
        }
        if(c != null)
            c.close();
        return item;
    }

    private Cursor cActivityByDate(String date) {
        String selection = ACTIVITY_COLUMN_HOST + " = ?";
        String[] selectionArgs = {date};
        return db.query(ACTIVITY_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public List<ActivityItem> getActivityItemsByDate(String date) {
        List<ActivityItem> list = null;
        Cursor c = cActivityByDate(date);
        if(c.moveToFirst()) {
            list = new ArrayList<>();
            while (!c.isAfterLast()) {
                ActivityItem item = new ActivityItem();
                item.setId(c.getLong(c.getColumnIndex(_ID)));
                item.setHost(c.getString(c.getColumnIndex(ACTIVITY_COLUMN_HOST)));
                item.setSec(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_SEC)));
                item.setSteps(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_STEPS)));
                item.setType(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_TYPE)));
                item.setDate(c.getString(c.getColumnIndex(ACTIVITY_COLUMN_DATE)));
                list.add(item);
                c.moveToNext();
            }
        }
        if(c != null)
            c.close();
        return list;
    }

    //TODO Daily part -----------------------------------------------------
    @Override
    public boolean saveDailySteps(DailyItem item) {
        boolean success = false;
        Log.e(TAG, "saveDailySteps ->" + item.toString());
        if(item != null && item.getSteps() != 0){
            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(DAILY_COLUMN_STEPS, item.getSteps());
                values.put(DAILY_COLUMN_DATE, item.getDate());
                values.put(DAILY_COLUMN_ACTI, item.getActivities());
                values.put(DAILY_COLUMN_SYNC, item.getSync());
                db.insert(DAILY_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                success = true;
            } catch (Exception e) {
                Log.e(TAG, "saveDailySteps -> " + " Exception " + e.getLocalizedMessage());
            } finally {
                db.endTransaction();
                Log.e(TAG, "saveDailySteps -> " + " finally ");
            }
        } else {
            Log.e(TAG, "saveDailySteps -> " + " Input data Error! ");
        }
        return success;
    }

    private Cursor cDailyStepsById(long id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.query(DAILY_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public boolean deleteDailySteps(DailyItem item) {
        boolean success = false;
        Cursor c = cDailyStepsById(item.getId());
        if(c.moveToFirst()){
            db.beginTransaction();
            try {
                int count = db.delete(DAILY_TABLE_NAME,
                        _ID + " = " + String.valueOf(item.getId()), null);
                if(count == 1) {
                    db.setTransactionSuccessful();
                    success = true;
                }
            } catch (Exception e){
                Log.e(TAG, "deleteDailySteps -> " + " Exception " + e.getLocalizedMessage());
            } finally {
                if(c != null)
                    c.close();
                db.endTransaction();
            }
        }
        return success;
    }

    private Cursor cDailyStepsByDate(String date) {
        String selection = DAILY_COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};
        return db.query(DAILY_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public DailyItem getDailyItemByDate(String date) {
        DailyItem item = null;
        Cursor c = cDailyStepsByDate(date);
        if(c.moveToFirst()){
            item = getDailyItem(c);
        }
        if(c != null)
            c.close();
        return item;
    }

    private Cursor cDailySteps() {
        return db.query(DAILY_TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public List<DailyItem> getDailyItems() {
        List<DailyItem> list = null;
        Cursor c = cDailySteps();
        if(c.moveToFirst()) {
            list = new ArrayList<>();
            while (!c.isAfterLast()) {
                list.add(getDailyItem(c));
                c.moveToNext();
            }
        }
        if(c != null)
            c.close();
        return list;
    }

    private Cursor cDailyStepsBySync(int sync) {
        String selection = DAILY_COLUMN_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(sync)};
        return db.query(DAILY_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public List<DailyItem> getDailyItems(int sync) {
        List<DailyItem> list = null;
        Cursor c = cDailyStepsBySync(sync);
        if(c.moveToFirst()) {
            list = new ArrayList<>();
            while (!c.isAfterLast()) {
                list.add(getDailyItem(c));
                c.moveToNext();
            }
        }
        if(c != null)
            c.close();
        return list;
    }

    private DailyItem getDailyItem(Cursor c){
        DailyItem item = new DailyItem();
        item.setId(c.getLong(c.getColumnIndex(DatabaseContract.DailyStepEntry._ID)));
        item.setSteps(c.getInt(c.getColumnIndex(DatabaseContract.DailyStepEntry.DAILY_COLUMN_STEPS)));
        item.setDate(c.getString(c.getColumnIndex(DatabaseContract.DailyStepEntry.DAILY_COLUMN_DATE)));
        item.setActivities(c.getString(c.getColumnIndex(DatabaseContract.DailyStepEntry.DAILY_COLUMN_ACTI)));
        item.setSync(c.getInt(c.getColumnIndex(DatabaseContract.DailyStepEntry.DAILY_COLUMN_SYNC)));
        return item;
    }
    //TODO End of ------------------------------


    @Override
    public void clearLabelTable() {
        db.delete(LabelEntry.LABEL_TABLE_NAME, null, null);
    }

    @Override
    public void clearLabelCache() {
        labelCache.clear();
    }

    @Override
    public String getLabelByKey(String key) {
        Cursor cursor = labelByKey(key);
        LabelItem item = null;
        if (cursor.moveToFirst()) {
            item = new LabelItem(key, cursor.getString(cursor.getColumnIndex(LabelEntry.LABEL_COLUMN_TITLE)));
            //Log.e(TAG, "LabelItem item-> " + item.toString());
        }
        if(cursor != null)
            cursor.close();
        if (item != null)
            return item.getTitle();
        else return "EMPTY";
    }

    @Override
    public String getCachedLabelByKey(String key) {
        if (labelCache.containsKey(key)) {
            //Log.e(TAG, "Cache hit");
            return labelCache.get(key);
        } else {
            String value = getLabelByKey(key);
            labelCache.put(key, value);
            //Log.e(TAG, "Cache missed");
            return value;
        }
    }

    @Override
    public boolean isUserLogged() {
        if(getLoggedUser() != null && getProfileUser() != null)
            return true;
        else
            return false;
        //return RaApp.getPref().getBoolean(PREFS_USER_LOGGED, false);
    }

    @Override
    public void removeLoggedUser() {
        RaApp.getPref().edit().remove(PREFS_USER).commit();
        RaApp.getPref().edit().remove(PROFILE_USER).commit();
    }

    @Override
    public void removeAllUserData() {

    }

    @Override
    public void setLoggedUser(UserLogin mData) {
        RaApp.getPref().edit().putString(PREFS_USER, gson.toJson(mData)).commit();
    }

    @Override
    public UserLogin getLoggedUser() {
        String userData = RaApp.getPref().getString(PREFS_USER, null);
        if (!TextUtils.isEmpty(userData))
            return gson.fromJson(userData, UserLogin.class);
        else
            return null;
    }

    @Override
    public void setProfileUser(UserProfile mData) {
        RaApp.getPref().edit().putString(PROFILE_USER, gson.toJson(mData)).commit();
    }

    @Override
    public UserProfile getProfileUser() {
        String data = RaApp.getPref().getString(PROFILE_USER, null);
        if (!TextUtils.isEmpty(data))
            return gson.fromJson(data, UserProfile.class);
        else
            return null;
    }

    @Override
    public void saveLoggedEmail(String email) {
        RaApp.getPref().edit().putString(PROFILE_EMAIL, email).commit();
    }

    @Override
    public String getLoggedEmail() {
        return RaApp.getPref().getString(PROFILE_EMAIL, null);
    }

    @Override
    public void saveGsmToken(String token) {
        RaApp.getPref().edit().putString(GSM_TOKEN, token).commit();
    }

    @Override
    public String getGsmToken() {
        return RaApp.getPref().getString(GSM_TOKEN, null);
    }

    @Override
    public void saveAlarmList(List<DrinkAlarmItem> list) {
        RaApp.getPref().edit().putString(ALARM_LIST, gson.toJson(list)).commit();
    }

    @Override
    public List<DrinkAlarmItem> loadAlarmList() {
        String data = RaApp.getPref().getString(ALARM_LIST, null);
        if (!TextUtils.isEmpty(data))
            return gson.fromJson(data, new TypeToken<List<DrinkAlarmItem>>() {}.getType());
        else
            return new ArrayList<>();
    }


    //TODO Cursors part
    private Cursor labelByKey(String key) {
        String selection = LabelEntry.LABEL_COLUMN_KEY + " = ?";
        String[] selectionArgs = {key};
        return db.query(LabelEntry.LABEL_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    private Cursor getStepsCursor() {
        //String selection = STEPS_COLUMN_TIME;
        return db.query(LabelEntry.LABEL_TABLE_NAME, null, null, null, null, null, StepsEntry.STEPS_COLUMN_TIME);
    }

    private Cursor getStepsCursor(long dateFrom, long dateTo) {
        String selection = StepsEntry.STEPS_COLUMN_TIME + " > ?  AND " + StepsEntry.STEPS_COLUMN_TIME + " < ?";
        String[] selectionArgs = {String.valueOf(dateFrom), String.valueOf(dateTo)};
        return db.query(LabelEntry.LABEL_TABLE_NAME, null, selection, selectionArgs, null, null, StepsEntry.STEPS_COLUMN_TIME);
    }

}
