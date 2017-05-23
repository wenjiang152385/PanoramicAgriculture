package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.EditPurchaseInfoPresenter;

/**
 * Created by Administrator on 2017/3/15 0015.
 *
 * @author
 */
public class EditPurchaseInfoActivity extends BaseActivity<EditPurchaseInfoPresenter> implements View.OnClickListener {

    private TextView tv_title_bar;
    private FrameLayout frameLayout_left;
    private TextView tv_purchase_crop;
    private EditText edit_fact_select;
    private TextView data_select;
    private Button button_save;
    private long id;
    private String neednum;

    @Override
    public EditPurchaseInfoPresenter createPresenter() {
        return new EditPurchaseInfoPresenter();
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
        setContentView(R.layout.edit_purchase_info);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setText("实际采购信息");
        frameLayout_left = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        frameLayout_left.setOnClickListener(this);
        tv_purchase_crop = (TextView)findViewById(R.id.tv_purchase_crop);
        edit_fact_select = (EditText) findViewById(R.id.edit_fact_select);
        data_select = (TextView) findViewById(R.id.add_date_select);
        button_save = (Button) findViewById(R.id.bt_save);
        button_save.setOnClickListener(this);

          Intent intent= getIntent();
        String name = intent.getStringExtra("name");
        String actualNum = intent.getStringExtra("ActualNum");
        String data = intent.getStringExtra("data");
        neednum = intent.getStringExtra("neednum");
        id = intent.getLongExtra("id", 0);
        tv_purchase_crop.setText(name);
         edit_fact_select.setText(actualNum);
        data_select.setText(data);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fl_title_bar_left:
              finish();
             break;
            case R.id.bt_save:
                    if (edit_fact_select==null){
                        Toast.makeText(EditPurchaseInfoActivity.this,"实际采购量不能为空",Toast.LENGTH_SHORT).show();
                        break;
                    }

                    String edit_fact= edit_fact_select.getText().toString().trim();
                if (edit_fact.isEmpty()){
                    Toast.makeText(EditPurchaseInfoActivity.this,"实际采购量不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                JsonObject jsonObject = new JsonObject();
                 jsonObject.addProperty("purchaseNeedsId",id);
                jsonObject.addProperty("actualNum",edit_fact);
                jsonObject.addProperty("needsNum", neednum);
                 getPresenter().purchaseNeedsUpdate("purchaseNeedsUpdate",jsonObject);

                break;
        }


    }
}
