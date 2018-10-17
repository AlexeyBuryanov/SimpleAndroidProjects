/**
 * Created by Alexey Bur'yanov on 27.11.2017.
 */

package com.example.alexey.simplecalc2;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;


// Используем компонент AlertDialog в связке с классом фрагмента DialogFragment
// для создания кастомного диалога для оповещения об ошибке
public class ErrorDialogFragment extends DialogFragment
{
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Ошибка")
                .setIcon(R.mipmap.ic_error_black_24dp)
                .setMessage("\nАСТАНАВИТЕСЬ!\n")
                .setPositiveButton("Ок, больше не буду", null)
                .create();
    } // onCreateDialog
} // ErrorDialogFragment