package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.presenter.PointMainPresenter;
import com.oraro.panoramicagriculture.ui.adapter.HolderData;
import com.oraro.panoramicagriculture.ui.adapter.PTHolderSwitcher;
import com.oraro.panoramicagriculture.ui.adapter.PointMainAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PointMainFarmAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PointMainPurchaseAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PointSaleImpl;
import com.oraro.panoramicagriculture.ui.adapter.PurchaseNeedImpl;
import com.oraro.panoramicagriculture.ui.view.MyPictureMarqueeView;
import com.oraro.panoramicagriculture.ui.view.MyRecycleView2;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

public class PointMainActivity extends BaseActivity<PointMainPresenter> {
    private MyRecycleView2 id_recycleview;
    private PointMainAdapter mAdapter;
    private int pointid;
    private int type;
    private HolderData holderData;

    private SwipeRefreshLayout mPointMainSwipeRefreshLayout;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public PointMainAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public PointMainPresenter createPresenter() {
        return new PointMainPresenter();
    }

    @Override
    public BaseActivity getUi() {
        LogUtils.e("yjd9 getUi");
        return this;
    }

    @Override
    public void initView() {
        getPresenter().onUiReady(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        setContentView(R.layout.activity_point_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.actionbar_black));
        //setStatusBarUpperAPI21();

        //id_MyPictureMarquee = (MyPictureMarqueeView) findViewById(R.id.id_MyPictureMarquee);
        id_recycleview = (MyRecycleView2) findViewById(R.id.id_recyclerview);
        id_recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mPointMainSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.point_main_swipe_refresh_layout);

        if (getIncoming()) {
            LogUtils.e("yjd9 getIncoming" + type);
            switch (type) {
                case Constants.ROLE_FARMER:
                    setTitle(getString(R.string.farm_main_title));
                    mAdapter = new PointMainFarmAdapter(this, null);
                    id_recycleview.setAdapter(mAdapter);
                    break;
                case Constants.ROLE_PURCHASER:
                    setTitle(getString(R.string.purchase_main_title));
                    PurchasingPointDao purchasingPointDao = PanoramicAgricultureApplication.getInstances().getDaoSession().getPurchasingPointDao();
                    PurchasingPoint purchasingPoint = purchasingPointDao.load((long) pointid);
                    holderData = generateHolderData(purchasingPoint);
                    id_recycleview.setCanEdit(holderData.bYouself);
                    mAdapter = new PointMainPurchaseAdapter(this, holderData);
                    id_recycleview.setAdapter(mAdapter);
                    break;

            }
        }
        //id_MyPictureMarquee.doInit(holderData);
        //initScroll();

        mPointMainSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.onResume();
            }
        });
    }

    public MyRecycleView2 getRecycleView() {
        return id_recycleview;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return mPointMainSwipeRefreshLayout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.onResume();
    }

    private boolean getIncoming() {
        Intent intent = getIntent();
        pointid = (int) intent.getLongExtra("pointId", 0);
        type = intent.getIntExtra("type", 0);
        LogUtils.e("jw","type=="+type);
        //只有销售点会用到所以固定 ROLE_PURCHASER
        if (type==0){
            type = Constants.ROLE_PURCHASER;
        }else {
            type=Constants.ROLE_DC;
        }
        return true;
    }

    private HolderData generateHolderData(PurchasingPoint entity) {
        HolderData mData = new HolderData();
        if (CommonUtils.CheckYouSelf(entity.getUserId())) {
            mData.bYouself = true;
        } else {
            mData.bYouself = false;
        }

        mData.type = type;
        mData.pointId = entity.getPurchasingPointId();
        mData.userId = entity.getUserId();
        mData.slider1 = entity.getSlide1();
        mData.slider2 = entity.getSlide2();
        mData.slider3 = entity.getSlide3();
        mData.slider4 = entity.getSlide4();
        mData.name = entity.getPurchasingPointName();
        mData.city = entity.getCity();
        mData.info = entity.getPurchasingPointInfo();
        mData.phone = entity.getPhoneNum();
        mData.title = getResources().getString(R.string.purchase_main_layout_info);
        return mData;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (type == Constants.ROLE_PURCHASER) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.purchasepointview, menu);
            if (holderData.bYouself) {
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(true);
            } else {
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
            }
        } else if (type == Constants.ROLE_FARMER) {
            if (holderData.bYouself) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.farmpointview, menu);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (type == Constants.ROLE_PURCHASER) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    break;
                case R.id.action_add://增加
                    PTHolderSwitcher.TypeGlobal type = mAdapter.getPTHolderSwitcherType();
                    Intent intent1 = new Intent();
                    intent1.putExtra("purchasePointId", holderData.pointId);
                    if (type == PTHolderSwitcher.TypeGlobal.purchase_page1){
                        intent1.setClass(this, EditSelectActivity.class);
                    }else if (type == PTHolderSwitcher.TypeGlobal.purchase_page2){
                        intent1.setClass(this, AddSaleInfoActivity.class);
                    }
                    startActivity(intent1);
                    break;
                case R.id.action_edit:
                    Intent intent2 = new Intent();
                    intent2.setClass(this, NewFarmActivity.class);
                    intent2.putExtra("pointId", holderData.pointId);
                    intent2.putExtra("pointType",NewFarmActivity.POINT_TYPE_PURCHASE);
                    startActivity(intent2);
                    break;

                case R.id.action_chart:
                    LogUtils.e("action_chart more click");
                    Intent intent = new Intent(PointMainActivity.this, MPChartActivity.class);
                    intent.putExtra("pointId", holderData.pointId);
                    if (mAdapter.getPTHolderSwitcherType() == PTHolderSwitcher.TypeGlobal.purchase_page1){
                        intent.putExtra("type", Constants.CHART_ROLE_POINT_REQUIRE);
                    }else if (mAdapter.getPTHolderSwitcherType() == PTHolderSwitcher.TypeGlobal.purchase_page2){
                        intent.putExtra("type", Constants.CHART_ROLE_POINT_SALE);
                    }
                    startActivityForResult(intent, 100);
                    break;
            }
        } else if (type == Constants.ROLE_FARMER) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    break;
                case R.id.action_edit:
                    Intent intent2 = new Intent();
                    intent2.setClass(this, NewFarmActivity.class);
                    intent2.putExtra("pointId", holderData.pointId);
                    intent2.putExtra("pointType",NewFarmActivity.POINT_TYPE_FARM);
                    startActivityForResult(intent2, 0);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 5.0版本以上
    private void setStatusBarUpperAPI21() {
        Window window = getWindow(); //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) { //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
        ViewCompat.setFitsSystemWindows(mChildView, false); }
    }
}
