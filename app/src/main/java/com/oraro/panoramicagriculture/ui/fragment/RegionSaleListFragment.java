package com.oraro.panoramicagriculture.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.presenter.RegionSaleListPresenter;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.RegionSaleListAdapter;
import com.oraro.panoramicagriculture.ui.listener.DateSetListener;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.UIManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/29.
 */
public class RegionSaleListFragment extends BaseDataListFragment<RegionSaleInfo, RegionSaleListPresenter> {
    private String date;

    private String region;

    private int regionType = 1;

    public String getDate() {
        return date;
    }

    public String getRegion() {
        return region;
    }

    @Override
    protected BaseListAdapter<RegionSaleInfo> onSetupAdapter() {
        return new RegionSaleListAdapter(this, BaseListAdapter.ONLY_FOOTER);
    }

    @Override
    protected boolean isNeedTouchRecyclerView() {
        return true;
    }

    @Override
    public RegionSaleListPresenter createPresenter() {
        return new RegionSaleListPresenter();
    }

    @Override
    public BaseFragment getUi() {
        return this;
    }

    @Override
    public void initView() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        date = dateFormat.format(today);
    }


    public int getRegionType() {
        return regionType;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        final JsonObject jsonObject = new JsonObject();

        switch (dateRadio.getCheckedRadioButtonId()) {
            case R.id.rb_today:
                LogUtils.i("rb_today");
                date = dateFormat.format(today);
                jsonObject.addProperty("date", date);
                break;
            case R.id.rb_yesterday:
                LogUtils.i("rb_yesterday");
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                Date yesterday = calendar.getTime();
                date = dateFormat.format(yesterday);
                jsonObject.addProperty("date", date);
                break;
            case R.id.rb_before_yesterday:
                LogUtils.i("rb_before_yesterday");
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                Date beforeYesterday = calendar.getTime();
                date = dateFormat.format(beforeYesterday);
                jsonObject.addProperty("date", date);
                break;
            case R.id.rb_more:
                UIManager.loadDatePicker(getActivity(), new DateSetListener() {
                    @Override
                    public void dataChoosed(String date) {
                        jsonObject.addProperty("date", date);
                        getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
                    }
                });
                break;
        }
        jsonObject.addProperty("type", 1);
        if (position == 0) {
            regionType = 1;
            region = ((OrdinaryDataActivity) getActivity()).getCity();
            jsonObject.addProperty("typeArea", 1);
            jsonObject.addProperty("areaName", ((OrdinaryDataActivity) getActivity()).getCity());
        } else {
            regionType = 2;
            region = spinnerAdapter.getItem(position);
            jsonObject.addProperty("areaName", spinnerAdapter.getItem(position));
            jsonObject.addProperty("typeArea", 2);
        }
        mCurrentPage = 0;
        jsonObject.addProperty("pageNum", 0);
        jsonObject.addProperty("pageSize", 20);
        jsonObject.addProperty("typeTime", 1);
        getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);


        final JsonObject jsonObject = new JsonObject();
        if (regionSpinner.getSelectedItemPosition() == 0) {
            jsonObject.addProperty("typeArea", 1);
            jsonObject.addProperty("areaName", ((OrdinaryDataActivity) getActivity()).getCity());
        } else {
            jsonObject.addProperty("areaName", spinnerAdapter.getItem(regionSpinner.getSelectedItemPosition()));
            jsonObject.addProperty("typeArea", 2);
        }
        mCurrentPage = 0;
        jsonObject.addProperty("type", 1);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("pageNum", 0);
        jsonObject.addProperty("pageSize", 20);
        switch (checkedId) {
            case R.id.rb_today:
                LogUtils.i("rb_today");
                date = dateFormat.format(today);
                jsonObject.addProperty("date", date);
                getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
                break;
            case R.id.rb_yesterday:
                LogUtils.i("rb_yesterday");
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                Date yesterday = calendar.getTime();
                date = dateFormat.format(yesterday);
                jsonObject.addProperty("date", date);
                getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
                break;
            case R.id.rb_before_yesterday:
                LogUtils.i("rb_before_yesterday");
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                Date beforeYesterday = calendar.getTime();
                date = dateFormat.format(beforeYesterday);
                jsonObject.addProperty("date", date);
                getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
                break;
            case R.id.rb_more:
                UIManager.loadDatePicker(getActivity(), new DateSetListener() {
                    @Override
                    public void dataChoosed(String date) {
                        jsonObject.addProperty("date", date);
                        getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }

//    @Override
//    public void onClick(View v) {
////        Intent intent = new Intent(getActivity(), MPChartActivity.class);
////        intent.putExtra("type", Constants.CHART_ROLE_PURCHASER);
////        getActivity().startActivity(intent);
//        rb_today.setChecked(false);
//        rb_yesterday.setChecked(false);
//        rb_before_yesterday.setChecked(false);
//        UIManager.loadDatePicker(getActivity(), new DateSetListener() {
//            @Override
//            public void dataChoosed(String date) {
//                LogUtils.e("jww","date11=="+date);
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("type", 1);
//                if (regionSpinner.getSelectedItemPosition() == 0) {
//                    regionType = 1;
//                    region = ((OrdinaryDataActivity) getActivity()).getCity();
//                    jsonObject.addProperty("typeArea", 1);
//                    jsonObject.addProperty("areaName", ((OrdinaryDataActivity) getActivity()).getCity());
//                } else {
//                    regionType = 2;
//                    region = spinnerAdapter.getItem(regionSpinner.getSelectedItemPosition());
//                    jsonObject.addProperty("areaName", spinnerAdapter.getItem(regionSpinner.getSelectedItemPosition()));
//                    jsonObject.addProperty("typeArea", 2);
//                }
//                mCurrentPage = 0;
//                jsonObject.addProperty("pageNum", 0);
//                jsonObject.addProperty("pageSize", 20);
//                jsonObject.addProperty("typeTime", 1);
//                jsonObject.addProperty("date",date);
//                getPresenter().getSalesData(BaseListFragment.LOAD_MODE_DEFAULT, "getSalesData", jsonObject);
//            }
//        });
//    }
}
