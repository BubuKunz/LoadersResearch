package com.example.yzubritskiy.loadersresearch.activitis;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.yzubritskiy.loadersresearch.adapters.OwnersAdatper;
import com.example.yzubritskiy.loadersresearch.R;
import com.example.yzubritskiy.loadersresearch.database.DBManager;
import com.example.yzubritskiy.loadersresearch.loaders.SQLiteTestDataLoader;

import java.util.List;

public class CustomLoaderActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List>{
    private static final String TAG = "TAG_" + CustomLoaderActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;
    private RecyclerView mRecyclerView;
    private OwnersAdatper mAdapter;
    private DBManager mDBManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_loader);
        mDBManager = new DBManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new OwnersAdatper();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        SQLiteTestDataLoader loader = new SQLiteTestDataLoader(this, mDBManager, null, null, null, null, null);
        Log.d(TAG, "onCreateLoader id->"+id+", loader->"+loader);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List data) {
        Log.d(TAG, "onLoadFinished loader->"+loader+", data->"+data.size());
        mAdapter.fillData(data);
    }

    @Override
    public void onLoaderReset(Loader<List> loader) {

    }
}
