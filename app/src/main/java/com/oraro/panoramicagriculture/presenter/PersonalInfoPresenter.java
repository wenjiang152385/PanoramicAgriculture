package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.UserEntityDao;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.BaseActivity;
import com.oraro.panoramicagriculture.ui.activity.PersonalInfoActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/17.
 */
public class PersonalInfoPresenter extends Presenter<PersonalInfoActivity> {
    private final String TAG = this.getClass().getSimpleName();


    /**
     * 更新用户头像
     *
     * @param userIcon 文件路径
     */
    public void updataUserIcon(String userIcon) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId());
        HttpManager.getInstance().updateUserIcon(userIcon, "userHeadUpload", jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError test" + e);
                CommonUtils.Toast(getUi(), e.getMessage());
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext Login.." + (LinkedTreeMap) o);
                getUi().updateUser2DB();
                getUi().dismissProgress();
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param method     userUpdate
     * @param jsonObject {"userId":33,"nickName":"管理者","phoneNumber":"未知","identityNumber":"9526"}
     */
    public void updateUserInfo(String method, JsonObject jsonObject) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "updateUserInfo onError  " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "updateUserInfo onNext" + o);
                getUi().dismissProgress();
                getUi().updateUser2DB();
            }
        });
    }
}
