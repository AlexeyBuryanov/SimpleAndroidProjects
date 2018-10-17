package com.example.alexey.searchsqlite;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Реализовать поиск по БД SQLite с помощью SearchView и Cursorloader.
 * */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView _listViewMain;
    private CustomAdapterListView _adapterListView;
    private DatabaseAdapter _dbAdapter;
    private ProgressDialog _pd;
    private ArrayList<ItemListView> _allWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Поиск в БД SQLite");
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        _listViewMain = findViewById(R.id.listViewMain);
        _dbAdapter = new DatabaseAdapter(this);
        _dbAdapter.open();
        _pd = new ProgressDialog(this);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _dbAdapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.menuItemSearch);
        SearchView searchView = (SearchView)itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = _dbAdapter.getFiltered(newText);
                ArrayList<ItemListView> nouns = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                        String noun = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));
                        nouns.add(new ItemListView(id, noun));
                    } while (cursor.moveToNext());
                } // if
                cursor.close();
                refresh(nouns);
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            refresh(_allWords);
            return false;
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemExit:
                finish();
                break;
        } // switch

        return super.onOptionsItemSelected(item);
    }

    /**
     * Запускается при создании Loader
     * @param i      - номер лоадера
     * @param bundle - параметры для лоадера
     * */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        _pd.setTitle("Загрузка списка");
        _pd.setMessage("Минуту...");
        _pd.show();
        return new CustomCursorLoader(this, _dbAdapter);
    }

    /** Запускается после окончания загрузки данных в курсор*/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        _allWords = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String noun = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));
                _allWords.add(new ItemListView(id, noun));
            } while (cursor.moveToNext());
        } // if
        cursor.close();
        refresh(_allWords);
        _pd.dismiss();
    }

    /** Запускается после уничтожения курсора*/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) { refresh(null); }

    private void refresh(ArrayList<ItemListView> nouns) {
        new Handler().post(() -> {
            _dbAdapter.close();
            _dbAdapter = new DatabaseAdapter(this);
            _dbAdapter.open();
            _adapterListView = new CustomAdapterListView(this, R.layout.listview_row, nouns);
            _listViewMain.setAdapter(_adapterListView);
        });
    }
} // MainActivity class