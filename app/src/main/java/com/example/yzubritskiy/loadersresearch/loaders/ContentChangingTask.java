package com.example.yzubritskiy.loadersresearch.loaders;

import android.os.AsyncTask;
import android.support.v4.content.Loader;

import com.example.yzubritskiy.loadersresearch.activitis.MainActivity;

/**
 * Created by yzubritskiy on 5/22/2017.
 */

public abstract class ContentChangingTask<T1, T2, T3> extends AsyncTask<T1, T2, T3> {
    private Loader<?> loader=null;
    ContentChangingTask(Loader<?> loader) {
        this.loader=loader;
    }
    @Override
    protected void onPostExecute(T3 param) {
        loader.onContentChanged();

    }
}
