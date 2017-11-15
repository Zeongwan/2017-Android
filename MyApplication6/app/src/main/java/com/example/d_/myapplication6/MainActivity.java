package com.example.d_.myapplication6;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    public static MediaPlayer mp = new MediaPlayer();
    private SimpleDateFormat currentTime = new SimpleDateFormat("mm:ss");
    private ServiceConnection serviceConnection;
    IBinder myBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button playButton = (Button) findViewById(R.id.play);
        final Button stopButton = (Button) findViewById(R.id.stop);
        final Button quitButton = (Button) findViewById(R.id.quit);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView status = (TextView) findViewById(R.id.status);
        final TextView startTime = (TextView) findViewById(R.id.startTime);
        final TextView endTime = (TextView) findViewById(R.id.endTime);
        verifyStoragePermissions(this);
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory() + "/melt.mp3");
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Time time = new Time(0);
        startTime.setText(currentTime.format(time));
        endTime.setText(currentTime.format(mp.getDuration()));
        seekBar.setMax(mp.getDuration());


        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = iBinder;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                super.handleMessage(message);
                seekBar.setProgress(mp.getCurrentPosition());
                startTime.setText(currentTime.format(time));
            }
        };
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (!mp.isPlaying()) {
                            handler.sendMessage(handler.obtainMessage(-1));
                        } else {
                            Thread.sleep(1000);
                            time.setTime(time.getTime() + 1000);
                            handler.sendMessage(handler.obtainMessage());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        final Thread thread = new Thread(runnable);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    mp.pause();
                    playButton.setText("Play");
                    status.setText("Pause");
                } else {
                    mp.start();
                    playButton.setText("Pause");
                    status.setText("Play");
                    if (thread.isAlive() == false) {
                        thread.start();
                    }
                }
                try {
                    int code = 101;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {
                    mp.stop();
                    try {
                        mp.prepare();
                        mp.seekTo(0);
                        time.setTime(0);
                        seekBar.setProgress(0);
                        status.setText("Stop");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
                time.setTime(seekBar.getProgress());
            }
        });
    }
}
