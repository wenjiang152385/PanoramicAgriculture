package com.oraro.panoramicagriculture.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;

/**
 * Created by admin on 2017/4/7.
 */
public class PointMainFarmAdapter extends PointMainAdapter{
    @Override
    protected PTHolderSwitcher onCreateSwitchHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void CreateMainAdapter(PTHolderSwitcher holder) {

    }


    public PointMainFarmAdapter(PointMainActivity context, HolderData holderData) {
        super(context, holderData);
    }
}
