package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 全局销售接口
 */
public class WholeSale extends MPChartData{


    private final static String PIE_METHOD = "getSalesData";
    private final static String LINE_METHOD = "getChartSalesDataByAreaAndStartDateAndEndDateAndVFcropsId";

    @Override
    public String getExceptNumProperty() {
        return "saleNumTotal";
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
        return "销售";
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
        jsonObject.addProperty("typeArea", 1);//1是城市2是区县
        jsonObject.addProperty("typeTime", "1");
    }

    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("typeArea", 1);
    }

}
