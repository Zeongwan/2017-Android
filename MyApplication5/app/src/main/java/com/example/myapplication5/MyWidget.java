package com.example.myapplication5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {
    static final String STATICTION = "STATICTION";
    static final String DYNAMICTION = "DYNAMICTION";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.widgetText, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            super.onUpdate(context, appWidgetManager, appWidgetIds);
            Intent clickInt = new Intent(STATICTION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickInt, 0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            remoteViews.setOnClickPendingIntent(R.id.widgetPic, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(STATICTION)) {
            remoteViews.setTextViewText(R.id.widgetText, bundle.getString("name"));
            remoteViews.setImageViewResource(R.id.widgetPic, bundle.getInt("picId"));
        }
        // 要以当前的类Widget.class作为基准
        ComponentName componentName = new ComponentName(context, MyWidget.class);
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
    }
}

