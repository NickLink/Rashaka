package com.rashaka.fragments.settings.profile.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.domain.profile.UserProfile;

/**
 * Created by User on 30.08.2017.
 */

public class GenderDialog extends BottomSheetDialogFragment {

    private static final String TAG = GenderDialog.class.getSimpleName();
    private SharedUserModel model;
    private int g_sex = -1;

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
        View contentView = View.inflate(getContext(), R.layout.dlg_prof_gender, null);
        dialog.setContentView(contentView);

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        TextView title = dialog.findViewById(R.id.title);
        RadioGroup gender_radiogroup = dialog.findViewById(R.id.gender_radiogroup);
        RadioButton gender_radio_male = dialog.findViewById(R.id.gender_radio_male);
        RadioButton gender_radio_female = dialog.findViewById(R.id.gender_radio_female);
        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
        TextView save_button = dialog.findViewById(R.id.save_button);

        Log.e(TAG, "Gender -> " + model.getSelected().getValue().toString());

        if(model.getSelected().getValue().getSex() != null) {
            if (model.getSelected().getValue().getSex().equals("0")) {
                gender_radiogroup.check(R.id.gender_radio_male);
            } else if (model.getSelected().getValue().getSex().equals("1")) {
                gender_radiogroup.check(R.id.gender_radio_female);
            }
        }

        gender_radiogroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.gender_radio_male:
                    g_sex = 0;
                    break;
                case R.id.gender_radio_female:
                    g_sex = 1;
                    break;
            }
        });

        cancel_button.setOnClickListener(view -> dismiss());
        save_button.setOnClickListener(view -> doSave(g_sex));

        title.setText(RaApp.getLabel("key_select_gender"));
        gender_radio_male.setText(RaApp.getLabel("key_male"));
        gender_radio_female.setText(RaApp.getLabel("key_female"));
        cancel_button.setText(RaApp.getLabel("key_cancel"));
        save_button.setText(RaApp.getLabel("key_save"));

    }

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(int sex) {
        UserProfile data = model.getSelected().getValue();
        data.setSex(String.valueOf(sex));
        model.select(data);
        dismiss();
    }

}
