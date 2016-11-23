package com.demo.learndemos.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.demo.learndemos.R;

/**
 * Created by zyf on 2016/10/20.
 */

public class ViewCollectionActivity extends Activity {

  private TextView tvViewInfo;

  public static void start(Context context) {
    context.startActivity(new Intent(context, ViewCollectionActivity.class));
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_collection);
    tvViewInfo = (TextView) findViewById(R.id.tv_view_info);
  }

  public void viewRect(View view) {
    int left = view.getLeft();
    int right = view.getRight();
    int top = view.getTop();
    int bottom = view.getBottom();
    float translationX = view.getTranslationX();
    float translationY = view.getTranslationY();
    float x = view.getX();
    float y = view.getY();
    int measuredWidth = view.getMeasuredWidth();
    int measuredHeight = view.getMeasuredHeight();
    tvViewInfo.setText("viewRect: left === "
        + left
        + ";right === "
        + right
        + ";width === "
        + measuredWidth
        + "；right - left === "
        + (right - left)
        + ";translationX  === "
        + translationX
        + ";X === "
        + x
        +
        "viewRect: top === "
        + top
        + ";bottom === "
        + bottom
        + ";height === "

        + measuredHeight
        + "；bottom - top === "
        + (bottom - top)
        + ";translationY  === "
        + translationY
        + ";Y === "
        + y);
  }

  public void touchSlop(View view) {
    int scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
    tvViewInfo.setText(
        "scaledTouchSlop为系统认为的最短的滑动距离，如果小于该距离则不认为是滑动。scaledTouchSlop === " + scaledTouchSlop);
  }

  public void showCustomView(View view) {
    GestureViewActivity.start(this);
  }

  public void goTestClick(View view) {
  }
}
