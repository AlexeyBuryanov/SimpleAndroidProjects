package com.example.alexey.simpledraw;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class DrawPanel extends View
{
    /**Объект, хранящий атрибуты рисования*/
    private Paint _paint;
    /**Список точек одного штриха*/
    private ArrayList <Point> _points;
    /**Список штрихов*/
    private ArrayList <ArrayList<Point>> _strokes;
    /**Изображение для рисования*/
    private Bitmap _bitmap;
    /**Холст для изображения*/
    private Canvas _canvas;

    private Random _rand;
    private int _width;
    private int _height;

    public int widthStroke;
    public int color;
    public String typeStroke;


    public DrawPanel(Context context, int width, int height) {
        super(context);
        widthStroke = 6;
        _rand = new Random();
        color = Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255));
        typeStroke = "Линия";

        _paint = createPaint(color, widthStroke);
        _points = new ArrayList<>();
        _strokes = new ArrayList<>();

        _width = width;
        _height = height;
        // Создание изображения с заданными параметрами
        _bitmap = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_8888);
        // Получить холст для рисования на изображении
        _canvas = new Canvas(_bitmap);
    } // DrawPanel


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.WHITE);

        // Перебрать список штрихов
        for (ArrayList<Point> stroke : _strokes) {
            // Отобразить текущий штрих
            drawStroke(stroke, _canvas, typeStroke);
        } // for

        // Отобразить текущий незаконченный штрих
        drawStroke(_points, _canvas, typeStroke);

        // Отобразить изображение на текущем view
        canvas.drawBitmap(_bitmap, 0, 0, null);
    } // onDraw


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Движение по поверхности
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            // Добавить текущую точку в список точек текущего штриха
            _points.add(new Point((int)event.getX(), (int)event.getY()));

            // Перерисовать холст
            invalidate();
        } // if

        // Окончание движения
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            // Создать новый штрих, заполнить его точками и добавить в список штрихов
            _strokes.add(_points);

            // Создать новый пустой штрих (список точек)
            _points = new ArrayList<>();
        } // if

        return true;
    } // onTouchEvent


    /**Рисование одного штриха.*/
    private void drawStroke(ArrayList<Point> stroke, Canvas canvas, String type) {
        if (stroke.size() > 0) {
            Point p0 = stroke.get(0);

            // Перебор всех точек штриха
            for (int i = 1; i < stroke.size(); i++) {
                Point p1 = stroke.get(i);

                // Рисование одной линиии из штриха
                if (Objects.equals(type, "Линия")) {
                    canvas.drawLine(p0.x, p0.y, p1.x, p1.y, _paint);
                } else if (Objects.equals(type, "Окружность")) {
                    canvas.drawCircle(p1.x, p1.y, 50, _paint);
                } else if (Objects.equals(type, "Прямоугольник")) {
                    canvas.drawRect(p0.x, p0.y, p1.x, p1.y, _paint);
                } // if-else

                p0 = p1;
            } // for i
        } // if
    } // drawStroke


    /**Задание атрибутов рисования.*/
    private Paint createPaint(int color, float width) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setStrokeCap(Paint.Cap.ROUND);

        return paint;
    } // createPaint


    public void clearCanvas() {
        // Создание изображения с заданными параметрами
        _bitmap = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_8888);
        // Получить холст для рисования на изображении
        _canvas = new Canvas(_bitmap);
    } // clearCanvas


    public void setPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(widthStroke);
        paint.setStrokeCap(Paint.Cap.ROUND);
        _paint = paint;
    } // setPaint


    public List<Data> getData() {
        List<Data> data = new LinkedList<>();
        data.add(new Data(_strokes, widthStroke, color, typeStroke));
        return data;
    } // getData
    public void setData(List<Data> dataList) {
        for (Data d : dataList) {
            _strokes = d.get_strokes();
            widthStroke = d.get_widthStroke();
            color = d.get_color();
            typeStroke = d.get_typeStroke();
        } // foreach
    } // setData


    private int randomInt(int min, int max) {
        return min+_rand.nextInt(max-min+1);
    } // randomInt
} // DrawPanel