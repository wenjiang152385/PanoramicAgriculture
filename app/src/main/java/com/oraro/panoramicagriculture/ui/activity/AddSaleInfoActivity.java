package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.AddSaleInfoPresenter;
import com.oraro.panoramicagriculture.presenter.EditSelectPresenter;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 *
 * @author
 */
public class AddSaleInfoActivity extends BaseActivity<AddSaleInfoPresenter> implements View.OnClickListener {
    public static final int DATE_REQUEST_CODE = 2;
    public static final int CROPS_REQUEST_CODE = 3;

    private FrameLayout mTitleLeftFl;
    private TextView mTitleTv;

    private TextView mAddSaleInfoCropsnameTv;
    private EditText mAddSaleInfoCountEt;
    private TextView mAddSaleInfoDateTv;
    private Button mAddSaleInfoSaveBtn;

    private String timer;

    private Bundle extras;

    private long purchasePointId;
    private Long userId;
    private String names;
    private long ids;

//    private ArrayList<String> names;
//    private ArrayList<Integer> ids;


    @Override
    public AddSaleInfoPresenter createPresenter() {
        return new AddSaleInfoPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        LogUtils.e("yjd initView");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_add_sale_info);

        Intent intent = getIntent();
        purchasePointId = intent.getLongExtra("purchasePointId", 0);

        userId = PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId();

        mTitleLeftFl = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        mTitleLeftFl.setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.tv_title_bar);
        mTitleTv.setText("新增销售");

        mAddSaleInfoCropsnameTv = (TextView)findViewById(R.id.add_sale_info_cropsname_tv);
        mAddSaleInfoCropsnameTv.setOnClickListener(this);

        mAddSaleInfoCountEt = (EditText) findViewById(R.id.add_sale_info_count_et);

        mAddSaleInfoDateTv = (TextView) findViewById(R.id.add_sale_info_date_tv);
        mAddSaleInfoDateTv.setOnClickListener(this);

        mAddSaleInfoSaveBtn = (Button) findViewById(R.id.add_sale_info_save_btn);
        mAddSaleInfoSaveBtn.setOnClickListener(this);

//        ids = new ArrayList<>();
//        names = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_bar_left:
                finish();
                LogUtils.e("yjd finish");
                break;
            case R.id.add_sale_info_cropsname_tv:
                Intent intent1 = new Intent(AddSaleInfoActivity.this, ColzaListActivity.class);
                startActivityForResult(intent1, CROPS_REQUEST_CODE);
                break;
            case R.id.add_sale_info_date_tv:
                Intent intent2 = new Intent(AddSaleInfoActivity.this, DateTimerActivity.class);
                startActivityForResult(intent2, DATE_REQUEST_CODE);
                break;
            case R.id.add_sale_info_save_btn:
                if (ids <= 0){
                    Toast.makeText(AddSaleInfoActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (names == null ) {
                    Toast.makeText(AddSaleInfoActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                String mSaleInfoCount = mAddSaleInfoCountEt.getText().toString();
                if (mSaleInfoCount.isEmpty()) {
                    Toast.makeText(AddSaleInfoActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("saleNum", mSaleInfoCount);
                jsonObject.addProperty("vfcropsId", ids);
                jsonObject.addProperty("date", timer);
                jsonObject.addProperty("purchasingPointId", purchasePointId);
                getPresenter().addSaleInfo("addSalesdata", jsonObject);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATE_REQUEST_CODE && resultCode == RESULT_OK) {
            timer = data.getStringExtra("timer2");
            mAddSaleInfoDateTv.setText(timer);
        }
        if (requestCode == CROPS_REQUEST_CODE && resultCode == RESULT_OK) {
            names = data.getStringExtra("names");
            ids = data.getLongExtra("ids", 0);
            mAddSaleInfoCropsnameTv.setText(names);
        }
    }
}
