package com.oraro.panoramicagriculture.ui.activity;

import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.sp.Preferences;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class SettingsActivity extends BaseActivity {

    private FrameLayout mFlTitleBarLeft;

    private TextView mTvTitleBar;

    private RadioGroup mShowIconRadioGroup;
    private RadioButton mShowAllIconRadioButton;
    private RadioButton mShowFarmIconRadioButton;
    private RadioButton mShowPurchaserIconRadioButton;

    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_settings);

        mFlTitleBarLeft = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        mTvTitleBar = (TextView) findViewById(R.id.tv_title_bar);

        mShowIconRadioGroup = (RadioGroup) findViewById(R.id.settings_show_icon_rg);
        mShowAllIconRadioButton = (RadioButton) findViewById(R.id.settings_show_all_icon_rbtn);
        mShowFarmIconRadioButton = (RadioButton) findViewById(R.id.settings_show_farm_icon_rbtn);
        mShowPurchaserIconRadioButton = (RadioButton) findViewById(R.id.settings_show_purchaser_icon_rbtn);
        initShowIconRadioButton();

        mFlTitleBarLeft.setOnClickListener(mOnClickListener);
        mTvTitleBar.setText(getResources().getString(R.string.setting));

        mShowIconRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private void initShowIconRadioButton() {
        switch (Preferences.getInstance(this).getShowIconOnMapFlag()){
            case Constants.SETTINGS_SHOW_ALL_ICON_FLAG:
                mShowAllIconRadioButton.setChecked(true);
                break;
            case Constants.SETTINGS_SHOW_FARM_ICON_FLAG:
                mShowFarmIconRadioButton.setChecked(true);
                break;
            case Constants.SETTINGS_SHOW_PURCHERSER_ICON_FLAG:
                mShowPurchaserIconRadioButton.setChecked(true);
                break;
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fl_title_bar_left:
                    finish();
                break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.settings_show_all_icon_rbtn:
                    Preferences.getInstance(getUi()).setShowIconOnMapFlag(Constants.SETTINGS_SHOW_ALL_ICON_FLAG);
                    break;
                case R.id.settings_show_farm_icon_rbtn:
                    Preferences.getInstance(getUi()).setShowIconOnMapFlag(Constants.SETTINGS_SHOW_FARM_ICON_FLAG);
                    break;
                case R.id.settings_show_purchaser_icon_rbtn:
                    Preferences.getInstance(getUi()).setShowIconOnMapFlag(Constants.SETTINGS_SHOW_PURCHERSER_ICON_FLAG);
                    break;
            }
        }
    };
}
