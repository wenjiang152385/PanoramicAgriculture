package com.oraro.panoramicagriculture.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.view.chart.MyLineChart;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.UIManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LineChartActivity extends BaseChartActivity implements View.OnClickListener {

    private final static String TAG = "LineChartActivity";

    private LineChart mLineChart;

    private LinearLayout mChartContainer;

    private DisplayMetrics mDisplayMetrics;

    private TextView mDataBegin;

    private TextView mDataEnd;

    private final static float PADINGTOP = 0.0391f;
    private final static float PADINGLEFT = 0.0278f;
    private final static float CHARTHEIGHT = 0.5313f;

    private final static float PARENTPADDING = 0.0234f;
    private final static float PARENTBOTTOM = 0.0078f;

    private final static float SUBHEIGHT = 0.1484f;
    private final static float ITEMWIDTH = 0.4514f;
    private final static float ITEMHEIGHT = 0.1484f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();


    }

    private void initViews() {
        setContentView(R.layout.activity_line_chart);
        mDisplayMetrics = CommonUtils.getDisplayMetrics(this);
        mDataBegin = (TextView) findViewById(R.id.tv_data_begin);
        mDataEnd = (TextView) findViewById(R.id.tv_data_end);
        mDataBegin.setOnClickListener(this);
        mDataEnd.setOnClickListener(this);
        initLineChartLayout();
        initBottomLayout();
    }

    private OnChartGestureListener mOnChartGestureListener = new OnChartGestureListener() {
        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            Log.i(TAG, "START, x: " + me.getX() + ", y: " + me.getY());
        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

            // un-highlight values after the gesture is finished and no single-tap
            if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
                mLineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
        }

        @Override
        public void onChartLongPressed(MotionEvent me) {
            Log.i(TAG, "Chart longpressed.");
        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {
            Log.i(TAG, "Chart double-tapped.");
        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {
            Log.i(TAG, "Chart single-tapped.");
        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            Log.i(TAG, "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            Log.i(TAG, "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {
            Log.i(TAG, "dX: " + dX + ", dY: " + dY);
        }
    };


    private OnChartValueSelectedListener mOnChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
//            Log.e("wjq", e.toString());
            Log.i("LOWHIGH", "low: " + mLineChart.getLowestVisibleX() + ", high: " + mLineChart.getHighestVisibleX());
            Log.i("MIN MAX", "xmin: " + mLineChart.getXChartMin() + ", xmax: " + mLineChart.getXChartMax() + ", ymin: " + mLineChart.getYChartMin() + ", ymax: " + mLineChart.getYChartMax());
            selectChangeData((int) e.getX());
        }

        @Override
        public void onNothingSelected() {
            Log.i("Nothing selected", "Nothing selected.");
        }
    };

    private void selectChangeData(int data) {

        String[] tag = {"00", "01", "10", "11"};
        for (int i = 0; i < 4; i++) {
            int month = 0;
            int year = 0;
            int rData = 0;
            RelativeLayout itemLayout = (RelativeLayout) mChartContainer.findViewWithTag("item" + tag[i]);
            if (i == 0) {
                rData = data - 1;
            }
            if (i == 1) {
                rData = data;
            }
            if (i == 2) {
                rData = data + 1;

            }
            if (i == 3) {
                rData = data + 2;
            }
            Data myData = getFormatMonth(rData);
            month = myData.getMonth();
            year = myData.getYear();
//
//            TextView tMonth = (TextView) itemLayout.findViewById(R.id.month);
//            TextView tYear = (TextView) itemLayout.findViewById(R.id.year);
////            Log.e("wjq", "month = " + month);
//            tMonth.setText(month + "月");
//            tYear.setText(year + "");

        }
    }


    private Data getFormatMonth(int data) {
        Data myData = new Data();
        myData.setMonth(data);
        myData.setYear(2016);
        if (data == 0) {
            myData.setMonth(12);
            myData.setYear(myData.getYear() - 1);
            return myData;
        }
        if (data >= 13) {
            myData.setMonth(data - 12);
            myData.setYear(myData.getYear() + 1);
            return myData;
        }

        return myData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_data_begin:
                loadDatePicker(mDataBegin);
                break;

            case R.id.tv_data_end:
                loadDatePicker(mDataEnd);
                break;

            default:
                break;
        }
    }


    private class Data {
        private int month;
        private int year;

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }


    }


    private void initLineChartLayout() {
        mChartContainer = (LinearLayout) findViewById(R.id.ll_chart);
        mChartContainer.setPadding((int) (mDisplayMetrics.widthPixels * PADINGLEFT), 0, (int) (mDisplayMetrics.widthPixels * PADINGLEFT), 0);
        LinearLayout.LayoutParams mLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mDisplayMetrics.heightPixels * CHARTHEIGHT));
//        mLp.setMargins((int) (mDisplayMetrics.widthPixels * PADINGLEFT), /*(int) (mDisplayMetrics.heightPixels * PADINGTOP)*/0, (int) (mDisplayMetrics.widthPixels * PADINGLEFT), 0);
        mLineChart = new LineChart(this);
        mChartContainer.addView(mLineChart, mLp);

        MyLineChart myLineChart = new MyLineChart(mLineChart,
                mOnChartGestureListener,
                mOnChartValueSelectedListener, this, R.layout.custom_marker_view);
//        myLineChart.setLineData(12, 100);
    }

    private void initBottomLayout() {
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        parentLayout.setBackgroundColor(Color.RED);
        lp.setMargins(/*(int) (mDisplayMetrics.widthPixels * PADINGLEFT)*/0,
                (int) (mDisplayMetrics.heightPixels * PARENTPADDING),
                /*(int) (mDisplayMetrics.widthPixels * PADINGLEFT)*/0,
                /*(int) (mDisplayMetrics.heightPixels * PARENTBOTTOM)*/0);
        mChartContainer.addView(parentLayout, lp);

        int[] colors = {Color.GREEN, Color.BLUE};

        for (int i = 0; i < 2; i++) {
            RelativeLayout subLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams subLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    /*(int) (mDisplayMetrics.heightPixels * SUBHEIGHT)*/ViewGroup.LayoutParams.WRAP_CONTENT);
//            subLayout.setBackgroundColor(colors[i]);
            if (i == 1) {
                subLp.setMargins(0, (int) (mDisplayMetrics.heightPixels * PARENTPADDING), 0, 0);
            }
            parentLayout.addView(subLayout, subLp);

            for (int j = 0; j < 2; j++) {
                RelativeLayout item = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_item, subLayout, false);
                item.setTag("item" + i + j);
//                Log.e("wjq", "tag = " + ("item" + i + j));
                RelativeLayout.LayoutParams childLp = new RelativeLayout.LayoutParams(
                        (int) (mDisplayMetrics.widthPixels * ITEMWIDTH),
                        (int) (mDisplayMetrics.heightPixels * ITEMHEIGHT));
                childLp.addRule(j == 0 ? RelativeLayout.ALIGN_PARENT_LEFT : RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                subLayout.addView(item, childLp);
            }

        }

    }

    private void loadDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(year + " " + monthOfYear + "月 " + dayOfMonth + "日");
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


}
