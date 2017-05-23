package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.HarvestListDao;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Administrator on 2017/3/29 0029.
 *
 * @author
 */
@Entity
public class HarvestList {
    @Id(autoincrement = true)
    private long id;
    private long expectHarvestNum;
    private String vfCropsName;//品种
    private String district;//地区
    private String city;//城市
    private long vfcropsId;//品种id
    @Transient
    private VFCrops vfcrops;

    @Generated(hash = 661540131)
    public HarvestList(long id, long expectHarvestNum, String vfCropsName,
            String district, String city, long vfcropsId) {
        this.id = id;
        this.expectHarvestNum = expectHarvestNum;
        this.vfCropsName = vfCropsName;
        this.district = district;
        this.city = city;
        this.vfcropsId = vfcropsId;
    }

    @Generated(hash = 1317111831)
    public HarvestList() {
    }

    public VFCrops getVfcrops() {
        return vfcrops;
    }

    public void setVfcrops(VFCrops vfcrops) {
        this.vfcrops = vfcrops;
    }

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

    public long getExpectHarvestNum() {
        return this.expectHarvestNum;
    }

    public void setExpectHarvestNum(long expectHarvestNum) {
        this.expectHarvestNum = expectHarvestNum;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}
