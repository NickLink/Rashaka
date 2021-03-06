package com.rashaka.fragments.main.home.exercise;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestPageResponse;
import com.rashaka.domain.routes.Routes;
import com.rashaka.fragments.main.home.exercise.it.ExerciseItemFragment;
import com.rashaka.utils.Consts;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;
import com.rashaka.utils.rest.RestUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class ExercisePresenter extends SuperPresenter<ExerciseView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;
    private boolean isLoading, isLastPage;
    private int pageNum = 0;
    private String mToken, mUserId, mLangType;

    public ExercisePresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mUserId = RaApp.getBase().getLoggedUser().getId();
        mLangType = RaApp.getBase().getLangType();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        loadData(mToken, mUserId, pageNum);
    }

    private void loadData(String mToken, String mUserId, int pageNum) {
        isLoading = true;
        mCompositeDisposable.add(Rest.call().getAllUserRoutes(
                mToken,
                mUserId,
                String.valueOf(pageNum)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestPageResponse<Routes> response) {
        isLoading = false;
        if(response.getStatus()) {
            if (pageNum == 0)
                getView().setAdapterData(response.getMData().getList());
            else
                getView().addAdapterData(response.getMData().getList());

            if (response.getNext_page() == -1)
                isLastPage = true;
            else
                pageNum++;
        } else {
            getView().setAdapterData(new ArrayList<>());
        }
    }

    private void handleError(@NonNull String error) {
        isLoading = false;
        getRouter().showError(error);
    }

    public void onStop() {
        mCompositeDisposable.clear();
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
                loadData(mToken, mUserId, pageNum);
            }
        }
    }

    public void onNewExersize() {
        getView().pushFragment(new ExerciseItemFragment());
    }
}
