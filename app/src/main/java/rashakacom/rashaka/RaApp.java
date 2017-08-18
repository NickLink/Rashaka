package rashakacom.rashaka;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by User on 15.08.2017.
 */

public class RaApp extends MultiDexApplication {

    private static Context sContext;
    private static SharedPreferences sPref;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        sPref = sContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    public static Context getContext() {
        return sContext;
    }

    @NonNull
    public static String getResourceString(int id) {
        return sContext.getString(id);
    }

}
