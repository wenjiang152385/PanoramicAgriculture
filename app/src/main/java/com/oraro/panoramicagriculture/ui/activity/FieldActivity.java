package com.oraro.panoramicagriculture.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.Polyline;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.FieldBorder;
import com.oraro.panoramicagriculture.presenter.FieldPresenter;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.view.FieldMarkerView;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.MapHelperManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/16.
 */
public class FieldActivity extends BaseActivity<FieldPresenter> implements AMap.OnMarkerClickListener, AMap.OnMapClickListener, AMap.OnMarkerDragListener, AMap.OnMapLongClickListener, AMap.InfoWindowAdapter {

    private final String TAG = this.getClass().getSimpleName();
    private MapView mapView;
    private AMap aMap;
    private MapHelperManager mapHelperManager;
    private Marker currentMarker;

    private List<List<Marker>> borderMarkers = new ArrayList<>();
    private List<Marker> fieldMarkers = new ArrayList<>();
    private List<Polygon> borderLines = new ArrayList<>();
    private List<Map<String, String>> fieldInfos = new ArrayList<>();

    private List<List<Marker>> serverBorderMarkers = new ArrayList<>();
    private List<Marker> serverFieldMarkers = new ArrayList<>();
    private Map<FarmField, Polyline> serverBorderLine = new HashMap<>();

    private final int ADD_BORDER_MARKER = 0;
    private final int ADD_FIELD_MARKER = 1;
    private final int ADD_FARM_MARKER = 2;

    private double farmServerArea = 0;

    @Override
    public FieldPresenter createPresenter() {
        return new FieldPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        mapView = (MapView) findViewById(R.id.field_map_view);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        aMap.getUiSettings().setZoomGesturesEnabled(true);
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerDragListener(this);
        aMap.setOnMapLongClickListener(this);
        aMap.setInfoWindowAdapter(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapHelperManager = MapHelperManager.getInstance();

        LatLng latLng = new LatLng(getIntent().getDoubleExtra("latitude", 116.2317), getIntent().getDoubleExtra("longitude", 39.5427));
        if (getIntent() != null && getIntent().getLongExtra("farmId", -1) != -1) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("farmId", getIntent().getLongExtra("farmId", -1));
            getPresenter().getPoint("getFarmField", jsonObject);
            FarmEntity farmEntity = PanoramicAgricultureApplication.getInstances().getDaoSession().getFarmEntityDao().load(getIntent().getLongExtra("farmId", -1));
            if (farmEntity != null) {
                latLng = new LatLng(farmEntity.getLatitude(), farmEntity.getLongitude());
            }
        }
        mapHelperManager.changeCameraPosition(aMap, latLng, 14, 0, 0, true);
        addMarker(latLng, ADD_FARM_MARKER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_farm_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                Log.i(TAG, "onOptionsItemSelected..." + borderMarkers.size() + "," + fieldMarkers.size());
                if (borderMarkers.size() == fieldMarkers.size()) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    List<LatLng> fieldLatLng = new ArrayList<>();
                    for (Marker fieldMarker : fieldMarkers) {
                        fieldLatLng.add(fieldMarker.getPosition());
                    }
                    List<List<LatLng>> borderLatLng = new ArrayList<>();
                    for (List<Marker> borderMarkerList : borderMarkers) {
                        List<LatLng> borderList = new ArrayList<>();
                        for (Marker borderMarker : borderMarkerList) {
                            borderList.add(borderMarker.getPosition());
                        }
                        borderLatLng.add(borderList);
                    }
                    bundle.putSerializable("borderLatLng", (Serializable) borderLatLng);
                    bundle.putSerializable("fieldInfos", (Serializable) fieldInfos);
                    bundle.putDouble("farmServerArea",farmServerArea);
                    bundle.putParcelableArrayList("fieldLatLng", (ArrayList<? extends Parcelable>) fieldLatLng);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i(TAG, "onMarkerClick..." + marker.isInfoWindowShown());
        if (fieldMarkers.contains(marker) || marker.getObject() instanceof FarmField) {
            marker.showInfoWindow();
            Log.i(TAG, "onMarkerClick..." + marker.isInfoWindowShown());
        } else if (currentMarker.isInfoWindowShown()) {
            currentMarker.hideInfoWindow();
        }
        currentMarker = marker;
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (currentMarker != null && currentMarker.isInfoWindowShown()) {
            currentMarker.hideInfoWindow();
        } else {
            addMarker(latLng, ADD_BORDER_MARKER);
        }
    }

//    public void addMarker(LatLng latLng, int type) {
//        switch (type) {
//            case ADD_BORDER_MARKER:
//                if (borderMarkers.isEmpty()) {
//                    addMarker(latLng, type, 1);
//                } else {
//                    addMarker(latLng, type, borderMarkers.get(borderMarkers.size()).size() + 1);
//                }
//                break;
//            case ADD_FIELD_MARKER:
//                addMarker(latLng, type, fieldMarkers.size() + 1);
//                break;
//        }
//    }

    public void addMarker(LatLng latLng, int type) {
        Marker marker = mapHelperManager.addMarker(aMap, latLng);
        switch (type) {
            case ADD_BORDER_MARKER:
                marker.setDraggable(true);
                if (borderMarkers.size() <= borderLines.size())
                    borderMarkers.add(borderLines.size(), new ArrayList<Marker>());
                Log.i(TAG, "addmarker.." + borderMarkers.get(borderLines.size()).size());
                marker.setIcon(BitmapDescriptorFactory.fromView(new FieldMarkerView(FieldActivity.this, borderMarkers.get(borderLines.size()).size() + 1, R.mipmap.field_border_marker)));
                borderMarkers.get(borderLines.size()).add(marker);
                break;
            case ADD_FIELD_MARKER:
                marker.setDraggable(true);
                marker.setTitle("");
                marker.setIcon(BitmapDescriptorFactory.fromView(new FieldMarkerView(FieldActivity.this, serverFieldMarkers.size() + fieldMarkers.size() + 1, R.mipmap.new_point_location)));
                fieldMarkers.add(marker);
                break;
            case ADD_FARM_MARKER:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.farm_marker_self));
                break;
        }
    }

    public Marker addServerMarker(LatLng latLng, int positon, int type) {
        Marker marker = mapHelperManager.addMarker(aMap, latLng);
        marker.setDraggable(true);
        switch (type) {
            case ADD_BORDER_MARKER:
                marker.setIcon(BitmapDescriptorFactory.fromView(new FieldMarkerView(FieldActivity.this, positon, R.mipmap.field_border_marker)));
                break;
            case ADD_FIELD_MARKER:
                marker.setTitle("server");
                serverFieldMarkers.add(marker);
                marker.setIcon(BitmapDescriptorFactory.fromView(new FieldMarkerView(FieldActivity.this, serverFieldMarkers.size(), R.mipmap.new_point_location)));
                break;
        }
        return marker;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.i(TAG, "onMarkerDragStart..." + marker.getId());
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        for (List<Marker> markers : borderMarkers) {
            if (markers.contains(marker))
                Log.i(TAG, "onMarkerDragEnd..latlng=" + marker.getPosition());
            refreshPolyline();
        }
        if (marker.getObject() instanceof FarmField) {
            ((FarmField) marker.getObject()).setLatitude(marker.getPosition().latitude);
            ((FarmField) marker.getObject()).setLongitude(marker.getPosition().longitude);
            Log.i(TAG, "onMarkerDragEnd..FarmField=" + ((FarmField) marker.getObject()).getFieldName() + ",lat=" + ((FarmField) marker.getObject()).getFieldBorderList().get(0).getLatitude()
                    + ",lng=" + ((FarmField) marker.getObject()).getLongitude());
            if (serverBorderLine.containsKey(((FarmField) marker.getObject()))) {
                List<LatLng> latLngList = new ArrayList<>();
                for (FieldBorder fieldBorder : ((FarmField) marker.getObject()).getFieldBorderList()) {
                    latLngList.add(new LatLng(fieldBorder.getLatitude(), fieldBorder.getLongitude()));
                }
                latLngList.add(latLngList.get(0));
                serverBorderLine.get(((FarmField) marker.getObject())).setPoints(latLngList);
            }
        }
        if (marker.getObject() instanceof FieldBorder) {
            ((FieldBorder) marker.getObject()).setLatitude(marker.getPosition().latitude);
            ((FieldBorder) marker.getObject()).setLongitude(marker.getPosition().longitude);
            Log.i(TAG, "onMarkerDragEnd..FieldBorder=" + ((FieldBorder) marker.getObject()));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (fieldMarkers.size() < borderMarkers.size() && borderMarkers.get(fieldMarkers.size()).size() > 2) {
            addMarker(latLng, ADD_FIELD_MARKER);
            refreshPolyline();
        }
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        final int position = fieldMarkers.indexOf(marker);
        View view = LayoutInflater.from(this).inflate(R.layout.newfield_infowindow_layout, null);
        view.setMinimumWidth(CommonUtils.getDisplayMetrics(FieldActivity.this).widthPixels / 3);

        final EditText fieldNameEdit = (EditText) view.findViewById(R.id.et_field_name);
        final EditText fieldAreaEdit = (EditText) view.findViewById(R.id.et_field_area);
        Button submitBtn = (Button) view.findViewById(R.id.btn_submit);

        if (marker.getObject() instanceof FarmField) {
            FarmField farmField = (FarmField) marker.getObject();
            fieldNameEdit.setText(farmField.getFieldName());
            fieldAreaEdit.setText(String.valueOf(farmField.getFieldArea()));
            Log.i(TAG, "farmfield==" + farmField);
            if (serverBorderLine.containsKey(farmField)) {
                List<LatLng> latLngList = new ArrayList<>();
                for (FieldBorder fieldBorder : ((FarmField) marker.getObject()).getFieldBorderList()) {
                    latLngList.add(new LatLng(fieldBorder.getLatitude(), fieldBorder.getLongitude()));
                }
                latLngList.add(latLngList.get(0));
                serverBorderLine.get(farmField).setPoints(latLngList);
            }
        } else {

            if (fieldInfos.size() <= position) {
                fieldInfos.add(new HashMap<String, String>());
            }
            fieldNameEdit.setText(fieldInfos.get(position).get("fieldName"));
            fieldAreaEdit.setText(fieldInfos.get(position).get("fieldArea"));
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker.getObject() instanceof FarmField) {
                    FarmField farmField = (FarmField) marker.getObject();
                    farmField.setFieldName(fieldNameEdit.getText().toString());
                    farmField.setFieldArea(Double.parseDouble(fieldAreaEdit.getText().toString()));
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("fieldId", farmField.getFieldId());
                    jsonObject.addProperty("fieldName", farmField.getFieldName());
                    jsonObject.addProperty("fieldArea", farmField.getFieldArea());
                    jsonObject.addProperty("latitude", farmField.getLatitude());
                    jsonObject.addProperty("longitude", farmField.getLongitude());
                    jsonObject.add("fieldBorderList", gson.toJsonTree(farmField.getFieldBorderList()));


                    Log.i(TAG, gson.toJson(marker.getObject()) + "/n,json=" + jsonObject);
                    showProgress();
                    getPresenter().updateFarmField("farmFieldUpdate", jsonObject);
                } else {
                    fieldInfos.get(position).put("fieldName", fieldNameEdit.getText().toString());
                    fieldInfos.get(position).put("fieldArea", fieldAreaEdit.getText().toString());
                }
            }
        });
        return view;

    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.i(TAG, "getInfoContents.." + marker.getId());
        return null;
    }

    void refreshPolyline() {
        for (Polygon polygon : borderLines) {
            polygon.remove();
        }
        borderLines.clear();
        for (List<Marker> screenMarker : borderMarkers) {
            List<LatLng> latLngs = new ArrayList<>();
            if (screenMarker.size() > 2) {
                for (Marker marker : screenMarker) {
                    latLngs.add(marker.getPosition());
                }
                Polygon polygon = mapHelperManager.addPolygon(latLngs, aMap);
                polygon.setStrokeWidth(5);
                polygon.setFillColor(Color.argb(70, 1, 1, 1));
                switch (borderLines.size() % 5) {
                    case 0:
                        polygon.setStrokeColor(getResources().getColor(R.color.user_info_text));
                        break;
                    case 1:
                        polygon.setStrokeColor(getResources().getColor(R.color.swipe_refresh_first));
                        break;
                    case 2:
                        polygon.setStrokeColor(getResources().getColor(R.color.swipe_refresh_second));
                        break;
                    case 3:
                        polygon.setStrokeColor(getResources().getColor(R.color.swipe_refresh_third));
                        break;
                    case 4:
                        polygon.setStrokeColor(getResources().getColor(R.color.swipe_refresh_four));
                        break;
                }
                borderLines.add(polygon);
            }
        }
    }

    public void updateUIFromServer(List<FarmField> farmFieldList) {
        for (FarmField farmField : farmFieldList) {
            List<FieldBorder> fieldBorderList = farmField.getFieldBorderList();
            Map<String, String> fieldInfo = new HashMap();
            fieldInfo.put("fieldName", farmField.getFieldName());
            fieldInfo.put("fieldArea", farmField.getFieldArea().toString());
            farmServerArea += Double.parseDouble(farmField.getFieldArea().toString());
            addServerMarker(new LatLng(farmField.getLatitude(), farmField.getLongitude()), -1, ADD_FIELD_MARKER).setObject(farmField);

            List<LatLng> latLngs = new ArrayList<>();
            for (FieldBorder fieldBorder : fieldBorderList) {
                LatLng latLng = new LatLng(fieldBorder.getLatitude(), fieldBorder.getLongitude());
                latLngs.add(latLng);
                addServerMarker(latLng, fieldBorder.getBorderIndex() + 1, ADD_BORDER_MARKER).setObject(fieldBorder);
            }
            latLngs.add(latLngs.get(0));
            Polyline polyline = mapHelperManager.addPolyline(latLngs, aMap);
            polyline.setWidth(2);
            serverBorderLine.put(farmField, polyline);

        }
    }
}
