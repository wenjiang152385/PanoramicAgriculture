package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.ui.activity.PointInfoActivity;
import com.oraro.panoramicagriculture.utils.CommonUtils;

/**
 * Created by admin on 2017/4/7.
 */
public class PTHolder1 extends PTHolder{
    private LinearLayout id_dismiss;
    private LinearLayout id_height;
    private LinearLayout id_info_layout;

    private TextView id_city;
    private TextView id_pointname;

    private TextView id_main_title;
    private TextView id_info;

    private int g_height;

    public PTHolder1(View itemView) {
        super(itemView);

        id_height = (LinearLayout)itemView.findViewById(R.id.id_height);
        id_dismiss = (LinearLayout)itemView.findViewById(R.id.id_dismiss);
        id_info_layout = (LinearLayout)itemView.findViewById(R.id.id_info_layout);
        id_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo2();
            }
        });

        id_main_title = (TextView)itemView.findViewById(R.id.id_main_title);
        id_city = (TextView)itemView.findViewById(R.id.id_city);
        id_pointname = (TextView)itemView.findViewById(R.id.id_pointname);
        id_pointname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PointInfoActivity.class);
                intent.putExtra("type",holderData.type);
                intent.putExtra("id",holderData.pointId);
                intent.putExtra("name",holderData.name);
                context.startActivity(intent);
            }
        });
        id_info = (TextView)itemView.findViewById(R.id.id_info);
    }


    @Override
    protected void doInit() {
        super.doInit();
        refreshPoint();
    }


    protected void refreshPoint(){
        id_main_title.setText(holderData.title);
        id_pointname.setText(holderData.name);
        id_city.setText(holderData.city);
        id_info.setText(holderData.info);
    }
    private void showInfo2(){
        g_height = g_height == 0?id_height.getHeight():g_height;
        if (id_dismiss.getVisibility() == View.GONE) {
            id_dismiss.setVisibility(View.VISIBLE);
            id_height.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, g_height));
        } else {
            id_dismiss.setVisibility(View.GONE);
            int height = getLineHeight(id_info.getText().toString());
            id_height.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        }
    }
    private int getLineHeight(String string){
        Rect rect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(id_info.getTextSize());
        paint.getTextBounds(string, 0, string.length(), rect);
        DisplayMetrics displayMetrics = CommonUtils.getDisplayMetrics(context);
        //80 padding 宽度
        int width = displayMetrics.widthPixels - CommonUtils.dip2px(context, 80);
        double row = Math.ceil(rect.width() * 1.0 / width);
        row = row - 3;
        //LogUtils.e("yjd row =" + row + "   " + rect.width() + "    " + width + "   " + pointImpl.mInfo.getWidth());

        return (int)(rect.height() * row + g_height);
    }
}