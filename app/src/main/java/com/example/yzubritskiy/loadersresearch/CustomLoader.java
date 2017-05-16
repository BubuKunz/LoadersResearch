package com.example.yzubritskiy.loadersresearch;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;

/**
 * Created by yzubritskiy on 5/15/2017.
 */

public class CustomLoader extends Loader<Cursor> {

    public static int ID = 1001;
    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public CustomLoader(Context context) {
        super(context);
    }
}
