package com.example.alexey.sqlitemasterdetail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by Alexey on 07.02.2018.
 * Custom RecyclerViewCursorAdapter
 */
public class PublishersCursorAdapter extends RecyclerView.Adapter<PublishersCursorAdapter.ViewHolder> {

    private LayoutInflater _inflater;
    private ItemClickListener _itemClickListener;
    private int _selectedPos = RecyclerView.NO_POSITION;
    private Context _context;
    private Cursor _cursor;
    private int _lastAnimPos = -1;
    private PublishersListActivity _parentActivity;
    private DatabaseAdapter _dbAdapter;
    private boolean _twoPane;

    PublishersCursorAdapter(Context context, Cursor cursor, PublishersListActivity parent, DatabaseAdapter db, boolean twoPane) {
        _inflater = LayoutInflater.from(context);
        _context = context;
        _cursor = cursor;
        _parentActivity = parent;
        _dbAdapter = db;
        _twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.item_publisher, parent, false);
        return new ViewHolder(view);
    }

    /** Запускается для каждой строки списка, когда её нужно заполнить*/
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (_cursor != null) {
            // Переместить курсор в выделенную позицию
            _cursor.moveToPosition(position);

            int id = _cursor.getInt(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_ID));
            String name = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_NAME));
            String country = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_COUNTRY));
            String city = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_CITY));

            // Заполнить элементы информацией из курсора
            viewHolder.id.setText(String.valueOf(id));
            viewHolder.name.setText(name);
            viewHolder.country.setText(country);
            viewHolder.city.setText(city);

            // Поместить в тег пункта списка поле au_id для дальнейшего показа книг этого автора в детальном экране
            viewHolder.itemView.setTag(id);

            setAnimation(viewHolder.itemView, position);
        } // if
    }

    @Override
    public int getItemCount() { return (_cursor == null) ? 0 : _cursor.getCount(); }

    public Publisher getItem(int id) {
        Publisher result = null;
        if (_cursor != null) {
            // Переместить курсор в выделенную позицию
            _cursor.moveToPosition(id);

            int _id = _cursor.getInt(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_ID));
            String name = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_NAME));
            String country = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_COUNTRY));
            String city = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COL_PUBLISHER_CITY));

            result = new Publisher(_id, name, country, city);
        } // if
        return result;
    }

    Cursor getCursor() { return _cursor; }
    void setCursor(Cursor cursor) { _cursor = cursor; }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > _lastAnimPos) {
            Animation animation = AnimationUtils.loadAnimation(_context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            _lastAnimPos = position;
        } // if
    }

    public interface ItemClickListener { void onItemClick(View view, int position); }

    void setClickListener(ItemClickListener itemClickListener) {
        _itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView id, name, country, city;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
            city = itemView.findViewById(R.id.city);
            // Разрешить нажатия на пунктах
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        /** Реакция на нажатие*/
        @Override
        public void onClick(View view) {
            // Получить id из пункта списка, по которому произошло нажатие
            int pubId = (int)view.getTag();

            // Снять выделение с уже выделенного элемента
            notifyItemChanged(_selectedPos);
            // Получить позицию выделенного элемента
            _selectedPos = getLayoutPosition();
            // Выделить новый элемент
            notifyItemChanged(_selectedPos);
            if (_itemClickListener != null)
                _itemClickListener.onItemClick(view, getAdapterPosition());

            // Запуск детальной активности или детального фрагмента для выделенной публикации
            // TODO:
            if (_twoPane) {
                Bundle arguments = new Bundle();
                arguments.putInt(TitlesDetailFragment.ARG_ITEM_ID, pubId);
                TitlesDetailFragment fragment = new TitlesDetailFragment();
                fragment.setArguments(arguments);
                _parentActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, TitlesDetailFragment.class);
                intent.putExtra(TitlesDetailFragment.ARG_ITEM_ID, pubId);
                context.startActivity(intent);
            } // if-else
        }
    } // ViewHolder
} // PublishersCursorAdapter