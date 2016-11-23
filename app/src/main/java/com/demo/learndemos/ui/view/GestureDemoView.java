package com.demo.learndemos.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.demo.learndemos.R;

/**
 * Created by zyf on 2016/10/20.
 */

public class GestureDemoView extends View {

    private int popTextColor = 0xff000000;
    private Paint paint;
    float measuredWidth;
    float measuredHeight;

    //    private String[] texts = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private String texts = "abcdefghijklmnopqrsuvwxyz";

    public GestureDemoView(Context context) {
        super(context);
        init(context);
    }

    public GestureDemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GestureDemoViewAttrs);
        popTextColor = typedArray.getColor(R.styleable.GestureDemoViewAttrs_PopTextColor, popTextColor);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setTextSize(50);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int length = texts.length();
        for (int i = 0; i < length; i++) {
            char c = texts.charAt(i);
            canvas.drawText(c + "", 0, 1, measuredWidth / 2, measuredHeight / length * (i + 1), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int length = texts.length();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float y = event.getY();
                float averHeight = measuredHeight / length;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float y = event.getY();
                float averHeight = measuredHeight / length;
                int index = (int) (y / averHeight);
                Log.i("TAG", "onTouchEvent: index === " + texts.charAt(index));
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}
