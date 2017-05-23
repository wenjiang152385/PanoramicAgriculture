package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.ui.activity.DcActivity;
import com.oraro.panoramicagriculture.ui.activity.FarmActivity;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.utils.MapHelperManager;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
public class PointManagerAdapter extends BaseListAdapter<Object> implements BaseListAdapter.OnItemClickListener, BaseListAdapter.OnLoadingHeaderCallBack {
    private final String TAG = this.getClass().getSimpleName();
    private MapHelperManager mapHelperManager;
    private Context context;

    public PointManagerAdapter(Context context, int mode) {
        super(context, mode);
        this.context = context;
        mapHelperManager = MapHelperManager.getInstance();
        this.setOnItemClickListener(this);
        this.setOnLoadingHeaderCallBack(this);
    }

    public PointManagerAdapter(Context context, List<Object> items, int mode) {
        super(context, items, mode);
        this.context = context;
        mapHelperManager = MapHelperManager.getInstance();
        this.setOnItemClickListener(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PointViewHolder(mInflater.inflate(R.layout.point_manager_list_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        PointViewHolder pointViewHolder = (PointViewHolder) h;
        Log.e(TAG, "position=" + position + ",name=");
        if (getDataSize() > position && position >= 0) {
            if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_FARMER) {
                FarmEntity farmEntity = ((FarmEntity) getItem(getPosition(position)));
                Log.e(TAG, "position=" + position + ",name=" + farmEntity.getFarmName());
                pointViewHolder.pointName.setText(farmEntity.getFarmName());
                String degree = "北纬" + mapHelperManager.latlng2MinSec(farmEntity.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(farmEntity.getLongitude());
                pointViewHolder.pointLatlng.setText(degree);
                pointViewHolder.farmArea.setText(farmEntity.getTotalArea() + "亩");
            } else if (getItem(getPosition(position)) instanceof PurchasingPoint) {
                PurchasingPoint purchasingPoint = (PurchasingPoint) getItem(getPosition(position));
                pointViewHolder.pointName.setText(purchasingPoint.getPurchasingPointName());
                String degree = "北纬" + mapHelperManager.latlng2MinSec(purchasingPoint.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(purchasingPoint.getLongitude());
                pointViewHolder.pointLatlng.setText(degree);
                pointViewHolder.farmArea.setVisibility(View.GONE);
            } else if (getItem(getPosition(position)) instanceof DCEntity) {
                DCEntity dcEntity = ((DCEntity) getItem(getPosition(position)));
                pointViewHolder.pointName.setText(dcEntity.getDcName());
                String degree = "北纬" + mapHelperManager.latlng2MinSec(dcEntity.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(dcEntity.getLongitude());
                pointViewHolder.pointLatlng.setText(degree);
                pointViewHolder.farmArea.setText(dcEntity.getTotalArea() + "亩");
            }
            if (position % 3 == 1) {
                pointViewHolder.pointMainCrop.setImageResource(R.mipmap.maizi_icon);
            } else if (position % 3 == 2) {
                pointViewHolder.pointMainCrop.setImageResource(R.mipmap.yumi_icon);
            } else {
                pointViewHolder.pointMainCrop.setImageResource(R.mipmap.doulei_icon);
            }
        }
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Intent intent = new Intent();
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_FARMER) {
            intent.setClass(context, FarmActivity.class);
            intent.putExtra("pointId", ((FarmEntity) getItem(position)).getFarmId());
        } else if (getItem((position)) instanceof PurchasingPoint){
            intent.setClass(context, PointMainActivity.class);
            intent.putExtra("pointId", ((PurchasingPoint) getItem(position)).getPurchasingPointId());
        }else if (getItem((position)) instanceof DCEntity) {
            intent.putExtra("pointId", ((DCEntity) getItem(position)).getId());
            intent.setClass(context, DcActivity.class);
        }
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new PointHeaderViewHolder(mInflater.inflate(R.layout.point_manager_list_header_layout, parent, false));
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, "position=" + position + ",onBindHeaderHolder=");
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ROLE_PURCHASER) {
            ((PointHeaderViewHolder) holder).allPoint.setText(context.getString(R.string.all_purchaser));
        }
    }

    private static final class PointViewHolder extends RecyclerView.ViewHolder {
        private ImageView pointMainCrop;
        private TextView pointName;
        private TextView pointLatlng;
        private TextView farmArea;

        public PointViewHolder(View itemView) {
            super(itemView);
            pointName = (TextView) itemView.findViewById(R.id.tv_point_name);
            pointMainCrop = (ImageView) itemView.findViewById(R.id.iv_point_icon);
            pointLatlng = (TextView) itemView.findViewById(R.id.tv_point_latlng);
            farmArea = (TextView) itemView.findViewById(R.id.farm_area);
        }
    }

    private static final class PointHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView allPoint;

        public PointHeaderViewHolder(View itemView) {
            super(itemView);
            allPoint = (TextView) itemView.findViewById(R.id.all_point);
        }
    }
}
