package com.oraro.panoramicagriculture.ui.activity;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.view.chart.GroupChart;

//wangxing
public class BarChartActivity extends BaseChartActivity {

    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        LinearLayout chartLayout = (LinearLayout) findViewById(R.id.ll_chart);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mChart = new BarChart(this);
        GroupChart groupChart = new GroupChart(mChart,mOnChartValueSelectedListener,this,R.layout.custom_marker_view,mTfLight);
        groupChart.setData(6,20);
        chartLayout.addView(mChart,layoutParams);




    }

    private OnChartValueSelectedListener mOnChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {

        }

        @Override
        public void onNothingSelected() {

        }
    };

}
