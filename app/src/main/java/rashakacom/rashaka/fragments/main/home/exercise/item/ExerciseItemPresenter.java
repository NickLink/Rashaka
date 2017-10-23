package rashakacom.rashaka.fragments.main.home.exercise.item;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.utils.communications.GPS_Tracker;
import rashakacom.rashaka.utils.communications.PointInfo;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

import static rashakacom.rashaka.utils.Consts.LOCATION_PERMISSIONS_REQUEST;

/**
 * Created by User on 24.08.2017.
 */

public class ExerciseItemPresenter extends SuperPresenter<ExerciseItemView, MainRouter> {

    private static final String TAG = ExerciseItemPresenter.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    public static final float MAX_SPEED_WALK = 3.0f;


    public ExerciseItemPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {

    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    public boolean checkPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e(TAG, " Permission do not exist - go to Ask for it !");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSIONS_REQUEST);
            return false;
        } else {
            Log.e(TAG, " Permission was granted !");
            return true;
        }
    }

    public void stopGpsTracker(GPS_Tracker tracker){
        if(tracker != null){
            tracker.stopUsingGPS();
            tracker = null;
        }
    }

    public boolean locationValid(Location location) {
        if(location != null && location.getLatitude() != 0 && location.getLongitude() != 0)
            return true;
        else
            return false;
    }

    public void checkQuality(ArrayList<PointInfo> route_list, int pos) {

    }
}
