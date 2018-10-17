package com.example.alexey.sqlitecrudexpandable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexey on 31.01.2018.
 * Класс-помошник для упрощения работы с базой.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**название бд*/
    private static final String DATABASE_NAME = "bookStore.sqlite";
    /**версия базы данных*/
    private static final int SCHEMA = 1;
    /**таблица авторов книг*/
    //static final String AUTHORS_TABLE = "Authors";
    //public static final String COL_AUTHOR_ID = "_id";
    //public static final String COL_AUTHOR_FIRSTNAME = "firstName";
    //public static final String COL_AUTHOR_MIDDLENAME = "middleName";
    //public static final String COL_AUTHOR_COUNTRY = "country";
    //public static final String COL_AUTHOR_CITY = "city";
    //public static final String[] COLUMNS_AUTHORS = new String[] {
    //        COL_AUTHOR_ID, COL_AUTHOR_FIRSTNAME, COL_AUTHOR_MIDDLENAME,
    //        COL_AUTHOR_COUNTRY, COL_AUTHOR_CITY
    //};
    /**таблица издателей*/
    static final String PUBLISHERS_TABLE = "Publishers";
    public static final String COL_PUBLISHER_ID = "_id";
    public static final String COL_PUBLISHER_NAME = "name";
    public static final String COL_PUBLISHER_COUNTRY = "country";
    public static final String COL_PUBLISHER_CITY = "city";
    public static final String[] COLUMNS_PUBLISHERS = new String[] {
            COL_PUBLISHER_ID, COL_PUBLISHER_NAME,
            COL_PUBLISHER_COUNTRY, COL_PUBLISHER_CITY
    };
    /**таблица тайтлов*/
    static final String TITLES_TABLE = "Titles";
    public static final String COL_TITLES_ID = "_id";
    public static final String COL_TITLES_NAME = "name";
    public static final String COL_TITLES_PRICE = "price";
    public static final String COL_TITLES_TYPE = "type";
    public static final String COL_TITLES_PUBID = "pubId";
    public static final String[] COLUMNS_TITLES = new String[] {
            COL_TITLES_ID, COL_TITLES_NAME,
            COL_TITLES_PRICE, COL_TITLES_TYPE, COL_TITLES_PUBID
    };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы Авторов ----------------------------------------------------------------
        //db.execSQL(String.format("CREATE TABLE %s (" +
        //                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
        //                "%s TEXT, " +
        //                "%s TEXT, " +
        //                "%s TEXT, " +
        //                "%s TEXT);",
        //        AUTHORS_TABLE, COL_AUTHOR_ID, COL_AUTHOR_FIRSTNAME, COL_AUTHOR_MIDDLENAME, COL_AUTHOR_COUNTRY, COL_AUTHOR_CITY));
        // Добавление начальных данных Авторов
        //db.execSQL(String.format("INSERT INTO %s (%s, %s, %s, %s) " +
        //                "VALUES " +
        //                "('Имя автора 1', 'Фамилия автора 1', 'Страта автора 1', 'Город автора 1')," +
        //                "('Имя автора 2', 'Фамилия автора 2', 'Страта автора 2', 'Город автора 2')," +
        //                "('Имя автора 3', 'Фамилия автора 3', 'Страта автора 3', 'Город автора 3');",
        //        AUTHORS_TABLE, COL_AUTHOR_FIRSTNAME, COL_AUTHOR_MIDDLENAME, COL_AUTHOR_COUNTRY, COL_AUTHOR_CITY));

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
                        "('Издатель 1', 'Страна издателя 1', 'Город издателя 1')," +
                        "('Издатель 2', 'Страна издателя 2', 'Город издателя 2')," +
                        "('Издатель 3', 'Страна издателя 3', 'Город издателя 3');",
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
                        "('Тайтл 1', '100', 'Тип тайтла 1', 1)," +
                        "('Тайтл 2', '200', 'Тип тайтла 2', 2)," +
                        "('Тайтл 3', '300', 'Тип тайтла 3', 3);",
                TITLES_TABLE, COL_TITLES_NAME, COL_TITLES_PRICE, COL_TITLES_TYPE, COL_TITLES_PUBID));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + AUTHORS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PUBLISHERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TITLES_TABLE);
        onCreate(db);
    }
} // DatabaseHelper