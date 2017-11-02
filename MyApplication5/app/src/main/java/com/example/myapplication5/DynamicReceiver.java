package com.example.myapplication5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class DynamicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (intent.getAction().equals("DYNAMICTION")) {
            Bundle bundle = intent.getExtras();
            String name = bundle.getString("name");
            bundle.putInt("isCart", 1);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            remoteViews.setTextViewText(R.id.widgetText, name + "已添加至购物车!");
            remoteViews.setImageViewResource(R.id.widgetPic, bundle.getInt("picId"));
            // 要以当前的类Widget.class作为基准
            ComponentName componentName = new ComponentName(context, MyWidget.class);
            Intent clickInt = new Intent(context, MainActivity.class);
            clickInt.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickInt, FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetPic, pendingIntent);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
    }
}
