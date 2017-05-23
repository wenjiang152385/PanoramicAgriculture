package com.oraro.panoramicagriculture.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.data.entity.HarvestList;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.fragment.BaseListFragment;
import com.oraro.panoramicagriculture.ui.fragment.HarvestListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by Administrator on 2017/3/29 0029.
 *
 * @author
 */
public class HarvestListPresenter extends Presenter<HarvestListFragment> {
    private Subscription subscription;
    @Override
    public void onUiReady(HarvestListFragment ui) {
        super.onUiReady(ui);
    }

    @Override
    public void requestData(int mode, int pageNum) {
        super.requestData(mode, pageNum);

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = new Date();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", 1);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("pageNum", pageNum);
        jsonObject.addProperty("pageSize", 20);
        jsonObject.addProperty("typeArea", getUi().getRegionType());//1是城市2是区县
        jsonObject.addProperty("areaName", getUi().getRegion());
        jsonObject.addProperty("date", getUi().getDate());
        getChartByHarvestAndAreaAndDate(BaseListFragment.LOAD_MODE_DEFAULT, "getFarmHarvest", jsonObject);
    }

    public void getChartByHarvestAndAreaAndDate(int mode, String method, JsonObject params) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = HttpManager.getInstance().getMainData(method, params, new Observer<Object>() {

            private List<HarvestList> harvestLists;

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i("jjww", "onError harvest" + e);
                getUi().onLoadErrorState(BaseListFragment.LOAD_MODE_DEFAULT);
            }

            @Override
            public void onNext(Object o) {
                LogUtils.i("jjww", "onNext harvest--" + o);
                Gson gson = new Gson();

                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    if (!mJsonObject.has("data")) {
                        harvestLists = new ArrayList<HarvestList>();
                    } else {
                        harvestLists = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<HarvestList>>() {
                        }.getType());
                        LogUtils.e("jjww", "harvestLists==" + harvestLists);
                    }
                    getUi().onLoadFinishState(BaseListFragment.LOAD_MODE_DEFAULT);
                    getUi().onLoadResultData(harvestLists);
                } catch (Exception e) {
                    LogUtils.e(e.toString() + "----harvest");
                }

            }

        });
    }
}
