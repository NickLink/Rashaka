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

import com.rashaka.RaApp;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.utils.helpers.views.picker.MyNumberPicker;

import com.rashaka.R;

/**
 * Created by User on 30.08.2017.
 */

public class WeightDialog extends BottomSheetDialogFragment {

    private static final String TAG = WeightDialog.class.getSimpleName();
    private SharedUserModel model;

    private int height_main = -1;
    private int height_extra = -1;

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
        View contentView = View.inflate(getContext(), R.layout.dlg_prof_weight, null);
        dialog.setContentView(contentView);

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        TextView title = dialog.findViewById(R.id.title);
        MyNumberPicker wMain = dialog.findViewById(R.id.weight_picker_main);
        MyNumberPicker wExtra = dialog.findViewById(R.id.weight_picker_extra);
        TextView weight_def = dialog.findViewById(R.id.weight_def);
        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
        TextView save_button = dialog.findViewById(R.id.save_button);

        int fWeight = 75, dWeight = 0;
        try {
            double height = Double.parseDouble(model.getSelected().getValue().getWeight());
            fWeight = (int) height;
            dWeight = (int)((height - fWeight) * 10);
        } catch (Exception e){
            Log.e(TAG, "Exception -> " + e.getLocalizedMessage());
        }
        wMain.setValue(fWeight);
        wExtra.setValue(dWeight);

        cancel_button.setOnClickListener(view -> dismiss());
        save_button.setOnClickListener(view -> doSave(wMain.getValue(), wExtra.getValue()));

        title.setText(RaApp.getLabel("key_select_weight"));
        weight_def.setText(RaApp.getLabel("key_kg"));
        cancel_button.setText(RaApp.getLabel("key_cancel"));
        save_button.setText(RaApp.getLabel("key_save"));

    }

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(int main, int extra) {
        UserProfile data = model.getSelected().getValue();
        data.setWeight(main + "." + extra);
        model.select(data);
        dismiss();
    }

}
