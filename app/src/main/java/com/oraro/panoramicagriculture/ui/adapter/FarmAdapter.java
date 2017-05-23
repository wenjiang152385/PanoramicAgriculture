package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.Polygon;
import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.data.entity.FieldBorder;
import com.oraro.panoramicagriculture.ui.activity.FarmActivity;
import com.oraro.panoramicagriculture.ui.listener.DateSetListener;
import com.oraro.panoramicagriculture.ui.view.FarmFieldMapView;
import com.oraro.panoramicagriculture.ui.view.FieldOnMapView;
import com.oraro.panoramicagriculture.ui.view.MyButtonView;
import com.oraro.panoramicagriculture.ui.view.MyLandView;
import com.oraro.panoramicagriculture.ui.view.MyPointDetailView;
import com.oraro.panoramicagriculture.ui.view.chart.GradientProgressBar;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.MapHelperManager;
import com.oraro.panoramicagriculture.utils.UIManager;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/11.
 */
public class FarmAdapter extends RecyclerView.Adapter implements View.OnClickListener, AMap.OnCameraChangeListener, RadioGroup.OnCheckedChangeListener {

    private final String TAG = this.getClass().getSimpleName();
    protected static final int VIEW_TYPE_HEADER = 0;
    protected static final int VIEW_TYPE_HARVEST = -1;
    protected static final int VIEW_TYPE_PLANT = -2;
    protected static final int VIEW_TYPE_FIELD = -3;
    protected static final int VIEW_TYPE_MAP = -4;
    protected static final int VIEW_TYPE_HEADER_TOP1 = -5;
    protected static final int VIEW_TYPE_HEADER_TOP2 = -6;
    protected static final int VIEW_TYPE_FOOTER = -7;
    protected int mState = STATE_LOAD_MORE;
    private int BEHAVIOR_MODE;

    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;

    public int getBEHAVIOR_MODE() {
        return BEHAVIOR_MODE;
    }

    private Context mContext;
    private FarmActivity activity;

    public FarmViewHolderHeader getFarmViewHolderHeader() {
        return farmViewHolderHeader;
    }

    private FarmViewHolderHeader farmViewHolderHeader;

    private List<FarmField> farmFieldList = new ArrayList<>();
    private List<FarmField> harvestList = new ArrayList<>();

    public FarmFieldMapView mapView;
    public AMap aMap;

    private PTHolder0 ptHolder0;

    public void setFarmFieldList(List<FarmField> farmFieldList) {
        this.farmFieldList = farmFieldList;
        Log.i(TAG, "farmFieldList.." + farmFieldList.size());
        mState = STATE_HIDE;
        notifyDataSetChanged();
    }


    public void setFarmHarvest(List<FarmField> harvestList) {
        this.harvestList = harvestList;
        Log.i(TAG, "farmFieldList.." + harvestList.size());
        mState = STATE_HIDE;
        notifyDataSetChanged();
    }

    public FarmAdapter(Context context) {
        mContext = context;
        activity = (FarmActivity) context;
    }

    @Override
    public int getItemViewType(int position) {
        if (BEHAVIOR_MODE != VIEW_TYPE_MAP && position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTER;
        }
        if (position == 0) {
            return VIEW_TYPE_HEADER_TOP1;
        } else if (position == 1) {
            return VIEW_TYPE_HEADER_TOP2;
        } else if (position == 2) {
            return VIEW_TYPE_HEADER;
        } else return BEHAVIOR_MODE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder.." + viewType);
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case VIEW_TYPE_HEADER_TOP1:
                ptHolder0 = new PTHolder0(inflater.inflate(R.layout.item_ptholder0, parent, false));
                ptHolder0.initData(activity.getHolderData());
                holder = ptHolder0;
                break;
            case VIEW_TYPE_HEADER_TOP2:
                PTHolder1 ptHolder1 = new PTHolder1(inflater.inflate(R.layout.item_ptholder1, parent, false));
                ptHolder1.initData(activity.getHolderData());
                holder = ptHolder1;
                break;
            case VIEW_TYPE_HEADER:
                holder = new FarmViewHolderHeader(inflater.inflate(R.layout.farm_item_head, parent, false));
                farmViewHolderHeader = (FarmViewHolderHeader) holder;
                onHot(farmViewHolderHeader.harvestButton);
                break;
            case VIEW_TYPE_HARVEST:
                holder = new HarvestViewHolder(inflater.inflate(R.layout.item_plant_point_detail, parent, false));
                break;
            case VIEW_TYPE_PLANT:
                holder = new PlantViewHolder(inflater.inflate(R.layout.item_plant, parent, false));
                break;
            case VIEW_TYPE_FIELD:
                holder = new FieldViewHolder(inflater.inflate(R.layout.item_field_list, parent, false));
                break;
            case VIEW_TYPE_MAP:
                holder = new MapViewHolder(inflater.inflate(R.layout.item_map, parent, false));
                break;
            case VIEW_TYPE_FOOTER:
                holder = new FooterViewHolder(inflater.inflate(R.layout.list_footer_layout, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int positionInList = position - 3;
        Log.i(TAG, "onBindViewHolder..." + position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                farmViewHolderHeader.harvestButton.setOnClickListener(this);
                farmViewHolderHeader.harvestButton.SetText("收获");
                farmViewHolderHeader.plantButton.setOnClickListener(this);
                farmViewHolderHeader.plantButton.SetText("种植");
                farmViewHolderHeader.fieldButton.setOnClickListener(this);
                farmViewHolderHeader.fieldButton.SetText("田块");
                farmViewHolderHeader.mapButton.setOnClickListener(this);
                farmViewHolderHeader.mapButton.SetText("地图");
                farmViewHolderHeader.id_radioGroup.setOnCheckedChangeListener(this);
//                farmViewHolderHeader.id_more.setOnClickListener(this);
                break;
            case VIEW_TYPE_HARVEST:
                HarvestViewHolder hvh = (HarvestViewHolder) holder;
                if (harvestList.size() > positionInList) {
                    FarmField data = harvestList.get(positionInList);
                    hvh.__position = position;
                    hvh.position.setText(data.getFieldName());
                    hvh.cropsimage.setImageURI(Constants.SERVER_ADDRESS + data.getVfCrops().getPath1());
                    hvh.vfCrops_byname.setText(data.getVfCrops().getByname());
                    hvh.plantNum.setText(String.valueOf(data.getActualHarvestNum()));
                    hvh.expectHarvestNum.setText(String.valueOf(data.getExpectHarvestNum()));
                    try {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        Date mDate = sf.parse(data.getCurrentSowTime());
                        sf = new SimpleDateFormat("yyyy.MM.dd");
                        hvh.plantTime.setText(sf.format(mDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        Date mDate = sf.parse(data.getCurrentMatureTime());
                        sf = new SimpleDateFormat("yyyy.MM.dd");
                        hvh.expectHarvestTime.setText(sf.format(mDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case VIEW_TYPE_PLANT:
                PlantViewHolder plantViewHolder = (PlantViewHolder) holder;
                plantViewHolder.myLandView1.setAnAcreLand(farmFieldList.get(positionInList * 3));
                if ((positionInList * 3) + 2 < farmFieldList.size()) {
                    plantViewHolder.myLandView2.setAnAcreLand(farmFieldList.get(positionInList * 3 + 1));
                    plantViewHolder.myLandView3.setAnAcreLand(farmFieldList.get(positionInList * 3 + 2));
                    plantViewHolder.myLandView2.setVisibility(View.VISIBLE);
                    plantViewHolder.myLandView3.setVisibility(View.VISIBLE);
                } else if ((positionInList * 3) + 1 < farmFieldList.size()) {
                    plantViewHolder.myLandView2.setAnAcreLand(farmFieldList.get(positionInList * 3 + 1));
                    plantViewHolder.myLandView3.setVisibility(View.INVISIBLE);
                    plantViewHolder.myLandView2.setVisibility(View.VISIBLE);
                } else {
                    plantViewHolder.myLandView2.setVisibility(View.INVISIBLE);
                    plantViewHolder.myLandView3.setVisibility(View.INVISIBLE);
                }
                break;
            case VIEW_TYPE_FIELD:
                bindFieldData((FieldViewHolder) holder, positionInList);
                break;
            case VIEW_TYPE_MAP:
                bindMapData();
                break;
            case VIEW_TYPE_FOOTER:
//                Log.d(TAG, "VIEW_TYPE_FOOTER.." + mState + ",," + onLoadingListener);
//                if ((mState == STATE_LOAD_MORE) && onLoadingListener != null) {
//                    onLoadingListener.onLoading();
//                }
                FooterViewHolder fvh = (FooterViewHolder) holder;
                fvh.itemView.setVisibility(View.VISIBLE);
                switch (mState) {
                    case STATE_INVALID_NETWORK:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.invalid_network));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_MORE:
                    case STATE_LOADING:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.loading));
                        fvh.probar.setVisibility(View.VISIBLE);
                        Log.i(TAG, "FooterViewHolder STATE_LOAD_MORE");
                        break;
                    case STATE_NO_MORE:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.load_no_more));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_REFRESHING:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.refreshing));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_ERROR:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.load_failed));
                        fvh.probar.setVisibility(View.GONE);
                        mState = STATE_LOAD_MORE;
                        break;
                    case STATE_HIDE:
                        fvh.itemView.setVisibility(View.GONE);
                        break;
                }
                break;

        }
    }

    @Override
    public int getItemCount() {
        int count = 1;
        switch (BEHAVIOR_MODE) {
            case VIEW_TYPE_HEADER:
                break;
            case VIEW_TYPE_HARVEST:
                count += harvestList.size();
                count ++;
                break;
            case VIEW_TYPE_PLANT:
                if (farmFieldList.size() % 3 == 0) {
                    count = farmFieldList.size() / 3 + 1;
                } else {
                    count = farmFieldList.size() / 3 + 2;
                }
                count ++;
                break;
            case VIEW_TYPE_FIELD:
                count = farmFieldList.size() + 1;
                count ++;
                break;
            case VIEW_TYPE_MAP:
                count++;
                break;
        }
        return count + 2;
    }

    public void onResume() {
        BEHAVIOR_MODE = VIEW_TYPE_HARVEST;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        activity.LoadHarvest(sf.format(cal.getTime()));
    }

    public void onSliderResume() {
        if (ptHolder0 != null) {
            ptHolder0.mDemoSlider.startAutoCycle();
        }
    }

    public void onSliderPause() {
        if (ptHolder0 != null) {
            ptHolder0.mDemoSlider.stopAutoCycle();
        }
    }


    @Override
    public void onClick(View v) {
        mState = STATE_LOAD_MORE;
        switch (v.getId()) {
            case R.id.id_btn_harvest:
                Log.i(TAG, "onClick..VIEW_TYPE_PLANT.." + BEHAVIOR_MODE);
                farmViewHolderHeader.mShowDays.setVisibility(View.VISIBLE);
                BEHAVIOR_MODE = VIEW_TYPE_HARVEST;
                onHot(v);
                onCheckedChanged(farmViewHolderHeader.id_radioGroup, farmViewHolderHeader.id_radioGroup.getCheckedRadioButtonId());
                break;
            case R.id.id_btn_plant1:
                Log.i(TAG, "onClick..VIEW_TYPE_PLANT.." + BEHAVIOR_MODE);
                farmViewHolderHeader.mShowDays.setVisibility(View.GONE);
                BEHAVIOR_MODE = VIEW_TYPE_PLANT;
                onHot(v);
                activity.LoadFarmField();
                break;
            case R.id.id_btn_plant2:
                Log.i(TAG, "onClick..VIEW_TYPE_FIELD.." + BEHAVIOR_MODE);
                farmViewHolderHeader.mShowDays.setVisibility(View.GONE);
                BEHAVIOR_MODE = VIEW_TYPE_FIELD;
                onHot(v);
                activity.LoadFarmField();
                break;
            case R.id.id_btn_plant3:
                Log.i(TAG, "onClick..VIEW_TYPE_MAP.." + BEHAVIOR_MODE);
                farmViewHolderHeader.mShowDays.setVisibility(View.GONE);
                BEHAVIOR_MODE = VIEW_TYPE_MAP;
                onHot(v);
                activity.LoadFarmField();
                break;
//            case R.id.id_more:
//                Intent intent = new Intent(activity, MPChartActivity.class);
//                intent.putExtra("pointId", activity.getHolderData().pointId);
//                intent.putExtra("type", Constants.CHART_ROLE_FARM_REQUIRE);
//                activity.startActivityForResult(intent, 100);
//                break;
        }

        notifyDataSetChanged();
    }

    private void onHot(View btnHot) {
        {
            farmViewHolderHeader.harvestButton.setHot(false);
            farmViewHolderHeader.fieldButton.setHot(false);
            farmViewHolderHeader.plantButton.setHot(false);
            farmViewHolderHeader.mapButton.setHot(false);
        }

        ((MyButtonView) btnHot).setHot(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == farmViewHolderHeader.id_radioGroup) {
            String date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Log.e("yjd", "radio" + checkedId + "------>" + R.id.id_today);
            switch (checkedId) {
                case R.id.id_today:
                    date = sf.format(cal.getTime());
                    activity.LoadHarvest(date);
                    break;
                case R.id.id_yesterday:
                    cal.add(cal.DATE, 1);
                    date = sf.format(cal.getTime());
                    activity.LoadHarvest(date);
                    break;
                case R.id.id_threedaysago:
                    cal.add(cal.DATE, 2);
                    date = sf.format(cal.getTime());
                    activity.LoadHarvest(date);
                    break;
                case R.id.id_more:
                    UIManager.loadDatePicker(activity, new DateSetListener() {
                        @Override
                        public void dataChoosed(String date) {
                            activity.LoadHarvest(date);
                        }
                    });
                    break;
            }
        }
    }

    private void bindFieldData(FieldViewHolder fieldViewHolder, int position) {
        FarmField farmField = farmFieldList.get(position);
        double percent = 0;
        if (farmField.getTotalday() != 0) {
            LogUtils.i("getTotalday  " + farmField.getPlantday() + "," + farmField.getTotalday());
            percent = (Double.valueOf(farmField.getPlantday()) / Double.valueOf(farmField.getTotalday()));
        }
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(0);

        fieldViewHolder.matureDegreeChart.setPercent(percent);
        fieldViewHolder.itemPostionText.setText("No." + (position + 1));
        fieldViewHolder.itemTitle.setText(farmField.getFieldName());
        fieldViewHolder.fieldAreaText.setText(farmField.getFieldArea().toString() + "亩");
        fieldViewHolder.vfcropsNameText.setText(farmField.getVfCrops() == null ? "未种植" : farmField.getVfCrops().getByname());
        if (farmField.getNextVFcrops() != null) {
            fieldViewHolder.nextPlantCropText.setText(farmField.getNextVFcrops().getByname());
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy.M.d");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        if (farmField.getNextSowTime() != null) {
            String nextSowTime = null;
            try {
                nextSowTime = simpleDateFormat2.format(simpleDateFormat1.parse(farmField.getNextSowTime().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fieldViewHolder.nextPlantTimeText.setText(nextSowTime);
        }
        if (farmField.getCurrentSowTime() != null) {
            String currentSowTime = null;
            try {
                currentSowTime = simpleDateFormat2.format(simpleDateFormat1.parse(farmField.getCurrentSowTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fieldViewHolder.plantTimeText.setText(currentSowTime);
        }
        if (farmField.getCurrentMatureTime() != null) {
            String currentMatureTime = null;
            try {
                currentMatureTime = simpleDateFormat2.format(simpleDateFormat1.parse(farmField.getCurrentMatureTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fieldViewHolder.harvestTimeText.setText(currentMatureTime);
        }
        switch (position % 4) {
            case 0:
                fieldViewHolder.colorView.setBackgroundColor(Color.parseColor("#FFF89200"));
                break;
            case 1:
                fieldViewHolder.colorView.setBackgroundColor(Color.parseColor("#FF20CEB4"));
                break;
            case 2:
                fieldViewHolder.colorView.setBackgroundColor(Color.parseColor("#FFEE4C3C"));
                break;
            case 3:
                fieldViewHolder.colorView.setBackgroundColor(Color.parseColor("#FFBDBDBD"));
                break;
        }
    }

    private void bindMapData() {
        aMap.clear();
        MapHelperManager mapHelperManager = MapHelperManager.getInstance();
        List<Polygon> borderLines = new ArrayList<>();
        FarmEntity farmEntity = PanoramicAgricultureApplication.getInstances().getDaoSession().getFarmEntityDao().load(((FarmActivity) mContext).getFarmId());
        LatLng farmLatLng = new LatLng(farmEntity.getLatitude(), farmEntity.getLongitude());
        mapHelperManager.changeCameraPosition(aMap, new LatLng(farmEntity.getLatitude() - 0.03, farmEntity.getLongitude()), 14, 0, 0, true);
        Marker farmMarker = mapHelperManager.addMarker(aMap, farmLatLng);
        farmMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.farm_marker_self));
        for (int i = 0; i < farmFieldList.size(); i++) {
            FarmField farmField = farmFieldList.get(i);
            List<FieldBorder> fieldBorderList = farmField.getFieldBorderList();
            Map<String, String> fieldInfo = new HashMap();
            fieldInfo.put("fieldName", farmField.getFieldName());
            fieldInfo.put("fieldArea", farmField.getFieldArea().toString());
//            addServerMarker(new LatLng(farmField.getLatitude(), farmField.getLongitude()), -1, ADD_FIELD_MARKER);

            List<LatLng> latLngs = new ArrayList<>();
            for (FieldBorder fieldBorder : fieldBorderList) {
                LatLng latLng = new LatLng(fieldBorder.getLatitude(), fieldBorder.getLongitude());
                latLngs.add(latLng);
//                addServerMarker(latLng, fieldBorder.getBorderIndex() + 1, ADD_BORDER_MARKER);

            }
            int percent = 0;
            if (farmField.getTotalday() != 0) {
                percent = (int) ((Double.valueOf(farmField.getPlantday()) / Double.valueOf(farmField.getTotalday())) * 100);
            }

            Bitmap bitmap = getBitmapFromView(new FieldOnMapView(mContext, i + 1, farmField.getFieldName(), percent));
            farmField.setFieldPosition(i);

            LogUtils.i("getDrawingCache==" + bitmap.getHeight() + ",with=" + bitmap.getWidth() + "," + percent + "," + i);

            Bitmap newBm = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(newBm);


            Marker marker = mapHelperManager.addMarker(aMap, new LatLng(farmField.getLatitude(), farmField.getLongitude()));
            marker.setIcon(bitmapDescriptor);
            marker.setObject(farmField);


            Polygon polygon = mapHelperManager.addPolygon(latLngs, aMap);
            polygon.setStrokeWidth(5);
            polygon.setFillColor(Color.argb(70, 1, 1, 1));
            switch (borderLines.size() % 4) {
                case 0:
                    polygon.setStrokeColor(Color.parseColor("#FFF89200"));
                    break;
                case 1:
                    polygon.setStrokeColor(Color.parseColor("#FF20CEB4"));
                    break;
                case 2:
                    polygon.setStrokeColor(Color.parseColor("#FFEE4C3C"));
                    break;
                case 3:
                    polygon.setStrokeColor(Color.parseColor("#FFBDBDBD"));
                    break;
            }
            borderLines.add(polygon);
        }
    }


    private Bitmap getBitmapFromView(View currentView) {
        currentView.setDrawingCacheEnabled(true);
        currentView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        currentView.layout(0, 0, currentView.getMeasuredWidth(),
                currentView.getMeasuredHeight());
        currentView.buildDrawingCache();
        Bitmap viewBitmap = currentView.getDrawingCache();
//        currentView.setDrawingCacheEnabled(false);
        return viewBitmap;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.i(TAG, "onCameraChange");
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.i(TAG, "onCameraChangeFinish");
        float scaleSize = cameraPosition.zoom / 14;
        if (scaleSize > 1) {
            scaleSize *= 1.3;
        } else if (scaleSize < 1) {
            scaleSize *= 0.8;
        }

        for (Marker marker : aMap.getMapScreenMarkers()) {
            if (marker.getObject() instanceof FarmField) {
                FarmField farmField = (FarmField) marker.getObject();
                int precent = 0;
                if (farmField.getTotalday() != 0) {
                    precent = (int) ((Double.valueOf(farmField.getPlantday()) / Double.valueOf(farmField.getTotalday())) * 100);
                }
                Bitmap bitmap = getBitmapFromView(new FieldOnMapView(mContext, farmField.getFieldPosition() + 1, farmField.getFieldName(), precent));

                LogUtils.i("getDrawingCache==" + bitmap.getHeight() + ",with=" + bitmap.getWidth());

                Bitmap newBm = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / 2 * scaleSize), (int) (bitmap.getHeight() / 2 * scaleSize), true);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(newBm);
                marker.setIcon(bitmapDescriptor);
            }
        }
    }


    public class FarmViewHolderHeader extends RecyclerView.ViewHolder {

        private MyButtonView harvestButton;
        private MyButtonView plantButton;
        private MyButtonView fieldButton;
        private MyButtonView mapButton;
        private LinearLayout mShowDays;
        public RadioGroup id_radioGroup;
        private TextView id_more;

        public FarmViewHolderHeader(View itemView) {
            super(itemView);
            harvestButton = (MyButtonView) itemView.findViewById(R.id.id_btn_harvest);
            plantButton = (MyButtonView) itemView.findViewById(R.id.id_btn_plant1);
            fieldButton = (MyButtonView) itemView.findViewById(R.id.id_btn_plant2);
            mapButton = (MyButtonView) itemView.findViewById(R.id.id_btn_plant3);
            mShowDays = (LinearLayout) itemView.findViewById(R.id.id_showDays);
            id_radioGroup = (RadioGroup) itemView.findViewById(R.id.id_radioGroup);
//            id_more = (TextView) itemView.findViewById(R.id.id_more);
        }
    }

    private class PlantViewHolder extends RecyclerView.ViewHolder {

        private MyLandView myLandView1;
        private MyLandView myLandView2;
        private MyLandView myLandView3;

        public PlantViewHolder(View itemView) {
            super(itemView);
            myLandView1 = (MyLandView) itemView.findViewById(R.id.my_land_view1);
            myLandView2 = (MyLandView) itemView.findViewById(R.id.my_land_view2);
            myLandView3 = (MyLandView) itemView.findViewById(R.id.my_land_view3);
        }
    }

    private class FieldViewHolder extends RecyclerView.ViewHolder {
        private View colorView;
        private TextView itemPostionText;
        private TextView itemTitle;
        private TextView fieldAreaText;
        private TextView nextPlantCropText;
        private TextView nextPlantTimeText;
        private TextView plantTimeText;
        private TextView harvestTimeText;
        private TextView vfcropsNameText;
        private GradientProgressBar matureDegreeChart;

        public FieldViewHolder(View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.view_color);
            itemPostionText = (TextView) itemView.findViewById(R.id.tv_field_item_position);
            itemTitle = (TextView) itemView.findViewById(R.id.tv_field_item_crop);
            nextPlantCropText = (TextView) itemView.findViewById(R.id.tv_next_plant_crop);
            nextPlantTimeText = (TextView) itemView.findViewById(R.id.tv_next_plant_time);
            plantTimeText = (TextView) itemView.findViewById(R.id.tv_plant_time);
            harvestTimeText = (TextView) itemView.findViewById(R.id.tv_harvest_time);
            fieldAreaText = (TextView) itemView.findViewById(R.id.tv_field_area);
            vfcropsNameText = (TextView) itemView.findViewById(R.id.tv_vfcrops_name);
            matureDegreeChart = (GradientProgressBar) itemView.findViewById(R.id.mature_degree_chart);
        }
    }

    class MapViewHolder extends RecyclerView.ViewHolder {

        public MapViewHolder(View itemView) {
            super(itemView);
            mapView = (FarmFieldMapView) itemView.findViewById(R.id.farm_field_mapview);
            mapView.onCreate(null);
            mapView.onResume();
            aMap = mapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
            aMap.getUiSettings().setZoomGesturesEnabled(true);
            aMap.getUiSettings().setScaleControlsEnabled(true);
            aMap.setOnCameraChangeListener(FarmAdapter.this);
        }
    }


    class HarvestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        int __position;
        SimpleDraweeView cropsimage;
        TextView position;
        TextView vfCrops_byname;
        TextView plantNum;
        TextView expectHarvestNum;
        TextView plantTime;
        TextView expectHarvestTime;

        TextView btn_detail_cancel;
        TextView btn_detail_edit;
        TextView btn_detail_editneed;

        public HarvestViewHolder(View view) {
            super(view);
            MyPointDetailView id_MyDetailView = (MyPointDetailView) view.findViewById(R.id.id_MyDetailView);
            cropsimage = id_MyDetailView.cropsimage;
            position = id_MyDetailView.position;
            vfCrops_byname = id_MyDetailView.vfCrops_byname;
            plantNum = id_MyDetailView.num1;
            expectHarvestNum = id_MyDetailView.num2;
            plantTime = id_MyDetailView.time1;
            expectHarvestTime = id_MyDetailView.time2;

            btn_detail_cancel = id_MyDetailView.btn_detail_cancel;
            btn_detail_cancel.setOnClickListener(this);
            btn_detail_edit = id_MyDetailView.btn_detail_edit;
            btn_detail_edit.setOnClickListener(this);
            btn_detail_editneed = id_MyDetailView.btn_detail_editneed;
            btn_detail_editneed.setOnClickListener(this);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
//
            RefreshButton(btn_detail_cancel);
            RefreshButton(btn_detail_edit);
            RefreshButton(btn_detail_editneed);
        }

        public void RefreshButton(TextView tv) {
            if (activity.getHolderData().bYouself) {
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_detail_cancel:
                    //revokeFarmHarvest(Long.parseLong(__id.getText().toString()));
                    //notifyDataSetChanged();
                    break;
                default:
                    //Toast.makeText(mFragment.getActivity(), "Click ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //Toast.makeText(mFragment.getActivity(), "LongClick ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * footer view holder
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar probar;
        public TextView mStateText;

        public FooterViewHolder(View view) {
            super(view);
            probar = (ProgressBar) view.findViewById(R.id.progressbar);
            mStateText = (TextView) view.findViewById(R.id.state_text);
        }
    }
}
