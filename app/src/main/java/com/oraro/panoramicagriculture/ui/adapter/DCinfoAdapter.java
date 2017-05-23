package com.oraro.panoramicagriculture.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.FarmEntity;
import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
import com.oraro.panoramicagriculture.ui.activity.DCinfoActivity;
import com.oraro.panoramicagriculture.ui.activity.PurchaseDCActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.MapHelperManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5 0005.
 *
 * @author
 */
public class DCinfoAdapter extends BaseListAdapter<Object> implements BaseListAdapter.OnItemClickListener {
    private MapHelperManager mapHelperManager;
    private Context context;
    private DCinfoActivity dCinfoActivity;
    public DCinfoAdapter(Context context, int mode, DCinfoActivity activity) {
        super(context, mode);
        this.context = context;
        dCinfoActivity=activity;
        mapHelperManager = MapHelperManager.getInstance();
        this.setOnItemClickListener(this);
    }

    public DCinfoAdapter(Context context, List<Object> items, int mode) {
        super(context, items, mode);
        this.context = context;
        mapHelperManager = MapHelperManager.getInstance();
        this.setOnItemClickListener(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PointViewHolder(mInflater.inflate(R.layout.region_crop_point_list_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        PointViewHolder pointViewHolder = (PointViewHolder) h;
        if (getDataSize() > position) {
            Object item = getItem(getPosition(position));
            String degree = null;
            if (item instanceof FarmEntity) {
                FarmEntity farmEntity = (FarmEntity) item;
                pointViewHolder.pointName.setText(farmEntity.getFarmName());
                degree = "北纬" + mapHelperManager.latlng2MinSec(farmEntity.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(farmEntity.getLongitude());
            } else if (item instanceof PurchasingPoint) {
                PurchasingPoint purchasingPoint = (PurchasingPoint) item;
                pointViewHolder.pointName.setText(purchasingPoint.getPurchasingPointName());
                degree = "北纬" + mapHelperManager.latlng2MinSec(purchasingPoint.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(purchasingPoint.getLongitude());
            }else if (item instanceof DCEntity){
                DCEntity dcEntity = (DCEntity) item;
                pointViewHolder.pointName.setText(dcEntity.getDcName());
                degree = "北纬" + mapHelperManager.latlng2MinSec(dcEntity.getLatitude()) + "、" + "东经" + mapHelperManager.latlng2MinSec(dcEntity.getLongitude());
            }
            pointViewHolder.pointIcon.setImageResource(R.mipmap.farm_icon_black);
            pointViewHolder.pointDistance.setVisibility(View.GONE);
            pointViewHolder.pointLatlng.setText(degree);
        }
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Intent intent = new Intent();
        Object item = getItem(getPosition(position));
        if (item instanceof DCEntity) {
            DCEntity dcEntity = (DCEntity) item;
            intent.putExtra("dcId", dcEntity.getId());
            intent.putExtra("dcname",dcEntity.getDcName());
            LogUtils.e("jw","pointdcId....="+dcEntity.getId()+"pointname...name="+dcEntity.getDcName());
        }
        dCinfoActivity.setResult(Activity.RESULT_OK,intent);
        dCinfoActivity.finish();



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
