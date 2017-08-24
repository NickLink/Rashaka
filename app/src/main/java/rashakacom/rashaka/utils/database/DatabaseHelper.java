package rashakacom.rashaka.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 22.08.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "rashaka.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e(TAG,"DatabaseHelper ->" + " onCreate");
        final String SQL_CREATE_LABELS_TABLE = "CREATE TABLE "
                + DatabaseContract.LabelEntry.LABEL_TABLE_NAME + " ("
                + DatabaseContract.LabelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.LabelEntry.LABEL_COLUMN_KEY + " TEXT NOT NULL, "
                + DatabaseContract.LabelEntry.LABEL_COLUMN_TITLE + " TEXT NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_LABELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
