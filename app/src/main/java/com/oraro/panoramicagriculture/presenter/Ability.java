package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.oraro.panoramicagriculture.data.entity.AbilityEntry;
import com.oraro.panoramicagriculture.data.entity.AbilityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 * 获取能力
 */
public abstract class Ability {


    public abstract String[] geAbilities();

    public abstract String[] geAbilityNames();

    public abstract String label();

    public abstract String getMethod();

    public void getDataJsonObject(JsonObject jsonObject, long id) {

    }

    public AbilityModel parserJsonData(Object o) {
        LinkedTreeMap entity = getLinkedTreeMapList(o);
        List<AbilityEntry> list = new ArrayList<>();
        for(int j = 0; j < geAbilities().length; j++) {
            String property = geAbilities()[j];
            double num = (Double) entity.get(property);
            list.add(new AbilityEntry(geAbilityNames()[j],num));
        }
        AbilityModel abilityModel = new AbilityModel(list,label());
        return abilityModel;
    }

    public LinkedTreeMap getLinkedTreeMapList(Object o) {
        LinkedTreeMap data = (LinkedTreeMap) ((LinkedTreeMap) o).get("data");
        return data;
    }
}
