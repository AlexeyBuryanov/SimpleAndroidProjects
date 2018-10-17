package com.example.alexey.sqlitecrudexpandable;

import java.util.ArrayList;

/**
 * Created by Alexey on 14.12.2017.
 * Класс представляет групповой элемент списка ExpandableListView.
 */
public class GroupElement
{
    private int _icon;
    private String _name;
    private ArrayList<ChildElement> _childList = new ArrayList<>();
    private int _pubId;


    public GroupElement(int icon, String name, int pubId)
    {
        _icon = icon;
        _name = name;
        _pubId = pubId;
    } // GroupElement ctor
    public GroupElement(int icon, String name, int pubId, ArrayList<ChildElement> childs)
    {
        _icon = icon;
        _name = name;
        _pubId = pubId;
        _childList = childs;
    } // GroupElement ctor

    public int get_pubId() {
        return _pubId;
    }
    public void set_pubId(int pubId) {
        _pubId = pubId;
    }

    public int get_icon() {
        return _icon;
    } // get_icon
    public void set_icon(int icon) {
        _icon = icon;
    } // set_icon

    public String get_name() {
        return _name;
    } // get_name
    public void set_name(String name) {
        _name = name;
    } // set_name

    public ArrayList<ChildElement> getChildList() {
        return _childList;
    }
    public void setChildList(ArrayList<ChildElement> childList) {
        _childList = childList;
    }
} // GroupElement class