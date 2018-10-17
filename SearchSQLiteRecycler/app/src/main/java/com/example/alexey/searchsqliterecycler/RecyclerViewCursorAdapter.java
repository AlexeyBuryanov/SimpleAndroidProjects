package com.example.alexey.searchsqliterecycler;

import android.content.Context;
import android.database.Cursor;
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
public class RecyclerViewCursorAdapter extends RecyclerView.Adapter<RecyclerViewCursorAdapter.ViewHolder> {

    private LayoutInflater _inflater;
    private ItemClickListener _itemClickListener;
    private int _selectedPos = RecyclerView.NO_POSITION;
    private Context _context;
    private Cursor _cursor;
    private int _lastAnimPos = -1;

    RecyclerViewCursorAdapter(Context context, Cursor cursor) {
        _inflater = LayoutInflater.from(context);
        _context = context;
        _cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    /** Запускается для каждой строки списка, когда её нужно заполнить*/
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (_cursor != null) {
            // Переместить курсор в выделенную позицию
            _cursor.moveToPosition(position);

            int id = _cursor.getInt(_cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String noun = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));

            // Заполнить элементы информацией из курсора
            viewHolder.id.setText(String.valueOf(id));
            viewHolder.noun.setText(noun);

            setAnimation(viewHolder.itemView, position);
        } // if
    }

    @Override
    public int getItemCount() {
        return (_cursor == null) ? 0 : _cursor.getCount();
    }

    public ItemRecyclerView getItem(int id) {
        ItemRecyclerView result = null;
        if (_cursor != null) {
            // Переместить курсор в выделенную позицию
            _cursor.moveToPosition(id);

            int _id = _cursor.getInt(_cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String noun = _cursor.getString(_cursor.getColumnIndex(DatabaseHelper.COLUMN_NOUN));

            result = new ItemRecyclerView(_id, noun);
        } // if
        return result;
    }

    Cursor getCursor() {
        return _cursor;
    }
    void setCursor(Cursor cursor) {
        _cursor = cursor;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > _lastAnimPos) {
            Animation animation = AnimationUtils.loadAnimation(_context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            _lastAnimPos = position;
        } // if
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        _itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView id;
        final TextView noun;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            noun = itemView.findViewById(R.id.noun);
            // Разрешить нажатия на пунктах
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        /** Реакция на нажатие*/
        @Override
        public void onClick(View view) {
            // Снять выделение с уже выделенного элемента
            notifyItemChanged(_selectedPos);
            // Получить позицию выделенного элемента
            _selectedPos = getLayoutPosition();
            // Выделить новый элемент
            notifyItemChanged(_selectedPos);
            if (_itemClickListener != null)
                _itemClickListener.onItemClick(view, getAdapterPosition());
        }
    } // ViewHolder
} // RecyclerViewCursorAdapter