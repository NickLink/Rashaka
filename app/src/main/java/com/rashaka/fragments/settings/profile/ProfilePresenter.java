package com.rashaka.fragments.settings.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.BaseResponse;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestKeys;
import com.rashaka.utils.rest.RestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by User on 24.08.2017.
 */

public class ProfilePresenter extends SuperPresenter<ProfileView, MainRouter> {

    private static final String TAG = ProfilePresenter.class.getSimpleName();
    private UserProfile profile;
    private boolean saveEnabled;
    //private PublishSubject<Boolean> changeObservable = PublishSubject.create();

    public ProfilePresenter(MainRouter router) {
        setRouter(router);
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
        saveEnabled = false;
        //getView().setSaveEnabled(saveEnabled);

//        mCompositeDisposable.add(getSaveChanges()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .skip(1)
//                .subscribe(aBoolean -> setSave(aBoolean)));


    }

    private void setSave(Boolean aBoolean) {
        //getView().setSaveEnabled(aBoolean);
//        if (aBoolean)
//            saveProfileUpdate(profile);
    }

    public void onBackgroundPlusClick() {
        getView().selectImage();
    }

    public void onBackgroundImageClick() {
        getView().selectImage();
    }

    public void onProfileImageClick() {
        getView().selectImage();
    }


    //TODO Settings buttons
    public void onDialogClick(BottomSheetDialogFragment dialogFragment) {
        getView().setBottomDialog(dialogFragment);
    }

    public void onSaveClick() {

    }

    private void handleError(Throwable error) {
        Log.e(TAG, "handleError -> " + error.getLocalizedMessage());
        getRouter().showError(RestUtils.ErrorMessages(error));
    }

    private void handleResponse(BaseResponse response, UserProfile value) {
        Log.e(TAG, "handleResponse -> " + response.toString());
        RaApp.getBase().setProfileUser(value);
        getRouter().showError(response.getMessage());
        //changeObservable.onNext(false);
        getView().changesSaved();
    }

    public void onProfileDataChanged(UserProfile profile) {
        Log.e(TAG, "onProfileDataChanged");
        this.profile = profile;
        if (!TextUtils.isEmpty(profile.getSex())) {
            getView().setProfileGenderText(profile.getSex().equals("0")
                    ? RaApp.getLabel(LangKeys.key_male)
                    : RaApp.getLabel(LangKeys.key_female));
        }
        if (!TextUtils.isEmpty(profile.getBirthday())) {
            Log.e(TAG, "SET DOB is -> " + profile.getBirthday());
            getView().setProfileDobText(Support.getStringDateByDate(profile.getBirthday(), Support.DATE_FORMAT, false));
        }
        if (!TextUtils.isEmpty(profile.getHight()))
            getView().setProfileHeightText(profile.getHight() + " " + RaApp.getLabel(LangKeys.key_cm));

        if (!TextUtils.isEmpty(profile.getWeight())) {
            getView().setProfileWeightText(profile.getWeight() + " " + RaApp.getLabel(LangKeys.key_kg));
        }
        if (!TextUtils.isEmpty(profile.getWeightGoal())) {
            getView().setProfileWeightGText(profile.getWeightGoal() + " " + RaApp.getLabel(LangKeys.key_kg));
        }
        if (!TextUtils.isEmpty(profile.getStepsGoal())) {
            getView().setProfileStepsGText(profile.getStepsGoal() + " " + RaApp.getLabel(LangKeys.key_steps));
        }

        if (!TextUtils.isEmpty(profile.getBackground())) {
            getView().setProfileBackground(profile.getBackground());
        }
        if (!TextUtils.isEmpty(profile.getImage())) {
            getView().setProfileImage((profile.getImage()));
        }

        //TODO Set progress bars
        Log.e(TAG, "onProfileDataChanged progress AVmin = " + profile.getWeeklyA().getAvMin() + " AVsteps " + profile.getWeeklyA().getAvSteps());
        getView().setActiveBar(profile.getWeeklyA().getAvMin(), profile.getWeeklyA().getAvMinPercent());
        getView().setStepsBar(profile.getWeeklyA().getAvSteps(), profile.getWeeklyA().getAvStepsPercent());


        //changeObservable.onNext(true);
        if(saveEnabled){
            saveProfileUpdate(profile);
        }
        saveEnabled = true;
    }

//    public Observable<Boolean> getSaveChanges() {
//        return changeObservable;
//    }

    public void saveProfileUpdate(UserProfile value) {

        String tocken = RaApp.getBase().getLoggedUser().getTocken();
        String userId = RaApp.getBase().getLoggedUser().getId();
        Log.e(TAG, " getTocken -> " + tocken + " getId -> " + userId);
        mCompositeDisposable.add(
                Rest.call().updateUserProfile(
                        tocken,
                        userId,
                        getParamsMap(value),
                        getFileMap(value))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response, value), error -> handleError(error))
        );
    }

    private Map<String, RequestBody> getParamsMap(UserProfile value) {
        Log.e(TAG, "getParamsMap - > " + value.getSex());
        Map<String, RequestBody> params = new HashMap<>();
        if (!TextUtils.isEmpty(value.getPhone()))
            params.put(RestKeys.KEY_PHONE, Rest.createRequestBody(value.getPhone()));

        if (!TextUtils.isEmpty(value.getFirstName()))
            params.put(RestKeys.KEY_F_NAME, Rest.createRequestBody(value.getFirstName()));

        if (!TextUtils.isEmpty(value.getLastName()))
            params.put(RestKeys.KEY_L_NAME, Rest.createRequestBody(value.getLastName()));

        if (!TextUtils.isEmpty(value.getBirthday()))
            params.put(RestKeys.KEY_BIRTHDAY, Rest.createRequestBody(value.getBirthday()));

        if (!TextUtils.isEmpty(value.getSex()))
            params.put(RestKeys.KEY_SEX, Rest.createRequestBody(value.getSex()));

        if (!TextUtils.isEmpty(value.getHight()))
            params.put(RestKeys.KEY_HEIGHT, Rest.createRequestBody(value.getHight()));

        if (!TextUtils.isEmpty(value.getWeight()))
            params.put(RestKeys.KEY_WEIGHT, Rest.createRequestBody(value.getWeight()));

        if (!TextUtils.isEmpty(value.getWeightGoal()))
            params.put(RestKeys.KEY_WEIGHT_GOAL, Rest.createRequestBody(value.getWeightGoal()));

        if (!TextUtils.isEmpty(value.getStepsGoal()))
            params.put(RestKeys.KEY_STEPS_GOAL, Rest.createRequestBody(value.getStepsGoal()));

        return params;
    }

    private List<MultipartBody.Part> getFileMap(UserProfile value) {
        List<MultipartBody.Part> files = new ArrayList<>();
        //Log.e(TAG, " value.getImage() -> " + value.getImage() + " is -> " + value.getImage().contains(Consts.userImage));
        if (!TextUtils.isEmpty(value.getImage()) && value.getImage().contains(Consts.userImage)) {
            String path = value.getImage().replace("file:", "");
            RequestBody requestFile = Rest.createRequestBody(new File(path));
            files.add(MultipartBody.Part.createFormData(RestKeys.KEY_IMAGE, Consts.userImage, requestFile));
        }
        //Log.e(TAG, " value.getBackground() -> " + value.getBackground() + " is -> " + value.getBackground().contains(Consts.backImage));
        if (!TextUtils.isEmpty(value.getBackground()) && value.getBackground().contains(Consts.backImage)) {
            String path = value.getBackground().replace("file:", "");
            RequestBody requestFile = Rest.createRequestBody(new File(path));
            files.add(MultipartBody.Part.createFormData(RestKeys.KEY_BACKGROUND, Consts.backImage, requestFile));
        }

        return files;
    }
}
