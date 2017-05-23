package com.oraro.panoramicagriculture.factory;

import com.oraro.panoramicagriculture.presenter.Ability;
import com.oraro.panoramicagriculture.presenter.FarmAbility;
import com.oraro.panoramicagriculture.utils.CommonUtils;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class PointInfoFactory {
    private static PointInfoFactory mPointInfoFactory;

    private PointInfoFactory() {

    }

    public synchronized static PointInfoFactory getInstance() {
        if (null == mPointInfoFactory) {
            mPointInfoFactory = new PointInfoFactory();
        }
        return mPointInfoFactory;
    }

    public Ability getAbility(CommonUtils.ABILITY_TYPE type) {
        Ability ability = null;
        if (type == CommonUtils.ABILITY_TYPE.FARM) {
            ability = new FarmAbility();
            return ability;
        }
        return ability;
    }
}
