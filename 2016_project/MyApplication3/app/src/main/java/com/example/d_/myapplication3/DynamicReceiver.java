package com.example.d_.myapplication3;

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
import android.widget.Toast;

public class DynamicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals("DYNAMICACTION")) {
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamic);
            //Toast.makeText(context, intent.getExtras().getString("text"), Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("动态广播")
                    .setContentText(bundle.getString("text"))
                    .setLargeIcon(bm)
                    .setSmallIcon(R.drawable.dynamic)
                    .setTicker("一条新的动态广播")
                    .setAutoCancel(true);
            Intent newIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = builder.build();
            manager.notify(0, notify);
        }
    }
}
