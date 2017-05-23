package com.oraro.panoramicagriculture.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import java.text.SimpleDateFormat;

/**
 * Created by admin on 2017/4/10.
 */
public class PTHolderSwitcher extends PTHolder{
    //需求、销售类型
    public enum TypeGlobal{purchase_page1, purchase_page2};
    //今天、昨天、明天类型
    public enum DateTypeGlobal{TODAY, YESTERDAY, BEFORE_YESTERDAY,MORE};
    protected TypeGlobal type = TypeGlobal.purchase_page1;
    protected DateTypeGlobal mDateType = DateTypeGlobal.TODAY;
    protected String date;
    protected PointMainAdapter pointMainAdapter;
    protected Activity activity;
    protected LayoutInflater mInflater;
    SimpleDateFormat sf;

    public TypeGlobal getType() {
        return type;
    }

    public void setType(TypeGlobal type) {
        this.type = type;
    }

    public void setDateType(DateTypeGlobal type){
        mDateType = type;
    }

    public DateTypeGlobal getDateType(){
        return mDateType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PTHolderSwitcher(Activity activity, PointMainAdapter pointMainAdapter, View itemView) {
        super(itemView);
        this.activity = activity;
        mInflater = LayoutInflater.from(activity);
        this.pointMainAdapter = pointMainAdapter;

        sf = new SimpleDateFormat("yyyy-MM-dd");
    }
    public int switchHolder(){
        return 0;
    }
}
