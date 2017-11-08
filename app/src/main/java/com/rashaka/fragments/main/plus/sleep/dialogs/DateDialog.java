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
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.rashaka.R;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.fragments.main.plus.sleep.SleepLogFragment;
import com.rashaka.utils.database.SharedUserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 30.08.2017.
 */

public class DateDialog extends BottomSheetDialogFragment {

    private static final String TAG = DateDialog.class.getSimpleName();
    private static final String KEY = "KEY";
    private SharedUserModel model;
    private DateResult myInterface;
    private String mDate;
    private int to;

    public static DateDialog newInstance(int start){
        DateDialog dialog = new DateDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, start);
        dialog.setArguments(bundle);
        return dialog;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myInterface = (DateResult) getParentFragment();
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
        View contentView = View.inflate(getContext(), R.layout.dlg_sleep_date, null);
        dialog.setContentView(contentView);

        ButterKnife.bind(this, contentView);

        if(getArguments() != null && getArguments().containsKey(KEY)){
            to = getArguments().getInt(KEY);
        }

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

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

        mCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.e(TAG, "Date selected day = " + date.getDay() + " month = " + date.getMonth() + " year = " + date.getYear());
                mDate = date.getYear() + "-" + (date.getMonth()+ 1) + "-" + date.getDay() + " 00:00:00";
                mButtonSave.setEnabled(true);
            }
        });

        mButtonCancel.setOnClickListener(view1 -> onCancelClick());

        mButtonSave.setOnClickListener(view12 -> {
            onSaveClick();
        });

        mButtonSave.setEnabled(false);

    }

    private void onSaveClick() {
        if(to == SleepLogFragment.M_START){
            myInterface.DateStartSet(mDate);
        } else {
            myInterface.DateEndSet(mDate);
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

    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendar;

    @BindView(R.id.save_button)
    TextView mButtonSave;

    @BindView(R.id.cancel_button)
    TextView mButtonCancel;

}
