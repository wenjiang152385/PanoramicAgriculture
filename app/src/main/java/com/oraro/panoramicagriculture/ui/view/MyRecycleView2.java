package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017/3/24 0024.
 *
 * @author
 */
public class MyRecycleView2 extends RecyclerView {

    private Context mContext;

    private int downX;
    private int downY;
    private int lastX;
    private int lastY;
    private int slidePosition;
    private int mLastSlidePosition;
    private static final int deltaY = 20;
    private static final int deltaX = 20;
    private View topLayout;
    private View mLastTopLayout;
    private View bottomLayout;
    private View mLastBottomLayout;

    private boolean bLeftRight = false;
    private boolean bDownUp = false;

    private boolean mCanEdit = false;

    /**
     * 滑动类
     */
    private Scroller scroller;

    private int mTouchSlop;

    public MyRecycleView2(Context context) {
        super(context);
        init(context);
    }

    public MyRecycleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRecycleView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        scroller = new Scroller(context);
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    public void setCanEdit(boolean b){
        mCanEdit = b;
    }

    public void hideEditView(){
        if(mLastTopLayout != null && mLastBottomLayout != null){
            FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) mLastTopLayout.getLayoutParams();
            flParams.leftMargin = 0;
            flParams.rightMargin = 0;
            topLayout.setLayoutParams(flParams);
        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        //由于该recycleview嵌套在scrollview中，并且该recycleview内容充满的
//        // 所有将其触摸滑动拦截不响应，都交给scrollview处理
//        LogUtils.e("yjd onInterceptTouchEvent");
//
//        if(!bLeftRight){
//            requestDisallowInterceptTouchEvent(false);
//            return false;
//        }
//        return super.onInterceptTouchEvent(e);
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

    public int pointToPosition(int x, int y) {
        Rect frame = new Rect();

        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                downX = (int) event.getX();
                downY = (int) event.getY();

                lastX = downX;
                lastY = downY;

                slidePosition = pointToPosition(downX, downY);

                //如果点击其他项，则还原上一次侧滑的项
                if(mLastTopLayout != null && mLastBottomLayout != null && mLastSlidePosition != slidePosition){
                    FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) mLastTopLayout.getLayoutParams();
                    flParams.leftMargin = 0;
                    flParams.rightMargin = 0;
                    mLastTopLayout.setLayoutParams(flParams);
                }

                LogUtils.e("yjd9 MyRecycleView2 dispatchTouchEvent" + slidePosition);
                if (slidePosition >= 0){
                    View view = getChildAt(slidePosition);
                    MyPointDetailView myPointDetailView = (MyPointDetailView)view.findViewById(R.id.id_MyDetailView);
                    if (myPointDetailView != null) {
                        topLayout = myPointDetailView.id_toplayout;
                        bottomLayout = myPointDetailView.id_bottomlayout;
                    }
                }
                if (topLayout != null && bottomLayout != null) {
                    topLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            return;
                        }
                    });
                }
                mLastSlidePosition = slidePosition;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(!mCanEdit){
                    break;
                }
                if (topLayout != null && bottomLayout != null) {
                    int deltaX = (int)event.getX() - lastX;
                    int deltaY = (int)event.getY() - lastY;
                    if(bLeftRight){
                        FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) topLayout.getLayoutParams();
                        if(deltaX < 0){//向左
                            if(flParams.rightMargin < bottomLayout.getWidth()){
                                flParams.leftMargin = flParams.leftMargin + deltaX;
                                flParams.rightMargin = flParams.rightMargin - deltaX;
                                topLayout.setLayoutParams(flParams);
                            }
                        }else if(deltaX > 0){//向右
                            if(flParams.rightMargin > 0){
                                flParams.leftMargin = flParams.leftMargin + deltaX;
                                flParams.rightMargin = flParams.rightMargin - deltaX;
                                topLayout.setLayoutParams(flParams);
                            }
                        }
                    }
                    lastX = (int)event.getX();
                    lastY = (int)event.getY();

                    if(!bLeftRight && !bDownUp){
                        if(Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > mTouchSlop){
                            bLeftRight = true;
                        }
                        if(Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(deltaY) > mTouchSlop){
                            bDownUp = true;

                            //点在相同的已经侧滑出的选项上，如果是上下滑动，则还原其侧滑的部分
                            if(mLastTopLayout != null && mLastBottomLayout != null && mLastSlidePosition == slidePosition){
                                FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) mLastTopLayout.getLayoutParams();
                                flParams.leftMargin = 0;
                                flParams.rightMargin = 0;
                                mLastTopLayout.setLayoutParams(flParams);
                            }
                        }
                    }
                }
                if(bLeftRight){
                    requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                if(topLayout != null && bottomLayout != null){
                    FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) topLayout.getLayoutParams();
                    if(flParams.rightMargin > 0 ){//第一层向左滑出了
                        int delta = 0;
                        if(flParams.rightMargin <= bottomLayout.getWidth()/2){
                            flParams.leftMargin = 0;
                            flParams.rightMargin = 0;
                            topLayout.setLayoutParams(flParams);
                        }else if(bottomLayout.getWidth()/2 < flParams.rightMargin && flParams.rightMargin <= bottomLayout.getWidth()){
                            flParams.leftMargin = -bottomLayout.getWidth();
                            flParams.rightMargin = bottomLayout.getWidth();
                            topLayout.setLayoutParams(flParams);
                        }else{
                            flParams.leftMargin = -bottomLayout.getWidth();
                            flParams.rightMargin = bottomLayout.getWidth();
                            topLayout.setLayoutParams(flParams);
                        }
//                        scroller.startScroll(topLayout.getScrollX(), 0, delta, 0,
//                                Math.abs(delta));
//                        postInvalidate(); // 刷新itemView
                    }else if(flParams.rightMargin < 0 ){
                        flParams.leftMargin = 0;
                        flParams.rightMargin = 0;
                        topLayout.setLayoutParams(flParams);
                    }
                }
                mLastTopLayout = topLayout;
                mLastBottomLayout = bottomLayout;
                bLeftRight = false;
                bDownUp = false;

                break;
        }
        return super.dispatchTouchEvent(event);
    }

//    @Override
//    public void computeScroll() {
//        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
//        if (scroller.computeScrollOffset()) {
//            // 让ListView item根据当前的滚动偏移量进行滚动
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//
//            postInvalidate();
//
//        }
//    }
}
