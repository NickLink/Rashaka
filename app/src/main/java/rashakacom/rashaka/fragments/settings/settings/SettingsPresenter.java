package rashakacom.rashaka.fragments.settings.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import rashakacom.rashaka.LoginActivity;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.settings.settings.change.ChangePasswordFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class SettingsPresenter extends SuperPresenter<SettingsView, MainRouter> {

    public SettingsPresenter() {


    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
    }

    public void onChangePassword() {
        getView().pushFragment(new ChangePasswordFragment());
    }

    public void LogoutAndRestart(FragmentActivity activity) {
        //TODO Logout user first
        RaApp.getBase().removeLoggedUser();

        restart(activity);
        //TODO Restart in AlarmManager Way
//        Intent mStartActivity = new Intent(activity, LoginActivity.class);
//        int mPendingIntentId = 989796;
//        PendingIntent mPendingIntent = PendingIntent.getActivity(activity, mPendingIntentId, mStartActivity,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
//        System.exit(0);
    }

    public void restart(FragmentActivity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }
}
