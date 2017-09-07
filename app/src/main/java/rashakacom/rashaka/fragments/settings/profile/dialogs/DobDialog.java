package rashakacom.rashaka.fragments.settings.profile.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.database.SharedUserModel;
import rashakacom.rashaka.utils.rest.models.profile.UserProfile;

/**
 * Created by User on 30.08.2017.
 */

public class DobDialog extends BottomSheetDialogFragment {

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
        View contentView = View.inflate(getContext(), R.layout.dlg_prof_dob, null);
        dialog.setContentView(contentView);

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

        TextView title = dialog.findViewById(R.id.title);
        DatePicker mCalendar = dialog.findViewById(R.id.dob_calendar);
        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
        TextView save_button = dialog.findViewById(R.id.save_button);

        cancel_button.setOnClickListener(view -> dismiss());
        save_button.setOnClickListener(view -> doSave(
                mCalendar.getDayOfMonth() + "-" +
                        mCalendar.getMonth() + "-" +
                        mCalendar.getYear()));

        title.setText(RaApp.getLabel("key_date_of_birth"));
        cancel_button.setText(RaApp.getLabel("key_cancel"));
        save_button.setText(RaApp.getLabel("key_save"));

    }

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(String date) {
        UserProfile data = model.getSelected().getValue();
        data.setBirthday(date); //DateFormat.format("dd-MM-yyyy", new Date(date)).toString()
        model.select(data);
        dismiss();
    }

}
