package rashakacom.rashaka.utils.helpers.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by User on 06.09.2017.
 */


public class CustomLayoutManager extends LinearLayoutManager {
    /**
     * Disable predictive animations. There is a bug in RecyclerView which causes views that
     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
     * adapter size has decreased since the ViewHolder was recycled.
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

//    public CustomLayoutManager(Context context, int spanCount) {
//        super(context, spanCount);
//    }
//
//    public CustomLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
//        super(context, spanCount, orientation, reverseLayout);
//    }
}
