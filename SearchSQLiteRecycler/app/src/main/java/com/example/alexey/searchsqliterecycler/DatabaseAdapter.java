package com.example.alexey.searchsqliterecycler;

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

    DatabaseAdapter (Context context) {
        _dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    DatabaseAdapter open() {
        _database = _dbHelper.getWritableDatabase();
        return this;
    }

    void close(){
        _dbHelper.close();
    }

    Cursor getFiltered(String filter) {
        String query = "SELECT * FROM " +
                DatabaseHelper.TABLE + " " +
                "WHERE " + DatabaseHelper.COLUMN_NOUN + " " +
                "LIKE '" + filter + "%'";
        return _database.rawQuery(query, null);
    }

    Cursor getAllEntries() {
        return _database.query(DatabaseHelper.TABLE, DatabaseHelper.COLUMNS, null,
                null, null, null, null);
    }

    public ArrayList<ItemRecyclerView> getNouns() {
        ArrayList<ItemRecyclerView> nouns = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String noun = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));
                nouns.add(new ItemRecyclerView(id, noun));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nouns;
    }

    public ArrayList<ItemRecyclerView> getFilteredNouns(String filter) {
        ArrayList<ItemRecyclerView> nouns = new ArrayList<>();
        Cursor cursor = getFiltered(filter);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String noun = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));
                nouns.add(new ItemRecyclerView(id, noun));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nouns;
    }
} // DatabaseAdapter