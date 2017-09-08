package rashakacom.rashaka.fragments.settings.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.settings.profile.dialogs.DobDialog;
import rashakacom.rashaka.fragments.settings.profile.dialogs.GenderDialog;
import rashakacom.rashaka.fragments.settings.profile.dialogs.HeightDialog;
import rashakacom.rashaka.fragments.settings.profile.dialogs.WeightDialog;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.models.BaseResponse;
import rashakacom.rashaka.utils.rest.models.profile.UserProfile;

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

    public void onBackgroundPlusClick() {
        getView().selectImage();
    }

    public void onBackgroundImageClick() {

    }

    public void onProfileImageClick() {
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


    public void onSaveClick() {
        //TODO Save profile data
        mCompositeDisposable.add(
                Rest.call().updateUserHashMap(
                        "api_key - tocken",
                        "multiform part",
                        "userId",
                        new HashMap<String, Object>()

                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response), error -> handleError(error))
        );

    }

    private void handleError(Throwable error) {
    }

    private void handleResponse(BaseResponse response) {
    }

    public void onProfileDataChanged(UserProfile o) {
        if (!TextUtils.isEmpty(o.getSex())) {
            getView().setProfileGenderText(o.getSex().equals("0")
                    ? RaApp.getLabel("key_male")
                    : RaApp.getLabel("key_female"));
        }
        if (!TextUtils.isEmpty(o.getBirthday())) {
            getView().setProfileDobText(o.getBirthday());
        }

        if (!TextUtils.isEmpty(o.getHight()))
            getView().setProfileHeightText(o.getHight());

        if (!TextUtils.isEmpty(o.getWeight())) {
            getView().setProfileWeightText(o.getWeight());
        }

        getView().setProfileWeightGText("123");
        getView().setProfileStepsGText("9999");

        if (!TextUtils.isEmpty(o.getBackground())) {
            getView().setProfileBackground(o.getBackground());
        }
        if (!TextUtils.isEmpty(o.getImage())) {
            getView().setProfileImage((o.getImage()));
        }
    }
}
