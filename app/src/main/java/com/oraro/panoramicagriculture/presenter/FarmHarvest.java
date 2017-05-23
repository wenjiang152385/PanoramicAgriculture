package com.oraro.panoramicagriculture.presenter;


import com.google.gson.JsonObject;

/**
 * 某个农场收获接口
 */
public class FarmHarvest extends MPChartData{


    private final static String PIE_METHOD = "getFarmHarvest";
    private final static String LINE_METHOD = "getFarmHarvest";

    @Override
    public String getExceptNumProperty() {
        return "sumTotalExpectHarvestNum";
    }

    @Override
    public String getActualNumProperty() {
        return "sumTotalActualHarvestNum";
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
        jsonObject.addProperty("farmId", id);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeData", 2);
        jsonObject.addProperty("date", date);
    }


    @Override
    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id, JsonObject jsonObject) {
        super.setMPDataLineJson(vfcropsId, StartDate, EndDate, id, jsonObject);
        jsonObject.addProperty("farmId", id);
        jsonObject.addProperty("typeTime", 2);
        jsonObject.addProperty("type", 2);
    }



}
