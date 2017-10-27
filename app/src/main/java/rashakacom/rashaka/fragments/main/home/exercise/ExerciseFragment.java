package rashakacom.rashaka.fragments.main.home.exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.domain.routes.RouteInfo;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.home.exercise.show.ShowRouteFragment;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_exercise)
public class ExerciseFragment extends BaseFragment implements ExerciseView {

    private MainRouter myRouter;
    private ExercisePresenter mPresenter;
    private AddRouteLayout mAddLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ExercisePresenter();
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

        mAddLayout = new AddRouteLayout();
        ButterKnife.bind(mAddLayout, mRouteAdd);
        mAddLayout.mItemStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onNewExersize();
            }
        });

        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mExerciseRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        mExerciseRecyclerView.addItemDecoration(mDividerItemDecoration);

        mExerciseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mPresenter.onScrolled(recyclerView);
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mExerciseTitle.setText(RaApp.getLabel(LangKeys.key_track_exercise));
        mAddLayout.mItemStartText.setText(RaApp.getLabel(LangKeys.key_start));
        mAddLayout.mItemStartButton.setText(RaApp.getLabel(LangKeys.key_start));
        mAddLayout.mItemDateText.setText(Support.getCurrentStringDate(true));
    }

    @Override
    public void setAdapterData(List<RouteInfo> list) {
        mExerciseRecyclerView.setAdapter(
                new ExerciseRecyclerAdapter(getActivity(),
                        list,
                        position -> goMap(list.get(position))));
    }

    @Override
    public void addAdapterData(List<RouteInfo> list) {
        ((ExerciseRecyclerAdapter)mExerciseRecyclerView.getAdapter()).addData(list);
    }

    private void goMap(RouteInfo routeInfo) {
        mFragmentNavigation.pushFragment(ShowRouteFragment.newInstance(routeInfo.getId()));
    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        mFragmentNavigation.pushFragment(fragment);
    }

    @BindView(R.id.exercise_page_title)
    TextView mExerciseTitle;

    @BindView(R.id.exercise_recycler_view)
    RecyclerView mExerciseRecyclerView;

    //    @BindView(R.id.exercise_item)
//    LinearLayout mExerciseSelectedItem;
//
//    @BindView(R.id.map_container)
//    FrameLayout mMapContainer;
//
//    @BindView(R.id.map_view)
//    MapView mMapView;
    @BindView(R.id.route_add)
    View mRouteAdd;

    static class AddRouteLayout {
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
