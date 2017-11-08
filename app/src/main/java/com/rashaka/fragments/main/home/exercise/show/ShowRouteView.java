package com.rashaka.fragments.main.home.exercise.show;

import com.google.android.gms.maps.CameraUpdate;
import com.rashaka.domain.routes.PointInfo;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface ShowRouteView {

    void goBack();
    void setViewsValues();

    void setStartPoint(PointInfo pointInfo);
    void setEndPoint(PointInfo pointInfo);
    void setPoint(PointInfo pointInfo);
    void showRoute(List<PointInfo> list);
    void animateCamera(CameraUpdate cameraUpdate);

    void showToastMessage(String message);

    void showTotalDistance(String text);
    void showTotalTime(String text);
    void showAvgSpeed(String text);

    boolean isMapReady();
}
