/**
 * Created by Alexey on 10.12.2017.
 */

package com.example.alexey.listviewpeople;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ListViewAdapter extends ArrayAdapter<Worker>
{
    private ArrayList<Worker> _list;
    private LayoutInflater _inflater;
    private int _layout;


    ListViewAdapter(Context context, int resource, ArrayList<Worker> list) {
        super(context, resource, list);
        _list = list;
        _inflater = LayoutInflater.from(context);
        _layout = resource;
    } // ListViewAdapter


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

        final Worker worker = _list.get(position);
        viewHolder.textViewFullName.setText(worker.get_fullName());
        viewHolder.textViewPhone.setText(worker.get_phone());
        viewHolder.textViewAddress.setText(worker.get_address());

        return convertView;
    } // getView


    // Класс для определения views
    private class ViewHolder
    {
        final TextView textViewFullName, textViewPhone, textViewAddress;

        ViewHolder(View view) {
            textViewFullName = view.findViewById(R.id.FullName);
            textViewPhone = view.findViewById(R.id.Phone);
            textViewAddress = view.findViewById(R.id.Address);
        } // ViewHolder ctor
    } // ViewHolder class
} // ListViewAdapters