package com.example.alexey.listviewpeople;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Objects;
import static com.example.alexey.listviewpeople.Constants.ADDRESS_COLUMN;
import static com.example.alexey.listviewpeople.Constants.FULLNAME_COLUMN;
import static com.example.alexey.listviewpeople.Constants.PHONE_COLUMN;


// Приложение имеет 2 активности.
// На первой активности отображается список с людьми и телефонами.
// В меню или на action bar добавлены команды:
// добавить, удалить, редактировать и просмотреть.
// Просмотр и редактирование реализованы во второй активности.
// Удалить можно сразу несколько записей.
// Хранимая информация о человеке: ФИО, телефон, адрес.
public class MainActivity extends AppCompatActivity
{
    // Список, который связан с адаптером ListViewAdapter
    private ArrayList<Worker> _adapterList;
    // Выбранные люди со списка
    private ArrayList<Worker> _selectedPeople;
    private EditText _editTextFullName;
    private EditText _editTextPhone;
    private EditText _editTextAddress;
    private ListView _listViewPeople;
    private ListViewAdapter _adapter;
    private TextView _selection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();

        _listViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Получаем нажатый элемент
                Worker worker = _adapter.getItem(position);
                if (_listViewPeople.isItemChecked(position)) {
                    _selectedPeople.add(worker);
                } else {
                    _selectedPeople.remove(worker);
                } // if-else

                // Строка выбранных людей
                StringBuilder selected = new StringBuilder();
                // Проходим по списку текущего выбора
                for (Worker w : _selectedPeople) {
                    selected.append(w.get_fullName());
                } // foreach
                // Отображаем выбранных людей
                _selection.setText("Выбрано: " + selected.toString());
            } // onItemClick
        }); // OnItemClickListener
    } // onCreate


    // Инициализация компонентов.
    // Находит элементы из разметки и создаёт нужные объекты
    private void InitializeComponent() {
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        _editTextFullName = findViewById(R.id.EditTextFullName);
        _editTextPhone = findViewById(R.id.EditTextPhone);
        _editTextAddress = findViewById(R.id.EditTextAddress);
        _selection = findViewById(R.id.Selection);
        _listViewPeople = findViewById(R.id.ListViewPeople);

        _adapterList = new ArrayList<>();
        _selectedPeople = new ArrayList<>();
        _adapter = new ListViewAdapter(this, R.layout.column_row, _adapterList);
        _listViewPeople.setAdapter(_adapter);
        PopulateAdapterList();
    } // InitializeComponent


    // Заполняет список начальными значениями
    private void PopulateAdapterList() {
        _adapter.add(new Worker("Фомин А.А.", "8 (922) 913-27-25",
                "422511, г. Разино, ул. Королёва, дом 38, квартира 100"));
        _adapter.add(new Worker("Прокофьев С. П.", "8 (950) 934-73-67",
                "393081, г. Узловое, ул. Сталина, дом 26, квартира 20"));
        _adapter.add(new Worker("Фомин Ю. В.", "8 (930) 661-68-63",
                "607687, г. Ивановка, ул. Герцена, дом 38, квартира 140"));
        _adapter.add(new Worker("Иванов Е. Р.", "8 (913) 822-54-57",
                "303718, г. Белозерское, ул. Базовая, дом 87, квартира 205"));
    } // PopulateAdapterList


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
            case R.id.menuItemDelete:
                DeleteItems();
                return true;
            case R.id.menuItemEdit:
                EditItem();
                return true;
        } // switch

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected


    private void EditItem() {
        String fullname = "";
        String phone = "";
        String address = "";

        // Если ничего не выбрано
        if (_selectedPeople.size() == 0) return;
        // Если выбрано больше одного
        if (_selectedPeople.size() > 1) return;

        Intent intent = new Intent(this, EditActivity.class);
        for (Worker w : _selectedPeople) {
            fullname = w.get_fullName();
            phone = w.get_phone();
            address = w.get_address();
        } // foreach
        intent.putExtra(FULLNAME_COLUMN, fullname);
        intent.putExtra(PHONE_COLUMN, phone);
        intent.putExtra(ADDRESS_COLUMN, address);
        startActivityForResult(intent, 1);
    } // EditItem


    private void DeleteItems() {
        // Получаем и удаляем выделенные элементы
        _adapterList.removeAll(_selectedPeople);
        _adapter.notifyDataSetChanged();
        // Снимаем все ранее установленные отметки
        _listViewPeople.clearChoices();
        // Очищаем массив выбраных объектов
        _selectedPeople.clear();
        _selection.setText("");
    } // DeleteItems


    public void ButtonAdd_onCLick(View view) {
        // Запрещаем вводить пустоту
        if (Objects.equals(_editTextFullName.getText().toString(), "") ||
                Objects.equals(_editTextPhone.getText().toString(), "") ||
                Objects.equals(_editTextAddress.getText().toString(), ""))
            return;

        // Формируем элемент для добавления
        Worker temp = new Worker(_editTextFullName.getText().toString(),
                _editTextPhone.getText().toString(), _editTextAddress.getText().toString());

        // Если такого элемента в списке нет, то добавляем
        if (!_adapterList.contains(temp)) {
            _adapterList.add(temp);
            _editTextFullName.setText("");
            _editTextPhone.setText("");
            _editTextAddress.setText("");
            _adapter.notifyDataSetChanged();
        } // if
    } // ButtonAdd_onCLick


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Если активность завершилась без ошибок
        if (resultCode == RESULT_OK)  {
            // Если это определённая активность с номером 1
            if (requestCode == 1) {
                // Если возвращён контейнер с параметрами
                if (data != null) {
                    // Worker[] adapterArrayTemp = (Worker[])_adapterList.toArray();
                    // int index = 0;
                    // for (int i = 0; i < adapterArrayTemp.length; i++) {
                    //     for (Worker sel : _selectedPeople) {
                    //         if (Objects.equals(adapterArrayTemp[i].get_fullName(), sel.get_fullName())) {
                    //             index = i;
                    //         } // if
                    //     } // foreach
                    // } // for i

                    // TODO: изменение без удаления
                    // Удаляем
                    _adapterList.removeAll(_selectedPeople);

                    // Формируем новый item
                    Worker temp = new Worker(data.getStringExtra(FULLNAME_COLUMN),
                            data.getStringExtra(PHONE_COLUMN), data.getStringExtra(ADDRESS_COLUMN));
                    _adapterList.add(temp);

                    // Снимаем все ранее установленные отметки
                    _listViewPeople.clearChoices();
                    // Очищаем массив выбраных объектов
                    _selectedPeople.clear();
                    _adapter.notifyDataSetChanged();
                } // if
            } // if
        } // if
    } // onActivityResult
} // MainActivity class