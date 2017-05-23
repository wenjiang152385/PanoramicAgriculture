package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 某个配送中心订货接口
 */
public class DCOrder extends MPChartData{


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
        return "订货";
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
        jsonObject.addProperty("typeData", 2);
    }

    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("dcId", id);
        jsonObject.addProperty("typeTime", 2);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeData", 2);
    }


}
