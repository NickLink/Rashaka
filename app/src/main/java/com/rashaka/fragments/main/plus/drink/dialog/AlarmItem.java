package com.rashaka.fragments.main.plus.drink.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.main.plus.drink.DrinkAlarmItem;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.dialogs.SimpleInitiallyExpandedBottomSheetFragment;
import com.rashaka.utils.helpers.views.picker.MyNumberPicker;
import com.rashaka.utils.helpers.views.picker.MyStringPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 06.11.2017.
 */

public class AlarmItem extends SimpleInitiallyExpandedBottomSheetFragment implements View.OnClickListener {
    private static final String TAG = AlarmItem.class.getSimpleName();
    private AlarmItemResult myInterface;
    //private AlarmItemPresenter mPresenter;
    //private DrinkAlarmModel model;
    private DrinkAlarmItem mDataItem;
    public static final String KEY = "KEY";

    public static AlarmItem newInstance(Parcelable item){
        AlarmItem dialog = new AlarmItem();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, item);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mPresenter = new AlarmItemPresenter((MainActivity)context);
        myInterface = (AlarmItemResult) getParentFragment();
//        model = ViewModelProviders.of(getActivity()).get(DrinkAlarmModel.class);
//        mDataItem = model.getSelected().getValue();
        //mPresenter.setInitialValues(model.getSelected().getValue());
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fr_plus_drink_edit, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);

        if(getArguments() != null && getArguments().containsKey(KEY)){
            //TODO Edit mode
            mDataItem = getArguments().getParcelable(KEY);
        }

        mHoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "mHoursPicker -> onValueChange " + i + " i1 " + i1);
                //mPresenter.setHours(i1);
                mDataItem.setHours(i1);
            }
        });

        mMinutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "mMinutesPicker -> onValueChange " + i + " i1 " + i1);
                //mPresenter.setMinutes(i1);
                mDataItem.setMinutes(i1);
            }
        });

        mAmPicker.setOnValueChangedListener((numberPicker, i, i1) -> {
            Log.e(TAG, "mAmPicker -> onValueChange " + i + " i1 " + i1);
            switch (i1){
                case 0:
                    //mPresenter.setAm(Calendar.AM);
                    mDataItem.setAm(Calendar.AM);
                    break;
                case 1:
                    //mPresenter.setAm(Calendar.PM);
                    mDataItem.setAm(Calendar.PM);
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

        String[] amString = {RaApp.getLabel(LangKeys.key_am), RaApp.getLabel(LangKeys.key_pm)};
        mAmPicker.setDisplayedValues(amString);

        mButtonCancel.setOnClickListener(view1 -> {
            //myInterface.alarmCancel();
            //mPresenter.onCancelClick();
            onCancel(mDataItem);
        });

        mButtonSave.setOnClickListener(view12 -> {
            //myInterface.alarmSaved();
            //mPresenter.onSaveClick();
            doSave(mDataItem);
        });

        setViewsValues();
        setDateValues(getDayOfWeek(), getDateOfDay());
    }

    public String getDayOfWeek() {
        int dayOfWeekPos = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Log.e(TAG, "getDayOfWeek - > dayOfWeekPos = " + dayOfWeekPos);
        switch (dayOfWeekPos){
            case Calendar.MONDAY:
                return RaApp.getLabel(LangKeys.key_mon);
            case Calendar.TUESDAY:
                return RaApp.getLabel(LangKeys.key_tues);
            case Calendar.WEDNESDAY:
                return RaApp.getLabel(LangKeys.key_weds);
            case Calendar.THURSDAY:
                return RaApp.getLabel(LangKeys.key_thurs);
            case Calendar.FRIDAY:
                return RaApp.getLabel(LangKeys.key_fri);
            case Calendar.SATURDAY:
                return RaApp.getLabel(LangKeys.key_sat);
            case Calendar.SUNDAY:
                return RaApp.getLabel(LangKeys.key_sun);
            default:
                return "";
        }
    }

    public String getDateOfDay() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(Calendar.getInstance().getTime());
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

    @Override
    public void onClick(View view) {
        onDayClick((TextView)view);
    }

    public void setViewsValues() {
        mTitle.setText(RaApp.getLabel(LangKeys.key_drink_alarm));
        mHoursPicker.setValue(mDataItem.getHours());
        mMinutesPicker.setValue(mDataItem.getMinutes());
        mAmPicker.setValue(mDataItem.getAm());
        setDayChecked(mItemDayMon, mDataItem.isEnMonday());
        setDayChecked(mItemDayTue, mDataItem.isEnTuesday());
        setDayChecked(mItemDayWed, mDataItem.isEnWednesday());
        setDayChecked(mItemDayThu, mDataItem.isEnThursday());
        setDayChecked(mItemDayFri, mDataItem.isEnFriday());
        setDayChecked(mItemDaySat, mDataItem.isEnSaturday());
        setDayChecked(mItemDaySun, mDataItem.isEnSunday());

        mButtonSave.setText(RaApp.getLabel(LangKeys.key_save));
        mButtonCancel.setText(RaApp.getLabel(LangKeys.key_cancel));
    }

    public void setDayChecked(TextView view, boolean enable) {
        if (enable){
            view.setBackgroundResource(R.drawable.time_circle_green);
        } else {
            view.setBackgroundResource(0);
        }
    }

    public void setDateValues(String dayOfWeek, String dateOfDay) {
        mCurrentDay.setText(dayOfWeek);
        mCurrentDate.setText(dateOfDay);
    }

    public void doSave(DrinkAlarmItem data) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, data);
        myInterface.alarmSaved(bundle);
        //model.select(data);
        dismiss();
    }

    public void onCancel(DrinkAlarmItem data) {
        myInterface.alarmCancel();
        //model.select(data);
        dismiss();
    }

    public void onDayClick(TextView view) {
        switch (view.getId()){
            case R.id.item_day_Mon:
                mDataItem.setEnMonday(!mDataItem.isEnMonday());
                setDayChecked(view, mDataItem.isEnMonday());
                break;
            case R.id.item_day_Tue:
                mDataItem.setEnTuesday(!mDataItem.isEnTuesday());
                setDayChecked(view, mDataItem.isEnTuesday());
                break;
            case R.id.item_day_Wed:
                mDataItem.setEnWednesday(!mDataItem.isEnWednesday());
                setDayChecked(view, mDataItem.isEnWednesday());
                break;
            case R.id.item_day_Thu:
                mDataItem.setEnThursday(!mDataItem.isEnThursday());
                setDayChecked(view, mDataItem.isEnThursday());
                break;
            case R.id.item_day_Fri:
                mDataItem.setEnFriday(!mDataItem.isEnFriday());
                setDayChecked(view, mDataItem.isEnFriday());
                break;
            case R.id.item_day_Sat:
                mDataItem.setEnSaturday(!mDataItem.isEnSaturday());
                setDayChecked(view, mDataItem.isEnSaturday());
                break;
            case R.id.item_day_Sun:
                mDataItem.setEnSunday(!mDataItem.isEnSunday());
                setDayChecked(view, mDataItem.isEnSunday());
                break;
        }
    }
}
