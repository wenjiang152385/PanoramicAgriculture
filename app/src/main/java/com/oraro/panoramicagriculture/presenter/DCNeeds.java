package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 某个配送中心配送接口
 */
public class DCNeeds extends MPChartData{


    private final static String PIE_METHOD = "getDistribution";
    private final static String LINE_METHOD = "getDistribution";

    @Override
    public String getExceptNumProperty() {
        return "num";
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
        return "配送";
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
        jsonObject.addProperty("dcId", id);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("type", 2);
    }

    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("dcId", id);
        jsonObject.addProperty("typeTime", 2);
        jsonObject.addProperty("type", 2);
    }


}
