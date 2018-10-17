package com.example.alexey.tablelayoutpeople;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import java.util.LinkedList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Приложение в основной активности отображает список пользователей
 * (ФИО, телефон, пол, адрес) в TableLayout. Имеется возможность
 * добавлять и удалять пользователей. Добавление пользовтеля происходит
 * на отдельном экране. Поля ввода декорировать при помощи градиентов.
 * При вводе использовать AutoCompleteTextView.
 * */
public class MainActivity extends AppCompatActivity implements IDatable
{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tableLayout) TableLayout tableLayout;
    @BindView(R.id.viewSwitcherMain) ViewSwitcher viewSwitcher;
    @BindView(R.id.actvFullName) AutoCompleteTextView actvFullName;
    @BindView(R.id.actvPhone) AutoCompleteTextView actvPhone;
    @BindView(R.id.actvGender) AutoCompleteTextView actvGender;
    @BindView(R.id.actvAddress) AutoCompleteTextView actvAddress;
    @BindView(R.id.buttonAdd) Button buttonAdd;

    private List<View> _rows;
    private int _id;
    private ArrayAdapter<String> _adapterFullName;
    private ArrayAdapter<String> _adapterPhones;
    private ArrayAdapter<String> _adapterGenders;
    private ArrayAdapter<String> _adapterAddresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        _id = 0;
        _rows = new LinkedList<>();

        viewSwitcher.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(),
                        true));
                viewSwitcher.showNext();
            } // onSwipeRight
            public void onSwipeLeft() {
                viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(),
                        false));
                viewSwitcher.showPrevious();
            } // onSwipeLeft
        });

        String[] fullNames = getResources().getStringArray(R.array.fullNames);
        String[] phones = getResources().getStringArray(R.array.phones);
        String[] genders = getResources().getStringArray(R.array.genders);
        String[] addresses = getResources().getStringArray(R.array.addresses);

        _adapterFullName = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, fullNames);
        _adapterPhones = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, phones);
        _adapterGenders = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genders);
        _adapterAddresses = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, addresses);

        actvFullName.setAdapter(_adapterFullName);
        actvPhone.setAdapter(_adapterPhones);
        actvGender.setAdapter(_adapterGenders);
        actvAddress.setAdapter(_adapterAddresses);

        buttonAdd.setOnClickListener(view -> {
            add(new Worker(actvFullName.getText().toString(), actvPhone.getText().toString(),
                    actvGender.getText().toString(), actvAddress.getText().toString()));
        });

        InitializeFirstRows();
    } // onCreate


    private void InitializeFirstRows() {
        TextView textViewFullName = new TextView(this);
        TextView textViewPhone = new TextView(this);
        TextView textViewGender = new TextView(this);
        TextView textViewAddress = new TextView(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 20, 8);
        textViewFullName.setLayoutParams(params);
        textViewPhone.setLayoutParams(params);
        textViewGender.setLayoutParams(params);
        textViewAddress.setLayoutParams(params);

        textViewFullName.setText("Вихирева Ж. И.");
        textViewPhone.setText("8 (926) 409-99-23");
        textViewGender.setText("Ж");
        textViewAddress.setText("ул. Вагонников 2-я, дом 59, квартира 146");

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(params);
        tableRow.addView(textViewFullName);
        tableRow.addView(textViewPhone);
        tableRow.addView(textViewGender);
        tableRow.addView(textViewAddress);

        tableRow.setId(++_id);
        tableRow.setOnClickListener(view -> {
            _rows.remove(view);
            tableLayout.removeView(view);
        });
        _rows.add(tableRow);
        tableLayout.addView(tableRow);
    } // InitializeFirstRow


    public void Add_onClickMenu() {
        DialogFragment dialog = AddDialogFragment.newInstance();
        dialog.show(getSupportFragmentManager(), "add");
    } // buttonAdd_onClick


    @Override
    public void add(Worker worker) {
        TextView textViewFullName = new TextView(this);
        TextView textViewPhone = new TextView(this);
        TextView textViewGender = new TextView(this);
        TextView textViewAddress = new TextView(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 20, 8);
        textViewFullName.setLayoutParams(params);
        textViewPhone.setLayoutParams(params);
        textViewGender.setLayoutParams(params);
        textViewAddress.setLayoutParams(params);

        textViewFullName.setText(worker.get_fullName());
        textViewPhone.setText(worker.get_phone());
        textViewGender.setText(worker.get_gender());
        textViewAddress.setText(worker.get_address());

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(params);
        tableRow.addView(textViewFullName);
        tableRow.addView(textViewPhone);
        tableRow.addView(textViewGender);
        tableRow.addView(textViewAddress);

        tableRow.setId(++_id);
        tableRow.setOnClickListener(view -> {
            _rows.remove(view);
            tableLayout.removeView(view);
        });
        _rows.add(tableRow);
        tableLayout.addView(tableRow);
    } // add


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
            case R.id.menuItemAdd:
                Add_onClickMenu();
                return true;
        } // switch id

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected
} // MainActivity