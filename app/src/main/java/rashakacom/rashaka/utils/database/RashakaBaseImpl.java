package rashakacom.rashaka.utils.database;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.domain.LabelItem;
import rashakacom.rashaka.domain.login.UserLogin;
import rashakacom.rashaka.domain.profile.UserProfile;
import rashakacom.rashaka.domain.steps.StepsInMinute;
import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

import static rashakacom.rashaka.utils.Consts.ALARM_LIST;
import static rashakacom.rashaka.utils.Consts.LANG_EN;
import static rashakacom.rashaka.utils.Consts.PREFS_LANG;
import static rashakacom.rashaka.utils.Consts.PREFS_USER;
import static rashakacom.rashaka.utils.Consts.PROFILE_EMAIL;
import static rashakacom.rashaka.utils.Consts.PROFILE_USER;
import static rashakacom.rashaka.utils.database.DatabaseContract.LabelEntry.LABEL_COLUMN_KEY;
import static rashakacom.rashaka.utils.database.DatabaseContract.LabelEntry.LABEL_COLUMN_TITLE;
import static rashakacom.rashaka.utils.database.DatabaseContract.LabelEntry.LABEL_TABLE_NAME;
import static rashakacom.rashaka.utils.database.DatabaseContract.StepEntry.STEP_COLUMN_TIME;
import static rashakacom.rashaka.utils.database.DatabaseContract.StepEntry.STEP_TABLE_NAME;
import static rashakacom.rashaka.utils.database.DatabaseContract.StepsEntry.STEPS_COLUMN_STEPS;
import static rashakacom.rashaka.utils.database.DatabaseContract.StepsEntry.STEPS_COLUMN_TIME;
import static rashakacom.rashaka.utils.database.DatabaseContract.StepsEntry.STEPS_TABLE_NAME;

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
                    values.put(LABEL_COLUMN_KEY, item.getKeyWord());
                    values.put(LABEL_COLUMN_TITLE, item.getTitle());
                    db.insert(LABEL_TABLE_NAME, null, values);
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
                values.put(STEPS_COLUMN_TIME, stepsInMinute.getTime());
                values.put(STEPS_COLUMN_STEPS, stepsInMinute.getSteps());
                db.insert(STEPS_TABLE_NAME, null, values);
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
                    cursor.getLong(cursor.getColumnIndex(STEPS_COLUMN_TIME)),
                    cursor.getInt(cursor.getColumnIndex(STEPS_COLUMN_STEPS))
            ));
            cursor.moveToNext();
        }
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
                db.insert(STEP_TABLE_NAME, null, values);
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
        return DatabaseUtils.queryNumEntries(db, STEP_TABLE_NAME);
    }

    @Override
    public long getStepsCount(long from) {
        return DatabaseUtils.queryNumEntries(db,
                STEP_TABLE_NAME, STEP_COLUMN_TIME + " > ?", new String[] {String.valueOf(from)});
    }

    @Override
    public long getStepsCount(long from, long to) {
        return DatabaseUtils.queryNumEntries(db,
                STEP_TABLE_NAME, STEP_COLUMN_TIME + " > ? AND " + STEP_COLUMN_TIME + " < ?",
                new String[] {String.valueOf(from), String.valueOf(to)});
    }

    @Override
    public void clearLabelTable() {
        db.delete(LABEL_TABLE_NAME, null, null);
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
            item = new LabelItem(key, cursor.getString(cursor.getColumnIndex(LABEL_COLUMN_TITLE)));
            //Log.e(TAG, "LabelItem item-> " + item.toString());
        }
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
        String selection = LABEL_COLUMN_KEY + " = ?";
        String[] selectionArgs = {key};
        return db.query(LABEL_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    private Cursor getStepsCursor() {
        //String selection = STEPS_COLUMN_TIME;
        return db.query(LABEL_TABLE_NAME, null, null, null, null, null, STEPS_COLUMN_TIME);
    }

    private Cursor getStepsCursor(long dateFrom, long dateTo) {
        String selection = STEPS_COLUMN_TIME + " > ?  AND " + STEPS_COLUMN_TIME + " < ?";
        String[] selectionArgs = {String.valueOf(dateFrom), String.valueOf(dateTo)};
        return db.query(LABEL_TABLE_NAME, null, selection, selectionArgs, null, null, STEPS_COLUMN_TIME);
    }

}
