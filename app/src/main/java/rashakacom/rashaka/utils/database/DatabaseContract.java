package rashakacom.rashaka.utils.database;

import android.provider.BaseColumns;

/**
 * Created by User on 22.08.2017.
 */

public class DatabaseContract {

    public static final class LabelEntry implements BaseColumns {
        public static final String LABEL_TABLE_NAME = "lang";
        public static final String LABEL_COLUMN_KEY = "key_word";
        public static final String LABEL_COLUMN_TITLE = "title";
    }
}
