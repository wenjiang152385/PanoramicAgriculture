package com.oraro.panoramicagriculture.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.DCinfoAdapter;
import com.oraro.panoramicagriculture.ui.adapter.FarmListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5 0005.
 *
 * @author
 */
public class DCinfoActivity extends BaseActivity<Presenter> {
    private RecyclerView pointListView;
    private DCinfoAdapter dcinfoAdapter;
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
        setContentView(R.layout.activity_region_crop_point);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pointListView = (RecyclerView) findViewById(R.id.point_list_view);
        pointListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dcinfoAdapter = new DCinfoAdapter(this, BaseListAdapter.NEITHER,this);
        pointListView.setAdapter(dcinfoAdapter);
        dcinfoAdapter.setState(BaseListAdapter.STATE_NO_MORE);

        List<Object> farm = new ArrayList<>();
        farm.addAll(PanoramicAgricultureApplication.getInstances().getDaoSession().getDCEntityDao().loadAll());
        dcinfoAdapter.addItems(farm);
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
}
