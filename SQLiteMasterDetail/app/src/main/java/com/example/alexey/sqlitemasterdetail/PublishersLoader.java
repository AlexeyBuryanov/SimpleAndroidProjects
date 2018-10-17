package com.example.alexey.sqlitemasterdetail;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by Alexey on 05.02.2018.
 * CursorLoader для асинхронной загрузки данных в список.
 */
public class PublishersLoader extends CursorLoader {

    private DatabaseAdapter _dbAdapter;

    PublishersLoader(Context context, DatabaseAdapter dbAdapter) {
        super(context);
        _dbAdapter = dbAdapter;
    }

    // Загрузка данных из БД в отдельном потоке
    @Override
    public Cursor loadInBackground() { return _dbAdapter.getAllPublishers(); }
} // PublishersLoader