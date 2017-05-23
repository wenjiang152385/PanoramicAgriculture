package com.oraro.panoramicagriculture.ui.view.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.PurchaseNeedsChart;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class MyPieChart {

    /**
     * 开启调试模式
     */
    private static final boolean DEBUG_MODE = false;

    private PieChart mPieChart;

    private Typeface mTfLight;

    public MyPieChart(PieChart pieChart, OnChartValueSelectedListener mOnChartValueSelectedListener,
                      Context context, Typeface tfLight, Typeface tfRegular, int drawable) {
        mPieChart = pieChart;
        mTfLight = tfLight;


        mPieChart.setBackground(context.getResources().getDrawable(R.drawable.corner_shape));
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setCenterTextTypeface(tfLight);
        mPieChart.setCenterText(generateCenterSpannableText());
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setHoleColor(Color.parseColor("#00000000"));

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(false);

        mPieChart.setRotationAngle(0f);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        // mPieChart.setUnit("€");
        // mPieChart.setDrawUnitsInChart(true);

        // add a selection listener
        mPieChart.setDrawEntryLabels(false);
        mPieChart.setOnChartValueSelectedListener(mOnChartValueSelectedListener);

//        setPieData(3, 100);

        Legend l = mPieChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(12f);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(10f);
        l.setYOffset(0f);

        // entry label styling
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTypeface(tfRegular);
        mPieChart.setEntryLabelTextSize(12f);


    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("农产品比重\n");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    public void setPieData(List<PieEntry> entries) {
        mPieChart.clearValues();
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        String[] crops = {"土豆", "马铃薯", " potato"};

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(10f);

//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(Constants.colors);
        // dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mPieChart.setData(data);
        mPieChart.setTouchEnabled(true);

        // undo all highlights
//        mPieChart.highlightValues(null);
        mPieChart.animateY(2400, Easing.EasingOption.EaseInOutQuad, new Chart.AnimationEndListener() {
            @Override
            public void onFinish() {
                mPieChart.setHighlight(0);
            }
        });
    }

}
