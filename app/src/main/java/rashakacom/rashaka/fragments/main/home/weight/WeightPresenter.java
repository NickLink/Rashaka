package rashakacom.rashaka.fragments.main.home.weight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.domain.BaseResponse;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.rest.Rest;

/**
 * Created by User on 24.08.2017.
 */

public class WeightPresenter extends SuperPresenter<WeightView, MainRouter> {

    private static final String TAG = WeightPresenter.class.getSimpleName();
    private double mCurrentWeight;
    private double mDefWeight = 80;
    private double mMinWeight = 30;
    private double mMaxWeight = 240;


    public WeightPresenter() {
        if(!TextUtils.isEmpty(RaApp.getBase().getProfileUser().getWeight()))
            try{
                mCurrentWeight = Double.parseDouble(RaApp.getBase().getProfileUser().getWeight());
            } catch (Exception e){
                mCurrentWeight = mDefWeight;
            }
        else
            mCurrentWeight = mDefWeight;
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().setBigWeightText(getWeight(mCurrentWeight));
    }

    public void onPlusClick() {
        if(mCurrentWeight < mMaxWeight){
            mCurrentWeight++;
            getView().setBigWeightText(getWeight(mCurrentWeight));
        }
    }

    public void onMinusClick() {
        if(mCurrentWeight > mMinWeight){
            mCurrentWeight--;
            getView().setBigWeightText(getWeight(mCurrentWeight));
        }

    }

    private String getWeight(double weight){
        return weight + " " + RaApp.getLabel("key_kg");
    }

    public void onSaveClick() {
        String tocken = RaApp.getBase().getLoggedUser().getTocken();
        String userId = RaApp.getBase().getLoggedUser().getId();
        mCompositeDisposable.add(Rest.call().postDailyWeight(tocken, userId, String.valueOf(mCurrentWeight))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response), error -> handleError(error.getLocalizedMessage()))
        );
    }

    private void handleError(String error) {
        Log.e(TAG, "handleError -> " + error);
        getRouter().showError(error);

    }

    private void handleResponse(BaseResponse response) {
        Log.e(TAG, "handleResponse -> " + response.toString());
        getRouter().showError(response.getMessage());
        //RaApp.getBase().getProfileUser().setWeight();

    }

    public void setupViewPager(ViewPager mViewPager, FragmentManager childFragmentManager) { //Context context
        //WeightPagerAdapter adapter = new WeightPagerAdapter(context);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(childFragmentManager);
        mViewPager.setAdapter(adapter);
    }

    public void setSeekValue(int i) {
        int intPart = (int)mCurrentWeight;
        int decPart = i;
        mCurrentWeight = Double.valueOf(intPart + "." + decPart);
        getView().setBigWeightText(getWeight(mCurrentWeight));
    }

//    Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
//            .setScale(3, RoundingMode.HALF_UP)
//            .doubleValue();
}
