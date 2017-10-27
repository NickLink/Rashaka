package rashakacom.rashaka.fragments.main.plus.drink;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.plus.drink.edit.AlarmEditFragment;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.database.DrinkAlarmModel;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.CustomLayoutManager;
import rashakacom.rashaka.utils.helpers.views.swipe.SwipeHelper;

import static rashakacom.rashaka.utils.Consts.ALARM_START_VALUE;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_plus_drink)
public class DrinkAlarmFragment extends BaseFragment implements DrinkAlarmView {

    private static final String TAG = DrinkAlarmFragment.class.getSimpleName();
    private MainRouter myRouter;
    private DrinkAlarmPresenter mPresenter;
    private DrinkAlarmModel model;
    private List<DrinkAlarmItem> list;
    private int editedItemPosition = -1;
    private SwipeHelper swipeHelper;
    private boolean removed;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new DrinkAlarmPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        model = ViewModelProviders.of(getActivity()).get(DrinkAlarmModel.class);

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
                model.select(list.get(pos));
                mFragmentNavigation.pushFragment(new AlarmEditFragment());
            }

            @Override
            public void switchClick(int pos, boolean b) {
                Log.e(TAG, "Switched to " + b + " on position " + pos);
                list.get(pos).setEnabled(b);
                mPresenter.setAlarm(getActivity(), list.get(pos));
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

        model.getSelected().observe(this, o -> {
            Log.e(TAG, "Observed Changes!!!");
            if(o == null && !removed){
                Log.e(TAG, "Observed o == null list size = " + list.size());
                list.remove(editedItemPosition);
                removed = true;
            } else {
                if (editedItemPosition != -1 && !removed) {
                    list.set(editedItemPosition, o);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                    Log.e(TAG, "saveAlarmListChanges");
                    mPresenter.saveAlarmListChanges(list);
                }
            }
        });

        mButtonAdd.setOnClickListener(view1 -> {
            removed = false;
            DrinkAlarmItem item = new DrinkAlarmItem();
            item.setId(ALARM_START_VALUE + list.size());
            list.add(item);
            editedItemPosition = list.size() - 1;
            Log.e(TAG, "LIST SIZE NOW -> " + list.size());
            model.select(list.get(editedItemPosition));
            mFragmentNavigation.pushFragment(new AlarmEditFragment());
        });
    }


    private void clearSwiped(int pos){
        Log.e(TAG, "clearSwiped ======= " + pos);
        DrinkAlarmItem item = list.get(pos);
        item.setEnabled(false);
        mPresenter.setAlarm(getActivity(), item);

        list.remove(pos);
        //mRecyclerView.getAdapter().notifyDataSetChanged();
        swipeHelper.clearSwipe();
        mRecyclerView.getAdapter().notifyItemRemoved(pos);
        Log.e(TAG, "saveAlarmListChanges");
        mPresenter.saveAlarmListChanges(list);

        //mRecyclerView.getRecycledViewPool().clear();
        //mRecyclerView.getAdapter().notifyDataSetChanged();
        //swipeHelper.clearSwipe();
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


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.add_new_alarm)
    FrameLayout mButtonAdd;


    @BindView(R.id.add_new_text)
    TextView mButtonAddText;
}
