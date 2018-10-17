package com.example.alexey.searchsqlite;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Alexey on 05.02.2018.
 */
public class CustomSimpleCursorAdapter extends SimpleCursorAdapter {

    public CustomSimpleCursorAdapter(Context context, int layout, Cursor c,
                                     String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
} // CustomSimpleCursorAdapter