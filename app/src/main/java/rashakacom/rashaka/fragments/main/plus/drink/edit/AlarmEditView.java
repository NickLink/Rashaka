package rashakacom.rashaka.fragments.main.plus.drink.edit;

import android.widget.TextView;

import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

/**
 * Created by User on 24.08.2017.
 */

public interface AlarmEditView {

    void setViewsValues();

    void setDayChecked(TextView view, boolean enable);

    void setDateValues(String dayOfWeek, String dateOfDay);

    void doSave(DrinkAlarmItem data);

    void onCancel();
}
