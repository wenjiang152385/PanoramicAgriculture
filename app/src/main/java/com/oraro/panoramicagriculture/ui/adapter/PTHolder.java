package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by admin on 2017/4/10.
 */
public class PTHolder extends RecyclerView.ViewHolder{
    protected Context context;
    protected HolderData holderData;

    public PTHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }
    public void initData(HolderData holderData){
        this.holderData = holderData;
        doInit();
    }
    protected void doInit(){}
}
