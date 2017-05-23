package com.oraro.panoramicagriculture.ui.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.AbilityEntry;
import com.oraro.panoramicagriculture.data.entity.AbilityModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class MyRadarChart {
    private RadarChart mRadarChart;
    private Context mContext;
    private Typeface mTfLight;

    public MyRadarChart(RadarChart radarChart, Context context, Typeface tfLight) {
        mRadarChart = radarChart;
        mContext = context;
        mTfLight = tfLight;
        mRadarChart.setBackgroundColor(/*Color.rgb(60, 65, 82)*/Color.WHITE);

        mRadarChart.getDescription().setEnabled(false);

        mRadarChart.setWebLineWidth(0.8f);
        mRadarChart.setWebColor(Color.LTGRAY);
        mRadarChart.setWebLineWidthInner(0.8f);
        mRadarChart.setWebColorInner(Color.LTGRAY);
        mRadarChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(context, R.layout.radar_markerview);
        mv.setChartView(mRadarChart); // For bounds control
        mRadarChart.setMarker(null); // Set the marker to the chart


        mRadarChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setTextColor(Color.LTGRAY);

        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mRadarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setTypeface(mTfLight);
        l.setXEntrySpace(4f);
        l.setYEntrySpace(2f);
        l.setTextColor(Color.LTGRAY);

    }

    public void setData(final AbilityModel abilityModel) {

        float mult = 80;
        float min = 20;
        int cnt = 5;

        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((AbilityEntry)abilityModel.getList().get((int) value)).getAbilityName();
            }
        });

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < abilityModel.getList().size(); i++) {
//            float val1 = (float) (Math.random() * mult) + min;
            float val1 = (float) ((AbilityEntry)abilityModel.getList().get(i)).getNum();
            entries1.add(new RadarEntry(val1));

//            float val2 = (float) (Math.random() * mult) + min;
//            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, abilityModel.getLabel());
        set1.setColor(/*Color.rgb(103, 110, 129)*/Color.parseColor("#257cc4"));
        set1.setFillColor(/*Color.rgb(103, 110, 129)*/Color.parseColor("#1dcdb3"));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

//        RadarDataSet set2 = new RadarDataSet(entries2, "农场水平");
//        set2.setColor(/*Color.rgb(121, 162, 175)*/Color.parseColor("#ff8300"));
//        set2.setFillColor(/*Color.rgb(121, 162, 175)*/Color.parseColor("#fadd00"));
//        set2.setDrawFilled(true);
//        set2.setFillAlpha(180);
//        set2.setLineWidth(2f);
//        set2.setDrawHighlightCircleEnabled(true);
//        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
//        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mRadarChart.setData(data);
        mRadarChart.invalidate();
    }
}
