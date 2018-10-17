package com.example.alexey.searchsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Alexey on 31.01.2018.
 * Фактически репозиторий данных для упрощённой работы с базой.
 */
public class DatabaseAdapter {

    private DatabaseHelper _dbHelper;
    private SQLiteDatabase _database;

    public DatabaseAdapter (Context context) {
        _dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open() {
        _database = _dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        _dbHelper.close();
    }

    public Cursor getFiltered(String filter) {
        String query = "SELECT * FROM " +
                DatabaseHelper.TABLE + " " +
                "WHERE " + DatabaseHelper.COLUMN_NOUN + " " +
                "LIKE '" + filter + "%'";
        return _database.rawQuery(query, null);
    }

    public Cursor getAllEntries() {
        return _database.query(DatabaseHelper.TABLE, DatabaseHelper.COLUMNS, null,
                null, null, null, null);
    }

    public ArrayList<ItemListView> getNouns() {
        ArrayList<ItemListView> nouns = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String noun = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));
                nouns.add(new ItemListView(id, noun));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nouns;
    }
} // DatabaseAdapter