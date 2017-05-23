package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.DCPurchaseData;
import com.oraro.panoramicagriculture.data.entity.Distribution;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.DcActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/5/3 0003.
 *
 * @author
 */
public class DcPresenter extends Presenter<DcActivity> {
    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();

    public  void getDCPurchaseData (String method, JsonObject params){

        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("jw", "onError " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.e("jw","Object o=="+o);
                Gson gson = new Gson();
                List<DCPurchaseData> result = new ArrayList<DCPurchaseData>();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    result = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<DCPurchaseData>>() {
                    }.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogUtils.e("jw","result=="+result);
                getUi().onLoadResultData(result);

            }
        });
    }
    public  void getPurchaseNeeds (String method, JsonObject params){

        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("jw", "onError " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.e("jw","Object o=="+o);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                List<PurchaseResults> result = new ArrayList<PurchaseResults>();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    LogUtils.e("jw","mJsonObject="+mJsonObject);
                    LogUtils.e("jw","mJsonObject data ="+mJsonObject.getString("data"));
                    result = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<PurchaseResults>>() {}.getType());
                } catch (JSONException e) {
                    LogUtils.e("jw","e.printStackTrace=="+e.toString());
                    e.printStackTrace();
                }
                LogUtils.e("jw","result=="+result);
                getUi().onPurchaseData(result);

            }
        });
    }
    public  void getDistribution (String method, JsonObject params){

        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("jw", "onError " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.e("jw","Object o=="+o);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                List<Distribution> result = new ArrayList<Distribution>();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    LogUtils.e("jw","mJsonObject="+mJsonObject);
                    LogUtils.e("jw","mJsonObject data ="+mJsonObject.getString("data"));
                    result = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<Distribution>>() {}.getType());
                } catch (JSONException e) {
                    LogUtils.e("jw","e.printStackTrace=="+e.toString());
                    e.printStackTrace();
                }
                LogUtils.e("jw","result=="+result);
                getUi().onDCData(result);

            }
        });
    }
}
