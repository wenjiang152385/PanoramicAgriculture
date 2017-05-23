package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.ui.activity.EditNeedCountActivity;
import com.oraro.panoramicagriculture.ui.activity.EditPurchaseInfoActivity;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.ui.view.MyPointDetailView;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 2017/4/11.
 */
public class PurchaseNeedImpl extends PointMainAdapterImpl<PurchaseResults>{


    public PurchaseNeedImpl(PointMainActivity activity, PointMainAdapter adapter, HolderData holderData) {
        super(activity, adapter, holderData);
        activity.getRecycleView().setCanEdit(holderData.bYouself);
    }

    public void getList(String date){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("purchasingPointId",holderData.pointId);
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeData",1);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("pageNum", activity.getAdapter().getCurrentPage());
        jsonObject.addProperty("pageSize", PointMainAdapter.PAGE_COUNT);
        Log.e("yjd", "getPurchaseNeedsByDate" + date);
        activity.getPresenter().getPurchaseNote(this, "getPurchaseNeeds", jsonObject, activity.getAdapter().getCurrentLoadType());
    }

    public void revokeFarmHarvest(Long havid,int position){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("purchasingPointId", havid);
        jsonObject.addProperty("userId", holderData.userId);
        activity.getPresenter().revokePurchaseNote(this, "revokePurchaseNeeds", jsonObject,position);
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
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        ViewHolder holder = (ViewHolder) h;
        if (getDataSize() > position) {
            PurchaseResults prd = getItem(position);
            Log.e("bbb", "position==" + position + "prd==" + prd.toString());
            holder.position = position;
            holder.id_id.setText(prd.getId().toString());
            holder.cropsimage.setImageURI(Constants.SERVER_ADDRESS + prd.getVfcrops().getPath1());
            holder.id_cropsid.setText(prd.getVfcrops().getByname());
            holder.id_allkg.setText(prd.getNeedsNum().toString());
            holder.id_price.setText(prd.getActualNum().toString());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
            holder.id_time.setText(sf.format(prd.getDate()));
            if (prd.getDc()!=null) {
                holder.id_time2.setText(prd.getDc().getDcName());
            }
            holder.btn_detail_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.getRecycleView().hideEditView();
                    revokeFarmHarvest(getItem(position).getId(),position);
                }
            });
            holder.btn_detail_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.e("yjd" + "btn_detail_edit");
                    Intent intent = new Intent(activity, EditPurchaseInfoActivity.class);
                    intent.putExtra("id", getItem(position).getId());
                    intent.putExtra("name", getItem(position).getVfcrops().getByname());
                    intent.putExtra("ActualNum", getItem(position).getActualNum().toString());
                    intent.putExtra("neednum",getItem(position).getNeedsNum().toString());
                    intent.putExtra("data", getItem(position).getDate());
                    activity.startActivity(intent);
                }
            });
            holder.btn_detail_editneed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.e("yjd" + "btn_detail_editneed");
                    activity.getRecycleView().hideEditView();
                    Intent intent1 = new Intent(activity, EditNeedCountActivity.class);
                    Gson mGson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
                    intent1.putExtra(EditNeedCountActivity.EXTRA_EDIT_NEED_BEAN, mGson.toJson(getItem(position)));
                    activity.startActivity(intent1);
                }
            });
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
            id_MyDetailView.num1_type.setText(activity.getResources().getString(R.string.farm_detail_list_title2)+"：");
            id_MyDetailView.num2_type.setText(activity.getResources().getString(R.string.farm_detail_list_title3)+"：");
            id_MyDetailView.time1_type.setText(activity.getResources().getString(R.string.farm_detail_list_title4)+"：");
            id_MyDetailView.time2_type.setText("配送中心: ");
//            id_MyDetailView.time2.setVisibility(View.GONE);

            btn_detail_cancel = (TextView) view.findViewById(R.id.btn_detail_cancel);
            btn_detail_edit = (TextView) view.findViewById(R.id.btn_detail_edit);
            btn_detail_editneed = (TextView) view.findViewById(R.id.btn_detail_editneed);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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
