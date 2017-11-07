package com.rashaka.fragments.settings.profile.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.utils.dialogs.SimpleInitiallyExpandedBottomSheetFragment;

import java.util.Calendar;

import static com.rashaka.utils.Support.DATE_FORMAT;

/**
 * Created by User on 30.08.2017.
 */

public class DobDialog extends SimpleInitiallyExpandedBottomSheetFragment { //BottomSheetDialogFragment

    private static final String TAG = DobDialog.class.getSimpleName();
    private SharedUserModel model;

//    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
//
//        @Override
//        public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss();
//            }
//        }
//
//        @Override
//        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//        }
//    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.dlg_prof_dob, null);
        dialog.setContentView(contentView);

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
//        CoordinatorLayout.Behavior behavior = params.getBehavior();
//        ((BottomSheetBehavior)behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
//
//        ((BottomSheetBehavior)behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
//                    ((BottomSheetBehavior)behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            }
//        });

//        LinearLayout parent_layout = dialog.findViewById(R.id.parent_layout);
//        parent_layout.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;

        TextView title = dialog.findViewById(R.id.title);
        DatePicker mCalendar = dialog.findViewById(R.id.dob_calendar);
        TextView cancel_button = dialog.findViewById(R.id.cancel_button);
        TextView save_button = dialog.findViewById(R.id.save_button);

        try {
            String date = model.getSelected().getValue().getBirthday();
            Calendar c = Calendar.getInstance();
            Log.e(TAG, "Date from DOB -> " + date);
            c.setTime(Support.getDateFromString(date, DATE_FORMAT));
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DATE);
            Log.e(TAG, "Set cal- > " + year + " " + month + " " + day);
            mCalendar.updateDate(year, month, day);
        } catch (Exception e){
            Log.e(TAG, "Exception -> " + e.getLocalizedMessage());
        }

        cancel_button.setOnClickListener(view -> dismiss());
        save_button.setOnClickListener(view -> {
            StringBuilder sb = new StringBuilder();
            sb.append(mCalendar.getYear());
            sb.append("-");
            sb.append(String.format("%02d", mCalendar.getMonth() + 1));  //TODO - months starts from 0 index
            sb.append("-");
            sb.append(String.format("%02d", mCalendar.getDayOfMonth()));
            doSave(sb.toString());
        });

        title.setText(RaApp.getLabel(LangKeys.key_date_of_birth));
        cancel_button.setText(RaApp.getLabel(LangKeys.key_cancel));
        save_button.setText(RaApp.getLabel(LangKeys.key_save));

    }

    //TODO - Decide later what todo save in dialog or from parent fragment
    private void doSave(String date) {
        Log.e(TAG, "doSave -> " + date);
        UserProfile data = model.getSelected().getValue();
        data.setBirthday(date); //DateFormat.format("dd-MM-yyyy", new Date(date)).toString()
        model.select(data);
        dismiss();
    }

}
