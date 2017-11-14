package com.rashaka.utils.database;

import com.rashaka.domain.LabelItem;
import com.rashaka.domain.database.ActivityItem;
import com.rashaka.domain.database.DailyItem;
import com.rashaka.domain.login.UserLogin;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.domain.steps.StepsInMinute;
import com.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

import java.util.List;

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

    void saveGsmToken(String tocken);
    String getGsmToken();

    //TODO LogSleep parts
    void saveSleepStartTime(String startTime);
    void saveSleepEndTime(String endTime);
    String getSleepStartTime();
    String getSleepEndTime();

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

    long getFirstStep();
    boolean deleteSteps(long from, long to);

    //TODO Sync data part
    boolean saveActivityItem(ActivityItem item);
    boolean deleteActivityItem(ActivityItem item);
    ActivityItem getActivityItemById(int id);
    List<ActivityItem> getActivityItemsByDate(String date);

    boolean saveDailySteps(DailyItem item);
    boolean deleteDailySteps(DailyItem item);
    DailyItem getDailyItemByDate(String date);
    List<DailyItem> getDailyItems();
    List<DailyItem> getDailyItems(int sync);


    void clearAllTables();
}
