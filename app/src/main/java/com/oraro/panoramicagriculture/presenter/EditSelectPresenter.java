package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.EditSelectActivity;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/21 0021.
 *
 * @author
 */
public class EditSelectPresenter extends Presenter<EditSelectActivity> {
    public  void saveFarmselect (String method, JsonObject params){
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
                Log.e("jw","Object o采购=="+o);
               String  string= (String) ((LinkedTreeMap)o).get("desc");
                  Toast.makeText(getUi(), string,Toast.LENGTH_SHORT).show();
               // getUi().finish();

            }
        });
    }
}
