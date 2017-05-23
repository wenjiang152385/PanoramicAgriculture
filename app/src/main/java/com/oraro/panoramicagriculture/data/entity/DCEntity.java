package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/5/3.
 */
@Entity
public class DCEntity {
    @Id
    private Long id;//配送中心id，自增
    private String dcName;//配送中心名
    private Double latitude;//配送中心经度
    private Double longitude;//配送中心纬度
    private String address;//地址
    private String country;//国家
    private String province;// 省
    private String city;//城市
    private String district;//区县
    private String street;//街道
    private String streetNum;//门牌号
    private Double totalArea;//配送中心大小
    private String dcInfo;//配送中心简介
    private String slide1;//配送中心图片1
    private String slide2;
    private String slide3;
    private String slide4;

    private Double coverageArea;//覆盖范围
    private String phoneNum;
    private Long userId;//
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public Double getCoverageArea() {
        return this.coverageArea;
    }
    public void setCoverageArea(Double coverageArea) {
        this.coverageArea = coverageArea;
    }
    public String getSlide4() {
        return this.slide4;
    }
    public void setSlide4(String slide4) {
        this.slide4 = slide4;
    }
    public String getSlide3() {
        return this.slide3;
    }
    public void setSlide3(String slide3) {
        this.slide3 = slide3;
    }
    public String getSlide2() {
        return this.slide2;
    }
    public void setSlide2(String slide2) {
        this.slide2 = slide2;
    }
    public String getSlide1() {
        return this.slide1;
    }
    public void setSlide1(String slide1) {
        this.slide1 = slide1;
    }
    public String getDcInfo() {
        return this.dcInfo;
    }
    public void setDcInfo(String dcInfo) {
        this.dcInfo = dcInfo;
    }
    public Double getTotalArea() {
        return this.totalArea;
    }
    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }
    public String getStreetNum() {
        return this.streetNum;
    }
    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }
    public String getStreet() {
        return this.street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getDistrict() {
        return this.district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public String getDcName() {
        return this.dcName;
    }
    public void setDcName(String dcName) {
        this.dcName = dcName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 566634466)
    public DCEntity(Long id, String dcName, Double latitude, Double longitude,
            String address, String country, String province, String city,
            String district, String street, String streetNum, Double totalArea,
            String dcInfo, String slide1, String slide2, String slide3,
            String slide4, Double coverageArea, String phoneNum, Long userId) {
        this.id = id;
        this.dcName = dcName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.streetNum = streetNum;
        this.totalArea = totalArea;
        this.dcInfo = dcInfo;
        this.slide1 = slide1;
        this.slide2 = slide2;
        this.slide3 = slide3;
        this.slide4 = slide4;
        this.coverageArea = coverageArea;
        this.phoneNum = phoneNum;
        this.userId = userId;
    }
    @Generated(hash = 2113373801)
    public DCEntity() {
    }
}
