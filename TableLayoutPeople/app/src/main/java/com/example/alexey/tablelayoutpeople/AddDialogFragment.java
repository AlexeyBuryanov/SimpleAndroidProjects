package com.example.alexey.tablelayoutpeople;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


/**
 * Created by Alexey on 20.12.2017.
 * Используем компонент AlertDialog в связке с классом фрагмента DialogFragment
 * для создания кастомного диалога добавления.
 */
public class AddDialogFragment extends DialogFragment
{
    private ViewHolder _viewHolder;
    /**Контекст данных. В данном случае главная активность.*/
    private IDatable _datable;
    private ArrayAdapter<String> _adapterFullName;
    private ArrayAdapter<String> _adapterPhones;
    private ArrayAdapter<String> _adapterGenders;
    private ArrayAdapter<String> _adapterAddresses;


    static AddDialogFragment newInstance() {
        return new AddDialogFragment();
    } // AddDialogFragment


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        _datable = (IDatable) context;

        String[] fullNames = getResources().getStringArray(R.array.fullNames);
        String[] phones = getResources().getStringArray(R.array.phones);
        String[] genders = getResources().getStringArray(R.array.genders);
        String[] addresses = getResources().getStringArray(R.array.addresses);

        _adapterFullName = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, fullNames);
        _adapterPhones = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, phones);
        _adapterGenders = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, genders);
        _adapterAddresses = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, addresses);
    } // onAttach


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add, container, false);
        _viewHolder = new ViewHolder(view);
        return view;
    } // onCreateView


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Добавить в список")
                .setIcon(R.mipmap.ic_add_box_black_24dp)
                .setView(R.layout.dialog_add)
                .setPositiveButton("Добавить", (dialogInterface, i) -> {
//                    _viewHolder.actvFullName.setText("asd");
//                    _viewHolder.actvPhone.setText("123");
//                    _viewHolder.actvGender.setText("asd");
//                    _viewHolder.actvAddress.setText("asd");
                    _datable.add(new Worker(_viewHolder.actvFullName.getText().toString(),
                            _viewHolder.actvPhone.getText().toString(),
                            _viewHolder.actvGender.getText().toString(),
                            _viewHolder.actvAddress.getText().toString()));
                })
                .setNegativeButton("Отмена", null)
                .create();
    } // onCreateDialog


    /**
     * Класс для определения views.
     */
    private class ViewHolder
    {
        final AutoCompleteTextView actvFullName;
        final AutoCompleteTextView actvPhone;
        final AutoCompleteTextView actvGender;
        final AutoCompleteTextView actvAddress;

        ViewHolder(View view) {
            actvFullName = view.findViewById(R.id.actvFullName);
            actvPhone = view.findViewById(R.id.actvPhone);
            actvGender = view.findViewById(R.id.actvGender);
            actvAddress = view.findViewById(R.id.actvAddress);

            actvFullName.setAdapter(_adapterFullName);
            actvPhone.setAdapter(_adapterPhones);
            actvGender.setAdapter(_adapterGenders);
            actvAddress.setAdapter(_adapterAddresses);
        } // ViewHolder ctor
    } // ViewHolder class
} // AddDialogFragment