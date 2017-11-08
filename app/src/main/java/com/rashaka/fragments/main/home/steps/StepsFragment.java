package com.rashaka.fragments.main.home.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.domain.fake_models.FakeDataSource;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_steps)
public class StepsFragment extends BaseFragment implements StepsView {

    private MainRouter myRouter;
    private StepsPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new StepsPresenter();
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(new StepsAdapter(getActivity(), FakeDataSource.getStepsList(), new StepsAdapter.OnClick() {
            @Override
            public void click(int position) {

            }

            @Override
            public void clickId(int position, String id) {

            }
        }));

        mPresenter.getStepsByPeriod("2017-11-08");

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {
//        share_title.setText(one);
//        share_text.setText(two);
    }

    @BindView(R.id.progress_walking)
    ProgressBar mProgressWalking;

    @BindView(R.id.progress_running)
    ProgressBar mProgressRunning;

    @BindView(R.id.progress_active)
    ProgressBar mProgressActive;

    @BindView(R.id.progress_walking_title)
    TextView mWalkingTitle;

    @BindView(R.id.progress_running_title)
    TextView mRunningTitle;

    @BindView(R.id.progress_active_today)
    TextView mActiveToday;

    @BindView(R.id.progress_active_title)
    TextView mActiveTitle;

    @BindView(R.id.progress_walking_value)
    TextView mWalkingValue;

    @BindView(R.id.progress_running_value)
    TextView mRunningValue;

    @BindView(R.id.progress_active_value)
    TextView mActiveValue;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @BindView(R.id.time_graph_layout)
    LinearLayout mTimeGraphLayout;
//
//    @BindView(R.id.share_text)
//    TextView share_text;




}
