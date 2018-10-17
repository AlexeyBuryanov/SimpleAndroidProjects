package com.example.alexey.sqlitemasterdetail;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by Alexey on 08.02.2018.
 * CursorLoader для асинхронной загрузки данных в список.
 */
public class TitlesLoader extends CursorLoader {

    private DatabaseAdapter _dbAdapter;
    private int _pubId;

    TitlesLoader(Context context, DatabaseAdapter dbAdapter, int pubId) {
        super(context);
        _dbAdapter = dbAdapter;
        _pubId = pubId;
    }

    // Загрузка данных из БД в отдельном потоке
    @Override
    public Cursor loadInBackground() { return _dbAdapter.getAllTitles(_pubId); }
} // TitlesLoader