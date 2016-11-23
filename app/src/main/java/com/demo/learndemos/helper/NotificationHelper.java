package com.demo.learndemos.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.demo.learndemos.R;
import com.demo.learndemos.ui.act.NotificationTestActivity;

/**
 * Created by zyf on 2016/10/19.
 */

public class NotificationHelper {

    public static void createNotification(Context context, int iconRes) {
        Notification notification = new Notification();
        notification.icon = iconRes;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(context, NotificationTestActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
        remoteViews.setOnClickPendingIntent(R.id.rl_notification, pendingIntent);
        remoteViews.setTextViewText(R.id.tv_msg, "HelloWorld!");
        remoteViews.setImageViewResource(R.id.iv_icon, iconRes);
        notification.contentIntent = pendingIntent;
        notification.contentView = remoteViews;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

}
