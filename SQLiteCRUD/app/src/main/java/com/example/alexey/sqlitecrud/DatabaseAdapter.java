package com.example.alexey.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    private Cursor getAllEntries() {
        String[] columns = new String[] {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_FIRSTNAME,
                DatabaseHelper.COLUMN_MIDDLENAME,
                DatabaseHelper.COLUMN_ADDRESS,
                DatabaseHelper.COLUMN_GENDER
        };
        return _database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public ArrayList<ItemListView> getUsers() {
        ArrayList<ItemListView> users = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
                String middleName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MIDDLENAME));
                String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));
                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));
                users.add(new ItemListView(id, firstName, middleName, address, gender));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public ArrayList<ItemListView> getFilteredUsers(String column, String value) {
        ArrayList<ItemListView> users = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", DatabaseHelper.TABLE, column);
        Cursor cursor = _database.rawQuery(query, new String[] { value });
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
                String middleName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MIDDLENAME));
                String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));
                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));
                users.add(new ItemListView(id, firstName, middleName, address, gender));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(_database, DatabaseHelper.TABLE);
    }

    public ItemListView getUser(long id) {
        ItemListView user = null;
        String query = String.format("SELECT * FROM %s WHERE %s = ?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = _database.rawQuery(query, new String[] { String.valueOf(id) });
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
            String middleName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MIDDLENAME));
            String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));
            String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));
            user = new ItemListView(id, firstName, middleName, address, gender);
        } // if
        cursor.close();
        return user;
    }

    public long insert(ItemListView user) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FIRSTNAME, user.get_firstName());
        cv.put(DatabaseHelper.COLUMN_MIDDLENAME, user.get_middleName());
        cv.put(DatabaseHelper.COLUMN_ADDRESS, user.get_address());
        cv.put(DatabaseHelper.COLUMN_GENDER, user.get_gender());
        return _database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long userId) {
        String whereClause = DatabaseHelper.COLUMN_ID + " = " + String.valueOf(userId);
        return _database.delete(DatabaseHelper.TABLE, whereClause, null);
    }

    public long update(ItemListView user) {
        String whereClause = DatabaseHelper.COLUMN_ID + " = " + String.valueOf(user.get_id());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FIRSTNAME, user.get_firstName());
        cv.put(DatabaseHelper.COLUMN_MIDDLENAME, user.get_middleName());
        cv.put(DatabaseHelper.COLUMN_ADDRESS, user.get_address());
        cv.put(DatabaseHelper.COLUMN_GENDER, user.get_gender());
        return _database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
} // DatabaseAdapter