package com.example.alexey.sqlitecrud;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Alexey on 31.01.2018.
 * Кастомный CursorAdapter.
 */
public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater _inflater;

    /**
     * Рекомендуемый конструктор.
     *
     * @param context Контекст
     * @param c       Курсор, из которого можно получить данные.
     * @param flags   Флаги, используемые для определения поведения адаптера; может быть
     *                любая комбинация {@link #FLAG_AUTO_REQUERY}
     *                и {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        _inflater = LayoutInflater.from(context);
    }

    /**
     * Создает новый вид для хранения данных, на которые указывает курсор.
     *
     * @param context Интерфейс глобальной информации приложения
     * @param cursor  Курсор, из которого можно получить данные. Курсор уже перемещен
     *                в правильное положение.
     * @param parent  Родитель, к которому присоединен новый вид
     * @return вновь созданный вид.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return _inflater.inflate(R.layout.listview_row, parent, false);
    }

    /**
     * Привязать существующее представление к данным, на которые указывает курсор
     *
     * @param view    Существующий вид, возвращенный ранее newView
     * @param context Интерфейс глобальной информации приложения
     * @param cursor  Курсор, из которого можно получить данные
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
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
} // CustomCursorAdapter