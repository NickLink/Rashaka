package rashakacom.rashaka.fragments.settings.profile.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.database.SharedViewModel;
import rashakacom.rashaka.utils.helpers.views.picker.MyNumberPicker;
import rashakacom.rashaka.utils.rest.models.UserData;

/**
 * Created by User on 30.08.2017.
 */

public class HeightDialog extends BottomSheetDialogFragment {

    private SharedViewModel model;

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
        View contentView = View.inflate(getContext(), R.layout.dlg_prof_height, null);
        dialog.setContentView(contentView);

        model = ViewModelProviders.of(getParentFragment()).get(SharedViewModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        TextView title = dialog.findViewById(R.id.title);
        MyNumberPicker hMain = dialog.findViewById(R.id.height_picker_main);
        MyNumberPicker hExtra = dialog.findViewById(R.id.height_picker_extra);
        TextView height_def = dialog.findViewById(R.id.height_def);
        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
        TextView save_button = dialog.findViewById(R.id.save_button);

        cancel_button.setOnClickListener(view -> dismiss());
        save_button.setOnClickListener(view -> doSave(hMain.getValue(), hExtra.getValue()));

        title.setText(RaApp.getLabel("key_select_height"));
        height_def.setText(RaApp.getLabel("key_cm"));
        cancel_button.setText(RaApp.getLabel("key_cancel"));
        save_button.setText(RaApp.getLabel("key_save"));

    }

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(int main, int extra) {
        UserData data = model.getSelected().getValue();
        data.setHight(main + "." + extra);
        model.select(data);
        dismiss();
    }

}
