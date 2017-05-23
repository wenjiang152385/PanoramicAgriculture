package com.oraro.panoramicagriculture.ui.adapter;

import android.view.ViewGroup;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;

/**
 * Created by admin on 2017/4/7.
 */
public class PointMainPurchaseAdapter extends PointMainAdapter {
    @Override
    protected PTHolderSwitcher onCreateSwitchHolder(ViewGroup parent) {
        PTHolderSwitcher switcher = new PTHolder2(context, this, mInflate.inflate(R.layout.item_ptholder2, parent, false));
        return switcher;
    }

    @Override
    protected void CreateMainAdapter(PTHolderSwitcher holder) {
        if (holder == null){
            setCurrentLoadType(TYPE_LOAD_NEEDS_TODAY);
            mainAdapter =  new PurchaseNeedImpl(context, this, holderData);
        }else{
            if (holder.getType() == PTHolderSwitcher.TypeGlobal.purchase_page1){
                if(holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.TODAY){
                    setCurrentLoadType(TYPE_LOAD_NEEDS_TODAY);
                }else if(holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.YESTERDAY){
                    setCurrentLoadType(TYPE_LOAD_NEEDS_YESTERDAY);
                }else if(holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.BEFORE_YESTERDAY){
                    setCurrentLoadType(TYPE_LOAD_NEEDS_BEFORE_YESTERDAY);
                }else if (holder.getDateType()==PTHolderSwitcher.DateTypeGlobal.MORE){
                    setCurrentLoadType(TYPE_LOAD_NEEDS_MORE);
                }
                mainAdapter = new PurchaseNeedImpl(context, this, holderData);
            }else if (holder.getType() == PTHolderSwitcher.TypeGlobal.purchase_page2){
                if(holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.TODAY){
                    setCurrentLoadType(TYPE_LOAD_SALE_TODAY);
                }else if(holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.YESTERDAY){
                    setCurrentLoadType(TYPE_LOAD_SALE_YESTERDAY);
                }else if(holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.BEFORE_YESTERDAY){
                    setCurrentLoadType(TYPE_LOAD_SALE_BEFORE_YESTERDAY);
                }else if (holder.getDateType() == PTHolderSwitcher.DateTypeGlobal.MORE){
                    setCurrentLoadType(TYPE_LOAD_SALE_MORE);
                }
                mainAdapter = new PointSaleImpl(context, this, holderData);
            }
        }
        //mainAdapter.loadData(holder);
    }

    public PointMainPurchaseAdapter(PointMainActivity context, HolderData holderData) {
        super(context, holderData);
    }
}
