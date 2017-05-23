package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.fragment.BaseListFragment;
import com.oraro.panoramicagriculture.ui.fragment.RegionSaleListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by Administrator on 2017/3/29.
 */
public class RegionSaleListPresenter extends Presenter<RegionSaleListFragment> {


    private DataObserver dataObserver = new DataObserver(BaseListFragment.LOAD_MODE_DEFAULT);
    private Subscription subscription;

    @Override
    public void onUiReady(RegionSaleListFragment ui) {
        super.onUiReady(ui);
    }

    @Override
    public void requestData(int mode, int pageNum) {
        super.requestData(mode, pageNum);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", getUi().getRegion());
        jsonObject.addProperty("type", 1);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("typeArea", getUi().getRegionType());//1是城市2是区县
        jsonObject.addProperty("pageNum", pageNum);
        jsonObject.addProperty("pageSize", 20);
        jsonObject.addProperty("date", getUi().getDate());
        getSalesData(mode, "getSalesData", jsonObject);
    }

    /**
     * 获取销售数据
     *
     * @param method     getSalesData
     * @param jsonObject {
     *                   "areaName":"南京市",    // 地区名
     *                   "type":1,              //  1：总销售数据；2：单个销售点数据
     *                   "typeTime":1,          //  1：某一天     2： 一段时间
     *                   "typeArea":1,          //  1；城市       2：区县
     *                   "date":"2017-03-31"    //
     *                   }
     */
    public void getSalesData(final int mode, String method, JsonObject jsonObject) {
        dataObserver.setMode(mode);
        if (subscription != null) {
            subscription.unsubscribe();
            LogUtils.i("getSalesDatagetSalesData../...."+subscription.isUnsubscribed());
        }
        subscription = HttpManager.getInstance().getMainData(method,jsonObject,dataObserver);
    }

    class DataObserver implements Observer {
        public DataObserver(int mode) {
            this.mode = mode;
        }

        private int mode;

        @Override
        public void onCompleted() {
            LogUtils.i("getSalesData  onCompleted..");
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.i("getSalesData  onError.." + e);
        }

        @Override
        public void onNext(Object o) {
            LogUtils.i("getSalesData  onNext.." + o);
            Gson gson = new Gson();
            try {
                JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                List<RegionSaleInfo> regionSaleInfoList = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<RegionSaleInfo>>() {
                }.getType());
                getUi().onLoadFinishState(mode);
                getUi().onLoadResultData(regionSaleInfoList);
            } catch (Exception e) {
                LogUtils.e("getSalesData onNext" + e.toString());
            }
        }

        public void setMode(int mode) {
            this.mode = mode;
        }
    }
}
