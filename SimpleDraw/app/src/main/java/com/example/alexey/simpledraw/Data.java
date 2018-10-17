package com.example.alexey.simpledraw;
import android.graphics.Point;
import java.util.ArrayList;


/**
 * Created by Alexey on 21.12.2017.
 * Представляет данные, которые будем сериализовать в JSON.
 */
public class Data
{
    private ArrayList<ArrayList<Point>> _strokes;
    private int _widthStroke;
    private int _color;
    private String _typeStroke;

    Data(ArrayList<ArrayList<Point>> strokes, int widthStroke, int color, String typeStroke) {
        _strokes = strokes;
        _widthStroke = widthStroke;
        _color = color;
        _typeStroke = typeStroke;
    } // Data

    int get_widthStroke() { return _widthStroke; } // get_widthStroke
    public void set_widthStroke(int widthStroke) { _widthStroke = widthStroke; } // set_widthStroke

    int get_color() { return _color; } // get_color
    public void set_color(int color) { _color = color; } // set_color

    String get_typeStroke() { return _typeStroke; } // get_typeStroke
    public void set_typeStroke(String typeStroke) { _typeStroke = typeStroke; } // set_typeStroke

    ArrayList<ArrayList<Point>> get_strokes() { return _strokes; } // get_strokes
    public void set_strokes(ArrayList<ArrayList<Point>> strokes) { _strokes = strokes; } // set_strokes
} // Data class