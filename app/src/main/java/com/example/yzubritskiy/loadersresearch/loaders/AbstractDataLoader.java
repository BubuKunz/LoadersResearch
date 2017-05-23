package com.example.yzubritskiy.loadersresearch.loaders;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

/**
 * Created by yzubritskiy on 5/22/2017.
 */

public abstract  class AbstractDataLoader <E> extends AsyncTaskLoader <List<E>> {
    private LoaderBroadcastReceiver loaderBroadcastReceiver = null;

    protected List<E> mLastDataList = null;
    protected abstract List<E> buildList();
    public AbstractDataLoader(Context context) {
        super(context);
    }

    /**
     * Runs on a worker thread, loading in our data. Delegates the real work to
     * concrete subclass' buildCursor() method.
     */
    @Override
    public List<E> loadInBackground() {
        return buildList();
    }
    /**
     * Runs on the UI thread, routing the results from the background thread to
     * whatever is using the dataList.
     */
    @Override
    public void deliverResult(List<E> dataList) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            emptyDataList(dataList);
            return;
        }
        List<E> oldDataList = mLastDataList;
        mLastDataList = dataList;
        if (isStarted()) {
//            if (oldDataList != null && !oldDataList.equals(dataList)) {
//                onNewDataDelivered(dataList);
//            }
            super.deliverResult(dataList);
        }
        if (oldDataList != null && oldDataList != dataList
                && oldDataList.size() > 0) {
            emptyDataList(oldDataList);
        }
    }

//    protected void onNewDataDelivered(List<E> data) {
//    }
    /**
     * Starts an asynchronous load of the list data. When the result is ready
     * the callbacks will be called on the UI thread. If a previous load has
     * been completed and is still valid the result may be passed to the
     * callbacks immediately.
     *
     * Must be called from the UI thread.
     */
    @Override
    protected void onStartLoading() {
        if (mLastDataList != null) {
            deliverResult(mLastDataList);
        }
        if (takeContentChanged() || mLastDataList == null
                || mLastDataList.size() == 0) {
            forceLoad();
        }
        if(loaderBroadcastReceiver == null)
        {
            loaderBroadcastReceiver = new LoaderBroadcastReceiver(this);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(loaderBroadcastReceiver, new IntentFilter("NEWDATASTRING"));
        }
    }
    /**
     * Must be called from the UI thread, triggered by a call to stopLoading().
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }
    /**
     * Must be called from the UI thread, triggered by a call to cancel(). Here,
     * we make sure our Cursor is closed, if it still exists and is not already
     * closed.
     */
    @Override
    public void onCanceled(List<E> dataList) {
        if (dataList != null && dataList.size() > 0) {
            emptyDataList(dataList);
        }
    }
    /**
     * Must be called from the UI thread, triggered by a call to reset(). Here,
     * we make sure our Cursor is closed, if it still exists and is not already
     * closed.
     */
    @Override
    protected void onReset() {
        super.onReset();
        // Ensure the loader is stopped
        onStopLoading();
        if (mLastDataList != null && mLastDataList.size() > 0) {
            emptyDataList(mLastDataList);
        }
        mLastDataList = null;
    }
    protected void emptyDataList(List<E> dataList) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.remove(i);
            }
        }
    }

}
