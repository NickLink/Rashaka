package com.rashaka.fragments.settings.profile.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.utils.helpers.views.picker.My100NumberPicker;
import com.rashaka.utils.helpers.views.picker.MyNumberPicker;

/**
 * Created by User on 30.08.2017.
 */

public class StepsGoalDialog extends BottomSheetDialogFragment {

    private static final String TAG = StepsGoalDialog.class.getSimpleName();
    private SharedUserModel model;

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
        View contentView = View.inflate(getContext(), R.layout.dlg_prof_steps_goal, null);
        dialog.setContentView(contentView);

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        TextView title = dialog.findViewById(R.id.title);
        MyNumberPicker wMain = dialog.findViewById(R.id.picker_main);
        My100NumberPicker wExtra = dialog.findViewById(R.id.picker_extra);
        TextView value_def = dialog.findViewById(R.id.value_def);
        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
        TextView save_button = dialog.findViewById(R.id.save_button);

        int tValue = 0, hValue = 0;
        try {
            int steps = Integer.parseInt(model.getSelected().getValue().getStepsGoal());
            tValue = steps / 1000;
            hValue = (steps % 1000) / 100;
        } catch (Exception e){
            Log.e(TAG, "Exception -> " + e.getLocalizedMessage());
        }
        wMain.setValue(tValue);
        wExtra.setValue(hValue);

        cancel_button.setOnClickListener(view -> dismiss());
        save_button.setOnClickListener(view -> {
            doSave(wMain.getValue() == 0 ? "" : String.valueOf(wMain.getValue()),
                    wExtra.numbers[wExtra.getValue()]);
        });

        title.setText(RaApp.getLabel("key_set_steps_goal"));
        value_def.setText(RaApp.getLabel("key_steps"));
        cancel_button.setText(RaApp.getLabel("key_cancel"));
        save_button.setText(RaApp.getLabel("key_save"));

    }

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(String main, String extra) {
        Log.e(TAG, " Steps goal will be " + main + "" + extra);
        UserProfile data = model.getSelected().getValue();
        data.setStepsGoal(main + extra);
        model.select(data);
        dismiss();
    }

}
