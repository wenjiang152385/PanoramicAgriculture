package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.UserListActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/4/25.
 */
public class UserListPresenter extends Presenter<UserListActivity> {
    public void getAllUser(String method, JsonObject jsonObject) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("UserListPresenter getAllUser onError..." + e);
                getUi().dismissProgress();
                e.printStackTrace();
            }

            @Override
            public void onNext(Object o) {
                LogUtils.i("UserListPresenter getAllUser onNext..." + o);
                Gson gson = new Gson();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    if (mJsonObject.getInt("code") != 0) {
                        CommonUtils.Toast(getUi(), mJsonObject.getString("desc"));
                        return;
                    }
                    List<UserEntity> userEntityList = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<UserEntity>>() {
                    }.getType());
                    getUi().updateUI(userEntityList);
                    getUi().dismissProgress();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
