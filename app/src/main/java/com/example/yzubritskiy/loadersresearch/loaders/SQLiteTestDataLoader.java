package com.example.yzubritskiy.loadersresearch.loaders;

import android.content.Context;
import android.util.Log;

import com.example.yzubritskiy.loadersresearch.database.DBManager;
import com.example.yzubritskiy.loadersresearch.model.Owner;

import java.util.List;

/**
 * Created by yzubritskiy on 5/22/2017.
 */

public class SQLiteTestDataLoader extends AbstractDataLoader<List> {
    private static final String TAG = "TAG_" + ContentChangingTask.class.getSimpleName();


    private DBManager mDBManager;
    private String mSelection;
    private String[] mSelectionArgs;
    private String mGroupBy;
    private String mHaving;
    private String mOrderBy;

    public SQLiteTestDataLoader(Context context, DBManager dbManager, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        super(context);
        mDBManager = dbManager;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mGroupBy = groupBy;
        mHaving = having;
        mOrderBy = orderBy;
    }

    @Override
    protected List buildList() {
        Log.d(TAG, "buildList");
        List testList = mDBManager. getAllOwners();
        return testList;
    }
    public void insert(Owner entity) {
        Log.d(TAG, "insert");

        new InsertTask(this).execute(entity);
    }
    public void update(Owner entity) {
        Log.d(TAG, "update");
        new UpdateTask(this).execute(entity);
    }
    public void delete(Owner entity) {
        Log.d(TAG, "delete");
        new DeleteTask(this).execute(entity);
    }
    private class InsertTask extends ContentChangingTask<Owner, Void, Void> {
        InsertTask(SQLiteTestDataLoader loader) {
            super(loader);
        }
        @Override
        protected Void doInBackground(Owner... params) {
            Log.d(TAG, "InsertTask");
            mDBManager.addOwner(params[0]);
            return (null);
        }
    }
    private class UpdateTask extends ContentChangingTask<Owner, Void, Void> {
        UpdateTask(SQLiteTestDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Owner... params) {
            Log.d(TAG, "UpdateTask");
            mDBManager.updateOwner(params[0]);
            return (null);
        }
    }
    private class DeleteTask extends ContentChangingTask<Owner, Void, Void> {

        DeleteTask(SQLiteTestDataLoader loader) {
            super(loader);
        }
        @Override
        protected Void doInBackground(Owner... params) {
            Log.d(TAG, "DeleteTask");
            mDBManager.deleteOwner(params[0]);
            return (null);
        }
    }
}
