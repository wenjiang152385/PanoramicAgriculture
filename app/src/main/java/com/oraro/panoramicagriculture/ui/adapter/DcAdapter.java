package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.DCEntity;
import com.oraro.panoramicagriculture.data.entity.DCPurchaseData;
import com.oraro.panoramicagriculture.data.entity.Distribution;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.ui.activity.DcActivity;
import com.oraro.panoramicagriculture.ui.listener.DateSetListener;
import com.oraro.panoramicagriculture.ui.view.FarmFieldMapView;
import com.oraro.panoramicagriculture.ui.view.MyButtonView;
import com.oraro.panoramicagriculture.ui.view.MyDcDetailView;
import com.oraro.panoramicagriculture.ui.view.MyPointDetailView;
import com.oraro.panoramicagriculture.ui.view.MyPurchaseDCView;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.MapHelperManager;
import com.oraro.panoramicagriculture.utils.UIManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4 0004.
 *
 * @author
 */
public class DcAdapter extends RecyclerView.Adapter implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    protected static final int VIEW_TYPE_TOP1 = 0;
    protected static final int VIEW_TYPE_TOP2 = 1;
    protected static final int VIEW_TYPE_HEAD = 2;
    protected static final int VIEW_TYPE_HEAD_PURCHASE = 3;
    protected static final int VIEW_TYPE_HEAD_ORDER = 4;
    protected static final int VIEW_TYPE_HEAD_DC = 5;
    protected static final int VIEW_TYPE_MAP = 6;
    private List<DCPurchaseData> dcPurchaseDataList = new ArrayList<>();
    private List<PurchaseResults> purchaseResultsList=new ArrayList<>();
    private List<Distribution> distributionList=new ArrayList<>();
    private int STATE_MODE;
    private Context mContext;
    private final DcActivity activity;
    public DcViewHolderHeader dcViewHolderHeader;
    private PTHolder0 ptHolder0;
    private FarmFieldMapView mapView;
    private AMap aMap;

    public DcAdapter(Context context) {
        mContext = context;
        activity = (DcActivity) context;
    }
    public int getSTATE_MODE() {
        return STATE_MODE;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    private void onHot(View btnHot) {
        {
            dcViewHolderHeader.btn_purchase.setHot(false);
            dcViewHolderHeader.btn_order.setHot(false);
            dcViewHolderHeader.btn_dc.setHot(false);
            dcViewHolderHeader.id_btn_map.setHot(false);

        }

        ((MyButtonView) btnHot).setHot(true);
    }
    public DcViewHolderHeader getDcViewHolderHeader() {
        return dcViewHolderHeader;
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

    public void onResume() {
        STATE_MODE =VIEW_TYPE_HEAD_PURCHASE;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf =  new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(cal.getTime());
        activity.LoadDcpurchase(format);
    }

    public void setDcPurchaseDataList(List<DCPurchaseData> dcPurchaseDatas) {
        this.dcPurchaseDataList = dcPurchaseDatas;
        Log.e("jw", "dcPurchaseDataList.." + dcPurchaseDataList.size());
        notifyDataSetChanged();
    }
    public void setpurchaseResultsDataList(List<PurchaseResults> purchaseResultses){
        LogUtils.e("jw","purchaseResultsList="+purchaseResultsList.size());
        this.purchaseResultsList=purchaseResultses;
        notifyDataSetChanged();
    }
    public void setDistributionListData(List<Distribution> distributions){
        LogUtils.e("jw","purchaseResultsList="+distributions.size());
        this.distributionList=distributions;
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("jw", "onCreateViewHolder.." + viewType);
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case VIEW_TYPE_TOP1:
                ptHolder0 = new PTHolder0(inflater.inflate(R.layout.item_ptholder0, parent, false));
                ptHolder0.initData(activity.getHolderData());
                holder = ptHolder0;
                break;
            case VIEW_TYPE_TOP2:
                PTHolder1 ptHolder1 = new PTHolder1(inflater.inflate(R.layout.item_ptholder1, parent, false));
                ptHolder1.initData(activity.getHolderData());
                holder = ptHolder1;
                break;
            case VIEW_TYPE_HEAD:
                holder = new DcViewHolderHeader(inflater.inflate(R.layout.item_dc_head, parent, false));
                dcViewHolderHeader = (DcViewHolderHeader) holder;
                onHot(dcViewHolderHeader.btn_purchase);
                break;
            case VIEW_TYPE_HEAD_PURCHASE:
                holder = new PurchaseDCViewHolder(inflater.inflate(R.layout.item_purchase_detail, parent, false));
                break;
            case VIEW_TYPE_MAP:
                holder = new MapViewHolder(inflater.inflate(R.layout.item_map, parent, false));
                break;
            case VIEW_TYPE_HEAD_ORDER:
                holder=new OrderViewHolder(inflater.inflate(R.layout.item_plant_point_detail,parent,false));
                break;
            case VIEW_TYPE_HEAD_DC:
                holder=new DcenterViewHolder(inflater.inflate(R.layout.item_my_dcdetail_view,parent,false));
                break;
        }




        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtils.e("jw", "viewtype==" + holder.getItemViewType());
        int positionInList = position - 3;
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEAD:
                dcViewHolderHeader.btn_purchase.setOnClickListener(this);
                dcViewHolderHeader.btn_purchase.SetText("采购");
                dcViewHolderHeader.btn_order.setOnClickListener(this);
                dcViewHolderHeader.btn_order.SetText("订货");
                dcViewHolderHeader.btn_dc.setOnClickListener(this);
                dcViewHolderHeader.btn_dc.SetText("配送");
                dcViewHolderHeader.id_radioGroup.setOnCheckedChangeListener(this);
//                dcViewHolderHeader.id_more.setOnClickListener(this);
                dcViewHolderHeader.id_btn_map.SetText("地图");
                dcViewHolderHeader.id_btn_map.setOnClickListener(this);
                break;
            case VIEW_TYPE_HEAD_PURCHASE:
                PurchaseDCViewHolder pcvh = (PurchaseDCViewHolder) holder;
                LogUtils.e("jw", "dcPurchaseDataList==" + dcPurchaseDataList.size() + "position==" + positionInList);
                if (dcPurchaseDataList.size() > positionInList) {
                    DCPurchaseData data = dcPurchaseDataList.get(positionInList);
                    LogUtils.e("jw", "data==" + data);
                    pcvh.__position = position;
                    pcvh.position.setText(data.getDcid() + "");
                    if (data.getVFcrops() != null) {
                        pcvh.cropsimage.setImageURI(Constants.SERVER_ADDRESS + data.getVFcrops().getPath1());
                        pcvh.vfCrops_byname.setText(data.getVFcrops().getByname());
                    }
                    pcvh.plantNum.setText(String.valueOf(data.getExpectneedsNum()));
                    if (data.getFarmEntity() != null) {
                        pcvh.expectHarvestNum.setText(data.getFarmEntity().getFarmName());
                    }
                    try {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        Date mDate = sf.parse(data.getDate());
                        sf = new SimpleDateFormat("yyyy.MM.dd");
                        pcvh.plantTime.setText(sf.format(mDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case VIEW_TYPE_MAP:
                aMap.clear();
                DCEntity dcEntity = ((DcActivity) mContext).getDcEntity();
                LatLng latLng = new LatLng(dcEntity.getLatitude(), dcEntity.getLongitude());
                MapHelperManager mapHelperManager = MapHelperManager.getInstance();
                aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(mContext.getResources(), R.mipmap.dc_point_marker))));
                Circle circle = aMap.addCircle(new CircleOptions().
                        center(latLng).
                        radius(dcEntity.getCoverageArea() * 1000).
                        fillColor(Color.parseColor("#649db1cc")).
                        strokeColor(Color.parseColor("#647193ca")).
                        strokeWidth(1));
                float zoom = 0;
                double offset = 0;
                if (dcEntity.getCoverageArea() < 10) {
                    zoom = (float) (15 - dcEntity.getCoverageArea() / 3);
                    offset = 0.01 + 0.01 * dcEntity.getCoverageArea() / 3;
                } else if (dcEntity.getCoverageArea() < 50) {
                    zoom = (float) (12 - dcEntity.getCoverageArea() / 20);
                    offset = 0.1 + 0.1 * dcEntity.getCoverageArea() / 20;
                } else if (dcEntity.getCoverageArea() < 100) {
                    offset = 0.5;
                    zoom = 10;
                } else {
                    offset = 2.5;
                    zoom = 8;
                }
                Log.i("sysout", "getCoverageArea==" + dcEntity.getCoverageArea() + ",zoom=" + zoom);
                mapHelperManager.changeCameraPosition(aMap, new LatLng(dcEntity.getLatitude() - offset, dcEntity.getLongitude()), zoom, 0, 0, true);
                break;
            case VIEW_TYPE_HEAD_ORDER:
                OrderViewHolder orderViewHolder= (OrderViewHolder) holder;
                if (purchaseResultsList.size()>positionInList){
                    PurchaseResults data = purchaseResultsList.get(positionInList);
                    orderViewHolder.position = position;
                    orderViewHolder.id_id.setText(data.getId().toString());
                    orderViewHolder.cropsimage.setImageURI(Constants.SERVER_ADDRESS + data.getVfcrops().getPath1());
                    orderViewHolder.id_cropsid.setText(data.getVfcrops().getByname());
                    orderViewHolder.id_allkg.setText(data.getNeedsNum().toString());
                    orderViewHolder.id_price.setText(data.getActualNum().toString());
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
                    orderViewHolder.id_time.setText(sf.format(data.getDate()));
                    if (data.getDc()!=null) {
                        orderViewHolder.id_time2.setText(data.getDc().getDcName());
                    }
                }

                break;
            case VIEW_TYPE_HEAD_DC:
                 DcenterViewHolder dcenterViewHolder= (DcenterViewHolder) holder;
                if (distributionList.size()>positionInList){
                      Distribution data=distributionList.get(positionInList);
                      dcenterViewHolder.position=position;
                    dcenterViewHolder.id_id.setText(data.getId()+"");
                    if(data.getVfCrops()!=null){
                        dcenterViewHolder.cropsimage.setImageURI(Constants.SERVER_ADDRESS + data.getVfCrops().getPath1());
                        dcenterViewHolder.id_cropsid.setText(data.getVfCrops().getByname());
                    }
                    if (data.getPurchasingPoint()!=null) {
                        dcenterViewHolder.id_allkg.setText(data.getPurchasingPoint().getPurchasingPointName());
                    }
                    dcenterViewHolder.id_price.setText(data.getNum()+"");
                    dcenterViewHolder.id_time.setText(data.getPurchaseneedsId()+"");
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
                    dcenterViewHolder.id_time2.setText(sf.format(data.getStartDate()));
                }
                break;
        }


    }

    @Override
    public int getItemCount() {
        int count = 1;
       // LogUtils.e("jw", "STATE_MODE==" + STATE_MODE);
        switch (STATE_MODE) {
            case VIEW_TYPE_HEAD:
                break;
            case VIEW_TYPE_HEAD_PURCHASE:
                count += dcPurchaseDataList.size();
                break;
            case VIEW_TYPE_HEAD_ORDER:
                count+=purchaseResultsList.size();
                break;
            case VIEW_TYPE_MAP:
                count++;
                break;
            case VIEW_TYPE_HEAD_DC:
                count+=distributionList.size();
                break;
        }
        return count + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return VIEW_TYPE_TOP1;
        } else if (position == 1) {
            return VIEW_TYPE_TOP2;
        } else if (position == 2) {
            return VIEW_TYPE_HEAD;
        } else
            return STATE_MODE;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_purchase:
                onHot(v);
                STATE_MODE = VIEW_TYPE_HEAD_PURCHASE;
                onCheckedChanged(dcViewHolderHeader.id_radioGroup, dcViewHolderHeader.id_radioGroup.getCheckedRadioButtonId());
                dcViewHolderHeader.mShowDays.setVisibility(View.VISIBLE);
                break;
            case R.id.id_btn_order:
                onHot(v);
                STATE_MODE = VIEW_TYPE_HEAD_ORDER;
                onCheckedChanged(dcViewHolderHeader.id_radioGroup, dcViewHolderHeader.id_radioGroup.getCheckedRadioButtonId());
                dcViewHolderHeader.mShowDays.setVisibility(View.VISIBLE);
                break;
            case R.id.id_btn_dc:
                onHot(v);
                STATE_MODE = VIEW_TYPE_HEAD_DC;
                onCheckedChanged(dcViewHolderHeader.id_radioGroup, dcViewHolderHeader.id_radioGroup.getCheckedRadioButtonId());
                dcViewHolderHeader.mShowDays.setVisibility(View.VISIBLE);
                break;
            case R.id.id_btn_map:
                onHot(v);
                STATE_MODE = VIEW_TYPE_MAP;
                dcViewHolderHeader.mShowDays.setVisibility(View.GONE);
                break;
//            case R.id.id_more:
//                Intent intent = new Intent(mContext, MPChartActivity.class);;
//                if (STATE_MODE == VIEW_TYPE_HEAD_ORDER) {
//                    intent.putExtra("type", Constants.CHART_ROLE_DC_ORDER);
//                }else if (STATE_MODE == VIEW_TYPE_HEAD_PURCHASE) {
//                    intent.putExtra("type", Constants.CHART_ROLE_DC_PURCHASER);
//                }else if (STATE_MODE == VIEW_TYPE_HEAD_DC) {
//                    intent.putExtra("type", Constants.CHART_ROLE_DC_NEEDS);
//                }
//                intent.putExtra("pointId", activity.pointId);
//                mContext.startActivity(intent);
//                break;


        }
        notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == dcViewHolderHeader.id_radioGroup) {
            String date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Log.e("jw", "radio" + checkedId + "------>" + R.id.id_today);
            switch (checkedId) {
                case R.id.id_today:
                    date = sf.format(cal.getTime());
                    if (STATE_MODE==VIEW_TYPE_HEAD_PURCHASE) {

                        activity.LoadDcpurchase(date);
                    } else if (STATE_MODE==VIEW_TYPE_HEAD_ORDER){
                        activity.LoadOrderData(date);
                    }else if (STATE_MODE==VIEW_TYPE_HEAD_DC){
                        activity.LoadDCData(date);
                    }
                    break;
                case R.id.id_yesterday:
                    cal.add(cal.DATE, 1);
                    date = sf.format(cal.getTime());
                    if (STATE_MODE==VIEW_TYPE_HEAD_PURCHASE) {
                        activity.LoadDcpurchase(date);
                    } else if (STATE_MODE==VIEW_TYPE_HEAD_ORDER){
                        activity.LoadOrderData(date);
                    }else if (STATE_MODE==VIEW_TYPE_HEAD_DC){
                        activity.LoadDCData(date);
                    }
                    break;
                case R.id.id_threedaysago:
                    cal.add(cal.DATE, 2);
                    date = sf.format(cal.getTime());
                    if (STATE_MODE==VIEW_TYPE_HEAD_PURCHASE) {
                        activity.LoadDcpurchase(date);
                    } else if (STATE_MODE==VIEW_TYPE_HEAD_ORDER){
                        activity.LoadOrderData(date);
                    }else if (STATE_MODE==VIEW_TYPE_HEAD_DC){
                        activity.LoadDCData(date);
                    }
                    break;
                case R.id.id_more:
                    UIManager.loadDatePicker(activity, new DateSetListener() {
                        @Override
                        public void dataChoosed(String date) {
                            if (STATE_MODE==VIEW_TYPE_HEAD_PURCHASE) {
                                activity.LoadDcpurchase(date);
                            } else if (STATE_MODE==VIEW_TYPE_HEAD_ORDER){
                                activity.LoadOrderData(date);
                            }else if (STATE_MODE==VIEW_TYPE_HEAD_DC){
                                activity.LoadDCData(date);
                            }
                        }
                    });
                    break;

            }
        }
    }
    class DcenterViewHolder extends RecyclerView.ViewHolder{
        int position;
        SimpleDraweeView cropsimage;
        TextView id_cropsid;
        TextView id_allkg;
        TextView id_price;
        TextView id_time;
        TextView id_time2;
        TextView id_id;

        TextView btn_detail_cancel;
        TextView btn_detail_edit;
        TextView btn_detail_editneed;
        public DcenterViewHolder(View itemView) {
            super(itemView);
            MyDcDetailView id_MyDetailView = (MyDcDetailView)itemView.findViewById(R.id.id_MyDetailView);
            id_id = id_MyDetailView.position;
            cropsimage = id_MyDetailView.cropsimage;
            id_cropsid = id_MyDetailView.vfCrops_byname;
            id_price = id_MyDetailView.num1;
            id_allkg = id_MyDetailView.num2;
            id_time = id_MyDetailView.time1;
            id_time2 = id_MyDetailView.time2;

            btn_detail_cancel = (TextView) itemView.findViewById(R.id.btn_detail_cancel);
            btn_detail_edit = (TextView) itemView.findViewById(R.id.btn_detail_edit);
            btn_detail_editneed = (TextView) itemView.findViewById(R.id.btn_detail_editneed);
        }
    }

   public class DcViewHolderHeader extends RecyclerView.ViewHolder {

        private final MyButtonView btn_purchase;
        private final MyButtonView btn_order;
        private final MyButtonView btn_dc;
        private final LinearLayout mShowDays;
        public RadioGroup id_radioGroup;
//        private final TextView id_more;
        private final MyButtonView id_btn_map;

        public DcViewHolderHeader(View itemView) {
            super(itemView);
            id_btn_map = (MyButtonView) itemView.findViewById(R.id.id_btn_map);
            btn_purchase = (MyButtonView) itemView.findViewById(R.id.id_btn_purchase);
            btn_order = (MyButtonView) itemView.findViewById(R.id.id_btn_order);
            btn_dc = (MyButtonView) itemView.findViewById(R.id.id_btn_dc);
            mShowDays = (LinearLayout) itemView.findViewById(R.id.id_showDays);
            id_radioGroup = (RadioGroup) itemView.findViewById(R.id.id_radioGroup);
//            id_more = (TextView) itemView.findViewById(R.id.id_more);
        }
    }

    class PurchaseDCViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MyPurchaseDCView id_myDetailView;
        int __position;
        SimpleDraweeView cropsimage;
        TextView position;
        TextView vfCrops_byname;
        TextView plantNum;
        TextView expectHarvestNum;
        TextView plantTime;

        TextView btn_detail_cancel;
        TextView btn_detail_edit;
        TextView btn_detail_editneed;

        public PurchaseDCViewHolder(View itemView) {
            super(itemView);
            id_myDetailView = (MyPurchaseDCView) itemView.findViewById(R.id.id_my_purchaseDc);
            cropsimage = id_myDetailView.cropsimage;
            position = id_myDetailView.position;
            vfCrops_byname = id_myDetailView.vfCrops_byname;
            plantNum = id_myDetailView.num1;
            expectHarvestNum = id_myDetailView.num2;
            plantTime = id_myDetailView.time1;
            //expectHarvestTime = id_myDetailView.time2;

            btn_detail_cancel = id_myDetailView.btn_detail_cancel;
            btn_detail_cancel.setOnClickListener(this);
            btn_detail_edit = id_myDetailView.btn_detail_edit;
            btn_detail_edit.setOnClickListener(this);
            btn_detail_editneed = id_myDetailView.btn_detail_editneed;
            btn_detail_editneed.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
     class OrderViewHolder extends RecyclerView.ViewHolder {
         int position;
         SimpleDraweeView cropsimage;
         TextView id_cropsid;
         TextView id_allkg;
         TextView id_price;
         TextView id_time;
         TextView id_time2;
         TextView id_id;

         TextView btn_detail_cancel;
         TextView btn_detail_edit;
         TextView btn_detail_editneed;
         public OrderViewHolder(View itemView) {
             super(itemView);
             MyPointDetailView id_MyDetailView = (MyPointDetailView)itemView.findViewById(R.id.id_MyDetailView);
             id_id = id_MyDetailView.position;
             cropsimage = id_MyDetailView.cropsimage;
             id_cropsid = id_MyDetailView.vfCrops_byname;
             id_price = id_MyDetailView.num1;
             id_allkg = id_MyDetailView.num2;
             id_time = id_MyDetailView.time1;
             id_time2 = id_MyDetailView.time2;

             id_MyDetailView.id_tdbh.setText(activity.getResources().getString(R.string.farm_detail_list_title1));
             id_MyDetailView.num1_type.setText(activity.getResources().getString(R.string.farm_detail_list_title2)+"：");
             id_MyDetailView.num2_type.setText(activity.getResources().getString(R.string.farm_detail_list_title3)+"：");
             id_MyDetailView.time1_type.setText(activity.getResources().getString(R.string.farm_detail_list_title4)+"：");
             id_MyDetailView.time2_type.setText("配送中心: ");
//            id_MyDetailView.time2.setVisibility(View.GONE);

             btn_detail_cancel = (TextView) itemView.findViewById(R.id.btn_detail_cancel);
             btn_detail_edit = (TextView) itemView.findViewById(R.id.btn_detail_edit);
             btn_detail_editneed = (TextView) itemView.findViewById(R.id.btn_detail_editneed);



         }
     }
    class MapViewHolder extends RecyclerView.ViewHolder {

        public MapViewHolder(View itemView) {
            super(itemView);
            mapView = (FarmFieldMapView) itemView.findViewById(R.id.farm_field_mapview);
            mapView.onCreate(null);
            mapView.onResume();
            aMap = mapView.getMap();
            aMap.getUiSettings().setZoomGesturesEnabled(true);
            aMap.getUiSettings().setScaleControlsEnabled(true);
        }
    }


    public void recycleMapView(){
        if (mapView != null) {
            mapView.onDestroy();
        }
    }
}
