package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.DCPurchaseData;
import com.oraro.panoramicagriculture.data.entity.Distribution;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.presenter.DcPresenter;
import com.oraro.panoramicagriculture.ui.adapter.DcAdapter;
import com.oraro.panoramicagriculture.ui.adapter.FarmAdapter;
import com.oraro.panoramicagriculture.ui.adapter.HolderData;
import com.oraro.panoramicagriculture.ui.fragment.BaseListFragment;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3 0003.
 *
 * @author
 */
public class DcActivity extends BaseActivity<DcPresenter> implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HolderData holderData;
    public long pointId;
    private DcAdapter dcAdapter;

    private DCEntity dcEntity;

    public DCEntity getDcEntity() {
        return dcEntity;
    }

    @Override
    public DcPresenter createPresenter() {
        return new DcPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_dc);
        getPresenter().onUiReady(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.actionbar_black));
        setTitle("配送中心概况");
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(
                R.color.light_refresh_progress_background);

           Intent intent= getIntent();
        pointId = intent.getLongExtra("pointId", -1);
        LogUtils.e("jw","pointid=="+pointId);
        dcEntity = PanoramicAgricultureApplication.getInstances().getDaoSession().getDCEntityDao().load(pointId);
        holderData = generateHolderData(dcEntity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dcAdapter = new DcAdapter(this);
        recyclerView.setAdapter(dcAdapter);
        dcAdapter.onResume();
    }

    @Override
    public void onRefresh() {
        if (dcAdapter.getSTATE_MODE() == 3) {
            String date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            switch (dcAdapter.getDcViewHolderHeader().id_radioGroup.getCheckedRadioButtonId()) {
                case R.id.id_today:
                    date = sf.format(cal.getTime());
                    LoadDcpurchase(date);
                    break;
                case R.id.id_yesterday:
                    cal.add(cal.DATE, 1);
                    date = sf.format(cal.getTime());
                    LoadDcpurchase(date);
                    break;
                case R.id.id_threedaysago:
                    cal.add(cal.DATE, 2);
                    date = sf.format(cal.getTime());
                    LoadDcpurchase(date);
                    break;
            }
        }else if (dcAdapter.getSTATE_MODE() == 4){
            String date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            switch (dcAdapter.getDcViewHolderHeader().id_radioGroup.getCheckedRadioButtonId()) {
                case R.id.id_today:
                    date = sf.format(cal.getTime());
                    LoadOrderData(date);
                    break;
                case R.id.id_yesterday:
                    cal.add(cal.DATE, 1);
                    date = sf.format(cal.getTime());
                    LoadOrderData(date);
                    break;
                case R.id.id_threedaysago:
                    cal.add(cal.DATE, 2);
                    date = sf.format(cal.getTime());
                    LoadOrderData(date);
                    break;
            }

        }else if (dcAdapter.getSTATE_MODE()==5){
            String date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            switch (dcAdapter.getDcViewHolderHeader().id_radioGroup.getCheckedRadioButtonId()) {
                case R.id.id_today:
                    date = sf.format(cal.getTime());
                   LoadDCData(date);
                    break;
                case R.id.id_yesterday:
                    cal.add(cal.DATE, 1);
                    date = sf.format(cal.getTime());
                    LoadDCData(date);
                    break;
                case R.id.id_threedaysago:
                    cal.add(cal.DATE, 2);
                    date = sf.format(cal.getTime());
                    LoadDCData(date);
                    break;
            }
        }
        else if (dcAdapter.getSTATE_MODE()==6){
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dcAdapter.getDcViewHolderHeader()!=null){
            onRefresh();
        }
        dcAdapter.onSliderResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    dcAdapter.onSliderPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (holderData.bYouself) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.dcpurchase, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dcAdapter.recycleMapView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit_dc:
                Intent intent2 = new Intent();
                intent2.setClass(this, NewFarmActivity.class);
                intent2.putExtra("pointId", pointId);
                intent2.putExtra("pointType",NewFarmActivity.POINT_TYPE_DC);
                startActivityForResult(intent2, 0);
                break;
            case R.id.action_add_dc1:
                Intent intent1 = new Intent();
                intent1.setClass(this, DcPurchaseActivity.class);
                intent1.putExtra("pointId", pointId);
                startActivity(intent1);
                break;
            case R.id.action_add_dc3:
                Intent intent3 = new Intent();
                intent3.setClass(this, DeliveryActivity.class);
                intent3.putExtra("pointId", pointId);
                startActivity(intent3);
                break;

            case R.id.action_chart:
                Intent intent = new Intent(DcActivity.this, MPChartActivity.class);;
                if (dcAdapter.getSTATE_MODE() == 4) {
                    intent.putExtra("type", Constants.CHART_ROLE_DC_ORDER);
                }else if (dcAdapter.getSTATE_MODE() == 3) {
                    intent.putExtra("type", Constants.CHART_ROLE_DC_PURCHASER);
                }else if (dcAdapter.getSTATE_MODE() == 5) {
                    intent.putExtra("type", Constants.CHART_ROLE_DC_NEEDS);
                }
                intent.putExtra("pointId", pointId);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public HolderData getHolderData() {
        return holderData;
    }
    private HolderData generateHolderData(DCEntity entity) {
        HolderData mData = new HolderData();
        if (CommonUtils.CheckYouSelf(entity.getUserId())) {
            mData.bYouself = true;
        } else {
            mData.bYouself = false;
        }

        mData.type = Constants.ROLE_DC;
        mData.pointId = entity.getId();
        mData.userId = entity.getUserId();
        mData.slider1 = entity.getSlide1();
        mData.slider2 = entity.getSlide2();
        mData.slider3 = entity.getSlide3();
        mData.slider4 = entity.getSlide4();
        mData.name = entity.getDcName();
        mData.city = entity.getCity();
        mData.info = entity.getDcInfo();
        mData.phone = entity.getPhoneNum();
        mData.title = getResources().getString(R.string.dc_main_title);
        return mData;
    }

    public void onLoadResultData(List<DCPurchaseData> result) {
        dcAdapter.setDcPurchaseDataList(result);
        mSwipeRefreshLayout.setRefreshing(false);
    }
    public void LoadDcpurchase(String date ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dcId", pointId);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("date", date);
        getPresenter().getDCPurchaseData("getDCPurchaseData", jsonObject);
    }

    public void onPurchaseData(List<PurchaseResults> result) {
      dcAdapter.setpurchaseResultsDataList(result);
        mSwipeRefreshLayout.setRefreshing(false);
    }
    public void LoadOrderData(String date ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dcId", pointId);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("typeData",2);
        getPresenter().getPurchaseNeeds("getPurchaseNeeds", jsonObject);
    }
    public void  onDCData(List<Distribution> result){
        dcAdapter.setDistributionListData(result);
        mSwipeRefreshLayout.setRefreshing(false);

    }
    public void LoadDCData(String date){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type",2);
        jsonObject.addProperty("typeTime",1);
        jsonObject.addProperty("dcId",pointId);
        jsonObject.addProperty("date",date);
        getPresenter().getDistribution("getDistribution",jsonObject);
    }
}
