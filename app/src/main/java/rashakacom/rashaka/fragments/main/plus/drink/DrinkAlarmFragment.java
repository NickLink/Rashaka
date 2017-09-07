package rashakacom.rashaka.fragments.main.plus.drink;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.plus.drink.edit.AlarmEditFragment;
import rashakacom.rashaka.utils.database.DrinkAlarmModel;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.CustomLayoutManager;
import rashakacom.rashaka.utils.helpers.views.swipe.SwipeHelper;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_plus_drink)
public class DrinkAlarmFragment extends BaseFragment implements DrinkAlarmView {

    private MainRouter myRouter;
    private DrinkAlarmPresenter mPresenter;
    private DrinkAlarmModel model;
    private List<DrinkAlarmItem> list;
    private int editedItemPosition = -1;
    private SwipeHelper swipeHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new DrinkAlarmPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        model = ViewModelProviders.of(getActivity()).get(DrinkAlarmModel.class);

        list = new ArrayList<>();
        DrinkAlarmItem item0 = new DrinkAlarmItem(true, 12, 12, 1, true, true, true, true, true, true, true);
        DrinkAlarmItem item1 = new DrinkAlarmItem(true, 6, 45, 1, false, false, false, true, true, true, true);
        DrinkAlarmItem item2 = new DrinkAlarmItem(false, 9, 00, 1, true, true, true, false, false, false, false);
        DrinkAlarmItem item3 = new DrinkAlarmItem(false, 6, 45, 1, false, false, false, true, true, true, true);
        list.add(item0);
        list.add(item1);
        list.add(item2);

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//            getActivity().setTitle("BARABAKA");
//
//            menu.clear();
//            //inflater.inflate(R.menu.shadow, menu);
//
//            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setHomeButtonEnabled(false);
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
//            }
//    }

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
                Log.e("TAG", "Switched to " + b + " on position " + pos);
                list.get(pos).setEnabled(b);
            }
        }));

        swipeHelper = new SwipeHelper(getActivity(), mRecyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                Log.e("TAG", "onDelete position -> " + pos);
                                clearSwiped(pos);
                            }
                        }
                ));
            }
        };

        model.getSelected().observe(this, o -> {
            Log.e("TAG", "Observed Changes!!!");
            if (editedItemPosition != -1) {
                list.set(editedItemPosition, o);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add(new DrinkAlarmItem());
                editedItemPosition = list.size() - 1;
                Log.e("TAG", "LIST SIZE NOW -> " + list.size());
                model.select(list.get(editedItemPosition));
                mFragmentNavigation.pushFragment(new AlarmEditFragment());
            }
        });
    }


    private void clearSwiped(int pos){
        Log.e("TAG", "clearSwiped ======= " + pos);
        list.remove(pos);
        //mRecyclerView.getAdapter().notifyDataSetChanged();
        swipeHelper.clearSwipe();
        mRecyclerView.getAdapter().notifyItemRemoved(pos);


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
        mTitle.setText(RaApp.getLabel("key_drink_alarm"));
    }


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.add_new_alarm)
    FrameLayout mButtonAdd;

}
