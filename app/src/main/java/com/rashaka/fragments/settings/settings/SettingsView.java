package com.rashaka.fragments.settings.settings;

import com.rashaka.fragments.BaseFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface SettingsView {

    void setViewsValues();

    void pushFragment(BaseFragment fragment);
}
