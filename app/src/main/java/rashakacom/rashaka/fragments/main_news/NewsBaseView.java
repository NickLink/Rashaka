package rashakacom.rashaka.fragments.main_news;

import java.util.List;

import rashakacom.rashaka.utils.rest.fake_models.Article;

/**
 * Created by User on 24.08.2017.
 */

public interface NewsBaseView {

    void setAdapterData(List<Article> list);
}
