package com.example.yzubritskiy.loadersresearch.activitis;


import android.content.Intent;
import android.database.Cursor;
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

import com.example.yzubritskiy.loadersresearch.adapters.OwnersAdatper;
import com.example.yzubritskiy.loadersresearch.R;
import com.example.yzubritskiy.loadersresearch.database.DBManager;
import com.example.yzubritskiy.loadersresearch.database.OwnersTable;
import com.example.yzubritskiy.loadersresearch.model.Car;
import com.example.yzubritskiy.loadersresearch.model.MockDataHelper;
import com.example.yzubritskiy.loadersresearch.model.Owner;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String TAG = "TAG_" + MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private OwnersAdatper mAdapter;
    private DBManager mDBManager;
    private Button mUpdateBtn, mInsertBtn, mDeleteBtn, mNextBtn;
    private static final int LOADER_ID = 909;
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
        mNextBtn = (Button) findViewById(R.id.custom_loader_activity_btn);
        fillDataBase(mDBManager);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        mNextBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mInsertBtn.setOnClickListener(this);
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
//        OwnersTable.save(getApplicationContext(), owner);

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
//        Log.d(TAG, "onLoadFinished +cursor.getCount()->"+cursor.getCount());

        cursor.moveToFirst();
        List<Owner> owners = new ArrayList<>();
        while (cursor.moveToNext()){
            Owner owner = OwnersTable.fromCursor(cursor);
            owners.add(owner);
        }
        Log.d(TAG, "owners->"+owners.size());
        mAdapter.fillData(owners);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_loader_activity_btn:
                Intent intent = new Intent(this, CustomLoaderActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_btn:
                List<Owner> owners = mAdapter.getData();
                if(owners != null && !owners.isEmpty()){
                    Random r = new Random();
                    mDBManager.deleteOwner(owners.get(r.nextInt(owners.size())));
                    getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);}
                break;
            case R.id.insert_btn:
                addRandomOwnerToDB();
                getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
                break;
            case R.id.update_btn:
                break;

        }
    }
}
