package com.rashaka.fragments.main.plus.sleep;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.sleep.SleepItem;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.fragments.main.plus.sleep.dialogs.DateDialog;
import com.rashaka.fragments.main.plus.sleep.dialogs.DateResult;
import com.rashaka.fragments.main.plus.sleep.dialogs.TimeDialog;
import com.rashaka.fragments.main.plus.sleep.dialogs.TimeResult;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.Utility;
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

@Layout(id = R.layout.fr_plus_sleep)
public class SleepLogFragment extends BaseFragment implements SleepLogView, DateResult, TimeResult {

    private static final String TAG = SleepLogFragment.class.getSimpleName();
    private MainRouter myRouter;
    private SleepLogPresenter mPresenter;
    private IncludedLayout mLayoutStart, mLayoutEnd;
    private String mDateStart, mDateEnd;
    public static int M_START = 1, M_END = 0;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new SleepLogPresenter(myRouter);
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

        mLayoutStart = new IncludedLayout();
        mLayoutEnd = new IncludedLayout();

        ButterKnife.bind(mLayoutStart, mSleepStart );
        ButterKnife.bind(mLayoutEnd, mSleepEnd );

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.hasFixedSize();
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

        mLayoutStart.mItemTimeText.setOnClickListener(view1 -> {
            BottomSheetDialogFragment bottomDialog = TimeDialog.newInstance(M_START);
            bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
        });
        mLayoutEnd.mItemTimeText.setOnClickListener(view12 -> {
            BottomSheetDialogFragment bottomDialog = TimeDialog.newInstance(M_END);
            bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
        });

        mLayoutStart.mItemDateParent.setOnClickListener(view13 -> {
            BottomSheetDialogFragment bottomDialog = DateDialog.newInstance(M_START);
            bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
        });

        mLayoutEnd.mItemDateParent.setOnClickListener(view14 -> {
            BottomSheetDialogFragment bottomDialog = DateDialog.newInstance(M_END);
            bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
        });

        mLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "getStartSleepDateAndTime -> " + getStartSleepDateAndTime() +
                " getEndSleepDateAndTime -> " + getEndSleepDateAndTime());
                mPresenter.onLogClick(
                        getStartSleepDateAndTime(),
                        getEndSleepDateAndTime(),
                        null);
            }
        });

    }

    private String getStartSleepDateAndTime(){
        return new StringBuilder()
                .append(mDateStart)
                .append(" ")
                .append(mLayoutStart.mItemTimeText.getText())
                .append(":00")
                .toString();
    }

    private String getEndSleepDateAndTime(){
        return new StringBuilder()
                .append(mDateEnd)
                .append(" ")
                .append(mLayoutEnd.mItemTimeText.getText())
                .append(":00")
                .toString();
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValues() {
        mLayoutStart.mItemTopText.setText(RaApp.getLabel(LangKeys.key_sleep_start));
        mLayoutStart.mItemBottomText.setText(Support.getPreviousDayDate(false));
        mDateStart = Support.GetPreviousDateForAPI(Support.DATE_FORMAT);

        mLayoutEnd.mItemTopText.setText(RaApp.getLabel(LangKeys.key_sleep_end));
        mLayoutEnd.mItemBottomText.setText(Support.getCurrentStringDate(false));
        mDateEnd = Support.GetDateTimeForAPI(Support.DATE_FORMAT);

        mTitle.setText(RaApp.getLabel(LangKeys.key_log_sleep));
        mLogText.setText(RaApp.getLabel(LangKeys.key_log));

        mLayoutStart.mItemTimeText.setText(RaApp.getBase().getSleepStartTime());
        mLayoutEnd.mItemTimeText.setText(RaApp.getBase().getSleepEndTime());
    }

    @Override
    public void setStartSleep(String startSleep) {
        mLayoutStart.mItemBottomText.setText(startSleep);
    }

    @Override
    public void setEndSleep(String endSleep) {
        mLayoutEnd.mItemBottomText.setText(endSleep);
    }

    @Override
    public void setStartSleepTime(String startSleepTime) {
        mLayoutStart.mItemTimeText.setText(startSleepTime);
    }

    @Override
    public void setEndSleepTime(String endSleepTime) {
        mLayoutEnd.mItemTimeText.setText(endSleepTime);
    }

    @Override
    public void DateStartSet(String date) {
        Log.e(TAG, "DateSet -> " + date);
        mDateStart = date;
        setStartSleep(Support.getStringDateByDate(date, Support.DATE_FORMAT, false));
    }

    @Override
    public void DateEndSet(String date) {
        Log.e(TAG, "DateSet -> " + date);
        mDateEnd = date;
        setEndSleep(Support.getStringDateByDate(date, Support.DATE_FORMAT, false));
    }

    @Override
    public void DateCancel() {

    }

    @Override
    public void setAdapterData(List<SleepItem> list) {
        if (list != null && list.size() > 0) {//
            Utility.setCollapseScroll(mToolbarLayout);
            mEmptyList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            SleepLogAdapter adapter = new SleepLogAdapter(getActivity(),
                    list,
                    new SleepLogAdapter.onClick() {
                        @Override
                        public void onClick(int position) {
                        }

                        @Override
                        public void onClickId(int position, String id) {
                        }
                    });
            mRecyclerView.setAdapter(adapter);
        } else {
            Utility.setSnagScroll(mToolbarLayout);
            mEmptyList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void addAdapterData(List<SleepItem> list) {
        if (mRecyclerView.getAdapter() != null)
            ((SleepLogAdapter) mRecyclerView.getAdapter()).addData(list);
    }

    @Override
    public void TimeStartSet(String time) {
        setStartSleepTime(time);
    }

    @Override
    public void TimeEndSet(String time) {
        setEndSleepTime(time);
    }

    @Override
    public void TimeCancel() {

    }

    @BindView(R.id.colapsing_title)
    CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.log_sleep_start)
    View mSleepStart;

    @BindView(R.id.log_sleep_end)
    View mSleepEnd;

    @BindView(R.id.log_button)
    FrameLayout mLogButton;

    @BindView(R.id.log_text)
    TextView mLogText;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_list)
    EmptyListView mEmptyList;

    static class IncludedLayout {

        @BindView( R.id.item_date_parent )
        LinearLayout mItemDateParent;

        @BindView( R.id.item_icon )
        ImageView mItemIcon;

        @BindView( R.id.item_top_text )
        TextView mItemTopText;

        @BindView( R.id.item_bottom_text )
        TextView mItemBottomText;

        @BindView( R.id.item_time_text )
        TextView mItemTimeText;
    }

}
