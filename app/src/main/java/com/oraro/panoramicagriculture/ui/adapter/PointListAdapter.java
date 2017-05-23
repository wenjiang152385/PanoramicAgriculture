package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.ui.activity.DcActivity;
import com.oraro.panoramicagriculture.ui.activity.FarmActivity;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryMapActivity;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.utils.MapHelperManager;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public class PointListAdapter extends BaseListAdapter<Object> implements BaseListAdapter.OnItemClickListener {
    private MapHelperManager mapHelperManager;
    private Context context;
    public PointListAdapter(Context context, int mode) {
        super(context, mode);
        this.context = context;
        mapHelperManager = MapHelperManager.getInstance();
        this.setOnItemClickListener(this);
    }

    public PointListAdapter(Context context, List<Object> items, int mode) {
        super(context, items, mode);
        this.context = context;
        mapHelperManager = MapHelperManager.getInstance();
        this.setOnItemClickListener(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PointViewHolder(mInflater.inflate(R.layout.point_popup_list_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final int position) {
        PointViewHolder pointViewHolder = (PointViewHolder) h;
        if (getDataSize() > position) {
            Object item = getItem(getPosition(position));
            String degree = null;
            if (item instanceof FarmEntity) {
                FarmEntity farmEntity = (FarmEntity) item;
                pointViewHolder.pointName.setText(farmEntity.getFarmName());
                degree = "北纬" + mapHelperManager.latlng2MinSec(farmEntity.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(farmEntity.getLongitude());
                pointViewHolder.pointIcon.setImageResource(R.mipmap.farm_icon_white);
                if (((OrdinaryMapActivity) context).getCurrentLatLng() != null) {
                    int distance = (int) AMapUtils.calculateLineDistance(new LatLng(farmEntity.getLatitude(), farmEntity.getLongitude()), ((OrdinaryMapActivity) context).getCurrentLatLng());
                    if (distance > 1000) {
                        pointViewHolder.pointDistance.setText(((double) distance / 1000)+ "km");
                    } else {
                        pointViewHolder.pointDistance.setText(distance + "m");
                    }
                }
            } else if (item instanceof PurchasingPoint) {
                PurchasingPoint purchasingPoint = (PurchasingPoint) item;
                pointViewHolder.pointName.setText(purchasingPoint.getPurchasingPointName());
                degree = "北纬" + mapHelperManager.latlng2MinSec(purchasingPoint.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(purchasingPoint.getLongitude());
                pointViewHolder.pointIcon.setImageResource(R.mipmap.ic_navigation_buyer);
                if (((OrdinaryMapActivity) context).getCurrentLatLng() != null) {
                    int distance = (int) AMapUtils.calculateLineDistance(new LatLng(purchasingPoint.getLatitude(), purchasingPoint.getLongitude()), ((OrdinaryMapActivity) context).getCurrentLatLng());
                    if (distance > 1000) {
                        pointViewHolder.pointDistance.setText(((double) distance / 1000) + "km");
                    } else {
                        pointViewHolder.pointDistance.setText(distance + "m");
                    }
                }
            } else if (item instanceof DCEntity) {
                DCEntity dcEntity = (DCEntity) item;
                pointViewHolder.pointName.setText(dcEntity.getDcName());
                degree = "北纬" + mapHelperManager.latlng2MinSec(dcEntity.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(dcEntity.getLongitude());
                pointViewHolder.pointIcon.setImageResource(R.mipmap.ic_dc);
                if (((OrdinaryMapActivity) context).getCurrentLatLng() != null) {
                    int distance = (int) AMapUtils.calculateLineDistance(new LatLng(dcEntity.getLatitude(), dcEntity.getLongitude()), ((OrdinaryMapActivity) context).getCurrentLatLng());
                    if (distance > 1000) {
                        pointViewHolder.pointDistance.setText(((double) distance / 1000) + "km");
                    } else {
                        pointViewHolder.pointDistance.setText(distance + "m");
                    }
                }
            }
            pointViewHolder.pointLatlng.setText(degree);
        }
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Intent intent = new Intent();
        Object item = getItem(getPosition(position));
        if (item instanceof FarmEntity) {
            FarmEntity farmEntity = (FarmEntity) item;
            intent.setClass(context, FarmActivity.class);
            intent.putExtra("pointId", farmEntity.getFarmId());
        } else if (item instanceof PurchasingPoint) {
            PurchasingPoint purchasingPoint = (PurchasingPoint) item;
            intent.setClass(context, PointMainActivity.class);
            intent.putExtra("pointId", purchasingPoint.getPurchasingPointId());
        } else if (item instanceof DCEntity) {
            DCEntity dcEntity = (DCEntity) item;
            intent.setClass(context, DcActivity.class);
            intent.putExtra("pointId", dcEntity.getId());
        }
        context.startActivity(intent);
    }


    private static final class PointViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout pointItem;
        private TextView pointName;
        private TextView pointLatlng;
        private ImageView pointIcon;
        private TextView pointDistance;

        public PointViewHolder(View itemView) {
            super(itemView);
            pointName = (TextView) itemView.findViewById(R.id.tv_point_name);
            pointItem = (RelativeLayout) itemView.findViewById(R.id.rl_pointlist_item);
            pointLatlng = (TextView) itemView.findViewById(R.id.tv_point_latlng);
            pointIcon = (ImageView) itemView.findViewById(R.id.iv_point_icon);
            pointDistance = (TextView) itemView.findViewById(R.id.tv_point_distance);
        }
    }
}
