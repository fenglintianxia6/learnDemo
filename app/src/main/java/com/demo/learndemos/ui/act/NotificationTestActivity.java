package com.demo.learndemos.ui.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by zyf on 2016/10/19.
 */

public class NotificationTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setHeight(getResources().getDisplayMetrics().heightPixels);
        textView.setWidth(getResources().getDisplayMetrics().widthPixels);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTextColor(0xff000000);
        textView.setText(getClass().getSimpleName());
        setContentView(textView);
    }
}
