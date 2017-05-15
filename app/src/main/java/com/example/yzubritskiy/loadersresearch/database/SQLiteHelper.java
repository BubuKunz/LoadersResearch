package com.example.yzubritskiy.loadersresearch.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by yzubritskiy on 5/14/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String CONTENT_AUTHORITY = "com.research.loaders";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String DATABASE_NAME = "com.research.loader.database.db";

    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CarsTable.Requests.CREATION_REQUEST);
        db.execSQL(OwnersTable.Requests.CREATION_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CarsTable.Requests.DROP_REQUEST);
        db.execSQL(OwnersTable.Requests.DROP_REQUEST);
        onCreate(db);
    }
}
