package com.rashaka.fragments.main.plus.sleep;

import com.rashaka.domain.sleep.SleepItem;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface SleepLogView {

    void setLangValues();

    void setStartSleep(String startSleep);
    void setEndSleep(String endSleep);

    void setStartSleepTime(String startSleepTime);
    void setEndSleepTime(String endSleepTime);

    void setAdapterData(List<SleepItem> list);
    void addAdapterData(List<SleepItem> list);
}
