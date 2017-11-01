package com.rashaka.fragments.main.plus.food;

import com.rashaka.domain.food.LogFoodItem;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface FoodLogView {

    void setLangValues();
    void setDate(String date);
    void clearFoodInput();
    void enableFoodLogButton(boolean enable);

    void setAdapterData(List<LogFoodItem> list);
    void addAdapterData(List<LogFoodItem> list);
}
