package com.example.yzubritskiy.loadersresearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yzubritskiy.loadersresearch.model.Car;
import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzubritskiy on 5/15/2017.
 */

public class DBManager {
    private static final String TAG = "TAG_" + DBManager.class.getSimpleName();
    public DBHelper m_DBHelper;
    private SQLiteDatabase m_DB;

    public DBManager(Context context){
        m_DBHelper = new DBHelper(context);
    }

    public void open() {
        m_DB = m_DBHelper.getWritableDatabase();
    }

    public void close() {
        if (m_DBHelper != null)
            m_DBHelper.close();
    }

    public boolean isOpen() {
        if (m_DB != null) {
            if (m_DB.isOpen()) {
                return true;
            }
        }
        return false;
    }

    public long addOwner(Owner owner){
        open();
        ContentValues contentValues = OwnersTable.toContentValues(owner);
        long id = m_DB.insert(OwnersTable.Requests.TABLE_NAME, null, contentValues);
        for(Car car:owner.getCars()){
            car.setOwnerId(id);
            ContentValues carValues = CarsTable.toContentValues(car);
            long carId =  m_DB.insert(CarsTable.Requests.TABLE_NAME, null, carValues);
            Log.d(TAG, "car OwnerId->"+id+", owner carId->"+carId);
        }
        close();
        Log.d(TAG, "addOwner id->"+id);
        return id;
    }

    public int updateOwner(Owner owner){
        open();
        ContentValues contentValues = OwnersTable.toContentValues(owner);
        int rowsNumber = m_DB.update(OwnersTable.Requests.TABLE_NAME, contentValues, OwnersTable.Columns.ID + " = " + owner.getId(), null);
        for(Car car:owner.getCars()){
            car.setOwnerId(owner.getId());
            ContentValues carValues = CarsTable.toContentValues(car);
            int count = m_DB.update(OwnersTable.Requests.TABLE_NAME, contentValues, OwnersTable.Columns.ID + " = " + owner.getId(), null);
            if(count<0){
                m_DB.insert(CarsTable.Requests.TABLE_NAME, null, carValues);
            }
        }
        close();
        return rowsNumber;
    }

    public void deleteOwner(Owner owner){
        open();
        int count = m_DB.delete(OwnersTable.Requests.TABLE_NAME, OwnersTable.Columns.ID + " = " + owner.getId(), null);
        Log.d(TAG, "count->"+count+", owner id->"+owner.getId());
        if(owner.getCars()!=null){
            for(Car car:owner.getCars()){
                m_DB.delete(CarsTable.Requests.TABLE_NAME, OwnersTable.Columns.ID + " = " + car.getId(), null);
            }
        }
        close();
    }

    public List<Owner> getAllOwners(){
        open();
        Cursor cursor = m_DB.query(OwnersTable.Requests.TABLE_NAME, null, null, null, null, null, null);
        List<Owner> owners = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Owner owner = OwnersTable.fromCursor(cursor);
            owner.setCars(getOwnersCars(owner.getId()));
            owners.add(owner);
        }
        cursor.close();
        close();
        return owners;
    }

    public void clearAllData(){
        m_DBHelper.deleteAllTables(m_DB);
    }

    public List<Car> getOwnersCars(long ownerId){
        String[] arrColumns = new String[]{CarsTable.Columns.ID,
                CarsTable.Columns.NUMBER,
                CarsTable.Columns.OWNER_ID,
                CarsTable.Columns.MODEL,
                CarsTable.Columns.YEAR};
        String strWhere = CarsTable.Columns.OWNER_ID + "=?";
        Cursor cursor = m_DB.query(CarsTable.Requests.TABLE_NAME, arrColumns, strWhere,
                new String[] { Long.toString(ownerId) }, null, null, null);
        List<Car> cars = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            cars.add(CarsTable.fromCursor(cursor));
        }
        Log.d(TAG, "getOwnersCars OwnerId->"+ownerId+", cars.size"+cars.size()+", cursor->"+cursor.getCount());

        cursor.close();
        return cars;
    }
}
