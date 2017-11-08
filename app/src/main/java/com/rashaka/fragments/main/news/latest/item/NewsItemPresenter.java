package com.rashaka.fragments.main.news.latest.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.news.NewsItem;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Utility;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public void onShareClick(Context context) {
        Utility.shareIntent(
                context,
                "Here is the share content body",
                RaApp.getLabel(LangKeys.key_share_app));
    }

}
