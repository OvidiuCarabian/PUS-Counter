package com.example.ovidiuworkstation.pushupscounter.database;

/**
 * Created by Ovidiu Workstation on 1/5/2017.
 */
import android.net.Uri;
import android.provider.BaseColumns;


public final class PushLink implements BaseColumns {
    public final static String AUTHORITY = "com.example.ovidiuworkstation.pushupscounter";
    public final static Uri BASE_URI = Uri.parse("content://"+ AUTHORITY);
    public final static String PATH_PUSH = "pushes";
    public final static String PATH_PARAMETERS = "parameters";

    //empty constructor
    private PushLink(){};

    //entry class for sql table
    public static class PushEntry{
        public static final String TABLE_NAME = PATH_PUSH;
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_DATE ="date";
        public static final String COLUMN_COUNT = "count";
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_DURATION = "duration";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,PATH_PUSH);
    }

    //output class for settings params
    public static class Parameter{
        public static final String TABLE_NAME = PATH_PARAMETERS;
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_HEIGHT = "height";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,PATH_PARAMETERS);
    }
}