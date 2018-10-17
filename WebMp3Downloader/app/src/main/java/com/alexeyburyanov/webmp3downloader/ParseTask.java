package com.alexeyburyanov.webmp3downloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Alexey on 05.03.2018.
 */
public class ParseTask extends AsyncTask<Void, Void, Void> {

    private final String _urlPath;
    private final ContentLoadingProgressBar _progressBar;
    private final MainActivity _mainActivity;
    private DownloadManager _downloadManager = null;
    private long _id = 0;

    ParseTask(String urlPath, MainActivity mainActivity, ContentLoadingProgressBar progressBar) {
        _urlPath = urlPath;
        _progressBar = progressBar;
        _mainActivity = mainActivity;
    }

    @Override
    protected Void doInBackground(Void... unused) {
        _mainActivity.runOnUiThread(() -> {
            _progressBar.setVisibility(View.VISIBLE);
        });

        try {
            Document doc  = Jsoup.connect(_urlPath).get();
            Elements h1s = doc.select("td");
            for (int i = 0; i < h1s.size(); i++) {
                String mp3Url = h1s.get(i).attr("item_url");
                if (mp3Url.endsWith(".mp3")) {
                    downloadMusicTrack(mp3Url, i);
                }
            } // foreach
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    @Override
    protected void onPostExecute(Void unused) {
        _mainActivity.runOnUiThread(() -> {
            _progressBar.setVisibility(View.GONE);
            Toast.makeText(_mainActivity, "Загрузка завершена", Toast.LENGTH_SHORT).show();
        });
    }

    private void downloadMusicTrack(String mp3Url, int i) {
        // Формирование запроса для скачивания
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mp3Url));
        // Указать название и описание для пользователя
        request.setDescription("MusicTrack#"+i);
        request.setTitle("MusicTrack#"+i+".mp3");
        // Разрешить сканирование результатов скачивания
        request.allowScanningByMediaScanner();
        // Разрешить уведомление об окончании скачивания
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // Указать место назначения для загрузки и имя файла
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.format("music#%d.mp3", i));
        // Получить ссылку на DownloadManager
        _downloadManager = (DownloadManager)_mainActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        // Поставить в очередь на загрузку
        if (_downloadManager != null) {
            _id = _downloadManager.enqueue(request);
        }
        // Объявить обработчик уведомления об окончании загрузки
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                Toast.makeText(_mainActivity, "Началась загрузка mp3-файлов...", Toast.LENGTH_SHORT).show();
            }
        };
        // Подписка на уведомление об окончании загрузки
        _mainActivity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}