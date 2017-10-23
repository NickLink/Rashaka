package rashakacom.rashaka.utils.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import rashakacom.rashaka.domain.profile.UserProfile;

/**
 * Created by User on 30.08.2017.
 */

public class SharedUserModel extends ViewModel {

    private static final String TAG = SharedUserModel.class.getSimpleName();
    private MutableLiveData<UserProfile> selected = new MutableLiveData<>();

    public SharedUserModel() {
        selected.setValue(new UserProfile());
    }

    public void select(UserProfile item) {
        Log.e(TAG, "SharedUserModel select");
        selected.setValue(item);
    }

    public LiveData<UserProfile> getSelected() {
        if (selected == null)
            selected = new MutableLiveData<>();
        return selected;
    }
}