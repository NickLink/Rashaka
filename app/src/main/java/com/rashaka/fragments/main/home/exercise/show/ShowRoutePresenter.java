package com.rashaka.fragments.main.home.exercise.show;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.routes.PointInfo;
import com.rashaka.domain.routes.RouteInfo;
import com.rashaka.fragments.main.home.exercise.it.LocationMath;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestKeys;
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

public class ShowRoutePresenter extends SuperPresenter<ShowRouteView, MainRouter> {

    private static final String TAG = ShowRoutePresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private String id;

    @Getter
    private RouteInfo routeInfo;


    public ShowRoutePresenter() {
        mCompositeDisposable = new CompositeDisposable();
        routeInfo = new RouteInfo();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {
        if (bundle != null && bundle.containsKey(RestKeys.KEY_ID)) {
            this.id = bundle.getString(RestKeys.KEY_ID);
        }
    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        if (!TextUtils.isEmpty(id))
            mCompositeDisposable.add(Rest.call().getUserRouteById(
                    RaApp.getBase().getLoggedUser().getTocken(),
                    id
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> handleResponse(response), error -> handleError(error)));
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    private void handleError(Throwable error) {
        getRouter().showError(RestUtils.ErrorMessages(error));
        Log.e(TAG, "Error -> " + error.getLocalizedMessage());
    }

    private void handleResponse(RestResponse<RouteInfo> response) {
        if (getView().isMapReady()) {
            getView().showRoute(response.getMData().getPoints());
            getView().showTotalDistance(LocationMath.getRouteDistance(response.getMData().getDistance()) + " km");
            getView().showTotalTime(LocationMath.getRouteTime(response.getMData().getTime()) + " sec");
            getView().showAvgSpeed(String.format("%.2f", response.getMData().getPace()) + " km/h");
        }
    }

    public void drawRoute(GoogleMap map, List<PointInfo> route_list) {
        PolylineOptions lineOptions = new PolylineOptions();
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        getView().setStartPoint(route_list.get(0));
        getView().setEndPoint(route_list.get(route_list.size() - 1));

        if (true) {
            LatLngBounds.Builder bc = new LatLngBounds.Builder();
            for (int i = 0; i < route_list.size(); i++) {
                points.add(new LatLng(route_list.get(i).getLat(), route_list.get(i).getLon()));
                bc.include(new LatLng(route_list.get(i).getLat(), route_list.get(i).getLon()));
                if (!(i == 0 || i == route_list.size() - 1))
                    getView().setPoint(route_list.get(i));
            }
            lineOptions.addAll(points);
            lineOptions.width(4);
            lineOptions.color(Color.RED);
            map.addPolyline(lineOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
        }
    }

    public void drawStartPoint(GoogleMap map, PointInfo point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(point.getLat(), point.getLon()));
        map.addMarker(markerOptions.title("Start").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_blue)));
    }

    public void drawEndPoint(GoogleMap map, PointInfo point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(point.getLat(), point.getLon()));
        map.addMarker(markerOptions.title("End").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_blue)));
    }

    public void drawPoint(GoogleMap map, PointInfo point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(point.getLat(), point.getLon()));
        map.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_route_point)));
    }

}
