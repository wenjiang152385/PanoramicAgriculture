package com.oraro.panoramicagriculture.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by admin on 2017/4/11.
 */
public abstract class PointMainAdapterImpl<T extends Object>{

    List<T> datas;
    protected HolderData holderData;
    PointMainActivity activity;
    PointMainAdapter adapter;
    LayoutInflater mInflate;

    public int getPosition(int index) {
        return index + 3;
    }
    public void addItem(T obj){
        datas.add(obj);
        int position = datas.indexOf(obj);
        adapter.notifyItemInserted(getPosition(position));
    }

    public void addItem(int position, T obj) {
        datas.add(position, obj);
        adapter.notifyItemInserted(getPosition(position));
        //activity.getRecycleView().setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    public final void clear() {
        datas.clear();
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    public final T getItem(int position) {
        return datas.get(position);
    }

    public final int getDataSize() {
        return datas.size();
    }
    public final List<T> getDataSet() {
        return datas;
    }
    public void removeItem(T obj) {
        int position = datas.indexOf(obj);
        datas.remove(position);
        adapter.notifyItemRemoved(adapter.SUM_TOP_ROW_COUNT+position);
    }

    public void removeItemPostion(int position) {
        datas.remove(position);
        adapter.notifyItemRemoved(adapter.SUM_TOP_ROW_COUNT+position);
    }

    public final void addItems(int position, List<T> objs) {
        if (datas != null && objs != null && !objs.isEmpty()) {
            datas.addAll(position, objs);
            adapter.notifyDataSetChanged();
        }
        activity.getUi().dismissProgress();
    }

    public final void addItems(List<T> objs,int state) {
        if (datas != null && objs != null && !objs.isEmpty()) {
            datas.addAll(objs);
        }
        adapter.setState(state);
    }

    public PointMainAdapterImpl(PointMainActivity activity, PointMainAdapter adapter, HolderData holderData){
        this.activity = activity;
        this.adapter = adapter;
        this.holderData = holderData;
        mInflate = LayoutInflater.from(activity);
        this.datas = new ArrayList<>();

        if(activity.getAdapter() != null){
            clear();
            activity.getAdapter().setCurrentPage(0);
            activity.getAdapter().setState(PointManagerAdapter.STATE_LOAD_MORE);
        }
    }
    public List<T> getDatas(){return datas;}

    public abstract void loadData(PTHolderSwitcher switcher);
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
    public int getItemCount() {
        return datas.size();
    }
}
