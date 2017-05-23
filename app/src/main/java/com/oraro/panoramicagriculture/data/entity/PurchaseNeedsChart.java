package com.oraro.panoramicagriculture.data.entity;

import java.util.Date;


public class PurchaseNeedsChart {
	private  int sumNeedsNum;
	private  int sumActualNum;
	private Date date; 
	private long vfcropsId;
	private long 	purchasingPointId;
	private VFCrops vfcrops;
	public VFCrops getVfFcrops() {
		return vfcrops;
	}
	public void setVfFcrops(VFCrops vfFcrops) {
		this.vfcrops = vfFcrops;
	}
	public long getPurchasingPointId() {
		return purchasingPointId;
	}
	public void setPurchasingPointId(long purchasingPointId) {
		this.purchasingPointId = purchasingPointId;
	}
	public int getSumNeedsNum() {
		return sumNeedsNum;
	}
	public void setSumNeedsNum(int sumNeedsNum) {
		this.sumNeedsNum = sumNeedsNum;
	}
	public int getSumActualNum() {
		return sumActualNum;
	}
	public void setSumActualNum(int sumActualNum) {
		this.sumActualNum = sumActualNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getVfcropsId() {
		return vfcropsId;
	}
	public void setVfcropsId(long vfcropsId) {
		this.vfcropsId = vfcropsId;
	}
	
}
