package com.example.alexey.searchsqlite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alexey on 31.01.2018.
 * Адаптер для настройки кастомного вида ListView.
 */
public class CustomAdapterListView extends ArrayAdapter<ItemListView> {

    private ArrayList<ItemListView> _list;
    private LayoutInflater _inflater;
    private int _layout;


    CustomAdapterListView(Context context, int resource, ArrayList<ItemListView> list) {
        super(context, resource, list);
        _list = list;
        _inflater = LayoutInflater.from(context);
        _layout = resource;
    } // CustomAdapterListView


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = _inflater.inflate(_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            // Если viewHolder уже есть, то он хранится в тэге
            // Таким образом экономим ресурсы на лишнюю инициализацию
            viewHolder = (ViewHolder) convertView.getTag();
        } // if-else

        final ItemListView item = _list.get(position);
        viewHolder.tvId.setText(String.valueOf(item.get_id()));
        viewHolder.tvNoun.setText(item.get_noun());

        return convertView;
    } // getView


    /*** Класс для определения views.*/
    private class ViewHolder
    {
        final TextView tvId, tvNoun;

        ViewHolder(View view) {
            tvId = view.findViewById(R.id.id);
            tvNoun = view.findViewById(R.id.noun);
        } // ViewHolder ctor
    } // ViewHolder class
} // CustomAdapterListView