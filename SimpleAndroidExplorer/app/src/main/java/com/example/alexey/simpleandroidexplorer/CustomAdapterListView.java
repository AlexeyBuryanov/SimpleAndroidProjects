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
 * Created by Alexey on 13.12.2017.
 * Адаптер для настройки кастомного вида ListView.
 */
public class CustomAdapterListView extends ArrayAdapter<Item>
{
    private ArrayList<Item> _list;
    private LayoutInflater _inflater;
    private int _layout;


    CustomAdapterListView(Context context, int resource, ArrayList<Item> list) {
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

        final Item item = _list.get(position);
        viewHolder.imageViewIcon.setImageResource(item.get_icon());
        viewHolder.textViewFileName.setText(item.get_fileName());
        viewHolder.textViewExtension.setText(item.get_extension());
        viewHolder.textViewLength.setText(String.valueOf(item.get_length()));
        viewHolder.textViewRights.setText(item.get_rights());

        return convertView;
    } // getView


    /**
     * Класс для определения views.
     */
    private class ViewHolder
    {
        final ImageView imageViewIcon;
        final TextView textViewFileName, textViewExtension, textViewLength, textViewRights;

        ViewHolder(View view) {
            imageViewIcon = view.findViewById(R.id.Icon);
            textViewFileName = view.findViewById(R.id.FileName);
            textViewExtension = view.findViewById(R.id.Extension);
            textViewLength = view.findViewById(R.id.Length);
            textViewRights = view.findViewById(R.id.Rights);
        } // ViewHolder ctor
    } // ViewHolder class
} // CustomAdapterListView