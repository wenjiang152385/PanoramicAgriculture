package com.oraro.panoramicagriculture.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oraro.panoramicagriculture.data.entity.FarmField;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FARM_FIELD".
*/
public class FarmFieldDao extends AbstractDao<FarmField, Void> {

    public static final String TABLENAME = "FARM_FIELD";

    /**
     * Properties of entity FarmField.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property FieldId = new Property(0, Long.class, "fieldId", false, "FIELD_ID");
        public final static Property FieldName = new Property(1, String.class, "fieldName", false, "FIELD_NAME");
        public final static Property State = new Property(2, int.class, "state", false, "STATE");
        public final static Property FieldArea = new Property(3, Double.class, "fieldArea", false, "FIELD_AREA");
        public final static Property MatureDegree = new Property(4, Double.class, "matureDegree", false, "MATURE_DEGREE");
        public final static Property Latitude = new Property(5, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(6, Double.class, "longitude", false, "LONGITUDE");
        public final static Property CurrentSowTime = new Property(7, String.class, "currentSowTime", false, "CURRENT_SOW_TIME");
        public final static Property CurrentMatureTime = new Property(8, String.class, "currentMatureTime", false, "CURRENT_MATURE_TIME");
        public final static Property NextSowTime = new Property(9, String.class, "nextSowTime", false, "NEXT_SOW_TIME");
        public final static Property FarmId = new Property(10, Long.class, "farmId", false, "FARM_ID");
        public final static Property PlantNum = new Property(11, long.class, "plantNum", false, "PLANT_NUM");
        public final static Property ExpectHarvestNum = new Property(12, long.class, "expectHarvestNum", false, "EXPECT_HARVEST_NUM");
        public final static Property ActualHarvestNum = new Property(13, long.class, "actualHarvestNum", false, "ACTUAL_HARVEST_NUM");
        public final static Property Totalday = new Property(14, int.class, "totalday", false, "TOTALDAY");
        public final static Property Plantday = new Property(15, int.class, "plantday", false, "PLANTDAY");
        public final static Property PlantHistoryId = new Property(16, long.class, "plantHistoryId", false, "PLANT_HISTORY_ID");
        public final static Property HarvestHistoryId = new Property(17, long.class, "harvestHistoryId", false, "HARVEST_HISTORY_ID");
        public final static Property CurrentVFcropsId = new Property(18, long.class, "currentVFcropsId", false, "CURRENT_VFCROPS_ID");
        public final static Property NextVFcropsId = new Property(19, long.class, "nextVFcropsId", false, "NEXT_VFCROPS_ID");
    };


    public FarmFieldDao(DaoConfig config) {
        super(config);
    }
    
    public FarmFieldDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FARM_FIELD\" (" + //
                "\"FIELD_ID\" INTEGER," + // 0: fieldId
                "\"FIELD_NAME\" TEXT," + // 1: fieldName
                "\"STATE\" INTEGER NOT NULL ," + // 2: state
                "\"FIELD_AREA\" REAL," + // 3: fieldArea
                "\"MATURE_DEGREE\" REAL," + // 4: matureDegree
                "\"LATITUDE\" REAL," + // 5: latitude
                "\"LONGITUDE\" REAL," + // 6: longitude
                "\"CURRENT_SOW_TIME\" TEXT," + // 7: currentSowTime
                "\"CURRENT_MATURE_TIME\" TEXT," + // 8: currentMatureTime
                "\"NEXT_SOW_TIME\" TEXT," + // 9: nextSowTime
                "\"FARM_ID\" INTEGER," + // 10: farmId
                "\"PLANT_NUM\" INTEGER NOT NULL ," + // 11: plantNum
                "\"EXPECT_HARVEST_NUM\" INTEGER NOT NULL ," + // 12: expectHarvestNum
                "\"ACTUAL_HARVEST_NUM\" INTEGER NOT NULL ," + // 13: actualHarvestNum
                "\"TOTALDAY\" INTEGER NOT NULL ," + // 14: totalday
                "\"PLANTDAY\" INTEGER NOT NULL ," + // 15: plantday
                "\"PLANT_HISTORY_ID\" INTEGER NOT NULL ," + // 16: plantHistoryId
                "\"HARVEST_HISTORY_ID\" INTEGER NOT NULL ," + // 17: harvestHistoryId
                "\"CURRENT_VFCROPS_ID\" INTEGER NOT NULL ," + // 18: currentVFcropsId
                "\"NEXT_VFCROPS_ID\" INTEGER NOT NULL );"); // 19: nextVFcropsId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FARM_FIELD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FarmField entity) {
        stmt.clearBindings();
 
        Long fieldId = entity.getFieldId();
        if (fieldId != null) {
            stmt.bindLong(1, fieldId);
        }
 
        String fieldName = entity.getFieldName();
        if (fieldName != null) {
            stmt.bindString(2, fieldName);
        }
        stmt.bindLong(3, entity.getState());
 
        Double fieldArea = entity.getFieldArea();
        if (fieldArea != null) {
            stmt.bindDouble(4, fieldArea);
        }
 
        Double matureDegree = entity.getMatureDegree();
        if (matureDegree != null) {
            stmt.bindDouble(5, matureDegree);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(6, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(7, longitude);
        }
 
        String currentSowTime = entity.getCurrentSowTime();
        if (currentSowTime != null) {
            stmt.bindString(8, currentSowTime);
        }
 
        String currentMatureTime = entity.getCurrentMatureTime();
        if (currentMatureTime != null) {
            stmt.bindString(9, currentMatureTime);
        }
 
        String nextSowTime = entity.getNextSowTime();
        if (nextSowTime != null) {
            stmt.bindString(10, nextSowTime);
        }
 
        Long farmId = entity.getFarmId();
        if (farmId != null) {
            stmt.bindLong(11, farmId);
        }
        stmt.bindLong(12, entity.getPlantNum());
        stmt.bindLong(13, entity.getExpectHarvestNum());
        stmt.bindLong(14, entity.getActualHarvestNum());
        stmt.bindLong(15, entity.getTotalday());
        stmt.bindLong(16, entity.getPlantday());
        stmt.bindLong(17, entity.getPlantHistoryId());
        stmt.bindLong(18, entity.getHarvestHistoryId());
        stmt.bindLong(19, entity.getCurrentVFcropsId());
        stmt.bindLong(20, entity.getNextVFcropsId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FarmField entity) {
        stmt.clearBindings();
 
        Long fieldId = entity.getFieldId();
        if (fieldId != null) {
            stmt.bindLong(1, fieldId);
        }
 
        String fieldName = entity.getFieldName();
        if (fieldName != null) {
            stmt.bindString(2, fieldName);
        }
        stmt.bindLong(3, entity.getState());
 
        Double fieldArea = entity.getFieldArea();
        if (fieldArea != null) {
            stmt.bindDouble(4, fieldArea);
        }
 
        Double matureDegree = entity.getMatureDegree();
        if (matureDegree != null) {
            stmt.bindDouble(5, matureDegree);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(6, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(7, longitude);
        }
 
        String currentSowTime = entity.getCurrentSowTime();
        if (currentSowTime != null) {
            stmt.bindString(8, currentSowTime);
        }
 
        String currentMatureTime = entity.getCurrentMatureTime();
        if (currentMatureTime != null) {
            stmt.bindString(9, currentMatureTime);
        }
 
        String nextSowTime = entity.getNextSowTime();
        if (nextSowTime != null) {
            stmt.bindString(10, nextSowTime);
        }
 
        Long farmId = entity.getFarmId();
        if (farmId != null) {
            stmt.bindLong(11, farmId);
        }
        stmt.bindLong(12, entity.getPlantNum());
        stmt.bindLong(13, entity.getExpectHarvestNum());
        stmt.bindLong(14, entity.getActualHarvestNum());
        stmt.bindLong(15, entity.getTotalday());
        stmt.bindLong(16, entity.getPlantday());
        stmt.bindLong(17, entity.getPlantHistoryId());
        stmt.bindLong(18, entity.getHarvestHistoryId());
        stmt.bindLong(19, entity.getCurrentVFcropsId());
        stmt.bindLong(20, entity.getNextVFcropsId());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public FarmField readEntity(Cursor cursor, int offset) {
        FarmField entity = new FarmField( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // fieldId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fieldName
            cursor.getInt(offset + 2), // state
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // fieldArea
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // matureDegree
            cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5), // latitude
            cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6), // longitude
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // currentSowTime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // currentMatureTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // nextSowTime
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // farmId
            cursor.getLong(offset + 11), // plantNum
            cursor.getLong(offset + 12), // expectHarvestNum
            cursor.getLong(offset + 13), // actualHarvestNum
            cursor.getInt(offset + 14), // totalday
            cursor.getInt(offset + 15), // plantday
            cursor.getLong(offset + 16), // plantHistoryId
            cursor.getLong(offset + 17), // harvestHistoryId
            cursor.getLong(offset + 18), // currentVFcropsId
            cursor.getLong(offset + 19) // nextVFcropsId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FarmField entity, int offset) {
        entity.setFieldId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFieldName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setState(cursor.getInt(offset + 2));
        entity.setFieldArea(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setMatureDegree(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setLatitude(cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5));
        entity.setLongitude(cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6));
        entity.setCurrentSowTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCurrentMatureTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setNextSowTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFarmId(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setPlantNum(cursor.getLong(offset + 11));
        entity.setExpectHarvestNum(cursor.getLong(offset + 12));
        entity.setActualHarvestNum(cursor.getLong(offset + 13));
        entity.setTotalday(cursor.getInt(offset + 14));
        entity.setPlantday(cursor.getInt(offset + 15));
        entity.setPlantHistoryId(cursor.getLong(offset + 16));
        entity.setHarvestHistoryId(cursor.getLong(offset + 17));
        entity.setCurrentVFcropsId(cursor.getLong(offset + 18));
        entity.setNextVFcropsId(cursor.getLong(offset + 19));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(FarmField entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(FarmField entity) {
        return null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
