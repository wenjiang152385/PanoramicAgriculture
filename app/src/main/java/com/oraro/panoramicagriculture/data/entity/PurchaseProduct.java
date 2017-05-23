package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/2/10.
 */
@Entity
public class PurchaseProduct {
    @Id(autoincrement = true)
    private Long id;
    //采购编号
    private String poNO;
    //产品编号[农作物id]
    private Long productNO;
    //采购报价
    private Double offer;
    //采购数量
    private Double number;
    //单位
    private String unit;
    //采购截止日 或预定截至日期
    private Date deadLine;
    //实际采购价
    private Double price;
    //损耗比率
    private Double lossRate;
    //已认购数量[所有完成的认购数量总和等于采购数量]
    private Double subscribeNumber;
    public Double getSubscribeNumber() {
        return this.subscribeNumber;
    }
    public void setSubscribeNumber(Double subscribeNumber) {
        this.subscribeNumber = subscribeNumber;
    }
    public Double getLossRate() {
        return this.lossRate;
    }
    public void setLossRate(Double lossRate) {
        this.lossRate = lossRate;
    }
    public Double getPrice() {
        return this.price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Date getDeadLine() {
        return this.deadLine;
    }
    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public Double getNumber() {
        return this.number;
    }
    public void setNumber(Double number) {
        this.number = number;
    }
    public Double getOffer() {
        return this.offer;
    }
    public void setOffer(Double offer) {
        this.offer = offer;
    }
    public Long getProductNO() {
        return this.productNO;
    }
    public void setProductNO(Long productNO) {
        this.productNO = productNO;
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
    @Generated(hash = 1480968332)
    public PurchaseProduct(Long id, String poNO, Long productNO, Double offer,
            Double number, String unit, Date deadLine, Double price,
            Double lossRate, Double subscribeNumber) {
        this.id = id;
        this.poNO = poNO;
        this.productNO = productNO;
        this.offer = offer;
        this.number = number;
        this.unit = unit;
        this.deadLine = deadLine;
        this.price = price;
        this.lossRate = lossRate;
        this.subscribeNumber = subscribeNumber;
    }
    @Generated(hash = 992423362)
    public PurchaseProduct() {
    }
}
