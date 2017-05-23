package com.oraro.panoramicagriculture.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.data.dao.CropsDao;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;
import com.oraro.panoramicagriculture.data.entity.Crops;
import com.oraro.panoramicagriculture.data.entity.VFCrops;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.CropsActivity;
import com.oraro.panoramicagriculture.ui.fragment.CropsFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/14 0014.
 *
 * @author
 */
public class CropsPresenter extends Presenter<CropsFragment> {

    private static DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
    private List<VFCrops> cropsLists;

    /**
     *
     * @param currentClickPosition
     * @param method
     * @param params
     */

    public void requestCropsData(final int currentClickPosition, String method, JsonObject params) {
        HttpManager.getInstance().test(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError1", "e==" + e);
                Toast.makeText(getUi().getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
                getUi().dismissProgress();
            }

            @Override
            public void onNext(Object o) {
                LogUtils.e("onNextVfcrops", "object=" + o);
                Gson gson = new Gson();

                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                 LogUtils.e("onNextVfcrops","mJsonObject=="+mJsonObject);
                    cropsLists = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<VFCrops>>() {
                    }.getType());
                    LogUtils.e("onNextVfcrops","cropsLists=="+cropsLists);
                    getUi().setRightData(currentClickPosition,cropsLists);
                    getUi().dismissProgress();
                }catch(Exception e){
                    LogUtils.e(e.toString() + "----harvest");
                }
//                List<LinkedTreeMap> datasBean = (ArrayList) ((LinkedTreeMap) o).get("data");
//                Log.e("jw", "data1==" + datasBean);
//                List<VFCrops> cropsList = new ArrayList<VFCrops>();
//                for (int i = 0; i <datasBean.size() ; i++) {
//                         LinkedTreeMap  data=datasBean.get(i);
//                    VFCrops vfcrop=new VFCrops();
//                    vfcrop.setVfid(Math.round((Double)data.get("vfid")));
//                    vfcrop.setVfname(String.valueOf(data.get("vfname")));
//                    vfcrop.setByname(String.valueOf(data.get("byname")));
//                    vfcrop.setPath1(String.valueOf(data.get("path1")));
//                    vfcrop.setPath2(String.valueOf(data.get("path2")));
//                    vfcrop.setPath3(String.valueOf(data.get("path3")));
//                    VFCropsDao vfcropdao=daoSession.getVFCropsDao();
//                    cropsList.add(vfcrop);
//                    if (vfcropdao.queryBuilder().where(VFCropsDao.Properties.Vfid.eq(vfcrop.getVfid())).list().size() > 0) {
//                        continue;
//                    }
//                    vfcropdao.insert(vfcrop);
//                }

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


            }
        });

    }

}
