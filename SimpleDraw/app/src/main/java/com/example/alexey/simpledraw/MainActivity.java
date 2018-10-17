package com.example.alexey.simpledraw;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import java.io.File;
import java.util.ArrayList;


/**
 * Приложение позволяет рисовать по документу. Пользователь может
 * выбрать цвет, толщину линий и примитив (Карандаш, окружность, линия,
 * прямоугольник). Поддерживается загрузка / сохрание документа в текстовом формате.
 *
 * Добавить в программу-графический редактор возможность выбирать размер
 * холста при создании документа, а также сохранять и загружать холст с
 * размерами. Кроме того, рисование производится по изображению.
 *
 * TODO: ScrollViewers, настройка размера холста, сохранение размера холста
 * */
public class MainActivity extends AppCompatActivity implements HSVColorPickerDialog.OnColorSelectedListener
{
    private ViewSwitcher _viewSwitcher;
    private EditText _editTextFileName;
    private DrawPanel _drawPanel;
    private String _fileName;
    private JSONHelper _json;
    private ListView _listViewFiles;
    private ArrayAdapter<String> _adapterListView;
    private ArrayList<String> _adapterListViewList;
    private Spinner _spinnerType;
    private String[] _types;
    private EditText _editTextStrokeWidth;
    private EditText _editTextColor;
    private String _currentTypeStroke;
    private LinearLayout _linearLayoutDrawPanel;
    private ScrollView _vScrollView;
    private HorizontalScrollView _hScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        _viewSwitcher = findViewById(R.id.viewSwitcherMain);
        _editTextFileName = findViewById(R.id.editTextFileName);
        _listViewFiles = findViewById(R.id.listViewFiles);
        _spinnerType = findViewById(R.id.spinnerType);
        _editTextStrokeWidth = findViewById(R.id.editTextStrokeWidth);
        _editTextColor = findViewById(R.id.editTextColor);
        //_vScrollView = findViewById(R.id.vScroll);
        //_hScrollView = findViewById(R.id.hScroll);

        // Получение размера экрана
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        _linearLayoutDrawPanel = findViewById(R.id.linearLayoutDrawPanel);
        // Изменение размеров скроллируемой области
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(size.x, size.y);
        _linearLayoutDrawPanel.setLayoutParams(params);

        _drawPanel = new DrawPanel(this, size.x, size.y);
        _linearLayoutDrawPanel.addView(_drawPanel);

        _json = new JSONHelper();
        _adapterListViewList = new ArrayList<>();
        _adapterListView = new ArrayAdapter(this, android.R.layout.simple_list_item_1, _adapterListViewList);
        _listViewFiles.setAdapter(_adapterListView);
        _listViewFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = _adapterListView.getItem(position);
                if (_listViewFiles.isItemChecked(position)) {
                    _listViewFiles.clearChoices();
                    // Открываем файл
                    openFile(fileName);
                } // if
            } // onItemClick
        }); // OnItemClickListener
        _types = new String[] {"Линия", "Окружность", "Прямоугольник"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, _types);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerType.setAdapter(adapterSpinner);
        _spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                _currentTypeStroke = (String)parent.getItemAtPosition(position);
            } // onItemSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        _currentTypeStroke = "Линия";
        ShowFiles();
    } // onCreate


    private void openFile(String fileName) {
        _drawPanel.clearCanvas();
        _fileName = fileName;
        _json.fileName = _fileName;
        _drawPanel.setData(_json.importFromJSON(this));
        _drawPanel.setPaint();
        if (_drawPanel.getData() != null) {
            Toast.makeText(this, String.format("Рисунок %s открыт", _fileName), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Не удалось открыть рисунок", Toast.LENGTH_LONG).show();
        } // if
        _viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(), true));
        _viewSwitcher.showPrevious();
    } // openFile


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
            case R.id.menuItemSettings:
                _viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(),false));
                _viewSwitcher.showNext();
                return true;
        } // switch id

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected


    private void ShowFiles() {
        _adapterListView.clear();

        File[] files = FindFiles(new File(getFilesDir().getPath()));
        if (files == null) {
            return;
        } // if

        for (File file : files) {
            if (!file.isDirectory()) {
                _adapterListView.add(file.getName());
            } // if
        } // foreach
    } // ShowFiles


    /**
     * Поиск файлов в указанной дериктории.
     * @param directory папка, где происходит поиск.
     * @return найденные файлы, массив File.
     */
    private File[] FindFiles(File directory) {
        File[] files = directory.listFiles();
        assert files != null;
        return files;
    } // FindFiles


    public void buttonSave_onClick(View view) {
        _fileName = _editTextFileName.getText().toString();
        _json.fileName = _fileName+".draw";
        boolean result = _json.exportToJSON(this, _drawPanel.getData());
        if (result) {
            ShowFiles();
            Toast.makeText(this, String.format("Рисунок %s сохранен", _fileName), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Не удалось сохранить рисунок", Toast.LENGTH_LONG).show();
        } // if
        _viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(), true));
        _viewSwitcher.showPrevious();
    } // buttonSave_onClick


    public void buttonApply_onClick(View view) {
        _drawPanel.widthStroke = Integer.parseInt(_editTextStrokeWidth.getText().toString());
        _drawPanel.typeStroke = _currentTypeStroke;
        _drawPanel.color = Integer.parseInt(_editTextColor.getText().toString());
        _drawPanel.setPaint();
        _viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(), true));
        _viewSwitcher.showPrevious();
    } // buttonApply_onClick


    public void buttonPickColor_onClick(View view) {
        HSVColorPickerDialog cpd = new HSVColorPickerDialog(this, 0xFF0000FF, this);
        cpd.setTitle( "Pick a color" );
        cpd.show();
    } // buttonPickColor_onClick


    @Override
    public void colorSelected(Integer color) {
        _editTextColor.setText(String.valueOf(color));
    } // colorSelected
} // MainActivity