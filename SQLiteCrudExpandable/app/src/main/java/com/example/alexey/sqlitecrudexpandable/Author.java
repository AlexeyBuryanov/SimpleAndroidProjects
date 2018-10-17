package com.example.alexey.sqlitecrudexpandable;

/**
 * Created by Alexey on 04.02.2018.
 * Описание одного автора.
 */
public class Author {

    private int _id;
    private String _firstName;
    private String _middleName;
    private String _country;
    private String _city;

    public Author(int id, String firstName, String middleName, String country, String city) {
        _id = id;
        _firstName = firstName;
        _middleName = middleName;
        _country = country;
        _city = city;
    }

    public long get_id() {
        return _id;
    }
    public void set_id(int id) {
        _id = id;
    }

    public String get_firstName() {
        return _firstName;
    }
    public void set_firstName(String firstName) {
        _firstName = firstName;
    }

    public String get_middleName() {
        return _middleName;
    }
    public void set_middleName(String middleName) {
        _middleName = middleName;
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
} // Author