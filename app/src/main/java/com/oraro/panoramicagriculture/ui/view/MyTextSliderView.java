package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017.04.21.
 */

public class MyTextSliderView extends TextSliderView {

    private String mUrl = "";

    public MyTextSliderView(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_textslider_view,null);
        SimpleDraweeView mSimpleDraweeView = (SimpleDraweeView) v.findViewById(R.id.my_textslider_img);
        GenericDraweeHierarchy hierarchy = mSimpleDraweeView.getHierarchy();
        hierarchy.setPlaceholderImage(R.drawable.bg_ptholder0_default);
        hierarchy.setFailureImage(R.drawable.bg_ptholder0_default);

        mSimpleDraweeView.setImageURI(mUrl);

        return v;
    }
}
