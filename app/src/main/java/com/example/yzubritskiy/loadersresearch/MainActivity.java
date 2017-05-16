package com.example.yzubritskiy.loadersresearch;


import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yzubritskiy.loadersresearch.database.CarsTable;
import com.example.yzubritskiy.loadersresearch.database.DBManager;
import com.example.yzubritskiy.loadersresearch.database.OwnersTable;
import com.example.yzubritskiy.loadersresearch.model.Car;
import com.example.yzubritskiy.loadersresearch.model.MockDataHelper;
import com.example.yzubritskiy.loadersresearch.model.Owner;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "TAG_" + MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private Button mUpdateBtn, mInsertBtn, mDeleteBtn;
    private OwnersAdatper mAdapter;
    private DBManager mDBManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBManager = new DBManager(this);
        mDBManager.open();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new OwnersAdatper();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mUpdateBtn = (Button) findViewById(R.id.update_btn);
        mInsertBtn = (Button) findViewById(R.id.insert_btn);
        mDeleteBtn = (Button) findViewById(R.id.delete_btn);
        fillDataBase(mDBManager);
        getSupportLoaderManager().initLoader(1, null, this);

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Owner> owners = mAdapter.getData();
                if(owners != null && !owners.isEmpty()){
                    Random r = new Random();
                    mDBManager.deleteOwner(owners.get(r.nextInt(owners.size())));
                    getSupportLoaderManager().initLoader(1, null, MainActivity.this);}
            }
        });

        mInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRandomOwnerToDB();
                getSupportLoaderManager().initLoader(1, null, MainActivity.this);
            }
        });
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
//            OwnersTable.save(getApplicationContext(), owner);
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
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        CursorLoader cursorLoader = new CursorLoader(this, OwnersTable.URI, null,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished");
        cursor.moveToFirst();
        List<Owner> owners = new ArrayList<>();
        while (cursor.moveToNext()){
            Owner owner = new Owner();
            owner.setBirthDate(new Date(cursor.getLong(cursor.getColumnIndex(OwnersTable.Columns.BIRTH_DATE))));
            owner.setFirstName(cursor.getString(cursor.getColumnIndex(OwnersTable.Columns.FIRST_NAME)));
            owner.setSecondName(cursor.getString(cursor.getColumnIndex(OwnersTable.Columns.SECOND_NAME)));
            owner.setId(cursor.getLong(cursor.getColumnIndex(OwnersTable.Columns.ID)));
            owners.add(owner);

        }
        Log.d(TAG, "owners->"+owners.size());
        mAdapter.fillData(owners);
//        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
