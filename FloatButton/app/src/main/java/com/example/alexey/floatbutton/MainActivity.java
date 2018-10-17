package com.example.alexey.floatbutton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.Timer;
import java.util.TimerTask;


// В окне находится кнопка. При нажатии на неё она начинает плавно двигаться по таймеру и отскакивает от стен.
public class MainActivity extends AppCompatActivity
{
    private RelativeLayout _layout;
    private Button _button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _layout = findViewById(R.id.activity_main);
        _button = findViewById(R.id.button);
    } // onCreate


    public void button_onClick(View view) {
        // Планируем таймер
        TimerTask timerTask = new ButtonTaskTimer(_layout, _button, this);
        Timer timer = new Timer();
        timer.schedule(timerTask, 100, 22);
    } // button_onClick
} // MainActivity