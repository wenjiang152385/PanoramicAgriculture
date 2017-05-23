package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.DaoException;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.dao.AnAcreLandDao;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class AnAcreLand {
	@Id(autoincrement = true)
	private long id;
	private long farmId;
	private long vfcropsId;
	//种植时间
	private Date plantTime;
	//收获时间
	private Date expectHarvestTime;
	//预计收获数量
	private long expectHarvestNum;
	//种植数量
	private long plantNum;
	//实际收获数量
	private long actualHarvestNum;
	private int state; //0 初始化 1 播种期 2 生长期 3 收获期
	//田块编号
	private int position;
	//总天数
	private int totalday;
	//种植天数
	private int plantday;
	private long plantHistoryId;
	private long harvestHistoryId;

	@ToOne(joinProperty = "vfcropsId")
	private VFCrops vfCrops;
	@Generated(hash = 400272940)
	private transient Long vfCrops__resolvedKey;
	/** Used for active entity operations. */
	@Generated(hash = 167829710)
	private transient AnAcreLandDao myDao;
	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;

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
	public int getPosition() {
		return this.position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getState() {
		return this.state;
	}
	public void setState(int state) {
		this.state = state;
	}
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
	public Date getExpectHarvestTime() {
		return this.expectHarvestTime;
	}
	public void setExpectHarvestTime(Date expectHarvestTime) {
		this.expectHarvestTime = expectHarvestTime;
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
	public long getFarmId() {
		return this.farmId;
	}
	public void setFarmId(long farmId) {
		this.farmId = farmId;
	}
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Generated(hash = 1189217178)
	public AnAcreLand(long id, long farmId, long vfcropsId, Date plantTime,
			Date expectHarvestTime, long expectHarvestNum, long plantNum,
			long actualHarvestNum, int state, int position, int totalday, int plantday,
			long plantHistoryId, long harvestHistoryId) {
		this.id = id;
		this.farmId = farmId;
		this.vfcropsId = vfcropsId;
		this.plantTime = plantTime;
		this.expectHarvestTime = expectHarvestTime;
		this.expectHarvestNum = expectHarvestNum;
		this.plantNum = plantNum;
		this.actualHarvestNum = actualHarvestNum;
		this.state = state;
		this.position = position;
		this.totalday = totalday;
		this.plantday = plantday;
		this.plantHistoryId = plantHistoryId;
		this.harvestHistoryId = harvestHistoryId;
	}
	@Generated(hash = 2116172421)
	public AnAcreLand() {
	}

	@Override
	public String toString() {
		return "AnAcreLand{" +
				"id=" + id +
				", farmId=" + farmId +
				", vfcropsId=" + vfcropsId +
				", plantTime=" + plantTime +
				", expectHarvestTime=" + expectHarvestTime +
				", expectHarvestNum=" + expectHarvestNum +
				", plantNum=" + plantNum +
				", actualHarvestNum=" + actualHarvestNum +
				", state=" + state +
				", position=" + position +
				", totalday=" + totalday +
				", plantday=" + plantday +
				", plantHistoryId=" + plantHistoryId +
				", harvestHistoryId=" + harvestHistoryId +
				'}';
	}
	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 1942392019)
	public void refresh() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.refresh(this);
	}
	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 713229351)
	public void update() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.update(this);
	}
	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 128553479)
	public void delete() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.delete(this);
	}
	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 2005044908)
	public void setVfCrops(@NotNull VFCrops vfCrops) {
		if (vfCrops == null) {
			throw new DaoException(
					"To-one property 'vfcropsId' has not-null constraint; cannot set to-one to null");
		}
		synchronized (this) {
			this.vfCrops = vfCrops;
			vfcropsId = vfCrops.getVfid();
			vfCrops__resolvedKey = vfcropsId;
		}
	}
	/** To-one relationship, resolved on first access. */
	@Generated(hash = 368260777)
	public VFCrops getVfCrops() {
		long __key = this.vfcropsId;
		if (vfCrops__resolvedKey == null || !vfCrops__resolvedKey.equals(__key)) {
			final DaoSession daoSession = this.daoSession;
			if (daoSession == null) {
				throw new DaoException("Entity is detached from DAO context");
			}
			VFCropsDao targetDao = daoSession.getVFCropsDao();
			VFCrops vfCropsNew = targetDao.load(__key);
			synchronized (this) {
				vfCrops = vfCropsNew;
				vfCrops__resolvedKey = __key;
			}
		}
		return vfCrops;
	}
	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 1038070779)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getAnAcreLandDao() : null;
	}
}
