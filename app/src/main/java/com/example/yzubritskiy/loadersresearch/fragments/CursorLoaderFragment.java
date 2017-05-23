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
import com.example.yzubritskiy.loadersresearch.database.OwnersContentProvider;
import com.example.yzubritskiy.loadersresearch.database.OwnersTable;
import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzubritskiy on 5/23/2017.
 */

public class CursorLoaderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "TAG_" + CursorLoaderFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private OwnersAdatper mAdapter;
    public static final int LOADER_ID = 908;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.cursor_loader_fragment_layout, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new OwnersAdatper();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        CursorLoader cursorLoader = new CursorLoader(getContext(), OwnersContentProvider.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null){
            Log.d(TAG, "onLoadFinished +cursor.getCount()->"+cursor.getCount());
            cursor.moveToFirst();
            List<Owner> owners = new ArrayList<>();
            while (cursor.moveToNext()){
                Owner owner = OwnersTable.fromCursor(cursor);
                owners.add(owner);
            }
            Log.d(TAG, "owners->"+owners.size());
            mAdapter.fillData(owners);
        }


    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.d(TAG, "onLoaderReset->");

    }
}
