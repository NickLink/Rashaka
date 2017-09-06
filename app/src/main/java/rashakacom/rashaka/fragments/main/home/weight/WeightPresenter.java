package rashakacom.rashaka.fragments.main.home.weight;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class WeightPresenter extends SuperPresenter<WeightView, MainRouter> {

    private int mCurrentWeight;
    private int mMinWeight = 30;
    private int mMaxWeight = 240;


    public WeightPresenter() {
        //mCurrentWeight = RaApp.getBase().getLoggedUser().getWeight();
        mCurrentWeight = 100;
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

    private String getWeight(int weight){
        return weight + " " + RaApp.getLabel("key_kg");
    }

    public void onSaveClick() {

    }
}
