package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.PurchaseResults;
import com.oraro.panoramicagriculture.presenter.OrderListPresenter;
import com.oraro.panoramicagriculture.ui.fragment.CropsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10 0010.
 *
 * @author
 */
public class OrderListActivity extends BaseActivity<OrderListPresenter> implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup id_radio;
    private RecyclerView recl_day;
    private List<PurchaseResults> purchaseResultsList = new ArrayList<>();
    private MyAdapter myAdapter;
    private long dcid;

    @Override
    public OrderListPresenter createPresenter() {
        return new OrderListPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.item_day_head);
         Intent intent= getIntent();
        dcid = intent.getLongExtra("dcid", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_radio = (RadioGroup) findViewById(R.id.id_radioGroup);
        recl_day = (RecyclerView) findViewById(R.id.rcl_day);
        recl_day.setLayoutManager(new LinearLayoutManager(this));
        id_radio.setOnCheckedChangeListener(this);
        myAdapter = new MyAdapter();
        recl_day.setAdapter(myAdapter);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
       String date = sf.format(cal.getTime());
        LoadOrderData(date);
    }

    public void onPurchaseData(List<PurchaseResults> result) {
        this.purchaseResultsList = result;
        myAdapter.notifyDataSetChanged();

    }
    public void LoadOrderData(String date ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dcId", dcid);
        jsonObject.addProperty("type", 2);
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("typeData",2);
        getPresenter().getPurchaseNeeds("getPurchaseNeeds", jsonObject);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String date;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        switch (checkedId) {
            case R.id.id_today:
                date = sf.format(cal.getTime());
                LoadOrderData(date);
                break;
            case R.id.id_yesterday:
                cal.add(cal.DATE, 1);
                date = sf.format(cal.getTime());
                LoadOrderData(date);
                break;
            case R.id.id_threedaysago:
                cal.add(cal.DATE, 2);
                date = sf.format(cal.getTime());
                LoadOrderData(date);
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        private SimpleDateFormat sf;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(OrderListActivity.this).inflate(R.layout.item_my_point_detail_view, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(inflate);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final PurchaseResults data = purchaseResultsList.get(position);
            holder.position.setText(data.getId().toString());
            holder.cropsimage.setImageURI(Constants.SERVER_ADDRESS + data.getVfcrops().getPath1());
             holder.vfCrops_byname.setText(data.getVfcrops().getByname());
            holder.num2.setText(data.getNeedsNum().toString());
            holder.num1.setText(data.getActualNum().toString());
            sf = new SimpleDateFormat("yyyy.MM.dd");
            holder.time1.setText(sf.format(data.getDate()));
            if (data.getDc()!=null) {
                holder.time2.setText(data.getDc().getDcName());
            }
            holder.id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent();
                    intent.putExtra("puchaseid",data.getId());
                    intent.putExtra("cropname",data.getVfcrops().getByname());
                    intent.putExtra("neednum",data.getNeedsNum().toString());
                    intent.putExtra("needdate",sf.format(data.getDate()));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return purchaseResultsList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout id_bottomlayout;
            public LinearLayout id_toplayout;
            public TextView btn_detail_cancel;
            public TextView btn_detail_editneed;
            public TextView btn_detail_edit;
            public TextView id_total;
            public TextView id_tdbh;
            public TextView position;
            public SimpleDraweeView cropsimage;
            public TextView vfCrops_byname;
            public TextView num1_type;
            public TextView num1;
            public TextView num2_type;
            public TextView num2;
            public TextView time1_type;
            public TextView time1;
            public TextView time2_type;
            public TextView time2;
            public FrameLayout id;

            public MyViewHolder(View itemView) {
                super(itemView);
                id = (FrameLayout) itemView.findViewById(R.id.fra_layout);
                id_bottomlayout = (LinearLayout) itemView.findViewById(R.id.id_bottomlayout);
                id_toplayout = (LinearLayout) itemView.findViewById(R.id.id_toplayout);
                btn_detail_cancel = (TextView)itemView.findViewById(R.id.btn_detail_cancel);
                btn_detail_editneed = (TextView)itemView.findViewById(R.id.btn_detail_editneed);
                btn_detail_edit = (TextView)itemView.findViewById(R.id.btn_detail_edit);
                id_total = (TextView)itemView.findViewById(R.id.id_total);
                id_tdbh = (TextView)itemView.findViewById(R.id.id_tdbh);
                position = (TextView)itemView.findViewById(R.id.position);
                vfCrops_byname = (TextView)itemView.findViewById(R.id.vfCrops_byname);
                num1_type = (TextView)itemView.findViewById(R.id.num1_type);
                num1 = (TextView)itemView.findViewById(R.id.num1);
                num2_type = (TextView)itemView.findViewById(R.id.num2_type);
                num2 = (TextView)itemView.findViewById(R.id.num2);
                time1_type = (TextView)itemView.findViewById(R.id.time1_type);
                time1 = (TextView)itemView.findViewById(R.id.time1);
                time2_type = (TextView)itemView.findViewById(R.id.time2_type);
                time2 = (TextView)itemView.findViewById(R.id.time2);
                cropsimage = (SimpleDraweeView)itemView.findViewById(R.id.cropsimage);
                num1_type.setText(OrderListActivity.this.getResources().getString(R.string.farm_detail_list_title2)+"：");
                num2_type.setText(OrderListActivity.this.getResources().getString(R.string.farm_detail_list_title3)+"：");
                time1_type.setText(OrderListActivity.this.getResources().getString(R.string.farm_detail_list_title4)+"：");
                time2_type.setText("配送中心: ");
                id_tdbh.setText(OrderListActivity.this.getResources().getString(R.string.farm_detail_list_title1));
            }
        }
    }
}
