package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.DCEntityDao;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.FarmEntityDao;
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.data.entity.TotalHarvestEntity;
import com.oraro.panoramicagriculture.data.entity.TotalNeedsEntity;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryMapActivity;
import com.oraro.panoramicagriculture.ui.view.SupplyAndDemandView;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/5/18 0018.
 *
 * @author
 */
public class GoudeMapPresenter extends Presenter<OrdinaryMapActivity> {
    private final String TAG = this.getClass().getSimpleName();

    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();

    /**
     * 获取所在地区农场、销售点数据
     *
     * @param method getPlaceInfo
     * @param params {
     *               "regionType":1,   地区类型：0：省，1：市，2：区县
     *               "regionName":"南京市", 地区名
     *               "userRole":0           获取点数据类型：0，全部，1：农场，2：销售点
     *               }
     */
    public void getAllPoint(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, " onError  " + e);
            }

            @Override
            public void onNext(Object o) {
                List<LinkedTreeMap> datas = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                Log.i(TAG, datas.size() + " onNext" + o);
                List pointList = new ArrayList();
                for (int i = 0; i < datas.size(); i++) {
                    LinkedTreeMap data = datas.get(i);
                    LatLng latLng = new LatLng((Double) data.get("latitude"), (Double) data.get("longitude"));
                    FarmEntity farmEntity = new FarmEntity();
                    PurchasingPoint purchasingPoint = new PurchasingPoint();
                    if (data.containsKey("purchasingPointId")) {
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
                        PurchasingPointDao purchasingPointDao = daoSession.getPurchasingPointDao();
                        if (purchasingPointDao.load(purchasingPoint.getPurchasingPointId()) == null) {
                            purchasingPointDao.insert(purchasingPoint);
                        } else {
                            purchasingPointDao.update(purchasingPoint);
                        }
                        pointList.add(purchasingPoint);
                        getUi().addMarker(latLng, purchasingPoint.getPurchasingPointName(), purchasingPoint.getPurchasingPointInfo(), 2, purchasingPoint.getPurchasingPointId());
                    } else if (data.containsKey("farmId")) {
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
                        farmEntity.setPhoneNum((String) data.get("phoneNum"));
                        farmEntity.setSlide1((String) data.get("slide1"));
                        farmEntity.setSlide2((String) data.get("slide2"));
                        farmEntity.setSlide3((String) data.get("slide3"));
                        farmEntity.setSlide4((String) data.get("slide4"));
                        FarmEntityDao farmEntityDao = daoSession.getFarmEntityDao();
                        if (farmEntityDao.load(farmEntity.getFarmId()) == null) {
                            farmEntityDao.insert(farmEntity);
                        } else {
                            farmEntityDao.update(farmEntity);
                        }
                        pointList.add(farmEntity);
                        getUi().addMarker(latLng, farmEntity.getFarmName(), farmEntity.getFarmInfo(), 1, farmEntity.getFarmId());
                    } else {
                        Gson gson = new Gson();
                        DCEntity dcEntity = gson.fromJson(gson.toJson(data), DCEntity.class);
                        DCEntityDao dcEntityDao = daoSession.getDCEntityDao();
                        if (dcEntityDao.load(dcEntity.getId()) == null) {
                            dcEntityDao.insert(dcEntity);
                        } else {
                            dcEntityDao.update(dcEntity);
                        }
                        pointList.add(dcEntity);
                        getUi().addMarker(latLng, dcEntity.getDcName(), dcEntity.getDcInfo(), 3, dcEntity.getId());
                    }
                }
                getUi().addPointListData(pointList);
            }
        });
    }

    /**
     * 获取该城市的总的供需数据
     *
     * @param method getTotalPurchaseNeedsAndFarmFieldHarvest
     * @param params {"areaName":"南京市","typeArea":1,"date":"2017-05-18"}
     *               version = 1.0
     *               2017-04-27 11:09:25.714
     *               {code=0.0,
     *               data=[
     *               {date=2017-05-05, vfcropsId=2.0, totalNeedsNum=140.0},
     *               {date=2017-05-05, vfcropsId=7.0, totalNeedsNum=510.0},
     *               {date=2017-05-05, vfcropsId=8.0, totalNeedsNum=120.0},
     *               {date=2017-05-05, vfcropsId=20.0, totalNeedsNum=130.0},
     *               {date=2017-05-05, vfcropsId=22.0, totalHarvestNum=700.0}
     *               ],
     *               desc=成功}
     *               method = getTotalPurchaseNeedsAndFarmFieldHarvest
     */
    public void getTotalPurchaseNeedsAndFarmFieldHarvest(String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, " onError  " + e);
                if (getUi() != null) {
                    getUi().getSupplyAndDemandTv().setText("获取供求数据失败");
                    getUi().getSupplyAndDemandView().setData(SupplyAndDemandView.GET_DATA_FAIL_STATE,0,null,null);
                }
            }

            @Override
            public void onNext(Object o) {
                String totalNeedsNumKey = "totalNeedsNum";
                String totalHarvestNumKey = "totalHarvestNum";
                int maxNum = 0;
                int state = SupplyAndDemandView.NO_DATA_STATE;
                List<TotalNeedsEntity> totalNeedsEntities = new ArrayList<TotalNeedsEntity>();
                List<TotalHarvestEntity> totalHarvestEntities = new ArrayList<TotalHarvestEntity>();
                for(int i = 1; i < 34; i++){
                    TotalNeedsEntity totalNeedsEntity = new TotalNeedsEntity();
                    totalNeedsEntity.setVfcropsId(i);
                    totalNeedsEntity.setTotalNeedsNum(0);
                    totalNeedsEntities.add(totalNeedsEntity);

                    TotalHarvestEntity totalHarvestEntity = new TotalHarvestEntity();
                    totalHarvestEntity.setCurrentVFcropsId(i);
                    totalHarvestEntity.setTotalHarvestNum(0);
                    totalHarvestEntities.add(totalHarvestEntity);
                }

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    if (mJsonObject.getInt("code") != 0) {
                        if (getUi() != null) {
                            getUi().getSupplyAndDemandTv().setText("暂无供求数据");
                            CommonUtils.Toast(getUi().getApplicationContext(), mJsonObject.getString("desc"));
                        }
                        state = SupplyAndDemandView.NO_DATA_STATE;
                    }else{
                        JSONObject mDataJsonObject = mJsonObject.getJSONObject("data");

                        JSONArray mTotalNeedsNumJSONArray = mDataJsonObject.getJSONArray(totalNeedsNumKey);
                        JSONArray mTotalHarvestNumJSONArray = mDataJsonObject.getJSONArray(totalHarvestNumKey);

                        //供需都没有数据，提示不平衡
                        if(mTotalNeedsNumJSONArray.length() == 0 && mTotalHarvestNumJSONArray.length() == 0){
                            state = SupplyAndDemandView.NO_DATA_STATE;
                        }else{
                            for(int i = 0;i < mTotalNeedsNumJSONArray.length(); i++){
                                TotalNeedsEntity totalNeedsEntity = gson.fromJson(mTotalNeedsNumJSONArray.getJSONObject(i).toString(),TotalNeedsEntity.class);
                                int index = totalNeedsEntity.getVfcropsId()-1;
                                totalNeedsEntities.get(index).setVfcropsId(index);
                                totalNeedsEntities.get(index).setTotalNeedsNum(totalNeedsEntity.getTotalNeedsNum());
                                if(totalNeedsEntity.getTotalNeedsNum() > maxNum){
                                    maxNum = totalNeedsEntity.getTotalNeedsNum();
                                }
                            }

                            for(int i = 0;i < mTotalHarvestNumJSONArray.length(); i++){
                                TotalHarvestEntity totalHarvestEntity = gson.fromJson(mTotalHarvestNumJSONArray.getJSONObject(i).toString(),TotalHarvestEntity.class);
                                int index = totalHarvestEntity.getCurrentVFcropsId()-1;
                                totalHarvestEntities.get(index).setCurrentVFcropsId(index);
                                totalHarvestEntities.get(index).setTotalHarvestNum(totalHarvestEntity.getTotalHarvestNum());
                                if(totalHarvestEntity.getTotalHarvestNum() > maxNum){
                                    maxNum = totalHarvestEntity.getTotalHarvestNum();
                                }
                            }

                            //判断供需是否平衡
                            for(int i = 0; i < 34; i++){
                                TotalNeedsEntity totalNeedsEntity = totalNeedsEntities.get(i);
                                TotalHarvestEntity totalHarvestEntity = totalHarvestEntities.get(i);
                                if(totalNeedsEntity.getTotalNeedsNum() > totalHarvestEntity.getTotalHarvestNum()){
                                    break;
                                }
                            }
                            state = SupplyAndDemandView.GET_DATA_SUCCESS_STATE;
                        }
                    }

                    if (getUi() != null) {
                        //getUi().getSupplyAndDemandTv().setText(isBalance?"供需平衡":"供需不平衡");
                        getUi().getSupplyAndDemandView().setData(state,maxNum,totalNeedsEntities,totalHarvestEntities);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
