package com.alexeyburyanov.bindbroadcastcompress;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Alexey on 11.03.2018.
 */
public class ZipCompression {

    private static final int BUFFER_SIZE = 1024;

    public static void zip(String[] files, String zipFile) throws IOException {
        BufferedInputStream origin = null;
        try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)))) {
            byte data[] = new byte[BUFFER_SIZE];

            for (String file : files) {
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    } // while
                } finally {
                    origin.close();
                } // try-finally
            } // for i
        } // try
    }

    public static void unzip(String zipFile, String location) throws IOException {
        try {
            File f = new File(location);
            if (!f.isDirectory()) {
                boolean mkdirs = f.mkdirs();
            } // if
            try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            boolean mkdirs = unzipFile.mkdirs();
                        } // if
                    } else {
                        try (FileOutputStream fout = new FileOutputStream(path, false)) {
                            for (int i = zin.read(); i != -1; i = zin.read()) {
                                fout.write(i);
                            } // for i
                            zin.closeEntry();
                        } // try
                    } // if-else
                } // while
            } // try
        } catch (Exception e) {
            Log.e("ZipCompression", "Unzip exception", e);
        } // try-catch
    }
}