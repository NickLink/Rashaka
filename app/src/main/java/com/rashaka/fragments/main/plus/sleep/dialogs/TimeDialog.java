package com.rashaka.fragments.main.plus.sleep.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.fragments.main.plus.sleep.SleepLogFragment;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.utils.helpers.views.picker.MyNumberPicker;
import com.rashaka.utils.helpers.views.picker.MyStringPicker;

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
    private int to;

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
        String[] amString = {"am", "pm"};
        mAmPicker.setDisplayedValues(amString);

        mButtonCancel.setOnClickListener(view1 -> onCancelClick());

        mButtonSave.setOnClickListener(view12 -> {
            onSaveClick();
        });

//        TextView title = dialog.findViewById(R.id.title);
//        MyNumberPicker hMain = dialog.findViewById(R.id.height_picker_main);
//        MyNumberPicker hExtra = dialog.findViewById(R.id.height_picker_extra);
//        TextView height_def = dialog.findViewById(R.id.height_def);
//        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
//        TextView save_button = dialog.findViewById(R.id.save_button);
//
//        cancel_button.setOnClickListener(view -> dismiss());
//        save_button.setOnClickListener(view -> doSave(hMain.getValue(), hExtra.getValue()));
//
//        title.setText(RaApp.getLabel("key_select_height"));
//        height_def.setText(RaApp.getLabel("key_cm"));
//        cancel_button.setText(RaApp.getLabel("key_cancel"));
//        save_button.setText(RaApp.getLabel("key_save"));

    }

    private void onSaveClick() {
        mTime = String.format("%02d", mHoursPicker.getValue() + mAmPicker.getValue() * 12) + ":"
                + String.format("%02d", mMinutesPicker.getValue());
        Log.e(TAG, "OnSaveClick -> " + mTime);
        if(to == SleepLogFragment.M_START){
            myInterface.TimeStartSet(mTime);
        } else {
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
