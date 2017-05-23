package com.oraro.panoramicagriculture.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.NeedList;
import com.oraro.panoramicagriculture.presenter.NeedListPresenter;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.NeedListAdapter;
import com.oraro.panoramicagriculture.ui.listener.DateSetListener;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.UIManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/29 0029.
 *
 * @author
 */
public class NeedListFragment extends BaseDataListFragment<NeedList, NeedListPresenter> {
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
    protected BaseListAdapter<NeedList> onSetupAdapter() {
        return new NeedListAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER, this);
    }

    @Override
    protected boolean isNeedTouchRecyclerView() {
        return true;
    }

    @Override
    public NeedListPresenter createPresenter() {
        return new NeedListPresenter();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                        getPresenter().getneedlist(BaseListFragment.LOAD_MODE_DEFAULT, "getPurchaseNeeds", jsonObject);
                    }
                });
                break;
        }
        jsonObject.addProperty("type", 1);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("pageNum", 0);
        jsonObject.addProperty("pageSize", 20);
        if (position == 0) {
            regionType = 1;
            region = ((OrdinaryDataActivity) getActivity()).getCity();
            jsonObject.addProperty("typeArea", 1);
            jsonObject.addProperty("areaName", region);
        } else {
            regionType = 2;
            region = spinnerAdapter.getItem(position);
            LogUtils.e("jw", "spinnerAdapter.getItem(position)==" + spinnerAdapter.getItem(position));
            jsonObject.addProperty("areaName", region);
            jsonObject.addProperty("typeArea", 2);
        }
        mCurrentPage = 0;
        getPresenter().getneedlist(BaseListFragment.LOAD_MODE_DEFAULT, "getPurchaseNeeds", jsonObject);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        final JsonObject jsonObject = new JsonObject();

        switch (checkedId) {
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
                Date before_yesterday = calendar.getTime();
                date = dateFormat.format(before_yesterday);
                jsonObject.addProperty("date", date);
                break;
            case R.id.rb_more:
                UIManager.loadDatePicker(getActivity(), new DateSetListener() {
                    @Override
                    public void dataChoosed(String date) {
                        jsonObject.addProperty("date", date);
                        getPresenter().getneedlist(BaseListFragment.LOAD_MODE_DEFAULT, "getPurchaseNeeds", jsonObject);
                    }
                });
                break;
        }
        jsonObject.addProperty("type", 1);//1是城市2是区县
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("pageNum", 0);
        jsonObject.addProperty("pageSize", 20);
        if (regionSpinner.getSelectedItemPosition() == 0) {
            regionType = 1;
            region = ((OrdinaryDataActivity) getActivity()).getCity();
            jsonObject.addProperty("areaName", region);
        } else {
            regionType = 2;
            region = spinnerAdapter.getItem(regionSpinner.getSelectedItemPosition());
            jsonObject.addProperty("areaName", region);
        }
        mCurrentPage = 0;
        getPresenter().getneedlist(BaseListFragment.LOAD_MODE_DEFAULT, "getPurchaseNeeds", jsonObject);
    }

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(getActivity(), MPChartActivity.class);
//        intent.putExtra("type", Constants.CHART_ROLE_REQUIRE);
//        getActivity().startActivity(intent);
//        rb_today.setChecked(false);
//        rb_yesterday.setChecked(false);
//        rb_yesterday.setChecked(false);
//        UIManager.loadDatePicker(getActivity(), new DateSetListener() {
//            @Override
//            public void dataChoosed(String date) {
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("type", 1);//1是城市2是区县
//                jsonObject.addProperty("typeTime", 1);
//                jsonObject.addProperty("typeArea", 1);
//                jsonObject.addProperty("pageNum", 0);
//                jsonObject.addProperty("pageSize", 20);
//                if (regionSpinner.getSelectedItemPosition() == 0) {
//                    regionType = 1;
//                    region = ((OrdinaryDataActivity) getActivity()).getCity();
//                    jsonObject.addProperty("areaName", region);
//                } else {
//                    regionType = 2;
//                    region = spinnerAdapter.getItem(regionSpinner.getSelectedItemPosition());
//                    jsonObject.addProperty("areaName", region);
//                }
//                mCurrentPage = 0;
//                getPresenter().getneedlist(BaseListFragment.LOAD_MODE_DEFAULT, "getPurchaseNeeds", jsonObject);
//            }
//        });
    }
}
