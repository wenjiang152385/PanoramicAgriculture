package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.FarmEntityDao;
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.NewFarmActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/14.
 */
public class NewFarmPresenter extends Presenter<NewFarmActivity> {
    private DaoSession daoSession;

    /**
     * 新建农场
     *
     * @param method addFarm
     * @param params {
     *               "farm":{
     *               "address":"江苏省南京市浦口区顶山街道定山大街",
     *               "city":"南京市",
     *               "country":"中华人民共和国",
     *               "district":"浦口区",
     *               "farmInfo":"成本价",
     *               "farmName":"我的城市",
     *               "latitude":32.079948,
     *               "longitude":118.698745,
     *               "phoneNum":"15236548866",
     *               "province":"江苏省",
     *               "street":"",
     *               "streetNum":"",
     *               "totalArea":5,
     *               "userId":33
     *               },
     *               "farmField":[  ///地块集合
     *               {
     *               "fieldArea":2,  // 地块大小
     *               "fieldBorderList":[
     *               {
     *               "longitude":118.694969,
     *               "latitude":32.091655,
     *               "borderIndex":0
     *               },
     *               {
     *               "longitude":118.706382,
     *               "latitude":32.092964,
     *               "borderIndex":1
     *               },
     *               {
     *               "longitude":118.703125,
     *               "latitude":32.08744,
     *               "borderIndex":2
     *               }
     *               ],
     *               "fieldName":"uu", 地块名
     *               "latitude":32.093257,
     *               "longitude":118.70166,
     *               "totalday":0,
     *               "expectHarvestNum":0,
     *               "nextVFcropsId":0,
     *               "plantHistoryId":0,
     *               "plantNum":0,
     *               "actualHarvestNum":0,
     *               "currentVFcropsId":0,
     *               "harvestHistoryId":0,
     *               "state":0,
     *               "plantday":0
     *               }
     *               ]
     *               }
     */
    public void newFarm(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "onError test" + e);
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "onNext newFarm" + o);
                Toast.makeText(getUi(), getUi().getString(R.string.save_succeed), Toast.LENGTH_LONG).show();
                daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
                FarmEntityDao farmEntityDao = daoSession.getFarmEntityDao();
                FarmEntity farmEntity = new FarmEntity();
                LinkedTreeMap data = (LinkedTreeMap) ((LinkedTreeMap) o).get("data");
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
                farmEntity.setTotalArea((Double) data.get("totalArea"));
                farmEntity.setUserId(Math.round((Double) data.get("userId")));
                farmEntity.setFarmInfo((String) data.get("farmInfo"));
                farmEntity.setSlide1((String) data.get("slide1"));
                farmEntity.setSlide2((String) data.get("slide2"));
                farmEntity.setSlide3((String) data.get("slide3"));
                farmEntity.setSlide4((String) data.get("slide4"));
                farmEntity.setPhoneNum((String) data.get("phoneNum"));
                farmEntityDao.insert(farmEntity);
                getUi().uploadPicture(farmEntity.getFarmId());

            }
        });
    }


    public void newDCPoint(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "new dc onnext ===" + o);
                Toast.makeText(getUi(), getUi().getString(R.string.save_succeed), Toast.LENGTH_LONG).show();
                daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
                Gson gson = new Gson();
                JSONObject mJsonObject = null;
                DCEntity dcEntity = null;
                try {
                    mJsonObject = new JSONObject(gson.toJson(o));
                    if (mJsonObject.getInt("code") != 0) {
                        CommonUtils.Toast(getUi(), mJsonObject.getString("desc"));
                        getUi().dismissProgress();
                        return;
                    }
                    dcEntity = gson.fromJson(mJsonObject.getString("data"), DCEntity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUi().uploadPicture(dcEntity.getId());
                daoSession.getDCEntityDao().insertOrReplace(dcEntity);
            }
        });
    }

    /**
     * 新建销售点
     *
     * @param method addPurchase
     * @param params
     */
    public void newPurchasingPoint(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "onError test" + e);
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "onNext newPurchasingPoint   " + o);
                Toast.makeText(getUi(), getUi().getString(R.string.save_succeed), Toast.LENGTH_LONG).show();
                daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
                PurchasingPointDao purchasingPointDao = daoSession.getPurchasingPointDao();
                PurchasingPoint purchasingPoint = new PurchasingPoint();
                LinkedTreeMap data = (LinkedTreeMap) ((LinkedTreeMap) o).get("data");
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
                purchasingPoint.setSlide1((String) data.get("slide1"));
                purchasingPoint.setSlide2((String) data.get("slide2"));
                purchasingPoint.setSlide3((String) data.get("slide3"));
                purchasingPoint.setSlide4((String) data.get("slide4"));
                purchasingPointDao.insert(purchasingPoint);
                getUi().uploadPicture(purchasingPoint.getPurchasingPointId());
            }
        });
    }


    /**
     * 更新销售点
     *
     * @param method purchaseUpdate
     * @param params
     */
    public void purchaseUpdate(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "onError purchaseUpdate");
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                getUi().updateDB();
            }
        });
    }


    /**
     * 农场更新
     *
     * @param method farmUpdate
     * @param params
     */
    public void farmUpdate(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "onError farmUpdate");
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                getUi().updateDB();
            }
        });
    }

    public void DCUpdate(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.i("sysout", "DCUpdate onerror  " + e);
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "DCUpdate onnewxt  " + o);
                getUi().updateDB();
            }
        });
    }

    /**
     * 上传农场、销售点图片
     *
     * @param method     农场；farmSlideUpload， 销售点：purchaseSlideUpload
     * @param jsonObject 农场：   {"farmId", 21}        ，销售点：{"purchasingPointId", 32}
     * @param pathList   文件路径
     */
    public void updatePointPicture(String method, JsonObject jsonObject, List<String> pathList) {
        HttpManager.getInstance().updatePointPicture(pathList, method, jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.i("sysout", "onError updatePointPicture  " + e);
                e.printStackTrace();
                CommonUtils.Toast(getUi(), e.getMessage());
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                Log.i("sysout", "onNext updatePointPicture  " + o);
                getUi().dismissProgress();
                getUi().finish();
            }
        });
    }
}
