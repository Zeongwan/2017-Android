package com.example.d_.myapplication4;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DynamicReceiver extends BroadcastReceiver {
    static final String DYNAMICATION = "DYNAMICATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
        Bundle bundle = intent.getExtras();

        if (intent.getAction().equals(DYNAMICATION)) {
            Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show();
            remoteViews.setTextViewText(R.id.appwidget_text, bundle.getString("text"));
            remoteViews.setImageViewResource(R.id.appwidget_pic, R.drawable.dynamic);
            // 要以当前的类Widget.class作为基准
            ComponentName componentName = new ComponentName(context, WidgetDemo.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
            // AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, AppWidgetManager.getInstance(context).getClass()), remoteViews);
        }
    }
}
