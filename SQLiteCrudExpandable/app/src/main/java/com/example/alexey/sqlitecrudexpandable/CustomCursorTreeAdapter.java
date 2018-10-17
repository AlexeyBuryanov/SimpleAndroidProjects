package com.example.alexey.sqlitecrudexpandable;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alexey on 04.02.2018.
 * Кастомный адаптер древовидного курсора.
 */
public class CustomCursorTreeAdapter extends CursorTreeAdapter {

    private DatabaseAdapter _dbAdapter;
    private LayoutInflater _inflater;
    private GroupViewHolder _groupViewHolder;
    private ChildViewHolder _childViewHolder;


    public CustomCursorTreeAdapter(DatabaseAdapter dbAdapter, Context context) {
        super(dbAdapter.getAllPublishers(), context);
        _dbAdapter = dbAdapter;
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    protected Cursor getChildrenCursor(Cursor cursor) {
        int idCol = cursor.getColumnIndex("_id");
        Cursor cur = _dbAdapter.getAllTitles(cursor.getInt(idCol));
        return cur;
    }
    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean b, ViewGroup viewGroup) {
        return _inflater.inflate(R.layout.group_view, viewGroup, false);
    }
    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean b) {
        _groupViewHolder = new GroupViewHolder(view);
        _groupViewHolder.iconGroup.setImageResource(android.R.drawable.ic_menu_gallery);
        _groupViewHolder.textGroup.setText(cursor.getString(cursor.getColumnIndex("name")));
    }
    @Override
    protected View newChildView(Context context, Cursor cursor, boolean b, ViewGroup viewGroup) {
        return _inflater.inflate(R.layout.child_view, viewGroup, false);
    }
    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean b) {
        _childViewHolder = new ChildViewHolder(view);
        _childViewHolder.iconChild.setImageResource(android.R.drawable.ic_menu_gallery);
        _childViewHolder.tvName.setText(cursor.getString(cursor.getColumnIndex("name")));
        _childViewHolder.tvPrice.setText(cursor.getString(cursor.getColumnIndex("price")));
        _childViewHolder.tvType.setText(cursor.getString(cursor.getColumnIndex("type")));
    }


    public void insertPublisher(Publisher publisher) {
        // Добавление нового издателя
        _dbAdapter.insertPublisher(publisher);
        // Создать новый курсор и заменить старый для повторного запроса
        Cursor newCursor = _dbAdapter.getAllPublishers();
        // Сменить курсор на актуальный
        setGroupCursor(newCursor);
        // Обновить адаптер
        notifyDataSetChanged();
    }
    public void insertTitle(Title title) {
        _dbAdapter.insertTitle(title);
        // Создать новый курсор и заменить старый для повторного запроса
        Cursor newCursor = _dbAdapter.getAllPublishers();
        // Сменить курсор на актуальный
        setGroupCursor(newCursor);
        // Обновить адаптер
        notifyDataSetChanged();
    }
    public void deletePublisher(int pubId) {
        // Удаление издателя
        _dbAdapter.deletePublisher(pubId);
        _dbAdapter.deleteTitle(pubId);
        // Создать новый курсор и заменить старый для повторного запроса
        Cursor newCursor = _dbAdapter.getAllPublishers();
        // Сменить курсор на актуальный
        setGroupCursor(newCursor);
        // Обновить адаптер
        notifyDataSetChanged();
    }


    /**
     * Класс для определения views группы.
     */
    private class GroupViewHolder {
        final ImageView iconGroup;
        final TextView textGroup;
        GroupViewHolder(View view) {
            iconGroup = view.findViewById(R.id.iconGroup);
            textGroup = view.findViewById(R.id.textGroup);
        } // GroupViewHolder ctor
    } // GroupViewHolder class
    /**
     * Класс для определения views подэлементов группы.
     */
    private class ChildViewHolder {
        final ImageView iconChild;
        final TextView tvName, tvPrice, tvType;
        ChildViewHolder(View view) {
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvType = view.findViewById(R.id.tvType);
            iconChild = view.findViewById(R.id.iconChild);
        } // ChildViewHolder ctor
    } // ChildViewHolder class
} // CustomCursorTreeAdapter