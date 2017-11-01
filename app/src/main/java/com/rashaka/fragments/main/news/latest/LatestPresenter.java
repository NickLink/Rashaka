package com.rashaka.fragments.main.news.latest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestPageResponse;
import com.rashaka.domain.news.News;
import com.rashaka.utils.Consts;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class LatestPresenter extends SuperPresenter<LatestView, MainRouter> {

    private static final String TAG = LatestPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    private boolean isLoading, isLastPage;
    private int pageNum = 0;
    private String mToken, mLangType;

    public LatestPresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mLangType = RaApp.getBase().getLangType();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        isLastPage = false;
        loadData(mToken,
                mLangType,
                pageNum);
    }

    private void loadData(String tocken, String lang, int page) {
        isLoading = true;
        mCompositeDisposable.add(Rest.call().getNews(tocken, lang, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));

    }

    private void handleResponse(RestPageResponse<News> response) {
        Log.e(TAG, "handleResponse -> " + response.toString());
        isLoading = false;
        if (pageNum == 0 && !isLastPage)
            getView().setAdapterData(response.getMData().getList());
        if (pageNum > 0 && !isLastPage)
            getView().addAdapterData(response.getMData().getList());

        if (response.getNext_page() == -1)
            isLastPage = true;
        else
            pageNum++;


//        isLoading = false;
//        if (response.getNext_page() == -1) // &&
//            isLastPage = true;
//        else
//            pageNum++;
//
//        if (pageNum == 0)
//            getView().setAdapterData(response.getMData());
//        else
//            getView().addAdapterData(response.getMData());
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        isLoading = false;
        getRouter().showError(error);
    }

    public void onScrolled(RecyclerView recyclerView) {
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= Consts.PAGE_SIZE) {
                loadData(mToken, mLangType, pageNum);
            }
        }
    }


    public void onStop() {
        mCompositeDisposable.clear();
    }
}
