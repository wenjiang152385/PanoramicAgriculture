package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.UserEntityDao;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.LoginActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/10.
 */
public class LoginPresenter extends Presenter<LoginActivity> {
    private final String TAG = this.getClass().getSimpleName();
    private static DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();

    /**
     * 登錄
     *
     * @param method userLogin
     * @param params {"userName":"admin","password":"admin"}
     */
    public void test(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext Login.." + (LinkedTreeMap) o);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    if (mJsonObject.getInt("code") != 0) {
                        CommonUtils.Toast(getUi(), mJsonObject.getString("desc"));
                        return;
                    }
                    UserEntity userEntity = gson.fromJson(mJsonObject.getString("data"), UserEntity.class);

                    UserEntityDao userEntityDao = daoSession.getUserEntityDao();
                    long i = userEntityDao.insertOrReplace(userEntity);
                    PanoramicAgricultureApplication.LOCAL_LOGINED_USER = userEntity;
                    Toast.makeText(getUi(), getUi().getString(R.string.Login), Toast.LENGTH_SHORT).show();
                    getUi().finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
