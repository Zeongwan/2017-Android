package com.example.d_.myapplication6;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    // 申请动态权限
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    private SimpleDateFormat currentTime = new SimpleDateFormat("mm:ss");
    private ServiceConnection serviceConnection;
    MusicService.MyBinder myBinder;
    private MusicService musicService;
    private int code;
    private Parcel data = Parcel.obtain();
    private Parcel reply = Parcel.obtain();
    // 标记是否在播放音乐
    boolean isPlaying = false;

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
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        // 动画设置匀速
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        objectAnimator.setInterpolator(linearInterpolator);
        verifyStoragePermissions(this);

        // 绑定service
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = (MusicService.MyBinder) iBinder;
                musicService = myBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);

        final Time time = new Time(0);
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("1", "hello world");
        }

//        //为什么这里musicService是空，要用一次transact么？
//        endTime.setText(currentTime.format(musicService.getTotalProgress()));
//        seekBar.setMax(musicService.getTotalProgress());

        // 在主进程里面申请新的线程，可以更改UI
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                // 每秒向服务发送请求
                super.handleMessage(message);
                try {
                    code = 104;
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 新线程设置UI
                endTime.setText(currentTime.format(musicService.getTotalProgress()));
                seekBar.setMax(musicService.getTotalProgress());
                seekBar.setProgress(musicService.getPlayProgress());
                time.setTime(musicService.getPlayProgress());
                startTime.setText(currentTime.format(time));
            }
        };
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (serviceConnection != null)
                        handler.obtainMessage(110).sendToTarget();
                }
            }
        };
        final Thread thread = new Thread(runnable);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread.isAlive() == false) {
                    thread.start();
                    objectAnimator.start();
                }
                // 音乐是否在播放
                if (isPlaying == false) {
                    if (objectAnimator.isRunning())
                        objectAnimator.resume();
                    else
                        objectAnimator.start();
                    status.setText("Playing");
                    playButton.setText("Pause");
                    isPlaying = true;
                } else {
                    objectAnimator.pause();
                    status.setText("Pause");
                    playButton.setText("Play");
                    isPlaying = false;
                }
                try {
                    code = 101;
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    code = 102;
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                objectAnimator.end();
                status.setText("Stop");
                playButton.setText("Play");
                isPlaying = false;
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出的时候取消绑定服务
                unbindService(serviceConnection);
                serviceConnection = null;
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
                try {
                    code = 105;
                    Parcel newData = Parcel.obtain();
                    newData.writeInt(seekBar.getProgress());
                    myBinder.transact(code, newData, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
