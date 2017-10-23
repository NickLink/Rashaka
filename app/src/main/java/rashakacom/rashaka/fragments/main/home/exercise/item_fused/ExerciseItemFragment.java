package rashakacom.rashaka.fragments.main.home.exercise.item_fused;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.Utility;
import rashakacom.rashaka.utils.communications.FusedLocationReceiver;
import rashakacom.rashaka.utils.communications.FusedLocationService;
import rashakacom.rashaka.utils.communications.PointInfo;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static rashakacom.rashaka.utils.Consts.LOCATION_PERMISSIONS_REQUEST;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_exercise_item)
public class ExerciseItemFragment extends BaseFragment implements ExerciseItemView {

    private MainRouter myRouter;
    private ExerciseItemPresenter mPresenter;
    private static final String TAG = "MyActivity";
    public static final int REQUEST_CHECK_SETTINGS = 998;

    //Tracking vars
    private FusedLocationService fusedLocationService;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private GoogleMap googleMap;
    private OnMapReadyCallback mMapReadyCallback;
    private CameraUpdate cameraUpdate;

    private ArrayList<PointInfo> route_list;
    private int pos = 0;
    private LatLng mPoint;
    //private LatLng mLocation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ExerciseItemPresenter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        route_list = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (!mPresenter.checkPlayServices(getActivity())) {
            //TODO NO PlayServices installed - need to show dialog
            return;
        }
        if (!mPresenter.checkPermission(getActivity())) {
            //TODO Show that it`s impossible to do this without
            showPermissionDenied();
        } else {
            Log.e(TAG, " Permission was granted !");
            mapInit();
            //checkLocation();
        }
    }

    private void getLastLocation() {

//        Location loc = mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                        }
//                    }
//                });
    }

    private void checkLocation() {

        LocationRequest request = LocationRequest.create();
        request.setPriority(PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(getActivity()).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    Log.e(TAG, "LocationSettingsResponse -> " + response.getLocationSettingsStates().toString());
                    goLocationService();

                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.e(TAG, "ApiException RESOLUTION_REQUIRED");
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        getActivity(),
                                        REQUEST_CHECK_SETTINGS);
                            }
                            catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            catch (ClassCastException e) {
                                Log.e(TAG, "ApiException ClassCastException");
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            Log.e(TAG, "ApiException SETTINGS_CHANGE_UNAVAILABLE");

                            break;
                    }
                }
            }
        });

    }

    public void goLocationService(){
        fusedLocationService = new FusedLocationService(getActivity(), new FusedLocationReceiver(){
            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "onLocationChanged");
                Toast.makeText(getActivity(), "WE GET -> Lon " + location.getLongitude()
                                + " Lat " + location.getLatitude()
                                + " Speed " + location.getSpeed()
                        , Toast.LENGTH_LONG).show();
                //updateUI();
                goMap(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });
    }

    public void mapInit(){
        mMapView.onCreate(null);
        mMapView.onResume();
        MapsInitializer.initialize(getContext());
        mMapView.getMapAsync(googleMap1 -> {
            googleMap = googleMap1;
            checkLocation();
        });
    }

    private void showPermissionDenied() {
    }

    private void goMap(final LatLng mLocation) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mLocation).zoom((float) 18) //.bearing(45).tilt(20)
                    .build();
            cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);

//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 5, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    // TODO Auto-generated method stub
//                    if(mPresenter.locationValid(location)){
//
//                        route_list.add(new PointInfo(pos, System.currentTimeMillis(), Long.toString(pos),
//                                new LatLng(location.getLatitude(), location.getLongitude())));
//                        Toast.makeText(getActivity(),
//                                "Step " + Long.toString(pos) + " Lat=" + location.getLatitude()
//                                        + " Lon=" + location.getLongitude(), Toast.LENGTH_SHORT).show();
//                        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
//                        googleMap.animateCamera(cameraUpdate);
//
//                        if(pos > 0) {
//                            mPresenter.checkQuality(route_list, pos);
//                            drawLine(googleMap, route_list, pos);
//                        }
//                        pos++;
//
//                    }
//
//
////                    location.getSpeed();
////                    location.getTime();
////                    location.getExtras();
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//                    // TODO Auto-generated method stub
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//                    // TODO Auto-generated method stub
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status,
//                                            Bundle extras) {
//                    // TODO Auto-generated method stub
//                }
//            });


    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
//        share_title.setText(one);
//        share_text.setText(two);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    checkLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showPermissionDenied();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult");
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "onActivityResult RESULT_OK");
                        // All required changes were successfully made
                        checkLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "onActivityResult RESULT_CANCELED");
                        // The user was asked to change settings, but chose not to

                        break;
                    default:
                        Log.e(TAG, "onActivityResult default");
                        break;
                }
                break;
        }
    }


//    @BindView(R.id.share_title)
//    TextView share_title;
//
//    @BindView(R.id.share_text)
//    TextView share_text;

    @BindView(R.id.exercise_page_title)
    TextView mExerciseTitle;

    @BindView(R.id.exercise_item)
    LinearLayout mExerciseSelectedItem;

    @BindView(R.id.map_container)
    FrameLayout mMapContainer;

    @BindView(R.id.map_view)
    MapView mMapView;


    void drawLine(GoogleMap map, List<PointInfo> route_list, int pos) {
        PolylineOptions lineOptions = new PolylineOptions();
        MarkerOptions markerOptions = new MarkerOptions();
        LatLngBounds.Builder bc = new LatLngBounds.Builder();
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0 ; i < route_list.size() ; i ++){
            bc.include(route_list.get(i).getCoordinates());
        }
        points.add(route_list.get(pos - 1).getCoordinates());
        points.add(route_list.get(pos).getCoordinates());
        lineOptions.addAll(points);
        lineOptions.width(3);
        lineOptions.color(Color.RED);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
        map.addPolyline(lineOptions);
    }
//  .get(pos - 1).getCoordinates(), route_list.get(pos).getCoordinates()
//    void draw_route(ArrayList<PointInfo> list_to_draw) {
//        map.clear();
//        PolylineOptions lineOptions = new PolylineOptions();
//        MarkerOptions markerOptions = new MarkerOptions();
//        LatLngBounds.Builder bc = new LatLngBounds.Builder();
//        ArrayList<LatLng> points = new ArrayList<LatLng>();
//        for (int i = 0; i < list_to_draw.size(); i++) {
//            bc.include(list_to_draw.get(i).coordinates);
//            points.add(list_to_draw.get(i).coordinates);
//        }
//        lineOptions.addAll(points);
//        lineOptions.width(6);
//        lineOptions.color(Color.RED);
//        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
//
//        //Add start & end of route
//        markerOptions.position(points.get(0));
//        map.addMarker(markerOptions.title("Start").icon(BitmapDescriptorFactory.fromResource(R.drawable.store_pin)));
//        markerOptions.position(points.get(points.size() - 1));
//        map.addMarker(markerOptions.title("End").icon(BitmapDescriptorFactory.fromResource(R.drawable.store_pin)));
//        //Draw route
//        map.addPolyline(lineOptions);
//    }


    @Override
    public void onDestroy() {
       // mPresenter.stopGpsTracker(tracker);
        super.onDestroy();
    }



}
