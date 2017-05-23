package com.oraro.panoramicagriculture.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.oraro.citypickerview.model.CityModel;
import com.oraro.citypickerview.model.DistrictModel;
import com.oraro.citypickerview.model.ProvinceModel;
import com.oraro.citypickerview.utils.XmlParserHandler;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.ui.activity.MPChartActivity;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.activity.RegionCropPointActivity;
import com.oraro.panoramicagriculture.ui.animation.AlphaInAnimation;
import com.oraro.panoramicagriculture.ui.animation.BaseAnimation;
import com.oraro.panoramicagriculture.ui.animation.ScaleInAnimation;
import com.oraro.panoramicagriculture.ui.fragment.RegionSaleListFragment;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2017/3/29.
 */
public class RegionSaleListAdapter extends BaseListAdapter<RegionSaleInfo> implements BaseListAdapter.OnItemClickListener {
    private Context mContext;
    private RegionSaleListFragment fragment;


    public RegionSaleListAdapter(RegionSaleListFragment regionSaleListFragment, int mode) {
        this(regionSaleListFragment.getActivity(), mode);
        fragment = regionSaleListFragment;
    }

    public RegionSaleListAdapter(Context context, int mode) {
        super(context, mode);
        mContext = context;
        setOnItemClickListener(this);
    }

    public RegionSaleListAdapter(Context context, List<RegionSaleInfo> items, int mode) {
        super(context, items, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new RegionListViewHolder(mInflater.inflate(R.layout.item_region_sale_info, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        RegionListViewHolder holder = (RegionListViewHolder) h;
        RegionSaleInfo regionSaleInfo = getItem(getPosition(position));
        holder.saleImage.setImageURI(Constants.SERVER_ADDRESS + regionSaleInfo.getVfcrops().getPath3());
        holder.saleTitle.setText(regionSaleInfo.getVfcrops().getByname());
        holder.saleTotle.setText(regionSaleInfo.getSaleNumTotal().toString());
    }


    @Override
    public void onItemClick(int position, long id, View view) {
        mContext.startActivity(new Intent(mContext, RegionCropPointActivity.class));
    }




   public class RegionListViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView saleImage;
        private TextView saleTitle;
        private TextView saleTotle;

        public RegionListViewHolder(View itemView) {
            super(itemView);
            saleImage = (SimpleDraweeView) itemView.findViewById(R.id.sdv_sale_info);
            saleTitle = (TextView) itemView.findViewById(R.id.tv_sale_crop);
            saleTotle = (TextView) itemView.findViewById(R.id.tv_sale_totle);
        }
    }
}
