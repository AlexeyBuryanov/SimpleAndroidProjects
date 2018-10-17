package com.alexeyburyanov.pictureparse;

import android.graphics.drawable.Drawable;

/**
 * Created by Alexey on 01.03.2018.
 */
public class Item {
    private String _icon;
    private String _picName;

    public Item(String icon, String picName) {
        _icon = _icon;
        _picName = _picName;
    }

    public String get_icon() {
        return _icon;
    }
    public void set_icon(String icon) {
        _icon = icon;
    }

    public String get_picName() {
        return _picName;
    }
    public void set_picName(String picName) {
        _picName = picName;
    }
}