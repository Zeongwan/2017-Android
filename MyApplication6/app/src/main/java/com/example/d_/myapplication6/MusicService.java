package com.example.d_.myapplication6;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class MusicService extends Service {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public MusicService() {
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/Will-Melt.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 101:
                    // 播放按钮
                    break;
                case 102:
                    // 停止
                    break;
                case 103:
                    // 退出
                    break;
                case 104:
                    // 刷新界面，刷新时间
                    break;
                case 105:
                    // 拖动进度条
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}