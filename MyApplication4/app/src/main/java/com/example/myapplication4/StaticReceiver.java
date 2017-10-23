package com.example.myapplication4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class StaticReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("STATICTION")) {
            Bundle bundle = intent.getExtras();
            String name = bundle.getString("name");
            String price = bundle.getString("price");
            String type = bundle.getString("type");
            String info = bundle.getString("info");
            int picId = bundle.getInt("picId");
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), picId);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("新商品热卖")
                    .setContentText(name + "仅售" + price)
                    .setLargeIcon(bm)
                    .setSmallIcon(picId)
                    .setTicker("实验4")
                    .setAutoCancel(true);
            Intent newIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = builder.build();
            manager.notify(0, notify);
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
