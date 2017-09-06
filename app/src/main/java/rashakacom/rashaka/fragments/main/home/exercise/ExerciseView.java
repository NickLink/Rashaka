package rashakacom.rashaka.fragments.main.home.exercise;

import java.util.List;

import rashakacom.rashaka.utils.rest.fake_models.Article;

/**
 * Created by User on 24.08.2017.
 */

public interface ExerciseView {

    void setViewsValues();
    void setAdapterData(List<Article> list);
}
