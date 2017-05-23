package com.example.yzubritskiy.loadersresearch.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yzubritskiy.loadersresearch.R;
import com.example.yzubritskiy.loadersresearch.adapters.OwnersAdatper;
import com.example.yzubritskiy.loadersresearch.database.DBManager;
import com.example.yzubritskiy.loadersresearch.database.OwnersTable;
import com.example.yzubritskiy.loadersresearch.loaders.SQLiteTestDataLoader;
import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzubritskiy on 5/23/2017.
 */

public class CustomLoaderFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List>
{
    private static final String TAG = "TAG_" + CustomLoaderFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private OwnersAdatper mAdapter;
    public static final int LOADER_ID = 909;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.custom_loader_fragment_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new OwnersAdatper();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        SQLiteTestDataLoader loader = new SQLiteTestDataLoader(getContext(), new DBManager(getContext()), null, null, null, null, null);
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
        Log.d(TAG, "onLoaderReset");
    }
}
