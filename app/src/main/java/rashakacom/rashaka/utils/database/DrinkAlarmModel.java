package rashakacom.rashaka.utils.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

/**
 * Created by User on 30.08.2017.
 */

public class DrinkAlarmModel extends ViewModel {

    private MutableLiveData<DrinkAlarmItem> selected = new MutableLiveData<>();

    public DrinkAlarmModel() {
        selected.setValue(new DrinkAlarmItem());
    }

    public void select(DrinkAlarmItem item) {
        Log.e("TAG", "SharedUserModel select");
        selected.setValue(item);
    }

    public LiveData<DrinkAlarmItem> getSelected() {
        if (selected == null)
            selected = new MutableLiveData<>();
        return selected;
    }
}