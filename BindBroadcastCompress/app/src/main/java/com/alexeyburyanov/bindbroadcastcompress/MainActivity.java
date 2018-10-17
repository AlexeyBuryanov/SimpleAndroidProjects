package com.alexeyburyanov.bindbroadcastcompress;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 1. Сервис получает в качестве параметра путь к файлу в файловой системе и создаёт
 * его сжатую копию по тому же пути. По окончании сжатия сервис уведомляет
 * активность при помощи Broadcast-сообщения. Задания на сжатие сервис получает
 * при помощи привязки по инициативе активности.
 * */
public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "BindBroadcastCompress";

    private BroadcastReceiver _broadcastReciever;
    private ServiceConnection _servCon;
    private Intent _servIntent;
    private SimpleService _service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _servCon = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                Log.d(LOG_TAG, "onServiceConnected()");
                // По подключению получаем ссылку на сервис
                _service = ((SimpleService.SimpleBinder)binder).getService();

                // Выполняем метод по текущему заданию на сервисе
                Log.d(LOG_TAG, "execute from activity post start...");
                _service.execute(_servIntent);
                Log.d(LOG_TAG, "execute from activity post stop.");
            }
            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                Log.d(LOG_TAG, "Service onServiceDisconnected()");
            }
        };

        // Получение Broadcast-сообщения из сервиса
        _broadcastReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                boolean success = intent.getBooleanExtra("success", false);
                if (success) {
                    Log.d(LOG_TAG, "onReceive(): compress success");
                } else {
                    Log.d(LOG_TAG, "onReceive(): compress fail");
                } // if-else
            } // onReceive
        };

        // Регистрация получателя
        registerReceiver(_broadcastReciever, new IntentFilter("success"));
    }

    @Override
    protected void onDestroy() {
        // Отмена регистрации
        unregisterReceiver(_broadcastReciever);
        super.onDestroy();
    }

    public void onPressButton(View view) {
        new android.os.Handler(getMainLooper()).postDelayed(() -> {
            // Проверка на права
            if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
            } // if

            // Создаём Intent с параметром пути к файлу
            Intent intent = new Intent(this, SimpleService.class);
            intent.putExtra("path",
                    Environment.getExternalStorageDirectory().getPath()+"/"+
                            Environment.DIRECTORY_MUSIC+"/"+
                            "/duranduran.mp3");
            // Для дальнейшей работы сохраняем его в поле
            _servIntent = intent;
            // Привязываемся к сервису
            bindService(intent, _servCon, BIND_AUTO_CREATE);
        }, 1000);
    }
}