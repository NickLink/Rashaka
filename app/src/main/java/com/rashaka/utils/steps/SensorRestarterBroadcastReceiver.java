package com.rashaka.utils.steps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by User on 26.09.2017.
 */

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = SensorRestarterBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG , "onReceive SensorService intent");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") ||
                intent.getAction().equals("android.intent.action.MY_PACKAGE_REPLACED")||
                        intent.getAction().equals("com.rashaka.utils.steps.RestartSensor")) {
            context.startService(new Intent(context, SensorService.class));
        }

    }

}
