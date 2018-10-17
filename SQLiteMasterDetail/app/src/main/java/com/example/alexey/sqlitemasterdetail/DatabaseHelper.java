package com.example.alexey.sqlitemasterdetail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexey on 31.01.2018.
 * Класс-помошник для упрощения работы с базой.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**название бд*/
    private static final String DATABASE_NAME = "db.sqlite";
    /**версия базы данных*/
    private static final int SCHEMA = 1;

    /**таблица издателей*/
    static final String PUBLISHERS_TABLE = "Publishers";
    static final String COL_PUBLISHER_ID = "_id";
    static final String COL_PUBLISHER_NAME = "name";
    static final String COL_PUBLISHER_COUNTRY = "country";
    static final String COL_PUBLISHER_CITY = "city";
    static final String[] COLUMNS_PUBLISHERS = new String[] {
            COL_PUBLISHER_ID, COL_PUBLISHER_NAME,
            COL_PUBLISHER_COUNTRY, COL_PUBLISHER_CITY
    };
    /**таблица тайтлов*/
    static final String TITLES_TABLE = "Titles";
    static final String COL_TITLES_ID = "_id";
    static final String COL_TITLES_NAME = "name";
    static final String COL_TITLES_PRICE = "price";
    static final String COL_TITLES_TYPE = "type";
    static final String COL_TITLES_PUBID = "pubId";
    static final String[] COLUMNS_TITLES = new String[] {
            COL_TITLES_ID, COL_TITLES_NAME,
            COL_TITLES_PRICE, COL_TITLES_TYPE, COL_TITLES_PUBID
    };

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы Издателей --------------------------------------------------------------
        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT);",
                PUBLISHERS_TABLE, COL_PUBLISHER_ID, COL_PUBLISHER_NAME, COL_PUBLISHER_COUNTRY, COL_PUBLISHER_CITY));
        // Добавление начальных данных Издателей
        db.execSQL(String.format("INSERT INTO %s (%s, %s, %s) " +
                        "VALUES " +
                        "('Издатель 1', 'Страна 1', 'Город 1')," +
                        "('Издатель 2', 'Страна 2', 'Город 2')," +
                        "('Издатель 3', 'Страна 3', 'Город 3');",
                PUBLISHERS_TABLE, COL_PUBLISHER_NAME, COL_PUBLISHER_COUNTRY, COL_PUBLISHER_CITY));

        // Создание таблицы Тайтлов --------------------------------------------------------------
        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s INT, " +
                        "%s TEXT," +
                        "%s INT);",
                TITLES_TABLE, COL_TITLES_ID, COL_TITLES_NAME, COL_TITLES_PRICE, COL_TITLES_TYPE, COL_TITLES_PUBID));
        // Добавление начальных данных Тайтлов
        db.execSQL(String.format("INSERT INTO %s (%s, %s, %s, %s) " +
                        "VALUES " +
                        "('Тайтл 1', '100', 'Тип 1', 1)," +
                        "('Тайтл 2', '200', 'Тип 2', 2)," +
                        "('Тайтл 3', '300', 'Тип 3', 3);",
                TITLES_TABLE, COL_TITLES_NAME, COL_TITLES_PRICE, COL_TITLES_TYPE, COL_TITLES_PUBID));
    } // onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PUBLISHERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TITLES_TABLE);
        onCreate(db);
    }
} // DatabaseHelper