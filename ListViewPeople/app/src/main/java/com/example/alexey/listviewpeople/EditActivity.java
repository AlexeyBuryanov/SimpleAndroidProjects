package com.example.alexey.listviewpeople;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import static com.example.alexey.listviewpeople.Constants.ADDRESS_COLUMN;
import static com.example.alexey.listviewpeople.Constants.FULLNAME_COLUMN;
import static com.example.alexey.listviewpeople.Constants.PHONE_COLUMN;


public class EditActivity extends AppCompatActivity
{
    private EditText _editTextFullName;
    private EditText _editTextPhone;
    private EditText _editTextAddress;
    private String _fullName;
    private String _phone;
    private String _address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        setContentView(R.layout.activity_edit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _fullName = extras.getString(FULLNAME_COLUMN);
            _phone = extras.getString(PHONE_COLUMN);
            _address = extras.getString(ADDRESS_COLUMN);
        } // if

        _editTextFullName = findViewById(R.id.EditTextFullName);
        _editTextPhone = findViewById(R.id.EditTextPhone);
        _editTextAddress = findViewById(R.id.EditTextAddress);

        _editTextFullName.setText(_fullName);
        _editTextPhone.setText(_phone);
        _editTextAddress.setText(_address);
    } // onCreate


    public void ButtonOk_onCLick(View view) {
        Intent intent = new Intent();
        intent.putExtra(FULLNAME_COLUMN, _editTextFullName.getText().toString());
        intent.putExtra(PHONE_COLUMN, _editTextPhone.getText().toString());
        intent.putExtra(ADDRESS_COLUMN, _editTextAddress.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    } // ButtonOk_onCLick


    public void ButtonCancel_onCLick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    } // ButtonCancel_onCLick
} // EditActivity