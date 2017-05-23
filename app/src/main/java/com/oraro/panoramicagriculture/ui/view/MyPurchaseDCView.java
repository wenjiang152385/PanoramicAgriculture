package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.R;

/**
 * Created by Administrator on 2017/5/8 0008.
 *
 * @author
 */
public class MyPurchaseDCView extends LinearLayout {
    private Context mContext;
    public LinearLayout id_bottomlayout;
    public LinearLayout id_toplayout;
    public TextView btn_detail_cancel;
    public TextView btn_detail_editneed;
    public TextView btn_detail_edit;
    public TextView id_total;
    public TextView id_tdbh;
    public TextView position;
    public SimpleDraweeView cropsimage;
    public TextView vfCrops_byname;
    public TextView num1_type;
    public TextView num1;
    public TextView num2_type;
    public TextView num2;
    public TextView time1_type;
    public TextView time1;
    public TextView time2_type;
    public TextView time2;

    public MyPurchaseDCView(Context context) {
        super(context);
        mContext=context;
        initView();
    }

    public MyPurchaseDCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    private void initView() {
        View itemview = View.inflate(mContext, R.layout.item_my_dcpurchase_detail_view, this);
        id_bottomlayout = (LinearLayout) itemview.findViewById(R.id.id_bottomlayout);
        id_toplayout = (LinearLayout) itemview.findViewById(R.id.id_toplayout);
        btn_detail_cancel = (TextView)itemview.findViewById(R.id.btn_detail_cancel);
        btn_detail_editneed = (TextView)itemview.findViewById(R.id.btn_detail_editneed);
        btn_detail_edit = (TextView)itemview.findViewById(R.id.btn_detail_edit);
        id_total = (TextView)itemview.findViewById(R.id.id_total);
        id_tdbh = (TextView)itemview.findViewById(R.id.id_tdbh);
        position = (TextView)itemview.findViewById(R.id.position);
        vfCrops_byname = (TextView)itemview.findViewById(R.id.vfCrops_byname);
        num1_type = (TextView)itemview.findViewById(R.id.num1_type);
        num1 = (TextView)itemview.findViewById(R.id.num1);
        num2_type = (TextView)itemview.findViewById(R.id.num2_type);
        num2 = (TextView)itemview.findViewById(R.id.num2);
        time1_type = (TextView)itemview.findViewById(R.id.time1_type);
        time1 = (TextView)itemview.findViewById(R.id.time1);
//        time2_type = (TextView)itemview.findViewById(R.id.time2_type);
//        time2_type = (TextView)itemview.findViewById(R.id.time2_type);
//        time2 = (TextView)itemview.findViewById(R.id.time2);
        cropsimage = (SimpleDraweeView)itemview.findViewById(R.id.cropsimage);
    }



}
