package rashakacom.rashaka;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.utils.rest.Rest;
import rashakacom.rashaka.domain.PartnersDataItem;
import rashakacom.rashaka.domain.RestResponse;
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

    private void handleResponse(RestResponse<List<PartnersDataItem>> response) {
        mRouter.setPartnersLogos(response.getMData());
    }

    public void onDestroy(){
        mCompositeDisposable.clear();
    }

}
