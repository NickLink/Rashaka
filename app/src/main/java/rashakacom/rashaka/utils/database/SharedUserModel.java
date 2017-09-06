package rashakacom.rashaka.utils.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import rashakacom.rashaka.utils.rest.models.UserData;

/**
 * Created by User on 30.08.2017.
 */

public class SharedUserModel extends ViewModel {

    private MutableLiveData<UserData> selected = new MutableLiveData<>();

    public SharedUserModel() {
        selected.setValue(new UserData());
    }

    public void select(UserData item) {
        Log.e("TAG", "SharedUserModel select");
        selected.setValue(item);
    }

    public LiveData<UserData> getSelected() {
        if (selected == null)
            selected = new MutableLiveData<>();
        return selected;
    }
}