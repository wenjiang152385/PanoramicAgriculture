package com.oraro.panoramicagriculture.data.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public class ChartModel {
    private String cityName;
    private double totalNum;
    private List<MyChartEntry> myExceptEntries;
    private List<MyChartEntry> myActualEntries;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public List<MyChartEntry> getMyExceptEntries() {
        return myExceptEntries;
    }

    public void setMyExceptEntries(List<MyChartEntry> myExceptEntries) {
        this.myExceptEntries = myExceptEntries;
    }

    public List<MyChartEntry> getMyActualEntries() {
        return myActualEntries;
    }

    public void setMyActualEntries(List<MyChartEntry> myActualEntries) {
        this.myActualEntries = myActualEntries;
    }

}
