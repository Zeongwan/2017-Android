package com.example.d_.myapplication6;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;

import static com.example.d_.myapplication6.R.id.seekBar;

public class MusicService extends Service {
    public static MediaPlayer mp = new MediaPlayer();
    private final IBinder myBinder = new MyBinder();
    public MusicService() {
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory() + "/melt.mp3");
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 返回实例
        // TODO: Return the communication channel to the service.
        return myBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        this.stopSelf();
        super.onDestroy();
    }
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 101:
                    // 播放按钮
                    if (mp.isPlaying()) {
                        mp.pause();
                    } else {
                        mp.start();
                    }
                    break;
                case 102:
                    // 停止
                    mp.stop();
                    try {
                        mp.prepare();
                        mp.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 103:
                    // 退出
                    System.exit(0);
                    break;
                case 104:
                    // 刷新界面，刷新时间
                    break;
                case 105:
                    // 拖动进度条
                    mp.seekTo(data.readInt());
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
    public int getPlayProgress() {
        return mp.getCurrentPosition();
    }
    public int getTotalProgress() {
        return mp.getDuration();
    }
}
