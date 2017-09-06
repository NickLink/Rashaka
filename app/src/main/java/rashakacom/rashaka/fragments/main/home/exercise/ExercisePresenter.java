package rashakacom.rashaka.fragments.main.home.exercise;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.fake_models.FakeNews;
import rashakacom.rashaka.utils.rest.models.RestUtils;

/**
 * Created by User on 24.08.2017.
 */

public class ExercisePresenter extends SuperPresenter<ExerciseView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;

    public ExercisePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        mCompositeDisposable.add(Rest.call().getFakeNews3().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(FakeNews response) {
        getView().setAdapterData(response.getArticles());
    }

    private void handleError(String error) {
        getRouter().showError(error);
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }
}
