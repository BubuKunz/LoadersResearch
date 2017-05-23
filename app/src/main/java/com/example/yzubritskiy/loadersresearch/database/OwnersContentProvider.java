package com.example.yzubritskiy.loadersresearch.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yzubritskiy on 5/16/2017.
 */

public class OwnersContentProvider extends ContentProvider {
    private static  String TAG = "TAG_"+OwnersContentProvider.class.getSimpleName();
    private DBHelper mDBHelper;
    private static final int OWNERS_TABLE = 1;
    private static final int CARS_TABLE = 2;


    private static final String BASE_PATH = OwnersTable.Requests.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + DBHelper.CONTENT_AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/owners";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/owners";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);;

    static {

        URI_MATCHER.addURI(DBHelper.CONTENT_AUTHORITY, OwnersTable.Requests.TABLE_NAME, OWNERS_TABLE);
        URI_MATCHER.addURI(DBHelper.CONTENT_AUTHORITY, CarsTable.Requests.TABLE_NAME, CARS_TABLE);
    }
    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query uri->"+uri+", projection->"+projection+", selection->"+selection+", selectionArgs->"+selectionArgs+",sortOrder->"+sortOrder);
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        } else {
            // make sure that potential listeners are getting notified
            Cursor cursor = database.query(table, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent("NEWDATASTRING"));
            return cursor;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType uri->"+uri+", URI_MATCHER.match(uri)->"+URI_MATCHER.match(uri));
        switch (URI_MATCHER.match(uri)) {
            case OWNERS_TABLE:
                return OwnersTable.Requests.TABLE_NAME;
            case CARS_TABLE:
                return CarsTable.Requests.TABLE_NAME;
            default:
                return "";
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "insert uri->"+uri+", values->"+values);
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String table = getType(uri);
        Log.d(TAG, "insert table->"+table);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            long id = database.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            getContext().getContentResolver().notifyChange(uri, null);
            Uri result = Uri.parse(BASE_PATH + "/" + id);
            Log.d(TAG, "inserted result->"+result+", uri->"+uri);
            return result;
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        Log.d(TAG, "bulkInsert");
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            int numInserted = 0;
            database.beginTransaction();
            try {
                for (ContentValues contentValues : values) {
                    long id = database.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    if (id > 0) {
                        numInserted++;
                    }
                }
                database.setTransactionSuccessful();
            }
            finally {
                database.endTransaction();
            }
            return numInserted;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete");
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            int rowsDeleted = database.delete(table, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsDeleted;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      String selection, String[] selectionArgs) {
        Log.d(TAG, "update");
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            return database.update(table, values, selection, selectionArgs);
        }
    }
}
