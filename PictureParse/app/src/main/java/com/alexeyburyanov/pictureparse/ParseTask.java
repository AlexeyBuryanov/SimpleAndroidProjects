package com.alexeyburyanov.pictureparse;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexey on 01.03.2018.
 */
public class ParseTask extends AsyncTask<Object, Object, Object> {

    private final ContentLoadingProgressBar _progressBar;
    private final String _urlPath;
    private final MainActivity _mainActivity;
    private final EditText _etUrlPage;
    private final FloatingActionButton _fab;
    private final CustomAdapterGridView _adapterGridView;
    private List<Item> items = new LinkedList<>();

    ParseTask(String urlPath, ContentLoadingProgressBar progressBar, MainActivity mainActivity,
              EditText etUrlPage, FloatingActionButton fab, CustomAdapterGridView adapterGridView) {
        _urlPath = urlPath;
        _progressBar = progressBar;
        _mainActivity = mainActivity;
        _etUrlPage = etUrlPage;
        _fab = fab;
        _adapterGridView = adapterGridView;
    }

    protected Object doInBackground(Object... arg0) {
        doit();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        _mainActivity.runOnUiThread(() -> {
            _progressBar.setVisibility(View.GONE);
            _etUrlPage.setEnabled(true);
            _fab.setEnabled(true);
        });
    }

    private void doit() {
        _mainActivity.runOnUiThread(() -> {
            _progressBar.setVisibility(View.VISIBLE);
            _etUrlPage.setEnabled(false);
            _fab.setEnabled(false);
            _adapterGridView.clear();
        });

        try {
            Document doc  = Jsoup.connect(_urlPath).get();
            Elements imgElements = doc.select("img");
            for (int i = 0; i < imgElements.size(); i++) {
                String imgSrc = imgElements.get(i).attr("src");
                if (imgSrc.endsWith(".png") ||
                    imgSrc.endsWith(".jpg") ||
                    imgSrc.endsWith(".jpeg") ||
                    imgSrc.endsWith(".gif")) {
                    String src = _urlPath+imgSrc;
                    if (!imgSrc.contains(_urlPath))
                        src = imgSrc;
                    downloadPic(src, i);
                    String imagePath = MessageFormat.format("{0}/Pictures/picture{1}.jpg",
                            Environment.getExternalStorageDirectory().getPath(), i);
                    // TODO: nulls
                    Item item = new Item(imagePath, "picture"+ i);
                    item.set_icon(imagePath);
                    item.set_picName("picture"+ i);
                    items.add(item);
                } // if
            } // foreach
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadPic(String imgSrc, int index) {
        int count;

        try {
            // Класс хранит адрес ресурса в инете
            URL url = new URL(imgSrc);
            // Создать соединение с хостом по адресу
            URLConnection connection = url.openConnection();
            // Установить соединение
            connection.connect();
            // Получить длину запрашиваемого файла
            int lenghtOfFile = connection.getContentLength();
            // Получить поток для чтения информации
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            // Открыть файловый поток для записи картинки
            OutputStream output = new FileOutputStream(MessageFormat.format("{0}/Pictures/picture{1}.jpg",
                    Environment.getExternalStorageDirectory().getPath(), index));
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception ignored) {}
    }
}