package com.example.midapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.io.IOException;

public class MusicService extends Service {
    private static MediaPlayer mp = new MediaPlayer();
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
                    // 播放暂停按钮
                    if (mp.isPlaying()) {
                        mp.pause();
                    } else {
                        mp.start();
                    }
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
