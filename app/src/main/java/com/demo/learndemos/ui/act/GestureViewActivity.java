package com.demo.learndemos.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.learndemos.ui.view.GestureDemoView;

/**
 * Created by zyf on 2016/10/21.
 */

public class GestureViewActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, GestureViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GestureDemoView(this));
    }
}
