package com.example.yzubritskiy.loadersresearch;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.yzubritskiy.loadersresearch.database.OwnersTable;
import com.example.yzubritskiy.loadersresearch.model.MockDataHelper;
import com.example.yzubritskiy.loadersresearch.model.Owner;

/**
 * Created by yzubritskiy on 5/15/2017.
 */

public class DataReceivingService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    private void fillData(){
        for(int i = 0; i<10; i++){
            Owner owner = MockDataHelper.createRandomOwner();
            OwnersTable.save(getApplicationContext(), owner);
        }
    }


}
