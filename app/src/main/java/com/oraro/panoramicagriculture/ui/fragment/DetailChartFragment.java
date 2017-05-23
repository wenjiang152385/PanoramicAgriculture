package com.oraro.panoramicagriculture.ui.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.ItemView;
import com.oraro.panoramicagriculture.presenter.ChartPresenter;
import com.oraro.panoramicagriculture.ui.activity.MPChartActivity;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.CardAdapter;
import com.oraro.panoramicagriculture.ui.view.chart.GroupChart;
import com.oraro.panoramicagriculture.ui.view.chart.MyLineChart;
import com.oraro.panoramicagriculture.ui.view.chart.MyPieChart;
import com.oraro.panoramicagriculture.ui.view.MyRecycleView;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.view.jameson.library.CardScaleHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DetailChartFragment extends DetailFragment<ChartPresenter> {

    private final static String TAG = "DetailChartFragment";

    private LineChart mLineChart;

    private BarChart mBarChart;

    public PieChart mPieChart;

    private MyPieChart mMyPieChart;

    private MyLineChart mMyLineChart;

    private LinearLayout mLayoutContainer;

    private LinearLayout mChartContainer;

    private DisplayMetrics mDisplayMetrics;

    public TextView mDataBegin;

    public TextView mDataEnd;

    private final static float PADINGTOP = 0.0391f;
    private final static float PADINGLEFT = 0.0478f;
    private final static float CHARTHEIGHT = 0.5313f;

    private final static float PARENTPADDING = 0.0234f;
    private final static float PARENTBOTTOM = 0.0078f;

    private final static float SUBHEIGHT = 0.1484f;
    private final static float ITEMWIDTH = 0.4514f;
    private final static float ITEMHEIGHT = 0.1484f;


    private final static int LINECHART = 0;
    private final static int BARCHART = 1;
    private final static int PIECHART = 2;

    public CardAdapter mChartItemAdapter;


    public int mType;
    public long mPointId;

    public String mLabel = "";


    private TextView mChartUnit;


    private long mVFCropsId = -1;


    private MyRecycleView myRecycleView;

    private LinearLayoutManager mLayoutManager;

    private boolean isFirstTime = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getActivity().getIntent().getIntExtra("type", -1);
        mPointId = getUi().getActivity().getIntent().getLongExtra("pointId", -1);
        ((MPChartActivity) getActivity()).registerBackListener(mBackListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myRecycleView.unRegisterItemChangedListener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_chart;
    }

    @Override
    public void initViews(View v) {
        mChartUnit = (TextView) v.findViewById(R.id.chart_unit);
        mChartUnit.setVisibility(View.GONE);
        mDisplayMetrics = CommonUtils.getDisplayMetrics(getActivity());
        mDataBegin = (TextView) v.findViewById(R.id.tv_data_begin);
        mDataEnd = (TextView) v.findViewById(R.id.tv_data_end);
        mDataEnd.setOnClickListener(mOnClickListener);
        initChartLayout(v);
        attachChartLayout(initChartLayout(PIECHART));
        initBottomLayout(v);

        setDateLayout(PIECHART);
    }

    public void setChartTitle(String name) {
        ((MPChartActivity) getActivity()).mTitle.setText(name);
    }

    private void setDateLayout(int type) {
        mDataBegin.setOnClickListener(type == PIECHART ? null : mOnClickListener);
        mDataBegin.setText(type == PIECHART ? "请输入时间" : getDateText(-1));
        mDataEnd.setText(getDateText(0));
    }


    @Override
    public ChartPresenter createPresenter() {
        return new ChartPresenter();
    }

    @Override
    public DetailFragment getUi() {
        return this;
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


    protected MPChartActivity.OnBackPressListener mBackListener = new MPChartActivity.OnBackPressListener() {
        @Override
        public int onBackPressed() {
            if (mLineChart != null) {

                mChartUnit.setVisibility(View.GONE);

                setDateLayout(PIECHART);

                mLineChart.setOnChartValueSelectedListener(null);
                detachToLayout(mLineChart);
                mLineChart = null;
                attachChartLayout(initChartLayout(PIECHART));

                getPresenter().requestPieChartData(mDataEnd.getText().toString(), mPointId);

                setChartTitle(getPresenter().getMpChartData().getTitle(""));

                return 1;
            } else {
                return 0;
            }
        }
    };


    private OnChartValueSelectedListener mOnChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            int position = -1;
            if (null != mPieChart) {
                position = getPresenter().getIndexOfAdapter(e, CommonUtils.CHART_TYPE.PIE);
            }

            if (null != mLineChart) {
                position = getPresenter().getIndexOfAdapter(e, CommonUtils.CHART_TYPE.LINE);
            }

            if (position != -1) {
                mLayoutManager.smoothScrollToPosition(myRecycleView,null,position);
            }

        }

        @Override
        public void onNothingSelected() {
            Log.i("Nothing selected", "Nothing selected.");
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_data_begin:
                    loadDatePicker(mDataBegin,false);
                    break;

                case R.id.tv_data_end:
                    loadDatePicker(mDataEnd,true);
                    break;

                default:
                    break;
            }
        }
    };

    private void initChartLayout(View v) {
        mLayoutContainer = (LinearLayout) v.findViewById(R.id.ll_chart);
        mLayoutContainer.setPadding((int) (mDisplayMetrics.widthPixels * PADINGLEFT), 40, (int) (mDisplayMetrics.widthPixels * PADINGLEFT), 0);
        LinearLayout.LayoutParams mLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mDisplayMetrics.heightPixels * CHARTHEIGHT));

        mChartContainer = new LinearLayout(getActivity());
        mLayoutContainer.addView(mChartContainer, mLp);
    }

    private void attachChartLayout(Chart chart) {
        LinearLayout.LayoutParams mLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mChartContainer.addView(chart, mLp);
    }

    private void detachToLayout(Chart chart) {
        mChartContainer.removeView(chart);
    }

    private Chart initChartLayout(int type) {
        if (type == LINECHART) {
            mLineChart = new LineChart(getActivity());
            mMyLineChart = new MyLineChart(mLineChart,
                    mOnChartGestureListener,
                    mOnChartValueSelectedListener, getActivity(), R.layout.custom_marker_view);
            return mLineChart;
        }

        if (type == BARCHART) {
            mBarChart = new BarChart(getActivity());
            GroupChart groupChart = new GroupChart(mBarChart, mOnChartValueSelectedListener,
                    getActivity(),
                    R.layout.custom_marker_view, mTfLight);
            groupChart.setData(6, 30);
            return mBarChart;
        }

        if (type == PIECHART) {
            mPieChart = new PieChart(getActivity());
            mMyPieChart = new MyPieChart(mPieChart, mOnChartValueSelectedListener, getActivity(), mTfLight, mTfRegular, R.mipmap.test_icon);
            return mPieChart;
        }
        return null;
    }

    private void initBottomLayout(View v) {
        myRecycleView = new MyRecycleView(getActivity());

        myRecycleView.setItemChangedListener(new MyRecycleView.ItemChangedListener() {
            @Override
            public void getItemPosition(int position, int direction) {
                if (null != mPieChart) {
                    mPieChart.setHighlight(position);
                }

                if (null != mLineChart) {
                    mLineChart.setHighLight(position);
                }

            }
        });
//        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        myRecycleView.setLayoutManager(mLayoutManager);
        mChartItemAdapter = new CardAdapter(getActivity(), BaseListAdapter.NEITHER);
        RecyclerView.ItemDecoration itemDecoration = mChartItemAdapter.new SpaceItemDecoration(0, 0, 0, 0);
        new LinearSnapHelper().attachToRecyclerView(myRecycleView);
        myRecycleView.addItemDecoration(itemDecoration);
//        mCardScaleHelper = new CardScaleHelper();
//        mCardScaleHelper.registerGalleryCallback(new GalleryCallback() {
//            @Override
//            public void setCurrentPositionCallback(int position) {
//                Log.e("json","position = " + position);
//            }
//        });
//
//        mCardScaleHelper.attachToRecyclerView(myRecycleView);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        parentLayout.setBackgroundColor(Color.RED);
        lp.setMargins(/*(int) (mDisplayMetrics.widthPixels * PADINGLEFT)*/0,
                (int) (mDisplayMetrics.heightPixels * PARENTPADDING),
                /*(int) (mDisplayMetrics.widthPixels * PADINGLEFT)*/0,
                /*(int) (mDisplayMetrics.heightPixels * PARENTBOTTOM)*/0);
        mChartItemAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long id, View view) {
                //Log.e("wjq" ,position+"");
                final ItemView itemView = (ItemView) mChartItemAdapter.getItem(position);
                mVFCropsId = itemView.getmId();
                if (mPieChart != null) {
                    mPieChart.inverseAnimateY(1400, Easing.EasingOption.EaseInOutQuad, new Chart.AnimationEndListener() {
                        @Override
                        public void onFinish() {
                            mChartUnit.setVisibility(View.VISIBLE);
                            setDateLayout(LINECHART);

                            mPieChart.clearValues();
                            mPieChart.setOnChartValueSelectedListener(null);
                            detachToLayout(mPieChart);
                            mPieChart = null;
                            attachChartLayout(initChartLayout(LINECHART));
                            getPresenter().requestLineChartData(mVFCropsId,
                                    mDataBegin.getText().toString(),
                                    mDataEnd.getText().toString(), mPointId);

                            setChartTitle(getPresenter().getMpChartData().getTitle(itemView.getName()));

                        }
                    });
                }
            }
        });
        myRecycleView.setAdapter(mChartItemAdapter);
        mLayoutContainer.addView(myRecycleView, lp);


    }

    private void loadDatePicker(final TextView textView, boolean isEnd) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(year + "-" + formatMonth(monthOfYear + 1) + "-" + dayOfMonth);

                if (mPieChart != null) {
                    mPieChart.clearValues();
                    getPresenter().requestPieChartData(mDataEnd.getText().toString(), mPointId);

                }

                if (mLineChart != null) {
                    mLineChart.clearValues();
                    getPresenter().requestLineChartData(mVFCropsId, mDataBegin.getText().toString(),
                            mDataEnd.getText().toString(), mPointId);
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        DatePicker datePicker = datePickerDialog.getDatePicker();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (isEnd) {
            try {
                Date beginDate = sdf.parse(mDataBegin.getText().toString());
                datePicker.setMinDate(beginDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            try {
                Date endDate = sdf.parse(mDataEnd.getText().toString());
                datePicker.setMaxDate(endDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        datePickerDialog.show();
    }

    private String formatMonth(int month) {
        String text = "";
        if (month < 10) {
            text = "0" + month;
        } else {
            text = month + "";
        }
        return text;
    }


    public MyPieChart getPieChart() {
        return mMyPieChart;
    }

    public MyLineChart getLineChart() {
        return mMyLineChart;
    }

    public String getDateText(int offestMonth) {
        String dateText;
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);

        calendar.add(Calendar.MONTH, offestMonth);

        dateText = calendar.get(Calendar.YEAR) + "-"
                + formatMonth(calendar.get(Calendar.MONTH) + 1) + "-"
                + formatMonth(calendar.get(Calendar.DAY_OF_MONTH));

        return dateText;
    }

    @Override
    public void onDestroy() {
        ((MPChartActivity) getActivity()).unRegisterBackListener(mBackListener);
        super.onDestroy();
    }
}
