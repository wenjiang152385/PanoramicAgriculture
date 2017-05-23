package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class PlantHistory {
	@Id(autoincrement = true)
	private Long id;
	private Long anAcreLandId;
	private long vfcropsId;
	//种植时间
	private Date plantTime;
	//收获时间
	private Date harvestTime;
	//预计收货量
	private long expectHarvestNum;
	//种植数量
	private long plantNum;
	//实际收获量
	private long actualHarvestNum;
	public long getActualHarvestNum() {
		return this.actualHarvestNum;
	}
	public void setActualHarvestNum(long actualHarvestNum) {
		this.actualHarvestNum = actualHarvestNum;
	}
	public long getPlantNum() {
		return this.plantNum;
	}
	public void setPlantNum(long plantNum) {
		this.plantNum = plantNum;
	}
	public long getExpectHarvestNum() {
		return this.expectHarvestNum;
	}
	public void setExpectHarvestNum(long expectHarvestNum) {
		this.expectHarvestNum = expectHarvestNum;
	}
	public Date getHarvestTime() {
		return this.harvestTime;
	}
	public void setHarvestTime(Date harvestTime) {
		this.harvestTime = harvestTime;
	}
	public Date getPlantTime() {
		return this.plantTime;
	}
	public void setPlantTime(Date plantTime) {
		this.plantTime = plantTime;
	}
	public long getVfcropsId() {
		return this.vfcropsId;
	}
	public void setVfcropsId(long vfcropsId) {
		this.vfcropsId = vfcropsId;
	}
	public Long getAnAcreLandId() {
		return this.anAcreLandId;
	}
	public void setAnAcreLandId(Long anAcreLandId) {
		this.anAcreLandId = anAcreLandId;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Generated(hash = 822829164)
	public PlantHistory(Long id, Long anAcreLandId, long vfcropsId, Date plantTime,
			Date harvestTime, long expectHarvestNum, long plantNum, long actualHarvestNum) {
		this.id = id;
		this.anAcreLandId = anAcreLandId;
		this.vfcropsId = vfcropsId;
		this.plantTime = plantTime;
		this.harvestTime = harvestTime;
		this.expectHarvestNum = expectHarvestNum;
		this.plantNum = plantNum;
		this.actualHarvestNum = actualHarvestNum;
	}
	@Generated(hash = 2057543970)
	public PlantHistory() {
	}

}