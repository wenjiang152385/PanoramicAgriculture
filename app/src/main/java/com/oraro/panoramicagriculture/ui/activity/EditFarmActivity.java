package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.oraro.citypickerview.widget.wheel.WheelView;
import com.oraro.citypickerview.widget.wheel.adapters.ArrayWheelAdapter;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.CropsDao;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.Crops;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.presenter.EditFarmPresenter;
import com.oraro.panoramicagriculture.ui.adapter.ArrayListAdapter;
import com.oraro.panoramicagriculture.ui.listener.ArrayListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 *
 * @author
 */
public class EditFarmActivity extends BaseActivity<EditFarmPresenter> implements View.OnClickListener {

    private EditText editText_crop;
    private TextView edit_date;
    private String timer;
    private EditText edit_allkg;
    private String price;
    private String allkg;
    private RadioButton radioday;
    private RadioButton radiomonth;
    private RadioButton radioseason;
    private RadioGroup radioGroup;
    int date;
    private long timer1;
    private long anAcreLandId;
    private Button button;
    private TextView tv_title;
    private FrameLayout frameLayout1;
    private Bundle extras;
    private ArrayList<String> names;
    private Integer id;
    private ArrayList<Integer> ids;
    private Long userId;
    private EditText edit_price;
    private com.wx.wheelview.widget.WheelView wheelView;
    private long farmid;
    private long fieldId;
    private TextView tv_field;
    private TextView tv_crops;
    private TextView tv_expectHarvestNum;
    private TextView tv_plantNum;
    private TextView tv_planttime;
    private TextView tv_hatvesttime;
    private String vfname;
    private String farmfield;
    private long plantNum;
    private long expectHarvestNum;
    private String currentSowTime;
    private String currentMatureTime;

    @Override
    public EditFarmPresenter createPresenter() {
        return new EditFarmPresenter();
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
        setContentView(R.layout.activity_edit_farm);
        Intent intent = getIntent();
        farmid = intent.getLongExtra("farmid", 0);
        fieldId = intent.getLongExtra("fieldId", 0);
        vfname = intent.getStringExtra("vfname");
        farmfield = intent.getStringExtra("farmfield");
        plantNum = intent.getLongExtra("plantNum", 0);
        expectHarvestNum = intent.getLongExtra("expectHarvestNum", 0);
        currentSowTime = intent.getStringExtra("currentSowTime");
        currentMatureTime = intent.getStringExtra("currentMatureTime");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("收获信息");
        //editText_crop = (EditText) findViewById(R.id.edit_crop);
        //editText_crop.setOnClickListener(this);
//        edit_date = (TextView) findViewById(R.id.edit_date);
//        edit_date.setOnClickListener(this);
//        edit_price = (EditText) findViewById(R.id.edit_price);
        button = (Button) findViewById(R.id.bt_save);
        button.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title_bar);
        tv_title.setText(R.string.harvest_info);
        frameLayout1 = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        frameLayout1.setOnClickListener(this);
        edit_allkg = (EditText) findViewById(R.id.edit_allkg);
        tv_field = (TextView) findViewById(R.id.id_fieldname);
        tv_crops = (TextView) findViewById(R.id.id_vfcropname);
        tv_expectHarvestNum = (TextView) findViewById(R.id.expectHarvestNum);
        tv_plantNum = (TextView) findViewById(R.id.plantNum);
        tv_planttime = (TextView) findViewById(R.id.plantTime);
        tv_hatvesttime = (TextView) findViewById(R.id.harvestTime);
        tv_field.setText(farmfield);
        tv_crops.setText(vfname);
        tv_expectHarvestNum.setText( expectHarvestNum+"");
        tv_plantNum.setText( plantNum+"");
        tv_planttime.setText(currentSowTime);
        tv_hatvesttime.setText(currentMatureTime);

        //initWheel2();
//        List<String> mList = new ArrayList<>();
//        for(int i = 0;i < 1;i++){
//            mList.add("选择农作物");
//        }
//
//        com.wx.wheelview.widget.WheelView wheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.wheelview);
//
//      ArrayListAdapter   arrayWheelAdapter=new ArrayListAdapter(getUi());
//        wheelView.setWheelAdapter(arrayWheelAdapter); // 文本数据源
//        wheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Common); // common皮肤
//        wheelView.setWheelData(mList);// 数据集合
//        arrayWheelAdapter.setOnItem(new ArrayListener() {
//            @Override
//            public void setOnClickLeftItem(View view, int position) {
//                Intent intent1 = new Intent(EditFarmActivity.this, CropsActivity.class);
//
//                startActivityForResult(intent1, 1);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //  inflater.inflate(R.menu.cropsresults, menu);
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
                break;
//            case R.id.edit_crop:
//                Intent intent1 = new Intent(EditFarmActivity.this, CropsActivity.class);
//
//                startActivityForResult(intent1, 1);
//                break;
//            case R.id.edit_date:
//                Intent intent2 = new Intent(EditFarmActivity.this, DateTimerActivity.class);
//                startActivityForResult(intent2, 2);
//                break;
            case R.id.bt_save:

                if ( edit_allkg == null
                        ) {
                    Toast.makeText(EditFarmActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                allkg = edit_allkg.getText().toString();
                if ( allkg.isEmpty()) {
                    Toast.makeText(EditFarmActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                FarmEntity farmEntity = PanoramicAgricultureApplication.getInstances().getDaoSession().getFarmEntityDao().load(farmid);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("actualHarvestNum", allkg);
                jsonObject.addProperty("farmFieldId",fieldId);
                jsonObject.addProperty("farmId",farmid);
                jsonObject.addProperty("userId",farmEntity.getUserId());
                getPresenter().saveFarmResult("addFarmFieldHarvest", jsonObject);


                break;
        }
    }
      public  List<Integer> getids(){
             return ids;
      }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("jw", "requestCode=" + requestCode + "---resultCode=" + resultCode);
//        if (requestCode == 2 && resultCode == RESULT_OK) {
//            timer = data.getStringExtra("timer2");
//            timer1 = data.getLongExtra("timer1", 0);
//            edit_date.setText(timer);
//        }
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//
//            extras = data.getExtras();
//            Log.e("jjww","extras=="+ extras);
//             names = extras.getStringArrayList("names");
////            for (int i = 0; i <names.size() ; i++) {
////
////                if(i>0 && i < names.size()) {
////                    name+=",";
////                }
////                name = name + names.get(i);
////
////            }
//
//
//            ids = extras.getIntegerArrayList("ids");
//            for (int i = 0; i < ids.size() ; i++) {
//                id = ids.get(i);
//                Log.e("vvv","id=="+id);
//            }
//            wheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.wheelview);
//            wheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(getUi())); // 文本数据源
//            wheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Common); // common皮肤
//            wheelView.setLoop(true);
//            wheelView.setWheelData(names);// 数据集合
//
//
//        }
//    }

}
