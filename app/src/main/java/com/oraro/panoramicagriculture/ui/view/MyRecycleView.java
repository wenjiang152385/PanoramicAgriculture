package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public class MyRecycleView extends RecyclerView {

    private int mLastPosition;
    private ItemChangedListener mItemChangedListener;

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    public void setItemChangedListener(ItemChangedListener itemChangedListener) {
        mItemChangedListener = itemChangedListener;
    }

    public void unRegisterItemChangedListener() {
        mItemChangedListener = null;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        int needPosition = -1;
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();

            if (lastItemPosition == firstItemPosition) {
                needPosition = firstItemPosition;
                if (needPosition > mLastPosition) {
                    if (null != mItemChangedListener)
                        mItemChangedListener.getItemPosition(needPosition , 1);
                }else if (needPosition < mLastPosition){
                    if (null != mItemChangedListener)
                        mItemChangedListener.getItemPosition(needPosition , -1);
                }
                mLastPosition = needPosition;
            }


        }
    }

    public interface ItemChangedListener {
        void getItemPosition(int position,int direction);
    }
}
