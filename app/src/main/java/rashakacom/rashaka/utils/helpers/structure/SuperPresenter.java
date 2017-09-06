package rashakacom.rashaka.utils.helpers.structure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import rashakacom.rashaka.utils.helpers.structure.helpers.OnDestroyListener;

/**
 * Created by com on 27.07.16.
 */
public abstract class SuperPresenter<View, Router> implements
        OnDestroyListener {

    private View mView;
    private Router mRouter;
    protected CompositeDisposable mCompositeDisposable;

    public SuperPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public abstract void onStart(@Nullable Bundle bundle);

    public abstract void onViewReady();

    @Override
    public void onDestroy() {
        mView = null;
        mRouter = null;
    }

    @Nullable
    public View getView() {
        return mView;
    }

    public void setView(@NonNull View view) {
        mView = view;
    }

    @Nullable
    public Router getRouter() {
        return mRouter;
    }

    public void setRouter(@NonNull Router router) {
        mRouter = router;
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }
}
