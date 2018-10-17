package com.alexeyburyanov.webmp3downloader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Разработать приложение, которое позволяет просматривать просматривать
 * инет и удобно скачивать mp3-файлы на устройство.
 * Задйствовать WebView и DownloadManager.
 * */
public class MainActivity extends AppCompatActivity {

    private ConstraintLayout _layout;
    private WebView _webView;
    private CustomWebViewClient _client;
    private EditText _etUrl;
    private Button _btnLoad;
    private ContentLoadingProgressBar _progressBar;
    private String _urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _layout = findViewById(R.id.layout);
        _etUrl = findViewById(R.id.etAddress);
        _progressBar = findViewById(R.id.progressBar);

        _client = new CustomWebViewClient(this, _progressBar);

        _webView = findViewById(R.id.webView);
        _webView.setWebViewClient(_client);
        _webView.setInitialScale(200);
        _webView.getSettings().setBuiltInZoomControls(true);

        _btnLoad = findViewById(R.id.btnLoad);
        _btnLoad.setOnClickListener(v -> {
            if (!(checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.INTERNET,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
            } // if
            if (!_etUrl.getText().toString().contains("http://")) {
                _webView.loadUrl(String.format("http://%s", _etUrl.getText().toString()));
            } // if
            _webView.loadUrl(_etUrl.getText().toString());
            _urlString = _etUrl.getText().toString();
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Загрузка всех МП3 со страницы
            new ParseTask(_urlString, this, _progressBar).execute();
        });
    }

    @Override
    public void onBackPressed() {
        if (_webView.canGoBack()) {
            _webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
