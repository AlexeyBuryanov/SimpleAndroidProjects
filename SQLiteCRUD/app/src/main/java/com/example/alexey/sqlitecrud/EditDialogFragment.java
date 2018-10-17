package com.example.alexey.sqlitecrud;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alexey on 01.02.2018.
 * Диалог редактирования.
 */
public class EditDialogFragment extends DialogFragment implements View.OnClickListener {

    private ViewHolder _viewHolder;
    private IDatable _datable;
    private ItemListView _user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _datable = (IDatable) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add, container, false);
        _viewHolder = new ViewHolder(view);
        _user = new ItemListView(getArguments().getLong("id"),
                getArguments().getString("firstName"),
                getArguments().getString("middleName"),
                getArguments().getString("address"),
                getArguments().getString("gender"));
        _viewHolder.etFirstName.setText(_user.get_firstName());
        _viewHolder.etMiddleName.setText(_user.get_middleName());
        _viewHolder.etAddress.setText(_user.get_address());
        _viewHolder.etGender.setText(_user.get_gender());
        ImageView iv = view.findViewById(R.id.ivIcon);
        iv.setImageResource(android.R.drawable.ic_menu_edit);
        TextView title = view.findViewById(R.id.tvTitle);
        title.setText("Изменение");
        Button yes = view.findViewById(R.id.yesButton);
        yes.setOnClickListener(this);
        yes.setText("Изменить");
        view.findViewById(R.id.noButton).setOnClickListener(this);
        return view;
    } // onCreateView

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesButton:
                _datable.edit(new ItemListView(_user.get_id(), _viewHolder.etFirstName.getText().toString(),
                        _viewHolder.etMiddleName.getText().toString(),
                        _viewHolder.etAddress.getText().toString(),
                        _viewHolder.etGender.getText().toString()));
                dismiss();
                break;
            case R.id.noButton:
                dismiss();
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
} // EditDialogFragment