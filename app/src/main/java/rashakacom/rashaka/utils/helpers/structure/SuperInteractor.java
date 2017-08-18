package rashakacom.rashaka.utils.helpers.structure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rashakacom.rashaka.utils.helpers.structure.helpers.DataPresentsListener;
import rashakacom.rashaka.utils.helpers.structure.helpers.OnDestroyListener;
import rashakacom.rashaka.utils.helpers.structure.helpers.ResultListener;


/**
 * Created by com on 27.07.16.
 */
public abstract class SuperInteractor<Entity> implements
        DataPresentsListener,
        OnDestroyListener {

    protected Entity mData;

    public abstract void loadData(@Nullable Bundle bundle,
                                  @NonNull ResultListener listener);

    @Nullable
    public Entity getData() {
        return mData;
    }

    @Override
    public boolean hasData() {
        return getData() != null;
    }

    @Override
    public void onDestroy() {
        mData = null;
    }
}
