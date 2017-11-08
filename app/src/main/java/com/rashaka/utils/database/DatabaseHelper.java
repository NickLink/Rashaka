package com.rashaka.utils.database;

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

        final String SQL_CREATE_STEPS_TABLE = "CREATE TABLE "
                + DatabaseContract.StepsEntry.STEPS_TABLE_NAME + " ("
                + DatabaseContract.StepsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.StepsEntry.STEPS_COLUMN_TIME + " INTEGER NOT NULL, "
                + DatabaseContract.StepsEntry.STEPS_COLUMN_STEPS + " INTEGER NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_STEPS_TABLE);

        final String SQL_CREATE_STEP_TABLE = "CREATE TABLE "
                + DatabaseContract.StepEntry.STEP_TABLE_NAME + " ("
                + DatabaseContract.StepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.StepEntry.STEP_COLUMN_TIME + " INTEGER NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_STEP_TABLE);

        final String SQL_CREATE_DAILYSTEP_TABLE = "CREATE TABLE "
                + DatabaseContract.DailyStepEntry.DAILY_TABLE_NAME + " ("
                + DatabaseContract.DailyStepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.DailyStepEntry.DAILY_COLUMN_STEPS + " INTEGER NOT NULL, "
                + DatabaseContract.DailyStepEntry.DAILY_COLUMN_DATE + " TEXT NOT NULL, "
                + DatabaseContract.DailyStepEntry.DAILY_COLUMN_ACTI + " TEXT NOT NULL, "
                + DatabaseContract.DailyStepEntry.DAILY_COLUMN_SYNC + " INTEGER NOT NULL DEFAULT 0"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_DAILYSTEP_TABLE);

        final String SQL_CREATE_ACTIVITY_TABLE = "CREATE TABLE "
                + DatabaseContract.ActivityEntry.ACTIVITY_TABLE_NAME + " ("
                + DatabaseContract.ActivityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.ActivityEntry.ACTIVITY_COLUMN_HOST + " TEXT NOT NULL, "
                + DatabaseContract.ActivityEntry.ACTIVITY_COLUMN_SEC + " INTEGER NOT NULL, "
                + DatabaseContract.ActivityEntry.ACTIVITY_COLUMN_STEPS + " INTEGER NOT NULL, "
                + DatabaseContract.ActivityEntry.ACTIVITY_COLUMN_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + DatabaseContract.ActivityEntry.ACTIVITY_COLUMN_DATE + " TEXT NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_ACTIVITY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
