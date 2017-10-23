package rashakacom.rashaka.fragments.main.home.tips;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class TipsPresenter extends SuperPresenter<TipsView, MainRouter> {

    public TipsPresenter() {

    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().loadLink("http://www.rashaka.sa/index/pages/18/general_tips");
    }
}
