package rashakacom.rashaka.fragments.settings.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.settings.profile.dialogs.DobDialog;
import rashakacom.rashaka.fragments.settings.profile.dialogs.GenderDialog;
import rashakacom.rashaka.fragments.settings.profile.dialogs.HeightDialog;
import rashakacom.rashaka.fragments.settings.profile.dialogs.WeightDialog;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class ProfilePresenter extends SuperPresenter<ProfileView, MainRouter> {

    public ProfilePresenter() {

    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setLangValue();
        getView().setProfileInfo(RaApp.getBase().getLoggedUser().getFirstName() + " "
                + RaApp.getBase().getLoggedUser().getLastName(),
                RaApp.getBase().getLoggedUser().getEmail());
    }

    public void onBackgroundPlusClick(){
        getView().selectImage();
    }

    public void onBackgroundImageClick(){

    }

    public void onProfileImageClick(){
        getView().selectImage();
    }



    //TODO Settings buttons
    public void onGenderClick() {
        BottomSheetDialogFragment bottomSheetDialogFragment = new GenderDialog();
        getView().setBottomDialog(bottomSheetDialogFragment);
    }

    public void onDobClick() {
        BottomSheetDialogFragment bottomSheetDialogFragment = new DobDialog();
        getView().setBottomDialog(bottomSheetDialogFragment);
    }

    public void onHeightClick() {
        BottomSheetDialogFragment bottomSheetDialogFragment = new HeightDialog();
        getView().setBottomDialog(bottomSheetDialogFragment);
    }

    public void onWeightClick() {
        BottomSheetDialogFragment bottomSheetDialogFragment = new WeightDialog();
        getView().setBottomDialog(bottomSheetDialogFragment);
    }

    public void onWeightGClick() {
    }

    public void onStepsGClick() {
    }


}
