package com.example.alexey.simpleandroidexplorer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by Alexey on 17.12.2017.
 * Адаптер для настройки кастомного вида GridView.
 */
public class CustomAdapterGridView extends ArrayAdapter<Item>
{
    private ArrayList<Item> _list;
    private LayoutInflater _inflater;
    private int _layout;


    CustomAdapterGridView(Context context, int resource, ArrayList<Item> list) {
        super(context, resource, list);
        _list = list;
        _inflater = LayoutInflater.from(context);
        _layout = resource;
    } // CustomAdapterGridView


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final CustomAdapterGridView.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = _inflater.inflate(_layout, parent, false);
            viewHolder = new CustomAdapterGridView.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            // Если viewHolder уже есть, то он хранится в тэге
            // Таким образом экономим ресурсы на лишнюю инициализацию
            viewHolder = (CustomAdapterGridView.ViewHolder) convertView.getTag();
        } // if-else

        final Item item = _list.get(position);
        viewHolder.imageViewIcon.setImageResource(item.get_icon());
        viewHolder.textViewFileName.setText(item.get_fileName());

        return convertView;
    } // getView


    /**
     * Класс для определения views.
     */
    private class ViewHolder
    {
        final ImageView imageViewIcon;
        final TextView textViewFileName;

        ViewHolder(View view) {
            imageViewIcon = view.findViewById(R.id.Icon);
            textViewFileName = view.findViewById(R.id.FileName);
        } // ViewHolder ctor
    } // ViewHolder class
} // CustomAdapterGridView