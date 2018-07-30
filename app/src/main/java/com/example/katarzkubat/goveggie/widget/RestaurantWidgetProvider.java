package com.example.katarzkubat.goveggie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.ui.DetailActivity;
import com.example.katarzkubat.goveggie.ui.MainActivity;

public class RestaurantWidgetProvider extends AppWidgetProvider {

    public static RemoteViews buildRemoteView(Context context, int widgetId) {

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.restaurant_widget_layout);
        Intent intent = new Intent(context, RemoteListService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        Intent openAppIntent = new Intent(context, MainActivity.class);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, 0);
        view.setOnClickPendingIntent(R.id.widget_title_label, openAppPendingIntent);

        view.setRemoteAdapter(R.id.widget_restaurant_list, intent);

        Intent openDetailIntent = new Intent(context, DetailActivity.class);
        PendingIntent openDetailPendingIntent = PendingIntent.getActivity(context, 0, openDetailIntent, 0);
        view.setPendingIntentTemplate(R.id.widget_restaurant_list, openDetailPendingIntent);

        return view;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews rv = buildRemoteView(context, appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_restaurant_list);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = buildRemoteView(context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
