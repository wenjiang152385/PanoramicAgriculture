package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.Crops;
import com.oraro.panoramicagriculture.presenter.CropsDialogPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3 0003.
 *
 * @author
 */
public class CropsDialogActivity extends BaseActivity<CropsDialogPresenter> {

    private ImageView iv_save;
    private RecyclerView recycle_button;
    private ArrayList<String> names;
    private ArrayList<Integer> ids;
    private MyItemAdapter myItemAdapter;


    @Override
    public CropsDialogPresenter createPresenter() {
        return new CropsDialogPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_crops_dialog);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        names = bundle.getStringArrayList("name");
        ids = bundle.getIntegerArrayList("id");
        Log.e("ccc", "names==" + names + "....ids==" + ids);
        iv_save = (ImageView) findViewById(R.id.iv_save);
        recycle_button = (RecyclerView) findViewById(R.id.recycle_button);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_button.setLayoutManager(new GridLayoutManager(this, 3));
        myItemAdapter = new MyItemAdapter();
        recycle_button.setAdapter(myItemAdapter);
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putStringArrayList("names", names);
                bundle1.putIntegerArrayList("ids", ids);
                intent.putExtras(bundle1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.MyItemViewHolder> {

        private View inflate;

        @Override
        public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflate = LayoutInflater.from(CropsDialogActivity.this).inflate(R.layout.recycle_dialog_item, parent, false);
            MyItemViewHolder myItemViewHolder = new MyItemViewHolder(inflate);
            return myItemViewHolder;
        }

        @Override
        public void onBindViewHolder(MyItemViewHolder holder, final int position) {
            holder.textView.setText(names.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String remove = names.remove(position);
                    ids.remove(position);
                    myItemAdapter.notifyDataSetChanged();
                    notifyItemChanged(position);
                    if (position != names.size() && position != ids.size()) { // 如果移除的是最后一个，忽略
                        notifyItemRangeChanged(0, names.size() - position);
                        notifyItemRangeChanged(0, ids.size() - position);
                    }
                    Log.e("jjj", "remove--" + remove + ",names=" + names);
                }
            });
        }

        @Override
        public int getItemCount() {
            return names.size();
        }

        class MyItemViewHolder extends RecyclerView.ViewHolder {

            private final ImageView imageView;
            private final TextView textView;

            public MyItemViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.select_cancel);
                textView = (TextView) itemView.findViewById(R.id.button_text);
            }
        }
    }
}
