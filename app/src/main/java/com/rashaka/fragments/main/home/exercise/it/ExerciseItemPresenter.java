package com.rashaka.fragments.main.home.exercise.it;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.BaseResponse;
import com.rashaka.domain.routes.PointInfo;
import com.rashaka.domain.routes.RouteInfo;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

/**
 * Created by User on 24.08.2017.
 */

public class ExerciseItemPresenter extends SuperPresenter<ExerciseItemView, MainRouter> {

    private static final String TAG = ExerciseItemPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;

    @Getter
    private RouteInfo routeInfo;
    private boolean isSaving = false, follow = true;
    private int pos = 0, all_time = 0;
    private double all_distance = 0, avg_speed = 0;
    private long start_time = 0;


    public ExerciseItemPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        routeInfo = new RouteInfo();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void newPoint(Location location) {
        //LocLog(location);
        PointInfo point = new PointInfo();
        if (LocationMath.locationValid(location)) {
            point.setId((long) pos);
            point.setDesc("Point №" + pos);
            point.setLat(location.getLatitude());
            point.setLon(location.getLongitude());
            point.setAcc(location.getAccuracy());
            point.setTime(Support.GetDateTimeForAPI());

            if (pos == 0) {
                routeInfo.getPoints().add(point);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(point.getLat(), point.getLon())).zoom((float) 18) //.bearing(45).tilt(20)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory
                        .newCameraPosition(cameraPosition);
                getView().animateCamera(cameraUpdate);
                getView().setStartPoint(point);
                start_time = System.currentTimeMillis();
                pos++;
            } else {

                //TODO Check for accuracy < distance between points
                if (LocationMath.checkQuality(routeInfo.getPoints().get(pos - 1), point)) {
                    routeInfo.getPoints().add(point);
                    //TODO Distance sum calculation
                    double diff = Support.round(LocationMath.getDistanceBetween(routeInfo.getPoints().get(pos - 1), routeInfo.getPoints().get(pos)), 2);
                    all_distance = Support.round(all_distance += diff, 2);
                    all_time = (int)(System.currentTimeMillis() - start_time) / 1000;
                    avg_speed = LocationMath.getAvgSpeed(all_distance, all_time);

                    getView().showTotalDistance(LocationMath.getRouteDistance(all_distance) + " km");
                    getView().showTotalTime(LocationMath.getRouteTime(all_time) + " seconds");
                    getView().showAvgSpeed(String.format("%.2f", avg_speed) + " km/h");

                    getView().setLine(routeInfo.getPoints(), pos);
                    getView().setPoint(point);

                    pos++;
                }

            }

        } else {
            getView().showToastMessage(RaApp.getLabel(LangKeys.key_gps_accuracy)); //"GPS accuracy not enough. Please wait..."
        }
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }


    public void onStopClick(RouteInfo routeInfo) {
        if (!isSaving && routeInfo.getPoints().size() > 1) {
            getView().setEndPoint(routeInfo.getPoints().get(routeInfo.getPoints().size() - 1));
            getView().setStopSave(RaApp.getLabel(LangKeys.key_save));
            getView().setAssistantStop();
            isSaving = true;
        } else {
            if(routeInfo.getPoints().size() > 1){
                //TODO goSave();
                routeInfo.setStart(Support.getDateFromMillis(start_time, Support.DATE_FORMAT_FULL));
                routeInfo.setStop(routeInfo.getPoints().get(routeInfo.getPoints().size() - 1).getTime());
                routeInfo.setDistance(all_distance);
                routeInfo.setTime(all_time);
                routeInfo.setPace(avg_speed);
                mCompositeDisposable.add(Rest.call().postNewUserRoute(
                        RaApp.getBase().getLoggedUser().getTocken(),
                        RaApp.getBase().getLoggedUser().getId(),
                        routeInfo.getStart(),
                        routeInfo.getStop(),
                        new Gson().toJson(routeInfo.getPoints()),
                        routeInfo.getTime(), //int time
                        routeInfo.getPace(), //double pace
                        routeInfo.getDistance(), //double distance
                        "Description from user..."
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response), error -> handleError(error)));
            } else {
                //TODO Message "Nothing to Save"!
            }

        }
    }

    private void handleError(Throwable error) {
        getRouter().showError(RestUtils.ErrorMessages(error));
    }

    private void handleResponse(BaseResponse response) {
        getRouter().showError(response.getMessage());
        getView().goBack();
    }

    public void drawLine(GoogleMap map, List<PointInfo> route_list, int pos) {
        PolylineOptions lineOptions = new PolylineOptions();
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(route_list.get(pos - 1).getLat(), route_list.get(pos - 1).getLon()));
        points.add(new LatLng(route_list.get(pos).getLat(), route_list.get(pos).getLon()));
        lineOptions.addAll(points);
        lineOptions.width(4);
        lineOptions.color(Color.RED);
        map.addPolyline(lineOptions);

        if(follow){
            LatLngBounds.Builder bc = new LatLngBounds.Builder();
            for (int i = 0; i < route_list.size(); i++) {
                bc.include(new LatLng(route_list.get(i).getLat(), route_list.get(i).getLon()));
            }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
        }
    }

    public void drawStartPoint(GoogleMap map, PointInfo point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(point.getLat(), point.getLon()));
        map.addMarker(markerOptions
                .title(RaApp.getLabel(LangKeys.key_start))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_blue)));
    }

    public void drawEndPoint(GoogleMap map, PointInfo point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(point.getLat(), point.getLon()));
        map.addMarker(markerOptions
                .title(RaApp.getLabel(LangKeys.key_end))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_blue)));
    }

    public void drawPoint(GoogleMap map, PointInfo point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(point.getLat(), point.getLon()));
        map.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_route_point)));
    }

    private void LocLog(Location location) {
        Toast.makeText(RaApp.getContext(), " Step = " + Long.toString(pos) +
                        " provider = " + location.getProvider() +
                        " Accuracy = " + location.getAccuracy() +
                        " Speed = " + location.getSpeed() +
                        " Lat= " + location.getLatitude() +
                        " Lon= " + location.getLongitude(),
                Toast.LENGTH_LONG).show();

        Log.e(TAG,
                " Step = " + Long.toString(pos) +
                        " provider = " + location.getProvider() +
                        " Accuracy = " + location.getAccuracy() +
                        " Speed = " + location.getSpeed() +
                        " Lat= " + location.getLatitude() +
                        " Lon= " + location.getLongitude());

    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }
}
