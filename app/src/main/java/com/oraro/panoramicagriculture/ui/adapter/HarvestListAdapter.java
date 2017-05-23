package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.oraro.citypickerview.model.CityModel;
import com.oraro.citypickerview.model.DistrictModel;
import com.oraro.citypickerview.model.ProvinceModel;
import com.oraro.citypickerview.utils.XmlParserHandler;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.HarvestList;
import com.oraro.panoramicagriculture.data.entity.NeedList;
import com.oraro.panoramicagriculture.ui.activity.MPChartActivity;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.activity.RegionCropPointActivity;
import com.oraro.panoramicagriculture.ui.fragment.HarvestListFragment;
import com.oraro.panoramicagriculture.ui.fragment.NeedListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2017/3/29 0029.
 *
 * @author
 */
public class HarvestListAdapter extends BaseListAdapter<HarvestList> implements BaseListAdapter.OnItemClickListener  {
    private ArrayAdapter<String> spinnerAdapter;
    private  Context mContext;
    private  HarvestListFragment harvestListFragment;
    private int currentPostion;
    public HarvestListAdapter(Context context, int mode, HarvestListFragment fragment) {
        super(context, mode);
        mContext=context;
       // harvestListFragment=fragment;
        setOnItemClickListener(this);
        //spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
       // spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // String[] regions = new String[]{"全城","建邺区", "雨花台区", "浦口区", "江宁区", "秦淮区", "玄武区", "鼓楼区", "栖霞区", "六合区","高淳区","白下区","下关区","其他"};
         // List<String> regionList=initProvinceDatas(context,((OrdinaryDataActivity) context).getProvince(),((OrdinaryDataActivity) context).getCity());
        //spinnerAdapter.addAll(regionList);
        //setOnLoadingHeaderCallBack(this);
    }

    public HarvestListAdapter(Context context, List<HarvestList> items, int mode) {
        super(context, items, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new HarvestListViewHolder(mInflater.inflate(R.layout.item_region_sale_info, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        HarvestListViewHolder harvestListViewHolder = (HarvestListViewHolder) h;
        HarvestList harvestList = getItem(getPosition(position));
        harvestListViewHolder.saleImage.setImageURI(Constants.SERVER_ADDRESS + harvestList.getVfcrops().getPath3());
        harvestListViewHolder.saleTitle.setText(harvestList.getVfcrops().getByname());
        harvestListViewHolder.saleTotle.setText(harvestList.getExpectHarvestNum()+"");
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
//        View inflate = mInflater.inflate(R.layout.item_harvest_info_header, parent, false);
//        HarvestListHeaderViewHolder harvestListHeaderViewHolder=new HarvestListHeaderViewHolder(inflate);
//        return  harvestListHeaderViewHolder;
//    }
//
//    @Override
//    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, final int position) {
//        final HarvestListHeaderViewHolder harvestListHeaderViewHolder= (HarvestListHeaderViewHolder) holder;
//
//
//    }

    @Override
    public void onItemClick(int position, long id, View view) {
        mContext.startActivity(new Intent(mContext, RegionCropPointActivity.class));
    }

//    class HarvestListHeaderViewHolder extends RecyclerView.ViewHolder{
//        private Spinner regionSpinner;
//        private RadioGroup dateRadio;
//        public HarvestListHeaderViewHolder(View itemView) {
//            super(itemView);
//            regionSpinner = (Spinner) itemView.findViewById(R.id.region_spinner);
//            dateRadio = (RadioGroup) itemView.findViewById(R.id.rg_date);
//            //regionSpinner.setAdapter(spinnerAdapter);
//        }
//    }
    class HarvestListViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView saleImage;
        private TextView saleTitle;
        private TextView saleTotle;

        public HarvestListViewHolder(View itemView) {
            super(itemView);
            saleImage = (SimpleDraweeView) itemView.findViewById(R.id.sdv_sale_info);
            saleTitle = (TextView) itemView.findViewById(R.id.tv_sale_crop);
            saleTotle = (TextView) itemView.findViewById(R.id.tv_sale_totle);

        }
    }
}
