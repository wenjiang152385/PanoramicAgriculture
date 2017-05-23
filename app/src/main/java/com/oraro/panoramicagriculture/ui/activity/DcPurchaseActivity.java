package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Build;
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
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.presenter.DcPurchasePresenter;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017/5/4 0004.
 *
 * @author
 */
public class DcPurchaseActivity extends BaseActivity<DcPurchasePresenter> implements View.OnClickListener {
    private TextView add_date_select;
    private EditText edit_allkg_select;
    private String timer;
    private long purchasePointId;
    private FrameLayout frameLayout1;
    private FrameLayout frameLayout2;
    private TextView tv_title;
    private Button button_save;
    private TextView id_cropname;
    private String names;
    private long ids;
    private long pointId;
    private String pointname;
    private TextView add_dc_select;

    @Override
    public DcPurchasePresenter createPresenter() {
        return new DcPurchasePresenter();
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
        setContentView(R.layout.activity_add_select);
        Intent intent = getIntent();
        purchasePointId = intent.getLongExtra("pointId", 0);
        Log.e("jw", "purchasePointId==" + purchasePointId);
        frameLayout1 = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        frameLayout2 = (FrameLayout) findViewById(R.id.fl_title_bar_right);
        tv_title = (TextView) findViewById(R.id.tv_title_bar);
        frameLayout1.setOnClickListener(this);
        button_save = (Button) findViewById(R.id.bt_save);
        add_dc_select = (TextView) findViewById(R.id.add_dc_select);
        add_dc_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DcPurchaseActivity.this, PurchaseDCActivity.class);
                startActivityForResult(intent, 4);
            }
        });
        button_save.setOnClickListener(this);
        tv_title.setText("新增采购需求");
        add_date_select = (TextView) findViewById(R.id.add_date_select);
        add_date_select.setOnClickListener(this);
        edit_allkg_select = (EditText) findViewById(R.id.edit_allkg_select);

        id_cropname = (TextView)findViewById(R.id.id_cropname);
        id_cropname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DcPurchaseActivity.this, ColzaListActivity.class);
                startActivityForResult(intent1, 3);
            }
        });
//        ids = new ArrayList<>();
//        names = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.addselect_info, menu);
        return super.onCreateOptionsMenu(menu);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_bar_left:
                finish();
                LogUtils.e("yjd finish");
                break;
//            case R.id.edit_crop_select:
//                Intent intent1 = new Intent(EditSelectActivity.this, CropsActivity.class);
//
//                startActivityForResult(intent1, 3);
//                break;
            case R.id.add_date_select:
                Intent intent2 = new Intent(DcPurchaseActivity.this, DateTimerActivity.class);
                startActivityForResult(intent2, 2);
                break;

            case R.id.bt_save:
                if (ids<=0){
                    Toast.makeText(DcPurchaseActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (edit_allkg_select == null || names == null ||add_dc_select==null ||add_date_select==null
                        ) {
                    Toast.makeText(DcPurchaseActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                String allkg = edit_allkg_select.getText().toString();
                if (allkg.isEmpty()) {
                    Toast.makeText(DcPurchaseActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
//                PurchasingPointDao purchasingPointDao = PanoramicAgricultureApplication.getInstances().getDaoSession().getPurchasingPointDao();
//                PurchasingPoint purchasingPoint = purchasingPointDao.load(purchasePointId);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("expectneedsNum", allkg);
                jsonObject.addProperty("vfcropsId", ids);
                jsonObject.addProperty("date", timer);
                jsonObject.addProperty("dcid", purchasePointId);
                jsonObject.addProperty("farmId",pointId);
               // jsonObject.addProperty("userId", purchasingPoint.getUserId());
                getPresenter().addDCPurchaseData("addDCPurchaseData", jsonObject);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("jjj", "requestCode1==" + requestCode+"data=="+data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            timer = data.getStringExtra("timer2");
            add_date_select.setText(timer);
        }
        if (requestCode==4&&resultCode==RESULT_OK){
            pointId = data.getLongExtra("pointdcId", 0);

            pointname = data.getStringExtra("pointname");
            LogUtils.e("jw","pointdcid=="+pointId+"pointname=="+pointname);
            add_dc_select.setText(pointname);

        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            names = data.getStringExtra("names");
            ids = data.getLongExtra("ids", 0);
            id_cropname.setText(names);
//
//            extras = data.getExtras();
//            Log.e("jjww", "extras==" + extras);
//            names = extras.getStringArrayList("names");
////            for (int i = 0; i <names.size() ; i++) {
////
////                if(i>0 && i < names.size()) {
////                    name+=",";
////                }
////                name = name + names.get(i);
////
////            }
//            ids = extras.getIntegerArrayList("ids");
//            if (names.size() > 0 && ids.size() > 0){
//                id_cropname.setText(names.get(0));
//            }
        }
    }
}
