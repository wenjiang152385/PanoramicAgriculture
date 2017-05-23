package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.adapter.FragmentAdapter;
import com.oraro.panoramicagriculture.ui.fragment.HarvestListFragment;
import com.oraro.panoramicagriculture.ui.fragment.NeedListFragment;
import com.oraro.panoramicagriculture.ui.fragment.RegionSaleListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class OrdinaryDataActivity extends BaseActivity<Presenter> implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private int offset = 0;
    // 屏幕宽度
    private int screenWidth = 0;
    // 屏幕宽度的三分之一
    private int screen1_3;
    //
    private LinearLayout.LayoutParams lp;
    private View cursor;
    private TextView tab1_tv;
    private TextView tab2_tv;
    private TextView tab3_tv;
    private ViewPager viewPager;
    List<Fragment> fragmentList = new ArrayList<>();

    private String province;
    private String city;

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public BaseActivity getUi() {
        return null;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_crops_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("全局数据");
        viewPager = (ViewPager) findViewById(R.id.third_vp);
        cursor = findViewById(R.id.cursor);
        tab1_tv = (TextView) findViewById(R.id.tab1_tv);
        tab2_tv = (TextView) findViewById(R.id.tab2_tv);
        tab3_tv = (TextView) findViewById(R.id.tab3_tv);
        tab1_tv.setOnClickListener(this);
        tab2_tv.setOnClickListener(this);
        tab3_tv.setOnClickListener(this);

        tab1_tv.setText("销售");
        tab2_tv.setText("需求");
        tab3_tv.setText("收获");

        city = getIntent().getStringExtra("city");
        province = getIntent().getStringExtra("province");
        LogUtils.i("city="+city+",province="+province);
        fragmentList.add(new RegionSaleListFragment());
        fragmentList.add(new NeedListFragment());
        fragmentList.add(new HarvestListFragment());
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) fragmentList));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(this);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screen1_3 = screenWidth / 3;
        lp = (LinearLayout.LayoutParams) cursor.getLayoutParams();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1_tv:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab2_tv:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab3_tv:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_chart:
                Intent intent = new Intent(OrdinaryDataActivity.this, MPChartActivity.class);
                if (viewPager.getCurrentItem() == 0) {
                    intent.putExtra("type", Constants.CHART_ROLE_PURCHASER);
                }else if (viewPager.getCurrentItem() == 1) {
                    intent.putExtra("type", Constants.CHART_ROLE_REQUIRE);
                }else if(viewPager.getCurrentItem() == 2) {
                    intent.putExtra("type", Constants.CHART_ROLE_FARMER);
                }

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ordinary_data_view, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        offset = (screen1_3 - cursor.getLayoutParams().width) / 2;
        Log.d("111", position + "--" + positionOffset + "--"
                + positionOffsetPixels);
        final float scale = getResources().getDisplayMetrics().density;
        if (position == 0) {// 0<->1
            lp.leftMargin = (int) (positionOffsetPixels / 3) + offset;
        } else if (position == 1) {// 1<->2
            lp.leftMargin = (int) (positionOffsetPixels / 3) + screen1_3 + offset;
        }
        cursor.setLayoutParams(lp);
//        currentIndex = position;
        upTextcolor(position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void upTextcolor(int position) {
        if (position == 0) {
            tab1_tv.setTextColor(getResources().getColor(R.color.button_bgd_pressed));
            tab2_tv.setTextColor(getResources().getColor(R.color.text_color_context));
            tab3_tv.setTextColor(getResources().getColor(R.color.text_color_context));
        } else if (position == 1) {
            tab1_tv.setTextColor(getResources().getColor(R.color.text_color_context));
            tab2_tv.setTextColor(getResources().getColor(R.color.button_bgd_pressed));
            tab3_tv.setTextColor(getResources().getColor(R.color.text_color_context));
        } else if (position == 2) {
            tab1_tv.setTextColor(getResources().getColor(R.color.text_color_context));
            tab2_tv.setTextColor(getResources().getColor(R.color.text_color_context));
            tab3_tv.setTextColor(getResources().getColor(R.color.button_bgd_pressed));
        }
    }

}
