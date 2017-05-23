package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.ItemView;
import com.view.jameson.library.CardAdapterHelper;

import java.util.List;


/**
 * Created by jameson on 8/30/16.
 */
public class CardAdapter extends BaseListAdapter {
//    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public CardAdapter(Context context, int mode) {
        super(context, mode);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
//        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
//        mCardAdapterHelper.onBindViewHolder(h.itemView, position, getItemCount());
        if (position < getItemCount()) {
            ViewHolder chartViewHolder = (ViewHolder) h;
            ItemView itemView = (ItemView) getItem(position);
            chartViewHolder.mName.setText(itemView.getName());
            chartViewHolder.mDate.setText(itemView.getDate());
            chartViewHolder.mEnumber.setText(itemView.geteNumber());
            chartViewHolder.mRnumber.setText(itemView.getrNumber());
            chartViewHolder.mPie.setText(itemView.getPie());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mDate;
        private TextView mEnumber;
        private TextView mRnumber;
        private TextView mPie;

        public ViewHolder(final View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.crops_name);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mEnumber = (TextView) itemView.findViewById(R.id.enumber);
            mRnumber = (TextView) itemView.findViewById(R.id.rnumber);
            mPie = (TextView) itemView.findViewById(R.id.pie);
        }

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
            outRect.right = mRightSpace;
//            }

//            outRect.bottom = mBottomSpace;
        }
    }

}
