package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.model.Text;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.AnAcreLand;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.presenter.EditFarmPresenter;
import com.oraro.panoramicagriculture.presenter.EditPlantPresenter;
import com.oraro.panoramicagriculture.ui.adapter.ArrayListAdapter;
import com.oraro.panoramicagriculture.ui.listener.ArrayListener;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.wx.wheelview.widget.WheelView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditPlantActivity extends BaseActivity<EditPlantPresenter> implements View.OnClickListener {

    private final static int rcPlantTime = 1;
    private final static int rcHarvestTime = 2;
    private final static int rcvfcropsId = 3;
    private final static int rcvfcropsId_next = 4;
    private final static int rcHarvestTime_next = 5;

    private FrameLayout frameLayout1;
    private FrameLayout frameLayout2;
    private TextView tv_title;

    private Long anAcreLandId;
    private int position;
    private FarmEntity farmEntity;
    private TextView plantTime;
    private TextView harvestTime;
    private EditText expectHarvestNum;
    private EditText plantNum;
    private Button btnSave;

    private TextView id_cropname;
//    private ArrayList<String> vfnames;
//    private ArrayList<Integer> vfids;

    private TextView id_cropname_next;
//    private ArrayList<String> vfnames_next;
//    private ArrayList<Integer> vfids_next;
    private TextView harvestTime_next;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private Long farmid;
    private String vfnames1;
    private long ids;
    private String vfnames_next1;
    private long vfids_next1;
    private TextView tv_fieldname;
    private String farmfield;

    @Override
    public EditPlantPresenter createPresenter() {
        return new EditPlantPresenter();
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
        setContentView(R.layout.activity_edit_plant);

        frameLayout1 = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        frameLayout2 = (FrameLayout) findViewById(R.id.fl_title_bar_right);
        tv_title = (TextView) findViewById(R.id.tv_title_bar);
        frameLayout1.setOnClickListener(this);
        tv_title.setText("添加种植");
        tv_fieldname = (TextView) findViewById(R.id.id_fieldname);

        plantTime = (TextView)findViewById(R.id.plantTime);
        plantTime.setOnClickListener(this);
        harvestTime = (TextView)findViewById(R.id.harvestTime);
        harvestTime.setOnClickListener(this);
        expectHarvestNum = (EditText) findViewById(R.id.expectHarvestNum);
        plantNum = (EditText)findViewById(R.id.plantNum);
        btnSave = (Button)findViewById(R.id.bt_save);
        btnSave.setOnClickListener(this);

        id_cropname = (TextView) findViewById(R.id.id_cropname);
        id_cropname.setOnClickListener(this);

        id_cropname_next = (TextView) findViewById(R.id.id_cropname_next);
        id_cropname_next.setOnClickListener(this);
        harvestTime_next = (TextView)findViewById(R.id.harvestTime_next);
        harvestTime_next.setOnClickListener(this);

//        vfids = new ArrayList<Integer>();
//        vfnames = new ArrayList<>();
//        vfids_next = new ArrayList<Integer>();
//        vfnames_next = new ArrayList<>();

        setDefault();
        getIncoming();
        tv_fieldname.setText(farmfield);
    }
    private void getIncoming(){
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Log.e("yjd","position=="+position);
        farmid = intent.getLongExtra("farmId", 0);
        anAcreLandId = intent.getLongExtra("anAcreLandId", 0);
        Log.e("yjd","farmId=="+farmid);
        farmfield = intent.getStringExtra("farmfield");
        LogUtils.e("jw","farmfield=="+farmfield);
        farmEntity = PanoramicAgricultureApplication.getInstances().getDaoSession().getFarmEntityDao().load(farmid);
    }
    private void setDefault(){
        Calendar cal   =   Calendar.getInstance();
        plantTime.setText(sf.format(cal.getTime()));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_title_bar_left:
                finish();
                break;
            case R.id.id_cropname:
                Intent intent_1 = new Intent(EditPlantActivity.this, ColzaListActivity.class);
                startActivityForResult(intent_1, rcvfcropsId);
                break;
            case R.id.id_cropname_next:
                Intent intent_2 = new Intent(EditPlantActivity.this, ColzaListActivity.class);
                startActivityForResult(intent_2, rcvfcropsId_next);
                break;
            case R.id.plantTime:
                Intent intent1 = new Intent(this, DateTimerActivity.class);
                startActivityForResult(intent1, rcPlantTime);
                break;
            case R.id.harvestTime:
                Intent intent2 = new Intent(this, DateTimerActivity.class);
                startActivityForResult(intent2, rcHarvestTime);
                break;
            case R.id.harvestTime_next:
                Intent intent3 = new Intent(this, DateTimerActivity.class);
                startActivityForResult(intent3, rcHarvestTime_next);
                break;

            case R.id.bt_save:
                if (ids<=0 || vfids_next1<=0) {
                    Toast.makeText(this, "农作物不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                if ( plantNum.getText().toString().isEmpty() || expectHarvestNum.getText().toString().isEmpty()) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (plantTime.getText().toString().isEmpty() || harvestTime.getText().toString().isEmpty()) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (plantTime.getText().toString().compareTo(harvestTime.getText().toString()) >= 0) {
                    Toast.makeText(this, "不能大于或等于收获时间", Toast.LENGTH_SHORT).show();
                    break;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("currentSowTime", plantTime.getText().toString());
                jsonObject.addProperty("currentMatureTime", harvestTime.getText().toString());
                jsonObject.addProperty("farmId", farmid);
                jsonObject.addProperty("expectHarvestNum", expectHarvestNum.getText().toString());
                jsonObject.addProperty("plantNum", plantNum.getText().toString());
                jsonObject.addProperty("actualHarvestNum", 0);
                jsonObject.addProperty("state", 1);
                int totalday = 0;
                int plantday = 0;
                try{
                    totalday = CommonUtils.getBetweenDays(plantTime.getText().toString(), harvestTime.getText().toString());
                    plantday = CommonUtils.getBetweenDays(harvestTime.getText().toString(), sf.format(Calendar.getInstance().getTime()));
                }catch (ParseException e){
                    Log.e("date parse error", e.toString());
                }
                jsonObject.addProperty("totalday", totalday);
                jsonObject.addProperty("plantday", plantday);
                jsonObject.addProperty("currentVFcropsId", ids);
                jsonObject.addProperty("nextVFcropsId", vfids_next1);
                jsonObject.addProperty("nextSowTime", harvestTime_next.getText().toString());
                if (0 != 0){
                    LogUtils.e("yjd" + anAcreLandId + "plantAnAcreLandInfo");
                    jsonObject.addProperty("fieldId", anAcreLandId);
                    getPresenter().addAnAcreLandInfo("plantAnAcreLandInfo", jsonObject);
                }else {
                    LogUtils.e("yjd" + anAcreLandId + "plantFarmField");
                    jsonObject.addProperty("fieldId", anAcreLandId);
                    getPresenter().addAnAcreLandInfo("plantFarmField", jsonObject);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == rcPlantTime && resultCode == RESULT_OK) {
            String timer = data.getStringExtra("timer2");
            plantTime.setText(timer);
        }
        else if (requestCode == rcHarvestTime && resultCode == RESULT_OK) {
            String timer = data.getStringExtra("timer2");
            harvestTime.setText(timer);
        }
        else if (requestCode == rcvfcropsId && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            vfnames.clear();
//            vfids.clear();
//            vfnames = extras.getStringArrayList("names");
//            vfids = extras.getIntegerArrayList("ids");
//            if (vfids.size() > 0 && vfnames.size() > 0) {
//                id_cropname.setText(vfnames.get(0));
//            }
            vfnames1 = data.getStringExtra("names");
            ids = data.getLongExtra("ids", 0);
            id_cropname.setText(vfnames1);
        }
        else if (requestCode == rcvfcropsId_next && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            vfnames_next.clear();
//            vfids_next.clear();
//            vfnames_next = extras.getStringArrayList("names");
//            vfids_next = extras.getIntegerArrayList("ids");
//            if (vfids_next.size() > 0 && vfnames_next.size() > 0) {
//                id_cropname_next.setText(vfnames_next.get(0));
//            }
            vfnames_next1 = data.getStringExtra("names");
            vfids_next1 = data.getLongExtra("ids", 0);
            id_cropname_next.setText(vfnames_next1);
        }
        else if (requestCode == rcHarvestTime_next && resultCode == RESULT_OK) {
            String timer = data.getStringExtra("timer2");
            harvestTime_next.setText(timer);
        }
    }
}
