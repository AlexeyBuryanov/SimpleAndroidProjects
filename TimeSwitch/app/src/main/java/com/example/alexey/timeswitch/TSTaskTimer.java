/**
 * Created by Alexey on 29.11.2017.
 */

package com.example.alexey.timeswitch;
import android.widget.TextSwitcher;
import java.util.TimerTask;


public class TSTaskTimer extends TimerTask
{
    private MainActivity _mainActivity;
    private TextSwitcher _tsMain;
    public static int _counter;


    TSTaskTimer(TextSwitcher tsMain, MainActivity mainActivity, int counter)
    {
        _mainActivity = mainActivity;
        _tsMain = tsMain;
        _counter = counter;
    } // TSTaskTimer


    @Override
    public void run() {
        _mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _tsMain.setText(String.valueOf(++_counter));
            } // run
        }); // Runnable
    } // run
} // TSTaskTimer