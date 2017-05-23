package com.oraro.panoramicagriculture.ui.adapter;

/**
 * Created by admin on 2017/3/2.
 */
public class PointInfo {
    private int i_icon;
    private String str_name;
    private String str_value;
    public PointInfo(int _icon, String _name, String _value){
        i_icon = _icon;
        str_name = _name;
        str_value = _value;
    }
    public int geticon(){return i_icon;}
    public void seticon(int _icon){i_icon = _icon;}
    public String getname(){return str_name;}
    public void setname(String _name){str_name = _name;}
    public String getvalue(){return str_value;}
    public void setvalue(String _value){str_value = _value;}
}
