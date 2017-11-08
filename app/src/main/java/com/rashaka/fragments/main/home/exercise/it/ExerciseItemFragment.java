package com.rashaka.fragments.main.home.exercise.it;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.routes.PointInfo;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.communications.LocationAssistant;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_exercise_item)
public class ExerciseItemFragment extends BaseFragment implements ExerciseItemView, LocationAssistant.Listener {

    private static final String TAG = ExerciseItemFragment.class.getSimpleName();
    private MainRouter myRouter;
    private ExerciseItemPresenter mPresenter;

    //Tracking vars
    private LocationAssistant assistant;
    private GoogleMap googleMap;
    //private CameraUpdate cameraUpdate;
    @Getter
    private boolean mapReady = false;
    private ExerciseItemLayout mLayout;


    private long UPDATE_INTERVAL = 5000;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ExerciseItemPresenter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assistant = new LocationAssistant(getActivity(), this, LocationAssistant.Accuracy.HIGH, UPDATE_INTERVAL, false);


        //route_list = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mLayout = new ExerciseItemLayout();
        ButterKnife.bind(mLayout, mExerciseItem);

        mLayout.mItemStartButton.setOnClickListener(view1 -> mPresenter.onStopClick(mPresenter.getRouteInfo()));

        mMapFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setFollow(false);
            }
        });
        mMapFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setFollow(true);
            }
        });

        mapInit();
    }

    private void mapInit() {
        if (!mapReady) {
            mMapView.onCreate(null);
            mMapView.onResume();
            MapsInitializer.initialize(getContext());

            mMapView.getMapAsync(googleMap1 -> {
                googleMap = googleMap1;
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                assistant.start();
                mapReady = true;
            });
        }
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goBack() {
        mFragmentNavigation.popFragment();
    }

    @Override
    public void setViewsValues() {
        mLayout.mItemStartButton.setText(RaApp.getLabel(LangKeys.key_stop));
        mLayout.mItemStartText.setText(RaApp.getLabel(LangKeys.key_start));
        mLayout.mItemStartText.setText(Support.getCurrentStringDate(true));

        mAvgText.setText(RaApp.getLabel(LangKeys.key_avg_speed));
        mTimeText.setText(RaApp.getLabel(LangKeys.key_total_time));
        mDistanceText.setText(RaApp.getLabel(LangKeys.key_total_distance));

        mMapFollow.setText(RaApp.getLabel(LangKeys.key_map_follow));
        mMapFree.setText(RaApp.getLabel(LangKeys.key_map_free));

    }

    @Override
    public void setStopSave(String value) {
        mLayout.mItemStartButton.setText(value);
    }

    @Override
    public void setAssistantStop() {
        assistant.stop();
    }

    @Override
    public void setStartPoint(PointInfo pointInfo) {
        mPresenter.drawStartPoint(googleMap, pointInfo);
    }

    @Override
    public void setEndPoint(PointInfo pointInfo) {
        mPresenter.drawEndPoint(googleMap, pointInfo);
    }

    @Override
    public void setPoint(PointInfo pointInfo) {
        mPresenter.drawPoint(googleMap, pointInfo);
    }

    @Override
    public void setLine(List<PointInfo> list, int position) {
        mPresenter.drawLine(googleMap, list, position);
    }

    @Override
    public void animateCamera(CameraUpdate cameraUpdate) {
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void showToastMessage(String message) {
        if(isVisible()){
            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    @Override
    public void showTotalDistance(String text) {
        mDistanceTotal.setText(text);
    }

    @Override
    public void showTotalTime(String text) {
        mTimeTotal.setText(text);
    }

    @Override
    public void showAvgSpeed(String text) {
        mAvgSpeed.setText(text);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        assistant.start();
//    }
//
//    @Override
//    public void onPause() {
//        assistant.stop();
//        super.onPause();
//    }

    @Override
    public void onDestroy() {
        assistant.stop();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        assistant.onPermissionsUpdated(requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        assistant.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onNeedLocationPermission() {
        assistant.requestAndPossiblyExplainLocationPermission();
    }

    @Override
    public void onExplainLocationPermission() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.permissionExplanation)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        assistant.requestLocationPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        tvLocation.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                assistant.requestLocationPermission();
//                            }
//                        });
                    }
                })
                .show();
    }

    @Override
    public void onLocationPermissionPermanentlyDeclined(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.permissionPermanentlyDeclined)
                .setPositiveButton(R.string.ok, fromDialog)
                .show();
    }

    @Override
    public void onNeedLocationSettingsChange() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.switchOnLocationShort)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        assistant.changeLocationSettings();
                    }
                })
                .show();
    }

    @Override
    public void onFallBackToSystemSettings(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog) {

    }

    @Override
    public void onNewLocationAvailable(Location location) {
        mPresenter.newPoint(location);
        //Location best = assistant.getBestLocation();
//        Log.e(TAG,
//                " provider = " + best.getProvider() +
//                        " Accuracy = " + best.getAccuracy() +
//                        " Speed = " + best.getSpeed() +
//                        " Lat= " + best.getLatitude() +
//                        " Lon= " + best.getLongitude());
    }

    @Override
    public void onMockLocationsDetected(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog) {

    }

    @Override
    public void onError(LocationAssistant.ErrorType type, String message) {

    }


    @BindView(R.id.exercise_page_title)
    TextView mExerciseTitle;

    @BindView(R.id.map_container)
    FrameLayout mMapContainer;

    @BindView(R.id.map_view)
    MapView mMapView;

    @BindView(R.id.stop_item)
    View mExerciseItem;

    @BindView(R.id.distance_total)
    TextView mDistanceTotal;

    @BindView(R.id.time_total)
    TextView mTimeTotal;

    @BindView(R.id.avg_speed)
    TextView mAvgSpeed;

    @BindView(R.id.follow_map)
    TextView mMapFollow;

    @BindView(R.id.free_map)
    TextView mMapFree;

    @BindView(R.id.distance_text)
    TextView mDistanceText;

    @BindView(R.id.time_text)
    TextView mTimeText;

    @BindView(R.id.avg_text)
    TextView mAvgText;


    static class ExerciseItemLayout {
        @BindView(R.id.item_pin_button)
        ImageView mPinIcon;

        @BindView(R.id.item_start_text)
        TextView mItemStartText;

        @BindView(R.id.item_date_text)
        TextView mItemDateText;

        @BindView(R.id.item_start_button)
        TextView mItemStartButton;
    }


}
