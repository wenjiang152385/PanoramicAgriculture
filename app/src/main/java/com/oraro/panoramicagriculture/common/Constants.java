package com.oraro.panoramicagriculture.common;

import android.graphics.Color;
import android.os.Environment;

public class Constants {
    public final static String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public final static String ERRORLOGDIR = Environment.getExternalStorageDirectory().toString() + "/";

    public final static int ACCESS_COARSE_LOCATION_REQUEST_CODE = 1;

    public final static int ADMIN = 0;
    public final static int ROLE_FARMER = 1;
    public final static int ROLE_PURCHASER = 2;
    public final static int ROLE_DC = 3;

    public final static int SETTINGS_SHOW_ALL_ICON_FLAG = 0;
    public final static int SETTINGS_SHOW_FARM_ICON_FLAG = 1;
    public final static int SETTINGS_SHOW_PURCHERSER_ICON_FLAG = 2;


    public final static int CHART_ROLE_REQUIRE = 1;
    public final static int CHART_ROLE_POINT_SALE = 2;
    public final static int CHART_ROLE_POINT_REQUIRE = 3;
    public final static int CHART_ROLE_FARM_REQUIRE = 4;
    public final static int CHART_ROLE_DC_ORDER = 5;
    public final static int CHART_ROLE_FARMER = 6;
    public final static int CHART_ROLE_PURCHASER = 7;
    public final static int CHART_ROLE_DC_PURCHASER = 8;
    public final static int CHART_ROLE_DC_NEEDS = 9;

    //    public final static String SERVER_ADDRESS = "http://192.168.9.97:8989/ClosedEconomyCloud/";
//    public final static String SERVER_ADDRESS = "http://192.168.9.64:8080/ClosedEconomyCloud/";
    public final static String SERVER_ADDRESS = "http://192.168.8.11:8080/ClosedEconomyCloud/";
//    public final static String SERVER_ADDRESS = "http://120.55.190.108:9090/ClosedEconomyCloud/";


    public final static int[] colors = {
            Color.parseColor("#a8a830"), Color.parseColor("#18d8a8"), Color.parseColor("#c0c030"),
            Color.parseColor("#a8c0d8"), Color.parseColor("#d8c060"), Color.parseColor("#48d8c0"),
            Color.parseColor("#a8a860"), Color.parseColor("#d8d890"), Color.parseColor("#fff0f0"),
            Color.parseColor("#78d8d8"), Color.parseColor("#ffd830"), Color.parseColor("#a86048"),
            Color.parseColor("#90d890"), Color.parseColor("#fff0a8"), Color.parseColor("#78c078"),
            Color.parseColor("#f0d830"), Color.parseColor("#ffc018"), Color.parseColor("#48c0a8"),
            Color.parseColor("#f0d8d8"), Color.parseColor("#f0f0d8"), Color.parseColor("#d8d8c0"),
            Color.parseColor("#c0d8d8"), Color.parseColor("#607818"), Color.parseColor("#d8d8f0"),
            Color.parseColor("#9090a8"), Color.parseColor("#789018"), Color.parseColor("#60d8c0"),
            Color.parseColor("#c0c060"), Color.parseColor("#f0d8c0"), Color.parseColor("#c0f0f0"),
            Color.parseColor("#c0c0d8"), Color.parseColor("#a8a818"), Color.parseColor("#c0c0c0")};
}