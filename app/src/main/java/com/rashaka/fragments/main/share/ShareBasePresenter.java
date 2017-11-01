package com.rashaka.fragments.main.share;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Utility;

import io.reactivex.disposables.CompositeDisposable;
import com.rashaka.MainRouter;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class ShareBasePresenter extends SuperPresenter<ShareBaseView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;

    public ShareBasePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setLangValue();
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    public void onShareClick(Context context) {
        Utility.shareIntent(
                context,
                "Here is the share content body",
                RaApp.getLabel(LangKeys.key_share_app));
    }
}
