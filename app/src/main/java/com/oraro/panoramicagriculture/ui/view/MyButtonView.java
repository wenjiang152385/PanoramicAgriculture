package com.oraro.panoramicagriculture.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;

/**
 * Created by admin on 2017/4/17.
 */
public class MyButtonView extends LinearLayout{

    private TextView id_button;
    private LinearLayout id_gradient;

    public MyButtonView(Context context) {
        super(context);
        initView(context);
    }

    public MyButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    protected void initView(Context context){
        View itemView = View.inflate(context, R.layout.my_button_layout, this);
        id_button = (TextView)itemView.findViewById(R.id.id_button);
        id_gradient = (LinearLayout)itemView.findViewById(R.id.id_gradient);
    }

    public void SetText(String s){
        id_button.setText(s);
    }
    public void setHot(boolean hot){
        if (hot) {
            id_button.setTextColor(Color.parseColor("#FF20CEB4"));
            id_gradient.setVisibility(VISIBLE);
        }else {
            id_button.setTextColor(Color.parseColor("#FF181818"));
            id_gradient.setVisibility(INVISIBLE);
        }

    }

}
