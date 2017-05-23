package com.oraro.panoramicagriculture.presenter;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.FieldActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/3/23.
 */
public class FieldPresenter extends Presenter<FieldActivity> {

    /**
     * 获取地块信息
     *
     * @param method     getFarmField
     * @param jsonObject {"farmId":41}
     */
    public void getPoint(String method, JsonObject jsonObject) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "getPoint onError  " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "getPoint" + o);
                Gson gson = new Gson();
                List<FarmField> result = new ArrayList<FarmField>();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    result = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<FarmField>>() {
                    }.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUi().updateUIFromServer(result);
            }
        });
    }


    public void updateFarmField(String method, JsonObject jsonObject) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                CommonUtils.Toast(getUi(), "田块更新失败");
                getUi().dismissProgress();
                Log.i("sysout", "updateFarmField  onError" + e);
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "updateFarmField  onNext" + o);
                getUi().dismissProgress();
                if ((Double)((LinkedTreeMap) o).get("code") == 0) {
                    CommonUtils.Toast(getUi(), "田块更新成功");
                } else {
                    CommonUtils.Toast(getUi(), "田块更新失败");
                }
            }
        });
    }
}
