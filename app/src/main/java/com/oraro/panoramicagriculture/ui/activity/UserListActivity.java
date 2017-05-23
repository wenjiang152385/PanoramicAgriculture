package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.presenter.UserListPresenter;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.UserListAdapter;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.MapHelperManager;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
public class UserListActivity extends BaseActivity<UserListPresenter> implements BaseListAdapter.OnItemClickListener {
    private RecyclerView userListView;
    private UserListAdapter userListAdapter;

    @Override
    public UserListPresenter createPresenter() {
        return new UserListPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_region_crop_point);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userListView = (RecyclerView) findViewById(R.id.point_list_view);
        userListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        userListAdapter = new UserListAdapter(this, BaseListAdapter.ONLY_FOOTER);

        userListView.setAdapter(userListAdapter);

        userListAdapter.setOnItemClickListener(this);
        showProgress();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userRole", getIntent().getIntExtra("userRole", 0));
        getPresenter().getAllUser("getUser", jsonObject);
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

    public void updateUI(List<UserEntity> userEntityList) {
        userListAdapter.addItems(userEntityList);
    }

    @Override
    public void onItemClick(final int position, long id, View view) {
        UserListActivity.this.showProgress();
        MapHelperManager.getInstance().getCurrentLocation(UserListActivity.this, new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Intent intent = new Intent(UserListActivity.this, NewFarmActivity.class);
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
                intent.putExtra("pointType",userListAdapter.getItem(position).getUserRole().intValue());
                Log.i("sysout", "pointType===" + userListAdapter.getItem(position).getUserRole());
                intent.putExtra("userId", userListAdapter.getItem(position).getUserId());
                LogUtils.i("userListAdapteruserListAdapteruserListAdapter  " + userListAdapter.getItem(position).getUserId());
                startActivity(intent);
                UserListActivity.this.dismissProgress();
                UserListActivity.this.finish();
            }
        });

    }
}
