package com.demo.learndemos.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.learndemos.R;

/**
 * Created by zyf on 2016/10/20.
 */

public class DrawableCollectionActivity extends Activity {

    private TextView tvViewInfo;

    public static void start(Context context) {
        context.startActivity(new Intent(context, DrawableCollectionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_collection);
        tvViewInfo = (TextView) findViewById(R.id.tv_view_info);
    }

    public void testBitmapdrawables(View view) {
        tvViewInfo.setBackgroundResource(R.drawable.drawable_bitmap);
    }

    public void shapeDrawable(View view) {
        ShapeDrawableActivity.start(this);
    }

}
