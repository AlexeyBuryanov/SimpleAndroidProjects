package com.example.alexey.timeswitch;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import java.util.Timer;


// Реализовать секундомер при помощи таймера, TextSwitcher и ViewSwitcher.
// На первом экране виден секундомер, на втором его можно настроить.
public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory
{
    private Timer _timer;
    private TextSwitcher _textSwitcherMain;
    private ViewSwitcher _viewSwitcherMain;
    private EditText _etPeriod;
    private int _counter;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _etPeriod = findViewById(R.id.etPeriod);
        _viewSwitcherMain = findViewById(R.id.viewSwitcherMain);
        _viewSwitcherMain.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                _viewSwitcherMain.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(),
                        true));
                _viewSwitcherMain.showNext();
            } // onSwipeRight
            public void onSwipeLeft() {
                _viewSwitcherMain.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(),
                        false));
                _viewSwitcherMain.showPrevious();
            } // onSwipeLeft
        });

        _textSwitcherMain = findViewById(R.id.textSwitcherMain);
        _textSwitcherMain.setFactory(this);
        _textSwitcherMain.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        _textSwitcherMain.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        _textSwitcherMain.setText(String.valueOf(0));

        _counter = 0;

        _timer = new Timer();
        _timer.schedule(new TSTaskTimer(_textSwitcherMain, this, _counter),
                500, 1000);
    } // onCreate


    @Override
    public View makeView() {
        TextView tv = new TextView(this);
        tv.setTextSize(80);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.RED);
        return tv;
    } // makeView


    public void buttonApply_OnClick(View view) {
        try {
            // Отменяем событие таймера и планируем новое с новым периодом обновления.
            // При этом сохраняем значение счётчика
            _timer.cancel();
            _counter = TSTaskTimer._counter;
            _timer.purge();
            _timer = new Timer();
            _timer.schedule(new TSTaskTimer(_textSwitcherMain, this, _counter),
                    500, Integer.parseInt(_etPeriod.getText().toString()));
            Toast.makeText(this, "Готово", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        } // try-catch
    } // buttonApply_OnClick
} // MainActivity