package com.rashaka.fragments.main.home.exercise.it;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import com.rashaka.domain.routes.PointInfo;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Support;

/**
 * Created by User on 25.10.2017.
 */

public class LocationMath {

    public static boolean checkQuality(PointInfo from, PointInfo to) {
        double diff = getDistanceBetween(from, to);
        double midAccuracy = getMidAccuracy(from, to);
        if (diff > midAccuracy)
            return true;
        else
            return false;
    }

    public static double getDistanceBetween(PointInfo from, PointInfo to) {
        return SphericalUtil.computeDistanceBetween(
                new LatLng(from.getLat(), from.getLon()),
                new LatLng(to.getLat(), to.getLon()));
    }

//    public static double getDistanceBetween(LatLng oldPosition, LatLng newPosition){
//        float[] results = new float[1];
//        Location.distanceBetween(oldPosition.latitude, oldPosition.longitude,
//                newPosition.latitude, newPosition.longitude, results);
//        return (double) results[0];
//    }

//    public static float getDistanceBetween(LatLng oldPosition, LatLng newPosition) {
//        Location startPoint = new Location("locationA");
//        startPoint.setLatitude(oldPosition.latitude);
//        startPoint.setLongitude(oldPosition.longitude);
//
//        Location endPoint = new Location("locationB");
//        endPoint.setLatitude(newPosition.latitude);
//        endPoint.setLongitude(newPosition.longitude);
//
//        float distance = startPoint.distanceTo(endPoint);
//        return distance;
//    }


    public static double getMidAccuracy(PointInfo from, PointInfo to) {
        return (from.getAcc() + to.getAcc()) / 2;
    }

    public static boolean locationValid(Location location) {
        if (location != null && location.getLatitude() != 0 && location.getLongitude() != 0
                && location.getAccuracy() < Consts.ACCURACY)
            return true;
        else
            return false;
    }

    public static String getRouteDistance(double meters){
        return String.format("%.2f", meters / 1000);
    }

    public static String getRouteTime(int secs) {
        StringBuilder sb = new StringBuilder();
        int seconds = secs % 60;
        int minutes = (secs / (60)) % 60;
        int hours = (secs / (60 * 60)) % 24;
        if (hours > 0)
            sb.append(hours + ":");
        if (minutes > 0)
            sb.append(String.format("%02d", minutes) + ":");

        sb.append(String.format("%02d", seconds));
        return sb.toString();
    }

    public static double getAvgSpeed(double dist, int time){
        return Support.round((dist / time) * 3.6, 2);
    }
}
