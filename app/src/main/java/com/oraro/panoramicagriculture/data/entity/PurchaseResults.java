package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.PurchaseResultsDao;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;

import java.util.Date;

/**
 * Created by admin on 2017/2/24.
 */
@Entity
public class PurchaseResults {
    @Id(autoincrement = true)
    private Long id;
    private Long vfcropsId;
    private Long purchasingPointId;
    private Date date;
    private Double needsNum;
    private Double actualNum;
    @Transient
    private VFCrops vfcrops;

    public VFCrops getVfcrops() {
        return vfcrops;
    }

    public void setVfcrops(VFCrops vfcrops) {
        this.vfcrops = vfcrops;
    }

    @Transient
    private  DCEntity dc;

    @Generated(hash = 680655639)
    public PurchaseResults(Long id, Long vfcropsId, Long purchasingPointId,
            Date date, Double needsNum, Double actualNum) {
        this.id = id;
        this.vfcropsId = vfcropsId;
        this.purchasingPointId = purchasingPointId;
        this.date = date;
        this.needsNum = needsNum;
        this.actualNum = actualNum;
    }

    @Generated(hash = 2014917776)
    public PurchaseResults() {
    }

    public DCEntity getDc() {
        return dc;
    }

    public void setDc(DCEntity dc) {
        this.dc = dc;
    }

    public Double getActualNum() {
        return this.actualNum;
    }

    public void setActualNum(Double actualNum) {
        this.actualNum = actualNum;
    }

    public Double getNeedsNum() {
        return this.needsNum;
    }

    public void setNeedsNum(Double needsNum) {
        this.needsNum = needsNum;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPurchasingPointId() {
        return this.purchasingPointId;
    }

    public void setPurchasingPointId(Long purchasingPointId) {
        this.purchasingPointId = purchasingPointId;
    }

    public Long getVfcropsId() {
        return this.vfcropsId;
    }

    public void setVfcropsId(Long vfcropsId) {
        this.vfcropsId = vfcropsId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PurchaseResults{" +
                "id=" + id +
                ", vfcropsId=" + vfcropsId +
                ", purchasingPointId=" + purchasingPointId +
                ", date=" + date +
                ", needsNum=" + needsNum +
                ", actualNum=" + actualNum +
                ", vfcrops=" + vfcrops +
                ", dc=" + dc +
                '}';
    }
}
