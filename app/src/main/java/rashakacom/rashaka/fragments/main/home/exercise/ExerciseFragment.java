package rashakacom.rashaka.fragments.main.home.exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.domain.fake_models.Article;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_exercise)
public class ExerciseFragment extends BaseFragment implements ExerciseView {

    private MainRouter myRouter;
    private ExercisePresenter mPresenter;
    private GoogleMap googleMap;
    private OnMapReadyCallback mMapReadyCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ExercisePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mExerciseTitle.setText(RaApp.getLabel("key_track_exercise"));

    }

    @Override
    public void setAdapterData(List<Article> list) {
        mExerciseRecyclerView.setAdapter(
                new ExerciseRecyclerAdapter(getActivity(),
                        list,
                        position -> goMap(list.get(position))));
    }

    private void goMap(Article article) {

        mExerciseRecyclerView.setVisibility(View.GONE);
        mExerciseSelectedItem.setVisibility(View.VISIBLE);
        mMapContainer.setVisibility(View.VISIBLE);


        mMapView.onCreate(null);
        mMapView.onResume();

        MapsInitializer.initialize(getContext());

        mMapView.getMapAsync(googleMap1 -> {
            googleMap = googleMap1;
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(49.222234, 31.205269)).zoom((float) 4.8) //.bearing(45).tilt(20)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.9518228, 35.7796706), 13)); //
//            if (mMapReadyCallback != null)
//                mMapReadyCallback.onMapReady(googleMap);
        });
    }

    @BindView(R.id.exercise_page_title)
    TextView mExerciseTitle;

    @BindView(R.id.exercise_recycler_view)
    RecyclerView mExerciseRecyclerView;

    @BindView(R.id.exercise_item)
    LinearLayout mExerciseSelectedItem;

    @BindView(R.id.map_container)
    FrameLayout mMapContainer;

    @BindView(R.id.map_view)
    MapView mMapView;

}
