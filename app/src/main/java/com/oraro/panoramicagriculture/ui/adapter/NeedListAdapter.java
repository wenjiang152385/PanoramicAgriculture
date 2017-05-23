package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.oraro.panoramicagriculture.data.entity.NeedList;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.ui.activity.MPChartActivity;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.ui.activity.RegionCropPointActivity;
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
public class NeedListAdapter extends BaseListAdapter<NeedList> implements BaseListAdapter.OnItemClickListener {
    private ArrayAdapter<String> spinnerAdapter;
    private Context mContext;
    private NeedListFragment needListFragment;
    private int currentPostion;

    public NeedListAdapter(Context context, int mode, NeedListFragment fragment) {
        super(context, mode);
        needListFragment = fragment;
        mContext = context;
        setOnItemClickListener(this);
    }

    public NeedListAdapter(Context context, List<NeedList> items, int mode) {
        super(context, items, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new NeedListViewHolder(mInflater.inflate(R.layout.item_region_sale_info, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        NeedListViewHolder needListViewHolder = (NeedListViewHolder) h;
        NeedList needList = getItem(getPosition(position));
        if (needList.getVfcrops()!=null) {
            needListViewHolder.saleImage.setImageURI(Constants.SERVER_ADDRESS + needList.getVfcrops().getPath3());
            needListViewHolder.saleTitle.setText(needList.getVfcrops().getByname());
        }
        needListViewHolder.saleTotle.setText(needList.getTotalPurchaseNeeds() + "");
    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
//        View inflate = mInflater.inflate(R.layout.item_region_sale_info_header, parent, false);
//        NeedListHeadViewHolder needListViewHolder = new NeedListHeadViewHolder(inflate);
//        return needListViewHolder;
//    }
//
//    @Override
//    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
//        final NeedListHeadViewHolder holderHeader = (NeedListHeadViewHolder) holder;
//
//    }

    @Override
    public void onItemClick(int position, long id, View view) {
        mContext.startActivity(new Intent(mContext, RegionCropPointActivity.class));
    }

//    class NeedListHeadViewHolder extends RecyclerView.ViewHolder {
//        private Spinner regionSpinner;
//        private RadioGroup dateRadio;
//
//        public NeedListHeadViewHolder(View itemView) {
//            super(itemView);
//            regionSpinner = (Spinner) itemView.findViewById(R.id.region_spinner);
//            dateRadio = (RadioGroup) itemView.findViewById(R.id.rg_date);
//
//
//        }
//    }

    class NeedListViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView saleImage;
        private TextView saleTitle;
        private TextView saleTotle;

        public NeedListViewHolder(View itemView) {
            super(itemView);
            saleImage = (SimpleDraweeView) itemView.findViewById(R.id.sdv_sale_info);
            saleTitle = (TextView) itemView.findViewById(R.id.tv_sale_crop);
            saleTotle = (TextView) itemView.findViewById(R.id.tv_sale_totle);
        }
    }
}

