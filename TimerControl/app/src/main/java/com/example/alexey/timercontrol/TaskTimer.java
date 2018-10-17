package com.example.alexey.timercontrol;
import android.graphics.Color;
import java.util.Date;


public class TaskTimer extends java.util.TimerTask
{
    private Date _time;
    private TimerView _timerView;


    TaskTimer(Date time, TimerView timerView) {
        _time = time;
        _timerView = timerView;
    }


    @Override
    public void run() {
        _time = new Date(0, 0, 0, _time.getHours(), _time.getMinutes(), _time.getSeconds()-1);
        _timerView.setTime(_time.getTime());
        _timerView.updateTime();
        if (_time.getHours() == 0 && _time.getMinutes() == 0 && _time.getSeconds() == 0) {
            _timerView.setForeground(Color.GREEN);
            _timerView.stopTimer();
        } // if
    } // run
} // TaskTimer