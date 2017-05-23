package com.oraro.panoramicagriculture.data.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class AbilityModel<T extends AbilityEntry> {

    private List<T> mValues = null;
    private String mLabel;

    public AbilityModel(List<T> list,String label) {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        mLabel = label;
        mValues = list;
    }

    public List<T> getList() {
        return mValues;
    }

    public String getLabel() {
        return mLabel;
    }
}
