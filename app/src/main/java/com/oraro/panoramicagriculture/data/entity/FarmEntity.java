package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/7.
 */
@Entity
public class FarmEntity {
    @Id(autoincrement = true)
    private Long farmId;//农场id，自增
    private String farmName;//农场名
    private Double latitude;//农场经度
    private Double longitude;//农场纬度
    private String address;//地址
    private String country;//国家
    private String province;// 省
    private String city;//城市
    private String district;//区县
    private String street;//街道
    private String streetNum;//门牌号
    private Double totalArea;//农场大小
    private String farmInfo;//农场简介
    private String slide1;//农场图片1
    private String slide2;
    private String slide3;
    private String slide4;

    private String phoneNum;
    private Long userId;//

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getFarmName() {
        return this.farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public Long getFarmId() {
        return this.farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public String getFarmInfo() {
        return this.farmInfo;
    }

    public void setFarmInfo(String farmInfo) {
        this.farmInfo = farmInfo;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Generated(hash = 828127170)
    public FarmEntity(Long farmId, String farmName, Double latitude,
            Double longitude, String address, String country, String province,
            String city, String district, String street, String streetNum,
            Double totalArea, String farmInfo, String slide1, String slide2,
            String slide3, String slide4, String phoneNum, Long userId) {
        this.farmId = farmId;
        this.farmName = farmName;
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
        this.farmInfo = farmInfo;
        this.slide1 = slide1;
        this.slide2 = slide2;
        this.slide3 = slide3;
        this.slide4 = slide4;
        this.phoneNum = phoneNum;
        this.userId = userId;
    }

    @Generated(hash = 145949961)
    public FarmEntity() {
    }


}
