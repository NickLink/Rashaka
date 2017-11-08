package com.rashaka.fragments.main.news.latest;

import com.rashaka.domain.news.NewsItem;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface LatestView {

    void setAdapterData(List<NewsItem> list);
    void addAdapterData(List<NewsItem> mData);
}
