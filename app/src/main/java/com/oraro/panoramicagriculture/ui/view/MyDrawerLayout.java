package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017.03.27.
 */

public class MyDrawerLayout extends DrawerLayout {
    public MyDrawerLayout(Context context) {
        super(context);
    }

    private MyMapView mMyMapView;
    //侧滑栏控件
    private LinearLayout mNavigationView;

    public void setMyMapView(MyMapView myMapView){
        mMyMapView = myMapView;
    }

    public void setNavigationView(LinearLayout navigationView){
        mNavigationView = navigationView;
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //LogUtils.e("zxl--->MyDrawerLayout--->dispatchTouchEvent--->"+mNavigationView.getWidth()+"--->"+mNavigationView.getLeft());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //LogUtils.e("zxl--->MyDrawerLayout--->dispatchTouchEvent--->ACTION_DOWN--->"+ev.getX());
                if(ev.getX() < 50){
                    mMyMapView.setDrwaerScroll(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //LogUtils.e("zxl--->MyDrawerLayout--->dispatchTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //LogUtils.e("zxl--->MyDrawerLayout--->dispatchTouchEvent--->ACTION_UP ACTION_CANCEL");
                mMyMapView.setDrwaerScroll(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //LogUtils.e("zxl--->MyDrawerLayout--->onInterceptTouchEvent--->ACTION_DOWN");
                if(ev.getX() < 50){
                    mMyMapView.setDrwaerScroll(true);
                }else{
                    //如果一开始滑动过快导致距离左边过大，则侧滑栏不响应触摸事件
                    if(mNavigationView.getLeft() == -mNavigationView.getWidth()){
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //LogUtils.e("zxl--->MyDrawerLayout--->onInterceptTouchEvent--->ACTION_MOVE");
                if(ev.getX() < 50){
                    mMyMapView.setDrwaerScroll(true);
                }else{
                    //如果一开始滑动过快导致距离左边过大，则侧滑栏不响应触摸事件
                    if(mNavigationView.getLeft() == -mNavigationView.getWidth()){
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //LogUtils.e("zxl--->MyDrawerLayout--->onInterceptTouchEvent--->ACTION_UP");

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //LogUtils.e("zxl--->MyDrawerLayout--->onTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //LogUtils.e("zxl--->MyDrawerLayout--->onTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //LogUtils.e("zxl--->MyDrawerLayout--->onTouchEvent--->ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
