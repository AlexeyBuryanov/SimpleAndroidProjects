package com.example.alexey.simpleandroidexplorer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static com.example.alexey.simpleandroidexplorer.Constants.CURRENT_PATH;


public class CreateFilesActivity extends AppCompatActivity
{
    private String _currentPath;
    private EditText _editTextFileName;
    private EditText _editTextSaveText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_files);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        _editTextFileName = findViewById(R.id.EditTextFileName);
        _editTextSaveText = findViewById(R.id.EditTextSaveText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _currentPath = extras.getString(CURRENT_PATH);
        } // if
    } // onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_files, menu);
        return true;
    } // onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuItemBackToMain:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            case R.id.menuItemOpen:
                OpenFile();
                return true;
            case R.id.menuItemSave:
                SaveFile();
                return true;
        } // switch

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected


    /**
     * Сохранение файла.
     * */
    private void SaveFile() {
        FileOutputStream fos = null;
        try {
            String text = _editTextSaveText.getText().toString();
            fos = openFileOutput(_editTextFileName.getText().toString()+".txt", MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }  catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } // try-catch
        } // try-catch-finally
    } // SaveFile


    /**
     * Открытие файла.
     * */
    private void OpenFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(_editTextFileName.getText().toString());
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            _editTextSaveText.setText(new String(bytes));
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } // try-catch
        } // try-catch-finally
    } // OpenFile
} // CreateFilesActivity