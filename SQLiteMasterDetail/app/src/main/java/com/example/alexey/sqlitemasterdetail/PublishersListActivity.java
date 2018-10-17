package com.example.alexey.sqlitemasterdetail;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

/**
  * Активность, представляющая список элементов. Эта активность
  * имеет разные презентации для телефонов и планшетов. На
  * телефонах, активность представляет список предметов, которые при касании,
  * привести к {@link TitlesDetailActivity}, представляющему
  * подробности о товаре. На планшетах представлен перечень предметов,
  * где используются две вертикальные панели.
  */
public class PublishersListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
      * Независимо от того, работает ли эта операция в двухпанельном режиме, то есть работает
      * на планшете.
      */
    private boolean _twoPane;
    private DatabaseAdapter _dbAdapter;
    private PublishersCursorAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "PublishersListActivity action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        if (findViewById(R.id.item_detail_container) != null) {
            // Если true, то активность должна быть в двухпанельном режиме.
            _twoPane = true;
        } // if

        _dbAdapter = DatabaseAdapter.getInstance(this);
        _dbAdapter.open();

        getLoaderManager().initLoader(0, null, this);

        // Настройка списка RecyclerView
        RecyclerView rv = findViewById(R.id.item_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        _adapter = new PublishersCursorAdapter(this, null, this, _dbAdapter, _twoPane);
        rv.setAdapter(_adapter);
    }

    /**
     * Запускается при создании Loader
     * @param i      - номер лоадера
     * @param bundle - параметры для лоадера
     * */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new PublishersLoader(this, _dbAdapter);
    }
    /** Запускается после окончания загрузки данных в курсор*/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        _adapter.setCursor(cursor);
        _adapter.notifyDataSetChanged();
    }
    /** Запускается после уничтожения курсора*/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
        _adapter.notifyDataSetChanged();
    }
} // PublishersListActivity