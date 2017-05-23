package com.oraro.panoramicagriculture.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.utils.CommonUtils;


/**
 * Created by Administrator on 2016/12/21.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener {
    private Button aMap;
    private Button baiduMap;

    private FrameLayout mActivityWelcomeFl;

    private ImageView mActivityWelcomeBgImg;

    private Bitmap mActivityWelcomeBmp = null;

    private Handler mHandler = new Handler();

    private ScaleAnimation localScaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);
        aMap = (Button) findViewById(R.id.amap_btn);
        baiduMap = (Button) findViewById(R.id.baidu_btn);
        aMap.setOnClickListener(this);
        baiduMap.setOnClickListener(this);


        mActivityWelcomeBgImg = (ImageView) findViewById(R.id.activity_welcome_bg_img);
        mActivityWelcomeBmp = CommonUtils.decodeBitmap(
                this,
                R.mipmap.bg_welcome,
                getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels);
        if(mActivityWelcomeBmp != null){
            mActivityWelcomeBgImg.setImageBitmap(mActivityWelcomeBmp);
        }

        mActivityWelcomeFl = (FrameLayout) findViewById(R.id.activity_welcome_fl);
        localScaleAnimation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation.setDuration(800L);
        mActivityWelcomeFl.startAnimation(localScaleAnimation);

        localScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, OrdinaryMapActivity.class));
                        finish();
                    }
                },1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amap_btn:
                startActivity(new Intent(WelcomeActivity.this, OrdinaryMapActivity.class));
                break;
            case R.id.baidu_btn:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mActivityWelcomeBmp != null){
            if(!mActivityWelcomeBmp.isRecycled()){
                mActivityWelcomeBmp.recycle();
            }
            mActivityWelcomeBmp = null;
        }

        mHandler.removeCallbacksAndMessages(null);
    }
}
