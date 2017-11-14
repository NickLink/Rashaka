package com.rashaka.fragments.main.home;

import com.rashaka.fragments.BaseFragment;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface HomeBaseView {

    void setLangValue();
    void pushFragment(BaseFragment fragment);

    void stopRefresh();
    boolean isRefreshing();

    void setActiveTime(String time, int progress);
    void setCalories(String calories, int progress);
    void setDistance(String distance, boolean up);
    void setSteps(String steps, boolean up);
    void setWeight(String weight, boolean up);

    void setDailyGraph(List<Integer> mList);

    void showDialogSetStepsGoal(String title, String text, String button);
}
