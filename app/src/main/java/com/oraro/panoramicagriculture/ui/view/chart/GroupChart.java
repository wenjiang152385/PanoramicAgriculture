package com.oraro.panoramicagriculture.ui.view.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.oraro.panoramicagriculture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */
public class GroupChart {

    private BarChart mChart;
    private Typeface mTfLight;

    public GroupChart(BarChart barChart, OnChartValueSelectedListener mOnChartValueSelectedListener,
                      Context context, int layoutId, Typeface typeface) {

        mChart = barChart;
        mTfLight = typeface;
        barChart.setBackgroundColor(/*Color.parseColor("#f0f0f0")*/Color.WHITE);
        barChart.setOnChartValueSelectedListener(mOnChartValueSelectedListener);
        barChart.getDescription().setEnabled(false);

//        mChart.setDrawBorders(true);


        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);

        barChart.setDrawGridBackground(false);

        barChart.setScaleXEnabled(false);

        barChart.setScaleYEnabled(false);

//        barChart.animateY(1400);

        barChart.setExtraBottomOffset(10);

        MyMarkerView mv = new MyMarkerView(context,
                layoutId);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv); // Set the marker to the chart

        Legend l = barChart.getLegend();
        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(true);
//        l.setTypeface(typeface);
//        l.setYOffset(0f);
//        l.setXOffset(10f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);


        mChart.getAxisLeft().setDrawGridLines(false);
        IAxisValueFormatter xAxisFormatter = new CropsAxisValueFormatter();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(typeface);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setGranularity(1f);
//        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return String.valueOf((int) value);
//            }
//        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(typeface);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setAxisLineColor(Color.parseColor("#8f8f8f"));
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        barChart.getAxisRight().setEnabled(false);

    }


    public void setData(int x, int y) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < x + 1; i++) {
            float mult = (y + 1);
            float val = (float) (Math.random() * mult) + mult / 3;
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "销量");
            int[] colors = new int[] {Color.parseColor("#26ceaf"),Color.parseColor("#fbd21b")};
            set1.setGradientColor(colors);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.21f);
            mChart.setData(data);
            mChart.setFitBars(true);
        }

        mChart.invalidate();
    }


}
