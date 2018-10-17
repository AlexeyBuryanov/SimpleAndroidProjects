package com.example.alexey.sqlitecrudexpandable;

/**
 * Created by Alexey on 04.02.2018.
 * Описание одного тайтла.
 */
public class Title {

    private int _id;
    private String _name;
    private int _price;
    private String _type;
    private int _pubId;

    public Title(int id, String name, int price, String type, int pubId) {
        _id = id;
        _name = name;
        _price = price;
        _type = type;
        _pubId = pubId;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int id) {
        _id = id;
    }

    public String get_name() {
        return _name;
    }
    public void set_name(String name) {
        _name = name;
    }

    public int get_price() {
        return _price;
    }
    public void set_price(int price) {
        _price = price;
    }

    public String get_type() {
        return _type;
    }
    public void set_type(String type) {
        _type = type;
    }

    public int get_pubId() {
        return _pubId;
    }
    public void set_pubId(int pubId) {
        _pubId = pubId;
    }
} // Title