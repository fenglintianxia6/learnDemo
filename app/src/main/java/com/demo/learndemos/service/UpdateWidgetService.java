package com.demo.learndemos.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.demo.learndemos.R;
import com.demo.learndemos.provider.ClockWidgetProvider;

import java.text.SimpleDateFormat;

/**
 * Created by zyf on 2016/10/20.
 */

public class UpdateWidgetService extends Service {

    private static final int ALARM_DURATION = 5 * 60 * 1000;//service的自启动间隔
    private static final int UPDATE_DURATION = 1000;
    private static final int UPDATE_MESSAGE = 0;
    public static final String TAG = UpdateWidgetService.class.getSimpleName();

    private UpdateHandler updateHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        updateHandler = new UpdateHandler();
        updateHandler.sendEmptyMessageDelayed(UPDATE_MESSAGE, UPDATE_DURATION);
    }


    private void updateWidget() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_clock);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        long l = System.currentTimeMillis();
        String format = simpleDateFormat.format(l);
        String hour = format.substring(0, 2);
        String minute = format.substring(2, 4);
        String second = format.substring(4);
        remoteViews.setTextViewText(R.id.tv_hour, hour);
        remoteViews.setTextViewText(R.id.tv_minute, minute);
        remoteViews.setTextViewText(R.id.tv_second, second);
        AppWidgetManager manager = (AppWidgetManager) getSystemService(APPWIDGET_SERVICE);
        manager.updateAppWidget(new ComponentName(getPackageName(), ClockWidgetProvider.class.getName()), remoteViews);
        updateHandler.sendEmptyMessageDelayed(UPDATE_MESSAGE, UPDATE_DURATION);
    }

    public class UpdateHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MESSAGE: {
                    updateWidget();
                    break;
                }
            }
        }
    }

}
