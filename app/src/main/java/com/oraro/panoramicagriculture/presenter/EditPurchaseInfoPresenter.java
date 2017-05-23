package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.EditPurchaseInfoActivity;

import rx.Observer;

/**
 * Created by Administrator on 2017/3/15 0015.
 *
 * @author
 */
public class EditPurchaseInfoPresenter extends Presenter<EditPurchaseInfoActivity> {
    public  void purchaseNeedsUpdate  (String method, JsonObject params){
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("jw", "onError2 " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.e("jw","更新采购=="+o);
                Toast.makeText(getUi(),"更新实际采购信息成功",Toast.LENGTH_SHORT).show();
                 getUi().finish();

            }
        });
    }
}
