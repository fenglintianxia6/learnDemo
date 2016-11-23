package com.demo.learndemos.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.demo.learndemos.R;
import com.demo.learndemos.utils.ToastUtils;

/**
 * Created by zyf on 2016/10/19.
 */

public class MyWeightProvider extends AppWidgetProvider {

    private static final String TAG = MyWeightProvider.class.getSimpleName();
    public static final String ACTION_CLICK = "action_click";

    public MyWeightProvider() {
        super();
    }

    /**
     * 广播的内置方法，收到广播时的操作
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i(TAG, "onReceive: intent.getAction===" + action);
        if (TextUtils.equals(action, ACTION_CLICK)) {
            onReceiveClick(context, intent);
        }
    }

    private void onReceiveClick(final Context context, final Intent intent) {
        ToastUtils.showToast(context, "clicked");
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                for (int i = 0; i < 37; i++) {
                    float degress = (i * 10) % 360;
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_weight);
                    remoteViews.setImageViewBitmap(R.id.iv_weight, rotateBitmap(context, bitmap, degress));
                    Intent clickIntent = new Intent();
                    clickIntent.setAction(ACTION_CLICK);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                    remoteViews.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);
                    manager.updateAppWidget(new ComponentName(context, MyWeightProvider.class), remoteViews);
                    SystemClock.sleep(30);
                }
            }
        }.start();
    }

    private Bitmap rotateBitmap(Context context, Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotateBitmap;
    }

    /**
     * 小部件创建的时候或者每次更新的时候进行调用，更新的时间在@xml/layout_widget_info中的updatePeriodMillions决定
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(TAG, "onUpdate");
        int length = appWidgetIds.length;
        for (int i = 0; i < length; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }


    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG, "onWidgetUpdate: appWidgetId === " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_weight);
        Intent intent = new Intent();
        intent.setAction(ACTION_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * 第一次创建小部件是回调
     *
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 删除小部件时的回调
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 当删除最后一个该应用的小部件时回调
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
