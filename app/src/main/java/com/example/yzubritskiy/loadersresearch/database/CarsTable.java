package com.example.yzubritskiy.loadersresearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.yzubritskiy.loadersresearch.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzubritskiy on 5/14/2017.
 */

public class CarsTable {
    public static final Uri URI = DBHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull Car car) {
        context.getContentResolver().insert(URI, toContentValues(car));
    }

    public static void save(Context context, @NonNull List<Car> cars) {
        ContentValues[] values = new ContentValues[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            values[i] = toContentValues(cars.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Car car) {
        ContentValues values = new ContentValues();
        values.put(Columns.NUMBER, car.getNumber());
        values.put(Columns.YEAR, car.getYear());
        values.put(Columns.MODEL, car.getModel());
        values.put(Columns.OWNER_ID, car.getOwnerId());
//        values.put(Columns.ID, car.getId());
        return values;
    }

    @NonNull
    public static Car fromCursor(@NonNull Cursor cursor) {
        int number = cursor.getInt(cursor.getColumnIndex(Columns.NUMBER));
        int year = cursor.getInt(cursor.getColumnIndex(Columns.YEAR));
        String model = cursor.getString(cursor.getColumnIndex(Columns.MODEL));
        long ownerId = cursor.getLong(cursor.getColumnIndex(Columns.OWNER_ID));
        long id = cursor.getLong(cursor.getColumnIndex(Columns.ID));
        return new Car(number, year, model, ownerId, id);
    }

    @NonNull
    public static List<Car> listFromCursor(@NonNull Cursor cursor) {
        List<Car> cars = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return cars;
        }
        try {
            do {
                cars.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return cars;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String NUMBER = "number";
        String YEAR = "mOwnersSecondName";
        String MODEL = "model";
        String OWNER_ID = "owner_id";
        String ID = "_id";
    }

    public interface Requests {

        String TABLE_NAME = CarsTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ID + " integer primary key autoincrement, " +
                Columns.OWNER_ID + " integer, " +
                Columns.NUMBER + " integer, " +
                Columns.YEAR + " integer, " +
                Columns.MODEL + " VARCHAR(200)" + ");";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
