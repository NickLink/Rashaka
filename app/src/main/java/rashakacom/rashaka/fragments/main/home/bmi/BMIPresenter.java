package rashakacom.rashaka.fragments.main.home.bmi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class BMIPresenter extends SuperPresenter<BMIView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;

    public BMIPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {

    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

}
