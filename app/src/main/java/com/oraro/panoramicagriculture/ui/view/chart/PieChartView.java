package com.oraro.panoramicagriculture.ui.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/2/24 0024.
 */
public class PieChartView extends View {

    private final static float mStartAngle = 270;

    private float mStrokeWidth = 20f;

    private static int mCenterX = 400;

    private static int mCenterY = 400;

    private  float mHalfCircleRadius= mStrokeWidth / 2;

    private float mMiniRadius = 120;

    private int mCount = 6;

    private int mPadding = 30;

    private float mSweepAngle = 0;

    private final static  String[] startColor= {"#c40ece","#fe5002","#ff7200","#ffa900","#24d0b5","#257bc7"};
    private final static  String[] endColor= {"#b954ed","#f8b85c","#fdd010","#f5e976","#89e6ce","#23c8b7"};
    private final static  float[]  testData = {0.8f,0.6f,0.5f,0.125f,0.4f,0.8f};

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitMap(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
//
    }

    public void setCenterXY(int centerX,int centerY) {
        mCenterX = centerX;
        mCenterY = centerY;
    }

    private void drawBitMap(Canvas canvas) {
        Paint defaultPaint = new Paint();
        defaultPaint.setStrokeWidth(mStrokeWidth);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setAntiAlias(true);
        defaultPaint.setColor(Color.GRAY);


        Paint paint = new Paint();
        paint.setStrokeWidth(mStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        Paint circlePaint = new Paint();

        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        //动态分配最小半径、间隔、宽度
        int mWidth = getWidth();
        mPadding = mWidth / (mCount * 3 * 2);
        mStrokeWidth = mWidth / (mCount * 3 * 2);
        mMiniRadius = mWidth / 2 - (mPadding + mStrokeWidth) * (mCount + 1);
        mHalfCircleRadius= mStrokeWidth / 2;

        float radius = 0;
        for (int i = 0; i < mCount; i++) {
            mSweepAngle = getSweepAngle(testData[i]);
            radius = (mMiniRadius + mStrokeWidth) + (mPadding + mStrokeWidth) * i;

//            drawCircle(canvas,radius,defaultPaint);
            CircleCenterPoint point = getPoint(radius);
            paint.setShader(new LinearGradient(mCenterX,mCenterY - radius,(float) point.getCenterX(),(float)point.getCenterY(), Color.parseColor(startColor[i]), Color.parseColor(endColor[i]), Shader.TileMode.MIRROR));
            drawArc(canvas,radius,paint);


            circlePaint.setColor(Color.parseColor(startColor[i]));
            drawStartCircle(canvas,radius,circlePaint);

            circlePaint.setColor(Color.parseColor(endColor[i]));

            drawEndCircle(canvas,circlePaint,point);

        }

        drawCenterText(canvas);

    }

    private float getStartAngle(float radius) {
        float angle = (float) (Math.asin(mHalfCircleRadius / radius) * 180 / Math.PI);
        return angle;
    }

    private CircleCenterPoint getCircleCenterPoint1(float pi,float radius) {
        CircleCenterPoint circleCenterPoint = new CircleCenterPoint();
        circleCenterPoint.setCenterX(radius * Math.cos(pi));
        circleCenterPoint.setCenterY(radius * Math.sin(pi));
        return circleCenterPoint;
    }

    private void drawCircle(Canvas canvas, float radius, Paint paint) {
        canvas.drawCircle(mCenterX, mCenterY, radius, paint);
    }

    private void drawCenterText(Canvas canvas) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#8f8f8f"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);
        String message = "南京市\n农作物分布图";
        StaticLayout staticLayout = new StaticLayout(message, textPaint, (int)(mMiniRadius * 2), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.translate(mCenterX - mMiniRadius,mCenterY - 15);
        staticLayout.draw(canvas);


    }

    private void drawArc(Canvas canvas, float radius, Paint paint) {
        canvas.drawArc(getRect(radius), mStartAngle + getStartAngle(radius), mSweepAngle -getStartAngle(radius) * 2, false, paint);
    }


    private void drawStartCircle(Canvas canvas, float radius, Paint paint) {
        float pi = (float) (Math.PI / 2 -  Math.asin(mHalfCircleRadius / radius));
        CircleCenterPoint centerP = getCircleCenterPoint1(pi,radius);
        canvas.drawCircle((float) centerP.getCenterX() + mCenterX,mCenterY - (float)centerP.getCenterY(),mHalfCircleRadius,paint);
    }


    private void drawEndCircle(Canvas canvas, Paint paint,  CircleCenterPoint point) {
        canvas.drawCircle((float)point.getCenterX(),(float)point.getCenterY(),mHalfCircleRadius,paint);
    }

    private CircleCenterPoint getPoint(float radius) {
        CircleCenterPoint point = new CircleCenterPoint();
        if (mSweepAngle > 0 && mSweepAngle <= 90) {
            float pi = (float) ((90 - mSweepAngle + getStartAngle(radius)) / 180 * Math.PI);
            CircleCenterPoint centerP = getCircleCenterPoint1(pi,radius);
            point.setCenterX(centerP.getCenterX() + mCenterX);
            point.setCenterY(mCenterY - centerP.getCenterY());
            return point;
        }

        if (mSweepAngle > 90 && mSweepAngle <= 180) {
            float pi = (float) ((mSweepAngle - 90 - getStartAngle(radius)) / 180 * Math.PI);
            CircleCenterPoint centerP = getCircleCenterPoint1(pi,radius);
            point.setCenterX(centerP.getCenterX() + mCenterX);
            point.setCenterY(mCenterY + centerP.getCenterY());
            return point;
        }

        if (mSweepAngle > 180 && mSweepAngle <= 270) {
            float pi = (float) ((270 - mSweepAngle + getStartAngle(radius)) / 180 * Math.PI);
            CircleCenterPoint centerP = getCircleCenterPoint1(pi,radius);
            point.setCenterX(mCenterX - centerP.getCenterX());
            point.setCenterY(mCenterY + centerP.getCenterY());
            return point;
        }

        if (mSweepAngle > 270 && mSweepAngle <= 360) {
            float pi = (float) ((mSweepAngle - 270 - getStartAngle(radius)) / 180 * Math.PI);
            CircleCenterPoint centerP = getCircleCenterPoint1(pi,radius);
            point.setCenterX(mCenterX - centerP.getCenterX());
            point.setCenterY(mCenterY - centerP.getCenterY());
            return point;
        }

        return null;
    }


    private RectF getRect(float radius) {

        RectF rect = new RectF(mCenterX - radius, mCenterY - radius, mCenterX
                + radius, mCenterY + radius);

        return rect;
    }

    public float getSweepAngle(float percent) {
        return  360 * percent;
    }

    private class CircleCenterPoint {
        private double centerX;
        private double centerY;

        public double getCenterX() {
            return centerX;
        }

        public void setCenterX(double centerX) {
            this.centerX = centerX;
        }

        public double getCenterY() {
            return centerY;
        }

        public void setCenterY(double centerY) {
            this.centerY = centerY;
        }
    }

}


