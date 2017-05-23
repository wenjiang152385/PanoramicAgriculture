package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.DcPurchaseActivity;

import rx.Observer;

/**
 * Created by Administrator on 2017/5/4 0004.
 *
 * @author
 */
public class DcPurchasePresenter extends Presenter<DcPurchaseActivity> {
    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
    public  void addDCPurchaseData (String method, JsonObject params){
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
