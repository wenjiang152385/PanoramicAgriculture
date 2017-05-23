package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.presenter.FarmManagerPresenter;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PointManagerAdapter;
import com.oraro.panoramicagriculture.utils.MapHelperManager;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
public class FarmManagerActivity extends BaseActivity<FarmManagerPresenter> implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    private LinearLayout headLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CoordinatorLayout rootLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private RecyclerView farmList;
    private PointManagerAdapter adapter;
    private LinearLayout newPointLayout;
    private TextView farmNumText;
    private LinearLayout loadingView;
    private TextView activityTitle;
    private TextView farmNumTitle;
    private TextView newPointText;
    private TextView pointCropsTitle;

    @Override
    public FarmManagerPresenter createPresenter() {
        return new FarmManagerPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_farm_manager);
//        setTitle(getString(R.string.my_farm));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        headLayout = (LinearLayout) findViewById(R.id.head_layout);
        rootLayout = (CoordinatorLayout) findViewById(R.id.root_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        newPointLayout = (LinearLayout) findViewById(R.id.new_point_layout);
        farmList = (RecyclerView) findViewById(R.id.farm_list);
        farmNumText = (TextView) appBarLayout.findViewById(R.id.tv_farm_num);
        loadingView = (LinearLayout) findViewById(R.id.tv_loading);
        activityTitle = (TextView) findViewById(R.id.tv_point_title);
        farmNumTitle = (TextView) findViewById(R.id.tv_farm_num_title);
        newPointText = (TextView) findViewById(R.id.new_point_text);
        pointCropsTitle = (TextView) findViewById(R.id.point_crop_num_title);
        newPointLayout.setOnClickListener(this);

        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.mipmap.my_point_logo));
        appBarLayout.addOnOffsetChangedListener(this);
        farmList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new PointManagerAdapter(this, BaseListAdapter.ONLY_HEADER);
        farmList.setAdapter(adapter);


        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_PURCHASER) {
            activityTitle.setText(getString(R.string.my_purchaser));
            farmNumTitle.setText(getString(R.string.purchaser_num));
            newPointText.setText(getString(R.string.new_purchasing_point));
            pointCropsTitle.setText(getString(R.string.purchase_crops_title));
        } else if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_DC) {
            activityTitle.setText(getString(R.string.my_dc));
            farmNumTitle.setText(getString(R.string.dc_num));
            pointCropsTitle.setText(getString(R.string.dc_crops_title));
        }
    }

    public void setAdapterFarmData(List<Object> farmEntityList) {
        loadingView.setVisibility(View.GONE);
        adapter.clear();
        adapter.addItems(farmEntityList);
        FarmManagerActivity.this.farmNumText.setText(String.valueOf(farmEntityList.size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId());
        jsonObject.addProperty("userRole", PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole());
        getPresenter().getMyPlaceInfo("getMyPlaceInfo", jsonObject);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_point_layout:
                FarmManagerActivity.this.showProgress();
                MapHelperManager.getInstance().getCurrentLocation(FarmManagerActivity.this, new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        Intent intent = new Intent(FarmManagerActivity.this, NewFarmActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("latitude", String.valueOf(aMapLocation.getLatitude()));
                        bundle.putString("longitude", String.valueOf(aMapLocation.getLongitude()));
                        bundle.putString("province", aMapLocation.getProvince());
                        bundle.putString("city", aMapLocation.getCity());
                        bundle.putString("district", aMapLocation.getDistrict());
                        bundle.putString("address", aMapLocation.getAddress());
                        bundle.putString("street", aMapLocation.getStreet());
                        bundle.putString("streetNum", aMapLocation.getStreetNum());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        FarmManagerActivity.this.dismissProgress();
                    }
                });
                break;
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset <= -headLayout.getHeight() / 2) {
            if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_FARMER) {
                collapsingToolbarLayout.setTitle(getString(R.string.my_farm));
            } else {
                collapsingToolbarLayout.setTitle(getString(R.string.my_purchaser));
            }
        } else {
            collapsingToolbarLayout.setTitle(" ");
        }
    }
}
