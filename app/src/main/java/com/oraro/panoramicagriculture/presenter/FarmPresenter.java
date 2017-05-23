package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.FarmHarvest;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.FarmActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/4/11.
 */
public class FarmPresenter extends Presenter<FarmActivity>{
    /**
     *  获取地块信息
     * @param mode  BaseListFragment的加载方式
     * @param method  getFarmField
     * @param jsonObject {"farmId":41}
     * onNext
     */
    public void getFarmField(final int mode, String method, JsonObject jsonObject) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i("AllFieldPresenter", "getFarmField onNext.." + o);
                Gson gson = new Gson();
                List<FarmField> result = new ArrayList<FarmField>();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    result = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<FarmField>>() {
                    }.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUi().onLoadResultData(result);
                getUi().dismissProgress();

            }
        });
    }


    public void getFarmDetail(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (getUi() != null) {
                    getUi().dismissProgress();
                }
            }

            @Override
            public void onNext(Object o) {
                Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                String mJsonStr = mGson.toJson(o);
                List<FarmField> landList = new ArrayList<FarmField>();
                try {
                    JSONObject mJsonObject = new JSONObject(mJsonStr);
                    String mDataStr = mJsonObject.get("data").toString();
                    LogUtils.e("yjd" + mDataStr);
                    landList = mGson.fromJson(mDataStr, new TypeToken<List<FarmField>>(){}.getType());
                    LogUtils.e("yjd landList" + landList.size() + mDataStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUi().onLoadHarvest(landList);
                getUi().dismissProgress();
            }
        });
    }
}
