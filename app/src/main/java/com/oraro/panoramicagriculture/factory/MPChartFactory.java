package com.oraro.panoramicagriculture.factory;


import android.util.Log;

import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.presenter.DCNeeds;
import com.oraro.panoramicagriculture.presenter.DCPurchase;
import com.oraro.panoramicagriculture.presenter.FarmHarvest;
import com.oraro.panoramicagriculture.presenter.MPChartData;
import com.oraro.panoramicagriculture.presenter.PointRequire;
import com.oraro.panoramicagriculture.presenter.PointSale;
import com.oraro.panoramicagriculture.presenter.DCOrder;
import com.oraro.panoramicagriculture.presenter.WholeHarvest;
import com.oraro.panoramicagriculture.presenter.WholeRequirement;
import com.oraro.panoramicagriculture.presenter.WholeSale;

/**
 * Created by Administrator on 2017/4/12 0012.
 */
public class MPChartFactory {
    private  static  MPChartFactory mpChartFactory;

    private MPChartFactory() {

    }
    public static synchronized MPChartFactory getInstance() {
        if (null == mpChartFactory) {
            mpChartFactory = new MPChartFactory();
        }
        return mpChartFactory;
    }

    public  MPChartData switchMPChartData(int type) {
        MPChartData mpChartData = null;
        if (Constants.CHART_ROLE_FARMER == type) {
            mpChartData = new WholeHarvest();
            return mpChartData;
        }
        if (Constants.CHART_ROLE_REQUIRE == type) {
            mpChartData = new WholeRequirement();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_PURCHASER == type) {
            mpChartData = new WholeSale();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_POINT_SALE == type) {
            mpChartData = new PointSale();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_POINT_REQUIRE == type) {
            mpChartData = new PointRequire();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_FARM_REQUIRE == type) {
            mpChartData = new FarmHarvest();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_DC_ORDER == type) {
            mpChartData = new DCOrder();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_DC_PURCHASER == type) {
            mpChartData = new DCPurchase();
            return mpChartData;
        }

        if (Constants.CHART_ROLE_DC_NEEDS == type) {
            mpChartData = new DCNeeds();
            return mpChartData;
        }

        return mpChartData;
    }
}
