package com.oraro.panoramicagriculture.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oraro.panoramicagriculture.data.entity.VFCrops;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VFCROPS".
*/
public class VFCropsDao extends AbstractDao<VFCrops, Long> {

    public static final String TABLENAME = "VFCROPS";

    /**
     * Properties of entity VFCrops.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Vfid = new Property(0, Long.class, "vfid", true, "_id");
        public final static Property Vfname = new Property(1, String.class, "vfname", false, "VFNAME");
        public final static Property Byname = new Property(2, String.class, "byname", false, "BYNAME");
        public final static Property Path1 = new Property(3, String.class, "path1", false, "PATH1");
        public final static Property Path2 = new Property(4, String.class, "path2", false, "PATH2");
        public final static Property Path3 = new Property(5, String.class, "path3", false, "PATH3");
        public final static Property Pinyin = new Property(6, String.class, "pinyin", false, "PINYIN");
    };


    public VFCropsDao(DaoConfig config) {
        super(config);
    }
    
    public VFCropsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VFCROPS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: vfid
                "\"VFNAME\" TEXT," + // 1: vfname
                "\"BYNAME\" TEXT," + // 2: byname
                "\"PATH1\" TEXT," + // 3: path1
                "\"PATH2\" TEXT," + // 4: path2
                "\"PATH3\" TEXT," + // 5: path3
                "\"PINYIN\" TEXT);"); // 6: pinyin
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VFCROPS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, VFCrops entity) {
        stmt.clearBindings();
 
        Long vfid = entity.getVfid();
        if (vfid != null) {
            stmt.bindLong(1, vfid);
        }
 
        String vfname = entity.getVfname();
        if (vfname != null) {
            stmt.bindString(2, vfname);
        }
 
        String byname = entity.getByname();
        if (byname != null) {
            stmt.bindString(3, byname);
        }
 
        String path1 = entity.getPath1();
        if (path1 != null) {
            stmt.bindString(4, path1);
        }
 
        String path2 = entity.getPath2();
        if (path2 != null) {
            stmt.bindString(5, path2);
        }
 
        String path3 = entity.getPath3();
        if (path3 != null) {
            stmt.bindString(6, path3);
        }
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(7, pinyin);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, VFCrops entity) {
        stmt.clearBindings();
 
        Long vfid = entity.getVfid();
        if (vfid != null) {
            stmt.bindLong(1, vfid);
        }
 
        String vfname = entity.getVfname();
        if (vfname != null) {
            stmt.bindString(2, vfname);
        }
 
        String byname = entity.getByname();
        if (byname != null) {
            stmt.bindString(3, byname);
        }
 
        String path1 = entity.getPath1();
        if (path1 != null) {
            stmt.bindString(4, path1);
        }
 
        String path2 = entity.getPath2();
        if (path2 != null) {
            stmt.bindString(5, path2);
        }
 
        String path3 = entity.getPath3();
        if (path3 != null) {
            stmt.bindString(6, path3);
        }
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(7, pinyin);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public VFCrops readEntity(Cursor cursor, int offset) {
        VFCrops entity = new VFCrops( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // vfid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // vfname
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // byname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // path1
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // path2
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // path3
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // pinyin
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, VFCrops entity, int offset) {
        entity.setVfid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVfname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setByname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPath1(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPath2(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPath3(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPinyin(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(VFCrops entity, long rowId) {
        entity.setVfid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(VFCrops entity) {
        if(entity != null) {
            return entity.getVfid();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
