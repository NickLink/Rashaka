package com.rashaka.fragments.main.home.exercise.show;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
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
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.rest.RestKeys;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_exercise_show)
public class ShowRouteFragment extends BaseFragment implements ShowRouteView {

    private static final String TAG = ShowRouteFragment.class.getSimpleName();
    private MainRouter myRouter;
    private ShowRoutePresenter mPresenter;

    //Tracking vars
    private GoogleMap googleMap;
    @Getter
    private boolean mapReady;

    public static ShowRouteFragment newInstance(@NonNull long id){
        ShowRouteFragment fragment = new ShowRouteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RestKeys.KEY_ID, String.valueOf(id));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ShowRoutePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mapReady = false;
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
        mAvgText.setText(RaApp.getLabel(LangKeys.key_avg_speed));
        mTimeText.setText(RaApp.getLabel(LangKeys.key_total_time));
        mDistanceText.setText(RaApp.getLabel(LangKeys.key_total_distance));
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
    public void showRoute(List<PointInfo> list) {
        mPresenter.drawRoute(googleMap, list);
    }


    @Override
    public void animateCamera(CameraUpdate cameraUpdate) {
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void showToastMessage(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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


    @BindView(R.id.exercise_page_title)
    TextView mExerciseTitle;

    @BindView(R.id.map_container)
    FrameLayout mMapContainer;

    @BindView(R.id.map_view)
    MapView mMapView;

    @BindView(R.id.distance_total)
    TextView mDistanceTotal;

    @BindView(R.id.time_total)
    TextView mTimeTotal;

    @BindView(R.id.avg_speed)
    TextView mAvgSpeed;

    @BindView(R.id.distance_text)
    TextView mDistanceText;

    @BindView(R.id.time_text)
    TextView mTimeText;

    @BindView(R.id.avg_text)
    TextView mAvgText;

}
