package rashakacom.rashaka.fragments.main_home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class HomeBasePresenter extends SuperPresenter<HomeBaseView, MainRouter> {

    public HomeBasePresenter() {


    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setValues(RaApp.getBase().loadLoggedUser().getFirstName(),
                RaApp.getBase().loadLoggedUser().getEmail(),
                RaApp.getBase().loadLoggedUser().getTocken());
    }
}
