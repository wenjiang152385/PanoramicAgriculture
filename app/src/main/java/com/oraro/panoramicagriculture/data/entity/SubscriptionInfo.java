package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/2/10.
 */
@Entity
public class SubscriptionInfo {
    @Id(autoincrement = true)
    private Long id;
    //采购单号
    private String poNO;
    //产品编号 农作物id
    private Long productNO;
    //农民编号
    private String farmerNO;
    //认购数量
    private Double number;
    //单位
    private String unit;
    //实际成交价
    private Double price;
    //损耗
    private Double lossRate;
    //认证日期
    private Date date;
    //合同内容
    private String contract;
    //审核，择优录取否
    private int check;
    public int getCheck() {
        return this.check;
    }
    public void setCheck(int check) {
        this.check = check;
    }
    public String getContract() {
        return this.contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    public String getFarmerNO() {
        return this.farmerNO;
    }
    public void setFarmerNO(String farmerNO) {
        this.farmerNO = farmerNO;
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
    @Generated(hash = 1859128987)
    public SubscriptionInfo(Long id, String poNO, Long productNO, String farmerNO,
            Double number, String unit, Double price, Double lossRate, Date date,
            String contract, int check) {
        this.id = id;
        this.poNO = poNO;
        this.productNO = productNO;
        this.farmerNO = farmerNO;
        this.number = number;
        this.unit = unit;
        this.price = price;
        this.lossRate = lossRate;
        this.date = date;
        this.contract = contract;
        this.check = check;
    }
    @Generated(hash = 202014363)
    public SubscriptionInfo() {
    }
}