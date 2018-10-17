package com.example.alexey.sqlitecrud;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Alexey on 31.01.2018.
 * Диалог используется для вопроса: редактировать или удалять
 */
public class QuestionDialogFragment extends DialogFragment {

    private ItemListView _user;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _user = new ItemListView(getArguments().getLong("id"),
                getArguments().getString("firstName"),
                getArguments().getString("middleName"),
                getArguments().getString("address"),
                getArguments().getString("gender"));

        return new AlertDialog.Builder(getActivity())
                .setTitle("Что Вы хотите сделать?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Удалить", (dialogInterface, i) -> {
                    DialogFragment dialog = new DeleteDialogFragment();
                    Bundle args = new Bundle();
                    args.putLong("id", _user.get_id());
                    args.putString("firstName", _user.get_firstName());
                    args.putString("middleName", _user.get_middleName());
                    args.putString("address", _user.get_address());
                    args.putString("gender", _user.get_gender());
                    dialog.setArguments(args);
                    dialog.show(getActivity().getSupportFragmentManager(), "delete");
                    dismiss();
                })
                .setNegativeButton("Редактировать", (dialogInterface, i) -> {
                    DialogFragment dialog = new EditDialogFragment();
                    Bundle args = new Bundle();
                    args.putLong("id", _user.get_id());
                    args.putString("firstName", _user.get_firstName());
                    args.putString("middleName", _user.get_middleName());
                    args.putString("address", _user.get_address());
                    args.putString("gender", _user.get_gender());
                    dialog.setArguments(args);
                    dialog.show(getActivity().getSupportFragmentManager(), "edit");
                    dismiss();
                })
                .setNeutralButton("Отмена", (dialogInterface, i) -> {
                    dismiss();
                })
                .create();
    } // onCreateDialog
} // QuestionDialogFragment