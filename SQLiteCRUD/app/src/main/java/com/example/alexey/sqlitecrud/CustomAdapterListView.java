package com.example.alexey.sqlitecrud;

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
        viewHolder.tvFirstName.setText(item.get_firstName());
        viewHolder.tvMiddleName.setText(item.get_middleName());
        viewHolder.tvAddress.setText(item.get_address());
        viewHolder.tvGender.setText(item.get_gender());

        return convertView;
    } // getView


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
} // CustomAdapterListView