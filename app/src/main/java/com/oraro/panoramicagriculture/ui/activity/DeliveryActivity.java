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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.DCEntityDao;
import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.presenter.DeliveryPresenter;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.greenrobot.greendao.annotation.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/5 0005.
 *
 * @author
 */
public class DeliveryActivity extends BaseActivity<DeliveryPresenter> implements View.OnClickListener {

    private FrameLayout frameLayout1;
    private FrameLayout frameLayout2;
    private TextView tv_title;
    private Button button_save;
    private EditText edit_allkg_select;
    private TextView id_cropname;
    private String names;
    private long ids;
    private long purchasePointId;
    private TextView add_order_select;
    private long puchaseid;
    private TextView id_date;
    private Date parse;
    private TextView tv1_name;
    private TextView tv2_name;
    private TextView tv1_num;
    private TextView tv2_num;
    private TextView tv1_date;
    private TextView tv2_date;
    private LinearLayout ll_name;
    private LinearLayout ll_num;
    private LinearLayout ll_date;

    @Override
    public DeliveryPresenter createPresenter() {
        return new DeliveryPresenter();
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
        setContentView(R.layout.add_delivery);
        Intent intent = getIntent();
        purchasePointId = intent.getLongExtra("pointId", 0);
        Log.e("jw", "purchasePointId==" + purchasePointId);
        frameLayout1 = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        frameLayout2 = (FrameLayout) findViewById(R.id.fl_title_bar_right);
        tv_title = (TextView) findViewById(R.id.tv_title_bar);
        frameLayout1.setOnClickListener(this);
        button_save = (Button) findViewById(R.id.bt_save);
        button_save.setOnClickListener(this);
        tv1_name = (TextView) findViewById(R.id.tv1_name);
        tv2_name = (TextView) findViewById(R.id.tv2_name);
        tv1_num = (TextView) findViewById(R.id.tv1_num);
        tv2_num = (TextView) findViewById(R.id.tv2_num);
        tv1_date = (TextView) findViewById(R.id.tv1_date);
        tv2_date = (TextView) findViewById(R.id.tv2_date);
        ll_name = (LinearLayout) findViewById(R.id.ll_name);
        ll_num = (LinearLayout) findViewById(R.id.ll_num);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        tv_title.setText("新增配送单");
        edit_allkg_select = (EditText) findViewById(R.id.edit_allkg_select);

        id_cropname = (TextView) findViewById(R.id.id_cropname);
        id_cropname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DeliveryActivity.this, OrderListActivity.class);
                intent1.putExtra("dcid", purchasePointId);
                startActivityForResult(intent1, 3);
            }
        });
//        id_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(DeliveryActivity.this, DateTimerActivity.class);
//                startActivityForResult(intent1, 1);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_bar_left:
                finish();
                break;
            case R.id.bt_save:
//                if (ids<=0){
//                    Toast.makeText(DeliveryActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
//                    break;
//                }
                if (edit_allkg_select == null
                        ) {
                    Toast.makeText(DeliveryActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                String allkg = edit_allkg_select.getText().toString();
                if (allkg.isEmpty()) {
                    Toast.makeText(DeliveryActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                DCEntityDao dcEntityDao = PanoramicAgricultureApplication.getInstances().getDaoSession().getDCEntityDao();
                DCEntity dcEntity = dcEntityDao.load(purchasePointId);
//                int dcstate = 1;
//                switch (((RadioGroup) findViewById(R.id.radioGroupRole)).getCheckedRadioButtonId()) {
//                    case R.id.radioFarmer:
//                        dcstate = 0;
//                        break;
//                    case R.id.radioBuyer:
//                        dcstate = 1;
//                        break;
//                    case R.id.radioDC:
//                        dcstate = 2;
//                        break;
//                }
//                PurchasingPointDao purchasingPointDao = PanoramicAgricultureApplication.getInstances().getDaoSession().getPurchasingPointDao();
//                PurchasingPoint purchasingPoint = purchasingPointDao.load(purchasePointId);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("num", allkg);
//                jsonObject.addProperty("purchaseNeedsId", ids);
                jsonObject.addProperty("dcId", purchasePointId);
                jsonObject.addProperty("purchaseneedsId", puchaseid);
                getPresenter().addDCdistribution("addDistribution", jsonObject);
                break;
        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("jjj", "requestCode1==" + requestCode + "data==" + data);
//        if (requestCode==1&&resultCode==RESULT_OK){
//            String timer2 = data.getStringExtra("timer2");
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                parse = sdf.parse(timer2);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            id_date.setText(timer2);
//        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            ll_name.setVisibility(View.VISIBLE);
            ll_date.setVisibility(View.VISIBLE);
            ll_num.setVisibility(View.VISIBLE);
            puchaseid = data.getLongExtra("puchaseid", 0);
            String cropname = data.getStringExtra("cropname");
            String neednum = data.getStringExtra("neednum");
            String needdate = data.getStringExtra("needdate");
            id_cropname.setText(puchaseid + "");
            tv2_name.setText(cropname);
            tv2_num.setText(neednum);
            tv2_date.setText(needdate);



//            names = data.getStringExtra("names");
//            ids = data.getLongExtra("ids", 0);
//            id_cropname.setText(names);
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
