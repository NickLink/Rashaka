package rashakacom.rashaka.fragments.main.home.exercise;

import java.util.List;

import rashakacom.rashaka.domain.fake_models.Article;
import rashakacom.rashaka.fragments.BaseFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface ExerciseView {

    void setViewsValues();
    void setAdapterData(List<Article> list);
    void pushFragment(BaseFragment fragment);
}
