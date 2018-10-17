package com.example.alexey.timercontrol;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;


/**
 Разработать элемент управления "Таймер", который наследует от готового контола Андроид.
 Таймер позволяет задать исходное время (часы, минуты и секунды) и каждую секунду тикает, пока время не истечёт.
 Когда время истекает, таймер как-то сигнализирует об этом.
 Методы:
 - setTime
 - start
 - stop
 - pause
 - continue

 Параметры:
 - delimiter
 - fontSize
 */
public class MainActivity extends AppCompatActivity
{
    private LinearLayout _linearLayoutMain;
    private TimerView _timerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _timerView = findViewById(R.id.TimerViewMain);
        _linearLayoutMain = findViewById(R.id.LinearLayoutMain);

        //_timerView.setTime(0, 5, 0);
        _timerView.startTimer();
    }
}