package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.FarmHarvest;
import com.oraro.panoramicagriculture.presenter.FarmPresenter;
import com.oraro.panoramicagriculture.ui.adapter.FarmAdapter;
import com.oraro.panoramicagriculture.ui.adapter.HolderData;
import com.oraro.panoramicagriculture.ui.fragment.BaseListFragment;
import com.oraro.panoramicagriculture.ui.view.ErrorLayout;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import freemarker.ext.beans.BeanModel;

/**
 * Created by Administrator on 2017/4/11.
 */
public class FarmActivity extends BaseActivity<FarmPresenter> implements ErrorLayout.OnActiveClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ErrorLayout mErrorLayout;

    private FarmAdapter farmAdapter;

    private Long farmId;
    private HolderData holderData;

    public Long getFarmId() {
        return farmId;
    }

    public HolderData getHolderData() {
        return holderData;
    }

    @Override
    public FarmPresenter createPresenter() {
        return new FarmPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        getPresenter().onUiReady(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.actionbar_black));
        setTitle(getString(R.string.farm_main_title));

        mListView = (RecyclerView) findViewById(R.id.list_view);

        mErrorLayout = (ErrorLayout) findViewById(R.id.error_frame);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(
                R.color.light_refresh_progress_background);
        mErrorLayout.setOnActiveClickListener(this);

        farmId = getIntent().getLongExtra("pointId", -1);
        FarmEntity farmEntity = PanoramicAgricultureApplication.getInstances().getDaoSession().getFarmEntityDao().load(farmId);
        holderData = generateHolderData(farmEntity);

        mListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        farmAdapter = new FarmAdapter(this);
        mListView.setAdapter(farmAdapter);
        farmAdapter.onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (farmAdapter.getFarmViewHolderHeader() != null){
            onRefresh();
        }
        farmAdapter.onSliderResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        farmAdapter.onSliderPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(farmAdapter.aMap != null){
            farmAdapter.aMap.clear();
        }
        if(farmAdapter.mapView != null){
            farmAdapter.mapView.onDestroy();
        }
    }

    private HolderData generateHolderData(FarmEntity entity) {
        HolderData mData = new HolderData();
        if (CommonUtils.CheckYouSelf(entity.getUserId())) {
            mData.bYouself = true;
        } else {
            mData.bYouself = false;
        }

        mData.type = Constants.ROLE_FARMER;
        mData.pointId = entity.getFarmId();
        mData.userId = entity.getUserId();
        mData.slider1 = entity.getSlide1();
        mData.slider2 = entity.getSlide2();
        mData.slider3 = entity.getSlide3();
        mData.slider4 = entity.getSlide4();
        mData.name = entity.getFarmName();
        mData.city = entity.getCity();
        mData.info = entity.getFarmInfo();
        mData.phone = entity.getPhoneNum();
        mData.title = getResources().getString(R.string.farm_main_title);
        return mData;
    }

    public void LoadFarmField() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("farmId", farmId);
        getPresenter().getFarmField(BaseListFragment.LOAD_MODE_DEFAULT, "getFarmField", jsonObject);
    }

    public void LoadHarvest(String date) {
        Log.e("yyyy", "1111111111111111111");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("farmId", farmId);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("typeData", 1);
        jsonObject.addProperty("date", date);
        getPresenter().getFarmDetail("getFarmHarvest", jsonObject);
    }

    @Override
    public void onLoadActiveClick() {

    }

    public void onLoadResultData(List<FarmField> result) {
        farmAdapter.setFarmFieldList(result);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onLoadHarvest(List<FarmField> result) {
        farmAdapter.setFarmHarvest(result);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (holderData.bYouself) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.farmpointview, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:
                Intent intent2 = new Intent();
                intent2.setClass(this, NewFarmActivity.class);
                intent2.putExtra("pointId", farmId);
                intent2.putExtra("pointType",NewFarmActivity.POINT_TYPE_FARM);
                startActivityForResult(intent2, 0);
                break;
            case R.id.action_chart:
                if (farmAdapter.getBEHAVIOR_MODE()== -1) {
                    Intent intent = new Intent(FarmActivity.this, MPChartActivity.class);
                    intent.putExtra("pointId", getHolderData().pointId);
                    intent.putExtra("type", Constants.CHART_ROLE_FARM_REQUIRE);
                    startActivityForResult(intent, 100);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (farmAdapter.getBEHAVIOR_MODE() == -1) {
            String date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            switch (farmAdapter.getFarmViewHolderHeader().id_radioGroup.getCheckedRadioButtonId()) {
                case R.id.id_today:
                    date = sf.format(cal.getTime());
                    LoadHarvest(date);
                    break;
                case R.id.id_yesterday:
                    cal.add(cal.DATE, 1);
                    date = sf.format(cal.getTime());
                    LoadHarvest(date);
                    break;
                case R.id.id_threedaysago:
                    cal.add(cal.DATE, 2);
                    date = sf.format(cal.getTime());
                    LoadHarvest(date);
                    break;
            }
        } else {
            LoadFarmField();
        }

    }
}
