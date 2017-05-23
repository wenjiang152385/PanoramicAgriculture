package com.oraro.panoramicagriculture.ui.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by admin on 2017/3/31.
 */
public class MyTouchListener implements View.OnTouchListener {
    private View sliderView;
    private View animateView;
    private float mDownX, mUpX;
    private boolean IsShowButton;
    public MyTouchListener(View sliderView, View animateView) {
        this.sliderView = sliderView;
        this.animateView = animateView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();

                if (animateView != null) {
                    //如果手指按下和抬起的水平距离的绝对值>25,则认为发生了滑动
                    if (Math.abs(mDownX - mUpX) > 100) {
                        //如果当前按钮是显示，则隐藏，反之，则显示
                        if (IsShowButton) {
                            //隐藏按钮的渐变动画
                            //HideAnimation(animateView);
                            animateView.setVisibility(View.GONE);
                            IsShowButton = false;
                        } else {
                            //显示按钮的渐变动画
                            //ShowAnimation(animateView);
                            animateView.setVisibility(View.VISIBLE);
                            IsShowButton = true;
                        }
                        //获取当前项目中的删除按钮，便于执行下一步
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mUpX = event.getX();
                break;
        }
        return true;
    }

    //显示按钮的渐变动画
    public void ShowAnimation(View v) {
        Animation Ani_Alpha = new AlphaAnimation(0.1f, 1.0f);
        Ani_Alpha.setDuration(1000);
        v.setAnimation(Ani_Alpha);

    }

    //隐藏按钮的渐变动画
    public void HideAnimation(View v) {
        Animation Ani_Alpha = new AlphaAnimation(1.0f, 0.1f);
        Ani_Alpha.setDuration(1000);
        v.setAnimation(Ani_Alpha);
    }

};
