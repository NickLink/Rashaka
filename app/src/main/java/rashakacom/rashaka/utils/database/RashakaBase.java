package rashakacom.rashaka.utils.database;

import java.util.List;

import rashakacom.rashaka.domain.LabelItem;
import rashakacom.rashaka.domain.login.UserLogin;
import rashakacom.rashaka.domain.profile.UserProfile;
import rashakacom.rashaka.fragments.main.plus.drink.DrinkAlarmItem;

/**
 * Created by User on 23.08.2017.
 */

public interface RashakaBase {

    void saveLangType(String type);
    String getLangType();

    void saveLabelList(List<LabelItem> labelList);

    void clearLabelTable();

    void clearLabelCache();

    String getLabelByKey(String key);

    String getCachedLabelByKey(String key);

    void setLoggedUser(UserLogin mData);
    UserLogin getLoggedUser();

    void setProfileUser(UserProfile mData);
    UserProfile getProfileUser();

    void saveAlarmList(List<DrinkAlarmItem> list);
    List<DrinkAlarmItem> loadAlarmList();
}
