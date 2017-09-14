package rashakacom.rashaka.fragments.main.news.latest;

import java.util.List;

import rashakacom.rashaka.domain.news.NewsItem;

/**
 * Created by User on 24.08.2017.
 */

public interface LatestView {

    void setAdapterData(List<NewsItem> list);

    void addAdapterData(List<NewsItem> mData);
}
