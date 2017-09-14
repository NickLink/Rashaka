package rashakacom.rashaka.fragments.main.recipe.latest;

import java.util.List;

import rashakacom.rashaka.domain.fake_models.Article;

/**
 * Created by User on 24.08.2017.
 */

public interface LatestView {

    void setAdapterData(List<Article> list);
}
