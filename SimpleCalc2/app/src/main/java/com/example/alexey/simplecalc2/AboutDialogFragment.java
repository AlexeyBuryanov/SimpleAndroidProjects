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
// для создания кастомного диалога "О программе"
public class AboutDialogFragment extends DialogFragment
{
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("О программе")
                .setIcon(R.drawable.calculator)
                .setMessage("\nПростой калькулятор.\n\n\t(c) Алексей Бурьянов, 2017\n")
                .setNegativeButton("Закрыть", null)
                .create();
    } // onCreateDialog
} // AboutDialogFragment