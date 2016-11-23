package com.demo.learndemos.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by zyf on 2016/10/19.
 */

public class ToastUtils {


    private static String lastMsg;
    private static long lastTime;
    private static final long duration = 3000;

    public static void showToast(Context context, String msg) {
        if (TextUtils.isEmpty(msg) || (TextUtils.equals(lastMsg, msg) && lastTime + duration > System.currentTimeMillis())) {

        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            lastTime = System.currentTimeMillis();
            lastMsg = msg;
        }


    }

}
