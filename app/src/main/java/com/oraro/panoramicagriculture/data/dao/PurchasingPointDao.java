package com.oraro.panoramicagriculture.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PURCHASING_POINT".
*/
public class PurchasingPointDao extends AbstractDao<PurchasingPoint, Long> {

    public static final String TABLENAME = "PURCHASING_POINT";

    /**
     * Properties of entity PurchasingPoint.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property PurchasingPointId = new Property(0, Long.class, "purchasingPointId", true, "_id");
        public final static Property PurchasingPointName = new Property(1, String.class, "purchasingPointName", false, "PURCHASING_POINT_NAME");
        public final static Property Latitude = new Property(2, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(3, Double.class, "longitude", false, "LONGITUDE");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property Country = new Property(5, String.class, "country", false, "COUNTRY");
        public final static Property Province = new Property(6, String.class, "province", false, "PROVINCE");
        public final static Property City = new Property(7, String.class, "city", false, "CITY");
        public final static Property District = new Property(8, String.class, "district", false, "DISTRICT");
        public final static Property Street = new Property(9, String.class, "street", false, "STREET");
        public final static Property StreetNum = new Property(10, String.class, "streetNum", false, "STREET_NUM");
        public final static Property PurchasingPointInfo = new Property(11, String.class, "purchasingPointInfo", false, "PURCHASING_POINT_INFO");
        public final static Property PhoneNum = new Property(12, String.class, "phoneNum", false, "PHONE_NUM");
        public final static Property UserId = new Property(13, Long.class, "userId", false, "USER_ID");
        public final static Property Slide1 = new Property(14, String.class, "slide1", false, "SLIDE1");
        public final static Property Slide2 = new Property(15, String.class, "slide2", false, "SLIDE2");
        public final static Property Slide3 = new Property(16, String.class, "slide3", false, "SLIDE3");
        public final static Property Slide4 = new Property(17, String.class, "slide4", false, "SLIDE4");
    };


    public PurchasingPointDao(DaoConfig config) {
        super(config);
    }
    
    public PurchasingPointDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PURCHASING_POINT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: purchasingPointId
                "\"PURCHASING_POINT_NAME\" TEXT," + // 1: purchasingPointName
                "\"LATITUDE\" REAL," + // 2: latitude
                "\"LONGITUDE\" REAL," + // 3: longitude
                "\"ADDRESS\" TEXT," + // 4: address
                "\"COUNTRY\" TEXT," + // 5: country
                "\"PROVINCE\" TEXT," + // 6: province
                "\"CITY\" TEXT," + // 7: city
                "\"DISTRICT\" TEXT," + // 8: district
                "\"STREET\" TEXT," + // 9: street
                "\"STREET_NUM\" TEXT," + // 10: streetNum
                "\"PURCHASING_POINT_INFO\" TEXT," + // 11: purchasingPointInfo
                "\"PHONE_NUM\" TEXT," + // 12: phoneNum
                "\"USER_ID\" INTEGER," + // 13: userId
                "\"SLIDE1\" TEXT," + // 14: slide1
                "\"SLIDE2\" TEXT," + // 15: slide2
                "\"SLIDE3\" TEXT," + // 16: slide3
                "\"SLIDE4\" TEXT);"); // 17: slide4
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PURCHASING_POINT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PurchasingPoint entity) {
        stmt.clearBindings();
 
        Long purchasingPointId = entity.getPurchasingPointId();
        if (purchasingPointId != null) {
            stmt.bindLong(1, purchasingPointId);
        }
 
        String purchasingPointName = entity.getPurchasingPointName();
        if (purchasingPointName != null) {
            stmt.bindString(2, purchasingPointName);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(3, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(4, longitude);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(6, country);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(7, province);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(8, city);
        }
 
        String district = entity.getDistrict();
        if (district != null) {
            stmt.bindString(9, district);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(10, street);
        }
 
        String streetNum = entity.getStreetNum();
        if (streetNum != null) {
            stmt.bindString(11, streetNum);
        }
 
        String purchasingPointInfo = entity.getPurchasingPointInfo();
        if (purchasingPointInfo != null) {
            stmt.bindString(12, purchasingPointInfo);
        }
 
        String phoneNum = entity.getPhoneNum();
        if (phoneNum != null) {
            stmt.bindString(13, phoneNum);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(14, userId);
        }
 
        String slide1 = entity.getSlide1();
        if (slide1 != null) {
            stmt.bindString(15, slide1);
        }
 
        String slide2 = entity.getSlide2();
        if (slide2 != null) {
            stmt.bindString(16, slide2);
        }
 
        String slide3 = entity.getSlide3();
        if (slide3 != null) {
            stmt.bindString(17, slide3);
        }
 
        String slide4 = entity.getSlide4();
        if (slide4 != null) {
            stmt.bindString(18, slide4);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PurchasingPoint entity) {
        stmt.clearBindings();
 
        Long purchasingPointId = entity.getPurchasingPointId();
        if (purchasingPointId != null) {
            stmt.bindLong(1, purchasingPointId);
        }
 
        String purchasingPointName = entity.getPurchasingPointName();
        if (purchasingPointName != null) {
            stmt.bindString(2, purchasingPointName);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(3, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(4, longitude);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(6, country);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(7, province);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(8, city);
        }
 
        String district = entity.getDistrict();
        if (district != null) {
            stmt.bindString(9, district);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(10, street);
        }
 
        String streetNum = entity.getStreetNum();
        if (streetNum != null) {
            stmt.bindString(11, streetNum);
        }
 
        String purchasingPointInfo = entity.getPurchasingPointInfo();
        if (purchasingPointInfo != null) {
            stmt.bindString(12, purchasingPointInfo);
        }
 
        String phoneNum = entity.getPhoneNum();
        if (phoneNum != null) {
            stmt.bindString(13, phoneNum);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(14, userId);
        }
 
        String slide1 = entity.getSlide1();
        if (slide1 != null) {
            stmt.bindString(15, slide1);
        }
 
        String slide2 = entity.getSlide2();
        if (slide2 != null) {
            stmt.bindString(16, slide2);
        }
 
        String slide3 = entity.getSlide3();
        if (slide3 != null) {
            stmt.bindString(17, slide3);
        }
 
        String slide4 = entity.getSlide4();
        if (slide4 != null) {
            stmt.bindString(18, slide4);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PurchasingPoint readEntity(Cursor cursor, int offset) {
        PurchasingPoint entity = new PurchasingPoint( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // purchasingPointId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // purchasingPointName
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // latitude
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // longitude
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // country
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // province
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // city
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // district
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // street
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // streetNum
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // purchasingPointInfo
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // phoneNum
            cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13), // userId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // slide1
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // slide2
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // slide3
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17) // slide4
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PurchasingPoint entity, int offset) {
        entity.setPurchasingPointId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPurchasingPointName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLatitude(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setLongitude(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCountry(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setProvince(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCity(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDistrict(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setStreet(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStreetNum(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPurchasingPointInfo(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPhoneNum(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUserId(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
        entity.setSlide1(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setSlide2(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setSlide3(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setSlide4(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PurchasingPoint entity, long rowId) {
        entity.setPurchasingPointId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PurchasingPoint entity) {
        if(entity != null) {
            return entity.getPurchasingPointId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}