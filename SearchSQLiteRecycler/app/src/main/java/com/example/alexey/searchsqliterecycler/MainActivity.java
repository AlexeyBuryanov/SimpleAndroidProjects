package com.example.alexey.searchsqliterecycler;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Реализовать поиск по БД SQLite с помощью SearchView и Cursorloader.
 * */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener, RecyclerViewCursorAdapter.ItemClickListener {

    private RecyclerView _recyclerViewMain;
    private RecyclerViewCursorAdapter _adapterRecyclerView;
    private DatabaseAdapter _dbAdapter;
    private ProgressBar _pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        _pb = findViewById(R.id.progressBar);
        _pb.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _recyclerViewMain = findViewById(R.id.recyclerViewMain);
        _adapterRecyclerView = new RecyclerViewCursorAdapter(this, null);
        _adapterRecyclerView.setClickListener(this);
        _recyclerViewMain.setAdapter(_adapterRecyclerView);
        _dbAdapter = new DatabaseAdapter(this);
        _dbAdapter.open();
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
                _adapterRecyclerView.setCursor(_dbAdapter.getFiltered(newText));
                _adapterRecyclerView.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            _adapterRecyclerView.setCursor(_dbAdapter.getAllEntries());
            _adapterRecyclerView.notifyDataSetChanged();
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
        return new CustomCursorLoader(this, _dbAdapter);
    }

    /** Запускается после окончания загрузки данных в курсор*/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        _adapterRecyclerView.setCursor(cursor);
        _adapterRecyclerView.notifyDataSetChanged();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        _pb.setVisibility(View.GONE);
    }

    /** Запускается после уничтожения курсора*/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _adapterRecyclerView.setCursor(null);
        _adapterRecyclerView.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {}

    @Override
    public void onItemClick(View view, int position) {
        if (position > 0) {
            // Получение курсора, связанного с адаптером
            Cursor cursor = _adapterRecyclerView.getCursor();
            // Переместить курсор в выделенную позицию
            cursor.moveToPosition(position);
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String noun = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));
            // Прочитать из результатов значение по курсору
            Toast.makeText(this, String.valueOf(id)+"   "+noun, Toast.LENGTH_SHORT).show();
        } // if
    }
} // MainActivity class