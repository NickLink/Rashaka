package rashakacom.rashaka.fragments.login.lang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.models.LabelItem;
import rashakacom.rashaka.utils.rest.models.RestUtils;

import static rashakacom.rashaka.utils.Consts.ANIMATION_LEFT;

/**
 * Created by User on 22.08.2017.
 */


public class LangPresenter extends SuperPresenter<LangView, LoginRouter> {

    private CompositeDisposable mCompositeDisposable;
    private String langType;

    public LangPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
//        db = getView().getDb();
    }

    public void onLangSelected(String lang) {
        this.langType = lang;
        mCompositeDisposable.add(
                Rest.call().getLabelsItems(lang)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response.getMData()), error -> handleError(error))); //this::handleResponse, this::handleError

    }

    private void handleResponse(List<LabelItem> listRestResponse) {
        if (listRestResponse != null && listRestResponse.size() != 0) {
            RaApp.getBase().saveLangType(langType);
            RaApp.getBase().clearLabelTable();
            RaApp.getBase().saveLabelList(listRestResponse);
            getRouter().callSignIn(ANIMATION_LEFT);
            getRouter().showError("All data saved succesfully!");
        } else {
            getRouter().showError("Data save Error!");
        }

    }

    private void handleError(Throwable error) {
        getRouter().showError(RestUtils.ErrorMessages(error));
        Log.e("TAG", "RestUtils.ErrorMessages(error)! " + error.getLocalizedMessage());
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

}
