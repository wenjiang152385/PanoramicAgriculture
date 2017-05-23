package com.oraro.panoramicagriculture.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.animation.AlphaInAnimation;
import com.oraro.panoramicagriculture.ui.animation.AnimationType;
import com.oraro.panoramicagriculture.ui.animation.BaseAnimation;
import com.oraro.panoramicagriculture.ui.animation.CustomAnimation;
import com.oraro.panoramicagriculture.ui.animation.ScaleInAnimation;
import com.oraro.panoramicagriculture.ui.animation.SlideInBottomAnimation;
import com.oraro.panoramicagriculture.ui.animation.SlideInLeftAnimation;
import com.oraro.panoramicagriculture.ui.animation.SlideInRightAnimation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015-2-4.
 */
public abstract class BaseListAdapter<T extends Object> extends RecyclerView.Adapter {
    private final String TAG = "BaseListAdapter";
    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;

    public final int BEHAVIOR_MODE;

    public static final int NEITHER = 0;
    public static final int ONLY_HEADER = 1;
    public static final int ONLY_FOOTER = 2;
    public static final int BOTH_HEADER_FOOTER = 3;

    protected static final int VIEW_TYPE_NORMAL = 0;
    protected static final int VIEW_TYPE_HEADER = -1;
    protected static final int VIEW_TYPE_FOOTER = -2;

    protected Context mContext;
    protected List<T> items;
    protected LayoutInflater mInflater;
    protected int mState;

    protected OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnLoadingListener onLoadingListener;
    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;
    private OnLoadingFooterCallBack onLoadingFooterCallBack;

    private BaseAnimation mCustomAnimation = new ScaleInAnimation();
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;

    public boolean ismOpenAnimationEnable() {
        return mOpenAnimationEnable;
    }

    public void setmOpenAnimationEnable(boolean mOpenAnimationEnable) {
        this.mOpenAnimationEnable = mOpenAnimationEnable;
    }

    public BaseListAdapter(Context context, int mode) {
        this(context, new ArrayList<T>(), mode);
    }

    public BaseListAdapter(Context context, List<T> items, int mode) {
        this.mContext = context;
        this.items = items;
        mState = STATE_HIDE;
        mInflater = LayoutInflater.from(mContext);
        BEHAVIOR_MODE = mode;
    }

    public final void addItem(T obj) {
        items.add(obj);
        int position = items.indexOf(obj);
        notifyItemInserted(position);
    }

    public final void addItem(int position, T obj) {
        items.add(position, obj);
        notifyItemInserted(position);
    }

    public final void addItems(List<T> objs) {
        if (items == null || objs == null) {
            return;
        }
        Log.i(TAG, items.size() + " addItems.." + objs.size());
        addItems(items.size(), objs);
    }

    public final void addItems(int position, List<T> objs) {
        if (items != null && objs != null && !objs.isEmpty()) {
            items.addAll(position, objs);
            Log.i(TAG, items.size() + " addItems.." + getPosition(position));
            notifyItemRangeInserted(getPosition(position), objs.size());
        }
    }

    public final T getItem(int position) {
        return items.get(getIndex(position));
    }

    //这个用不了辣
    public void removeObjectForId(long id) {
        Iterator<T> iterator = items.iterator();
        while (iterator.hasNext()) {
            T obj = iterator.next();
//            if (obj.getId() == id){
//                removeItem(obj);
//            }
        }
    }

    public void removeItem(T obj) {
        int position = items.indexOf(obj);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public int getIndex(int position) {
        return BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? position - 1 : position;
    }

    public int getPosition(int index) {
        return BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? index + 1 : index;
    }

    public final void clear() {
        items.clear();
        mState = STATE_HIDE;
        notifyDataSetChanged();
    }

    public final List<T> getDataSet() {
        return items;
    }

    public final void setState(int state) {
        mState = state;
        if (state == STATE_LOAD_ERROR) {
            if (BEHAVIOR_MODE == ONLY_FOOTER) {
                notifyItemChanged(getDataSize());
            } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
                notifyItemChanged(getDataSize() + 1);
            }
        }
    }

    public final int getState() {
        return mState;
    }

    public final int getDataSize() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType..." + position + "," + BEHAVIOR_MODE);
        if (position == 0 && (BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_HEADER;
        if (position + 1 == getItemCount() && (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_FOOTER;
        else return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == ONLY_HEADER) {
            return items.size() + 1;
        } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
            return items.size() + 2;
        } else return items.size();
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        Log.i(TAG, "onCreateViewHolder.." + type);
        switch (type) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    return onLoadingHeaderCallBack.onCreateHeaderHolder(parent);
                else throw new IllegalArgumentException("你使用了VIEW_TYPE_HEADER模式,但是你没有实现相应的接口");
            case VIEW_TYPE_FOOTER:
                if (onLoadingFooterCallBack != null) {
                    return onLoadingFooterCallBack.onCreateFooterHolder(parent);
                } else {
                    return new FooterViewHolder(mInflater.inflate(R.layout.list_footer_layout, parent, false));
                }
            default:
                final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, type);
                if (holder != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemClickListener != null)
                                onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.getItemId(), v);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (onItemLongClickListener == null)
                                return false;
                            onItemLongClickListener.onLongClick(holder.getAdapterPosition(), holder.getItemId(), v);
                            return true;
                        }
                    });
                }
                Log.v(TAG, "holder=" + holder.getItemViewType());
                return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        Log.d(TAG, "onBindViewHolder.." + position + "..." + h.getItemViewType());
        addAnimation(h);
        switch (h.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    onLoadingHeaderCallBack.onBindHeaderHolder(h, position);
                break;

            case VIEW_TYPE_FOOTER:
                Log.d(TAG, "VIEW_TYPE_FOOTER.." + mState + ",," + onLoadingListener);
                if (onLoadingFooterCallBack != null) {
                    onLoadingFooterCallBack.onBindFooterHolder(h, position);
                    break;
                }

                if ((mState == STATE_LOAD_MORE) && onLoadingListener != null) {
                    onLoadingListener.onLoading();
                }
                FooterViewHolder fvh = (FooterViewHolder) h;
                fvh.itemView.setVisibility(View.VISIBLE);
                switch (mState) {
                    case STATE_INVALID_NETWORK:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.invalid_network));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_MORE:
                    case STATE_LOADING:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.loading));
                        fvh.probar.setVisibility(View.VISIBLE);
                        Log.i(TAG, "FooterViewHolder STATE_LOAD_MORE");
                        break;
                    case STATE_NO_MORE:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.load_no_more));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_REFRESHING:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.refreshing));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_ERROR:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.load_failed));
                        fvh.probar.setVisibility(View.GONE);
                        mState = STATE_LOAD_MORE;
                        break;
                    case STATE_HIDE:
                        fvh.itemView.setVisibility(View.GONE);
                        break;
                }
                break;

            default:
                onBindDefaultViewHolder(h, getIndex(position));
        }
    }

    protected void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * Set the view animation type.
     *
     * @param animationType
     */
    public void openLoadAnimation(int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case AnimationType.ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case AnimationType.SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case AnimationType.SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case AnimationType.SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case AnimationType.SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            case AnimationType.SLIDEIN_CUSTOM:
                mSelectAnimation = new CustomAnimation();
                break;
            default:
                break;
        }
    }

    public final void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    public final void setOnLoadingListener(OnLoadingListener listener) {
        onLoadingListener = listener;
    }

    public final void setOnLoadingHeaderCallBack(OnLoadingHeaderCallBack listener) {
        onLoadingHeaderCallBack = listener;
    }

    public final void setOnLoadingFooterCallBack(OnLoadingFooterCallBack listener) {
        onLoadingFooterCallBack = listener;
    }

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position);

    /**
     * footer view holder
     */
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar probar;
        public TextView mStateText;

        public FooterViewHolder(View view) {
            super(view);
            probar = (ProgressBar) view.findViewById(R.id.progressbar);
            mStateText = (TextView) view.findViewById(R.id.state_text);
        }
    }

    /**
     * adapter item on click
     */
    public interface OnItemClickListener {
        void onItemClick(int position, long id, View view);
    }

    /**
     * adapter item on long click
     */
    public interface OnItemLongClickListener {
        void onLongClick(int position, long id, View view);
    }

    /**
     * when footer disappear, we should load data from remote internet
     */
    public interface OnLoadingListener {
        void onLoading();
    }

    /**
     * for load header view
     */
    public interface OnLoadingHeaderCallBack {
        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

        void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
    }

    public interface OnLoadingFooterCallBack {
        RecyclerView.ViewHolder onCreateFooterHolder(ViewGroup parent);

        void onBindFooterHolder(RecyclerView.ViewHolder holder, int position);
    }

}
