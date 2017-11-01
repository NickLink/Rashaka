package com.rashaka.fragments.main.home;

import com.rashaka.fragments.BaseFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface HomeBaseView {

    void setLangValue();
    void pushFragment(BaseFragment fragment);

    void stopRefresh();

    void setActiveTime(String time, int progress);
    void setCalories(String calories, int progress);
    void setDistance(String distance);
    void setSteps(String steps);
    void setWeight(String weight);

    boolean isRefreshing();

    void showDialogSetStepsGoal(String title, String text, String button);
}
