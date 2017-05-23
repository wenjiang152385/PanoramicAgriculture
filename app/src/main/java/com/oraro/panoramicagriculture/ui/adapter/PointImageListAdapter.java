package com.oraro.panoramicagriculture.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.bean.ImageItem;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public class PointImageListAdapter extends BaseListAdapter<ImageItem> implements BaseListAdapter.OnLoadingFooterCallBack {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    public PointImageListAdapter(Context context, List<ImageItem> items, int mode) {
        super(context, items, mode);
    }

    public PointImageListAdapter(Context context, int mode) {
        super(context, mode);
        mContext = context;
        setOnLoadingFooterCallBack(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SelectedImageViewHolder(mInflater.inflate(R.layout.item_point_image, parent, false));
    }


    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final int position) {
        Log.i(TAG,"onBindDefaultViewHolder "+position);
        SelectedImageViewHolder selectedImageViewHolder = (SelectedImageViewHolder) h;
        if (getDataSize() > position) {
            ImageItem imageItem = getItem(position);
            Log.i(TAG, position + "," + getItem(position));
            if ("network".equalsIgnoreCase(imageItem.mimeType)) {
                selectedImageViewHolder.iv_img.setImageURI(Constants.SERVER_ADDRESS + imageItem.path);
            } else {
                selectedImageViewHolder.iv_img.setImageURI(Uri.fromFile(new File(imageItem.path)));

                Picasso.with(mContext)//
                        .load(new File(imageItem.path))//
                        .placeholder(R.mipmap.default_image)//
                        .error(R.mipmap.default_image)//
                        .resize(40, 40)//
                        .centerInside()//
                        .into(selectedImageViewHolder.iv_img);
            }
        }
        selectedImageViewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"v "+position);
                removeItem(getItem(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterHolder(ViewGroup parent) {
        return new SelectedImageViewHolder(mInflater.inflate(R.layout.item_point_image, parent, false));
    }

    @Override
    public void onBindFooterHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "onBindFooterHolder..position=" + position + ",dataSize=" + getDataSize());
        SelectedImageViewHolder selectedImageViewHolder = (SelectedImageViewHolder) holder;
        selectedImageViewHolder.iv_delete.setVisibility(View.GONE);
        if (getDataSize() > 3) {
            selectedImageViewHolder.itemView.setVisibility(View.GONE);
        } else {
            selectedImageViewHolder.itemView.setVisibility(View.VISIBLE);
            selectedImageViewHolder.iv_img.setImageResource(R.drawable.selector_image_add);
            selectedImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.getItemId(), v);
                }
            });
        }
    }

    public class SelectedImageViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView iv_img;
        private ImageView iv_delete;

        public SelectedImageViewHolder(View itemView) {
            super(itemView);
            iv_img = (SimpleDraweeView) itemView.findViewById(R.id.iv_img);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
}
