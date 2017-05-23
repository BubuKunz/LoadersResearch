package com.example.yzubritskiy.loadersresearch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.yzubritskiy.loadersresearch.fragments.CursorLoaderFragment;
import com.example.yzubritskiy.loadersresearch.fragments.CustomLoaderFragment;

import java.util.List;

/**
 * Created by yzubritskiy on 5/23/2017.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {


    private static final int NUM_PAGES = 2;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CursorLoaderFragment();
            case 1:
                return new CustomLoaderFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
