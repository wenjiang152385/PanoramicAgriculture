package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.data.entity.PushMessageJson;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.MessageListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/5/16.
 */
public class MessageListPresenter extends Presenter<MessageListActivity> {
    private final String TAG = this.getClass().getSimpleName();

    public void getAllMessage(String method, JsonObject jsonObject) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onnext..." + o);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                try {
                    JSONObject response = new JSONObject(gson.toJson(o));
                    List<PushMessageJson> pushMessageJsonList = gson.fromJson(response.getString("data"), new TypeToken<List<PushMessageJson>>() {
                    }.getType());
                    getUi().updateUI(pushMessageJsonList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
