package com.example.alexey.sqlitecrud;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

/**
 * Created by Alexey on 02.02.2018.
 * Диалог фильтрации элементов.
 */
public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {

    private ViewHolder _viewHolder;
    private IDatable _datable;
    private String[] _cols = new String[] {
            "Id", "Имя", "Фамилия", "Адрес", "Пол"
    };
    private String _selectedCol = DatabaseHelper.COLUMN_ID;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _datable = (IDatable) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter, container, false);
        _viewHolder = new ViewHolder(view);
        _viewHolder.ivIcon.setImageResource(android.R.drawable.ic_input_get);
        _viewHolder.tvTitle.setText("Фильтр по");
        _viewHolder.buttonApplyFilter.setOnClickListener(this);
        _viewHolder.buttonReset.setOnClickListener(this);
        _viewHolder.buttonCancel.setOnClickListener(this);
        _viewHolder.spinnerColumn.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, _cols));
        _viewHolder.spinnerColumn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _selectedCol = (String)parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return view;
    } // onCreateView

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonApplyFilter:
                _datable.filter(_selectedCol, _viewHolder.etValue.getText().toString());
                dismiss();
                break;
            case R.id.buttonReset:
                _datable.reset();
                dismiss();
                break;
            case R.id.buttonCancel:
                dismiss();
                break;
        } // switch
    } // onClick

    /**
     * Класс для определения views.
     */
    private class ViewHolder
    {
        final ImageView ivIcon;
        final TextView tvTitle;
        final EditText etValue;
        final Spinner spinnerColumn;
        final Button buttonApplyFilter, buttonReset, buttonCancel;

        ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.tvTitle);
            etValue = view.findViewById(R.id.etValue);
            spinnerColumn = view.findViewById(R.id.spinnerColumn);
            buttonApplyFilter = view.findViewById(R.id.buttonApplyFilter);
            buttonReset = view.findViewById(R.id.buttonReset);
            buttonCancel = view.findViewById(R.id.buttonCancel);
            ivIcon = view.findViewById(R.id.ivIcon);
        } // ViewHolder ctor
    } // ViewHolder class
} // FilterDialogFragment