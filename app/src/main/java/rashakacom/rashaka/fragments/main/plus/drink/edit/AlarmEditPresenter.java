package rashakacom.rashaka.fragments.main.plus.drink.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

public class AlarmEditPresenter extends SuperPresenter<AlarmEditView, MainRouter> {

    private DrinkAlarmItem mDataItem;

    public AlarmEditPresenter() {
        //mDataItem = new DrinkAlarmItem();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {

    }

    @Override
    public void onViewReady() {
        getView().setViewsValues();
        getView().setDateValues(getDayOfWeek(), getDateOfDay());
    }

    public void onDayClick(TextView view) {

        switch (view.getId()){
            case R.id.item_day_Mon:
                mDataItem.setEnMonday(!mDataItem.isEnMonday());
                getView().setDayChecked(view, mDataItem.isEnMonday());
                break;
            case R.id.item_day_Tue:
                mDataItem.setEnTuesday(!mDataItem.isEnTuesday());
                getView().setDayChecked(view, mDataItem.isEnTuesday());
                break;
            case R.id.item_day_Wed:
                mDataItem.setEnWednesday(!mDataItem.isEnWednesday());
                getView().setDayChecked(view, mDataItem.isEnWednesday());
                break;
            case R.id.item_day_Thu:
                mDataItem.setEnThursday(!mDataItem.isEnThursday());
                getView().setDayChecked(view, mDataItem.isEnThursday());
                break;
            case R.id.item_day_Fri:
                mDataItem.setEnFriday(!mDataItem.isEnFriday());
                getView().setDayChecked(view, mDataItem.isEnFriday());
                break;
            case R.id.item_day_Sat:
                mDataItem.setEnSaturday(!mDataItem.isEnSaturday());
                getView().setDayChecked(view, mDataItem.isEnSaturday());
                break;
            case R.id.item_day_Sun:
                mDataItem.setEnSunday(!mDataItem.isEnSunday());
                getView().setDayChecked(view, mDataItem.isEnSunday());
                break;

        }
    }

    public String getDayOfWeek() {
        int dayOfWeekPos = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Log.e("TAG", "getDayOfWeek - > dayOfWeekPos = " + dayOfWeekPos);
        switch (dayOfWeekPos){
            case Calendar.MONDAY:
                return RaApp.getLabel("key_mon");
            case Calendar.TUESDAY:
                return RaApp.getLabel("key_tues");
            case Calendar.WEDNESDAY:
                return RaApp.getLabel("key_weds");
            case Calendar.THURSDAY:
                return RaApp.getLabel("key_thurs");
            case Calendar.FRIDAY:
                return RaApp.getLabel("key_fri");
            case Calendar.SATURDAY:
                return RaApp.getLabel("key_sat");
            case Calendar.SUNDAY:
                return RaApp.getLabel("key_sun");
            default:
                return "";
        }

    }

    public String getDateOfDay() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(Calendar.getInstance().getTime());
    }

    public void onCancelClick() {
        getView().onCancel();
    }

    public void onSaveClick() {
        getView().doSave(mDataItem);
    }

    public void setHours(int hours) {
        mDataItem.setHours(hours);
    }

    public void setMinutes(int minutes) {
        mDataItem.setMinutes(minutes);
    }

    public void setAm(int am) {
        mDataItem.setAm(am);
    }

    public void setInitialValues(DrinkAlarmItem value) {
        mDataItem = value;

    }
}
