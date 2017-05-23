package com.oraro.panoramicagriculture.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.NeedList;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.fragment.BaseListFragment;
import com.oraro.panoramicagriculture.ui.fragment.NeedListFragment;
import com.oraro.panoramicagriculture.ui.fragment.RegionSaleListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by Administrator on 2017/3/29 0029.
 *
 * @author
 */
public class NeedListPresenter extends Presenter<NeedListFragment> {

    private Subscription subscription;

    public void getneedlist(int mode, String method, JsonObject params) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = HttpManager.getInstance().getMainData(method, params, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("jjww", "onError needlist" + e);
                getUi().onLoadErrorState(BaseListFragment.LOAD_MODE_DEFAULT);
            }

            @Override
            public void onNext(Object o) {
                Log.i("jjww", "onNext 00needlist--" + o);
                Gson gson = new Gson();
                try {
                    JSONObject mJsonObject = new JSONObject(gson.toJson(o));
                    List<NeedList> needLists = gson.fromJson(mJsonObject.getString("data"), new TypeToken<List<NeedList>>() {
                    }.getType());
                    getUi().onLoadFinishState(BaseListFragment.LOAD_MODE_DEFAULT);
                    getUi().onLoadResultData(needLists);
                } catch (Exception e) {
                    LogUtils.e("needlist---" + e.toString() + "--needlist");
                }
            }

        });
    }

    @Override
    public void onUiReady(NeedListFragment ui) {
        super.onUiReady(ui);
    }

    @Override
    public void requestData(int mode, int pageNum) {
        super.requestData(mode, pageNum);

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = new Date();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", 1);//1是城市2是区县
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("pageNum", pageNum);
        jsonObject.addProperty("pageSize", 20);
        jsonObject.addProperty("typeArea", getUi().getRegionType());//1是城市2是区县
        jsonObject.addProperty("areaName", getUi().getRegion());
        jsonObject.addProperty("date", getUi().getDate());
        getneedlist(mode, "getPurchaseNeeds", jsonObject);

    }

}
