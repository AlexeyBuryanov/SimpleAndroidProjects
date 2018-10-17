package com.example.alexey.simpleandroidexplorer;


/**
 * Created by Alexey on 13.12.2017.
 * Элемент нашего списка (проводника).
 */
class Item
{
    private int _icon;
    private String _fileName;
    private String _extension;
    private String _length;
    private String _absolutePath;
    private boolean _isDirectory;
    private String _rights;


    Item(int icon, String fileName, String extension, String length, String absolutePath,
         boolean isDirectory, String rights)
    {
        _icon = icon;
        _fileName = fileName;
        _extension = extension;
        _length = length;
        _absolutePath = absolutePath;
        _isDirectory = isDirectory;
        _rights = rights;
    } // Item ctor


    String get_absolutePath() {
        return _absolutePath;
    } // get_absolutePath
    void set_absolutePath(String absolutePath) {
        _absolutePath = _absolutePath;
    } // set_absolutePath


    boolean get_isDirectory() {
        return _isDirectory;
    } // is_isDirectory
    void set_isDirectory(boolean isDirectory) {
        _isDirectory = isDirectory;
    } // set_isDirectory


    int get_icon() {
        return _icon;
    } // get_icon
    void set_icon(int icon) {
        _icon = icon;
    } // set_icon


    String get_fileName() {
        return _fileName;
    } // get_fileName
    void set_fileName(String fileName) {
        _fileName = fileName;
    } // set_fileName


    String get_extension() {
        return _extension;
    } // get_extension
    void set_extension(String extension) {
        _extension = extension;
    } // set_extension


    String get_length() {
        return _length;
    } // get_length
    void set_length(String length) {
        _length = length;
    } // set_length


    String get_rights() {
        return _rights;
    } // get_rights
    void set_rights(String rights) {
        _rights = rights;
    } // set_rights
} // Item