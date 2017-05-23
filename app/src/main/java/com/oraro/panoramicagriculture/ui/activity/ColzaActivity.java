package com.oraro.panoramicagriculture.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.VFCrops;
import com.oraro.panoramicagriculture.presenter.ColzaPresenter;
import com.oraro.panoramicagriculture.ui.view.anim.AddCartAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20 0020.
 *
 * @author
 */
public class ColzaActivity extends BaseActivity<ColzaPresenter> {

    private RecyclerView recycler1;
    private MyAdapter1 myAdapter1;
    private ImageView imageView;
    private ArrayList<VFCrops> cropsList = new ArrayList<>();
    private RelativeLayout relative_parent;

    @Override
    public ColzaPresenter createPresenter() {
        return new ColzaPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                break;
            case R.id.action_save:

                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void initView() {
        setContentView(R.layout.activity_colza);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("菜品种类");
        recycler1 = (RecyclerView) findViewById(R.id.rc_colza);
        relative_parent = (RelativeLayout) findViewById(R.id.relative_parent);
        imageView = (ImageView) findViewById(R.id.iv_save);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler1.setLayoutManager(new GridLayoutManager(this, 3));
        myAdapter1 = new MyAdapter1();
        recycler1.setAdapter(myAdapter1);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type1", "粮食作物");
        showProgress();
        getPresenter().requestCropsData("getVFcrops", jsonObject);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jww", "mmmmmmm");
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<Integer> ids = new ArrayList<>();

                for (int i = 0; i < cropsList.size(); i++) {
                    String name = cropsList.get(i).getByname();
                    int cropid = new Long((cropsList.get(i).getVfid())).intValue();
                    names.add(name);
                    ids.add(cropid);

                }
                bundle.putStringArrayList("names", names);
                bundle.putIntegerArrayList("ids", ids);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(getActivity(), CropsDialogActivity.class);
//                ArrayList<String> names = new ArrayList<String>();
//                ArrayList<Integer> ids = new ArrayList<Integer>();
//                for (int i = 0; i < cropsList.size(); i++) {
//                    String name = cropsList.get(i).getVfname();
//                    int cropid = new Long(cropsList.get(i).getVfid()).intValue();
//                    names.add(name);
//                    ids.add(cropid);
//                }
//                bundle.putStringArrayList("name", names);
//                bundle.putIntegerArrayList("id", ids);
//                intent.putExtras(bundle);
//                getActivity().startActivityForResult(intent, 1);

            }
        });

    }

    public void setDataColza(List<VFCrops> list) {
        myAdapter1.setRecycler1(list);
        myAdapter1.notifyDataSetChanged();

    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("jjj", "requestCode==" + requestCode);
//
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Log.e("jjww", "extras==" + extras);
//            ArrayList<String> names = extras.getStringArrayList("names");
//
//            ArrayList<Integer> ids = extras.getIntegerArrayList("ids");
//            Intent intent = new Intent();
//            Bundle bundle1 = new Bundle();
//            bundle1.putStringArrayList("names", names);
//            bundle1.putIntegerArrayList("ids", ids);
//            intent.putExtras(bundle1);
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//    }
    class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {
        public List<VFCrops> colzalist = new ArrayList<>();

        public void setRecycler1(List<VFCrops> list) {
            colzalist = list;
            notifyDataSetChanged();

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View inflate = LayoutInflater.from(ColzaActivity.this).inflate(R.layout.item_colza, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(inflate);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final VFCrops crops = colzalist.get(position);
            holder.textView.setText(crops.getByname());
            holder.ll_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.ischeck) {
                        cropsList.add(crops);
                        holder.ll_bt.setBackgroundResource(R.drawable.kuang_huang);
                        holder.ll_bt.setDrawingCacheEnabled(true);
                        Bitmap bitmap = holder.ll_bt.getDrawingCache();

                        AddCartAnimation.AddToCart(holder.ll_bt, bitmap, imageView, ColzaActivity.this, relative_parent, 1);
                        holder.ischeck = false;
                    } else {
                        cropsList.remove(crops);
                        holder.ll_bt.setBackgroundResource(R.drawable.kuang1);
                        holder.ischeck = true;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return colzalist.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView;
            private final LinearLayout ll_bt;
            boolean ischeck = true;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.button_1);
                ll_bt = (LinearLayout) itemView.findViewById(R.id.ll_bt);

            }
        }
    }
}
