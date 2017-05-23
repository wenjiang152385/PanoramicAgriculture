package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;
import com.oraro.panoramicagriculture.data.entity.Crops;
import com.oraro.panoramicagriculture.data.entity.HarvestList;
import com.oraro.panoramicagriculture.data.entity.VFCrops;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.ColzaActivity;
import com.oraro.panoramicagriculture.ui.activity.ColzaListActivity;
import com.oraro.panoramicagriculture.ui.fragment.BaseListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/4/20 0020.
 *
 * @author
 */
public class ColzaPresenter extends Presenter<ColzaListActivity> {
    private static DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
    private List<VFCrops> cropsLists;

    public void requestCropsData( String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError1", "e==" + e);
                if (getUi()!=null) {
                    Toast.makeText(getUi().getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNext(Object o) {
                Log.e("onNextVfcrops", "object=" + o);
                Gson gson = new Gson();
                if (getUi()!=null) {
                    try {
                        JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                        cropsLists = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<VFCrops>>() {
                        }.getType());
                        getUi().setlist(cropsLists);
                        getUi().dismissProgress();
                    } catch (Exception e) {
                        LogUtils.e(e.toString() + "----harvest");
                    }
                }

                daoSession.getVFCropsDao().insertOrReplaceInTx(cropsLists);


//                for (int i = 0; i < datasBean.size(); i++) {
//                    LinkedTreeMap data = datasBean.get(i);
//                    Log.e("jw", "data2==" + data);
//                    crops = new Crops();
//                    crops.setCropid(Math.round((Double) data.get("cropid")));
//                    Log.e("jw", "Double.doubleToRawLongBits((Double) data.get==" + Math.round((Double) data.get("cropid")));
//                   // crops.setType1((String) data.get("type1"));
//                    crops.setName((String) data.get("name"));
//                    crops.setType1(Math.round((Double) data.get("cropesConstantId")));
//                    Log.e("jw", "Math.round((Double) data.get(cropesConstantId))==" + Math.round((Double) data.get("cropesConstantId")));
//                    crops.setByname(String.valueOf(data.get("byname")));
//                    crops.setType2(String.valueOf(data.get("type2")));
//                    crops.setClasses(String.valueOf(data.get("classes")));
//                    crops.setFamily(String.valueOf(data.get("family")));
//                    CropsDao cropsDao = daoSession.getCropsDao();
//                    cropsList.add(crops);
//                    if (cropsDao.queryBuilder().where(CropsDao.Properties.Cropid.eq(crops.getCropid())).list().size() > 0) {
//                        continue;
//                    }
//                    cropsDao.insert(crops);
//                }
               // getUi().setRightData(currentClickPosition,cropsList);


            }
        });

    }
}
