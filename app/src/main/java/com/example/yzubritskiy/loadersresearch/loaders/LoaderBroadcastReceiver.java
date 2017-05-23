package com.example.yzubritskiy.loadersresearch.loaders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yzubritskiy.loadersresearch.database.DBManager;

/**
 * Created by yzubritskiy on 5/23/2017.
 */

public class LoaderBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = "TAG_" + LoaderBroadcastReceiver.class.getSimpleName();
    private AbstractDataLoader loader;

    public LoaderBroadcastReceiver(AbstractDataLoader loader)
    {
        this.loader = loader;
    }

    @Override
    public void onReceive(Context context, Intent intent)    {
        Log.d(TAG, "onReceive intent->"+intent);
        loader.onContentChanged();
    }
}
