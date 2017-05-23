package com.oraro.panoramicagriculture.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.fragment.DetailChartFragment;

import java.util.ArrayList;
import java.util.List;

public class MPChartActivity extends BaseActivity {

    private List<OnBackPressListener> mBackListeners = new ArrayList<>();

    public int mType;

    public TextView mTitle;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_mpchart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mType = getIntent().getIntExtra("type",-1)/*Constants.ROLE_REQUIRE*/;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new DetailChartFragment()).commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void registerBackListener(OnBackPressListener listener) {
        mBackListeners.add(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void unRegisterBackListener(OnBackPressListener listener) {
        if (mBackListeners.contains(listener)) {
            mBackListeners.remove(listener);
        }
    }


    @Override
    public void onBackPressed() {
        for (OnBackPressListener listener : mBackListeners) {
            if (0 == listener.onBackPressed()) {
                super.onBackPressed();
            }
        }
    }

    public interface OnBackPressListener {
        int onBackPressed();
    }
}
