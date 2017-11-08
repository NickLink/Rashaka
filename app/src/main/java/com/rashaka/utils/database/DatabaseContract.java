package com.rashaka.utils.database;

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

    public static final class DailyStepEntry implements BaseColumns {
        public static final String DAILY_TABLE_NAME = "daily";
        public static final String DAILY_COLUMN_STEPS = "steps";
        public static final String DAILY_COLUMN_DATE = "date";
        public static final String DAILY_COLUMN_ACTI = "acti";
        public static final String DAILY_COLUMN_SYNC = "sync";
    }

    public static final class ActivityEntry implements BaseColumns {
        public static final String ACTIVITY_TABLE_NAME = "activity";
        public static final String ACTIVITY_COLUMN_HOST = "host";
        public static final String ACTIVITY_COLUMN_SEC = "active_sec";
        public static final String ACTIVITY_COLUMN_STEPS = "steps";
        public static final String ACTIVITY_COLUMN_TYPE = "type";
        public static final String ACTIVITY_COLUMN_DATE = "date";
    }

}
