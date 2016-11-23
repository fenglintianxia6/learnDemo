package com.demo.learndemos.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demo.learndemos.R;

/**
 * Created by zyf on 2016/10/21.
 */

public class ShapeDrawableActivity extends Activity {

    public EditText etInputAngle;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ShapeDrawableActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_drawable);
        etInputAngle = (EditText) findViewById(R.id.et_angle_input);
    }

    public void applyRectBg(View view) {
        view.setBackgroundResource(R.drawable.drawable_shape_rect);
    }

    public void applyGradintBg(View view) {
        Button btn = (Button) view;
        view.setBackgroundResource(R.drawable.drawable_shape_gradint);
        btn.setTextColor(Color.WHITE);
    }

    public void applyStrokeBg(View view) {
        view.setBackgroundResource(R.drawable.drawable_rect_stroke);
    }


}
