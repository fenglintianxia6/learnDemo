package com.demo.learndemos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.learndemos.ui.act.AnimationActivity;
import com.demo.learndemos.ui.act.DrawableCollectionActivity;
import com.demo.learndemos.ui.act.RemoteViewActivity;
import com.demo.learndemos.ui.act.ViewCollectionActivity;
import com.demo.learndemos.utils.PhoneShakeListener;

public class MainActivity extends AppCompatActivity {
  PhoneShakeListener listener;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listener = new PhoneShakeListener(this);
  }

  @Override protected void onResume() {
    super.onResume();
    listener.registerSensorListener();
  }

  public void showViewActivity(View view) {
    ViewCollectionActivity.start(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    listener.unRegisterSensorListener();
  }

  public void showNotification(View view) {
    RemoteViewActivity.start(this);
  }

  public void drawableShape(View view) {
    DrawableCollectionActivity.start(this);
  }

  public void Animations(View view) {
    AnimationActivity.start(this);
  }
}
