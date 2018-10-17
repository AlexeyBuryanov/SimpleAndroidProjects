package com.alexeyburyanov.pictureparse;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Приложние позволяет пользователю ввести URL страницы, после чего загружает
 * сайт из инета и анализирует страницу, находит все адреса картинок, скачивает
 * их в особую папку, после чего все картинки показываются в GridView.
 * */
public class MainActivity extends AppCompatActivity {

    private ContentLoadingProgressBar _progressBar;
    private EditText _etUrlPage;
    private GridView _gridView;
    private ArrayList<Item> _adapterGridViewList = new ArrayList<>();
    private CustomAdapterGridView _adapterGridView;
    private ImageView _ivTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        _etUrlPage = findViewById(R.id.etUrlPage);
        _progressBar = findViewById(R.id.progressBar);
        _gridView = findViewById(R.id.gridView);
        _ivTest = findViewById(R.id.ivTest);

        _adapterGridView = new CustomAdapterGridView(this, R.layout.item_grid, _adapterGridViewList);
        _gridView.setAdapter(_adapterGridView);

        //_ivTest.setImageDrawable(Drawable.createFromPath(Environment.getExternalStorageDirectory().getPath()+"/Pictures/picture0.jpg"));
        //_adapterGridView.add(new Item(Environment.getExternalStorageDirectory()
        //        .getPath()+"/Pictures/picture0.jpg", "pic1"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (!(checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[] {
                                Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 1) ;
            } // if
            //ParseTask task = new ParseTask(_etUrlPage.getText().toString(), _progressBar, this,
            //        _etUrlPage, fab, _adapterGridView);
            //task.execute();
            //List<Item> list = null;
            //try {
            //    list = task.get();
            //} catch (InterruptedException | ExecutionException e) {
            //    e.printStackTrace();
            //}
            //if (list != null) {
            //    _adapterGridView.addAll(list);
            //}
            new ParseTask(_etUrlPage.getText().toString(), _progressBar, this,
                    _etUrlPage, fab, _adapterGridView).execute();
        });
    }
}