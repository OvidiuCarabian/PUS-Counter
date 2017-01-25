package com.example.ovidiuworkstation.pushupscounter.database;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ovidiu Workstation on 1/5/2017.
 */

public class DatabasePush extends SQLiteOpenHelper
{
    public DatabasePush(Context context) {super(context,"pushups.db", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " +
                PushLink.PushEntry.TABLE_NAME + "(" +
                PushLink.PushEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PushLink.PushEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                PushLink.PushEntry.COLUMN_COUNT + " INTEGER NOT NULL, "+
                PushLink.PushEntry.COLUMN_CALORIES +" INTEGER NOT NULL, " +
                PushLink.PushEntry.COLUMN_DURATION + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE);

        String PARAMETERS_OPEN_SQL = "CREATE TABLE "+
                PushLink.Parameter.TABLE_NAME + " ("+
                PushLink.Parameter.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PushLink.Parameter.COLUMN_WEIGHT+ " REAL NOT NULL, "+
                PushLink.Parameter.COLUMN_HEIGHT+ " INTEGER NOT NULL);";

        db.execSQL(PARAMETERS_OPEN_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
