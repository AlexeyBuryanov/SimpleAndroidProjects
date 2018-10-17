package com.example.alexey.searchsqliterecycler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alexey on 31.01.2018.
 * Класс-помошник для упрощения работы с базой.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**название бд*/
    private static final String DATABASE_NAME = "db.sqlite";
    /**версия базы данных*/
    private static final int SCHEMA = 1;
    /**название таблицы в бд*/
    static final String TABLE = "Nouns";
    // Названия столбцов
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NOUN = "noun";
    static final String[] COLUMNS = new String[] {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NOUN,
    };

    private Context _context;

    DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, SCHEMA);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (" +
                                 "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                 "%s TEXT);",
                         TABLE, COLUMN_ID, COLUMN_NOUN));
        // Добавление начальных данных
        //ArrayList<String> words = new ArrayList<>();
        //words.addAll(Arrays.asList(_context.getResources().getStringArray(R.array.array_of_words)));
        //words.forEach(word ->
        //        db.execSQL(String.format("INSERT INTO %s (%s) " +
        //                                 "VALUES " +
        //                                 "('%s');",
        //                         TABLE, COLUMN_NOUN, word)));
    } // onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
} // DatabaseHelper