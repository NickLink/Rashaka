package com.rashaka.fragments.main.recipe.latest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.rashaka.RaApp;
import com.rashaka.domain.recipes.RecipeItem;
import com.rashaka.utils.Consts;
import com.rashaka.utils.rest.RestUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import com.rashaka.MainRouter;
import com.rashaka.domain.RestPageResponse;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class RecipePresenter extends SuperPresenter<RecipeView, MainRouter> {

    private static final String TAG = RecipePresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;    
    private boolean isLoading, isLastPage;
    private int pageNum = 0;
    private String mToken, mLangType, mUserId;

    public RecipePresenter() {
        mCompositeDisposable = new CompositeDisposable();
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mLangType = RaApp.getBase().getLangType();
        mUserId = RaApp.getBase().getLoggedUser().getId();
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
        mCompositeDisposable.add(Rest.call().getRecipes(
                tocken,
                lang,
                mUserId,
                String.valueOf(page)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(RestUtils.ErrorMessages(error))));

    }

    private void handleResponse(RestPageResponse<List<RecipeItem>> response) {

        Log.e(TAG, "handleResponse -> " + response.toString());
        isLoading = false;
        if (pageNum == 0 && !isLastPage)
            getView().setAdapterData(response.getMData());
        if (pageNum > 0 && !isLastPage)
            getView().addAdapterData(response.getMData());

        if (response.getNext_page() == -1)
            isLastPage = true;
        else
            pageNum++;
        
    }

    private void handleError(String error) {
        isLoading = false;
        getRouter().showError(error);
    }

    public void onScrolled(RecyclerView recyclerView) {
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int[] firstVisibleItems = null;
        int pastVisibleItems = 0;
        firstVisibleItems = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPositions(firstVisibleItems);
        if (!isLoading && !isLastPage) {
            if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                pastVisibleItems = firstVisibleItems[0];
            }
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount
                    && pastVisibleItems >= 0
                    && totalItemCount >= Consts.PAGE_SIZE) {
                loadData(mToken, mLangType, pageNum);
            }
        }
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

}
