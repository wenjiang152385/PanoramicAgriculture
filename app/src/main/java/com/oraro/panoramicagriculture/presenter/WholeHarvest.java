package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 全局收获接口
 */
public class WholeHarvest extends MPChartData{


    private final static String PIE_METHOD = "getChartByHarvestAndAreaAndDate";
    private final static String LINE_METHOD = "getChartByHarvestAndAreaAndStartDateAndEndDateAndVFcropsId";

    @Override
    public String getExceptNumProperty() {
        return "expectHarvestNum";
    }

    @Override
    public String getActualNumProperty() {
        return "actualHarvestNum";
    }

    @Override
    public String getDateTextProperty() {
        return "expectHarvestTime";
    }

    @Override
    public String getClassName() {
        return "收获";
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
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("date", date);
    }

    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("typeArea", 1);
    }

}
