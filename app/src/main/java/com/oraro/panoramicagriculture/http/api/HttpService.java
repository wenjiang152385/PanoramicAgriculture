package com.oraro.panoramicagriculture.http.api;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/30.
 */
public interface HttpService {

    @POST("getPostion")
    Observable<LinkedTreeMap> requestCooperative();


    @POST("getBentu")
    Observable<List<LinkedTreeMap>> requestPieChartData();

    @POST("getZhu")
    Observable<List<LinkedTreeMap>> requestBarChartData();

    @POST("getZhe")
    Observable<List<LinkedTreeMap>> requestSplineChartData();

    @POST("api.jsp")
    Observable<Object> test(
            @Query("version") String version,
            @Query("method") String method,
            @Query("params") JsonObject params);

    @Multipart
    @POST("FileUpload.jsp")
    Observable<Object> updateUserIcon(
            @Query("version") String version,
            @PartMap Map<String, RequestBody> usericon,
            @Query("userId") Long userId
    );

    @Multipart
    @POST("FileUpload.jsp")
    Observable<Object> uploadPicture(
            @Query("version") String version,
            @PartMap Map<String, RequestBody> pointPicture,
            @Query("method") String method,
            @Query("params") JsonObject params
    );




}
