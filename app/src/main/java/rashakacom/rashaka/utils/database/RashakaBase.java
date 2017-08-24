package rashakacom.rashaka.utils.database;

import java.util.List;

import rashakacom.rashaka.utils.rest.models.LabelItem;
import rashakacom.rashaka.utils.rest.models.LoginData;

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

    void saveLoggedUser(LoginData mData);
    LoginData loadLoggedUser();
}
