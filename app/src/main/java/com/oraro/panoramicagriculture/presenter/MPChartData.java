package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.data.entity.ChartModel;
import com.oraro.panoramicagriculture.data.entity.MyChartEntry;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public abstract class MPChartData {

    private List<LinkedTreeMap> data;

    private String[] str = {"预计", "实际"};


    public abstract String getExceptNumProperty();

    public abstract String getActualNumProperty();

    public abstract String getDateTextProperty();

    public abstract String getClassName();

    public abstract String getPIEMethodName();

    public abstract String getLINEMethodName();

    public List<LinkedTreeMap> getLinkedTreeMapList(Object o) {
        List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
        return data;
    }


    public String getCityName(List<LinkedTreeMap> list) {
        if (null == list || list.size() <= 0) {
            return "";
        }
        String cityName = (String) list.get(0).get("city");
        String districtName = (String) list.get(0).get("district");
        if (null != districtName) {
            return districtName;
        } else {
            return cityName;
        }
    }


    public String getTitle(String text) {
        return  text + getClassName() + "数据";
    }


    public double getTotalNum(List<LinkedTreeMap> list, String property) {
        double totalNum = 0;
        for (int i = 0; i < list.size(); i++) {
            LinkedTreeMap entity = list.get(i);
            double expectNum = (Double) entity.get(property);
            totalNum += expectNum;
        }
        return totalNum;
    }


    public ChartModel parserJsonData(Object o) {
        data = getLinkedTreeMapList(o);
        ChartModel chartModel = new ChartModel();
        chartModel.setCityName(getCityName(data));
        chartModel.setTotalNum(getTotalNum(data, getExceptNumProperty()));
        List<MyChartEntry> myExceptEntries = new ArrayList<>();
        List<MyChartEntry> myActualEntries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            LinkedTreeMap entity = data.get(i);
            double expectNum = (Double) entity.get(getExceptNumProperty());
            double actualNum = getActualNumProperty() == null ? 0 : (Double) entity.get(getActualNumProperty());
            long vfCropsId = ((Double) entity.get("vfcropsId")).longValue();
            String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
            String dateText = (String) entity.get(getDateTextProperty());
            MyChartEntry myExceptEntry = new MyChartEntry();
            myExceptEntry.setLabel(label);
            myExceptEntry.setVfCropsId(vfCropsId);
            myExceptEntry.setNum(expectNum);
            myExceptEntry.setDateText(dateText);
            myExceptEntries.add(myExceptEntry);

            MyChartEntry myActualEntry = new MyChartEntry();
            myActualEntry.setLabel(label);
            myActualEntry.setVfCropsId(vfCropsId);
            myActualEntry.setNum(actualNum);
            myActualEntry.setVfCropsId(vfCropsId);
            myActualEntry.setVfCropsId(vfCropsId);
            myActualEntries.add(myActualEntry);
        }
        chartModel.setMyExceptEntries(myExceptEntries);
        chartModel.setMyActualEntries(myActualEntries);

        return chartModel;
    }

    public String getLegend(int index) {
        return str[index] + getClassName();
    }


    public String getDataText(double exceptNumber, double actualNumber) {
        String type = getClassName();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str[0] + type + ": "
                + (int) exceptNumber + "\n"
                + str[1] + type + ": "
                + (int) actualNumber);

        return stringBuffer.toString();
    }



    protected void setMPDataPIEJson(String date, long id,JsonObject jsonObject) {
        jsonObject.addProperty("date", date);
    }


    protected void setMPDataLineJson(long vfcropsId, String StartDate, String EndDate, long id,JsonObject jsonObject) {
        jsonObject.addProperty("vfcropsId",vfcropsId);
        jsonObject.addProperty("startDate", StartDate);
        jsonObject.addProperty("endDate", EndDate);
    }


}
