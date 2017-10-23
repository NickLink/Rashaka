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

    public static final class StepsEntry implements BaseColumns {
        public static final String STEPS_TABLE_NAME = "steps";
        public static final String STEPS_COLUMN_TIME = "key_time";
        public static final String STEPS_COLUMN_STEPS = "key_steps";
    }

    public static final class StepEntry implements BaseColumns {
        public static final String STEP_TABLE_NAME = "step";
        public static final String STEP_COLUMN_TIME = "key_time";
    }

}
