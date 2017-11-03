package com.rashaka.fragments.settings.settings;

import com.rashaka.fragments.BaseFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface SettingsView {

    void setViewsValues();
    void setAccount(String account);
    void setUserInfo(String userInfo);
    void pushFragment(BaseFragment fragment);
}
