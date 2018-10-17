package com.example.alexey.simpledraw;
import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * Created by Alexey on 21.12.2017.
 * Класс-помошник для работы с JSON.
 */
class JSONHelper
{
    String fileName;


    /**Сериализация.*/
    boolean exportToJSON(Context context, List<Data> dataList) {
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setData(dataList);

        String jsonString = gson.toJson(dataItems);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } // try-catch
            } // if
        } // try-catch-finally

        return false;
    } // exportToJSON


    /**Десереализация.*/
    List<Data> importFromJSON(Context context) {
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileName);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();

            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return dataItems.getData();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } // try-catch
            } // if
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } // try-catch
            } // if
        } // try-catch.finally

        return null;
    } // importFromJSON


    /**Вспомогательный класс для упрощения работы с данными.*/
    private static class DataItems
    {
        private List<Data> dataList;

        List<Data> getData() {
            return dataList;
        } // getData
        void setData(List<Data> dataList) {
            this.dataList = dataList;
        } // setData
    } // DataItems
} // JSONHelper