package com.example.alexey.floatbutton;
import android.widget.Button;
import android.widget.RelativeLayout;


public class ButtonTaskTimer extends java.util.TimerTask
{
    private MainActivity _mainActivity;
    private RelativeLayout _layout;
    private Button _button;
    private int _topMargin;
    private int _leftMargin;
    private int _step;
    private int _heightButton;
    private int _widthButton;
    private boolean _start;


    ButtonTaskTimer(RelativeLayout layout, Button button, MainActivity mainActivity) {
        _layout = layout;
        _button = button;
        _mainActivity = mainActivity;
        _topMargin = _step;
        _leftMargin = _step;
        _start = true;
        _heightButton = 270;
        _widthButton = _heightButton;
        _step = 10;
    } // ButtonTaskTimer


    @Override
    public void run() {
        _mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);

                // Если кнопка находится в левом нижнем углу.
                // Двигаемся вверх
                if (_leftMargin == 0) {
                    lp.leftMargin = _leftMargin;
                    lp.topMargin = (_topMargin-_step);
                    _topMargin = lp.topMargin;
                    lp.width = _widthButton;
                    lp.height = _heightButton;
                    _button.setLayoutParams(lp);
                } // if

                // Если достигнули первоначальной точки, то это стартовая позиция
                if (_leftMargin == 0 && _topMargin == _step) _start = true;

                // Если достигнута нижняя граница разметки (учитывая размер кнопки),
                // то двигаемся влево.
                // TODO: _layout.getHeight()-270 == ~1310 (условие не выполняется)
                //       то есть в зависимости от разрешения экрана устройства требутеся регулировать
                //       с каким шагом отнимать или прибавлять отступ
                if (1310 == _topMargin) {
                    lp.topMargin = _topMargin;
                    lp.leftMargin = (_leftMargin-_step);
                    _leftMargin = lp.leftMargin;
                    lp.width = _widthButton;
                    lp.height = _heightButton;
                    _button.setLayoutParams(lp);
                // Если достигнута правая граница макета (учитывая размер кнопки),
                // двигаемся вниз
                } else if ((_layout.getWidth()-_widthButton) == _leftMargin) {
                    lp.leftMargin = _leftMargin;
                    lp.topMargin += (_topMargin+_step);
                    _topMargin = lp.topMargin;
                    lp.width = _widthButton;
                    lp.height = _heightButton;
                    _button.setLayoutParams(lp);
                    _start = false;
                // Движение кнопки вправо - стартовая позиция
                } else if (_start) {
                    lp.leftMargin += (_leftMargin+_step);
                    _leftMargin = lp.leftMargin;
                    lp.width = _widthButton;
                    lp.height = _heightButton;
                    _button.setLayoutParams(lp);
                } // if
            } // run
        }); // Runnable
    } // run
} // ButtonTaskTimer