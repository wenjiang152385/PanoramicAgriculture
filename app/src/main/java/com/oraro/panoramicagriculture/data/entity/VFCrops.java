package com.oraro.panoramicagriculture.data.entity;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/14 0014.
 *
 * @author
 */
@Entity
public class VFCrops {
    @Id(autoincrement = true)
    private Long vfid;
    private  String vfname;
    private  String byname;//别名
    private  String path1;//生长初期图片
    private  String path2;//生长中期图片
    private  String path3;//收获图片
    private  String pinyin;//全拼
    public String getPath3() {
        return this.path3;
    }
    public void setPath3(String path3) {
        this.path3 = path3;
    }
    public String getPath2() {
        return this.path2;
    }
    public void setPath2(String path2) {
        this.path2 = path2;
    }
    public String getPath1() {
        return this.path1;
    }
    public void setPath1(String path1) {
        this.path1 = path1;
    }
    public String getByname() {
        return this.byname;
    }
    public void setByname(String byname) {
        this.byname = byname;
    }
    public String getVfname() {
        return this.vfname;
    }
    public void setVfname(String vfname) {
        this.vfname = vfname;
    }
    public Long getVfid() {
        return this.vfid;
    }
    public void setVfid(Long vfid) {
        this.vfid = vfid;
    }
    @Generated(hash = 12022331)
    public VFCrops(Long vfid, String vfname, String byname, String path1,
            String path2, String path3, String pinyin) {
        this.vfid = vfid;
        this.vfname = vfname;
        this.byname = byname;
        this.path1 = path1;
        this.path2 = path2;
        this.path3 = path3;
        this.pinyin = pinyin;
    }
    @Generated(hash = 1966921234)
    public VFCrops() {
    }

    @Override
    public String toString() {
        return "VFCrops{" +
                "vfid=" + vfid +
                ", vfname='" + vfname + '\'' +
                ", byname='" + byname + '\'' +
                ", path1='" + path1 + '\'' +
                ", path2='" + path2 + '\'' +
                ", path3='" + path3 + '\'' +
                '}';
    }
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
