package com.oraro.panoramicagriculture.data.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oraro.panoramicagriculture.data.entity.VFCrops;

import com.oraro.panoramicagriculture.data.entity.AnAcreLand;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "AN_ACRE_LAND".
*/
public class AnAcreLandDao extends AbstractDao<AnAcreLand, Long> {

    public static final String TABLENAME = "AN_ACRE_LAND";

    /**
     * Properties of entity AnAcreLand.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property FarmId = new Property(1, long.class, "farmId", false, "FARM_ID");
        public final static Property VfcropsId = new Property(2, long.class, "vfcropsId", false, "VFCROPS_ID");
        public final static Property PlantTime = new Property(3, java.util.Date.class, "plantTime", false, "PLANT_TIME");
        public final static Property ExpectHarvestTime = new Property(4, java.util.Date.class, "expectHarvestTime", false, "EXPECT_HARVEST_TIME");
        public final static Property ExpectHarvestNum = new Property(5, long.class, "expectHarvestNum", false, "EXPECT_HARVEST_NUM");
        public final static Property PlantNum = new Property(6, long.class, "plantNum", false, "PLANT_NUM");
        public final static Property ActualHarvestNum = new Property(7, long.class, "actualHarvestNum", false, "ACTUAL_HARVEST_NUM");
        public final static Property State = new Property(8, int.class, "state", false, "STATE");
        public final static Property Position = new Property(9, int.class, "position", false, "POSITION");
        public final static Property Totalday = new Property(10, int.class, "totalday", false, "TOTALDAY");
        public final static Property Plantday = new Property(11, int.class, "plantday", false, "PLANTDAY");
        public final static Property PlantHistoryId = new Property(12, long.class, "plantHistoryId", false, "PLANT_HISTORY_ID");
        public final static Property HarvestHistoryId = new Property(13, long.class, "harvestHistoryId", false, "HARVEST_HISTORY_ID");
    };

    private DaoSession daoSession;


    public AnAcreLandDao(DaoConfig config) {
        super(config);
    }
    
    public AnAcreLandDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"AN_ACRE_LAND\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"FARM_ID\" INTEGER NOT NULL ," + // 1: farmId
                "\"VFCROPS_ID\" INTEGER NOT NULL ," + // 2: vfcropsId
                "\"PLANT_TIME\" INTEGER," + // 3: plantTime
                "\"EXPECT_HARVEST_TIME\" INTEGER," + // 4: expectHarvestTime
                "\"EXPECT_HARVEST_NUM\" INTEGER NOT NULL ," + // 5: expectHarvestNum
                "\"PLANT_NUM\" INTEGER NOT NULL ," + // 6: plantNum
                "\"ACTUAL_HARVEST_NUM\" INTEGER NOT NULL ," + // 7: actualHarvestNum
                "\"STATE\" INTEGER NOT NULL ," + // 8: state
                "\"POSITION\" INTEGER NOT NULL ," + // 9: position
                "\"TOTALDAY\" INTEGER NOT NULL ," + // 10: totalday
                "\"PLANTDAY\" INTEGER NOT NULL ," + // 11: plantday
                "\"PLANT_HISTORY_ID\" INTEGER NOT NULL ," + // 12: plantHistoryId
                "\"HARVEST_HISTORY_ID\" INTEGER NOT NULL );"); // 13: harvestHistoryId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"AN_ACRE_LAND\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AnAcreLand entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getFarmId());
        stmt.bindLong(3, entity.getVfcropsId());
 
        java.util.Date plantTime = entity.getPlantTime();
        if (plantTime != null) {
            stmt.bindLong(4, plantTime.getTime());
        }
 
        java.util.Date expectHarvestTime = entity.getExpectHarvestTime();
        if (expectHarvestTime != null) {
            stmt.bindLong(5, expectHarvestTime.getTime());
        }
        stmt.bindLong(6, entity.getExpectHarvestNum());
        stmt.bindLong(7, entity.getPlantNum());
        stmt.bindLong(8, entity.getActualHarvestNum());
        stmt.bindLong(9, entity.getState());
        stmt.bindLong(10, entity.getPosition());
        stmt.bindLong(11, entity.getTotalday());
        stmt.bindLong(12, entity.getPlantday());
        stmt.bindLong(13, entity.getPlantHistoryId());
        stmt.bindLong(14, entity.getHarvestHistoryId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AnAcreLand entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getFarmId());
        stmt.bindLong(3, entity.getVfcropsId());
 
        java.util.Date plantTime = entity.getPlantTime();
        if (plantTime != null) {
            stmt.bindLong(4, plantTime.getTime());
        }
 
        java.util.Date expectHarvestTime = entity.getExpectHarvestTime();
        if (expectHarvestTime != null) {
            stmt.bindLong(5, expectHarvestTime.getTime());
        }
        stmt.bindLong(6, entity.getExpectHarvestNum());
        stmt.bindLong(7, entity.getPlantNum());
        stmt.bindLong(8, entity.getActualHarvestNum());
        stmt.bindLong(9, entity.getState());
        stmt.bindLong(10, entity.getPosition());
        stmt.bindLong(11, entity.getTotalday());
        stmt.bindLong(12, entity.getPlantday());
        stmt.bindLong(13, entity.getPlantHistoryId());
        stmt.bindLong(14, entity.getHarvestHistoryId());
    }

    @Override
    protected final void attachEntity(AnAcreLand entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public AnAcreLand readEntity(Cursor cursor, int offset) {
        AnAcreLand entity = new AnAcreLand( //
            cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // farmId
            cursor.getLong(offset + 2), // vfcropsId
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // plantTime
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // expectHarvestTime
            cursor.getLong(offset + 5), // expectHarvestNum
            cursor.getLong(offset + 6), // plantNum
            cursor.getLong(offset + 7), // actualHarvestNum
            cursor.getInt(offset + 8), // state
            cursor.getInt(offset + 9), // position
            cursor.getInt(offset + 10), // totalday
            cursor.getInt(offset + 11), // plantday
            cursor.getLong(offset + 12), // plantHistoryId
            cursor.getLong(offset + 13) // harvestHistoryId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AnAcreLand entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setFarmId(cursor.getLong(offset + 1));
        entity.setVfcropsId(cursor.getLong(offset + 2));
        entity.setPlantTime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setExpectHarvestTime(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setExpectHarvestNum(cursor.getLong(offset + 5));
        entity.setPlantNum(cursor.getLong(offset + 6));
        entity.setActualHarvestNum(cursor.getLong(offset + 7));
        entity.setState(cursor.getInt(offset + 8));
        entity.setPosition(cursor.getInt(offset + 9));
        entity.setTotalday(cursor.getInt(offset + 10));
        entity.setPlantday(cursor.getInt(offset + 11));
        entity.setPlantHistoryId(cursor.getLong(offset + 12));
        entity.setHarvestHistoryId(cursor.getLong(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AnAcreLand entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AnAcreLand entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getVFCropsDao().getAllColumns());
            builder.append(" FROM AN_ACRE_LAND T");
            builder.append(" LEFT JOIN VFCROPS T0 ON T.\"VFCROPS_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected AnAcreLand loadCurrentDeep(Cursor cursor, boolean lock) {
        AnAcreLand entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        VFCrops vfCrops = loadCurrentOther(daoSession.getVFCropsDao(), cursor, offset);
         if(vfCrops != null) {
            entity.setVfCrops(vfCrops);
        }

        return entity;    
    }

    public AnAcreLand loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<AnAcreLand> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<AnAcreLand> list = new ArrayList<AnAcreLand>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<AnAcreLand> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<AnAcreLand> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
