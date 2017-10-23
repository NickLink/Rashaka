package rashakacom.rashaka.fragments.main.plus.food;

import java.util.List;

import rashakacom.rashaka.domain.food.LogFoodItem;

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
