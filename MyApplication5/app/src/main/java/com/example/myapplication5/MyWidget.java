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

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {
    static final String STATICTION = "STATICTION";
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
            remoteViews.setTextViewText(R.id.widgetText, bundle.getString("name") + "仅售" + bundle.getString("price" ));
            remoteViews.setImageViewResource(R.id.widgetPic, bundle.getInt("picId"));
            // 要以当前的类Widget.class作为基准
            ComponentName componentName = new ComponentName(context, MyWidget.class);
            Intent clickInt = new Intent(context, GoodsActivity.class);
            clickInt.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickInt, FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetPic, pendingIntent);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
    }
}

