package com.oraro.panoramicagriculture.ui.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.oraro.panoramicagriculture.utils.LogUtils;

import java.text.NumberFormat;

/**
 * Created by Administrator on 2017/4/6.
 */
public class GradientProgressBar extends View {
    /*圆弧线宽*/
    private float circleBorderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    /*内边距*/
    private float circlePadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    /*字体大小*/
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
    /*绘制圆周的画笔*/
    private Paint backCirclePaint;
    /*绘制圆周白色分割线的画笔*/
    private Paint linePaint;
    /*绘制文字的画笔*/
    private Paint textPaint;
    /*百分比*/
    private double percent = 0;
    /*渐变圆周颜色数组*/
    private int[] gradientColorArray = new int[]{Color.parseColor("#f4ea29"), Color.parseColor("#afcd50"),
            Color.parseColor("#7cba59"), Color.parseColor("#2aa515")};
    private Paint gradientCirclePaint;
    private LinearGradient linearGradient;

    public GradientProgressBar(Context context) {
        super(context);
        init();
    }

    public GradientProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backCirclePaint = new Paint();
        backCirclePaint.setStyle(Paint.Style.STROKE);
        backCirclePaint.setAntiAlias(true);
        backCirclePaint.setColor(Color.LTGRAY);
        backCirclePaint.setStrokeWidth(circleBorderWidth);
//        backCirclePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));

        gradientCirclePaint = new Paint();
        gradientCirclePaint.setStyle(Paint.Style.STROKE);
        gradientCirclePaint.setAntiAlias(true);
        gradientCirclePaint.setColor(Color.LTGRAY);
        gradientCirclePaint.setStrokeWidth(circleBorderWidth);

        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(measureWidth, measureHeight), Math.min(measureWidth, measureHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.i("onDrawonDrawonDrawonDraw");
        //1.绘制灰色背景圆环
        canvas.drawArc(
                new RectF(circlePadding * 2, circlePadding * 2,
                        getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), -90, 360, false, backCirclePaint);
        //2.绘制颜色渐变圆环
        linearGradient = new LinearGradient(getMeasuredWidth() / 2, circlePadding,
                getMeasuredWidth() / 2, getMeasuredHeight() - circlePadding,
                gradientColorArray, null, Shader.TileMode.CLAMP);
        gradientCirclePaint.setShader(linearGradient);
        gradientCirclePaint.setShadowLayer(10, 10, 10, Color.RED);
        if (percent > 0.5 && percent < 1) {
            canvas.drawArc(
                    new RectF(circlePadding * 2, circlePadding * 2,
                            getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), -90, (float) 180, false, gradientCirclePaint);
            linearGradient = new LinearGradient(getMeasuredWidth() / 2, circlePadding,
                    getMeasuredWidth() / 2, getMeasuredHeight() - circlePadding,
                    gradientColorArray[gradientColorArray.length - 1], gradientColorArray[gradientColorArray.length - 1], Shader.TileMode.CLAMP);
            gradientCirclePaint.setShader(linearGradient);
            canvas.drawArc(
                    new RectF(circlePadding * 2, circlePadding * 2,
                            getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), 90, (float) (percent - 0.5) * 360, false, gradientCirclePaint);
        } else {
            canvas.drawArc(
                    new RectF(circlePadding * 2, circlePadding * 2,
                            getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), -90, (float) (percent) * 360, false, gradientCirclePaint);
        }
        //半径
        float radius = (getMeasuredWidth() - circlePadding * 3) / 2;
        //X轴中点坐标
        int centerX = getMeasuredWidth() / 2;
//        //3.绘制100份线段，切分空心圆弧
//        for (float i = 0; i < 360; i += 3.6) {
//            double rad = i * Math.PI / 180;
//            float startX = (float) (centerX + (radius - circleBorderWidth) * Math.sin(rad));
//            float startY = (float) (centerX + (radius - circleBorderWidth) * Math.cos(rad));
//
//            float stopX = (float) (centerX + radius * Math.sin(rad) + 1);
//            float stopY = (float) (centerX + radius * Math.cos(rad) + 1);
//
//            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
//        }

        //4.绘制文字
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        float textWidth = textPaint.measureText(numberFormat.format(percent));
        int textHeight = (int) (Math.ceil(textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent) + 2);
        canvas.drawText(numberFormat.format(percent), centerX - textWidth / 2, centerX + textHeight / 4, textPaint);
    }

    /**
     * 设置百分比
     *
     * @param percent
     */
    public void setPercent(double percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent >= 1) {
            percent = 1;
            gradientColorArray = new int[]{Color.parseColor("#FFFF5500"), Color.parseColor("#FFFF8F00")};
        } else if (percent > 0.9) {
            gradientColorArray = new int[]{Color.parseColor("#f6ef37"), Color.parseColor("#efb336"),
                    Color.parseColor("#e98f36"), Color.parseColor("#e16531"), Color.RED,};
        } else {
            gradientColorArray = new int[]{Color.parseColor("#f4ea29"), Color.parseColor("#afcd50"),
                    Color.parseColor("#7cba59"), Color.parseColor("#2aa515")};
        }
        this.percent = percent;
        invalidate();
    }
}
