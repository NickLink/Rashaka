package com.rashaka.fragments.main.plus.sleep;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.rashaka.fragments.BaseFragment;
import com.rashaka.fragments.main.plus.sleep.dialogs.DateDialog;
import com.rashaka.fragments.main.plus.sleep.dialogs.DateResult;
import com.rashaka.fragments.main.plus.sleep.dialogs.TimeDialog;
import com.rashaka.fragments.main.plus.sleep.dialogs.TimeResult;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;

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
    public static int M_START = 1, M_END = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new SleepLogPresenter();
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

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValues() {
        mLayoutStart.mItemTopText.setText(RaApp.getLabel(LangKeys.key_sleep_start));
        mLayoutStart.mItemBottomText.setText(Support.getCurrentStringDate(false));

        mLayoutEnd.mItemTopText.setText(RaApp.getLabel(LangKeys.key_sleep_end));
        mLayoutEnd.mItemBottomText.setText(Support.getCurrentStringDate(false));

        mTitle.setText(RaApp.getLabel(LangKeys.key_log_sleep));
        mLogText.setText(RaApp.getLabel(LangKeys.key_log));
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

    @BindView(R.id.log_sleep_start)
    View mSleepStart;

    @BindView(R.id.log_sleep_end)
    View mSleepEnd;

    @BindView(R.id.log_button)
    FrameLayout mLogButton;

    @BindView(R.id.log_text)
    TextView mLogText;

    @BindView(R.id.title)
    TextView mTitle;

    @Override
    public void DateStartSet(String date) {
        Log.e(TAG, "DateSet -> " + date);
        setStartSleep(Support.getStringDateByDate(date, false));
    }

    @Override
    public void DateEndSet(String date) {
        Log.e(TAG, "DateSet -> " + date);
        setEndSleep(Support.getStringDateByDate(date, false));
    }

    @Override
    public void DateCancel() {

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
