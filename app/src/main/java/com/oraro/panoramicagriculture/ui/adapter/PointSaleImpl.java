package com.oraro.panoramicagriculture.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.PointSale;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.ui.view.MyPointDetailView;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 2017/4/11.
 */
public class PointSaleImpl extends PointMainAdapterImpl<PointSale> {

    public PointSaleImpl(PointMainActivity activity, PointMainAdapter adapter, HolderData holderData) {
        super(activity, adapter, holderData);
        activity.getRecycleView().setCanEdit(false);
    }

    public void getList(String date){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("purchasingPointId", holderData.pointId);
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("pageNum", activity.getAdapter().getCurrentPage());
        jsonObject.addProperty("pageSize", PointMainAdapter.PAGE_COUNT);

        Log.e("yjd9", "getSalesData" + date);
        activity.getPresenter().getSalesData(this, "getSalesData", jsonObject,activity.getAdapter().getCurrentLoadType());
    }
    @Override
    public void loadData(PTHolderSwitcher switcher) {
        if (switcher == null){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cl = Calendar.getInstance();
            String date = sf.format(cl.getTime());
            getList(date);
        }else {
            getList(switcher.getDate());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflate.inflate(R.layout.item_purchase_point_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        ViewHolder holder = (ViewHolder) h;
        if (getDataSize() > position) {
            PointSale prd = getItem(position);
            Log.e("bbb", "position==" + position + "prd==" + prd.toString());
            holder.position = position;
            holder.id_id.setText(String.valueOf(prd.getId()));
            holder.cropsimage.setImageURI(Constants.SERVER_ADDRESS + prd.getVfcrops().getPath1());
            holder.id_cropsid.setText(prd.getVfcrops().getByname());
            holder.id_allkg.setText(String.valueOf(prd.getSaleNum()));
            holder.id_price.setText(String.valueOf(prd.getSaleNum()));
            SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
            holder.id_time.setText(sf.format(prd.getDate()));
        }
    }


    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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

        LinearLayout id_sliderlayout;

        public ViewHolder(View view) {
            super(view);
            MyPointDetailView id_MyDetailView = (MyPointDetailView)view.findViewById(R.id.id_MyDetailView);
            id_id = id_MyDetailView.position;
            cropsimage = id_MyDetailView.cropsimage;
            id_cropsid = id_MyDetailView.vfCrops_byname;
            id_price = id_MyDetailView.num1;
            id_allkg = id_MyDetailView.num2;
            id_time = id_MyDetailView.time1;
            id_time2 = id_MyDetailView.time2;

            id_MyDetailView.id_tdbh.setText(activity.getResources().getString(R.string.farm_detail_list_title1));
            id_MyDetailView.num1_type.setText(activity.getResources().getString(R.string.farm_detail_list_sale_num_title)+"：");
            id_MyDetailView.time1_type.setText(activity.getResources().getString(R.string.farm_detail_list_sale_time_title)+"：");

            id_MyDetailView.num2_type.setVisibility(View.GONE);
            id_MyDetailView.num2.setVisibility(View.GONE);
            id_MyDetailView.time2_type.setVisibility(View.GONE);
            id_MyDetailView.time2.setVisibility(View.GONE);

            btn_detail_cancel = (TextView) view.findViewById(R.id.btn_detail_cancel);
            btn_detail_cancel.setOnClickListener(this);
            btn_detail_edit = (TextView) view.findViewById(R.id.btn_detail_edit);
            btn_detail_edit.setOnClickListener(this);
            btn_detail_editneed = (TextView) view.findViewById(R.id.btn_detail_editneed);
            btn_detail_editneed.setOnClickListener(this);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
//
            RefreshButton(btn_detail_edit);
            RefreshButton(btn_detail_cancel);
            RefreshButton(btn_detail_editneed);
        }
        public void RefreshButton(TextView btn){
//            if (holderData.bYouself){
//                btn.setVisibility(View.VISIBLE);
//            }else{
//                btn.setVisibility(View.GONE);
//            }
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_detail_cancel://删除
//                    revokeFarmHarvest(Long.parseLong(id_id.getText().toString()));
//                    notifyDataSetChanged();
                    activity.getRecycleView().hideEditView();
                    Toast.makeText(activity, "Click ", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_detail_edit:
//                    LogUtils.e("yjd" + "btn_detail_edit");
//                    Intent intent = new Intent(mFragment.getActivity(), EditPurchaseInfoActivity.class);
//                    intent.putExtra("id", getItem(position).getId());
//                    intent.putExtra("name", getItem(position).getVfCrops().getVfname());
//                    intent.putExtra("ActualNum", getItem(position).getActualNum().toString());
//                    intent.putExtra("neednum",getItem(position).getNeedsNum().toString());
//                    intent.putExtra("data", getItem(position).getDate());
//                    mFragment.getActivity().startActivity(intent);
                    Toast.makeText(activity, "Click ", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_detail_editneed://编辑
//                    LogUtils.e("yjd" + "btn_detail_editneed");
//                    Intent intent1 = new Intent(mFragment.getActivity(), EditNeedCountActivity.class);
//                    intent1.putExtra("id1", getItem(position).getId());
//                    intent1.putExtra("name1", getItem(position).getVfCrops().getVfname());
//                    intent1.putExtra("ActualNum", getItem(position).getActualNum().toString());
//                    intent1.putExtra("needcount", getItem(position).getNeedsNum().toString());
//                    intent1.putExtra("date", getItem(position).getDate());
//                    mFragment.getActivity().startActivity(intent1);
                    activity.getRecycleView().hideEditView();
                    Toast.makeText(activity, "Click ", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(activity, "Click ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(activity, "LongClick ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
