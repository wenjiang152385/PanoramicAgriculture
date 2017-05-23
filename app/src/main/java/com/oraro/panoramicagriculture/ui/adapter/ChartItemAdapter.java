package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.ItemView;
import com.oraro.panoramicagriculture.presenter.DetailChartPresenter;

import java.util.List;


/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class ChartItemAdapter extends BaseListAdapter{

    private List<IPieDataSet> mDataSet;


    public ChartItemAdapter(Context context, int mode) {
        super(context, mode);
    }



    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,null));

    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        if (position < getItemCount()) {
            ChartViewHolder chartViewHolder = (ChartViewHolder) h;
            ItemView itemView = (ItemView) getItem(position);
            chartViewHolder.mName.setText(itemView.getName());
            chartViewHolder.mDate.setText(itemView.getDate());
            chartViewHolder.mNumber.setText(itemView.getrNumber());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private class ChartViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mCard;
        private TextView mName;
        private TextView mDate;
        private TextView mNumber;

        public ChartViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.crops_name);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mNumber = (TextView) itemView.findViewById(R.id.enumber);
            mCard = (RelativeLayout) itemView.findViewById(R.id.rl_card);
        }
    }

    public final void addItems(List<DetailChartPresenter.ItemView> objs, List<IPieDataSet> dataSet) {
        super.addItems(objs);
        mDataSet = dataSet;
    }

    public class  SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int mTopSpace = 0;
        private int mLeftSpace = 0;
        private int mBottomSpace = 0;
        private int mRightSpace = 0;


        public  SpaceItemDecoration (int topSpace,int leftSpace,int bottomSpace, int rightSpace) {
            mTopSpace = topSpace;
            mLeftSpace = leftSpace;
            mRightSpace = rightSpace;
            mBottomSpace = bottomSpace;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
//            int index = parent.getChildLayoutPosition(view) + 2;

//            if (index % 2 != 0) {
                outRect.left = mLeftSpace;
//            }

//            outRect.bottom = mBottomSpace;
        }
    }
}
