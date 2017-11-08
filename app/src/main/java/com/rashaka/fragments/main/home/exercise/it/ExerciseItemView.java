package com.rashaka.fragments.main.home.exercise.it;

import com.google.android.gms.maps.CameraUpdate;
import com.rashaka.domain.routes.PointInfo;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface ExerciseItemView {

    void goBack();
    void setViewsValues();

    void setStopSave(String value);
    void setAssistantStop();

    void setStartPoint(PointInfo pointInfo);
    void setEndPoint(PointInfo pointInfo);
    void setPoint(PointInfo pointInfo);
    void setLine(List<PointInfo> list, int position);
    void animateCamera(CameraUpdate cameraUpdate);

    void showToastMessage(String message);

    void showTotalDistance(String text);
    void showTotalTime(String text);
    void showAvgSpeed(String text);
}
