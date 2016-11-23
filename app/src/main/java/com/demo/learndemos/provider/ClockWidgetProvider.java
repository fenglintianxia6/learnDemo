package com.demo.learndemos.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.demo.learndemos.R;
import com.demo.learndemos.service.UpdateWidgetService;
import com.demo.learndemos.utils.ToastUtils;

import java.text.SimpleDateFormat;

/**
 * Created by zyf on 2016/10/19.
 */

public class ClockWidgetProvider extends AppWidgetProvider {

    public static final String TAG = ClockWidgetProvider.class.getSimpleName();
    public static final String ACTION_CLICK = "action_clock_click";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i(TAG, "onReceive: action === " + action);
        if (TextUtils.equals(ACTION_CLICK, action)) {
            dealClockClick(context, intent);
        }
    }

    /**
     * 处理小部件的点击事件，打开时钟
     *
     * @param context
     * @param intent
     */
    private void dealClockClick(Context context, Intent intent) {
        Intent clockIntent = new Intent();
        clockIntent.setAction(AlarmClock.ACTION_SET_ALARM);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int length = appWidgetIds.length;
        Log.i(TAG, "onUpdate: length === " + length);
        for (int i = 0; i < length; i++) {
            int appWidgetId = appWidgetIds[i];
            updateWidget(context, appWidgetManager, appWidgetId);
        }
        context.startService(new Intent(context, UpdateWidgetService.class));
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_custom_clock);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        long l = System.currentTimeMillis();
        String format = simpleDateFormat.format(l);
        ToastUtils.showToast(context, format);
        String hour = format.substring(0, 2);
        String minute = format.substring(2, 4);
        String second = format.substring(4);
        remoteViews.setTextViewText(R.id.tv_hour, hour);
        remoteViews.setTextViewText(R.id.tv_minute, minute);
        remoteViews.setTextViewText(R.id.tv_second, second);
        Intent intent = new Intent();
        intent.setAction(ACTION_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ll_clock_widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
