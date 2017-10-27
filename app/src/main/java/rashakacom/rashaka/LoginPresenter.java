package rashakacom.rashaka;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.domain.PartnersData;
import rashakacom.rashaka.domain.RestResponse;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.utils.rest.RestUtils;

/**
 * Created by User on 23.08.2017.
 */

public class LoginPresenter {

    private LoginRouter mRouter;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginRouter router) {
        this.mRouter = router;
        mCompositeDisposable = new CompositeDisposable();
        loadPartnersLogos();
    }

    public void loadPartnersLogos(){
        mCompositeDisposable.add(
        Rest.call().getPartnersItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)); //response -> handleResponse(response), error -> handleError(error)

    }

    private void handleError(Throwable error) {
        mRouter.showError(RestUtils.ErrorMessages(error));
    }

    private void handleResponse(RestResponse<PartnersData> response) {
        mRouter.setPartnersLogos(response.getMData().getList());
    }

    public void onDestroy(){
        mCompositeDisposable.clear();
    }

}
