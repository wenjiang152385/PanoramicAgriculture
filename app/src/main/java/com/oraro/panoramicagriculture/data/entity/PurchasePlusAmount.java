package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/2/10.
 */
@Entity
public class PurchasePlusAmount {
    @Id(autoincrement = true)
    private Long id;
    //采购编号
    private String poNO;
    //原因
    private String content;
    //比率
    private double percnet;
    //金额
    private double amonut;
    public double getAmonut() {
        return this.amonut;
    }
    public void setAmonut(double amonut) {
        this.amonut = amonut;
    }
    public double getPercnet() {
        return this.percnet;
    }
    public void setPercnet(double percnet) {
        this.percnet = percnet;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPoNO() {
        return this.poNO;
    }
    public void setPoNO(String poNO) {
        this.poNO = poNO;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2090684583)
    public PurchasePlusAmount(Long id, String poNO, String content, double percnet,
            double amonut) {
        this.id = id;
        this.poNO = poNO;
        this.content = content;
        this.percnet = percnet;
        this.amonut = amonut;
    }
    @Generated(hash = 1123550722)
    public PurchasePlusAmount() {
    }
}