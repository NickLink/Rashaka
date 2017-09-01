package rashakacom.rashaka;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import rashakacom.rashaka.utils.database.RashakaBase;
import rashakacom.rashaka.utils.database.RashakaBaseImpl;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by User on 15.08.2017.
 */

public class RaApp extends MultiDexApplication {

    private static RaApp mInstance;
    private static Context mContext;
    private static ConnectivityManager mCM;
    private static RashakaBase mBase;
    private static SharedPreferences mPref;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getContext();
        mInstance = this;
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
            mCM = (ConnectivityManager) mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mCM;
    }

    public static RashakaBase getBase() {
        if (mBase == null)
            mBase = new RashakaBaseImpl(mInstance);
        return mBase;
    }

    public static SharedPreferences getPref() {
        if (mPref == null)
            mPref = PreferenceManager.getDefaultSharedPreferences(mInstance);
        return mPref;
    }

    @NonNull
    public static String getResourceString(int id) {
        return mInstance.getString(id);
    }

    public static String getLabel(String key){
        return mBase.getCachedLabelByKey(key);
    }

}
