package com.example.alexey.sqlitecrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexey on 31.01.2018.
 * Класс-помошник для упрощения работы с базой.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**название бд*/
    private static final String DATABASE_NAME = "userStore.sqlite";
    /**версия базы данных*/
    private static final int SCHEMA = 1;
    /**название таблицы в бд*/
    static final String TABLE = "users";
    // Названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_MIDDLENAME = "middleName";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GENDER = "gender";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (" +
                                 "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                 "%s TEXT, " +
                                 "%s TEXT, " +
                                 "%s TEXT, " +
                                 "%s TEXT);",
                TABLE, COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_MIDDLENAME, COLUMN_ADDRESS, COLUMN_GENDER));
        // Добавление начальных данных
        db.execSQL(String.format("INSERT INTO %s (%s, %s, %s, %s) " +
                                 "VALUES " +
                                 "('Вася', 'Пяточкин', 'ул. Многостроев, 10', 'М')," +
                                 "('Витя', 'Мураев', 'ул. Один, 15', 'М')," +
                                 "('Настя', 'Квашня', 'ул. Дежавю, 12', 'Ж');",
                TABLE, COLUMN_FIRSTNAME, COLUMN_MIDDLENAME, COLUMN_ADDRESS, COLUMN_GENDER));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
} // DatabaseHelper