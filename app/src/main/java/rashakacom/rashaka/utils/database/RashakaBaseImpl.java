package rashakacom.rashaka.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.rest.models.LabelItem;
import rashakacom.rashaka.utils.rest.models.UserData;

import static rashakacom.rashaka.utils.Consts.LANG_EN;
import static rashakacom.rashaka.utils.Consts.PREFS_LANG;
import static rashakacom.rashaka.utils.Consts.PREFS_USER;
import static rashakacom.rashaka.utils.database.DatabaseContract.LabelEntry.LABEL_COLUMN_KEY;
import static rashakacom.rashaka.utils.database.DatabaseContract.LabelEntry.LABEL_COLUMN_TITLE;
import static rashakacom.rashaka.utils.database.DatabaseContract.LabelEntry.LABEL_TABLE_NAME;

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
                Log.e(TAG, "saveWeather ->" + " Exception " + e.getLocalizedMessage());
            } finally {
                db.endTransaction();
            }
        }
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
            //Log.e("TAG", "Cache hit");
            return labelCache.get(key);
        } else {
            String value = getLabelByKey(key);
            labelCache.put(key, value);
            //Log.e("TAG", "Cache missed");
            return value;
        }
    }

    @Override
    public void setLoggedUser(UserData mData) {
        RaApp.getPref().edit().putString(PREFS_USER, gson.toJson(mData)).commit();
    }

    @Override
    public UserData getLoggedUser() {
        String userData = RaApp.getPref().getString(PREFS_USER, null);
        if (!TextUtils.isEmpty(userData))
            return gson.fromJson(userData, UserData.class);
        else
            return null;
    }


    //TODO Cursors part
    private Cursor labelByKey(String key) {
        String selection = LABEL_COLUMN_KEY + " = ?";
        String[] selectionArgs = {key};
        return db.query(LABEL_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }


}
