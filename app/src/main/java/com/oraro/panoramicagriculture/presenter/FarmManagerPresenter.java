package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.DCEntityDao;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.FarmEntityDao;
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.dao.UserEntityDao;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.FarmManagerActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/14.
 */
public class FarmManagerPresenter extends Presenter<FarmManagerActivity> {
    private final String TAG = this.getClass().getSimpleName();
    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();

    /**
     *我的农场，销售点
     * @param method  getMyPlaceInfo
     * @param params  {"userId":3,"userRole":1}
     */
    public void getMyPlaceInfo(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getMyPlaceInfo onError  " + e);
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext getMyFarmList.." + (LinkedTreeMap) o);
                List<LinkedTreeMap> datas = (ArrayList) ((LinkedTreeMap) o).get("data");
                List<Object> farmEntityList = new ArrayList<>();
                for (LinkedTreeMap data : datas) {
                    if (data.containsKey("purchasingPointId")) {
                        PurchasingPoint purchasingPoint = new PurchasingPoint();
                        purchasingPoint.setPurchasingPointId(Math.round((Double) data.get("purchasingPointId")));
                        purchasingPoint.setPurchasingPointName((String) data.get("purchasingPointName"));
                        purchasingPoint.setLatitude((Double) data.get("latitude"));
                        purchasingPoint.setLongitude((Double) data.get("longitude"));
                        purchasingPoint.setAddress((String) data.get("address"));
                        purchasingPoint.setCountry((String) data.get("country"));
                        purchasingPoint.setProvince((String) data.get("province"));
                        purchasingPoint.setCity((String) data.get("city"));
                        purchasingPoint.setDistrict((String) data.get("district"));
                        purchasingPoint.setStreet((String) data.get("street"));
                        purchasingPoint.setStreetNum((String) data.get("streetNum"));
                        purchasingPoint.setUserId(Math.round((Double) data.get("userId")));
                        purchasingPoint.setPurchasingPointInfo((String) data.get("purchasingPointInfo"));
                        purchasingPoint.setPhoneNum((String) data.get("phoneNum"));
                        purchasingPoint.setSlide1((String) data.get("slide1"));
                        purchasingPoint.setSlide2((String) data.get("slide2"));
                        purchasingPoint.setSlide3((String) data.get("slide3"));
                        purchasingPoint.setSlide4((String) data.get("slide4"));
                        farmEntityList.add(purchasingPoint);
                        PurchasingPointDao purchasingPointDao = daoSession.getPurchasingPointDao();
                        if (purchasingPointDao.load(purchasingPoint.getPurchasingPointId()) == null) {
                            purchasingPointDao.insert(purchasingPoint);
                        } else {
                            purchasingPointDao.update(purchasingPoint);
                        }
                    } else if (data.containsKey("farmId")){
                        FarmEntity farmEntity = new FarmEntity();
                        farmEntity.setFarmId(Math.round((Double) data.get("farmId")));
                        farmEntity.setFarmName((String) data.get("farmName"));
                        farmEntity.setLatitude((Double) data.get("latitude"));
                        farmEntity.setLongitude((Double) data.get("longitude"));
                        farmEntity.setAddress((String) data.get("address"));
                        farmEntity.setCountry((String) data.get("country"));
                        farmEntity.setProvince((String) data.get("province"));
                        farmEntity.setCity((String) data.get("city"));
                        farmEntity.setDistrict((String) data.get("district"));
                        farmEntity.setStreet((String) data.get("street"));
                        farmEntity.setStreetNum((String) data.get("streetNum"));
                        farmEntity.setUserId(Math.round((Double) data.get("userId")));
                        farmEntity.setFarmInfo((String) data.get("farmInfo"));
                        farmEntity.setTotalArea((Double) data.get("totalArea"));
                        farmEntity.setPhoneNum((String) data.get("phoneNum"));
                        farmEntity.setSlide1((String) data.get("slide1"));
                        farmEntity.setSlide2((String) data.get("slide2"));
                        farmEntity.setSlide3((String) data.get("slide3"));
                        farmEntity.setSlide4((String) data.get("slide4"));
                        farmEntityList.add(farmEntity);
                        Log.e(TAG, farmEntityList.size() + " farmname==" + farmEntity.getFarmName());
                        FarmEntityDao farmEntityDao = daoSession.getFarmEntityDao();
                        if (farmEntityDao.load(farmEntity.getFarmId()) == null) {
                            farmEntityDao.insert(farmEntity);
                        } else {
                            farmEntityDao.update(farmEntity);
                        }
                    }else {
                        Gson gson = new Gson();
                        DCEntity dcEntity = gson.fromJson(gson.toJson(data), DCEntity.class);
                        DCEntityDao dcEntityDao = daoSession.getDCEntityDao();
                        if (dcEntityDao.load(dcEntity.getId()) == null) {
                            dcEntityDao.insert(dcEntity);
                        } else {
                            dcEntityDao.update(dcEntity);
                        }
                        farmEntityList.add(dcEntity);
                    }
                }
                getUi().setAdapterFarmData(farmEntityList);
//                getUi().setAdaterPurchasingPointData(purchasingPointList);
            }
        });
    }
}
