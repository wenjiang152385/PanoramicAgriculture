package com.oraro.panoramicagriculture.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;

/**
 * Created by admin on 2017/3/2.
 */
public class PointInfoAdapter extends BaseListAdapter<PointInfo> implements View.OnClickListener{
    private Activity context;

    @Override
    public void onClick(View v) {

    }

    public final class PointInfoHolder extends RecyclerView.ViewHolder{
        int position;
        ImageView id_icon;
        TextView id_name;
        TextView id_value;
        public PointInfoHolder(View view){
            super(view);
            id_icon = (ImageView)view.findViewById(R.id.id_icon);
            id_name = (TextView)view.findViewById(R.id.id_name);
            id_value = (TextView)view.findViewById(R.id.id_value);
        }

    }
    public PointInfoAdapter(Activity context, int mode){
        super(context,mode);
        this.context = context;
    }
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PointInfoHolder(mInflater.inflate(R.layout.item_point_info, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        PointInfoHolder holder = (PointInfoHolder) h;
        if (getDataSize() > position) {
            PointInfo pi = getItem(position);
            holder.position = position;
            holder.id_icon.setImageResource(pi.geticon());
            holder.id_name.setText(pi.getname());
            holder.id_value.setText(pi.getvalue());
        }
    }

}
