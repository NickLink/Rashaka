package com.rashaka.fragments.main.plus.drink;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
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
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.fragments.main.plus.drink.dialog.AlarmItem;
import com.rashaka.fragments.main.plus.drink.dialog.AlarmItemResult;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.CustomLayoutManager;
import com.rashaka.utils.helpers.views.swipe.SwipeHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_plus_drink)
public class DrinkAlarmFragment extends BaseFragment implements DrinkAlarmView, AlarmItemResult {

    private static final String TAG = DrinkAlarmFragment.class.getSimpleName();
    private MainRouter myRouter;
    private DrinkAlarmPresenter mPresenter;
    //private DrinkAlarmModel model;
    private List<DrinkAlarmItem> list;
    private int editedItemPosition = -1;
    private SwipeHelper swipeHelper;
    //private boolean removed, added;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new DrinkAlarmPresenter(myRouter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //model = ViewModelProviders.of(getActivity()).get(DrinkAlarmModel.class);

        list = mPresenter.loadAlarmList();

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

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.hasFixedSize();

        mRecyclerView.setAdapter(new DrinkAlarmAdapter(getActivity(), list, new DrinkAlarmAdapter.AlarmClick() {
            @Override
            public void editClick(int pos) {
                editedItemPosition = pos;
                BottomSheetDialogFragment bottomDialog = AlarmItem.newInstance(list.get(pos));
                bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
            }

            @Override
            public void switchClick(int pos, boolean b) {
                Log.e(TAG, "Switched to " + b + " on position " + pos);
                list.get(pos).setEnabled(b);
                mPresenter.setAlarm(getActivity(), list.get(pos));
                mPresenter.saveAlarmListChanges(list);
            }
        }));

        swipeHelper = new SwipeHelper(getActivity(), mRecyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        RaApp.getLabel(LangKeys.key_delete),
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                Log.e(TAG, "onDelete position -> " + pos);
                                clearSwiped(pos);
                            }
                        }
                ));
            }
        };

        mButtonAdd.setOnClickListener(view1 -> {
            DrinkAlarmItem item = new DrinkAlarmItem();
            list.add(item);
            editedItemPosition = list.size() - 1;
            BottomSheetDialogFragment bottomDialog = AlarmItem.newInstance(item);
            bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
        });
    }


    private void clearSwiped(int pos) {
        Log.e(TAG, "clearSwiped ======= " + pos);
        DrinkAlarmItem item = list.get(pos);
        item.setEnabled(false);
        mPresenter.setAlarm(getActivity(), item);

        list.remove(pos);
        swipeHelper.clearSwipe();
        mRecyclerView.getAdapter().notifyItemRemoved(pos);
        Log.e(TAG, "saveAlarmListChanges");
        mPresenter.saveAlarmListChanges(list);
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mTitle.setText(RaApp.getLabel(LangKeys.key_drink_alarm));
        mButtonAddText.setText(RaApp.getLabel(LangKeys.key_add_new));
    }

    @Override
    public void alarmSaved(Bundle bundle) {
        DrinkAlarmItem item = bundle.getParcelable(AlarmItem.KEY);
        list.set(editedItemPosition, item);
        if(item.isEnabled()){
            //TODO Re-Set alarm in case of it was changed!
            item.setEnabled(false);
            mPresenter.setAlarm(getActivity(), item);
            item.setEnabled(true);
            mPresenter.setAlarm(getActivity(), item);
        }
        //TODO Persist changes
        mPresenter.saveAlarmListChanges(list);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        //added = true;
    }

    @Override
    public void alarmCancel() {
        //added = false;
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.add_new_alarm)
    FrameLayout mButtonAdd;


    @BindView(R.id.add_new_text)
    TextView mButtonAddText;
}
