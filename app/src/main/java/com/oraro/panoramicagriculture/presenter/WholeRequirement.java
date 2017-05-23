package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 全局需求接口
 */
public class WholeRequirement extends MPChartData{


    private final static String PIE_METHOD = "getPurchaseNeeds";
    private final static String LINE_METHOD = "getChartPurchaseNeedsByAreaAndStartDateAndEndDateAndVFcropsId";


    @Override
    public String getExceptNumProperty() {
        return "totalPurchaseNeeds";
    }

    @Override
    public String getActualNumProperty() {
        return null;
    }

    @Override
    public String getDateTextProperty() {
        return "date";
    }

    @Override
    public String getClassName() {
        return "需求";
    }

    @Override
    public String getPIEMethodName() {
        return PIE_METHOD;
    }

    @Override
    public String getLINEMethodName() {
        return LINE_METHOD;
    }


    @Override
    protected void setMPDataPIEJson(String date, long id, JsonObject jsonObject) {
        super.setMPDataPIEJson(date, id, jsonObject);
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("type", 1);//1是城市2是区县
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("typeArea", 1);
    }

    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("typeArea", 1);
    }

}
