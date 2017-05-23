package com.oraro.panoramicagriculture.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MenuItem;

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
import com.oraro.panoramicagriculture.presenter.PointInfoPresenter;
import com.oraro.panoramicagriculture.ui.view.chart.MyRadarChart;
import com.oraro.panoramicagriculture.ui.view.chart.RadarMarkerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class PointInfoActivity extends BaseActivity<PointInfoPresenter>{
    private RadarChart mRadarChart;
    protected Typeface mTfLight;
    private  MyRadarChart myRadarChart;

    @Override
    public PointInfoPresenter createPresenter() {
        return new PointInfoPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        setContentView(R.layout.activity_point_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRadarChart = (RadarChart) findViewById(R.id.radar_chart);

        myRadarChart = new MyRadarChart(mRadarChart,this,mTfLight);
    }

    public MyRadarChart getMyRadarChart() {
        return myRadarChart;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
