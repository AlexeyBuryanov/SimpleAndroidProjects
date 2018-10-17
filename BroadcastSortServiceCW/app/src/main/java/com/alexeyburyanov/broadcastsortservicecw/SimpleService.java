package com.alexeyburyanov.broadcastsortservicecw;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Alexey on 09.03.2018.
 */
public class SimpleService extends Service {

    final String LOG_TAG = "SortServiceCW";


    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "SimpleService onCreate");
    }
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "SimpleService onDestroy");
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "SimpleService onStartCommand");

        int[] array = intent.getIntArrayExtra("array");

        new android.os.Handler(getMainLooper()).post(() -> {
            Intent send = new Intent("sort array");
            Log.d(LOG_TAG, "SimpleService post start...");
            new QuickSort().Sort(array);
            send.putExtra("array", array);
            // Начало передачи
            sendBroadcast(send);
            stopSelfResult(0);
            Log.d(LOG_TAG, "SimpleService stop");
        });

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}