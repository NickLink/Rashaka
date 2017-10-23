package rashakacom.rashaka.utils.database;

import java.util.List;

import rashakacom.rashaka.domain.LabelItem;
import rashakacom.rashaka.domain.login.UserLogin;
import rashakacom.rashaka.domain.profile.UserProfile;
import rashakacom.rashaka.domain.steps.StepsInMinute;
import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

/**
 * Created by User on 23.08.2017.
 */

public interface RashakaBase {

    //TODO Language part
    void saveLangType(String type);
    String getLangType();

    void saveLabelList(List<LabelItem> labelList);

    void clearLabelTable();
    void clearLabelCache();

    String getLabelByKey(String key);
    String getCachedLabelByKey(String key);

    //TODO User Prefs part
    boolean isUserLogged();
    void removeLoggedUser();
    void removeAllUserData();

    void setLoggedUser(UserLogin mData);
    UserLogin getLoggedUser();

    void setProfileUser(UserProfile mData);
    UserProfile getProfileUser();

    void saveLoggedEmail(String email);
    String getLoggedEmail();

    //TODO Alarms list part
    void saveAlarmList(List<DrinkAlarmItem> list);
    List<DrinkAlarmItem> loadAlarmList();

    //TODO Steps activity part
    void saveStepsInMinute(StepsInMinute stepsInMinute);
    List<StepsInMinute> getSteps();

    void saveStep(long time);
    long getStepsCount();
    long getStepsCount(long from);
    long getStepsCount(long from, long to);
}
