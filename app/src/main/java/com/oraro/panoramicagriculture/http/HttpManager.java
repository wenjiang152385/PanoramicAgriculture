package com.oraro.panoramicagriculture.http;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.http.api.HttpService;
import com.oraro.panoramicagriculture.http.retrofit.ApiException;
import com.oraro.panoramicagriculture.http.retrofit.RetrofitUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/10.
 */
public class HttpManager extends RetrofitUtils {
    private String TAG = this.getClass().getSimpleName();
    private final String serverVersion = "4.0";

    protected static final HttpService mService = getRetrofit().create(HttpService.class);

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    //获取单例
    public static HttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public void requestCooperative(Observer<LinkedTreeMap> observer) {
        Log.i("sysout", "HttpManager requestCooperative");
        Observable observable = mService.requestCooperative();
        setSubscribe(observable, observer);
    }

    public void requestPieChartData(Observer<List<LinkedTreeMap>> observer) {
        Log.i("sysout", "HttpManager requestCooperative");
        Observable observable = mService.requestPieChartData();
        setSubscribe(observable, observer);
    }

    public void requestSplineChartData(Observer<List<LinkedTreeMap>> observer) {
        Log.i("sysout", "HttpManager requestCooperative");
        Observable observable = mService.requestSplineChartData();
        setSubscribe(observable, observer);
    }

    public void requestBarChartData(Observer<List<LinkedTreeMap>> observer) {
        Log.i("sysout", "HttpManager requestCooperative");
        Observable observable = mService.requestBarChartData();
        setSubscribe(observable, observer);
    }

    public void test(String method, JsonObject params, Observer<Object> observer) {
        LogUtils.i(TAG, "method=" + method + ",params=" + params);
        Observable observable = mService.test(serverVersion, method, params);
        setSubscribe(observable, observer);
    }

    public Subscription getMainData(String method, JsonObject params, Observer<Object> observer) {
        LogUtils.i(TAG, "method=" + method + ",params=" + params);

        Observable observable = mService.test(serverVersion, method, params);
        Subscription mSubscription = setSubscribe(observable, observer);
        return mSubscription;
    }

    public void updateUserIcon(String userIcon, String method, JsonObject jsonObject, Observer<Object> observer) {

        File file = new File(userIcon);
        Log.i(TAG, "file.." + file.length());
        if (file.length() > 1024 * 1024 * 2) {
            observer.onError(new Throwable("图片太大,上传失败"));
            return;
        }
        Map<String, RequestBody> body = new HashMap<>();
        body.put("file\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        Observable observable = mService.uploadPicture(serverVersion, body, method, jsonObject);
        setSubscribe(observable, observer);
    }

    public void updatePointPicture(List<String> pathList, String method, JsonObject jsonObject, Observer<Object> observer) {
        Map<String, RequestBody> body = new HashMap<>();
        for (String path : pathList) {
            File file = new File(path);
            Log.i(TAG, "file.." + file.length());
            if (file.length() > 1024 * 1024 * 2) {
                observer.onError(new Throwable("图片太大,上传失败"));
                return;
            }
            body.put("file\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        Observable observable = mService.uploadPicture(serverVersion, body, method, jsonObject);
        setSubscribe(observable, observer);
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> Subscription setSubscribe(Observable<T> observable, Observer<T> observer) {
        Subscription mSubscription = observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
        return mSubscription;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            Log.e(TAG, "httpResult..." + httpResult);
            if (httpResult.getResult() != 1) {
                throw new ApiException(httpResult);
            }
            return httpResult.getData();
        }
    }

}
