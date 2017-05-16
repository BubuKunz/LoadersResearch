package com.example.yzubritskiy.loadersresearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yzubritskiy.loadersresearch.MainActivity;
import com.example.yzubritskiy.loadersresearch.model.Car;
import com.example.yzubritskiy.loadersresearch.model.Owner;

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
            ContentValues carValues = CarsTable.toContentValues(car);
            car.setOwnerId(id);
            m_DB.insert(CarsTable.Requests.TABLE_NAME, null, carValues);
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
            ContentValues carValues = CarsTable.toContentValues(car);
            car.setOwnerId(owner.getId());
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

    public void clearAllData(){
        m_DBHelper.deleteAllTables(m_DB);
    }
}
