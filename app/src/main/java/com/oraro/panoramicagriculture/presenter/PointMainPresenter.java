package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.PointSale;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.ui.adapter.PointMainAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PointMainAdapterImpl;
import com.oraro.panoramicagriculture.ui.adapter.PointManagerAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PurchaseNeedImpl;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by admin on 2017/4/7.
 */
public class PointMainPresenter extends Presenter<PointMainActivity> {
    private final String TAG = this.getClass().getSimpleName();

    private final DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();

    /**
     * 获取需求数据
     * @param adapter
     * @param method
     * @param params
     */
    public void getPurchaseNote(final PointMainAdapterImpl adapter, String method, JsonObject params, final int loadType) {
        LogUtils.e("yjd9 getPurchaseNote" + getUi());
        //getUi().showProgress();
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, " onError  " + e);
                //getUi().dismissProgress();
                if(getUi() != null && getUi().getAdapter().getCurrentLoadType() == loadType){
                    getUi().getSwipeRefreshLayout().setRefreshing(false);
                    getUi().getAdapter().setState(PointManagerAdapter.STATE_LOAD_ERROR);
                }
            }

            @Override
            public void onNext(Object o) {
                LogUtils.e("jw","Object o="+o);
                if(null == getUi() || getUi().getAdapter().getCurrentLoadType() != loadType){
                    return;
                }
//                List<LinkedTreeMap> datas = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
//                List<PurchaseResults> results = new ArrayList<PurchaseResults>();
//                for (int i = 0; i < datas.size(); i++) {
//                    LinkedTreeMap data = datas.get(i);
//                    PurchaseResults pd = new PurchaseResults();
//                    pd.setId(((Double) data.get("id")).longValue());
//                    pd.setVfcropsId(((Double) data.get("vfcropsId")).longValue());
//                    pd.setPurchasingPointId(((Double) data.get("purchasingPointId")).longValue());
//
//                    try {
//                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                        pd.setDate(sf.parse(data.get("date").toString()));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    pd.setNeedsNum((Double) data.get("needsNum"));
//                    pd.setActualNum((Double) data.get("actualNum"));
//
//                    daoSession.getPurchaseResultsDao().deleteByKey(pd.getId());
//                    daoSession.getPurchaseResultsDao().insert(pd);
//
//                    LinkedTreeMap data2 = (LinkedTreeMap) data.get("vfcrops");
//                    VFCrops vf = new VFCrops();
//                    vf.setVfid(((Double) data2.get("vfid")).longValue());
//                    vf.setVfname(data2.get("vfname").toString());
//                    vf.setByname(data2.get("byname").toString());
//                    vf.setPath1(data2.get("path1").toString());
//                    vf.setPath2(data2.get("path2").toString());
//                    vf.setPath3(data2.get("path3").toString());
//
//                    daoSession.getVFCropsDao().deleteByKey(vf.getVfid());
//                    daoSession.getVFCropsDao().insert(vf);
//
//                    LinkedTreeMap data3 = (LinkedTreeMap) data.get("dc");
//                    DCEntity dcEntity=new DCEntity();
//                    dcEntity.setId(((Double) data3.get("id")).longValue());
//                    dcEntity.setDcName(data3.get("dcName").toString());
//                    dcEntity.setLatitude((Double) data3.get("latitude"));
//                    dcEntity.setLongitude((Double) data3.get("longitude"));
//                    dcEntity.setAddress(data3.get("address").toString());
//                    dcEntity.setCountry(data3.get("country").toString());
//                    dcEntity.setProvince(data3.get("province").toString());
//                    dcEntity.setCity(data3.get("city").toString());
//                    dcEntity.setDistrict(data3.get("district").toString());
//                    dcEntity.setStreet(data3.get("street").toString());
//                    dcEntity.setStreetNum(data3.get("streetNum").toString());
//                    dcEntity.setTotalArea((Double) data3.get("totalArea"));
//                    dcEntity.setDcInfo(data3.get("dcInfo").toString());
//                    dcEntity.setSlide1(data3.get("slide1").toString());
//                    dcEntity.setSlide2(data3.get("slide2").toString());
//                    dcEntity.setSlide3(data3.get("slide3").toString());
//                    dcEntity.setSlide4(data3.get("slide4").toString());
//                    dcEntity.setCoverageArea((Double) data3.get("coverageArea"));
//                    dcEntity.setPhoneNum(data3.get("phoneNum").toString());
//                    dcEntity.setUserId(((Double) data3.get("userId")).longValue());
//                    daoSession.getDCEntityDao().deleteByKey(dcEntity.getId());
//                    daoSession.getDCEntityDao().insert(dcEntity);
//                    results.add(pd);
//                }


                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                List<PurchaseResults> results = new ArrayList<PurchaseResults>();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    results = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<PurchaseResults>>() {}.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                Gson gson = new Gson();
//                List<PurchaseResults> results = new ArrayList<PurchaseResults>();
//                try {
//                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
//                    results = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<PurchaseResults>>() {
//                    }.getType());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                LogUtils.e("jw","result=="+results);
////                getUi().onLoadResultData(result);
//                daoSession.getPurchaseResultsDao().deleteAll();
//                daoSession.getPurchaseResultsDao().loadAll();

                if(getUi() != null){
                    int state;
                    if(results.size() < PointMainAdapter.PAGE_COUNT){
                        state = PointMainAdapter.STATE_NO_MORE;
                    }else{
                        state = PointMainAdapter.STATE_LOAD_MORE;
                        getUi().getAdapter().setCurrentPage(getUi().getAdapter().getCurrentPage() + 1);
                    }
                    //getUi().dismissProgress();
                    getUi().getSwipeRefreshLayout().setRefreshing(false);
                    adapter.addItems(results,state);
                }

            }
        });
    }

    /**
     * 删除销售数据
     * @param adapter
     * @param method
     * @param params
     * @param position
     */
    public void revokePurchaseNote(final PurchaseNeedImpl adapter, String method, final JsonObject params, final int position) {
        if(getUi() != null){
            getUi().showProgress();
        }
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, " onError  " + e);
                if(getUi() != null){
                    getUi().dismissProgress();
                }
            }

            @Override
            public void onNext(Object o) {
//                Long havid = params.get("purchasingPointId").getAsLong();
//                PurchaseResultsDao crDao = daoSession.getPurchaseResultsDao();
//                crDao.deleteByKey(havid);
//                for(PurchaseResults prd : adapter.getDataSet()){
//                    if (prd.getId().equals(havid)){
//                        adapter.removeItem(prd);
//                    }
//                }
                adapter.removeItemPostion(position);
                Toast.makeText(getUi(), getUi().getResources().getString(R.string.point_list_revoke_success), Toast.LENGTH_SHORT).show();
                if(getUi() != null){
                    getUi().dismissProgress();
                }
            }
        });
    }

    /**
     * 获取销售数据
     * @param adapter
     * @param method
     * @param params
     */
    public void getSalesData(final PointMainAdapterImpl adapter, String method, JsonObject params, final int loadType) {
        //getUi().showProgress();
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, " onError  " + e);
                //getUi().dismissProgress();
                if(getUi() != null && getUi().getAdapter().getCurrentLoadType() == loadType){
                    getUi().getSwipeRefreshLayout().setRefreshing(false);
                    getUi().getAdapter().setState(PointManagerAdapter.STATE_LOAD_ERROR);
                }
            }

            @Override
            public void onNext(Object o) {
                Log.e("jw",o.toString());
                if(null == getUi() || getUi().getAdapter().getCurrentLoadType() != loadType){
                    return;
                }

                Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                String mJsonStr = mGson.toJson(o);
                List<PointSale> landList = new ArrayList<PointSale>();
                try {
                    JSONObject mJsonObject = new JSONObject(mJsonStr);
                    String mDataStr = mJsonObject.get("data").toString();
                    LogUtils.e("yjd" + mDataStr);
                    landList = mGson.fromJson(mDataStr, new TypeToken<List<PointSale>>(){}.getType());
                    LogUtils.e("yjd" + landList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(getUi() != null){
                    int state;
                    if(landList.size() < PointMainAdapter.PAGE_COUNT){
                        state = PointMainAdapter.STATE_NO_MORE;
                    }else{
                        getUi().getAdapter().setCurrentPage(getUi().getAdapter().getCurrentPage() + 1);
                        state = PointMainAdapter.STATE_LOAD_MORE;
                    }

                    //getUi().dismissProgress();
                    getUi().getSwipeRefreshLayout().setRefreshing(false);
                    adapter.addItems(landList,state);
                }

            }
        });
    }
}
