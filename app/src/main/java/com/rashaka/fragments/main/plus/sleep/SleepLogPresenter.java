package com.rashaka.fragments.main.plus.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.BaseResponse;
import com.rashaka.domain.RestPageResponse;
import com.rashaka.domain.sleep.Sleeps;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by User on 24.08.2017.
 */

public class SleepLogPresenter extends SuperPresenter<SleepLogView, MainRouter> {

    private static final String TAG = SleepLogPresenter.class.getSimpleName();
    private boolean isLoading, isLastPage;
    private int pageNum = 0;
    private String mToken, userId;

    public SleepLogPresenter(MainRouter myRouter) {
        setRouter(myRouter);
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        userId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setLangValues();
        refreshLogSleepList();
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);

    }

    public void refreshLogSleepList(){
        pageNum = 0;
        isLastPage = false;
        refreshLogSleepList(pageNum);
    }

    public void refreshLogSleepList(int page) {
        Log.e(TAG, "refreshLogSleepList page = " + page);
        isLoading = true;
        mCompositeDisposable.add(Rest.call().getAllSleepLog(
                mToken,
                userId,
                String.valueOf(page)
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> sleepLogListRefresh(response), error -> handleError(error.getLocalizedMessage()))
        );

    }

    private void sleepLogListRefresh(RestPageResponse<Sleeps> response) {
        Log.e(TAG, "handleResponse -> " + response.toString());
        isLoading = false;
        if(response.getStatus()){
            if (pageNum == 0 && !isLastPage)
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

    public void onScrolled(RecyclerView recyclerView) {
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
        Log.e(TAG, "isLoading -> " + isLoading + " isLastPage " + isLastPage);
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= Consts.PAGE_SIZE_10) {
                Log.e(TAG, "onScrolled  totalItemCount = " + totalItemCount + " visibleItemCount " + visibleItemCount);
                refreshLogSleepList(pageNum);
            }
        }
    }

    public void onLogClick(String start, String end, String desc) {
        int bedTime = getTimeInBed(start, end);
        if(bedTime < 0
                || bedTime < 60 * 60
                || bedTime > 60 * 60 * 23){
            getRouter().showDialog("Info",
                    "Sleep time cannot be less than one hour, more than 23 hours or be negative!",
                    null);

        } else {
            mCompositeDisposable.add(Rest.call().postUserLogSleep(
                    mToken,
                    userId,
                    start,
                    end,
                    desc,
                    String.valueOf(bedTime)
                    ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> sleepLogSuccess(response), error -> handleError(error.getLocalizedMessage()))
            );
        }
    }

    private void sleepLogSuccess(BaseResponse response) {
        Log.e(TAG, "sleepLogSuccess -> " + response.toString());
        getRouter().showError(response.getMessage());
        refreshLogSleepList();
    }

    public int getTimeInBed(String startSleepDateAndTime, String endSleepDateAndTime) {
        Log.e(TAG, "startSleepDateAndTime - > " + startSleepDateAndTime + " endSleepDateAndTime - > " + endSleepDateAndTime);
        Date sDate = Support.getDateFromString(startSleepDateAndTime, Support.DATE_FORMAT_FULL);
        long sTime = sDate.getTime();
        Date eDate = Support.getDateFromString(endSleepDateAndTime, Support.DATE_FORMAT_FULL);
        long eTime = eDate.getTime();

        long difference = eTime - sTime;
        int seconds = (int) difference / 1000;
        Log.e(TAG, "difference in Seconds - > " + seconds);
        return seconds;
    }
}
