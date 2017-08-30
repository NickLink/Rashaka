package rashakacom.rashaka.fragments.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.fragments.main.home.bmi.BMIFragment;
import rashakacom.rashaka.fragments.main.home.exercise.ExerciseFragment;
import rashakacom.rashaka.fragments.main.home.tips.TipsFragment;
import rashakacom.rashaka.fragments.main.home.weight.WeightFragment;
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
        getView().setLangValue();
    }

    public void onWeightClick(){
        getView().pushFragment(new WeightFragment());
    }

    public void onExerciseClick(){
        getView().pushFragment(new ExerciseFragment());
    }

    public void onTipsClick(){
        getView().pushFragment(new TipsFragment());
    }

    public void onBMIClick(){
        getView().pushFragment(new BMIFragment());
    }
}
