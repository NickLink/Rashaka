package com.rashaka;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.rashaka.utils.database.RashakaBase;
import com.rashaka.utils.database.RashakaBaseImpl;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by User on 15.08.2017.
 */

public class RaApp extends MultiDexApplication {

    //private static RaApp mInstance;
    private static Context mContext;
    private static ConnectivityManager mCM;
    private static RashakaBase mBase;
    private static SharedPreferences mPref;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        mContext = this;
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    public static Context getContext() {
        return mContext; //mInstance
    }

    public static ConnectivityManager getCM() {
        if (mCM == null)
            mCM = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mCM;
    }

    public static RashakaBase getBase() {
        if (mBase == null)
            mBase = new RashakaBaseImpl(mContext);
        return mBase;
    }

    public static SharedPreferences getPref() {
        if (mPref == null)
            mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        return mPref;
    }

    @NonNull
    public static String getResourceString(int id) {
        return mContext.getString(id);
    }

    public static String getLabel(String key){
        return mBase.getCachedLabelByKey(key);
    }

}
