package com.oraro.panoramicagriculture.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.ui.view.chart.PieChartView;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

public class PieChartActivity extends AppCompatActivity {

    public final static String EXTRA_LINE_CHART_TYPE = "EXTRA_LINE_CHART_TYPE";

    private static final double mPaddingTopPercent = 0.0313;
    private static final double mPaddingLeftPercent = 0.0278;
    private static final double mHeightPercent = 0.234;
    private static final int mPaddingUp = 50;
    private static final int mPaddingRight = 64;

    private int mPaddingTop = 0;
    private int mPaddingLeft = 0;
    private DisplayMetrics mDisplayMetrics;

    private TextView mTitleTv;

    private static final String[][] colors= {{"#257fc8","#2bd1b6"},{"#fdb311","#ff7c01"},{"#c5470f","#bf0fcc"}};
    //private final static  float[]  testData = {0.8f,0.6f,0.5f,0.125f,0.4f,0.8f};
    private static final String[][] crops= {{"木瓜 (80%)","倭瓜 (60%)"},{"南瓜 (50%)","冬瓜 (13%)"},{"西瓜 (40%)","香瓜 (80%)"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
    }

    private void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_pie_chart);

        //从农场进入为Constants.ROLE_FARMER,从采购点进入为Constants.ROLE_PURCHASER
        int mType = -1;
        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){
            mType = mBundle.getInt(EXTRA_LINE_CHART_TYPE);
        }
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        if(Constants.ROLE_FARMER == mType){
            mTitleTv.setText(getString(R.string.farm_main_list_title1));
        }else if(Constants.ROLE_PURCHASER == mType){
            mTitleTv.setText(getString(R.string.purchase_main_list_title1));
        }

        mDisplayMetrics = CommonUtils.getDisplayMetrics(this);
        LinearLayout parent = (LinearLayout) findViewById(R.id.linear);
        mPaddingLeft = (int) (mPaddingLeftPercent * mDisplayMetrics.widthPixels);

        mPaddingTop = (int) (mPaddingTopPercent * mDisplayMetrics.heightPixels);


        addChartContainer(parent);
        addChildContainer(parent);
    }


    private void addChartContainer(LinearLayout parent) {
        int width = mDisplayMetrics.widthPixels - mPaddingLeft * 2;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layoutParams.setMargins(mPaddingLeft, mPaddingTop, mPaddingLeft, 0);
        LinearLayout linearLayout = new LinearLayout(this);
        //图标背景色
        linearLayout.setBackgroundColor(/*Color.parseColor("#292933")*/Color.WHITE);
        linearLayout.setLayoutParams(layoutParams);

        PieChartView pieChartView = new PieChartView(this);
        pieChartView.setCenterXY(mDisplayMetrics.widthPixels / 2, mDisplayMetrics.widthPixels / 2);
        linearLayout.addView(pieChartView);
        parent.addView(linearLayout);
    }


    private void addChildContainer(LinearLayout parent) {
        int width = mDisplayMetrics.widthPixels - mPaddingLeft * 2;
        int height = (int) (mDisplayMetrics.heightPixels * mHeightPercent);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(mPaddingLeft, mPaddingTop, mPaddingLeft, 0);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(mPaddingRight, mPaddingUp, mPaddingRight, 0);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //底部导航背景图片
//        linearLayout.setBackgroundResource(R.drawable.cirle_item);
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setLayoutParams(layoutParams);
        parent.addView(linearLayout);
        int itemHeight = (height - mPaddingUp - (mPaddingUp -10) * 2) / 3;
        addCircleItem(linearLayout, itemHeight);


    }

    private void addCircleItem(LinearLayout parent, int height) {
        for (int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            if (i != 0) {
                layoutParams.setMargins(0,10,0,0);
            }
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            parent.addView(linearLayout);
            for (int j = 0; j < 2; j++) {

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                GradientDrawable myGrad = (GradientDrawable) getResources().getDrawable(R.drawable.circle_shape);
                myGrad.setBounds(0, 0, myGrad.getMinimumWidth(), myGrad.getMinimumHeight());
                myGrad.setColor(Color.parseColor(colors[i][j]));

                TextView textView = new TextView(this);

                if (j == 1) {
                    lp.setMargins(80,0,0,0);
                }
                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setText(crops[i][j]);
                //文字颜色
                textView.setTextColor(Color.parseColor("#8f8f8f"));
                textView.setCompoundDrawablePadding(10);
                textView.setCompoundDrawables(myGrad,null,null,null);
                linearLayout.addView(textView,lp);

            }


        }
    }


}
