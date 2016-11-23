package com.demo.learndemos.ui.act;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.demo.learndemos.R;
import com.demo.learndemos.utils.ToastUtils;

/**
 * Created by zyf on 2016/10/21.
 */

public class AnimationActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, AnimationActivity.class));
    }


    private ImageView ivAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ivAnimation = (ImageView) findViewById(R.id.iv_animation);
    }

    public void tranAnimate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translation_anim);
        ivAnimation.startAnimation(animation);
    }

    public void scaleAnimate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translation_anim);
        ivAnimation.startAnimation(animation);
    }

    public void rotateAnimate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translation_anim);
        ivAnimation.startAnimation(animation);
        ToastUtils.showToast(this, "width === " + ivAnimation.getWidth());
    }

    public void alphaAnimate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translation_anim);
        ivAnimation.startAnimation(animation);
    }

    public void properityAnimate(View view) {
        ViewWraper wraper = new ViewWraper(view);
        ObjectAnimator.ofInt(wraper, "width", 500).setDuration(3000).start();
//        ObjectAnimator.ofFloat(view, "alpha", 0.5f).setDuration(3000).start();
        ToastUtils.showToast(this, "width == " + view.getWidth() + "");
    }

    public static class ViewWraper {
        private View mTarget;

        public ViewWraper(View view) {
            if (view == null) {
                return;
            }
            mTarget = view;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
