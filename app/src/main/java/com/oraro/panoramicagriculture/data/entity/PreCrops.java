package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/13 0013.
 *
 * @author
 */
@Entity
public class PreCrops {
   @Id(autoincrement = true)
    private Long preid;
    private Long cropid;
    private Long userId;
    private  Long farmid;
    private int kg;
    private int area;
    private int price;
   public int getPrice() {
      return this.price;
   }
   public void setPrice(int price) {
      this.price = price;
   }
   public int getArea() {
      return this.area;
   }
   public void setArea(int area) {
      this.area = area;
   }
   public int getKg() {
      return this.kg;
   }
   public void setKg(int kg) {
      this.kg = kg;
   }
   public Long getFarmid() {
      return this.farmid;
   }
   public void setFarmid(Long farmid) {
      this.farmid = farmid;
   }
   public Long getUserId() {
      return this.userId;
   }
   public void setUserId(Long userId) {
      this.userId = userId;
   }
   public Long getCropid() {
      return this.cropid;
   }
   public void setCropid(Long cropid) {
      this.cropid = cropid;
   }
   public Long getPreid() {
      return this.preid;
   }
   public void setPreid(Long preid) {
      this.preid = preid;
   }
   @Generated(hash = 2005314811)
   public PreCrops(Long preid, Long cropid, Long userId, Long farmid, int kg,
         int area, int price) {
      this.preid = preid;
      this.cropid = cropid;
      this.userId = userId;
      this.farmid = farmid;
      this.kg = kg;
      this.area = area;
      this.price = price;
   }
   @Generated(hash = 1516776490)
   public PreCrops() {
   }

}
