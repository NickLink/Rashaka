package rashakacom.rashaka.fragments.main.news.latest.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.domain.RestResponse;
import rashakacom.rashaka.domain.news.NewsItem;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.RestUtils;

/**
 * Created by User on 24.08.2017.
 */

public class NewsItemPresenter extends SuperPresenter<NewsItemView, MainRouter> {

    private static final String TAG = NewsItemPresenter.class.getSimpleName();
    private String mToken, mLangType;

    public NewsItemPresenter() {
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mLangType = RaApp.getBase().getLangType();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    public void loadData(String id){
        Log.e(TAG, "loadData -> " + mToken + mLangType + id);
                mCompositeDisposable.add(Rest.call().getNewsItem(mToken, mLangType, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response),
                                error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<NewsItem> response) {
        getView().setViewData(response.getMData());
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);
    }

    @Override
    public void onViewReady() {

    }
}
