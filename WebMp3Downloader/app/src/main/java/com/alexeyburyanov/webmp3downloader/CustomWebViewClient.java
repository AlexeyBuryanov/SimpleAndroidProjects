package com.alexeyburyanov.webmp3downloader;

import android.graphics.Bitmap;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Alexey on 05.03.2018.
 */
public class CustomWebViewClient extends WebViewClient {

    private WebResourceRequest _request = null;
    private final ContentLoadingProgressBar _progressBar;
    private final MainActivity _mainActivity;

    CustomWebViewClient(MainActivity mainActivity, ContentLoadingProgressBar progressBar) {
        _mainActivity = mainActivity;
        _progressBar = progressBar;
    }

    // Запускается, когда начинается загрузка страницы
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        _mainActivity.runOnUiThread(() -> {
            _progressBar.setVisibility(View.VISIBLE);
        });
    }

    // Запускается, когда пользователь нажал на ссылку на странице или произошол редирект на другую страницу
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        _request = request;
        // Перейти по ссылке, на которую нажал (или произошёл редирект)
        view.loadUrl(_request.getUrl().toString());
        return true;
    }

    // Запускается, когда заканчивается загрузка страницы
    @Override
    public void onPageFinished(WebView view, String url) {
        _mainActivity.runOnUiThread(() -> {
            _progressBar.setVisibility(View.GONE);
        });
    }
}