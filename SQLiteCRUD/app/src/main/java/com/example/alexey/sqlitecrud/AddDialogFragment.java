package com.example.alexey.sqlitecrud;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alexey on 31.01.2018.
 * Кастомный диалог добавления.
 */
public class AddDialogFragment extends DialogFragment implements View.OnClickListener {

    private ViewHolder _viewHolder;
    /**Контекст данных. В данном случае главная активность.*/
    private IDatable _datable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _datable = (IDatable) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add, container, false);
        view.findViewById(R.id.yesButton).setOnClickListener(this);
        view.findViewById(R.id.noButton).setOnClickListener(this);
        ImageView iv = view.findViewById(R.id.ivIcon);
        iv.setImageResource(android.R.drawable.ic_input_add);
        TextView title = view.findViewById(R.id.tvTitle);
        title.setText("Добавление");
        _viewHolder = new ViewHolder(view);
        return view;
    } // onCreateView

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesButton:
                _datable.add(new ItemListView(_viewHolder.etFirstName.getText().toString(),
                        _viewHolder.etMiddleName.getText().toString(),
                        _viewHolder.etAddress.getText().toString(),
                        _viewHolder.etGender.getText().toString()));
                dismiss();
                break;
            case R.id.noButton:
                getDialog().cancel();
                break;
        } // switch
    } // onClick

    /**
     * Класс для определения views.
     */
    private class ViewHolder
    {
        final EditText etFirstName, etMiddleName, etAddress, etGender;

        ViewHolder(View view) {
            etFirstName = view.findViewById(R.id.etFirstName);
            etMiddleName = view.findViewById(R.id.etMiddleName);
            etAddress = view.findViewById(R.id.etAddress);
            etGender = view.findViewById(R.id.etGender);
        } // ViewHolder ctor
    } // ViewHolder class
} // AddDialogFragment