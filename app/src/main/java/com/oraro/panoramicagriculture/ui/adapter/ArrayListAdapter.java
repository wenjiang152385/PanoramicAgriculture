package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.oraro.panoramicagriculture.ui.listener.ArrayListener;
import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.wx.wheelview.widget.WheelItem;

/**
 * Created by Administrator on 2017/3/8 0008.
 *
 * @author
 */
public class ArrayListAdapter extends BaseWheelAdapter<String> {
    private  ArrayListener arrayListener;
    private Context mContext;

    public ArrayListAdapter(Context context) {
        this.mContext = context;
    }
    public  void setOnItem(ArrayListener listener){
        arrayListener=listener;
    }

    @Override
    protected View bindView(final int i, View view, ViewGroup viewGroup) {



        if( view == null) {
            view= new WheelItem(this.mContext);
        }

        WheelItem item = (WheelItem) view;
        item.setText((CharSequence)this.mList.get(i));
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hhh","........");
                arrayListener.setOnClickLeftItem(v,i);
            }
        });
        return (View)view;
    }
}
