package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.PushMessageJson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */
public class MessageListAdapter extends BaseListAdapter<PushMessageJson> {
    public MessageListAdapter(Context context, int mode) {
        super(context, mode);
    }

    public MessageListAdapter(Context context, List<PushMessageJson> items, int mode) {
        super(context, items, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MessageViewHolder(mInflater.inflate(mContext.getResources().getLayout(R.layout.item_message), parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        MessageViewHolder holder = (MessageViewHolder) h;
        holder.messageTitleText.setText(getItem(position).getMessagetitle());
        holder.messageContentText.setText(getItem(position).getMessagecontent());
        holder.sendMessageYear.setText(String.valueOf(getItem(position).getSendTime().getYear() + 1900));
        DateFormat dateFormat = new SimpleDateFormat("M-d");
        holder.sendMessageTime.setText(dateFormat.format(getItem(position).getSendTime()));
    }


    private class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTitleText;
        private TextView messageContentText;
        private TextView sendMessageTime;
        private TextView sendMessageYear;


        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTitleText = (TextView) itemView.findViewById(R.id.tv_message_title);
            messageContentText = (TextView) itemView.findViewById(R.id.tv_message_content);
            sendMessageTime = (TextView) itemView.findViewById(R.id.tv_send_time);
            sendMessageYear = (TextView) itemView.findViewById(R.id.tv_send_year);
        }
    }
}
