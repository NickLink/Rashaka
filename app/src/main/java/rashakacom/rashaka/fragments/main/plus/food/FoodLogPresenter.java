package rashakacom.rashaka.fragments.main.plus.food;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.domain.BaseResponse;
import rashakacom.rashaka.domain.RestPageResponse;
import rashakacom.rashaka.domain.food.LogFood;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.Consts;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class FoodLogPresenter extends SuperPresenter<FoodLogView, MainRouter> {

    private static final String TAG = FoodLogPresenter.class.getSimpleName();
    private boolean isLoading, isLastPage;
    private int pageNum = 0;
    private String mToken, userId;
    private String[] mFoodTypes = {RaApp.getLabel(LangKeys.key_breakfast),
            RaApp.getLabel(LangKeys.key_lunch),
            RaApp.getLabel(LangKeys.key_diner)};

    public FoodLogPresenter() {
        mToken = RaApp.getBase().getLoggedUser().getTocken();
        userId = RaApp.getBase().getLoggedUser().getId();

    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setLangValues();
        getView().setDate(RaApp.getLabel(LangKeys.key_today) + ", " + Support.GetDateTimeForScreen());
        refreshLogFoodList();
    }

    public ArrayAdapter<String> mAdapter(Context context){
        return new ArrayAdapter<String>(context, R.layout.item_food_spinner, mFoodTypes); //simple_spinner_item
    }

    public void onLogClick(String foodType, String foodDescription, String foodDateTime) {
        mCompositeDisposable.add(Rest.call().postUserLogFood(
                RaApp.getBase().getLoggedUser().getTocken(),
                RaApp.getBase().getLoggedUser().getId(),
                foodDescription,
                foodDateTime,
                foodType
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> foodLogSuccess(response), error -> handleError(error.getLocalizedMessage()))
        );
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);

    }

    private void foodLogSuccess(BaseResponse response) {
        Log.e(TAG, "foodLogSuccess -> " + response.toString());
        getRouter().showError(response.getMessage());
        getView().clearFoodInput();
    }

    public void refreshLogFoodList(){
        pageNum = 0;

        refreshLogFoodList(mToken, userId, pageNum);
    }

    public void refreshLogFoodList(String token, String id, int page) {
        Log.e(TAG, "refreshLogFoodList page = " + page);
        isLoading = true;
        mCompositeDisposable.add(Rest.call().getAllFoodLog(
                token,
                id,
                String.valueOf(page)
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> foodLogListRefresh(response), error -> handleError(error.getLocalizedMessage()))
        );

    }

    private void foodLogListRefresh(RestPageResponse<LogFood> response) {
        Log.e(TAG, "handleResponse -> " + response.toString());
        isLoading = false;
        if (pageNum == 0 && !isLastPage)
            getView().setAdapterData(response.getMData().getList());
        else
            getView().addAdapterData(response.getMData().getList());

        if (response.getNext_page() == -1)
            isLastPage = true;
        else
            pageNum++;


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
                refreshLogFoodList(mToken, userId, pageNum);
            }
        }
    }
}
