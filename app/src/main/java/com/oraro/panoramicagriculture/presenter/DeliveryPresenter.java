package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.DeliveryActivity;

import rx.Observer;

/**
 * Created by Administrator on 2017/5/5 0005.
 *
 * @author
 */
public class DeliveryPresenter extends Presenter<DeliveryActivity> {
    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
    public  void addDCdistribution (String method, JsonObject params){
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
                String  string= (String) ((LinkedTreeMap)o).get("desc");
                Toast.makeText(getUi(), string,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
