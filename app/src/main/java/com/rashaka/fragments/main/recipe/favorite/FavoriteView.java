package com.rashaka.fragments.main.recipe.favorite;

import com.rashaka.domain.fake_models.Article;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface FavoriteView {

    void setAdapterData(List<Article> list);
}
