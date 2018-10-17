package com.example.alexey.timercontrol;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import java.util.Date;
import java.util.Timer;


public class TimerView extends android.support.v7.widget.AppCompatTextView
{
    private String _delimiter=":";
    /**Задача для таймера*/
    private TaskTimer _taskTimer;
    /**Таймер*/
    private Timer _timer;
    /**Время*/
    private Date _time;


    public TimerView(Context context) {
        super(context);
        _time = new Date(0, 0, 0, 0, 0, 11);
        _timer = new Timer();
        _taskTimer = new TaskTimer(_time, this);
    } // TimerView

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _time = new Date(0, 0, 0, 0, 0, 11);
        _timer = new Timer();
        _taskTimer = new TaskTimer(_time, this);

        // Загрузка набора атрибутов для пользовательского элемента управления
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
        final int N = a.getIndexCount();

        // Просмотр атрибутов
        for (int i = 0; i < N; ++i) {
            // Получение определённого атрибута по номеру
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TimerView_delimiter:
                    _delimiter = a.getString(attr);
                    updateTime();
                    break;
            } // switch
        } // for
        a.recycle();
    } // TimerView

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _time = new Date(0, 0, 0, 0, 0, 11);
        _timer = new Timer();
        _taskTimer = new TaskTimer(_time, this);
    } // TimerView


    public void updateTime() {
        String time = String.format("%d%s%d%s%d", _time.getHours(), _delimiter, _time.getMinutes(), _delimiter, _time.getSeconds());
        setText(time);
    } // updateTime


    public void setForeground(int color) {
        setBackgroundColor(color);
    }


    public void setTime(int hh, int mm, int ss) {
        _time = new Date(0, 0,0, hh, mm, ss);
    } // setTime
    public void setTime(long time) {
        _time.setTime(time);
    } // setTime


    public void startTimer() {
        _timer.schedule(_taskTimer, 100, 1000);
    } // startTimer


    public void stopTimer() {
        _timer.cancel();
    } // stopTimer


    public void pauseTimer() {

    } // pauseTimer


    public void continueTimer() {

    } // continueTimer
}