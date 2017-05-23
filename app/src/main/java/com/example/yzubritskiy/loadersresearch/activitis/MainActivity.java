package com.example.yzubritskiy.loadersresearch.activitis;


import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yzubritskiy.loadersresearch.adapters.MyPagerAdapter;
import com.example.yzubritskiy.loadersresearch.R;
import com.example.yzubritskiy.loadersresearch.database.DBHelper;
import com.example.yzubritskiy.loadersresearch.database.DBManager;
import com.example.yzubritskiy.loadersresearch.database.OwnersContentProvider;
import com.example.yzubritskiy.loadersresearch.database.OwnersTable;
import com.example.yzubritskiy.loadersresearch.model.Car;
import com.example.yzubritskiy.loadersresearch.model.MockDataHelper;
import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "TAG_" + MainActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private DBManager mDBManager;
    private Button mFillDataBtn, mInsertBtn, mDeleteBtn, mNextBtn;
    public static final int LOADER_ID = 907;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mDBManager = new DBManager(this);
        mDBManager.open();

        mFillDataBtn = (Button) findViewById(R.id.fill_data_btn);
        mInsertBtn = (Button) findViewById(R.id.insert_btn);
        mDeleteBtn = (Button) findViewById(R.id.delete_btn);
        mNextBtn = (Button) findViewById(R.id.custom_loader_activity_btn);


        mNextBtn.setOnClickListener(this);
        mFillDataBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mInsertBtn.setOnClickListener(this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBManager.close();
    }

    private void fillDataBase(DBManager db) {
        Log.d(TAG, "fillDataBase");
        db.clearAllData();
        for (int i = 0; i < 10; i++) {
            addRandomOwnerToDB();
        }
    }

    private void addRandomOwnerToDB(){
        Owner owner = MockDataHelper.createRandomOwner();
        List<Car> cars = new ArrayList<>();
        cars.add(MockDataHelper.createRandomCar());
        cars.add(MockDataHelper.createRandomCar());
        owner.setCars(cars);
        mDBManager.addOwner(owner);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_loader_activity_btn:
                Intent intent = new Intent(this, CustomLoaderActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_btn:
                getContentResolver().delete(OwnersContentProvider.CONTENT_URI, null, null);
                break;
            case R.id.insert_btn:
                Owner owner = MockDataHelper.createRandomOwner();
                List<Car> cars = new ArrayList<>();
                cars.add(MockDataHelper.createRandomCar());
                cars.add(MockDataHelper.createRandomCar());
                owner.setCars(cars);

                getContentResolver().insert(OwnersContentProvider.CONTENT_URI, OwnersTable.toContentValues(owner));
                break;
            case R.id.fill_data_btn:
                fillDataBase(mDBManager);
                break;

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        CursorLoader cursorLoader = new CursorLoader(this, OwnersContentProvider.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null) {
            Log.d(TAG, "onLoadFinished +cursor.getCount()->" + data.getCount());
            data.moveToFirst();
            List<Owner> owners = new ArrayList<>();
            while (data.moveToNext()){
                Owner owner = OwnersTable.fromCursor(data);
                owners.add(owner);
            }
            Log.d(TAG, "owners->"+owners.size());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset->");

    }
}
