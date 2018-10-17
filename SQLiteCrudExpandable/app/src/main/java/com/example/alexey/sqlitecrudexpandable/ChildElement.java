package com.example.alexey.sqlitecrudexpandable;

/**
 * Created by Alexey on 14.12.2017.
 * Класс представляет дочерний элемент списка ExpandableListView.
 */
public class ChildElement {

    private String _name;
    private int _price;
    private int _icon;
    private String _type;

    ChildElement(String name, int price, String type, int icon) {
        _name = name;
        _price = price;
        _type = type;
        _icon = icon;
    } // ChildElement ctor
    ChildElement(Title title) {
        _name = title.get_name();
        _price = title.get_price();
        _type = title.get_type();
    } // ChildElement ctor

    String get_name() {
        return _name;
    } // get_name
    public void set_name(String name) {
        _name = name;
    } // set_name

    public int get_icon() {
        return _icon;
    } // get_icon
    public void set_icon(int icon) {
        _icon = icon;
    } // set_icon

    int get_price() {
        return _price;
    } // get_price
    public void set_price(int price) {
        _price = price;
    } // set_price

    String get_type() {
        return _type;
    }
    public void set_type(String type) {
        _type = type;
    }
} // ChildElement class