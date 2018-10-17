package com.alexeyburyanov.bindbroadcastcompress;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Alexey on 11.03.2018.
 */
public class SimpleService extends Service {

    private final String LOG_TAG = "BindBroadcastCompress";
    private SimpleBinder _binder = new SimpleBinder();

    class SimpleBinder extends Binder {
        SimpleService getService() {
            return SimpleService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "SimpleService onCreate()");
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "SimpleService onDestroy()");
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(LOG_TAG, "SimpleService onBind()");
        return _binder;
    }
    @Override
    public void onRebind(Intent intent) {
        Log.d(LOG_TAG, "SimpleService onRebind()");
        super.onRebind(intent);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "SimpleService onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "SimpleService onStartCommand()");

        Log.d(LOG_TAG, "SimpleService post start...");
        execute(intent);
        Log.d(LOG_TAG, "SimpleService stop");

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Главный метод по выполнению задачи.
     * Отправляет Broadcast сообщение по завершению.
     * */
    public void execute(Intent intent) {
        String path = intent.getStringExtra("path");

        new android.os.Handler(getMainLooper()).post(() -> {
            Intent send = new Intent("success");

            boolean success = false;
            try {
                // Компрессим файл
                ZipCompression.zip(new String[] { path },
                        Environment.getExternalStorageDirectory().getPath()+"/"+
                                Environment.DIRECTORY_MUSIC+"/"+
                                "/duran.zip");
                success = true;
            } catch (IOException e) {
                success = false;
                e.printStackTrace();
            } // try-catch

            // Отправка результата выполнения сжатия
            send.putExtra("success", success);
            sendBroadcast(send);
            stopSelfResult(0);
        });
    }
}