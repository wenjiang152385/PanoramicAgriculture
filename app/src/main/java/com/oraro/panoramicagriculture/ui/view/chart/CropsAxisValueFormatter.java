package com.oraro.panoramicagriculture.ui.view.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class CropsAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String[] names = {"灯笼椒","番茄","上海青","四季豆","玉米","土豆","皇帝菜"};
        return names[(int)value];
    }
}
