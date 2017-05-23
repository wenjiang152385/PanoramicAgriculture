package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/13 0013.
 *
 * @author
 */
@Entity
public class Crops {
    @Id(autoincrement = true)
    private Long cropid;
    private String name;
    private String byname;
    private Long type1;
    private String type2;
    private String classes;
    private String family;
    private String picPath;
    public String getFamily() {
        return this.family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    public String getClasses() {
        return this.classes;
    }
    public void setClasses(String classes) {
        this.classes = classes;
    }
    public String getType2() {
        return this.type2;
    }
    public void setType2(String type2) {
        this.type2 = type2;
    }
    public String getByname() {
        return this.byname;
    }
    public void setByname(String byname) {
        this.byname = byname;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getCropid() {
        return this.cropid;
    }
    public void setCropid(Long cropid) {
        this.cropid = cropid;
    }
    public String getPicPath() {
        return this.picPath;
    }
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
    public Long getType1() {
        return this.type1;
    }
    public void setType1(Long type1) {
        this.type1 = type1;
    }
    @Generated(hash = 1069134009)
    public Crops(Long cropid, String name, String byname, Long type1, String type2,
            String classes, String family, String picPath) {
        this.cropid = cropid;
        this.name = name;
        this.byname = byname;
        this.type1 = type1;
        this.type2 = type2;
        this.classes = classes;
        this.family = family;
        this.picPath = picPath;
    }
    @Generated(hash = 391315217)
    public Crops() {
    }
}
