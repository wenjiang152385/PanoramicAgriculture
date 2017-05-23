package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Administrator on 2017/5/3 0003.
 *
 * @author
 */
@Entity
public class DCPurchaseData {
    @Id(autoincrement = true)
    private long id;
    private long vfcropsId;// 农作物id
    private long expectneedsNum;// 预计采购量
    private String date;// 预定日期
    private long dcid;// 配送中心 id
    @Transient
    private VFCrops vfcrops;
    public VFCrops getVFcrops() {
        return vfcrops;
    }
    @Transient
    private  FarmEntity farm;

    public FarmEntity getFarmEntity() {
        return farm;
    }

    public void setFarmEntity(FarmEntity farmEntity) {
        this.farm = farmEntity;
    }

    public void setVFcrops(VFCrops currentVFcrops) {
        this.vfcrops = currentVFcrops;
    }
    public long getDcid() {
        return this.dcid;
    }
    public void setDcid(long dcid) {
        this.dcid = dcid;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public long getExpectneedsNum() {
        return this.expectneedsNum;
    }
    public void setExpectneedsNum(long expectneedsNum) {
        this.expectneedsNum = expectneedsNum;
    }
    public long getVfcropsId() {
        return this.vfcropsId;
    }
    public void setVfcropsId(long vfcropsId) {
        this.vfcropsId = vfcropsId;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 56733728)
    public DCPurchaseData(long id, long vfcropsId, long expectneedsNum,
            String date, long dcid) {
        this.id = id;
        this.vfcropsId = vfcropsId;
        this.expectneedsNum = expectneedsNum;
        this.date = date;
        this.dcid = dcid;
    }
    @Generated(hash = 1128289277)
    public DCPurchaseData() {
    }

    @Override
    public String toString() {
        return "DCPurchaseData{" +
                "id=" + id +
                ", vfcropsId=" + vfcropsId +
                ", expectneedsNum=" + expectneedsNum +
                ", date='" + date + '\'' +
                ", dcid=" + dcid +
                ", VFcrops=" + vfcrops +
                ", farmEntity=" + farm +
                '}';
    }
}
