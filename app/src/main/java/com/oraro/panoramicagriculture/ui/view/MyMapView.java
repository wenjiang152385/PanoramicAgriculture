package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.amap.api.maps2d.MapView;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017.03.27.
 */

public class MyMapView extends MapView {
    public MyMapView(Context context) {
        super(context);
    }

    public MyMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean mIsDrwaerScroll = false;
    public void setDrwaerScroll(boolean b){
        //LogUtils.e("zxl--->MyMapView--->setFlag--->"+b);
        mIsDrwaerScroll = b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //LogUtils.e("zxl--->MyMapView--->dispatchTouchEvent--->"+mIsDrwaerScroll);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //LogUtils.e("zxl--->MyMapView--->dispatchTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //LogUtils.e("zxl--->MyMapView--->dispatchTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //LogUtils.e("zxl--->MyMapView--->dispatchTouchEvent--->ACTION_UP");
                break;
        }
        //如果是在拖拽抽屉滑动，则地图层不接收滑动事件
        if(mIsDrwaerScroll){
            return  false;
        }else{
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //LogUtils.e("zxl--->MyMapView--->onInterceptTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //LogUtils.e("zxl--->MyMapView--->onInterceptTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //LogUtils.e("zxl--->MyMapView--->onInterceptTouchEvent--->ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //LogUtils.e("zxl--->MyMapView--->onTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //LogUtils.e("zxl--->MyMapView--->onTouchEvent--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //LogUtils.e("zxl--->MyMapView--->onTouchEvent--->ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
