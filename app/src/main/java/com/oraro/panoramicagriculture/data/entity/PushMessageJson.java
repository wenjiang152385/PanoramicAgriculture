package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PushMessageJson {
    //配送中心向种植基地采购
    @Transient
    public static final int TYPE_DC_ORDER_FARM = 0;
    //销售网点向配送中心订单
    @Transient
    public static final int TYPE_SALE_ORDER_DC = 1;
    //配送中心向销售网点配送
    @Transient
    public static final int TYPE_DC_SEND_SALE = 2;

    //消息发送失败
    @Transient
    public static final int STATE_SEND_FAIL = -1;
    //消息未发送
    @Transient
    public static final int STATE_NO_SEND = 0;
    //消息发送成功
    @Transient
    public static final int STATE_SEND_SUCCESS = 1;
    @Id()
    @NotNull
    private long messageId;//消息id

    @Transient
    private UserEntity sendUser;
    @Transient
    private UserEntity receiveUser;
    @Transient
    private FarmEntity farm;
    @Transient
    private PurchasingPoint purchase;
    @Transient
    private DCEntity dcEntity;
    @Transient
    private DCPurchaseData dcPurchaseData;//采购单
    @Transient
    private PurchaseNeeds purchaseNeeds;//订单
    @Transient
    private Distribution distribution;//配送单

    private String messagetitle; //消息标题
    private String messagecontent;//消息内容
    /**
     * 0表示采购消息
     * 1表示订货消息
     * 2表示配送消息
     */
    private int type;
    /**
     * -1表示发送失败
     * 0表示未发送状态
     * 1表示发送成功
     */
    private int state = 0;
    private Date sendTime;//消息发送时间
    public Date getSendTime() {
        return this.sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getMessagecontent() {
        return this.messagecontent;
    }
    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }
    public String getMessagetitle() {
        return this.messagetitle;
    }
    public void setMessagetitle(String messagetitle) {
        this.messagetitle = messagetitle;
    }
    public long getMessageId() {
        return this.messageId;
    }
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public UserEntity getSendUser() {
        return sendUser;
    }

    public void setSendUser(UserEntity sendUser) {
        this.sendUser = sendUser;
    }

    public UserEntity getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(UserEntity receiveUser) {
        this.receiveUser = receiveUser;
    }

    public FarmEntity getFarm() {
        return farm;
    }

    public void setFarm(FarmEntity farm) {
        this.farm = farm;
    }

    public PurchasingPoint getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchasingPoint purchase) {
        this.purchase = purchase;
    }

    public DCEntity getDcEntity() {
        return dcEntity;
    }

    public void setDcEntity(DCEntity dcEntity) {
        this.dcEntity = dcEntity;
    }

    public DCPurchaseData getDcPurchaseData() {
        return dcPurchaseData;
    }

    public void setDcPurchaseData(DCPurchaseData dcPurchaseData) {
        this.dcPurchaseData = dcPurchaseData;
    }

    public PurchaseNeeds getPurchaseNeeds() {
        return purchaseNeeds;
    }

    public void setPurchaseNeeds(PurchaseNeeds purchaseNeeds) {
        this.purchaseNeeds = purchaseNeeds;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    @Generated(hash = 1696435818)
    public PushMessageJson(long messageId, String messagetitle,
            String messagecontent, int type, int state, Date sendTime) {
        this.messageId = messageId;
        this.messagetitle = messagetitle;
        this.messagecontent = messagecontent;
        this.type = type;
        this.state = state;
        this.sendTime = sendTime;
    }
    @Generated(hash = 1896724967)
    public PushMessageJson() {
    }

}
