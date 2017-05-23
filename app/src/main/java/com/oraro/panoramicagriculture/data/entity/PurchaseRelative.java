package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/2/10.
 */
@Entity
public class PurchaseRelative {
    @Id(autoincrement = true)
    private Long id;
    //农民编号
    private String farmerNO;
    //描述
    private String desc;
    //建立日期
    private Date date;
    //审核，择优录取否
    private int check;
    public int getCheck() {
        return this.check;
    }
    public void setCheck(int check) {
        this.check = check;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getFarmerNO() {
        return this.farmerNO;
    }
    public void setFarmerNO(String farmerNO) {
        this.farmerNO = farmerNO;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2029365224)
    public PurchaseRelative(Long id, String farmerNO, String desc, Date date,
            int check) {
        this.id = id;
        this.farmerNO = farmerNO;
        this.desc = desc;
        this.date = date;
        this.check = check;
    }
    @Generated(hash = 1585600172)
    public PurchaseRelative() {
    }
}