/**
 *
 */
package com.oraro.panoramicagriculture.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.oraro.panoramicagriculture.common.Constants;


public class Preferences {

    private Context context;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private String DB_NAME = "PanoramicAgriculture";
    private static Preferences instance;

    public static Preferences getInstance(Context context) {
        if (instance == null)
            instance = new Preferences(context.getApplicationContext());
        return instance;
    }

    private Preferences(Context context) {
        this.context = context;
        settings = this.context.getSharedPreferences(DB_NAME, 0);
        editor = settings.edit();
    }

    /**
     * 设置地图上显示所有图标、农场图标、采购商图标
     */
    public void setShowIconOnMapFlag(int flag){
        editor.putInt("ShowIconOnMapFlag",flag);
        editor.commit();
    }

    public int getShowIconOnMapFlag(){
        return settings.getInt("ShowIconOnMapFlag", Constants.SETTINGS_SHOW_ALL_ICON_FLAG);
    }

}
