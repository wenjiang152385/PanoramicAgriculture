package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/21 0021.
 *
 * @author
 */
@Entity
public class CropsResults {
    @Id(autoincrement = true)
    private Long id;
    private Double price;
    private Double allkg;
    private Long farmId;
    private Long pretime;
    private Long cropid;
    private int time; //0按天,1按月,2按季
    private String cropname;
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public Long getCropid() {
        return this.cropid;
    }
    public void setCropid(Long cropid) {
        this.cropid = cropid;
    }
    public Long getPretime() {
        return this.pretime;
    }
    public void setPretime(Long pretime) {
        this.pretime = pretime;
    }
    public Long getFarmId() {
        return this.farmId;
    }
    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }
    public Double getAllkg() {
        return this.allkg;
    }
    public void setAllkg(Double allkg) {
        this.allkg = allkg;
    }
    public Double getPrice() {
        return this.price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCropname() {
        return this.cropname;
    }
    public void setCropname(String cropname) {
        this.cropname = cropname;
    }
    @Generated(hash = 1120837329)
    public CropsResults(Long id, Double price, Double allkg, Long farmId,
            Long pretime, Long cropid, int time, String cropname) {
        this.id = id;
        this.price = price;
        this.allkg = allkg;
        this.farmId = farmId;
        this.pretime = pretime;
        this.cropid = cropid;
        this.time = time;
        this.cropname = cropname;
    }
    @Generated(hash = 1174165941)
    public CropsResults() {
    }
   

}
