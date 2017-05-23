package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.OrderListActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/5/10 0010.
 *
 * @author
 */
public class OrderListPresenter extends Presenter<OrderListActivity> {
    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
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
}
