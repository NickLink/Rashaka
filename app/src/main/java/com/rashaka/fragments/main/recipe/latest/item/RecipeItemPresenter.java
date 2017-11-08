package com.rashaka.fragments.main.recipe.latest.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rashaka.MainRouter;
import com.rashaka.RaApp;
import com.rashaka.domain.RestResponse;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Utility;
import com.rashaka.utils.rest.RestUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.rashaka.domain.recipes.RecipeItem;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class RecipeItemPresenter extends SuperPresenter<RecipeItemView, MainRouter> {

    private static final String TAG = RecipeItemPresenter.class.getSimpleName();
    private String mToken, mLangType, mUserId;

    public RecipeItemPresenter() {
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        mLangType = RaApp.getBase().getLangType();
        mUserId = RaApp.getBase().getLoggedUser().getId();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    public void loadData(String id){
        Log.e(TAG, "loadData -> " + mToken + mLangType + id);
                mCompositeDisposable.add(Rest.call().getRecipeItem(mToken, mLangType, mUserId, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> handleResponse(response),
                                error -> handleError(RestUtils.ErrorMessages(error))));
    }

    private void handleResponse(RestResponse<RecipeItem> response) {
        getView().setViewData(response.getMData());
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);
    }

    @Override
    public void onViewReady() {

    }

    public void showWebView(WebView mWebView, String message) {
        if (!TextUtils.isEmpty(message)) {
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            mWebView.setBackgroundColor(0x00000000);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setDefaultFontSize(Consts.WEB_TEXT_SIZE);
            mWebView.loadDataWithBaseURL("", message, mimeType, encoding, "");
        }
    }

    public void onShareClick(Context context) {
        Utility.shareIntent(
                context,
                "Body",
                "Extra");
    }
}
