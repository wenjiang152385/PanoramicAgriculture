package com.oraro.panoramicagriculture.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.oraro.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Administrator on 2017/5/18 0018.
 *
 * @author
 */
public class MySlidingMenu extends SlidingMenu {
    private MyMapView mMyMapView;
    public MySlidingMenu(Context context) {
        super(context);
    }

    public MySlidingMenu(Activity activity, int slideStyle) {
        super(activity, slideStyle);
    }

    public MySlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setMyMapView(MyMapView myMapView){
        mMyMapView = myMapView;
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
                    if(getLeft() == -getWidth()){
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
                    if(getLeft() == -getWidth()){
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
        return super.onTouchEvent(event);
    }
}
