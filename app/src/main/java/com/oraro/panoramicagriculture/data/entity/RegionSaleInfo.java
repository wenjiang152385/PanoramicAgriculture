package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/28.
 */
@Entity
public class RegionSaleInfo {
    private long saleInfoId;
    private Double saleNumTotal;//总量

    private String vfCropsName;//品种
    private String district;//地区
    private String city;//城市
    private long vfcropsId;//品种id

    public VFCrops getVfcrops() {
        return vfcrops;
    }

    public void setVfcrops(VFCrops vfcrops) {
        this.vfcrops = vfcrops;
    }

    //    @ToOne(joinProperty = "vfcropsId")
    @Transient
    private VFCrops vfcrops;



    public long getVfcropsId() {
        return this.vfcropsId;
    }
    public void setVfcropsId(long vfcropsId) {
        this.vfcropsId = vfcropsId;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return this.district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getVfCropsName() {
        return this.vfCropsName;
    }
    public void setVfCropsName(String vfCropsName) {
        this.vfCropsName = vfCropsName;
    }
    public Double getSaleNumTotal() {
        return this.saleNumTotal;
    }
    public void setSaleNumTotal(Double saleNumTotal) {
        this.saleNumTotal = saleNumTotal;
    }
    public long getSaleInfoId() {
        return this.saleInfoId;
    }
    public void setSaleInfoId(long saleInfoId) {
        this.saleInfoId = saleInfoId;
    }
    @Generated(hash = 1285693815)
    public RegionSaleInfo(long saleInfoId, Double saleNumTotal, String vfCropsName,
            String district, String city, long vfcropsId) {
        this.saleInfoId = saleInfoId;
        this.saleNumTotal = saleNumTotal;
        this.vfCropsName = vfCropsName;
        this.district = district;
        this.city = city;
        this.vfcropsId = vfcropsId;
    }
    @Generated(hash = 880946191)
    public RegionSaleInfo() {
    }


}
