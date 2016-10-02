package com.example.vipin.assignment3;

import android.provider.BaseColumns;

/**
 * Created by vipin on 2/10/16.
 */
public final class TaskDB {
    private TaskDB(){}

    public static class Task implements BaseColumns {
        public static final String TABLE_NAME = "Task Table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESC = "description";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Task.TABLE_NAME + " (" +
                    Task._ID + " INTEGER PRIMARY KEY," +
                    Task.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    Task.COLUMN_DESC + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Task.TABLE_NAME;
}
