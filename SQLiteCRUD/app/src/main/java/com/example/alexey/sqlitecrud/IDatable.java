package com.example.alexey.sqlitecrud;

/**
 * Created by Alexey on 31.01.2018.
 * Интерфейс для взаимодействия между Activity и диалогами.
 */
interface IDatable {

    void add(ItemListView user);
    void edit(ItemListView user);
    void delete(ItemListView user);
    void filter(String column, String value);
    void reset();
} // IDatable interf