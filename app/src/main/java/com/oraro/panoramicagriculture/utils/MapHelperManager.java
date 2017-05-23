package com.oraro.panoramicagriculture.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.GroundOverlay;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.util.List;


/**
 * Created by Administrator on 2017/2/15.
 */
public class MapHelperManager {
    private final String TAG = this.getClass().getSimpleName();


    private static MapHelperManager instance;

    private MapHelperManager() {
    }

    public static MapHelperManager getInstance() {
        if (instance == null) {
            instance = new MapHelperManager();
        }
        return instance;
    }

    /**
     * 地理编码
     *
     * @param context
     * @param city
     * @param address
     * @param listener
     */
    public void geocode(Context context, String city, final String address, final AMapLocationListener listener) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                Log.i(TAG, "Asyn re.." + regeocodeResult);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null) {
                    GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                    LatLonPoint latLonPoint = geocodeAddress.getLatLonPoint();
                    AMapLocation aMapLocation = new AMapLocation("");
                    aMapLocation.setLatitude(latLonPoint.getLatitude());
                    aMapLocation.setLongitude(latLonPoint.getLongitude());
                    aMapLocation.setProvince(geocodeAddress.getProvince());
                    aMapLocation.setCity(geocodeAddress.getCity());
                    aMapLocation.setDistrict(geocodeAddress.getDistrict());
                    listener.onLocationChanged(aMapLocation);
                    Log.i(TAG, "Asyn geo.." + latLonPoint.getLatitude() + "," + latLonPoint.getLongitude() + ",amap.." + aMapLocation);
                } else {
                    listener.onLocationChanged(null);
                }
            }
        });
        GeocodeQuery query = new GeocodeQuery(address, city);
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    /**
     * 逆地理编码
     *
     * @param context
     * @param latLng
     */
    public void reverseGeocode(Context context, LatLng latLng, final AMapLocationListener listener) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                Log.i(TAG, "reverseGeography.." + i + "," + regeocodeResult);
                if (i == 1000) {
                    if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                            && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                        AMapLocation aMapLocation = new AMapLocation("");
                        aMapLocation.setProvince(regeocodeAddress.getProvince());
                        aMapLocation.setCity(regeocodeAddress.getCity());
                        aMapLocation.setDistrict(regeocodeAddress.getDistrict());
                        aMapLocation.setBuildingId(regeocodeAddress.getBuilding());
                        aMapLocation.setStreet(regeocodeAddress.getStreetNumber().getStreet());
                        aMapLocation.setNumber(regeocodeAddress.getStreetNumber().getNumber());
                        aMapLocation.setAddress(regeocodeAddress.getFormatAddress());
                        listener.onLocationChanged(aMapLocation);
                        String regeocodes = regeocodeResult.getRegeocodeAddress().getFormatAddress()
                                + "附近";
                        Log.i(TAG, "" + regeocodes + "," + regeocodeResult.getRegeocodeAddress());
                    }
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }


    /**
     * 获取定位
     *
     * @param context
     * @param listener
     */
    public void getCurrentLocation(Context context, final AMapLocationListener listener) {
        final AMapLocationClient aMapLocationClient = new AMapLocationClient(context.getApplicationContext());
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度模式
        aMapLocationClientOption.setOnceLocationLatest(true);//最近3s内精度最高的一次定位结果
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        listener.onLocationChanged(aMapLocation);
                        Log.i(TAG, "aMapLocation:" + aMapLocation);
                    } else {
                        Log.e(TAG, "location error, ErrorCode:" + aMapLocation.getErrorCode()
                                + ",ErrInfo:" + aMapLocation.getErrorInfo());
                    }
                }
                aMapLocationClient.onDestroy();
            }
        });
        aMapLocationClient.startLocation();
    }

    /**
     * 添加点标记
     *
     * @param aMap
     * @param latLng
     * @return
     */
    public Marker addMarker(AMap aMap, LatLng latLng) {
        MarkerOptions aMapMarkerOptions = new MarkerOptions();
        aMapMarkerOptions.position(latLng);
        Marker marker = aMap.addMarker(aMapMarkerOptions);
        return marker;
    }

    /**
     * 将经纬度转换为度分秒形式
     *
     * @param latlng
     * @return
     */
    public String latlng2MinSec(Double latlng) {
        if (latlng == null) {
            return null;
        }
        String minSec = latlng.intValue() + "°";
        Double min = (latlng - latlng.intValue()) * 60;
        Double sec = (min - min.intValue()) * 60;
        minSec = minSec + min.intValue() + "′";
        minSec = minSec + sec.intValue() + "″";
        return minSec;
    }

    /**
     * 天气查询
     *
     * @param context
     * @param city
     * @param listener
     */
    public void getCityWeather(Context context, String city, WeatherSearch.OnWeatherSearchListener listener) {
        WeatherSearchQuery mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch = new WeatherSearch(context);
        mweathersearch.setOnWeatherSearchListener(listener);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn();
    }

    /**
     * 行政区查询
     *
     * @param context
     * @param district
     * @param listener
     */
    public void districtSearch(Context context, String district, DistrictSearch.OnDistrictSearchListener listener) {
        DistrictSearch search = new DistrictSearch(context);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(district);//传入关键字
        search.setQuery(query);
        search.setOnDistrictSearchListener(listener);//绑定监听器
        search.searchDistrictAnsy();
    }

    /**
     * 绘制线
     *
     * @param latLngs
     * @param aMap
     * @return
     */
    public Polyline addPolyline(List<LatLng> latLngs, AMap aMap) {
        Polyline polyline = null;
        PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngs);
        polyline = aMap.addPolyline(polylineOptions);
        return polyline;
    }

    /**
     * 改变地图视角
     *
     * @param aMap
     * @param latLng
     * @param zoom
     * @param tilt
     * @param bearing
     * @param move
     */
    public void changeCameraPosition(AMap aMap, LatLng latLng, float zoom, float tilt, float bearing, boolean move) {
        CameraUpdate cameraUpdate = null;
        try {
            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    latLng, zoom, tilt, bearing
            ));
        } catch (NullPointerException e) {

        }
        if (move) {
            aMap.moveCamera(cameraUpdate);
        } else {
            aMap.animateCamera(cameraUpdate);
        }
    }

    /**
     * 绘制面
     * @param latLngs
     * @param aMap
     * @return
     */
    public Polygon addPolygon(List<LatLng> latLngs, AMap aMap) {
        Polygon polygon = null;
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(latLngs);
        polygon = aMap.addPolygon(polygonOptions);
        return polygon;
    }

    public GroundOverlay addGroundOverlay(AMap aMap, GroundOverlayOptions groundOverlayOptions){
        return aMap.addGroundOverlay(groundOverlayOptions);
    }
}
