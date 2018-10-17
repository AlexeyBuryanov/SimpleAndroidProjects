package com.example.alexey.simpleandroidexplorer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import static com.example.alexey.simpleandroidexplorer.Constants.CURRENT_PATH;
import static com.example.alexey.simpleandroidexplorer.Constants.VIEW_TABLE;
import static com.example.alexey.simpleandroidexplorer.Constants.VIEW_GRID;


/**
 * Приложение позволяет просматривать список файлов
 * из определённой папки в файловой системе Android.
 */
public class MainActivity extends AppCompatActivity
{
    private String _currentView;
    private CustomAdapterListView _adapterListView;
    private CustomAdapterGridView _adapterGridView;
    private ArrayList<Item> _adapterListViewList;
    private ArrayList<Item> _adapterGridViewList;
    private ListView _listViewMain;
    private GridView _gridViewMain;
    private TextView _textViewPath;
    private String _absolutePath;
    private File _rootSystem;
    private File _rootSdCard;
    private File _rootAppFolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        InitializeComponent();

        SetAdaptersAndClicks();

        _currentView = VIEW_TABLE;

        ShowFiles(_rootAppFolder);

        _absolutePath = _rootAppFolder.getAbsolutePath();
    } // onCreate


    /**
     * Инициализация компонентов.
     * Находит элементы разметки и создаёт нужные объекты.
     */
    private void InitializeComponent() {
        _gridViewMain = findViewById(R.id.GridViewMain);
        _listViewMain = findViewById(R.id.ListViewMain);
        _textViewPath = findViewById(R.id.TextViewPath);
        _adapterListViewList = new ArrayList<>();
        _adapterListView = new CustomAdapterListView(this, R.layout.column_row, _adapterListViewList);
        _adapterGridViewList = new ArrayList<>();
        _adapterGridView = new CustomAdapterGridView(this, R.layout.item_grid, _adapterGridViewList);
        _rootSystem = Environment.getRootDirectory();
        _rootSdCard = Environment.getExternalStorageDirectory();
        _rootAppFolder = new File(getFilesDir().getPath());
    } // InitializeComponent


    private void SetAdaptersAndClicks() {
        _gridViewMain.setAdapter(_adapterGridView);
        _gridViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Получаем нажатый элемент
                Item item = _adapterGridView.getItem(position);
                if (_gridViewMain.isItemChecked(position)) {
                    _gridViewMain.clearChoices();
                    assert item != null;
                    if (item.get_isDirectory()) {
                        _absolutePath = item.get_absolutePath();
                        ShowFiles(new File(_absolutePath));
                    } else {
                        _gridViewMain.clearChoices();
                    } // if-else
                } // if
            } // onItemClick
        }); // OnItemClickListener

        _listViewMain.setAdapter(_adapterListView);
        _listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Получаем нажатый элемент
                Item item = _adapterListView.getItem(position);
                if (_listViewMain.isItemChecked(position)) {
                    _listViewMain.clearChoices();
                    assert item != null;
                    if (item.get_isDirectory()) {
                        _absolutePath = item.get_absolutePath();
                        ShowFiles(new File(_absolutePath));
                    } else {
                        _listViewMain.clearChoices();
                    } // if-else
                } // if
            } // onItemClick
        }); // OnItemClickListener
    } // SetAdaptersAndClicks


    /**
     * Общий метод по отображению в зависимости от текущих настроек вида.
     * @param directory папка файловой системы андроид.
     */
    private void ShowFiles(File directory) {
        // Если текущий вид это вид "Таблицы" (ListView),
        // вызываем соответствующий метод для заполнения списка
        if (Objects.equals(_currentView, VIEW_TABLE)) {
            ShowFilesInTable(directory);
            // Иначе это у нас GridView - вид сеткой
        } else {
            ShowFilesInGrid(directory);
        } // if-else
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


    /**
     * Отобразить файлы в виде таблицы (список ListView).
     */
    private void ShowFilesInTable(File directory) {
        _adapterListView.clear();

        File[] files = FindFiles(directory);
        // Если файлов 0 по какой-то причине
        if (files == null) {
            _textViewPath.setText("невозможно просмотреть содержимое");
            return;
        } // if

        for (File file : files) {
            if (file.isDirectory()) {
                StringBuilder rights = new StringBuilder();
                if (file.canRead())
                    rights.append("r ");
                if (file.canWrite())
                    rights.append("w ");
                if (file.canExecute())
                    rights.append("e ");

                _adapterListView.add(new Item(R.mipmap.ic_folder_black_24dp,
                        file.getName(),
                        "Папка",
                        String.valueOf(file.length()+" B"),
                        file.getAbsolutePath(),
                        true,
                        Objects.equals(rights.toString(), "") ? "-" : rights.toString()));
            } else {
                try {
                    StringBuilder rights = new StringBuilder();
                    if (file.canRead())
                        rights.append("r ");
                    if (file.canWrite())
                        rights.append("w ");
                    if (file.canExecute())
                        rights.append("e ");

                    _adapterListView.add(new Item(R.mipmap.ic_insert_drive_file_black_24dp,
                            file.getName().substring(0, file.getName().lastIndexOf('.')),
                            file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()),
                            String.valueOf(file.length()+" B"),
                            file.getAbsolutePath(),
                            false,
                            Objects.equals(rights.toString(), "") ? "-" : rights.toString()));
                    // Если происходит исключение по индексу строк, (т.к. мы ранее со строки вытаскивали подстроку)
                    // то найденный файл либо двоичный, либо вообще не имеет никакого расширения.
                    // Отображаем соответственно.
                } catch (StringIndexOutOfBoundsException ex) {
                    StringBuilder rights = new StringBuilder();
                    if (file.canRead())
                        rights.append("r ");
                    if (file.canWrite())
                        rights.append("w ");
                    if (file.canExecute())
                        rights.append("e ");

                    _adapterListView.add(new Item(R.mipmap.ic_archive_black_24dp,
                            file.getName(),
                            "?",
                            String.valueOf(file.length()+" B"),
                            file.getAbsolutePath(),
                            false,
                            Objects.equals(rights.toString(), "") ? "-" : rights.toString()));
                    // Информируем об этом происшествии в логе
                    Log.w("Intercepted", "StringIndexOutOfBoundsException in ShowFilesInTable(File directory)");
                } // try-catch
            } // if-else
        } // foreach

        _textViewPath.setText(directory.getAbsolutePath());
    } // ShowFilesInTable


    /**
     * Отобразить файлы в виде сетки (список GridView).
     */
    private void ShowFilesInGrid(File directory) {
        _adapterGridView.clear();

        File[] files = FindFiles(directory);
        // Если файлов 0 по какой-то причине
        if (files == null) {
            _textViewPath.setText("невозможно просмотреть содержимое");
            return;
        } // if

        for (File file : files) {
            if (file.isDirectory()) {
                StringBuilder rights = new StringBuilder();
                if (file.canRead())
                    rights.append("r ");
                if (file.canWrite())
                    rights.append("w ");
                if (file.canExecute())
                    rights.append("e ");

                _adapterGridView.add(new Item(R.mipmap.ic_folder_black_24dp,
                        file.getName(),
                        "Папка",
                        String.valueOf(file.length()+" B"),
                        file.getAbsolutePath(),
                        true,
                        Objects.equals(rights.toString(), "") ? "-" : rights.toString()));
            } else {
                try {
                    StringBuilder rights = new StringBuilder();
                    if (file.canRead())
                        rights.append("r ");
                    if (file.canWrite())
                        rights.append("w ");
                    if (file.canExecute())
                        rights.append("e ");

                    _adapterGridView.add(new Item(R.mipmap.ic_insert_drive_file_black_24dp,
                            file.getName().substring(0, file.getName().lastIndexOf('.')),
                            file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()),
                            String.valueOf(file.length()+" B"),
                            file.getAbsolutePath(),
                            false,
                            Objects.equals(rights.toString(), "") ? "-" : rights.toString()));
                    // Если происходит исключение по индексу строк, (т.к. мы ранее со строки вытаскивали подстроку)
                    // то найденный файл либо двоичный, либо вообще не имеет никакого расширения.
                    // Отображаем соответственно.
                } catch (StringIndexOutOfBoundsException ex) {
                    StringBuilder rights = new StringBuilder();
                    if (file.canRead())
                        rights.append("r ");
                    if (file.canWrite())
                        rights.append("w ");
                    if (file.canExecute())
                        rights.append("e ");

                    _adapterGridView.add(new Item(R.mipmap.ic_archive_black_24dp,
                            file.getName(),
                            "?",
                            String.valueOf(file.length()+" B"),
                            file.getAbsolutePath(),
                            false,
                            Objects.equals(rights.toString(), "") ? "-" : rights.toString()));
                    // Информируем об этом происшествии в логе
                    Log.w("Intercepted", "StringIndexOutOfBoundsException in ShowFilesInGrid(File directory)");
                } // try-catch
            } // if-else
        } // foreach

        _textViewPath.setText(directory.getAbsolutePath());
    } // ShowFilesInGrid


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
            case R.id.menuItemBack:
                String path = _absolutePath.substring(0, _absolutePath.lastIndexOf('/'));
                _absolutePath = path;
                ShowFiles(new File(path));
                return true;
            case R.id.menuItemGoOverSystem:
                ShowFiles(_rootSystem);
                _absolutePath = _rootSystem.getAbsolutePath();
                return true;
            case R.id.menuItemGoOverAppFolder:
                ShowFiles(_rootAppFolder);
                _absolutePath = _rootAppFolder.getAbsolutePath();
                return true;
            case R.id.menuItemGoOverSdCard:
                ShowFiles(_rootSdCard);
                _absolutePath = _rootSdCard.getAbsolutePath();
                return true;
            case R.id.menuItemCreateFile:
                Intent intent = new Intent(this, CreateFilesActivity.class);
                intent.putExtra(CURRENT_PATH, _absolutePath);
                startActivity(intent);
                return true;
            case R.id.menuItemViewTable:
                if (!Objects.equals(_currentView, VIEW_TABLE)) {
                    _currentView = VIEW_TABLE;
                    _gridViewMain.setVisibility(View.GONE);
                    _listViewMain.setVisibility(View.VISIBLE);
                    ShowFiles(new File(_absolutePath));
                } // if
                return true;
            case R.id.menuItemViewGrid:
                // Если текущий вид не "сетка", то меняем его.
                // Иначе нет смысла всё перерисовывать
                if (!Objects.equals(_currentView, VIEW_GRID)) {
                    _currentView = VIEW_GRID;
                    _listViewMain.setVisibility(View.GONE);
                    _gridViewMain.setVisibility(View.VISIBLE);
                    ShowFiles(new File(_absolutePath));
                } // if
                return true;
            //case R.id.menuItemCreateFolder:
                // File dir = new File("/system/newFolder");
                // boolean success = dir.mkdir();
                // ShowFiles(new File("/system"));
                //return true;
        } // switch

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected
} // MainActivity