package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.RegisterActivity;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/13.
 */
public class RegisterPresenter extends Presenter<RegisterActivity> {
    /**
     *注册
     * @param method  userAdd
     * @param params  {"userName":"user","password":"user","nickName":"user","phoneNumber":"15236258978","identityNumber":"3215467978561342859","userRole":1,"sex":0}
     */
    public void test(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "onError test" + e);
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "onNext register");
                Toast.makeText(getUi(),getUi().getString(R.string.register_succeed),Toast.LENGTH_SHORT).show();
                getUi().finish();
            }
        });
    }
}
