package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yjd on 2017/2/10.
 */
@Entity
public class PurchaseOrder {
    @Id(autoincrement = true)
    private Long id;
    //采购编号
    private String poNO;
    //采购日期
    private Date orderTime;
    //采购员
    private String buyer;
    //采购金额
    private Double totalMoney;
    //采购地址
    private String address;
    //来源编号[1.将来源编号用农场id替换]\农场id
    private Long contractNO;
    //发货日期
    private Date shippingTime;
    //采购截止日 或预定截至日期
    private Date deadLine;
    //采购单类型 1-立即采购,2-预定采购，3-定点采购
    private int type;
    //税别1-含税，2-未税，3-零税，4-免税
    private int taxType;
    //税率
    private Double taxRate;
    //是否开具发票
    private Boolean needInvoice;
    //结算货币种类
    private String currencyKind;
    public String getCurrencyKind() {
        return this.currencyKind;
    }
    public void setCurrencyKind(String currencyKind) {
        this.currencyKind = currencyKind;
    }
    public Boolean getNeedInvoice() {
        return this.needInvoice;
    }
    public void setNeedInvoice(Boolean needInvoice) {
        this.needInvoice = needInvoice;
    }
    public Double getTaxRate() {
        return this.taxRate;
    }
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }
    public int getTaxType() {
        return this.taxType;
    }
    public void setTaxType(int taxType) {
        this.taxType = taxType;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Date getDeadLine() {
        return this.deadLine;
    }
    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
    public Date getShippingTime() {
        return this.shippingTime;
    }
    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }
    public Long getContractNO() {
        return this.contractNO;
    }
    public void setContractNO(Long contractNO) {
        this.contractNO = contractNO;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Double getTotalMoney() {
        return this.totalMoney;
    }
    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
    public String getBuyer() {
        return this.buyer;
    }
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
    public Date getOrderTime() {
        return this.orderTime;
    }
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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
    @Generated(hash = 977343385)
    public PurchaseOrder(Long id, String poNO, Date orderTime, String buyer,
            Double totalMoney, String address, Long contractNO, Date shippingTime,
            Date deadLine, int type, int taxType, Double taxRate,
            Boolean needInvoice, String currencyKind) {
        this.id = id;
        this.poNO = poNO;
        this.orderTime = orderTime;
        this.buyer = buyer;
        this.totalMoney = totalMoney;
        this.address = address;
        this.contractNO = contractNO;
        this.shippingTime = shippingTime;
        this.deadLine = deadLine;
        this.type = type;
        this.taxType = taxType;
        this.taxRate = taxRate;
        this.needInvoice = needInvoice;
        this.currencyKind = currencyKind;
    }
    @Generated(hash = 978149206)
    public PurchaseOrder() {
    }


}
