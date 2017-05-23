package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by admin on 2017/3/29.
 */
public class PointSale {
    private int id;
    private double saleNum;
    private Date date;
    private String province;
    private String district;
    private String city;
    private int vfcropsId;
    private int purchasingPointId;
    private VFCrops vfcrops;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(double saleNum) {
        this.saleNum = saleNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getVfcropsId() {
        return vfcropsId;
    }

    public void setVfcropsId(int vfcropsId) {
        this.vfcropsId = vfcropsId;
    }

    public int getPurchasingPointId() {
        return purchasingPointId;
    }

    public void setPurchasingPointId(int purchasingPointId) {
        this.purchasingPointId = purchasingPointId;
    }

    public VFCrops getVfcrops() {
        return vfcrops;
    }

    public void setVfcrops(VFCrops vfcrops) {
        this.vfcrops = vfcrops;
    }
}
