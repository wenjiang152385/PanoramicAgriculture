package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.EditNeedCountActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import rx.Observer;

/**
 * Created by Administrator on 2017/3/17 0017.
 *
 * @author
 */
public class EditNeedCountPresenter extends Presenter<EditNeedCountActivity> {
    public  void purchaseexpectUpdate  (String method, JsonObject params){
        getUi().showProgress();
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("jw", "onError2 " + e);
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                LogUtils.e("jw","object o="+o);
                Toast.makeText(getUi(),"更新预计需求信息成功",Toast.LENGTH_SHORT).show();
                getUi().dismissProgress();
                getUi().finish();

            }
        });
    }
}
