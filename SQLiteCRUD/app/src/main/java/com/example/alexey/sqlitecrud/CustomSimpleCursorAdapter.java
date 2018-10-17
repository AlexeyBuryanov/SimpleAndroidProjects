package com.example.alexey.sqlitecrud;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Alexey on 31.01.2018.
 * Кастомный SimpleCursorAdapter.
 */
public class CustomSimpleCursorAdapter extends SimpleCursorAdapter {

    private Context _context;
    private int _layout;
    private LayoutInflater _inflater;

    /**
     * Стандартный конструктор.
     *
     * @param context Контекст, в котором работает ListView, связанный с этим SimpleListItemFactory
     * @param layout  идентификатор ресурса файла макета, который определяет представления для
     *                этого элемента списка. Файл макета должен включать по крайней мере те
     *                названные представления, которые определены в «to»,
     * @param c       Курсор базы данных. Может быть пустым, если курсор еще недоступен.
     * @param from    Список имен столбцов, представляющих данные для привязки к пользовательскому
     *                интерфейсу. Может быть пустым, если курсор еще недоступен.
     * @param to      Представления, которые должны отображать столбец в параметре «from».
     *                Все они должны быть TextViews. В первых N представлениях в этом списке
     *                заданы значения первых N столбцов в параметре from. Может быть пустым,
     *                если курсор еще недоступен.
     * @param flags   Флаги, используемые для определения поведения адаптера,
     *                согласно {@link CursorAdapter # CursorAdapter (Context, Cursor, int)}.
     */
    public CustomSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        _context = context;
        _layout = layout;
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return _inflater.inflate(_layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvId.setText(cursor.getInt(0));
        viewHolder.tvFirstName.setText(cursor.getString(1));
        viewHolder.tvMiddleName.setText(cursor.getString(2));
        viewHolder.tvAddress.setText(cursor.getString(3));
        viewHolder.tvGender.setText(cursor.getString(4));
    }

    /*** Класс для определения views.*/
    private class ViewHolder
    {
        final TextView tvId, tvFirstName, tvMiddleName, tvAddress, tvGender;

        ViewHolder(View view) {
            tvId = view.findViewById(R.id.id);
            tvFirstName = view.findViewById(R.id.firstName);
            tvMiddleName = view.findViewById(R.id.middleName);
            tvAddress = view.findViewById(R.id.address);
            tvGender = view.findViewById(R.id.gender);
        } // ViewHolder ctor
    } // ViewHolder class
} // CustomSimpleCursorAdapter