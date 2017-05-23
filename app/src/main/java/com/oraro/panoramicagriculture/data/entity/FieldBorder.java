package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/20.
 */
@Entity
public class FieldBorder {

    private Long borderId;
    private int borderIndex;//边界点
    private Double latitude;//经度
    private Double longitude;//纬度
    private Long fieldId;//哪个地块
    public Long getFieldId() {
        return this.fieldId;
    }
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public int getBorderIndex() {
        return this.borderIndex;
    }
    public void setBorderIndex(int borderIndex) {
        this.borderIndex = borderIndex;
    }
    public Long getBorderId() {
        return this.borderId;
    }
    public void setBorderId(Long borderId) {
        this.borderId = borderId;
    }
    @Generated(hash = 1144895694)
    public FieldBorder(Long borderId, int borderIndex, Double latitude,
            Double longitude, Long fieldId) {
        this.borderId = borderId;
        this.borderIndex = borderIndex;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fieldId = fieldId;
    }
    @Generated(hash = 41584345)
    public FieldBorder() {
    }

    @Override
    public String toString() {
        return "{" +
                "borderIndex=" + borderIndex +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", fieldId=" + fieldId +
                '}';
    }
}
