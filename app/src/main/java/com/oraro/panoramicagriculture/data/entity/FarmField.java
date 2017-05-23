package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.DaoException;

import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.FarmFieldDao;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Administrator on 2017/3/20.
 */
@Entity
public class FarmField {
    private Long fieldId;
    private String fieldName;// 地块名称
    private int state;// 土地状态：0 初始化 1 播种期 2 生长期 3 收获期
    private Double fieldArea;// 地块大小
    private Double matureDegree;// 成熟度
    private Double latitude;// 经度
    private Double longitude;// 纬度
    private String currentSowTime;// 当前农作物播种时间
    private String currentMatureTime;// 当前农作物成熟时间
    private String nextSowTime;// 下次播种时间
    private Long farmId;// 所属农场
    // 种植数量
    private long plantNum;
    // 预计收获数量
    private long expectHarvestNum;
    // 实际收获数量
    private long actualHarvestNum;
    // 总天数
    private int totalday;
    // 种植天数
    private int plantday;
    private long plantHistoryId;
    private long harvestHistoryId;
    // 当前农作物
    private long currentVFcropsId;
    // 预计下次播种的农作物
    private long nextVFcropsId;

    @Transient
    private int fieldPosition;

    @Transient
    private VFCrops currentVFcrops;
    @Transient
    private VFCrops nextVFcrops;

    public VFCrops getNextVFcrops() {
        return nextVFcrops;
    }

    public void setNextVFcrops(VFCrops nextVFcrops) {
        this.nextVFcrops = nextVFcrops;
    }

    public int getFieldPosition() {
        return fieldPosition;
    }

    public void setFieldPosition(int fieldPosition) {
        this.fieldPosition = fieldPosition;
    }
    public  void  setVfCrops(VFCrops vfCrops){
        this.currentVFcrops=vfCrops;
    }
    public  VFCrops getVfCrops(){
        return currentVFcrops;
    }
    @Transient
    private List<FieldBorder> fieldBorderList;
    public List<FieldBorder> getFieldBorderList() {
        return fieldBorderList;
    }

    public void setFieldBorderList(List<FieldBorder> fieldBorderList) {
        this.fieldBorderList = fieldBorderList;
    }

    public long getNextVFcropsId() {
        return this.nextVFcropsId;
    }

    public void setNextVFcropsId(long nextVFcropsId) {
        this.nextVFcropsId = nextVFcropsId;
    }

    public long getCurrentVFcropsId() {
        return this.currentVFcropsId;
    }

    public void setCurrentVFcropsId(long currentVFcropsId) {
        this.currentVFcropsId = currentVFcropsId;
    }

    public long getHarvestHistoryId() {
        return this.harvestHistoryId;
    }

    public void setHarvestHistoryId(long harvestHistoryId) {
        this.harvestHistoryId = harvestHistoryId;
    }

    public long getPlantHistoryId() {
        return this.plantHistoryId;
    }

    public void setPlantHistoryId(long plantHistoryId) {
        this.plantHistoryId = plantHistoryId;
    }

    public int getPlantday() {
        return this.plantday;
    }

    public void setPlantday(int plantday) {
        this.plantday = plantday;
    }

    public int getTotalday() {
        return this.totalday;
    }

    public void setTotalday(int totalday) {
        this.totalday = totalday;
    }

    public long getActualHarvestNum() {
        return this.actualHarvestNum;
    }

    public void setActualHarvestNum(long actualHarvestNum) {
        this.actualHarvestNum = actualHarvestNum;
    }

    public long getExpectHarvestNum() {
        return this.expectHarvestNum;
    }

    public void setExpectHarvestNum(long expectHarvestNum) {
        this.expectHarvestNum = expectHarvestNum;
    }

    public long getPlantNum() {
        return this.plantNum;
    }

    public void setPlantNum(long plantNum) {
        this.plantNum = plantNum;
    }

    public Long getFarmId() {
        return this.farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public String getNextSowTime() {
        return this.nextSowTime;
    }

    public void setNextSowTime(String nextSowTime) {
        this.nextSowTime = nextSowTime;
    }

    public String getCurrentMatureTime() {
        return this.currentMatureTime;
    }

    public void setCurrentMatureTime(String currentMatureTime) {
        this.currentMatureTime = currentMatureTime;
    }

    public String getCurrentSowTime() {
        return this.currentSowTime;
    }

    public void setCurrentSowTime(String currentSowTime) {
        this.currentSowTime = currentSowTime;
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

    public Double getMatureDegree() {
        return this.matureDegree;
    }

    public void setMatureDegree(Double matureDegree) {
        this.matureDegree = matureDegree;
    }

    public Double getFieldArea() {
        return this.fieldArea;
    }

    public void setFieldArea(Double fieldArea) {
        this.fieldArea = fieldArea;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }



    @Generated(hash = 1264654675)
    public FarmField(Long fieldId, String fieldName, int state, Double fieldArea,
            Double matureDegree, Double latitude, Double longitude,
            String currentSowTime, String currentMatureTime, String nextSowTime,
            Long farmId, long plantNum, long expectHarvestNum,
            long actualHarvestNum, int totalday, int plantday, long plantHistoryId,
            long harvestHistoryId, long currentVFcropsId, long nextVFcropsId) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.state = state;
        this.fieldArea = fieldArea;
        this.matureDegree = matureDegree;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentSowTime = currentSowTime;
        this.currentMatureTime = currentMatureTime;
        this.nextSowTime = nextSowTime;
        this.farmId = farmId;
        this.plantNum = plantNum;
        this.expectHarvestNum = expectHarvestNum;
        this.actualHarvestNum = actualHarvestNum;
        this.totalday = totalday;
        this.plantday = plantday;
        this.plantHistoryId = plantHistoryId;
        this.harvestHistoryId = harvestHistoryId;
        this.currentVFcropsId = currentVFcropsId;
        this.nextVFcropsId = nextVFcropsId;
    }

    @Generated(hash = 1996872805)
    public FarmField() {
    }

    @Override
    public String toString() {
        return "FarmField{" +
                "fieldId=" + fieldId +
                ", fieldName='" + fieldName + '\'' +
                ", state=" + state +
                ", fieldArea=" + fieldArea +
                ", matureDegree=" + matureDegree +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", currentSowTime='" + currentSowTime + '\'' +
                ", currentMatureTime='" + currentMatureTime + '\'' +
                ", nextSowTime='" + nextSowTime + '\'' +
                ", farmId=" + farmId +
                ", plantNum=" + plantNum +
                ", expectHarvestNum=" + expectHarvestNum +
                ", actualHarvestNum=" + actualHarvestNum +
                ", totalday=" + totalday +
                ", plantday=" + plantday +
                ", plantHistoryId=" + plantHistoryId +
                ", harvestHistoryId=" + harvestHistoryId +
                ", currentVFcropsId=" + currentVFcropsId +
                ", nextVFcropsId=" + nextVFcropsId +
                ", vfCrops=" + currentVFcrops +
                ", fieldBorderList=" + fieldBorderList +
                '}';
    }
}
