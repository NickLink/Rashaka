package com.rashaka.fragments.main.home.bmi;

import com.rashaka.fragments.BaseFragment;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface BMIView {

    void setViewsValues();
    void setBmiValue(String value);
    void pushFragment(BaseFragment fragment);

    void setGraphData(List<Double> wList, List<String> dList);
}
