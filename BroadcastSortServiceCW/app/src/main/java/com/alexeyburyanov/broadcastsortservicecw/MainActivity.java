package com.alexeyburyanov.broadcastsortservicecw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Сервис принимает массив случайных чисел от активности, сортирует его каким-либо
 * методом и возвращает в активность отсортированный массив при помощи Broadcast
 * сообщения. Активность выводит массив в логи.
 * */
public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "SortServiceCW";
    // Получатель сообщений
    BroadcastReceiver broadcastReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создание обработчика сообщений
        broadcastReciever = new BroadcastReceiver() {
            // Метод получателя
            public void onReceive(Context context, Intent intent) {
                // Получение параметра из сервиса
                int[] array = intent.getIntArrayExtra("array");
                for (int i = 0; i < array.length; i++) {
                    Log.d(LOG_TAG, "onReceive: array index " + i + " == " + array[i]);
                } // for i
            } // onReceive
        };

        // Регистрация получателя
        registerReceiver(broadcastReciever, new IntentFilter("sort array"));

        // Старт сервиса с передачей параметров
        new android.os.Handler(getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, SimpleService.class);
            intent.putExtra("array", new int[] {
                    1, 2595, 857, 75712, 5872, 75, 7526, 8527, 8561, 85, 8237, 57235, 52, 852,
                    5832, 85, 825, 876, 9283, 8572, 852, 9682, 8162, 857, 8572, 8572, 2
            });
            startService(intent);
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Отмена регистрации
        unregisterReceiver(broadcastReciever);
    }
}