package com.example.yzubritskiy.loadersresearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yzubritskiy on 5/14/2017.
 */

public class OwnersTable {
    public static final Uri URI = DBHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static Uri save(Context context, @NonNull Owner owner) {
        return context.getContentResolver().insert(URI, toContentValues(owner));
    }

    public static void save(Context context, @NonNull List<Owner> owners) {
        ContentValues[] values = new ContentValues[owners.size()];
        for (int i = 0; i < owners.size(); i++) {
            values[i] = toContentValues(owners.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Owner owner) {
        ContentValues values = new ContentValues();
        values.put(Columns.FIRST_NAME, owner.getFirstName());
        values.put(Columns.SECOND_NAME, owner.getSecondName());
        values.put(Columns.BIRTH_DATE, owner.getBirthDate().getTime());
//        values.put(Columns.ID, owner.getId());
        return values;
    }

    @NonNull
    public static Owner fromCursor(@NonNull Cursor cursor) {
        String firstName = cursor.getString(cursor.getColumnIndex(Columns.FIRST_NAME));
        String secondName = cursor.getString(cursor.getColumnIndex(Columns.SECOND_NAME));
        Date birthDate = new Date(cursor.getLong(cursor.getColumnIndex(Columns.BIRTH_DATE)));
        long id = cursor.getLong(cursor.getColumnIndex(Columns.ID));
        return new Owner(firstName, secondName, birthDate, id);
    }

    @NonNull
    public static List<Owner> listFromCursor(@NonNull Cursor cursor) {
        List<Owner> owners = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return owners;
        }
        try {
            do {
                owners.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return owners;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String FIRST_NAME = "first_name";
        String SECOND_NAME = "second_name";
        String BIRTH_DATE = "birth_date";
        String ID = "_id";
    }

    public interface Requests {

        String TABLE_NAME = OwnersTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ID + " integer primary key autoincrement, " +
                Columns.FIRST_NAME + " VARCHAR(200) NOT NULL, " +
                Columns.SECOND_NAME + " VARCHAR(200) NOT NULL, " +
                Columns.BIRTH_DATE + " integer" + ");";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
