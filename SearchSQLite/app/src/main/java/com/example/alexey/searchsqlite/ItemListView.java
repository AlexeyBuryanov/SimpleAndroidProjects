package com.example.alexey.searchsqlite;

/**
 * Created by Alexey on 31.01.2018.
 * Элемент нашего списка ListView.
 */
public class ItemListView {

    private int _id;
    private String _noun;

    public ItemListView(int id, String noun) {
        _id = id;
        _noun = noun;
    }

    public long get_id() { return _id; }
    public void set_id(int id) { _id = id; }

    public String get_noun() { return _noun; }
    public void set_noun(String noun) { _noun = noun; }
} // ItemListView