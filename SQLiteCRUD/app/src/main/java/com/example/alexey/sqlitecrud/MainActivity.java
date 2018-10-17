package com.example.alexey.sqlitecrud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Разработать приложение, которое позволяет просматривать,
 * редактировать, добавлять и удалять (CRUD) данные о человеке:
 * ФИО, телефон, адрес, пол
 * */
public class MainActivity extends AppCompatActivity implements IDatable {

    private ListView _listViewMain;
    private CustomAdapterListView _adapterListView;
    private DatabaseAdapter _dbAdapter;
    private TextView _headerId, _headerFirstName, _headerMiddleName,
            _headerAddress, _headerGender;
    private boolean _headerIdIsPressed = false, _headerFirstNameIsPressed = false,
            _headerMiddleNameIsPressed = false, _headerAddressIsPressed = false,
            _headerGenderIsPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Работа с БД SQLite");
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Добавление записи
            new AddDialogFragment().show(getSupportFragmentManager(), "addDialog");
        });
        _listViewMain = findViewById(R.id.listViewMain);
        _listViewMain.setOnItemClickListener((parent, view, position, id) -> {
            // Получаем нажатый элемент
            ItemListView item = _adapterListView.getItem(position);
            if (_listViewMain.isItemChecked(position)) {
                _listViewMain.clearChoices();
                assert item != null;
                showDeleteOrEditDialog(item);
            } // if
        }); // OnItemClickListener
        refresh();
        _headerId = findViewById(R.id.headerId);
        _headerFirstName = findViewById(R.id.headerFirstName);
        _headerMiddleName = findViewById(R.id.headerMiddleName);
        _headerAddress = findViewById(R.id.headerAddress);
        _headerGender = findViewById(R.id.headerGender);
        _headerId.setOnClickListener(v -> sortBy("id"));
        _headerFirstName.setOnClickListener(v -> sortBy("firstName"));
        _headerMiddleName.setOnClickListener(v -> sortBy("middleName"));
        _headerAddress.setOnClickListener(v -> sortBy("address"));
        _headerGender.setOnClickListener(v -> sortBy("gender"));
    }

    private void sortBy(String by) {
        _dbAdapter = new DatabaseAdapter(getApplicationContext());
        _dbAdapter.open();
        ArrayList<ItemListView> users = _dbAdapter.getUsers();
        switch (by) {
            case "id":
                _headerFirstNameIsPressed = false;
                _headerMiddleNameIsPressed = false;
                _headerAddressIsPressed = false;
                _headerGenderIsPressed = false;
                if (_headerIdIsPressed) {
                    users.sort(ItemListView.COMPARE_BY_ID_DESC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerIdIsPressed = false;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Id - сортировка по убыванию", Toast.LENGTH_LONG).show();
                } else {
                    users.sort(ItemListView.COMPARE_BY_ID_ASC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerIdIsPressed = true;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Id - сортировка по возрастанию", Toast.LENGTH_LONG).show();
                } // if-else
                break;
            case "firstName":
                _headerIdIsPressed = false;
                _headerMiddleNameIsPressed = false;
                _headerAddressIsPressed = false;
                _headerGenderIsPressed = false;
                if (_headerFirstNameIsPressed) {
                    users.sort(ItemListView.COMPARE_BY_FIRSTNAME_DESC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerFirstNameIsPressed = false;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Имя - сортировка по убыванию", Toast.LENGTH_LONG).show();
                } else {
                    users.sort(ItemListView.COMPARE_BY_FIRSTNAME_ASC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerFirstNameIsPressed = true;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Имя - сортировка по возрастанию", Toast.LENGTH_LONG).show();
                } // if-else
                break;
            case "middleName":
                _headerIdIsPressed = false;
                _headerFirstNameIsPressed = false;
                _headerAddressIsPressed = false;
                _headerGenderIsPressed = false;
                if (_headerMiddleNameIsPressed) {
                    users.sort(ItemListView.COMPARE_BY_MIDDLENAME_DESC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerMiddleNameIsPressed = false;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Фамилия - сортировка по убыванию", Toast.LENGTH_LONG).show();
                } else {
                    users.sort(ItemListView.COMPARE_BY_MIDDLENAME_ASC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerMiddleNameIsPressed = true;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Фамилия - сортировка по возрастанию", Toast.LENGTH_LONG).show();
                } // if-else
                break;
            case "address":
                _headerIdIsPressed = false;
                _headerFirstNameIsPressed = false;
                _headerMiddleNameIsPressed = false;
                _headerGenderIsPressed = false;
                if (_headerAddressIsPressed) {
                    users.sort(ItemListView.COMPARE_BY_ADDRESS_DESC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerAddressIsPressed = false;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Адрес - сортировка по убыванию", Toast.LENGTH_LONG).show();
                } else {
                    users.sort(ItemListView.COMPARE_BY_ADDRESS_ASC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerAddressIsPressed = true;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Адрес - сортировка по возрастанию", Toast.LENGTH_LONG).show();
                } // if-else
                break;
            case "gender":
                _headerIdIsPressed = false;
                _headerFirstNameIsPressed = false;
                _headerMiddleNameIsPressed = false;
                _headerAddressIsPressed = false;
                if (_headerGenderIsPressed) {
                    users.sort(ItemListView.COMPARE_BY_GENDER_DESC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerGenderIsPressed = false;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Пол - сортировка по убыванию", Toast.LENGTH_LONG).show();
                } else {
                    users.sort(ItemListView.COMPARE_BY_GENDER_ASC);
                    _adapterListView = new CustomAdapterListView(getApplicationContext(),
                            R.layout.listview_row, users);
                    _listViewMain.setAdapter(_adapterListView);
                    _headerGenderIsPressed = true;
                    Toast.makeText(getApplicationContext(),
                            "Столбец Пол - сортировка по возрастанию", Toast.LENGTH_LONG).show();
                } // if-else
                break;
        } // switch
        _dbAdapter.close();
    } // sortBy

    private void refresh() {
        _dbAdapter = new DatabaseAdapter(getApplicationContext());
        _dbAdapter.open();
        _adapterListView = new CustomAdapterListView(getApplicationContext(),
                R.layout.listview_row, _dbAdapter.getUsers());
        _listViewMain.setAdapter(_adapterListView);
        _dbAdapter.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    /**Отображает диалог по которому пользователь выбирает,
     * что он хочет: удалить элемент или редактировать*/
    private void showDeleteOrEditDialog(ItemListView item) {
        DialogFragment dialog = new QuestionDialogFragment();
        Bundle args = new Bundle();
        args.putLong("id", item.get_id());
        args.putString("firstName", item.get_firstName());
        args.putString("middleName", item.get_middleName());
        args.putString("address", item.get_address());
        args.putString("gender", item.get_gender());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "question");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemFilter:
                new FilterDialogFragment().show(getSupportFragmentManager(), "filter");
                break;
            case R.id.menuItemFilterReset:
                refresh();
                break;
            case R.id.menuItemExit:
                finish();
                break;
        } // switch

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void add(ItemListView user) {
        _dbAdapter.open();
        _dbAdapter.insert(user);
        refresh();
        Snackbar.make(this.getWindow().getDecorView(), "Пользователь "+user.get_firstName()+" добавлен", Snackbar.LENGTH_LONG)
                .setAction("Добавлена запись", null).show();
        _dbAdapter.close();
    }

    @Override
    public void edit(ItemListView user) {
        _dbAdapter.open();
        _dbAdapter.update(user);
        refresh();
        Snackbar.make(this.getWindow().getDecorView(), "Пользователь изменён", Snackbar.LENGTH_LONG)
                .setAction("Изменена запись", null).show();
        _dbAdapter.close();
    }

    @Override
    public void delete(ItemListView user) {
        _dbAdapter.open();
        _dbAdapter.delete(user.get_id());
        refresh();
        Snackbar.make(this.getWindow().getDecorView(), "Пользователь "+user.get_firstName()+" удалён", Snackbar.LENGTH_LONG)
                .setAction("Удалена запись", null).show();
        _dbAdapter.close();
    }

    @Override
    public void filter(String column, String value) {
        try {
            if (Objects.equals(value, "") || Objects.equals(value, " "))
                throw new Exception("Пустое значение фильтрации недопустимо");
            switch (column) {
                case "Id":
                    column = DatabaseHelper.COLUMN_ID;
                    break;
                case "Имя":
                    column = DatabaseHelper.COLUMN_FIRSTNAME;
                    break;
                case "Фамилия":
                    column = DatabaseHelper.COLUMN_MIDDLENAME;
                    break;
                case "Адрес":
                    column = DatabaseHelper.COLUMN_ADDRESS;
                    break;
                case "Пол":
                    column = DatabaseHelper.COLUMN_GENDER;
                    break;
            } // switch

            _dbAdapter = new DatabaseAdapter(getApplicationContext());
            _dbAdapter.open();
            ArrayList<ItemListView> users = _dbAdapter.getFilteredUsers(column, value);
            _adapterListView = new CustomAdapterListView(getApplicationContext(),
                    R.layout.listview_row, users);
            _listViewMain.setAdapter(_adapterListView);
            _dbAdapter.close();
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Отфильтровать список по заданным критериям невозможно!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        } // try-catch
    }

    @Override
    public void reset() {
        refresh();
    }
} // MainActivity class