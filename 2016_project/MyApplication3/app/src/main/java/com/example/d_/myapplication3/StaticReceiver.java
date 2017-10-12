package com.example.d_.myapplication3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class StaticReceiver extends BroadcastReceiver {
    private  static final String STATICTION = "STATICTION";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals("STATICTION")) {
            String name = bundle.getString("name");
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), bundle.getInt("pic"));
            Toast.makeText(context, intent.getExtras().getString("name"), Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("静态广播")
                    .setContentText(bundle.getString("name"))
                    .setLargeIcon(bm)
                    .setSmallIcon(bundle.getInt("pic"))
                    .setTicker("一条新的静态广播")
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
