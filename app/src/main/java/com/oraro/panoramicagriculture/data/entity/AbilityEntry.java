package com.oraro.panoramicagriculture.data.entity;



/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class AbilityEntry {
    private String abilityName;
    private double num;

    public AbilityEntry(String abilityName, double num) {
        this.abilityName = abilityName;
        this.num = num;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public double getNum() {
        return num;
    }
}
