package com.rashaka.fragments.main.news.gallery;

import com.rashaka.domain.gallery.GalleryItem;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface GalleryView {

    void setAdapterData(List<GalleryItem> list);
    void addAdapterData(List<GalleryItem> mData);
}
