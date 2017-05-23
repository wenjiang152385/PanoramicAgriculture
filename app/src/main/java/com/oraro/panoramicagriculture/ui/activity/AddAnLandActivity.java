package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.AddAnLandPresenter;

/**
 * Created by Administrator on 2017/3/21 0021.
 *
 * @author
 */
public class AddAnLandActivity extends BaseActivity<AddAnLandPresenter> {

    private TextView tv_plant;
    private TextView tv_harvest;

    @Override
    public AddAnLandPresenter createPresenter() {
        return new AddAnLandPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_my_land);
        tv_plant = (TextView) findViewById(R.id.tv_plant);
        tv_harvest = (TextView) findViewById(R.id.tv_harvest);
        tv_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AddAnLandActivity.this,EditPlantActivity.class);
                  startActivity(intent);
            }
        });
    }
}
