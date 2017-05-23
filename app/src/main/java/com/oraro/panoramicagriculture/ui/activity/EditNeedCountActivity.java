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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.presenter.EditNeedCountPresenter;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017/3/17 0017.
 *
 * @author
 */
public class EditNeedCountActivity extends BaseActivity<EditNeedCountPresenter> implements View.OnClickListener {

    public static final String EXTRA_EDIT_NEED_BEAN = "EXTRA_EDIT_NEED_BEAN";

    private TextView tv_crop;
    private EditText edit_need_select;
    private EditText mEditFactSelect;
    private TextView date_select;
    private Button bt_save;

    private PurchaseResults mPurchaseResults;

    @Override
    public EditNeedCountPresenter createPresenter() {
        return new EditNeedCountPresenter();
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
        setContentView(R.layout.edit_need_count);
        TextView  tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setText("编辑需求");
        FrameLayout frameLayout_left = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        frameLayout_left.setOnClickListener(this);
        tv_crop = (TextView) findViewById(R.id.tv_crop);
        edit_need_select = (EditText) findViewById(R.id.edit_need_select);
        mEditFactSelect = (EditText) findViewById(R.id.edit_fact_select);
        date_select = (TextView) findViewById(R.id.date_select);
        bt_save = (Button) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(this);
        Intent intent= getIntent();
        String mNeddEditBean = intent.getStringExtra(EXTRA_EDIT_NEED_BEAN);

        Gson mGson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
        mPurchaseResults = mGson.fromJson(mNeddEditBean,PurchaseResults.class);

        tv_crop.setText(mPurchaseResults.getVfcrops().getByname());
        date_select.setText(CommonUtils.mDotDateFormat.format(mPurchaseResults.getDate()));
        edit_need_select.setText(mPurchaseResults.getNeedsNum().toString());
        mEditFactSelect.setText(mPurchaseResults.getActualNum().toString());

        edit_need_select.setSelection(edit_need_select.getText().length());
        mEditFactSelect.setSelection(mEditFactSelect.getText().length());
        mEditFactSelect.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_title_bar_left:
                finish();
                break;
            case R.id.bt_save:
                String edit_need= edit_need_select.getText().toString().trim();
                if (edit_need.isEmpty()){
                    Toast.makeText(EditNeedCountActivity.this,"预计采购量不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                String actual_need= mEditFactSelect.getText().toString().trim();
                if (actual_need.isEmpty()){
                    Toast.makeText(EditNeedCountActivity.this,"实际采购量不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("purchaseNeedsId",mPurchaseResults.getId());
                jsonObject.addProperty("needsNum",edit_need);
                jsonObject.addProperty("actualNum",actual_need);
                getPresenter().purchaseexpectUpdate("purchaseNeedsUpdate",jsonObject);
                break;

        }

    }
}
