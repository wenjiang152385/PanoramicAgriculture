package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.oraro.panoramicagriculture.data.entity.TotalHarvestEntity;
import com.oraro.panoramicagriculture.data.entity.TotalNeedsEntity;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017.04.27.
 */

public class SupplyAndDemandView extends LinearLayout {

    public static final int GET_DATA_FAIL_STATE = 1;
    public static final int NO_DATA_STATE = 2;
    public static final int GET_DATA_SUCCESS_STATE = 3;

    private static final int PADDING_TOP = 5;
    private static final int PADDING_BOTTOM = 5;
    private static int PADDING_LEFT = 8;
    private static int PADDING_RIGHT = 8;

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private int mState;

    private List<TotalNeedsEntity> totalNeedsEntities = new ArrayList<TotalNeedsEntity>();
    private List<TotalHarvestEntity> totalHarvestEntities = new ArrayList<TotalHarvestEntity>();

    private List<Point> mTotalNeedsPoints = new ArrayList<>();
    private List<Point> mTotalHarvestPoints = new ArrayList<>();

    private List<Point> mTotalNeedsBesselPoints = new ArrayList<Point>();
    private List<Point> mTotalHarvestBesselPoints = new ArrayList<Point>();

    private Paint mTotalNeedsPointPaint = new Paint();
    private Paint mTotalNeedsBesselLinePaint = new Paint();

    private Paint mTotalHarvestPointPaint = new Paint();
    private Paint mTotalHarvestBesselLinePaint = new Paint();

    private Path mTotalNeedsPath = new Path();
    private Path mTotalHarvestPath = new Path();

    private Path mTotalNeedsBesselPath = new Path();
    private Path mTotalHarvestBesselPath = new Path();

    private Paint mTextPaint = new Paint();

    //平滑因子
    private float smoothness = 0.4f;

    public SupplyAndDemandView(Context context) {
        super(context);
        init(context);
    }

    public SupplyAndDemandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context ctx){
        mContext = ctx;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTotalNeedsPointPaint.setColor(Color.parseColor("#f89200"));
        mTotalNeedsPointPaint.setAntiAlias(true);
        mTotalNeedsPointPaint.setStyle(Paint.Style.FILL);

        mTotalNeedsBesselLinePaint.setColor(Color.parseColor("#33f89200"));
        mTotalNeedsBesselLinePaint.setAntiAlias(true);
        mTotalNeedsBesselLinePaint.setStyle(Paint.Style.STROKE);

        mTotalHarvestPointPaint.setColor(Color.parseColor("#20ceb4"));
        mTotalHarvestPointPaint.setAntiAlias(true);
        mTotalHarvestPointPaint.setStyle(Paint.Style.FILL);

        mTotalHarvestBesselLinePaint.setColor(Color.parseColor("#3320ceb4"));
        mTotalHarvestBesselLinePaint.setAntiAlias(true);
        mTotalHarvestBesselLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint.setTextSize(CommonUtils.dip2px(mContext,16));
        mTextPaint.setColor(Color.parseColor("#20ceb4"));
        mTextPaint.setStrokeWidth(5);
    }

    public void setData(int state, int maxNum, List<TotalNeedsEntity> totalNeedsEntityList, List<TotalHarvestEntity> totalHarvestEntityList){
        mState = state;
        if(totalNeedsEntityList != null && totalHarvestEntityList != null ){
            totalNeedsEntities.clear();
            totalHarvestEntities.clear();

            totalNeedsEntities.addAll(totalNeedsEntityList);
            totalHarvestEntities.addAll(totalHarvestEntityList);

            mTotalNeedsPoints.clear();
            mTotalHarvestPoints.clear();

            mTotalNeedsBesselPoints.clear();

            int size = totalNeedsEntities.size();
            PADDING_LEFT = mWidth / (size + 1);
            PADDING_RIGHT = PADDING_LEFT;
            int mDataWidth = mWidth - PADDING_LEFT - PADDING_RIGHT;
            int mDataHeight = mHeight - PADDING_TOP - PADDING_BOTTOM;
            for(int i = 0; i < size; i++){
                if(i == 0){
                    Point mFirstNeedsPoint = new Point();
                    mFirstNeedsPoint.x = 0;
                    mFirstNeedsPoint.y = mHeight;
                    mTotalNeedsPoints.add(mFirstNeedsPoint);

                    Point mFirstHarvestPoint = new Point();
                    mFirstHarvestPoint.x = 0;
                    mFirstHarvestPoint.y = mHeight;
                    mTotalHarvestPoints.add(mFirstHarvestPoint);
                }

                TotalNeedsEntity totalNeedsEntity = totalNeedsEntities.get(i);
                Point mNeedsPoint = new Point();
                mNeedsPoint.x = PADDING_LEFT + i * mDataWidth / size;
                mNeedsPoint.y = (mHeight - PADDING_BOTTOM) - totalNeedsEntity.getTotalNeedsNum() * mDataHeight / maxNum;
                mTotalNeedsPoints.add(mNeedsPoint);

                TotalHarvestEntity totalHarvestEntity = totalHarvestEntities.get(i);
                Point mHarvestPoint = new Point();
                mHarvestPoint.x = PADDING_LEFT + i * mDataWidth / size;
                mHarvestPoint.y = mHeight - totalHarvestEntity.getTotalHarvestNum() * mHeight / maxNum;
                mTotalHarvestPoints.add(mHarvestPoint);

                if(i == size - 1){
                    Point mLastNeedsPoint = new Point();
                    mLastNeedsPoint.x = mWidth;
                    mLastNeedsPoint.y = mHeight;
                    mTotalNeedsPoints.add(mLastNeedsPoint);

                    Point mLastHarvestPoint = new Point();
                    mLastHarvestPoint.x = mWidth;
                    mLastHarvestPoint.y = mHeight;
                    mTotalHarvestPoints.add(mLastHarvestPoint);
                }
            }

            initPoints(mTotalNeedsPoints,mTotalNeedsBesselPoints);
            initPoints(mTotalHarvestPoints,mTotalHarvestBesselPoints);
        }
        invalidate();
    }

    private void initPoints(List<Point> points,List<Point> BesselPoints) {

        for(int i = 0;i < points.size();i++){
            if(i == 0){
                Point p0 = points.get(0);
                Point p1 = points.get(1);
                BesselPoints.add(p0);
                Point p01 = new Point();
                p01.x = p0.x + (p1.x - p0.x) * smoothness;
                p01.y = p1.y;
                BesselPoints.add(p01);
            }else if(i == points.size() - 1){
                Point p0 = points.get(points.size() - 2);
                Point p1 = points.get(points.size() - 1);
                Point p01 = new Point();
                p01.x = p1.x - (p1.x - p0.x) * smoothness;
                p01.y = p1.y;
                BesselPoints.add(p01);
                BesselPoints.add(p1);
            }else{
                Point p0 = points.get(i-1);
                Point p1 = points.get(i);
                Point p2 = points.get(i+1);

                Point p01 = new Point();
                p01.x = p1.x - (p1.x - p0.x) * smoothness;
                p01.y = p1.y;
                BesselPoints.add(p01);
                BesselPoints.add(p1);
                Point p12 = new Point();
                p12.x = p1.x + (p2.x - p1.x) * smoothness;
                p12.y = p1.y;
                BesselPoints.add(p12);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mWidth = getWidth();
        mHeight = getHeight();

        switch (mState){
            case GET_DATA_FAIL_STATE:
                String s = "获取供需数据失败";
                drawText(canvas,s);
                break;
            case NO_DATA_STATE:
                s = "暂无供需数据";
                drawText(canvas,s);
                break;
            case GET_DATA_SUCCESS_STATE:
                mTotalHarvestBesselPath.reset();
                for(int i = 0; i < mTotalHarvestBesselPoints.size(); i = i + 3){
                    if(i == 0){
                        mTotalHarvestBesselPath.moveTo(mTotalHarvestBesselPoints.get(i).x, mTotalHarvestBesselPoints.get(i).y);
                    }else{
                        mTotalHarvestBesselPath.cubicTo(
                                mTotalHarvestBesselPoints.get(i - 2).x, mTotalHarvestBesselPoints.get(i-2).y,
                                mTotalHarvestBesselPoints.get(i - 1).x, mTotalHarvestBesselPoints.get(i-1).y,
                                mTotalHarvestBesselPoints.get(i).x, mTotalHarvestBesselPoints.get(i).y);

                    }
                    if(i + 3 >= mTotalHarvestBesselPoints.size()){
                        mTotalHarvestBesselLinePaint.setStyle(Paint.Style.FILL);
                    }else{
                        mTotalHarvestBesselLinePaint.setStyle(Paint.Style.STROKE);
                    }
                    canvas.drawPath(mTotalHarvestBesselPath, mTotalHarvestBesselLinePaint);
                }

                mTotalNeedsBesselPath.reset();
                for(int i = 0; i < mTotalNeedsBesselPoints.size(); i = i + 3){
                    if(i == 0){
                        mTotalNeedsBesselPath.moveTo(0, mHeight);
                        mTotalNeedsBesselPath.lineTo(mTotalNeedsBesselPoints.get(i).x, mTotalNeedsBesselPoints.get(i).y);
                    }else{
                        mTotalNeedsBesselPath.cubicTo(
                                mTotalNeedsBesselPoints.get(i - 2).x, mTotalNeedsBesselPoints.get(i-2).y,
                                mTotalNeedsBesselPoints.get(i - 1).x, mTotalNeedsBesselPoints.get(i-1).y,
                                mTotalNeedsBesselPoints.get(i).x, mTotalNeedsBesselPoints.get(i).y);
                    }
                    if(i + 3 >= mTotalNeedsBesselPoints.size()){
                        mTotalNeedsBesselLinePaint.setStyle(Paint.Style.FILL);
                    }else{
                        mTotalNeedsBesselLinePaint.setStyle(Paint.Style.STROKE);
                    }
                    canvas.drawPath(mTotalNeedsBesselPath, mTotalNeedsBesselLinePaint);
                }
                break;
        }
    }

    private void drawText(Canvas canvas,String s) {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(s,0,s.length(),rect);
        canvas.drawText(s,mWidth/2 - rect.width()/2,mHeight/2 + rect.height()/2,mTextPaint);
    }

    class Point{
        public float x = 0;
        public float y = 0;
    }
}
