package rashakacom.rashaka.fragments.main.plus.drink.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;
import rashakacom.rashaka.utils.database.DrinkAlarmModel;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.picker.MyNumberPicker;
import rashakacom.rashaka.utils.helpers.views.picker.MyStringPicker;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_plus_drink_edit)
public class AlarmEditFragment extends BaseFragment implements AlarmEditView, View.OnClickListener {

    private static final String TAG = AlarmEditFragment.class.getSimpleName();
    private MainRouter myRouter;
    private AlarmEditPresenter mPresenter;
    private DrinkAlarmModel model;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new AlarmEditPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        model = ViewModelProviders.of(getActivity()).get(DrinkAlarmModel.class);
        mPresenter.setInitialValues(model.getSelected().getValue());
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

        mHoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "mHoursPicker -> onValueChange " + i + " i1 " + i1);
                mPresenter.setHours(i1);
            }
        });

        mMinutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "mMinutesPicker -> onValueChange " + i + " i1 " + i1);
                mPresenter.setMinutes(i1);
            }
        });

        mAmPicker.setOnValueChangedListener((numberPicker, i, i1) -> {
            Log.e(TAG, "mAmPicker -> onValueChange " + i + " i1 " + i1);
            switch (i1){
                case 0:
                    mPresenter.setAm(Calendar.AM);
                    break;
                case 1:
                    mPresenter.setAm(Calendar.PM);
                    break;
            }
        });

        mItemDayMon.setOnClickListener(this);
        mItemDayTue.setOnClickListener(this);
        mItemDayWed.setOnClickListener(this);
        mItemDayThu.setOnClickListener(this);
        mItemDayFri.setOnClickListener(this);
        mItemDaySat.setOnClickListener(this);
        mItemDaySun.setOnClickListener(this);

        String[] amString = {"am", "pm"};
        mAmPicker.setDisplayedValues(amString);

        mButtonCancel.setOnClickListener(view1 -> mPresenter.onCancelClick());

        mButtonSave.setOnClickListener(view12 -> mPresenter.onSaveClick());
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mTitle.setText(RaApp.getLabel("key_drink_alarm"));
        mHoursPicker.setValue(model.getSelected().getValue().getHours());
        mMinutesPicker.setValue(model.getSelected().getValue().getMinutes());
        mAmPicker.setValue(model.getSelected().getValue().getAm());
        setDayChecked(mItemDayMon, model.getSelected().getValue().isEnMonday());
        setDayChecked(mItemDayTue, model.getSelected().getValue().isEnTuesday());
        setDayChecked(mItemDayWed, model.getSelected().getValue().isEnWednesday());
        setDayChecked(mItemDayThu, model.getSelected().getValue().isEnThursday());
        setDayChecked(mItemDayFri, model.getSelected().getValue().isEnFriday());
        setDayChecked(mItemDaySat, model.getSelected().getValue().isEnSaturday());
        setDayChecked(mItemDaySun, model.getSelected().getValue().isEnSunday());


    }

    @Override
    public void setDayChecked(TextView view, boolean enable) {
        if (enable){
            view.setBackgroundResource(R.drawable.time_circle_green);
        } else {
            view.setBackgroundResource(0);
        }
    }

    @Override
    public void setDateValues(String dayOfWeek, String dateOfDay) {
        mCurrentDay.setText(dayOfWeek);
        mCurrentDate.setText(dateOfDay);
    }

    @Override
    public void onClick(View view) {
        mPresenter.onDayClick((TextView)view);
    }

    @Override
    public void doSave(DrinkAlarmItem data) {
        model.select(data);
        mFragmentNavigation.popFragment();
    }

    @Override
    public void onCancel(DrinkAlarmItem data) {
        //data = null;
        model.select(null);
        mFragmentNavigation.popFragment();
    }


    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.current_day)
    TextView mCurrentDay;

    @BindView(R.id.current_date)
    TextView mCurrentDate;

    @BindView(R.id.picker_hours)
    MyNumberPicker mHoursPicker;

    @BindView(R.id.picker_minutes)
    MyNumberPicker mMinutesPicker;

    @BindView(R.id.picker_am)
    MyStringPicker mAmPicker;

    @BindView(R.id.item_day_Mon)
    TextView mItemDayMon;

    @BindView(R.id.item_day_Tue)
    TextView mItemDayTue;

    @BindView(R.id.item_day_Wed)
    TextView mItemDayWed;

    @BindView(R.id.item_day_Thu)
    TextView mItemDayThu;

    @BindView(R.id.item_day_Fri)
    TextView mItemDayFri;

    @BindView(R.id.item_day_Sat)
    TextView mItemDaySat;

    @BindView(R.id.item_day_Sun)
    TextView mItemDaySun;

    @BindView(R.id.save_button)
    TextView mButtonSave;

    @BindView(R.id.cancel_button)
    TextView mButtonCancel;

}
