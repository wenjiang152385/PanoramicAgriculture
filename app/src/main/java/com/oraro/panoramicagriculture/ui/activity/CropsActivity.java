package com.oraro.panoramicagriculture.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.CropsPresenter;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.adapter.FragmentAdapter;
import com.oraro.panoramicagriculture.ui.fragment.CropsFragment;
import com.oraro.panoramicagriculture.ui.fragment.EconomicCropFragment;
import com.oraro.panoramicagriculture.ui.fragment.FeedCropFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 *
 * @author
 */
public class CropsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    // 指示器的偏移量
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        setContentView(R.layout.activity_crops_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("农作物");
        viewPager = (ViewPager) findViewById(R.id.third_vp);
        cursor = findViewById(R.id.cursor);
        tab1_tv = (TextView) findViewById(R.id.tab1_tv);
        tab2_tv = (TextView) findViewById(R.id.tab2_tv);
        tab3_tv = (TextView) findViewById(R.id.tab3_tv);
        tab1_tv.setOnClickListener(this);
        tab2_tv.setOnClickListener(this);
        tab3_tv.setOnClickListener(this);

        List<Fragment> fragmentList = new ArrayList<>();
        CropsFragment cropsFragment = new CropsFragment();
        fragmentList.add(cropsFragment);
        EconomicCropFragment economicCropFragment = new EconomicCropFragment();
        fragmentList.add(economicCropFragment);
        FeedCropFragment feedCropFragment = new FeedCropFragment();
        fragmentList.add(feedCropFragment);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("jjj", "requestCode==" + requestCode);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Log.e("jjww", "extras==" + extras);
            ArrayList<String> names = extras.getStringArrayList("names");

            ArrayList<Integer> ids = extras.getIntegerArrayList("ids");
            Intent intent = new Intent();
            Bundle bundle1 = new Bundle();
            bundle1.putStringArrayList("names", names);
            bundle1.putIntegerArrayList("ids", ids);
            intent.putExtras(bundle1);
            setResult(RESULT_OK, intent);
            CropsActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.serachview, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("jw", "query==" + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("jw", "newText==" + newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                break;
            case R.id.action_save:

                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onPageSelected(int position) {
        Log.e("111", "position==" + position);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
}
