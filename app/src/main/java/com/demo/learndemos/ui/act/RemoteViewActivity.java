package com.demo.learndemos.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.learndemos.R;
import com.demo.learndemos.helper.NotificationHelper;

/**
 * Created by zyf on 2016/10/19.
 */

public class RemoteViewActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, RemoteViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view);
    }

    public void showNotification(View view) {
        NotificationHelper.createNotification(this, R.mipmap.ic_launcher);
    }

}
