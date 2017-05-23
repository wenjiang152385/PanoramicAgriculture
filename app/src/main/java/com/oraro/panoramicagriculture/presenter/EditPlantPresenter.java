package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.EditFarmActivity;
import com.oraro.panoramicagriculture.ui.activity.EditPlantActivity;

import rx.Observer;

/**
 * Created by admin on 2017/3/21.
 */
public class EditPlantPresenter extends Presenter<EditPlantActivity> {
    public  void addAnAcreLandInfo (String method, JsonObject params){
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
                Log.e("yjd", "addAnAcreLandInfo");
                Toast.makeText(getUi(),"提交种植信息成功",Toast.LENGTH_SHORT).show();
                //getUi().finish();
            }
        });
    }
}