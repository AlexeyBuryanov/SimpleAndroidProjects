package com.example.alexey.sqlitecrud;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Alexey on 31.01.2018.
 * Диалог удаления.
 */
public class DeleteDialogFragment extends DialogFragment {

    private IDatable _datable;
    private ItemListView _user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _datable = (IDatable) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _user = new ItemListView(getArguments().getLong("id"),
                getArguments().getString("firstName"),
                getArguments().getString("middleName"),
                getArguments().getString("address"),
                getArguments().getString("gender"));

        return new AlertDialog.Builder(getActivity())
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить пользователя "+_user.get_firstName()+" "+_user.get_middleName()+"?")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton("Удалить", (dialogInterface, i) -> {
                    _datable.delete(_user);
                    dismiss();
                })
                .setNegativeButton("Отмена", (dialogInterface, i) -> {
                    dismiss();
                })
                .create();
    } // onCreateDialog
} // DeleteDialogFragment