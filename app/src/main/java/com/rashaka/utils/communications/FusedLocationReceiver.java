package com.rashaka.utils.communications;

import android.location.Location;

/**
 * Created by User on 20.09.2017.
 */

public abstract class FusedLocationReceiver {
    public abstract void onLocationChanged(Location location);
}