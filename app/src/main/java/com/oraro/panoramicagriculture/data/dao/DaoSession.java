package com.oraro.panoramicagriculture.data.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.oraro.panoramicagriculture.data.entity.AnAcreLand;
import com.oraro.panoramicagriculture.data.entity.Crops;
import com.oraro.panoramicagriculture.data.entity.CropsResults;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.DCPurchaseData;
import com.oraro.panoramicagriculture.data.entity.Distribution;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.FieldBorder;
import com.oraro.panoramicagriculture.data.entity.HarvestList;
import com.oraro.panoramicagriculture.data.entity.NeedList;
import com.oraro.panoramicagriculture.data.entity.PlantHistory;
import com.oraro.panoramicagriculture.data.entity.PreCrops;
import com.oraro.panoramicagriculture.data.entity.PurchaseOrder;
import com.oraro.panoramicagriculture.data.entity.PurchasePlusAmount;
import com.oraro.panoramicagriculture.data.entity.PurchaseProduct;
import com.oraro.panoramicagriculture.data.entity.PurchaseRelative;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.data.entity.PushMessageJson;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.data.entity.SubscriptionInfo;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.data.entity.VFCrops;

import com.oraro.panoramicagriculture.data.dao.AnAcreLandDao;
import com.oraro.panoramicagriculture.data.dao.CropsDao;
import com.oraro.panoramicagriculture.data.dao.CropsResultsDao;
import com.oraro.panoramicagriculture.data.dao.DCEntityDao;
import com.oraro.panoramicagriculture.data.dao.DCPurchaseDataDao;
import com.oraro.panoramicagriculture.data.dao.DistributionDao;
import com.oraro.panoramicagriculture.data.dao.FarmEntityDao;
import com.oraro.panoramicagriculture.data.dao.FarmFieldDao;
import com.oraro.panoramicagriculture.data.dao.FieldBorderDao;
import com.oraro.panoramicagriculture.data.dao.HarvestListDao;
import com.oraro.panoramicagriculture.data.dao.NeedListDao;
import com.oraro.panoramicagriculture.data.dao.PlantHistoryDao;
import com.oraro.panoramicagriculture.data.dao.PreCropsDao;
import com.oraro.panoramicagriculture.data.dao.PurchaseOrderDao;
import com.oraro.panoramicagriculture.data.dao.PurchasePlusAmountDao;
import com.oraro.panoramicagriculture.data.dao.PurchaseProductDao;
import com.oraro.panoramicagriculture.data.dao.PurchaseRelativeDao;
import com.oraro.panoramicagriculture.data.dao.PurchaseResultsDao;
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.dao.PushMessageJsonDao;
import com.oraro.panoramicagriculture.data.dao.RegionSaleInfoDao;
import com.oraro.panoramicagriculture.data.dao.SubscriptionInfoDao;
import com.oraro.panoramicagriculture.data.dao.UserEntityDao;
import com.oraro.panoramicagriculture.data.dao.VFCropsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig anAcreLandDaoConfig;
    private final DaoConfig cropsDaoConfig;
    private final DaoConfig cropsResultsDaoConfig;
    private final DaoConfig dCEntityDaoConfig;
    private final DaoConfig dCPurchaseDataDaoConfig;
    private final DaoConfig distributionDaoConfig;
    private final DaoConfig farmEntityDaoConfig;
    private final DaoConfig farmFieldDaoConfig;
    private final DaoConfig fieldBorderDaoConfig;
    private final DaoConfig harvestListDaoConfig;
    private final DaoConfig needListDaoConfig;
    private final DaoConfig plantHistoryDaoConfig;
    private final DaoConfig preCropsDaoConfig;
    private final DaoConfig purchaseOrderDaoConfig;
    private final DaoConfig purchasePlusAmountDaoConfig;
    private final DaoConfig purchaseProductDaoConfig;
    private final DaoConfig purchaseRelativeDaoConfig;
    private final DaoConfig purchaseResultsDaoConfig;
    private final DaoConfig purchasingPointDaoConfig;
    private final DaoConfig pushMessageJsonDaoConfig;
    private final DaoConfig regionSaleInfoDaoConfig;
    private final DaoConfig subscriptionInfoDaoConfig;
    private final DaoConfig userEntityDaoConfig;
    private final DaoConfig vFCropsDaoConfig;

    private final AnAcreLandDao anAcreLandDao;
    private final CropsDao cropsDao;
    private final CropsResultsDao cropsResultsDao;
    private final DCEntityDao dCEntityDao;
    private final DCPurchaseDataDao dCPurchaseDataDao;
    private final DistributionDao distributionDao;
    private final FarmEntityDao farmEntityDao;
    private final FarmFieldDao farmFieldDao;
    private final FieldBorderDao fieldBorderDao;
    private final HarvestListDao harvestListDao;
    private final NeedListDao needListDao;
    private final PlantHistoryDao plantHistoryDao;
    private final PreCropsDao preCropsDao;
    private final PurchaseOrderDao purchaseOrderDao;
    private final PurchasePlusAmountDao purchasePlusAmountDao;
    private final PurchaseProductDao purchaseProductDao;
    private final PurchaseRelativeDao purchaseRelativeDao;
    private final PurchaseResultsDao purchaseResultsDao;
    private final PurchasingPointDao purchasingPointDao;
    private final PushMessageJsonDao pushMessageJsonDao;
    private final RegionSaleInfoDao regionSaleInfoDao;
    private final SubscriptionInfoDao subscriptionInfoDao;
    private final UserEntityDao userEntityDao;
    private final VFCropsDao vFCropsDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        anAcreLandDaoConfig = daoConfigMap.get(AnAcreLandDao.class).clone();
        anAcreLandDaoConfig.initIdentityScope(type);

        cropsDaoConfig = daoConfigMap.get(CropsDao.class).clone();
        cropsDaoConfig.initIdentityScope(type);

        cropsResultsDaoConfig = daoConfigMap.get(CropsResultsDao.class).clone();
        cropsResultsDaoConfig.initIdentityScope(type);

        dCEntityDaoConfig = daoConfigMap.get(DCEntityDao.class).clone();
        dCEntityDaoConfig.initIdentityScope(type);

        dCPurchaseDataDaoConfig = daoConfigMap.get(DCPurchaseDataDao.class).clone();
        dCPurchaseDataDaoConfig.initIdentityScope(type);

        distributionDaoConfig = daoConfigMap.get(DistributionDao.class).clone();
        distributionDaoConfig.initIdentityScope(type);

        farmEntityDaoConfig = daoConfigMap.get(FarmEntityDao.class).clone();
        farmEntityDaoConfig.initIdentityScope(type);

        farmFieldDaoConfig = daoConfigMap.get(FarmFieldDao.class).clone();
        farmFieldDaoConfig.initIdentityScope(type);

        fieldBorderDaoConfig = daoConfigMap.get(FieldBorderDao.class).clone();
        fieldBorderDaoConfig.initIdentityScope(type);

        harvestListDaoConfig = daoConfigMap.get(HarvestListDao.class).clone();
        harvestListDaoConfig.initIdentityScope(type);

        needListDaoConfig = daoConfigMap.get(NeedListDao.class).clone();
        needListDaoConfig.initIdentityScope(type);

        plantHistoryDaoConfig = daoConfigMap.get(PlantHistoryDao.class).clone();
        plantHistoryDaoConfig.initIdentityScope(type);

        preCropsDaoConfig = daoConfigMap.get(PreCropsDao.class).clone();
        preCropsDaoConfig.initIdentityScope(type);

        purchaseOrderDaoConfig = daoConfigMap.get(PurchaseOrderDao.class).clone();
        purchaseOrderDaoConfig.initIdentityScope(type);

        purchasePlusAmountDaoConfig = daoConfigMap.get(PurchasePlusAmountDao.class).clone();
        purchasePlusAmountDaoConfig.initIdentityScope(type);

        purchaseProductDaoConfig = daoConfigMap.get(PurchaseProductDao.class).clone();
        purchaseProductDaoConfig.initIdentityScope(type);

        purchaseRelativeDaoConfig = daoConfigMap.get(PurchaseRelativeDao.class).clone();
        purchaseRelativeDaoConfig.initIdentityScope(type);

        purchaseResultsDaoConfig = daoConfigMap.get(PurchaseResultsDao.class).clone();
        purchaseResultsDaoConfig.initIdentityScope(type);

        purchasingPointDaoConfig = daoConfigMap.get(PurchasingPointDao.class).clone();
        purchasingPointDaoConfig.initIdentityScope(type);

        pushMessageJsonDaoConfig = daoConfigMap.get(PushMessageJsonDao.class).clone();
        pushMessageJsonDaoConfig.initIdentityScope(type);

        regionSaleInfoDaoConfig = daoConfigMap.get(RegionSaleInfoDao.class).clone();
        regionSaleInfoDaoConfig.initIdentityScope(type);

        subscriptionInfoDaoConfig = daoConfigMap.get(SubscriptionInfoDao.class).clone();
        subscriptionInfoDaoConfig.initIdentityScope(type);

        userEntityDaoConfig = daoConfigMap.get(UserEntityDao.class).clone();
        userEntityDaoConfig.initIdentityScope(type);

        vFCropsDaoConfig = daoConfigMap.get(VFCropsDao.class).clone();
        vFCropsDaoConfig.initIdentityScope(type);

        anAcreLandDao = new AnAcreLandDao(anAcreLandDaoConfig, this);
        cropsDao = new CropsDao(cropsDaoConfig, this);
        cropsResultsDao = new CropsResultsDao(cropsResultsDaoConfig, this);
        dCEntityDao = new DCEntityDao(dCEntityDaoConfig, this);
        dCPurchaseDataDao = new DCPurchaseDataDao(dCPurchaseDataDaoConfig, this);
        distributionDao = new DistributionDao(distributionDaoConfig, this);
        farmEntityDao = new FarmEntityDao(farmEntityDaoConfig, this);
        farmFieldDao = new FarmFieldDao(farmFieldDaoConfig, this);
        fieldBorderDao = new FieldBorderDao(fieldBorderDaoConfig, this);
        harvestListDao = new HarvestListDao(harvestListDaoConfig, this);
        needListDao = new NeedListDao(needListDaoConfig, this);
        plantHistoryDao = new PlantHistoryDao(plantHistoryDaoConfig, this);
        preCropsDao = new PreCropsDao(preCropsDaoConfig, this);
        purchaseOrderDao = new PurchaseOrderDao(purchaseOrderDaoConfig, this);
        purchasePlusAmountDao = new PurchasePlusAmountDao(purchasePlusAmountDaoConfig, this);
        purchaseProductDao = new PurchaseProductDao(purchaseProductDaoConfig, this);
        purchaseRelativeDao = new PurchaseRelativeDao(purchaseRelativeDaoConfig, this);
        purchaseResultsDao = new PurchaseResultsDao(purchaseResultsDaoConfig, this);
        purchasingPointDao = new PurchasingPointDao(purchasingPointDaoConfig, this);
        pushMessageJsonDao = new PushMessageJsonDao(pushMessageJsonDaoConfig, this);
        regionSaleInfoDao = new RegionSaleInfoDao(regionSaleInfoDaoConfig, this);
        subscriptionInfoDao = new SubscriptionInfoDao(subscriptionInfoDaoConfig, this);
        userEntityDao = new UserEntityDao(userEntityDaoConfig, this);
        vFCropsDao = new VFCropsDao(vFCropsDaoConfig, this);

        registerDao(AnAcreLand.class, anAcreLandDao);
        registerDao(Crops.class, cropsDao);
        registerDao(CropsResults.class, cropsResultsDao);
        registerDao(DCEntity.class, dCEntityDao);
        registerDao(DCPurchaseData.class, dCPurchaseDataDao);
        registerDao(Distribution.class, distributionDao);
        registerDao(FarmEntity.class, farmEntityDao);
        registerDao(FarmField.class, farmFieldDao);
        registerDao(FieldBorder.class, fieldBorderDao);
        registerDao(HarvestList.class, harvestListDao);
        registerDao(NeedList.class, needListDao);
        registerDao(PlantHistory.class, plantHistoryDao);
        registerDao(PreCrops.class, preCropsDao);
        registerDao(PurchaseOrder.class, purchaseOrderDao);
        registerDao(PurchasePlusAmount.class, purchasePlusAmountDao);
        registerDao(PurchaseProduct.class, purchaseProductDao);
        registerDao(PurchaseRelative.class, purchaseRelativeDao);
        registerDao(PurchaseResults.class, purchaseResultsDao);
        registerDao(PurchasingPoint.class, purchasingPointDao);
        registerDao(PushMessageJson.class, pushMessageJsonDao);
        registerDao(RegionSaleInfo.class, regionSaleInfoDao);
        registerDao(SubscriptionInfo.class, subscriptionInfoDao);
        registerDao(UserEntity.class, userEntityDao);
        registerDao(VFCrops.class, vFCropsDao);
    }
    
    public void clear() {
        anAcreLandDaoConfig.getIdentityScope().clear();
        cropsDaoConfig.getIdentityScope().clear();
        cropsResultsDaoConfig.getIdentityScope().clear();
        dCEntityDaoConfig.getIdentityScope().clear();
        dCPurchaseDataDaoConfig.getIdentityScope().clear();
        distributionDaoConfig.getIdentityScope().clear();
        farmEntityDaoConfig.getIdentityScope().clear();
        farmFieldDaoConfig.getIdentityScope().clear();
        fieldBorderDaoConfig.getIdentityScope().clear();
        harvestListDaoConfig.getIdentityScope().clear();
        needListDaoConfig.getIdentityScope().clear();
        plantHistoryDaoConfig.getIdentityScope().clear();
        preCropsDaoConfig.getIdentityScope().clear();
        purchaseOrderDaoConfig.getIdentityScope().clear();
        purchasePlusAmountDaoConfig.getIdentityScope().clear();
        purchaseProductDaoConfig.getIdentityScope().clear();
        purchaseRelativeDaoConfig.getIdentityScope().clear();
        purchaseResultsDaoConfig.getIdentityScope().clear();
        purchasingPointDaoConfig.getIdentityScope().clear();
        pushMessageJsonDaoConfig.getIdentityScope().clear();
        regionSaleInfoDaoConfig.getIdentityScope().clear();
        subscriptionInfoDaoConfig.getIdentityScope().clear();
        userEntityDaoConfig.getIdentityScope().clear();
        vFCropsDaoConfig.getIdentityScope().clear();
    }

    public AnAcreLandDao getAnAcreLandDao() {
        return anAcreLandDao;
    }

    public CropsDao getCropsDao() {
        return cropsDao;
    }

    public CropsResultsDao getCropsResultsDao() {
        return cropsResultsDao;
    }

    public DCEntityDao getDCEntityDao() {
        return dCEntityDao;
    }

    public DCPurchaseDataDao getDCPurchaseDataDao() {
        return dCPurchaseDataDao;
    }

    public DistributionDao getDistributionDao() {
        return distributionDao;
    }

    public FarmEntityDao getFarmEntityDao() {
        return farmEntityDao;
    }

    public FarmFieldDao getFarmFieldDao() {
        return farmFieldDao;
    }

    public FieldBorderDao getFieldBorderDao() {
        return fieldBorderDao;
    }

    public HarvestListDao getHarvestListDao() {
        return harvestListDao;
    }

    public NeedListDao getNeedListDao() {
        return needListDao;
    }

    public PlantHistoryDao getPlantHistoryDao() {
        return plantHistoryDao;
    }

    public PreCropsDao getPreCropsDao() {
        return preCropsDao;
    }

    public PurchaseOrderDao getPurchaseOrderDao() {
        return purchaseOrderDao;
    }

    public PurchasePlusAmountDao getPurchasePlusAmountDao() {
        return purchasePlusAmountDao;
    }

    public PurchaseProductDao getPurchaseProductDao() {
        return purchaseProductDao;
    }

    public PurchaseRelativeDao getPurchaseRelativeDao() {
        return purchaseRelativeDao;
    }

    public PurchaseResultsDao getPurchaseResultsDao() {
        return purchaseResultsDao;
    }

    public PurchasingPointDao getPurchasingPointDao() {
        return purchasingPointDao;
    }

    public PushMessageJsonDao getPushMessageJsonDao() {
        return pushMessageJsonDao;
    }

    public RegionSaleInfoDao getRegionSaleInfoDao() {
        return regionSaleInfoDao;
    }

    public SubscriptionInfoDao getSubscriptionInfoDao() {
        return subscriptionInfoDao;
    }

    public UserEntityDao getUserEntityDao() {
        return userEntityDao;
    }

    public VFCropsDao getVFCropsDao() {
        return vFCropsDao;
    }

}
