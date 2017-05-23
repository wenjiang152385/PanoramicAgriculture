package com.oraro.panoramicagriculture.data.entity;

import java.util.Date;

public class PurchaseNeeds
 {
	private long id;
	private long vfcropsId;
	private Double price;
	private long needsNum;
	private Date date;
	private VFCrops vfcrops;
    private long purchasingPointId;
    private long actualNum;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVfcropsId() {
		return vfcropsId;
	}
	public void setVfcropsId(long vfcropsId) {
		this.vfcropsId = vfcropsId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public long getNeedsNum() {
		return needsNum;
	}
	public void setNeedsNum(long needsNum) {
		this.needsNum = needsNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public VFCrops getVfcrops() {
		return vfcrops;
	}
	public void setVfcrops(VFCrops vfcrops) {
		this.vfcrops = vfcrops;
	}
	public long getPurchasingPointId() {
		return purchasingPointId;
	}
	public void setPurchasingPointId(long purchasingPointId) {
		this.purchasingPointId = purchasingPointId;
	}
	public long getActualNum() {
		return actualNum;
	}
	public void setActualNum(long actualNum) {
		this.actualNum = actualNum;
	}
	
}
