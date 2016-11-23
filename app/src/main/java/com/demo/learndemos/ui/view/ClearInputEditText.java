package com.demo.learndemos.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.demo.learndemos.R;

/**
 * Created by zyf on 2016/11/16.
 */

public class ClearInputEditText extends EditText {

  private Drawable drawable;
  private Drawable clearEmptyDrawable;
  //清除图标的资源id
  private int clearInputRes;
  //是否显示清除图标的标志位
  private boolean showClearIcon;

  private int iconHeight;
  private boolean shouldShowClearIcon = false;

  public ClearInputEditText(Context context) {
    super(context);
    init(context);
  }

  public ClearInputEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = context.obtainStyledAttributes(R.styleable.ClearInputEditText);
    clearInputRes =
        typedArray.getInt(R.styleable.ClearInputEditText_IconVisible, R.drawable.icon_clear_input);
    showClearIcon = typedArray.getBoolean(R.styleable.ClearInputEditText_showClearIcon, true);
    iconHeight = typedArray.getInt(R.styleable.ClearInputEditText_clearIconHeight, 20);
    init(context);
  }

  private void init(Context context) {
    clearEmptyDrawable = new ColorDrawable();
    clearEmptyDrawable.setBounds(0, 0, 0, 0);
    drawable = getResources().getDrawable(clearInputRes);
    int drawableHeight = drawable.getIntrinsicHeight();
    int drawableWidth = drawable.getIntrinsicWidth();
    int intrinsicHeight = dp2pix(iconHeight);
    drawable.setBounds(0, 0, (intrinsicHeight * drawableWidth / drawableHeight), intrinsicHeight);
    addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        shouldShowClearIcon = s.toString().length() != 0;
        invalidate();
      }
    });
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int compoundPaddingRight = getCompoundPaddingRight();
    int compoundPaddingBottom = getCompoundPaddingBottom();
    int scrollX = getScrollX();
    int width = getWidth();
    int scrollY = getScrollY();
    int height = getHeight();
    canvas.save();
    canvas.translate(scrollX + width - compoundPaddingRight - drawable.getBounds().right,
        height + scrollY - compoundPaddingBottom - drawable.getBounds().bottom);
    drawClearIcon(canvas);
    canvas.restore();
  }

  private void drawClearIcon(Canvas canvas) {
    if (shouldShowClearIcon) {
      drawable.draw(canvas);
    } else {
      clearEmptyDrawable.draw(canvas);
    }
    invalidate();
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    //当ActionUp被回调时，判断点击位置是否位于清除图标内，如果位于，则进行清除操作
    if (event.getAction() == MotionEvent.ACTION_UP) {
      float x = event.getX();
      float y = event.getY();
      boolean isInnerClick = x < getWidth() + getScrollX() - getCompoundPaddingRight()
          && x > getWidth() + getScrollX() - getCompoundPaddingRight() - drawable.getBounds().right
          && y < getHeight() + getScrollY() - getCompoundPaddingBottom()
          && y
          > getHeight() + getScrollY() - getCompoundPaddingBottom() - drawable.getBounds().bottom;

      if (isInnerClick) {
        setText("");
      }
    }

    return super.onTouchEvent(event);
  }

  private int dp2pix(int dp) {
    return (int) (dp * getResources().getDisplayMetrics().density);
  }
}
