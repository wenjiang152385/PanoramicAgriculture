package com.oraro.panoramicagriculture.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.FieldBorder;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.presenter.NewFarmPresenter;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.PointImageListAdapter;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.MapHelperManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
public class NewFarmActivity extends BaseActivity<NewFarmPresenter> implements View.OnClickListener, BaseListAdapter.OnItemClickListener {
    private final String TAG = this.getClass().getSimpleName();

    private Toolbar toolbar;
    private EditText pointNameEdit;
    private EditText farmAreaEdit;
    private EditText pointAddressEdit;
    private EditText pointBriefEdit;
    private EditText pointPhoneEdit;
    private EditText coverageAreaEdit;
    private TextView titleText;
    private TextView pointNameText;
    private TextView farmAreaText;
    private TextView pointAddressText;
    private TextView cropsKind;
    private TextView briefLengthText;
    private TextView cropSelectText;
    private RelativeLayout pointNameLayout;
    private RelativeLayout farmCropsLayout;
    private LinearLayout farmTotalAreaLayout;
    private RelativeLayout pointAddressLayout;
    private ImageButton quitButton;
    private Button submitButton;
    private Button setFieldButton;
    private RecyclerView pointImageList;
    private LinearLayout coverageAreaLayout;
    private PointImageListAdapter pointImageListAdapter;

    private TextWatcher pointBriefWatcher;
    private TextWatcher farmAreaWathcer;

    private Double latitude;//农场经度
    private Double longitude;//农场纬度
    private String address;//地址
    private String country;//国家
    private String province;//
    private String city;//城市
    private String district;//区县
    private String street;//街道
    private String streetNum;//门牌号
    private String cropName = "";

    private Long pointId;
    private int pointType;
    private FarmEntity farmEntity;
    private PurchasingPoint purchasingPoint;
    private DCEntity dcEntity;
    private DaoSession daoSession;

    List<LatLng> fieldLatLng;
    List<List<LatLng>> borderLatLng;
    private List<HashMap<String, String>> fieldInfos;
    private List<FarmField> farmFieldList = new ArrayList<>();
    private double farmTotalArea = 0;
    private long userId;

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    public static final int REQUEST_CODE_SET_FIELD = 200;

    public static final int POINT_TYPE_FARM = 1;
    public static final int POINT_TYPE_PURCHASE = 2;
    public static final int POINT_TYPE_DC = 3;

    @Override
    public NewFarmPresenter createPresenter() {
        return new NewFarmPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_new_point);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
        farmCropsLayout = (RelativeLayout) findViewById(R.id.farm_crops_layout);
        cropsKind = (TextView) findViewById(R.id.farm_crops_kinds);

        cropSelectText = (TextView) findViewById(R.id.tv_crops_select);
        titleText = (TextView) findViewById(R.id.tv_new_point_title);


        initTextWatcher();
        pointNameEdit = (EditText) findViewById(R.id.et_point_name);
        pointNameText = (TextView) findViewById(R.id.tv_point_name);
        farmAreaEdit = (EditText) findViewById(R.id.et_farm_totalArea);
        farmAreaText = (TextView) findViewById(R.id.tv_farm_totalArea);
        pointAddressEdit = (EditText) findViewById(R.id.et_point_address);
        pointAddressText = (TextView) findViewById(R.id.tv_point_address);
        pointBriefEdit = (EditText) findViewById(R.id.et_point_brief);
        briefLengthText = (TextView) findViewById(R.id.tv_brief_length);
        pointPhoneEdit = (EditText) findViewById(R.id.et_point_phone);
        coverageAreaEdit = (EditText) findViewById(R.id.et_coverage_area);

        coverageAreaLayout = (LinearLayout) findViewById(R.id.dc_coverage_area_layout);
        pointNameLayout = (RelativeLayout) findViewById(R.id.point_name_layout);
        pointAddressLayout = (RelativeLayout) findViewById(R.id.point_address_layout);
        farmTotalAreaLayout = (LinearLayout) findViewById(R.id.ll_totalArea);
        quitButton = (ImageButton) findViewById(R.id.btn_quit);
        submitButton = (Button) findViewById(R.id.btn_submit);
        pointImageList = (RecyclerView) findViewById(R.id.point_image_list);
        setFieldButton = (Button) findViewById(R.id.btn_set_field);
        setFieldButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        pointNameLayout.setOnClickListener(this);
        farmTotalAreaLayout.setOnClickListener(this);
        pointAddressLayout.setOnClickListener(this);
        farmCropsLayout.setOnClickListener(this);

        pointBriefEdit.addTextChangedListener(pointBriefWatcher);
//        farmAreaEdit.addTextChangedListener(farmAreaWathcer);
//        farmAddressEdit.addTextChangedListener(addressWatcher);

        pointImageListAdapter = new PointImageListAdapter(this, BaseListAdapter.ONLY_FOOTER);
        pointImageListAdapter.setOnItemClickListener(this);
        pointImageList.setLayoutManager(new GridLayoutManager(this, 5));
        pointImageList.setHasFixedSize(true);
        pointImageList.setAdapter(pointImageListAdapter);

        pointId = getIntent().getLongExtra("pointId", -1);
        pointType = getIntent().getIntExtra("pointType", -1);
        userId = getIntent().getLongExtra("userId", -1);
        Log.i(TAG, "pointId=" + pointId + ",pointType=" + pointType + ",userId=" + userId);
        if (pointId != -1) {
            if (pointType == POINT_TYPE_PURCHASE) {
                farmTotalAreaLayout.setVisibility(View.GONE);
                titleText.setText(getString(R.string.edit_purchasing_point));
                purchasingPoint = daoSession.getPurchasingPointDao().load(pointId);
                pointNameEdit.setText(purchasingPoint.getPurchasingPointName());
                pointNameEdit.setSelection(pointNameEdit.getText().length());
                pointAddressEdit.setText(purchasingPoint.getAddress());
                pointBriefEdit.setText(purchasingPoint.getPurchasingPointInfo());
                cropsKind.setText(purchasingPoint.getPurchasingPointInfo());
                pointPhoneEdit.setText(purchasingPoint.getPhoneNum());
                List<ImageItem> images = new ArrayList();
                images.add(new ImageItem(purchasingPoint.getSlide1(), -1, "slide1", -1, -1, "network", -1));
                images.add(new ImageItem(purchasingPoint.getSlide2(), -1, "slide2", -1, -1, "network", -1));
                images.add(new ImageItem(purchasingPoint.getSlide3(), 0, "slide3", -1, -1, "network", -1));
                images.add(new ImageItem(purchasingPoint.getSlide4(), 0, "slide4", -1, -1, "network", -1));
                pointImageListAdapter.addItems(images);
            } else if (pointType == POINT_TYPE_FARM) {
                titleText.setText(getString(R.string.edit_farm_point));
                farmEntity = daoSession.getFarmEntityDao().load(pointId);
                pointNameEdit.setText(farmEntity.getFarmName());
                pointNameEdit.setSelection(pointNameEdit.getText().length());
                pointAddressEdit.setText(farmEntity.getAddress());
                farmAreaEdit.setText(farmEntity.getTotalArea().toString());
                pointBriefEdit.setText(farmEntity.getFarmInfo());
                cropsKind.setText(farmEntity.getFarmInfo());
                pointPhoneEdit.setText(farmEntity.getPhoneNum());
                List<ImageItem> images = new ArrayList();
                images.add(new ImageItem(farmEntity.getSlide1(), -1, "slide1", -1, -1, "network", -1));
                images.add(new ImageItem(farmEntity.getSlide2(), -1, "slide2", -1, -1, "network", -1));
                images.add(new ImageItem(farmEntity.getSlide3(), 0, "slide3", -1, -1, "network", -1));
                images.add(new ImageItem(farmEntity.getSlide4(), 0, "slide4", -1, -1, "network", -1));
                pointImageListAdapter.addItems(images);
            } else if (pointType == POINT_TYPE_DC) {
                titleText.setText(getString(R.string.edit_dc_point));
                dcEntity = daoSession.getDCEntityDao().load(pointId);
                pointNameEdit.setText(dcEntity.getDcName());
                pointNameEdit.setSelection(pointNameEdit.getText().length());
                pointAddressEdit.setText(dcEntity.getAddress());
                farmAreaEdit.setText(dcEntity.getTotalArea().toString());
                farmAreaEdit.setEnabled(true);
                setFieldButton.setVisibility(View.GONE);
                pointBriefEdit.setText(dcEntity.getDcInfo());
                cropsKind.setText(dcEntity.getDcInfo());
                pointPhoneEdit.setText(dcEntity.getPhoneNum());
                coverageAreaLayout.setVisibility(View.VISIBLE);
                coverageAreaEdit.setText(String.valueOf(dcEntity.getCoverageArea()));
                List<ImageItem> images = new ArrayList();
                images.add(new ImageItem(dcEntity.getSlide1(), -1, "slide1", -1, -1, "network", -1));
                images.add(new ImageItem(dcEntity.getSlide2(), -1, "slide2", -1, -1, "network", -1));
                images.add(new ImageItem(dcEntity.getSlide3(), 0, "slide3", -1, -1, "network", -1));
                images.add(new ImageItem(dcEntity.getSlide4(), 0, "slide4", -1, -1, "network", -1));
                pointImageListAdapter.addItems(images);
            }
        } else {
            if (pointType == POINT_TYPE_PURCHASE) {
                farmTotalAreaLayout.setVisibility(View.GONE);
//            pointAddressLayout.setHint(getString(R.string.purchasing_point_address));
                titleText.setText(getString(R.string.new_purchasing_point));
            } else if (pointType == POINT_TYPE_DC) {
                farmAreaEdit.setEnabled(true);
                setFieldButton.setVisibility(View.GONE);
                titleText.setText(getString(R.string.new_dc));
                coverageAreaLayout.setVisibility(View.VISIBLE);
            }
            Bundle bundle = getIntent().getExtras();
            Log.i(TAG, "pointid=" + getIntent().getLongExtra("pointId", -1));
            if (bundle != null) {
                latitude = Double.parseDouble(bundle.getString("latitude"));
                longitude = Double.parseDouble(bundle.getString("longitude"));
                province = bundle.getString("province");
                city = bundle.getString("city");
                district = bundle.getString("district");
                address = bundle.getString("address");
                street = bundle.getString("street");
                streetNum = bundle.getString("streetNum");
                pointAddressEdit.setText(address.substring((province + city + district).length()));
            }
        }
        country = "中华人民共和国";
    }

    private void initTextWatcher() {
        pointBriefWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length;
                if (s != null) {
                    length = s.length();
                    briefLengthText.setText(length + "/300");
                    if (length > 300) {
                        briefLengthText.setTextColor(getResources().getColor(R.color.red_light));
                    } else {
                        briefLengthText.setTextColor(getResources().getColor(R.color.green_light));
                    }
                }
            }
        };

        farmAreaWathcer = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s != null && s.length() > 0) {
//                    setFieldButton.setVisibility(View.VISIBLE);
//                } else {
//                    setFieldButton.setVisibility(View.GONE);
//                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.new_farm_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                String farmName = pointNameEdit.getText().toString();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("latitude", latitude);
                jsonObject.addProperty("longitude", longitude);
                jsonObject.addProperty("address", address);
                jsonObject.addProperty("country", country);
                jsonObject.addProperty("province", province);
                jsonObject.addProperty("city", city);
                jsonObject.addProperty("district", district);
                jsonObject.addProperty("street", street);
                jsonObject.addProperty("streetNum", streetNum);
                jsonObject.addProperty("userId", PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId());

                if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_PURCHASER) {
                    jsonObject.addProperty("purchasingPointName", farmName);
                    jsonObject.addProperty("purchasingPointInfo", cropName);
                    getPresenter().newPurchasingPoint("addPurchase", jsonObject);
                } else {
                    jsonObject.addProperty("farmName", farmName);
                    jsonObject.addProperty("farmInfo", cropName);
                    Double totalArea = Double.parseDouble(farmAreaEdit.getText().toString());
                    jsonObject.addProperty("totalArea", totalArea);
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject1 = new JSONObject(gson.toJson(fieldLatLng));
                        jsonObject1.putOpt("fieldLatLng", fieldLatLng);
                        jsonObject1.putOpt("fieldInfos", fieldInfos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getPresenter().newFarm("addFarm", jsonObject);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.point_name_layout:
//                if (pointNameText.isShown()) {
//                    pointNameText.setVisibility(View.GONE);
//                    pointNameEdit.setVisibility(View.VISIBLE);
//                    if (pointNameText.getText() != null) {
//                        pointNameEdit.setText(pointNameText.getText());
//                        pointNameEdit.setSelection(pointNameEdit.getText().length());
//                    }
//                    pointNameEdit.requestFocus();
//                    showIME(pointNameEdit);
//                }
//                if (farmAreaEdit.isShown()) {
//                    farmAreaEdit.setVisibility(View.GONE);
//                    farmAreaText.setVisibility(View.VISIBLE);
//                    if (farmAreaEdit.getText() != null) {
//                        farmAreaText.setText(farmAreaEdit.getText());
//                    } else {
//                        farmAreaText.setText(null);
//                    }
//                }
//                if (pointAddressEdit.isShown()) {
//                    pointAddressEdit.setVisibility(View.GONE);
//                    pointAddressText.setVisibility(View.VISIBLE);
//                    if (pointAddressEdit.getText() != null) {
//                        pointAddressText.setText(pointAddressEdit.getText());
//                    } else {
//                        pointAddressText.setText(null);
//                    }
//                }
                break;
            case R.id.ll_totalArea:
//                if (pointNameEdit.isShown()) {
//                    pointNameEdit.setVisibility(View.GONE);
//                    pointNameText.setVisibility(View.VISIBLE);
//                    if (pointNameEdit.getText() != null) {
//                        pointNameText.setText(pointNameEdit.getText().toString());
//                    } else {
//                        pointNameText.setText(null);
//                    }
//                }
//                if (farmAreaText.isShown()) {
//                    farmAreaText.setVisibility(View.GONE);
//                    farmAreaEdit.setVisibility(View.VISIBLE);
//                    if (farmAreaText.getText() != null) {
//                        farmAreaEdit.setText(farmAreaText.getText());
//                        farmAreaEdit.setSelection(farmAreaEdit.getText().length());
//                    }
//                    farmAreaEdit.requestFocus();
//                    showIME(farmAreaEdit);
//                }
                break;
            case R.id.farm_crops_layout:
                if (pointNameEdit.isShown()) {
                    pointNameEdit.setVisibility(View.GONE);
                    pointNameText.setVisibility(View.VISIBLE);
                    if (pointNameEdit.getText() != null) {
                        pointNameText.setText(pointNameEdit.getText().toString());
                    } else {
                        pointNameText.setText(null);
                    }
                }
                if (farmAreaEdit.isShown()) {
                    farmAreaEdit.setVisibility(View.GONE);
                    farmAreaText.setVisibility(View.VISIBLE);
                    if (farmAreaEdit.getText() != null) {
                        farmAreaText.setText(farmAreaEdit.getText());
                    } else {
                        farmAreaText.setText(null);
                    }
                }
                startActivityForResult(new Intent(this, ColzaListActivity.class), 0);
                break;
            case R.id.point_address_layout:
//                if (pointNameEdit.isShown()) {
//                    pointNameEdit.setVisibility(View.GONE);
//                    pointNameText.setVisibility(View.VISIBLE);
//                    if (pointNameEdit.getText() != null) {
//                        pointNameText.setText(pointNameEdit.getText().toString());
//                    } else {
//                        pointNameText.setText(null);
//                    }
//                }
//                if (farmAreaEdit.isShown()) {
//                    farmAreaEdit.setVisibility(View.GONE);
//                    farmAreaText.setVisibility(View.VISIBLE);
//                    if (farmAreaEdit.getText() != null) {
//                        farmAreaText.setText(farmAreaEdit.getText());
//                    } else {
//                        farmAreaText.setText(null);
//                    }
//                }
//                if (pointAddressText.isShown()) {
//                    pointAddressEdit.setVisibility(View.VISIBLE);
//                    pointAddressText.setVisibility(View.GONE);
//                    if (pointAddressText.getText() != null) {
//                        pointAddressEdit.setText(pointAddressText.getText());
//                        pointAddressEdit.setSelection(pointAddressEdit.getText().length());
//                    }
//                    pointAddressEdit.requestFocus();
//                    showIME(pointAddressEdit);
//                }
                break;
            case R.id.btn_quit:
                NewFarmActivity.this.finish();
                break;
            case R.id.btn_submit:
//                if (pointNameEdit.getText() != null && pointNameEdit.getText().length() > 0) {
//                    pointNameText.setText(pointNameEdit.getText());
//                }
//                if (pointAddressEdit.getText() != null && pointAddressEdit.getText().length() > 0) {
//                    pointAddressText.setText(pointAddressEdit.getText());
//                }
//                if (farmAreaEdit.getText() != null && farmAreaEdit.getText().length() > 0) {
//                    farmAreaText.setText(farmAreaEdit.getText());
//                }
                if (pointBriefEdit != null && pointBriefEdit.getText() != null) {
                    final String brief = pointBriefEdit.getText().toString();
                    if (brief.length() > 300) {
                        CommonUtils.Toast(NewFarmActivity.this, R.string.brief_too_long);
                        break;
                    }
                    showProgress();
                    final String farmName = pointNameEdit.getText().toString();
                    final String phoneNum = pointPhoneEdit.getText().toString();
//                final String cropName = cropsKind.getText().toString();
                    final JsonObject jsonObject = new JsonObject();
                    final Gson gson = new Gson();
                    if (pointId != -1) {
                        address = pointAddressEdit.getText().toString();
                        city = address.substring(address.indexOf("省") + 1, address.indexOf("市") + 1);
                        Log.i(TAG, city + "," + address.substring(address.indexOf("市") + 1));
                        MapHelperManager.getInstance().geocode(NewFarmActivity.this, city, address.substring(address.indexOf("市") + 1), new AMapLocationListener() {
                            @Override
                            public void onLocationChanged(AMapLocation aMapLocation) {
                                Log.i(TAG, city + "," + aMapLocation);
                                if (aMapLocation == null) {
                                    CommonUtils.Toast(NewFarmActivity.this, R.string.incorrect_address_format);
                                    dismissProgress();
                                    return;
                                }
                                latitude = aMapLocation.getLatitude();
                                longitude = aMapLocation.getLongitude();
                                jsonObject.addProperty("latitude", aMapLocation.getLatitude());
                                jsonObject.addProperty("longitude", aMapLocation.getLongitude());
                                jsonObject.addProperty("address", address);
                                jsonObject.addProperty("country", country);
                                jsonObject.addProperty("province", aMapLocation.getProvince());
                                jsonObject.addProperty("city", aMapLocation.getCity());
                                jsonObject.addProperty("district", aMapLocation.getDistrict());
                                jsonObject.addProperty("street", "");
                                jsonObject.addProperty("streetNum", "");
                                jsonObject.addProperty("phoneNum", phoneNum);
                                if (pointType == POINT_TYPE_PURCHASE) {
                                    jsonObject.addProperty("purchasingPointName", farmName);
                                    jsonObject.addProperty("purchasingPointInfo", brief);
                                    jsonObject.addProperty("purchasingPointId", pointId);
                                    getPresenter().purchaseUpdate("purchaseUpdate", jsonObject);
                                    purchasingPoint.setLatitude(aMapLocation.getLatitude());
                                    purchasingPoint.setLongitude(aMapLocation.getLongitude());
                                    purchasingPoint.setAddress(address);
                                    purchasingPoint.setCountry(country);
                                    purchasingPoint.setProvince(aMapLocation.getProvince());
                                    purchasingPoint.setCity(aMapLocation.getCity());
                                    purchasingPoint.setDistrict(aMapLocation.getDistrict());
                                    purchasingPoint.setStreet("");
                                    purchasingPoint.setStreetNum("");
                                    purchasingPoint.setPurchasingPointName(farmName);
                                    purchasingPoint.setPurchasingPointInfo(brief);
                                    purchasingPoint.setPhoneNum(phoneNum);
                                } else if (pointType == POINT_TYPE_FARM) {
                                    jsonObject.addProperty("farmName", farmName);
                                    jsonObject.addProperty("farmInfo", brief);
                                    Double totalArea = Double.parseDouble(farmAreaEdit.getText().toString());
                                    jsonObject.addProperty("totalArea", totalArea);
                                    jsonObject.addProperty("farmId", pointId);

                                    JsonObject jsonObject1 = new JsonObject();
//                                    jsonObject1.addProperty("farmField", gson.toJson(farmFieldList).toString());
                                    jsonObject1.add("farmField", gson.toJsonTree(farmFieldList));

                                    farmEntity.setLatitude(aMapLocation.getLatitude());
                                    farmEntity.setLongitude(aMapLocation.getLongitude());
                                    farmEntity.setAddress(address);
                                    farmEntity.setCountry(country);
                                    farmEntity.setProvince(aMapLocation.getProvince());
                                    farmEntity.setCity(aMapLocation.getCity());
                                    farmEntity.setDistrict(aMapLocation.getDistrict());
                                    farmEntity.setStreet("");
                                    farmEntity.setStreetNum("");
                                    farmEntity.setFarmName(farmName);
                                    farmEntity.setFarmInfo(brief);
                                    farmEntity.setTotalArea(totalArea);
                                    farmEntity.setPhoneNum(phoneNum);
                                    farmEntity.setFarmId(pointId);
                                    jsonObject1.add("farm", gson.toJsonTree(farmEntity));
                                    getPresenter().farmUpdate("farmUpdate", jsonObject1);
                                } else if (pointType == POINT_TYPE_DC) {
                                    dcEntity.setLatitude(latitude);
                                    dcEntity.setLongitude(longitude);
                                    dcEntity.setPhoneNum(phoneNum);
                                    dcEntity.setAddress(address);
                                    dcEntity.setCountry(country);
                                    dcEntity.setProvince(aMapLocation.getProvince());
                                    dcEntity.setCity(city);
                                    dcEntity.setDistrict(aMapLocation.getDistrict());
                                    dcEntity.setStreet("");
                                    dcEntity.setStreetNum("");
                                    dcEntity.setDcName(farmName);
                                    dcEntity.setDcInfo(brief);
                                    dcEntity.setPhoneNum(phoneNum);
                                    dcEntity.setTotalArea(Double.parseDouble(farmAreaEdit.getText().toString()));
                                    dcEntity.setCoverageArea(Double.parseDouble(coverageAreaEdit.getText().toString()));

                                    jsonObject.addProperty("dcName", farmName);
                                    jsonObject.addProperty("totalArea", Double.parseDouble(farmAreaEdit.getText().toString()));
                                    jsonObject.addProperty("coverageArea", Double.parseDouble(coverageAreaEdit.getText().toString()));
                                    jsonObject.addProperty("dcInfo", brief);
                                    jsonObject.addProperty("id", pointId);
                                    getPresenter().DCUpdate("dcUpdate", jsonObject);
                                }
                            }
                        });
                    } else {
                        jsonObject.addProperty("latitude", latitude);
                        jsonObject.addProperty("longitude", longitude);
                        jsonObject.addProperty("address", address);
                        jsonObject.addProperty("country", country);
                        jsonObject.addProperty("province", province);
                        jsonObject.addProperty("city", city);
                        jsonObject.addProperty("district", district);
                        jsonObject.addProperty("street", street);
                        jsonObject.addProperty("streetNum", streetNum);
                        jsonObject.addProperty("userId", userId);
                        jsonObject.addProperty("phoneNum", phoneNum);
                        if (pointType == POINT_TYPE_PURCHASE) {
                            jsonObject.addProperty("purchasingPointName", farmName);
                            jsonObject.addProperty("purchasingPointInfo", brief);
                            getPresenter().newPurchasingPoint("addPurchase", jsonObject);
                        } else if (pointType == POINT_TYPE_FARM) {
                            jsonObject.addProperty("farmName", farmName);
                            jsonObject.addProperty("farmInfo", brief);
                            farmEntity = new FarmEntity();
                            farmEntity.setLatitude(latitude);
                            farmEntity.setLongitude(longitude);
                            farmEntity.setAddress(address);
                            farmEntity.setCountry(country);
                            farmEntity.setProvince(province);
                            farmEntity.setCity(city);
                            farmEntity.setDistrict(district);
                            farmEntity.setStreet("");
                            farmEntity.setStreetNum("");
                            farmEntity.setFarmName(farmName);
                            farmEntity.setFarmInfo(brief);
                            farmEntity.setUserId(userId);
                            Double totalArea = Double.parseDouble(farmAreaEdit.getText().toString());
                            farmEntity.setTotalArea(totalArea);
                            farmEntity.setPhoneNum(phoneNum);
                            jsonObject.addProperty("totalArea", totalArea);
                            JsonObject jsonObject1 = new JsonObject();
                            jsonObject1.add("farm", gson.toJsonTree(farmEntity));
                            jsonObject1.add("farmField", gson.toJsonTree(farmFieldList));
                            getPresenter().newFarm("addFarm", jsonObject1);
                        } else if (pointType == POINT_TYPE_DC) {
                            jsonObject.addProperty("dcName", farmName);
                            jsonObject.addProperty("dcInfo", brief);
                            jsonObject.addProperty("totalArea", Double.parseDouble(farmAreaEdit.getText().toString()));
                            jsonObject.addProperty("coverageArea", Double.parseDouble(coverageAreaEdit.getText().toString()));
                            getPresenter().newDCPoint("addDC", jsonObject);
                        }
                    }
                }
                break;
            case R.id.btn_set_field:
                Intent intent = new Intent(NewFarmActivity.this, FieldActivity.class);
                intent.putExtra("farmId", pointId);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivityForResult(intent, REQUEST_CODE_SET_FIELD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "requestCode=" + requestCode + ",resultCode=" + resultCode);
        if (requestCode == REQUEST_CODE_SET_FIELD && resultCode == RESULT_OK) {
            if (data != null) {
                fieldLatLng = data.getExtras().getParcelableArrayList("fieldLatLng");
                borderLatLng = (List<List<LatLng>>) data.getExtras().getSerializable("borderLatLng");
                fieldInfos = (List<HashMap<String, String>>) data.getExtras().getSerializable("fieldInfos");
                farmTotalArea = data.getDoubleExtra("farmServerArea", 0);
                Log.i(TAG, "farmTotalArea====" + String.valueOf(farmTotalArea));
                for (int i = 0; i < fieldLatLng.size(); i++) {
                    List<FieldBorder> fieldBorderList = new ArrayList<>();
                    FarmField farmField = new FarmField();
                    farmField.setLatitude(fieldLatLng.get(i).latitude);
                    farmField.setLongitude(fieldLatLng.get(i).longitude);
                    farmField.setFieldName(fieldInfos.get(i).get("fieldName"));
                    farmField.setFieldArea(Double.parseDouble(fieldInfos.get(i).get("fieldArea")));
                    farmTotalArea += Double.parseDouble(fieldInfos.get(i).get("fieldArea"));
                    for (int j = 0; j < borderLatLng.get(i).size(); j++) {
                        FieldBorder fieldBorder = new FieldBorder();
                        fieldBorder.setLongitude(borderLatLng.get(i).get(j).longitude);
                        fieldBorder.setLatitude(borderLatLng.get(i).get(j).latitude);
                        fieldBorder.setBorderIndex(j);
                        fieldBorderList.add(fieldBorder);
                    }
                    farmField.setFieldBorderList(fieldBorderList);
                    farmFieldList.add(farmField);
                }
                farmAreaEdit.setText(String.valueOf(farmTotalArea));
                JsonObject jsonObject = new JsonObject();
                Gson gson = new Gson();

                jsonObject.addProperty("farmField", gson.toJson(farmFieldList));
                Log.i(TAG, "farmTotalArea====" + String.valueOf(farmTotalArea) + "......");
            }
        }
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {
                List<String> crops = data.getExtras().getStringArrayList("names");
                Log.i(TAG, "crop" + crops.size());
                for (int i = 0; i < crops.size(); i++) {
                    if (i > 0) {
                        cropName += ",";
                    }
                    cropName = cropName + crops.get(i);
                }
                cropsKind.setText(cropName);
            }
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                pointImageListAdapter.addItems(images);
                pointImageListAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                Log.i("sysout", "ImageItem " + images.size());
                pointImageListAdapter.clear();
                pointImageListAdapter.addItems(images);
            }
        }
    }

    public void updateDB() {
        if (pointType == POINT_TYPE_PURCHASE) {
            daoSession.getPurchasingPointDao().update(purchasingPoint);
            uploadPicture(purchasingPoint.getPurchasingPointId());
        } else if (pointType == POINT_TYPE_FARM) {
            daoSession.getFarmEntityDao().update(farmEntity);
            uploadPicture(farmEntity.getFarmId());
        } else if (pointType == POINT_TYPE_DC) {
            daoSession.getDCEntityDao().update(dcEntity);
            uploadPicture(dcEntity.getId());
        }
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Log.i(TAG, "onItemClick.." + position);
        if (position >= pointImageListAdapter.getDataSize()) {
            ImagePicker.getInstance().setSelectLimit(4 - pointImageListAdapter.getDataSize());
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT);
        } else {
            Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
            Log.i(TAG, "datasize=" + pointImageListAdapter.getDataSize());
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) pointImageListAdapter.getDataSet());
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        }
    }


    public void uploadPicture(long pointIdId) {
        List<String> pathList = new ArrayList<>();
        for (ImageItem imageItem : pointImageListAdapter.getDataSet()) {
            if (imageItem.path.startsWith("/upload")) {
                continue;
            }
            pathList.add(imageItem.path);
        }
        if (pathList.size() > 0) {
            if (pointType == POINT_TYPE_FARM) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("farmId", pointIdId);
                getPresenter().updatePointPicture("farmSlideUpload", jsonObject, pathList);
            } else if (pointType == POINT_TYPE_PURCHASE) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("purchasingPointId", pointIdId);
                getPresenter().updatePointPicture("purchaseSlideUpload", jsonObject, pathList);
            } else if (pointType == POINT_TYPE_DC) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("dcId", pointIdId);
                getPresenter().updatePointPicture("dcSlideUpload", jsonObject, pathList);
            }
        } else {
            dismissProgress();
            finish();
        }
    }
}
