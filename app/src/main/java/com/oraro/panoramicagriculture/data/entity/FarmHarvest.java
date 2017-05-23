package com.oraro.panoramicagriculture.data.entity;

import java.util.Date;

/**
 * Created by admin on 2017/3/31.
 */
public class FarmHarvest {
    private int id;
    private int farmFieldId;
    private int vfcropsId;
    private VFCrops vfcrops;
    private Date expectHarvestTime;
    private String expectHarvestNum;
    private String actualHarvestNum;
    private int farmId;

    public int getVfcropsId() {
        return vfcropsId;
    }

    public void setVfcropsId(int vfcropsId) {
        this.vfcropsId = vfcropsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarmFieldId() {
        return farmFieldId;
    }

    public void setFarmFieldId(int farmFieldId) {
        this.farmFieldId = farmFieldId;
    }

    public VFCrops getVfcrops() {
        return vfcrops;
    }

    public void setVfcrops(VFCrops vfcrops) {
        this.vfcrops = vfcrops;
    }

    public Date getExpectHarvestTime() {
        return expectHarvestTime;
    }

    public void setExpectHarvestTime(Date expectHarvestTime) {
        this.expectHarvestTime = expectHarvestTime;
    }

    public String getExpectHarvestNum() {
        return expectHarvestNum;
    }

    public void setExpectHarvestNum(String expectHarvestNum) {
        this.expectHarvestNum = expectHarvestNum;
    }

    public String getActualHarvestNum() {
        return actualHarvestNum;
    }

    public void setActualHarvestNum(String actualHarvestNum) {
        this.actualHarvestNum = actualHarvestNum;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

}
