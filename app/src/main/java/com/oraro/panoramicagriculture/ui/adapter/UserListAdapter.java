package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
public class UserListAdapter extends BaseListAdapter<UserEntity> {
    private Context mContext;

    public UserListAdapter(Context context, int mode) {
        super(context, mode);
        mContext = context;
    }

    public UserListAdapter(Context context, List<UserEntity> items, int mode) {
        super(context, items, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new UserListViewHolder(mInflater.inflate(R.layout.item_user_list, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        UserListViewHolder hold = (UserListViewHolder) h;
        hold.phoneNum.setText(getItem(position).getPhoneNumber());

        hold.userImage.setImageURI(Constants.SERVER_ADDRESS + getItem(position).getHead());
        hold.userNickName.setText(getItem(position).getNickName());
        hold.userPoints.setText("积分：" + getItem(position).getPoints());
    }

    private class UserListViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView userImage;
        private TextView userNickName;
        private TextView phoneNum;
        private TextView userPoints;

        public UserListViewHolder(View itemView) {
            super(itemView);
            userImage = (SimpleDraweeView) itemView.findViewById(R.id.sdv_sale_info);
            userNickName = (TextView) itemView.findViewById(R.id.tv_sale_crop);
            phoneNum = (TextView) itemView.findViewById(R.id.tv_sale_totle);
            userPoints = (TextView) itemView.findViewById(R.id.tv_user_points);
        }
    }
}
