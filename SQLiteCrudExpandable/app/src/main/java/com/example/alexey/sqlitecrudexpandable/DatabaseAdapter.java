package com.example.alexey.sqlitecrudexpandable;

import android.content.ContentValues;
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

    //private Cursor getAllAuthors() {
    //    return _database.query(DatabaseHelper.AUTHORS_TABLE, DatabaseHelper.COLUMNS_AUTHORS,
    //            null, null, null, null, null);
    //}
    public Cursor getAllPublishers() {
        return _database.query(DatabaseHelper.PUBLISHERS_TABLE, DatabaseHelper.COLUMNS_PUBLISHERS,
                null, null, null, null, null);
    }
    public Cursor getAllTitles() {
        return _database.query(DatabaseHelper.TITLES_TABLE, DatabaseHelper.COLUMNS_TITLES,
                null, null, null, null, null);
    }
    public Cursor getAllTitles(int pubId) {
        Cursor cursor = _database.query(DatabaseHelper.TITLES_TABLE, DatabaseHelper.COLUMNS_TITLES,
                DatabaseHelper.COL_PUBLISHER_ID + " = " + String.valueOf(pubId),
                null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    //public ArrayList<Author> getAuthors() {
    //    ArrayList<Author> authors = new ArrayList<>();
    //    Cursor cursor = getAllAuthors();
    //    if (cursor.moveToFirst()) {
    //        do {
    //            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_ID));
    //            String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_FIRSTNAME));
    //            String middleName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_MIDDLENAME));
    //            String country = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_COUNTRY));
    //            String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_CITY));
    //            authors.add(new Author(id, firstName, middleName, country, city));
    //        } while (cursor.moveToNext());
    //    }
    //    cursor.close();
    //    return authors;
    //}
    public ArrayList<Publisher> getPublishers() {
        ArrayList<Publisher> publishers = new ArrayList<>();
        Cursor cursor = getAllPublishers();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_NAME));
                String country = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_COUNTRY));
                String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_CITY));
                publishers.add(new Publisher(id, name, country, city));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return publishers;
    }
    public ArrayList<Title> getTitles() {
        ArrayList<Title> titles = new ArrayList<>();
        Cursor cursor = getAllTitles();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TITLES_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TITLES_NAME));
                int price = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TITLES_PRICE));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TITLES_TYPE));
                int pubId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TITLES_PUBID));
                titles.add(new Title(id, name, price, type, pubId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return titles;
    }

    //public Author getAuthor(int id) {
    //    Author author = null;
    //    String query = String.format("SELECT * FROM %s WHERE %s = ?", DatabaseHelper.AUTHORS_TABLE, DatabaseHelper.COL_AUTHOR_ID);
    //    Cursor cursor = _database.rawQuery(query, new String[] { String.valueOf(id) });
    //    if (cursor.moveToFirst()) {
    //        String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_FIRSTNAME));
    //        String middleName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_MIDDLENAME));
    //        String country = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_COUNTRY));
    //        String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AUTHOR_CITY));
    //        author = new Author(id, firstName, middleName, country, city);
    //    } // if
    //    cursor.close();
    //    return author;
    //}
    public Publisher getPublisher(int id) {
        Publisher publisher = null;
        String query = String.format("SELECT * FROM %s WHERE %s = ?", DatabaseHelper.PUBLISHERS_TABLE, DatabaseHelper.COL_PUBLISHER_ID);
        Cursor cursor = _database.rawQuery(query, new String[] { String.valueOf(id) });
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_NAME));
            String country = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_COUNTRY));
            String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_CITY));
            publisher = new Publisher(id, name, country, city);
        } // if
        cursor.close();
        return publisher;
    }
    public Title getTitle(int id) {
        Title title = null;
        String query = String.format("SELECT * FROM %s WHERE %s = ?", DatabaseHelper.TITLES_TABLE, DatabaseHelper.COL_TITLES_ID);
        Cursor cursor = _database.rawQuery(query, new String[] { String.valueOf(id) });
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_NAME));
            int price = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_COUNTRY));
            String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_CITY));
            int pubId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TITLES_PUBID));
            title = new Title(id, name, price, type, pubId);
        } // if
        cursor.close();
        return title;
    }

    //public long insertAuthor(Author author) {
    //    ContentValues cv = new ContentValues();
    //    cv.put(DatabaseHelper.COL_AUTHOR_FIRSTNAME, author.get_firstName());
    //    cv.put(DatabaseHelper.COL_AUTHOR_MIDDLENAME, author.get_middleName());
    //    cv.put(DatabaseHelper.COL_AUTHOR_COUNTRY, author.get_country());
    //    cv.put(DatabaseHelper.COL_AUTHOR_CITY, author.get_city());
    //    return _database.insert(DatabaseHelper.AUTHORS_TABLE, null, cv);
    //}
    public long insertPublisher(Publisher publisher) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_PUBLISHER_NAME, publisher.get_name());
        cv.put(DatabaseHelper.COL_PUBLISHER_COUNTRY, publisher.get_country());
        cv.put(DatabaseHelper.COL_PUBLISHER_CITY, publisher.get_city());
        return _database.insert(DatabaseHelper.PUBLISHERS_TABLE, null, cv);
    }
    public long insertTitle(Title title) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_TITLES_NAME, title.get_name());
        cv.put(DatabaseHelper.COL_TITLES_PRICE, title.get_price());
        cv.put(DatabaseHelper.COL_TITLES_TYPE, title.get_type());
        cv.put(DatabaseHelper.COL_TITLES_PUBID, title.get_pubId());
        return _database.insert(DatabaseHelper.TITLES_TABLE, null, cv);
    }

    //public long deleteAuthor(int userId) {
    //    String whereClause = DatabaseHelper.COL_AUTHOR_ID + " = " + String.valueOf(userId);
    //    return _database.delete(DatabaseHelper.AUTHORS_TABLE, whereClause, null);
    //}
    public long deletePublisher(int pubId) {
        String whereClause = DatabaseHelper.COL_PUBLISHER_ID + " = " + String.valueOf(pubId);
        return _database.delete(DatabaseHelper.PUBLISHERS_TABLE, whereClause, null);
    }
    public long deleteTitle(int pubId) {
        String whereClause = DatabaseHelper.COL_PUBLISHER_ID + " = " + String.valueOf(pubId);
        return _database.delete(DatabaseHelper.TITLES_TABLE, whereClause, null);
    }

    //public long updateAuthor(Author author) {
    //    String whereClause = DatabaseHelper.COL_AUTHOR_ID + " = " + String.valueOf(author.get_id());
    //    ContentValues cv = new ContentValues();
    //    cv.put(DatabaseHelper.COL_AUTHOR_FIRSTNAME, author.get_firstName());
    //    cv.put(DatabaseHelper.COL_AUTHOR_MIDDLENAME, author.get_middleName());
    //    cv.put(DatabaseHelper.COL_AUTHOR_COUNTRY, author.get_country());
    //    cv.put(DatabaseHelper.COL_AUTHOR_CITY, author.get_city());
    //    return _database.update(DatabaseHelper.AUTHORS_TABLE, cv, whereClause, null);
    //}
    public long updatePublisher(Publisher publisher) {
        String whereClause = DatabaseHelper.COL_PUBLISHER_ID + " = " + String.valueOf(publisher.get_id());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_PUBLISHER_NAME, publisher.get_name());
        cv.put(DatabaseHelper.COL_PUBLISHER_COUNTRY, publisher.get_country());
        cv.put(DatabaseHelper.COL_PUBLISHER_CITY, publisher.get_city());
        return _database.update(DatabaseHelper.PUBLISHERS_TABLE, cv, whereClause, null);
    }
    public long updateTitle(Title title) {
        String whereClause = DatabaseHelper.COL_TITLES_ID + " = " + String.valueOf(title.get_id());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_TITLES_NAME, title.get_name());
        cv.put(DatabaseHelper.COL_TITLES_PRICE, title.get_price());
        cv.put(DatabaseHelper.COL_TITLES_TYPE, title.get_type());
        cv.put(DatabaseHelper.COL_TITLES_PUBID, title.get_pubId());
        return _database.update(DatabaseHelper.TITLES_TABLE, cv, whereClause, null);
    }
} // DatabaseAdapter