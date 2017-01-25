package com.example.ovidiuworkstation.pushupscounter.database;

/**
 * Created by Ovidiu Workstation on 1/5/2017.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

import static com.example.ovidiuworkstation.pushupscounter.database.PushLink.*;


public class MaintainanceDB extends ContentProvider {

    private DatabasePush pushDb;
    private static final int PUSH_ALL_ITEMS = 1;
    private static final int PUSH_SINGLE = 2;
    private static final int PARAMETER_ALL_ITEMS = 3;
    private static final int PARAMETER_SINGLE = 4;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PATH_PUSH, PUSH_ALL_ITEMS);
        uriMatcher.addURI(AUTHORITY, PATH_PUSH + "/#", PUSH_SINGLE);
        uriMatcher.addURI(AUTHORITY, PATH_PARAMETERS, PARAMETER_ALL_ITEMS);
        uriMatcher.addURI(AUTHORITY, PATH_PARAMETERS + "/#", PARAMETER_SINGLE);
    }

    @Override
    public boolean onCreate() {
        pushDb = new DatabasePush(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = uriMatcher.match(uri);
        Cursor cursor = null;
        SQLiteDatabase database = pushDb.getReadableDatabase();
        switch (match)
        {
            case PUSH_ALL_ITEMS:
                projection = new String[]{
                        PushEntry.COLUMN_ID,
                        PushEntry.COLUMN_DATE,
                        PushEntry.COLUMN_COUNT,
                        PushEntry.COLUMN_CALORIES,
                        PushEntry.COLUMN_DURATION
                };

                cursor = database.query(PushEntry.TABLE_NAME, projection, null, null, null, null, null, null);
                break;

            case PUSH_SINGLE:
                projection = new String[]{
                        PushEntry.COLUMN_ID,
                        PushEntry.COLUMN_DATE,
                        PushEntry.COLUMN_COUNT,
                        PushEntry.COLUMN_CALORIES,
                        PushEntry.COLUMN_DURATION
                };
                selection = PushEntry.COLUMN_ID + " =?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PushEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;

            case PARAMETER_ALL_ITEMS:
                projection = new String[]{Parameter.COLUMN_ID, Parameter.COLUMN_WEIGHT, Parameter.COLUMN_HEIGHT};
                cursor = database.query(Parameter.TABLE_NAME, projection, null, null, null, null, null);
                break;

            case PARAMETER_SINGLE:
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long result = 0;
        int match = uriMatcher.match(uri);
        SQLiteDatabase database = pushDb.getWritableDatabase();
        switch (match) {
            case PUSH_ALL_ITEMS:
                result = database.insert(PushEntry.TABLE_NAME, null, values);
                break;
            case PARAMETER_ALL_ITEMS:
                result = database.insert(Parameter.TABLE_NAME, null, values);
                //Toast.makeText(getContext(),"id"+ result, Toast.LENGTH_SHORT).show();
                break;
        }
        if (result != 0) getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, result);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        int result = 0;
        if (match == PUSH_SINGLE) {
            selection = PushEntry.COLUMN_ID + " =?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            SQLiteDatabase database = pushDb.getWritableDatabase();
            result = database.delete(PushEntry.TABLE_NAME, selection, selectionArgs);
            if (result != 0) {
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        if (match == PARAMETER_SINGLE) {
            SQLiteDatabase database = pushDb.getWritableDatabase();
            selection = Parameter.COLUMN_ID + " =?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            int result = database.update(Parameter.TABLE_NAME, values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return result;
        }
        return 0;
    }
}