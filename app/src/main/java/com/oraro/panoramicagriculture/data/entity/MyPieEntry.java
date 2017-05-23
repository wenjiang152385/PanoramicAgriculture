package com.oraro.panoramicagriculture.data.entity;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public class MyPieEntry {
    private String label;
    private long vfCropsId;
    private double num;
    private String dateText;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getVfCropsId() {
        return vfCropsId;
    }

    public void setVfCropsId(long vfCropsId) {
        this.vfCropsId = vfCropsId;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}
