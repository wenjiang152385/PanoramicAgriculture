package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.data.entity.AbilityModel;
import com.oraro.panoramicagriculture.factory.PointInfoFactory;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.PointInfoActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import rx.Observer;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class PointInfoPresenter extends Presenter<PointInfoActivity> {
    private Ability mAbility;

    @Override
    public void onUiReady(PointInfoActivity ui) {
        super.onUiReady(ui);
//        intent.putExtra("type",holderData.type);
//        intent.putExtra("id",holderData.pointId);
//        intent.putExtra("name",holderData.name);
        getUi().setTitle(getUi().getIntent().getStringExtra("name"));

        JsonObject jsonObject = new JsonObject();
        mAbility = PointInfoFactory.getInstance().getAbility(CommonUtils.ABILITY_TYPE.FARM);
        mAbility.getDataJsonObject(jsonObject, 1);
        setRadarChart(jsonObject, mAbility.getMethod());
    }

    private void setRadarChart(JsonObject jsonObject, String method) {
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("radar", e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e("radar", o.toString());
                AbilityModel abilityModel = mAbility.parserJsonData(o);
                getUi().getMyRadarChart().setData(abilityModel);
            }
        });
    }


}
