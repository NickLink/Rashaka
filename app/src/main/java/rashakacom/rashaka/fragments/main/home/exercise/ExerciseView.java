package rashakacom.rashaka.fragments.main.home.exercise;

import java.util.List;

import rashakacom.rashaka.domain.routes.RouteInfo;
import rashakacom.rashaka.fragments.BaseFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface ExerciseView {

    void setViewsValues();
    void pushFragment(BaseFragment fragment);

    void setAdapterData(List<RouteInfo> list);
    void addAdapterData(List<RouteInfo> list);
}
