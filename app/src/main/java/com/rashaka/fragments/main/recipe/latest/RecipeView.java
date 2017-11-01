package com.rashaka.fragments.main.recipe.latest;

import com.rashaka.domain.recipes.RecipeItem;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface RecipeView {

    void setAdapterData(List<RecipeItem> list);
    void addAdapterData(List<RecipeItem> mData);
}
