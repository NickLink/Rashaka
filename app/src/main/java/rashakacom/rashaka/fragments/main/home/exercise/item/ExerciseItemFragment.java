package rashakacom.rashaka.fragments.main.home.exercise.item;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.communications.GPS_Tracker;
import rashakacom.rashaka.utils.communications.PointInfo;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

import static rashakacom.rashaka.utils.Consts.LOCATION_PERMISSIONS_REQUEST;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_exercise_item)
public class ExerciseItemFragment extends BaseFragment implements ExerciseItemView {

    private static final String TAG = ExerciseItemFragment.class.getSimpleName();
    private MainRouter myRouter;
    private ExerciseItemPresenter mPresenter;

    //Tracking vars
    private GPS_Tracker tracker;
    private GoogleMap googleMap;
    private OnMapReadyCallback mMapReadyCallback;
    private CameraUpdate cameraUpdate;

    private ArrayList<PointInfo> route_list;
    private int pos = 0;
    private LatLng mPoint;

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
        tracker = new GPS_Tracker(getActivity());
        route_list = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        if (mPresenter.checkPermission(getActivity())) {
            Log.e(TAG, " Permission was granted !");
            checkLocation();
        } else {
            //TODO Show that it`s impossible to do this without
            showPermissionDenied();
        }
    }

    private void checkLocation() {

        LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

//        LatLng mLocation = new LatLng(49.222234, 31.205269);
//        if (tracker.canGetLocation() && tracker.getLocation() != null) {
//            mLocation = new LatLng(tracker.getLocation().getLatitude(), tracker.getLocation().getLongitude());
//            goMap(mLocation);
//        } else {
//            //TODO Show dialog with settings - to switch on GPS
//            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(viewIntent);
//        }
    }

    private void showPermissionDenied() {
    }

    private void goMap(final LatLng mLocation) {

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        String provider = lm.getBestProvider(criteria, false);

        mMapView.onCreate(null);
        mMapView.onResume();

        MapsInitializer.initialize(getContext());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMapView.getMapAsync(googleMap1 -> {
            googleMap = googleMap1;
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mLocation).zoom((float) 18) //.bearing(45).tilt(20)
                    .build();

            cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // TODO Auto-generated method stub
                    if(mPresenter.locationValid(location)){

                        route_list.add(new PointInfo(pos, System.currentTimeMillis(), Long.toString(pos),
                                new LatLng(location.getLatitude(), location.getLongitude())));
                        Toast.makeText(RaApp.getContext(),
                                "Step " + Long.toString(pos) + " Lat=" + location.getLatitude()
                                        + " Lon=" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                        googleMap.animateCamera(cameraUpdate);

                        if(pos > 0) {
                            mPresenter.checkQuality(route_list, pos);
                            drawLine(googleMap, route_list, pos);
                        }
                        pos++;

                    }


//                    location.getSpeed();
//                    location.getTime();
//                    location.getExtras();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onProviderEnabled(String provider) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    // TODO Auto-generated method stub
                }
            });
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        });
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
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.
        checkLocation();

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
//        lineOptions.color(Color.WEEK);
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
        mPresenter.stopGpsTracker(tracker);
        super.onDestroy();
    }
}
