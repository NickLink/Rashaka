package rashakacom.rashaka.utils.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import rashakacom.rashaka.utils.rest.models.UserData;

/**
 * Created by User on 30.08.2017.
 */

public class SharedViewModel extends ViewModel {

    private MutableLiveData<UserData> selected = new MutableLiveData<>();

    public SharedViewModel() {
        selected.setValue(new UserData());
    }

    public void select(UserData item) {
        selected.setValue(item);
    }

    public LiveData<UserData> getSelected() {
        if (selected == null)
            selected = new MutableLiveData<>();
        return selected;
    }
}