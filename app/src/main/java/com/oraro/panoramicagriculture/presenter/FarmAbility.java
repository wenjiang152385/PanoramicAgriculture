package com.oraro.panoramicagriculture.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class FarmAbility extends Ability{

    public final static String METHOD = "getFarmAbility";


    @Override
    public String[] geAbilities() {
        String[] str = {"vfsize","farmArea","farmHot","farmProduce","rank"};
        return str;
    }

    @Override
    public String[] geAbilityNames() {
        String[] strName = {"种植产量","种植规模","农场热度","得产率","农场排名"};
        return strName;
    }

    @Override
    public String label() {
        return "农场能力";
    }

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public void getDataJsonObject(JsonObject jsonObject, long id) {
        super.getDataJsonObject(jsonObject, id);
        jsonObject.addProperty("farmId",71);
    }
}
