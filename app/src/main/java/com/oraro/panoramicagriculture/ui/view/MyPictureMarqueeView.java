package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.ui.adapter.HolderData;

import java.util.HashMap;

/**
 * Created by admin on 2017/4/14.
 */
public class MyPictureMarqueeView extends FrameLayout{
    private Context mContext = null;
    private SliderLayout mDemoSlider;
    private PagerIndicator id_custom_indicator;
    private HolderData holderData;
    public MyPictureMarqueeView(Context context) {
        super(context);
        mContext = context;
        initView();
    }
    public MyPictureMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }
    private void initView() {
        View itemView = View.inflate(getContext(), R.layout.item_ptholder0, this);
        mDemoSlider = (SliderLayout)itemView.findViewById(R.id.id_slider);
        id_custom_indicator = (PagerIndicator)itemView.findViewById(R.id.id_custom_indicator);
    }

    public void doInit(HolderData holderData){
        this.holderData = holderData;
        initImageSlider();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDemoSlider.stopAutoCycle();
    }

    private void initImageSlider(){
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put(holderData.name + "", Constants.SERVER_ADDRESS + holderData.slider1);
        url_maps.put(holderData.name + " ", Constants.SERVER_ADDRESS + holderData.slider2);
        url_maps.put(holderData.name + "  ", Constants.SERVER_ADDRESS + holderData.slider3);
        url_maps.put(holderData.name + "   ", Constants.SERVER_ADDRESS + holderData.slider4);
        mDemoSlider.removeAllSliders();
        for(String name : url_maps.keySet()){
            // DefaultSliderView sliderView = new DefaultSliderView(this);
            TextSliderView textSliderView = new TextSliderView(mContext);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information 点击图片时可用到
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        // 设置默认过渡效果(可在xml中设置)
        //mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        // 设置默认指示器位置(默认指示器白色,位置在sliderlayout底部)
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // 设置自定义指示器(位置自定义)
        mDemoSlider.setCustomIndicator(id_custom_indicator);
        // 设置TextView自定义动画
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        //mDemoSlider2.setCustomAnimation(new ChildAnimationExample()); // 多种效果，进入该类修改，参考效果github/daimajia/AndroidViewAnimations
        // 设置持续时间
        mDemoSlider.setDuration(2000);
        mDemoSlider.startAutoCycle();
    }
}
