package com.rashaka.fragments.settings.notification;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.domain.fake_models.FakeDataSource;
import com.rashaka.domain.fake_models.FakeNotification;
import com.rashaka.domain.notif.NotificationItem;
import com.rashaka.fragments.BackFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.CustomLayoutManager;
import com.rashaka.utils.helpers.views.EmptyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_notification)
public class NotificationFragment extends BackFragment implements NotificationView {

    private static final String TAG = NotificationFragment.class.getSimpleName();
    private MainRouter myRouter;
    private NotificationPresenter mPresenter;
    List<FakeNotification> list;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new NotificationPresenter();
    }

    @Override
    public void onBackButtonPressed() {
        Log.e(TAG, "SettingsFragment ON BACK Pressed -> ");
        myRouter.onBackPressed();
        //mFragmentNavigation.popFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        list = FakeDataSource.getFakeNotificationList();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
        }
        super.onCreateOptionsMenu(menu, inflater);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackButtonPressed());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.hasFixedSize();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void setLangValue() {

    }

    @Override
    public void setAdapterData(List<NotificationItem> list) {
        if (false) { //list != null && list.size() > 0
            mEmptyList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            NotificationAdapter adapter = new NotificationAdapter(
                    getActivity(),
                    list,
                    pos -> {
                        //TODO Notification item Click
                        //Toast.makeText(getActivity(), "DO NOT TOUCH -> " + pos, Toast.LENGTH_SHORT).show();
                    });
            mRecyclerView.setAdapter(adapter);
        } else {
            mEmptyList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void addAdapterData(List<NotificationItem> mData) {
        if (mRecyclerView.getAdapter() != null)
            ((NotificationAdapter) mRecyclerView.getAdapter()).addData(mData);
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_list)
    EmptyListView mEmptyList;
}
