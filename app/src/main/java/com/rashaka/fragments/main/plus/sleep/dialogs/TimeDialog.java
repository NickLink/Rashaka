package com.rashaka.fragments.main.plus.sleep.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.fragments.main.plus.sleep.SleepLogFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.utils.helpers.views.picker.MyNumberPicker;
import com.rashaka.utils.helpers.views.picker.MyStringPicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 30.08.2017.
 */

public class TimeDialog extends BottomSheetDialogFragment {

    private static final String TAG = TimeDialog.class.getSimpleName();
    private static final String KEY = "KEY";
    private SharedUserModel model;
    private TimeResult myInterface;
    private String mTime;
    private int to = -1;

    public static TimeDialog newInstance(int start){
        TimeDialog dialog = new TimeDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, start);
        dialog.setArguments(bundle);
        return dialog;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myInterface = (TimeResult) getParentFragment();
    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.dlg_sleep_time, null);
        dialog.setContentView(contentView);

        ButterKnife.bind(this, contentView);

        if(getArguments() != null && getArguments().containsKey(KEY)){
            to = getArguments().getInt(KEY);
        }

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        ((BottomSheetBehavior)behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    ((BottomSheetBehavior)behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });




        mHoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "mHoursPicker -> onValueChange " + i + " i1 " + i1);
                //mPresenter.setHours(i1);
            }
        });

        mMinutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "mMinutesPicker -> onValueChange " + i + " i1 " + i1);
                //mPresenter.setMinutes(i1);
            }
        });

        mAmPicker.setOnValueChangedListener((numberPicker, i, i1) -> {
            Log.e(TAG, "mAmPicker -> onValueChange " + i + " i1 " + i1);
            switch (i1){
                case 0:
                    //mPresenter.setAm(Calendar.AM);
                    break;
                case 1:
                    //mPresenter.setAm(Calendar.PM);
                    break;
            }
        });
        String[] amString = {RaApp.getLabel(LangKeys.key_am), RaApp.getLabel(LangKeys.key_pm)};
        mAmPicker.setDisplayedValues(amString);

        mButtonCancel.setOnClickListener(view1 -> onCancelClick());

        mButtonSave.setOnClickListener(view12 -> {
            onSaveClick();
        });

        Log.e(TAG, "TO --->>> " + to);

        if(to != -1){
            setPickersValues(to, mHoursPicker, mMinutesPicker, mAmPicker);
        }

    }

    private void setPickersValues(int to, MyNumberPicker mHoursPicker, MyNumberPicker mMinutesPicker, MyStringPicker mAmPicker) {
        String mTime = null;
        if(to == SleepLogFragment.M_START){
            mTime = RaApp.getBase().getSleepStartTime();
        } else if(to == SleepLogFragment.M_END){
            mTime = RaApp.getBase().getSleepEndTime();
        }
        if(!TextUtils.isEmpty(mTime)){
            Log.e(TAG, "TIME STRING IS - > " + mTime);

            try {
                DateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date = sdf.parse(mTime);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                if(hours > 12){
                    mHoursPicker.setValue(hours - 12);
                    mAmPicker.setValue(1);
                } else {
                    mHoursPicker.setValue(hours);
                    mAmPicker.setValue(0);
                }
                mMinutesPicker.setValue(minute);

            }catch (Exception e){
                Log.e(TAG, "Parse Exception - > " + e.getLocalizedMessage());
            }

        }

    }

    private void onSaveClick() {
        mTime = String.format("%02d", mHoursPicker.getValue() + mAmPicker.getValue() * 12) + ":"
                + String.format("%02d", mMinutesPicker.getValue());
        Log.e(TAG, "OnSaveClick -> " + mTime);
        if(to == SleepLogFragment.M_START){
            RaApp.getBase().saveSleepStartTime(mTime);
            myInterface.TimeStartSet(mTime);
        } else if(to == SleepLogFragment.M_END){
            RaApp.getBase().saveSleepEndTime(mTime);
            myInterface.TimeEndSet(mTime);
        }
        dismiss();
    }

    private void onCancelClick() {
        dismiss();
    }
    
    

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(int main, int extra) {
        UserProfile data = model.getSelected().getValue();
        data.setHight(main + "." + extra);
        model.select(data);
        dismiss();
    }

    @BindView(R.id.picker_hours)
    MyNumberPicker mHoursPicker;

    @BindView(R.id.picker_minutes)
    MyNumberPicker mMinutesPicker;

    @BindView(R.id.picker_am)
    MyStringPicker mAmPicker;

    @BindView(R.id.save_button)
    TextView mButtonSave;

    @BindView(R.id.cancel_button)
    TextView mButtonCancel;

}
