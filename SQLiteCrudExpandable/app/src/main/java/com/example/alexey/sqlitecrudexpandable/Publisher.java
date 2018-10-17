package com.example.alexey.sqlitecrudexpandable;

/**
 * Created by Alexey on 04.02.2018.
 * Описание одного издателя.
 */
public class Publisher {

    private int _id;
    private String _name;
    private String _country;
    private String _city;

    public Publisher(int id, String name, String country, String city) {
        _id = id;
        _name = name;
        _country = country;
        _city = city;
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

    public String get_country() {
        return _country;
    }
    public void set_country(String country) {
        _country = country;
    }

    public String get_city() {
        return _city;
    }
    public void set_city(String city) {
        _city = city;
    }
} // Publisher