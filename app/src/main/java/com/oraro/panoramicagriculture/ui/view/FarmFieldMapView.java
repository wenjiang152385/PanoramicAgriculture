package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.amap.api.maps2d.MapView;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017/4/12.
 */
public class FarmFieldMapView extends MapView {
    public FarmFieldMapView(Context context) {
        super(context);
    }

    public FarmFieldMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() == 1 && (ev.getX() < 100 || getMeasuredWidth() - ev.getX() < 100) && ev.getAction() != MotionEvent.ACTION_UP) {
            return false;
        }
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

}
