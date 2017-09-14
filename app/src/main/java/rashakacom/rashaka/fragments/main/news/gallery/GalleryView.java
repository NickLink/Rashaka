package rashakacom.rashaka.fragments.main.news.gallery;

import java.util.List;

import rashakacom.rashaka.domain.fake_models.Article;

/**
 * Created by User on 24.08.2017.
 */

public interface GalleryView {

    void setAdapterData(List<Article> list);
}
