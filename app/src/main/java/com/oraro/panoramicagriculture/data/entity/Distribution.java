package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Distribution {
	@Id(autoincrement = true)
	private long id;
	private int state;// 配送单的状态
	private Date startDate;// 配送开始时间
	private Date endDate;// 配送结束时间
	private long vfcropsId;
	private long dcId;// 配送点id
	private long num;
	private Date date;
	private long purchaseneedsId;
	@Transient
	private VFCrops vfcrops;

	public VFCrops getVfCrops() {
		return vfcrops;
	}

	public void setVfCrops(VFCrops vfCrops) {
		this.vfcrops = vfCrops;
	}

	public PurchasingPoint getPurchasingPoint() {
		return purchase;
	}

	public void setPurchasingPoint(PurchasingPoint purchasingPoint) {
		this.purchase = purchasingPoint;
	}

	@Transient
	private PurchasingPoint purchase;
	public long getPurchaseneedsId() {
		return this.purchaseneedsId;
	}
	public void setPurchaseneedsId(long purchaseneedsId) {
		this.purchaseneedsId = purchaseneedsId;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getNum() {
		return this.num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public long getDcId() {
		return this.dcId;
	}
	public void setDcId(long dcId) {
		this.dcId = dcId;
	}
	public long getVfcropsId() {
		return this.vfcropsId;
	}
	public void setVfcropsId(long vfcropsId) {
		this.vfcropsId = vfcropsId;
	}
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getState() {
		return this.state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Generated(hash = 1194075304)
	public Distribution(long id, int state, Date startDate, Date endDate,
			long vfcropsId, long dcId, long num, Date date, long purchaseneedsId) {
		this.id = id;
		this.state = state;
		this.startDate = startDate;
		this.endDate = endDate;
		this.vfcropsId = vfcropsId;
		this.dcId = dcId;
		this.num = num;
		this.date = date;
		this.purchaseneedsId = purchaseneedsId;
	}
	@Generated(hash = 398043436)
	public Distribution() {
	}
	
}
