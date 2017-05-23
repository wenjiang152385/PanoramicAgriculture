package com.oraro.panoramicagriculture.ui.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.ItemView;
import com.oraro.panoramicagriculture.data.entity.PurchaseNeeds;
import com.oraro.panoramicagriculture.presenter.DetailChartPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class MyLineChart {

    private LineChart mLineChart;
    private Context mContext;

    public MyLineChart(LineChart lineChart, OnChartGestureListener onChartGestureListener, OnChartValueSelectedListener onChartValueSelectedListener,
                       Context context, int layoutId) {
        mLineChart = lineChart;
        mContext = context;
        setChartConfig(lineChart, onChartGestureListener, onChartValueSelectedListener, context, layoutId);
    }

    public void setChartConfig(LineChart lineChart, OnChartGestureListener onChartGestureListener, OnChartValueSelectedListener onChartValueSelectedListener,
                               Context context, int layoutId) {
        //图表背景色
        lineChart.setBackgroundColor(/*Color.parseColor("#21232a")*/Color.WHITE);
        lineChart.setOnChartGestureListener(onChartGestureListener);
        lineChart.setOnChartValueSelectedListener(onChartValueSelectedListener);
        lineChart.setDrawGridBackground(false);
        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        // mLineChart.setScaleXEnabled(true);
        // mLineChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        // set an alternative background color
        // mLineChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(context, layoutId);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv); // Set the marker to the chart

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.parseColor("#6d6e6b"));
        //x轴颜色
        xAxis.setAxisLineColor(/*Color.BLACK*/Color.parseColor("#8f8f8f"));
        //x轴平行网格颜色
        xAxis.setGridColor(/*Color.BLACK*/Color.parseColor("#8f8f8f"));



        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setTextColor(Color.parseColor("#6d6e6b"));
        //Y轴颜色
        leftAxis.setAxisLineColor(/*Color.BLACK*/Color.parseColor("#8f8f8f"));
        //y轴网格颜色
        leftAxis.setGridColor(/*Color.BLACK*/Color.parseColor("#8f8f8f"));
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(4f, 4f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.getAxisRight().setEnabled(false);


        //mLineChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);


        //图例文字颜色
        l.setTextColor(Color.parseColor("#8f8f8f"));

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

    }

    public void setLineData(final ArrayList<Entry> values1, final ArrayList<Entry> values2,
                            String type1, String type2,final List<ItemView> itemViews) {
        mLineChart.clearValues();
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int)value == -1 || itemViews.size() <= (int)value) {
                    return "";
                }else {
                    ItemView itemView = itemViews.get((int) value);
                    String date = itemView.getDate().split("-")[1] + "-" + itemView.getDate().split("-")[2];
                    return date;
                }
            }
        });

//        YAxis leftAxis = mLineChart.getAxisLeft();
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return (int)value + "斤";
//            }
//        });

        LineDataSet set1;
        LineDataSet set2;
        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mLineChart.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1,type1);
            set1.setMode(LineDataSet.Mode.LINEAR);
            if (values1.size() == 1) {
                set1.setType(-1);
            }else {
                set1.setType(1);
            }

            // set the line to be drawn like this "- - - - - -"
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            //图例颜色
            set1.setColor(Color.parseColor("#1dcdb3"));
            set1.setCircleColor(Color.parseColor("#1dcdb3"));
            set1.setDrawCircles(true);
            set1.setLineWidth(1.5f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            //图例宽度
            set1.setFormLineWidth(1.5f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);


            set2 = new LineDataSet(values2,type2);
            set2.setMode(LineDataSet.Mode.LINEAR);

            if (values2.size() == 1) {
                set2.setType(-1);
            }else {
                set2.setType(0);
            }
            // set the line to be drawn like this "- - - - - -"
//            set2.enableDashedLine(10f, 5f, 0f);
//            set2.enableDashedHighlightLine(10f, 5f, 0f);
            //图例颜色
            set2.setColor(Color.parseColor("#fadd00"));
            set2.setCircleColor(Color.parseColor("#fadd00"));
            set2.setDrawCircles(true);
            set2.setLineWidth(1.5f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setValueTextSize(9f);
            set2.setDrawFilled(true);
            set2.setFormLineWidth(1.5f);
//            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable1 = ContextCompat.getDrawable(mContext, R.drawable.fade_trans);
                set1.setFillDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(mContext, R.drawable.fade_trans);
                set2.setFillDrawable(drawable2);
            } else {
                set1.setFillColor(Color.BLACK);
                set2.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);
            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            data.setDrawValues(false);


            mLineChart.animateY(2000, new Chart.AnimationEndListener() {
                @Override
                public void onFinish() {
                    mLineChart.setHighLight(0);
                }
            });

            // set data
            mLineChart.setData(data);
            mLineChart.invalidate();

        }
    }


    public int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
