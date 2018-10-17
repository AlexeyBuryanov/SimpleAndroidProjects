package com.alexeyburyanov.simpleaudioplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Разработать приложение "Музыкальный проигрыватель", которое содержит плэйлист и позволяет
 * проигрывать музыку из него.
 * Функции:
 * - Загрузка плэйлиста (папки с файлами)
 * - старт, пауза, продожить, стоп
 * - перемещение по трэку при помощи SeekBar
 * - TODO: громче / тише
 * - TODO: аудио-фокус
 * - прогрывание следующей композиции в плэйлисте после окончания текущей
 * */
public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private boolean _isPlay = false, _isLoad = false, _isPause = false;
    private MediaPlayer _mediaPlayer;
    private SeekBar _seekBarTrack;
    private ImageButton _buttonPlay;
    private Timer _timer = new Timer();
    private ListView _listViewTracks;
    private ArrayAdapter<String> _adapterListView;
    private ArrayList<String> _adapterList = new ArrayList<>();
    private int[] _musicRes = new int[] {
            R.raw.dimension_pull_me_under, R.raw.duranduran,
            R.raw.the_prototypes_kill_the_silence, R.raw.zhu_jet_the_doberman
    };
    private TextView _textViewCurrentPlay;
    private int _currentPos = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        _seekBarTrack = findViewById(R.id.seekBarTrack);
        _seekBarTrack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (_mediaPlayer != null && fromUser) {
                    _mediaPlayer.seekTo(progress);
                    _isPlay = !_isPlay;
                    onClickPlayPause(null);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        _buttonPlay = findViewById(R.id.buttonPlay);
        _listViewTracks = findViewById(R.id.listViewTracks);
        _adapterListView = new ArrayAdapter(this, android.R.layout.simple_list_item_1, _adapterList);
        _listViewTracks.setAdapter(_adapterListView);
        _listViewTracks.setOnItemClickListener((parent, view, position, id) -> {
            String fileName = _adapterListView.getItem(position);
            if (_listViewTracks.isItemChecked(position)) {
                _currentPos = position;
                _listViewTracks.clearChoices();
                // Воспроизводим трек
                playFile(new File(getApplicationContext().getFilesDir().getPath()
                        .replace("files","music/")+fileName));
                _textViewCurrentPlay.setText(fileName);
            } // if
        }); // OnItemClickListener
        showFiles();
        _textViewCurrentPlay = findViewById(R.id.textViewCurrentPlay);
    }

    private void showFiles() {
        _adapterListView.clear();

        File[] files = findFiles(new File(getApplicationContext().getFilesDir().getPath()
                .replace("files","music/")));
        if (files == null) {
            return;
        } // if
        // ADD MUSIC HERE
        //copyMusic();

        for (File file : files) {
            if (!file.isDirectory()) {
                _adapterListView.add(file.getName());
            } // if
        } // foreach
    }

    private void copyMusic() {
        for (int i = 0; i < _musicRes.length; i++) {
            InputStream is = getApplicationContext().getResources().openRawResource(_musicRes[i]);
            String path = getApplicationContext().getFilesDir().getPath()
                    .replace("files","music/");
            String outFileName = path + "MusicTrack" + (i+1) + ".mp3";

            File f = new File(outFileName);
            if (f.exists()) {
                f.delete();
            } else {
                File f2 = new File(path);
                f2.mkdir();
            } // if

            OutputStream myOutput = null;
            try {
                myOutput = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } // try
            byte[] buffer = new byte[102400];
            int length;
            try {
                while ((length = is.read(buffer)) > 0) {
                    if (myOutput != null) {
                        myOutput.write(buffer, 0, length);
                    } // if
                } // while
                if (myOutput != null) {
                    myOutput.flush();
                } // if
                if (myOutput != null) {
                    myOutput.close();
                } // if
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // try
        } // for i
    }

    private File[] findFiles(File directory) {
        File[] files = directory.listFiles();
        assert files != null;
        return files;
    }

    private void playFile(File file) {
        releaseMP();
        _timer.cancel();
        _timer = new Timer();

        _mediaPlayer = MediaPlayer.create(this, Uri.fromFile(file));
        _seekBarTrack.setMax(_mediaPlayer.getDuration());

        File[] files = findFiles(new File(getApplicationContext().getFilesDir().getPath()
                .replace("files","music/")));
        if (files == null) {
            return;
        } // if
        if (_currentPos != files.length) {
            // Установка слушателя для окончания проигрывания
            _mediaPlayer.setOnCompletionListener(mp -> {
                if (_currentPos == files.length-1) return;
                _currentPos = _currentPos+1;
                playFile(files[_currentPos]);
            });
        } else {
            reset();
            return;
        }

        _buttonPlay.setBackgroundResource(R.mipmap.ic_pause_circle_filled_black_24dp);
        _isPlay = true;
        _isLoad = true;
        _mediaPlayer.start();

        runOnUiThread(() -> _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    _seekBarTrack.setProgress(_mediaPlayer.getCurrentPosition());
                } catch (java.lang.IllegalStateException e) {
                    reset();
                } // try-catch
            }
        },0,1000));
        _textViewCurrentPlay.setText(files[_currentPos].getName());
    }

    private void releaseMP() {
        if (_mediaPlayer != null) {
            try {
                // Освобождение ресурсов _mediaPlayer
                _mediaPlayer.release();
                _mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // if
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Освобождение памяти, занятой проигрывателями
        releaseMP();
    }

    private void reset() {
        releaseMP();
        _isPlay = false;
        _isLoad = false;
        _isPause = false;
        _buttonPlay.setBackgroundResource(R.mipmap.ic_play_circle_filled_black_24dp);
        _timer.cancel();
        _timer = new Timer();
        _textViewCurrentPlay.setText("-");
        runOnUiThread(() -> _seekBarTrack.setProgress(0));
    }

    public void onClickStop(View view) {
        if (_mediaPlayer != null) _mediaPlayer.stop();;
        reset();
    }

    public void onClickPlayPause(View view) {
        if (!_isLoad && _mediaPlayer == null) {
            _currentPos = 1;
            File[] files = findFiles(new File(getApplicationContext().getFilesDir().getPath()
                    .replace("files","music/")));
            if (files == null) {
                return;
            } // if
            playFile(files[_currentPos]);
            _textViewCurrentPlay.setText("MusicTrack2.mp3");
            _isLoad = true;
        } // if

        if (!_isPlay && _mediaPlayer != null) {
            if (!_mediaPlayer.isPlaying())
                _mediaPlayer.start();

            runOnUiThread(() -> _timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        _seekBarTrack.setProgress(_mediaPlayer.getCurrentPosition());
                    } catch (java.lang.IllegalStateException e) {
                        reset();
                    } // try-catch
                }
            },0,1000));

            _buttonPlay.setBackgroundResource(R.mipmap.ic_pause_circle_filled_black_24dp);
            _isPlay = true;
        } else if (_isPause && _mediaPlayer != null) {
            _mediaPlayer.pause();
            _buttonPlay.setBackgroundResource(R.mipmap.ic_play_circle_filled_black_24dp);
            _isPlay = false;
            _isPause = true;
        } // if
    }

    public void onClickNext(View view) {
        File[] files = findFiles(new File(getApplicationContext().getFilesDir().getPath()
                .replace("files","music/")));
        if (files == null) {
            return;
        } // if
        if (_currentPos == files.length-1) return;
        _currentPos = _currentPos+1;
        playFile(files[_currentPos]);
    }

    public void onClickPrev(View view) {
        File[] files = findFiles(new File(getApplicationContext().getFilesDir().getPath()
                .replace("files","music/")));
        if (files == null) {
            return;
        } // if
        if (_currentPos == 0) return;
        _currentPos = _currentPos-1;
        playFile(files[_currentPos]);
    }
}