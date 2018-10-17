package com.example.alexey.simplecalc2;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    // Текстовое поле для вывода результата
    private TextView _resultField;
    // Поле для ввода числа
    private EditText _numberField;
    // Текстовое поле для вывода знака операции
    private TextView _operationField;
    // Операнд операции
    private Double _operand = null;
    // Последняя операция
    private String _lastOperation = "=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        // Получаем элементы управления по Id
        _resultField = findViewById(R.id.resultField);
        _numberField = findViewById(R.id.editNumber);
        _operationField = findViewById(R.id.operationField);
    } // onCreate


    // Сохранение состояния (для восстановления при смене ориентации)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", _lastOperation);
        if(_operand != null)
            outState.putDouble("OPERAND", _operand);
        super.onSaveInstanceState(outState);
    } // onSaveInstanceState


    // Получение ранее сохраненного состояния
    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _lastOperation = savedInstanceState.getString("OPERATION");
        _operand = savedInstanceState.getDouble("OPERAND");
        _resultField.setText(_operand.toString());
        _operationField.setText(_lastOperation);
    } // onRestoreInstanceState


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuItemExit:
                finish();
                return true;
            case R.id.menuItemAbout:
                showDialogAbout();
                return true;
        } // switch

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected


    // Отображает диалоговое окно "О программе"
    private void showDialogAbout() {
        new AboutDialogFragment()
                .show(getFragmentManager(), "about");
    } // showDialogAbout


    // Сброс
    public void buttonReset_onClick(View view) {
        _numberField.setText("");
        _resultField.setText("");
        _operationField.setText("");
    } // buttonReset_onClick


    // Стереть 1 символ
    public void buttonErase_onClick(View view) {
        if (_numberField.getText().toString().length() != 0) {
            _numberField.setText(_numberField.getText().toString()
                    .substring(0, _numberField.getText().toString().length()-1));
            _numberField.setSelection(_numberField.getText().toString().length());
        } // if
    } // buttonErase_onClick


    // Обработка нажатия на числовую кнопку
    public void onNumberClick(View view) {
        Button button = (Button)view;
        _numberField.append(button.getText());

        // Если последняя операция представляла собой получение результата
        // (знак "равно"), то мы сбрасываем переменную operand.
        if (_lastOperation.equals("=") && _operand != null){
            _operand = null;
        } // if
    } // onNumberClick


    // Обработка нажатия на кнопку операции
    public void onOperationClick(View view) {
        Button button = (Button)view;
        String op = button.getText().toString();
        String number = _numberField.getText().toString();

        // Если введенно что-нибудь
        if (number.length() > 0) {
            // Заменяем запятую точкой т.к. в качестве разделителя мы выбрали запятую
            number = number.replace(',', '.');

            try {
                // Передаём введённое число и операцию в метод performOperation
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                // Если были введены нечисловые символы
                _numberField.setText("");
            } // try
        } // if

        _lastOperation = op;
        _operationField.setText(_lastOperation);
    } // onOperationClick


    @SuppressLint("SetTextI18n")
    private void performOperation(Double number, String operation) {
        // Если операнд ранее не был установлен (при вводе самой первой операции)
        if (_operand == null) {
            _operand = number;
        } else {

            if (_lastOperation.equals("=")) {
                _lastOperation = operation;
            } // if

            switch (_lastOperation) {
                case "=":
                    _operand = number;
                    break;

                case "/":
                    // Деление на ноль
                    if (number == 0) {
                        new ErrorDialogFragment()
                                .show(getFragmentManager(), "error");
                        _operand = 0.0;
                    } else {
                        _operand /= number;
                    } // if
                    break;

                case "*":
                    _operand *= number;
                    break;

                case "+":
                    _operand += number;
                    break;

                case "-":
                    _operand -= number;
                    break;
            } // switch
        } // if

        // Полученный результат операции хранится в переменной operand
        _resultField.setText(_operand.toString().replace('.', ','));
        _numberField.setText("");
    } // performOperation
} // MainActivity