package rashakacom.rashaka.fragments.main.home.bmi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.main.home.bmi.simulate.SimulateBMIFragment;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class BMIPresenter extends SuperPresenter<BMIView, MainRouter> {

    private CompositeDisposable mCompositeDisposable;
    private int mCount = 12;

    public BMIPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void setGraph(List<Double> list, ProgressBar... pBars){
        ProgressBar[] pArray = pBars;
        for(int i = 0 ; i < mCount ; i++){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)pArray[i].getLayoutParams();
            params.height = Support.dpToPx(getHeightForBMI(list.get(i)));
            pArray[i].setLayoutParams(params);
        }
    }

    public void setMonth(List<String> list, TextView... pText){
        TextView[] pArray = pText;
        for(int i = 0 ; i < mCount ; i++){
            pArray[i].setText(list.get(i));
        }
    }

    public void setValues(List<Double> list, TextView... pText){
        TextView[] pArray = pText;
        for(int i = 0 ; i < mCount ; i++){
            pArray[i].setText(String.valueOf(list.get(i)));
        }
    }

    private int getHeightForBMI(double bmi){
        int height = (int)(bmi - 15) * 8;
        return height;
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    public List<Double> bmiList(){
        List<Double> data = new ArrayList<>();
        data.add((double)30);
        data.add((double)31);
        data.add((double)32);
        data.add((double)33);
        data.add((double)32);
        data.add((double)32);
        data.add((double)30);
        data.add((double)32);
        data.add((double)33);
        data.add((double)32);
        data.add((double)32);
        data.add((double)30);
        return data;
    }

    public List<String> monthList(){
        List<String> data = new ArrayList<>();
        data.add(RaApp.getLabel(LangKeys.key_jan));
        data.add(RaApp.getLabel(LangKeys.key_feb));
        data.add(RaApp.getLabel(LangKeys.key_march));
        data.add(RaApp.getLabel(LangKeys.key_april));
        data.add(RaApp.getLabel(LangKeys.key_may));
        data.add(RaApp.getLabel(LangKeys.key_june));
        data.add(RaApp.getLabel(LangKeys.key_july));
        data.add(RaApp.getLabel(LangKeys.key_aug));
        data.add(RaApp.getLabel(LangKeys.key_sep));
        data.add(RaApp.getLabel(LangKeys.key_oct));
        data.add(RaApp.getLabel(LangKeys.key_nov));
        data.add(RaApp.getLabel(LangKeys.key_dec));
        return data;
    }

    public void onSimulateClick() {

        getView().pushFragment(new SimulateBMIFragment());

//        if(getUserAge() > 19){
//
//        } else {
//
//        }
//
//        if(RaApp.getBase().getProfileUser().getSex().equals(RaApp.getLabel(LangKeys.key_male))){
//            //TODO MALE case for age under 19
//        } else {
//
//        }



    }

    private int getUserAge(){
        String mDob = RaApp.getBase().getProfileUser().getBirthday();
        Date mDate = Support.getDateFromString(mDob, Support.DATE_FORMAT);
        int mAge = Support.getDiffYears(mDate, new Date());
        return mAge;
    }
}
