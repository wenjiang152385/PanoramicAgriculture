package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 某个销售点需求接口
 */
public class PointRequire extends MPChartData{


    private final static String PIE_METHOD = "getPurchaseNeeds";
    private final static String LINE_METHOD = "getPurchaseNeeds";

    @Override
    public String getExceptNumProperty() {
        return "needsNum";
    }

    @Override
    public String getActualNumProperty() {
        return "actualNum";
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
        jsonObject.addProperty("purchasingPointId", id);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeData", 1);
    }

    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("purchasingPointId", id);
        jsonObject.addProperty("typeTime", 2);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeData", 1);
    }


}
