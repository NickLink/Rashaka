package rashakacom.rashaka.fragments.main.home.bmi;

import rashakacom.rashaka.fragments.BaseFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface BMIView {

    void setViewsValues();
    void setBmiValue(String value);
    void pushFragment(BaseFragment fragment);
}
