package com.oraro.panoramicagriculture.ui.activity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.PushMessageJson;
import com.oraro.panoramicagriculture.presenter.MessagePresenter;

/**
 * Created by Administrator on 2017/5/16.
 */
public class MessageActivity extends BaseActivity<MessagePresenter> {
    private TextView pointNameText;
    private TextView vfCropsName;
    private TextView vfCropsNum;
    private TextView purchaseDataNeedTime;

    @Override
    public MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pointNameText = (TextView) findViewById(R.id.tv_point_name);
        vfCropsName = (TextView) findViewById(R.id.tv_vfcrops_name);
        vfCropsNum = (TextView) findViewById(R.id.tv_vfcrops_num);
        purchaseDataNeedTime = (TextView) findViewById(R.id.tv_purchase_data_need_time);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        PushMessageJson pushMessageJson = gson.fromJson(getIntent().getStringExtra("gson"), PushMessageJson.class);

        Log.i("sysout", "sdfsf..." + pushMessageJson.getMessagecontent());
        setTitle(pushMessageJson.getMessagetitle());
        pointNameText.setText(pushMessageJson.getFarm().getFarmName());
//        vfCropsImage.setImageURI(pushMessageJson.getDcPurchaseData().getVFcrops().getPath1());
        vfCropsName.setText(pushMessageJson.getDcPurchaseData().getVFcrops().getVfname());
        vfCropsNum.setText(String.valueOf(pushMessageJson.getDcPurchaseData().getExpectneedsNum()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
